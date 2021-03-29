package uk.ac.aston.teamproj.game.net;

import java.util.ArrayList;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.packet.IceEffect;
import uk.ac.aston.teamproj.game.net.packet.JoinGameSession;
import uk.ac.aston.teamproj.game.net.packet.LeftGameSession;
import uk.ac.aston.teamproj.game.net.packet.Login;
import uk.ac.aston.teamproj.game.net.packet.PlayerInfo;
import uk.ac.aston.teamproj.game.net.packet.SessionInfo;
import uk.ac.aston.teamproj.game.net.packet.StartGame;
import uk.ac.aston.teamproj.game.net.packet.Winner;
import uk.ac.aston.teamproj.game.screens.multi.LobbyScreen;
import uk.ac.aston.teamproj.game.screens.multi.MultiCreateScreen;
import uk.ac.aston.teamproj.game.screens.multi.MultiPlayScreen;
import uk.ac.aston.teamproj.game.screens.multi.MultiplayerMenuScreen;
import uk.ac.aston.teamproj.game.screens.multi.ServerErrorScreen;

/**
 * The Class MPClient.
 */
public class MPClient {

	/** The client. */
	public static Client client;
	
	/** The client ID. */
	private static int clientID;
	
	/** The session ID. */
	private int sessionID;

	/** The game. */
	private MainGame game;
	
	/** The name. */
	private String name;
	
	/** The is token wrong. */
	private boolean isTokenWrong = false;
	
	/** The is late. */
	private boolean isLate = false;
	
	/** The is host. */
	private boolean isHost = false;
	
	/** The is ready. */
	private boolean isReady = false;
	
	/** The is full. */
	private boolean isFull = false;

	/**
	 * Instantiates a new MP client.
	 *
	 * @param ip the ip
	 * @param name the name
	 * @param game the game
	 */
	public MPClient(String ip, String name, final MainGame game) {
		this.name = name;
		this.game = game;

		client = new Client();
		client.start();

		Network.register(client);

		try {
			client.connect(60000, ip, Network.TCP_PORT, Network.UDP_PORT);
			requestLogin();
			if (game.getScreen() instanceof MultiCreateScreen)
				isHost = true;

		} catch (Exception e) {
			game.setScreen(new ServerErrorScreen(game));
		}

		client.addListener(new ThreadedListener(new Listener() {

			public void disconnected(Connection connection) {
				LeftGameSession packet = new LeftGameSession();
				packet.setToken(MultiPlayScreen.sessionID);
				packet.setPlayerID(MultiPlayScreen.myID);
				client.sendTCP(packet);
			}
			
			public void connected(Connection connection) {
				// get text here
			}

			public void received(Connection connection, Object object) {

				if (object instanceof Login) {
					Login packet = (Login) object;
					clientID = packet.getID();
				}

				if (object instanceof JoinGameSession) {
					JoinGameSession packet = (JoinGameSession) object;
					// start the game
					isTokenWrong = packet.isErrorToken(); //checking for wrong token entry
					isLate = packet.isJoinedLate(); //checking for late game session joiners
					isFull = packet.isFull();
					//Checking if wrong token has been entered
					isReady = true;
				}

				if (object instanceof SessionInfo) {
					SessionInfo packet = (SessionInfo) object;
					LobbyScreen.currentPlayers = new ArrayList<>();
					for (int i = 0; i < packet.getPlayerIDs().size(); i++) {
						Integer id = packet.getPlayerIDs().get(i);
						String name = packet.getPlayerNames().get(i);
						LobbyScreen.currentPlayers.add(new Player(id, name));
					}
					MultiPlayScreen.mapPath = packet.getMapPath();
					MultiPlayScreen.sessionID = packet.getToken();
					MultiPlayScreen.myID = packet.getPlayerID();
					isReady = true;
				}

				if (object instanceof StartGame) {
					StartGame packet = (StartGame) object;
					MultiPlayScreen.players = new ArrayList<Player>();
					for (int i = 0; i < packet.getPlayerIDs().size() && i < packet.getPlayerNames().size(); i++) {
						Player p = new Player(packet.getPlayerIDs().get(i), packet.getPlayerNames().get(i));
						MultiPlayScreen.players.add(p);

					}

					LobbyScreen.isGameAboutToStart = true;
				}

				if (object instanceof PlayerInfo) {
					PlayerInfo packet = (PlayerInfo) object;
					for (Player p : MultiPlayScreen.players) {
						if (p.getID() == packet.getPlayerID()) {
							p.setPosX(packet.getPosX());
							p.setLives(packet.getLives());
							p.setCoins(packet.getCoins());
						}
					}
				}

				if (object instanceof Winner) {
					Winner packet = (Winner) object;
					MultiPlayScreen.winner = packet.getWinnerName();
				}

				if (object instanceof IceEffect) {
					MultiPlayScreen.getPlayer().setIceEffect();
				}
			}
		}));

	}

	/**
	 * Request login.
	 */
	public void requestLogin() {
		Login login = new Login();
		login.setName(name);
		client.sendTCP(login);
	}
	
	/**
	 * Checks if is token wrong.
	 *
	 * @return true, if is token wrong
	 */
	public boolean isTokenWrong() {
		return isTokenWrong;
	}
	
	/**
	 * Checks if is late.
	 *
	 * @return true, if is late
	 */
	public boolean isLate() {
		return isLate;
	}
	
	/**
	 * Checks if is host.
	 *
	 * @return true, if is host
	 */
	public boolean isHost() {
		return isHost;
	}
	
	/**
	 * Checks if is ready.
	 *
	 * @return true, if is ready
	 */
	public boolean isReady() {
		return isReady;
	}
	
	/**
	 * Checks if is full.
	 *
	 * @return true, if is full
	 */
	public boolean isFull() {
		return isFull;
	}
}
