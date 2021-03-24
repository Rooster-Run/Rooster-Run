package uk.ac.aston.teamproj.game.net;

import java.util.HashMap;
import java.util.Random;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.packet.CreateGameSession;
import uk.ac.aston.teamproj.game.net.packet.JoinGameSession;
import uk.ac.aston.teamproj.game.net.packet.Login;
import uk.ac.aston.teamproj.game.net.packet.PlayerInfo;
import uk.ac.aston.teamproj.game.net.packet.SessionInfo;
import uk.ac.aston.teamproj.game.net.packet.StartGame;
import uk.ac.aston.teamproj.game.net.packet.TerminateSession;
import uk.ac.aston.teamproj.game.net.packet.Winner;

public class MPServer {

	private MainGame game;
	private final static int TOKEN_LENGTH = 5;
	public Server server;
	public HashMap<String, GameSession> sessions;

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
			public void connected(Connection connection) {

			}

			public void received(Connection connection, Object object) {

				if (object instanceof Login) {
					Login packet = (Login) object;
					packet.id = connection.getID();
					server.sendToTCP(connection.getID(), packet);
				}

				if (object instanceof TerminateSession) {
					TerminateSession packet = (TerminateSession) object;
					sessions.get(packet.token).getPlayerByID(connection.getID()).playing = false;

					if (isDeleteable(packet)) {
						sessions.remove(packet.token);
					}

				}

				if (object instanceof CreateGameSession) {
					CreateGameSession packet = (CreateGameSession) object;
					String token = generateGameToken();
					packet.token = token;
					GameSession session = new GameSession(token, packet.mapPath);
					session.addPlayer(connection.getID(), packet.name);
					session.setHost(connection.getID());
					sessions.put(token, session);

					server.sendToTCP(connection.getID(), packet);
					notifyAllPlayers(session);
				}

				if (object instanceof JoinGameSession) {
					// get his token and see if exists
					JoinGameSession packet = (JoinGameSession) object;

					if (sessions.get(packet.token) == null || !sessions.containsKey(packet.token)) {
						// ErrorPacket invalidPacket = new ErrorPacket();
						// invalidPacket.invalidToken = true;
						packet.errorToken = true;
						server.sendToTCP(connection.getID(), packet);
					} else {
						sessions.get(packet.token).addPlayer(connection.getID(), packet.name);
						notifyAllPlayers(sessions.get(packet.token));

						server.sendToTCP(connection.getID(), packet);
					}
				}

				if (object instanceof StartGame) {
					StartGame packet = (StartGame) object;
					if (sessions.get(packet.token) != null) {
						GameSession session = sessions.get(packet.token);
						packet.playerIDs = session.getPlayerIDs();
						packet.playerNames = session.getPlayerNames();
						for (Integer connectionID : session.getPlayerIDs()) {
							server.sendToTCP(connectionID, packet);
						}
					}
					System.out.println(sessions.get(packet.token));
				}

				if (object instanceof PlayerInfo) {
					PlayerInfo packet = (PlayerInfo) object;
					if (sessions.get(packet.token) != null) {
						GameSession session = sessions.get(packet.token);
						for (Integer connectionID : session.getPlayerIDs()) {
							server.sendToTCP(connectionID, packet);
						}
					}
				}
				if (object instanceof Winner) {
					Winner packet = (Winner) object;
					System.out.println(1);

					if (sessions.get(packet.token) != null) {
						GameSession session = sessions.get(packet.token);
						String winnerName = "";
						for (Player p: session.getPlayers()) {
							if (p.getID()==packet.playerID)
								winnerName = p.getName();
						}
						System.out.println(2 +" " + winnerName);

						
						if(session.setWinner(winnerName)) {
							packet.winnerName = winnerName;							 
							for (Integer connectionID : session.getPlayerIDs()) {
								server.sendToTCP(connectionID, packet);
							}
							System.out.println(3);
						}						
					}
				}
			}
		});
	}

	private boolean isDeleteable(TerminateSession packet) {
		for (Player player : sessions.get(packet.token).getPlayers())
			if (player.playing)
				return false;
		return true;
	}

	private void notifyAllPlayers(GameSession session) {
		SessionInfo packet = new SessionInfo();
		packet.playerIDs = session.getPlayerIDs();
		packet.playerNames = session.getPlayerNames();
		packet.mapPath = session.getMapPath();
		packet.token = session.getToken();
		for (Integer connectionID : session.getPlayerIDs()) {
			packet.playerID = connectionID;
			server.sendToTCP(connectionID, packet);
		}
	}

	private String generateGameToken() {
		String saltStr;
		do {
			String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
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

	public static void main(String[] args) {
		// Log.set(Log.LEVEL_DEBUG);
		new MPServer();
	}
}
