package uk.ac.aston.teamproj.game.net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import uk.ac.aston.teamproj.game.net.packet.CreateGameSession;
import uk.ac.aston.teamproj.game.net.packet.IceEffect;
import uk.ac.aston.teamproj.game.net.packet.JoinGameSession;
import uk.ac.aston.teamproj.game.net.packet.LeftGameSession;
import uk.ac.aston.teamproj.game.net.packet.Login;
import uk.ac.aston.teamproj.game.net.packet.PlayerInfo;
import uk.ac.aston.teamproj.game.net.packet.SessionInfo;
import uk.ac.aston.teamproj.game.net.packet.StartGame;
import uk.ac.aston.teamproj.game.net.packet.TerminateSession;
import uk.ac.aston.teamproj.game.net.packet.Winner;


/**
 * The Class MPServer.
 */
public class MPServer {

	/** The Constant TOKEN_LENGTH. */
	private final static int TOKEN_LENGTH = 5;
	
	/** The server. */
	private final Server server;
	
	/** The sessions. */
	private final HashMap<String, GameSession> sessions;

	/**
	 * Instantiates a new MP server.
	 */
	public MPServer() {
		server = new Server();
		server.start();

		Network.register(server);

		sessions = new HashMap<>();

		try {
			server.bind(Network.TCP_PORT, Network.UDP_PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		server.addListener(new Listener() {
			
			public void disconnected(Connection connection) {
				
				boolean found = false;
				for (
						Iterator<Map.Entry<String, GameSession>> iter = sessions.entrySet().iterator();
						iter.hasNext() || !found;
				) {
					Map.Entry<String, GameSession> entry = iter.next();
					String key = entry.getKey();
				    GameSession session = entry.getValue();
				    if (session.removePlayerByID(connection.getID())) {
				    	if (session.getPlayers().size() <= 0) {
							sessions.remove(key);
						} else {						
							notifyAllPlayers(session);
						}
				    	found = true;
				    }						
				}				
			}
			
			public void received(Connection connection, Object object) {

				if (object instanceof Login) {
					Login packet = (Login) object;
					packet.setID(connection.getID());
					server.sendToTCP(connection.getID(), packet);
				}

				if (object instanceof TerminateSession) {
					TerminateSession packet = (TerminateSession) object;
					GameSession session = sessions.get(packet.getToken());
					if (session != null) {
						session.getPlayerByID(connection.getID()).playing = false;
	
						if (isDeleteable(packet)) {
							sessions.remove(packet.getToken());
						}
					}
				}

				if (object instanceof LeftGameSession) {
					LeftGameSession packet = (LeftGameSession) object;
					GameSession session = sessions.get(packet.getToken());
					if (session != null) {
						session.removePlayerByID(packet.getPlayerID());
						
						if (session.getPlayers().size() <= 0) {
							sessions.remove(packet.getToken());
						} else {						
							notifyAllPlayers(session);
						}
					}
				}

				if (object instanceof CreateGameSession) {
					CreateGameSession packet = (CreateGameSession) object;
					String token = generateGameToken();
					packet.setToken(token);
					GameSession session = new GameSession(token, packet.getMapPath());
					session.addPlayer(connection.getID(), packet.getName());
					session.setHost(connection.getID());
					sessions.put(token, session);
					server.sendToTCP(connection.getID(), packet);
					notifyAllPlayers(session);
				}

				if (object instanceof JoinGameSession) {
					// Get users token
					JoinGameSession packet = (JoinGameSession) object;
					GameSession session = sessions.get(packet.getToken());
					
					// Checking if the token is correct
					if (session == null) {;
						packet.setErrorToken(true); // token is wrong
						
						// Checking if session is in progress
					} else if (session.getHasStarted()) {
						packet.setJoinedLate(true); // session has started
						
					} else if (session.isFull()){
						packet.setFull(true);
						
					} else {
						session.addPlayer(connection.getID(), packet.getName());
						packet.setJoinedLate(session.getHasStarted()); // session hasn't started
						notifyAllPlayers(session);
					}
					
					server.sendToTCP(connection.getID(), packet);
				}

				if (object instanceof StartGame) {
					StartGame packet = (StartGame) object;
					if (sessions.get(packet.getToken()) != null) {
						GameSession session = sessions.get(packet.getToken());
						packet.setPlayerIDs(session.getPlayerIDs());
						packet.setPlayerNames(session.getPlayerNames());
						session.setHasStarted(true); // Host has started the session
						for (Integer connectionID : session.getPlayerIDs()) {
							server.sendToTCP(connectionID, packet);
						}
					}
				}

				if (object instanceof PlayerInfo) {
					PlayerInfo packet = (PlayerInfo) object;
					if (sessions.get(packet.getToken()) != null) {
						GameSession session = sessions.get(packet.getToken());
						for (Integer connectionID : session.getPlayerIDs()) {
							server.sendToTCP(connectionID, packet);
						}
					}
				}

				if (object instanceof Winner) {
					Winner packet = (Winner) object;

					if (sessions.get(packet.getToken()) != null) {
						GameSession session = sessions.get(packet.getToken());
						String winnerName = "";
						for (Player p : session.getPlayers()) {
							if (p.getID() == packet.getPlayerID())
								winnerName = p.getName();
						}

						if (session.setWinner(winnerName)) {
							packet.setWinnerName(winnerName);
							for (Integer connectionID : session.getPlayerIDs()) {
								server.sendToTCP(connectionID, packet);
							}
						}
					}
				}

				if (object instanceof IceEffect) {
					IceEffect packet = (IceEffect) object;
					if (sessions.get(packet.getToken()) != null) {
						GameSession session = sessions.get(packet.getToken());
						for (Integer connectionID : session.getPlayerIDs()) {
							if (connectionID != packet.getPlayerID())
								server.sendToTCP(connectionID, packet);
						}
					}
				}

			}
		});
	}

	/**
	 * Checks if is deleteable.
	 *
	 * @param packet the packet
	 * @return true, if is deleteable
	 */
	private boolean isDeleteable(TerminateSession packet) {
		for (Player player : sessions.get(packet.getToken()).getPlayers())
			if (player.playing)
				return false;
		return true;
	}

	/**
	 * Notify all players.
	 *
	 * @param session the session
	 */
	private void notifyAllPlayers(GameSession session) {
		SessionInfo packet = new SessionInfo();
		packet.setPlayerIDs(session.getPlayerIDs());
		packet.setPlayerNames(session.getPlayerNames());
		packet.setMapPath(session.getMapPath());
		packet.setToken(session.getToken());
		for (Integer connectionID : session.getPlayerIDs()) {
			packet.setPlayerID(connectionID);
			server.sendToTCP(connectionID, packet);
		}
	}

	/**
	 * Generate game token.
	 *
	 * @return the string
	 */
	private String generateGameToken() {
		String saltStr;
		do {
			String SALTCHARS = "ABCDEFGHIJKLMNPQRSTUVWXYZ123456789";
			StringBuilder salt = new StringBuilder();
			Random rnd = new Random();
			while (salt.length() < TOKEN_LENGTH) { // length of the random string.
				int index = (int) (rnd.nextFloat() * SALTCHARS.length());
				salt.append(SALTCHARS.charAt(index));
			}
			saltStr = salt.toString();
		} while (sessions.get(saltStr) != null);

		return saltStr;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		new MPServer();
	}
}
