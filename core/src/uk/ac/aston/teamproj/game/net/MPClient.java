package uk.ac.aston.teamproj.game.net;

import java.util.ArrayList;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.packet.CreateGameSession;
import uk.ac.aston.teamproj.game.net.packet.IceEffect;
import uk.ac.aston.teamproj.game.net.packet.JoinGameSession;
import uk.ac.aston.teamproj.game.net.packet.LeftGameSession;
import uk.ac.aston.teamproj.game.net.packet.Login;
import uk.ac.aston.teamproj.game.net.packet.PlayerInfo;
import uk.ac.aston.teamproj.game.net.packet.SessionInfo;
import uk.ac.aston.teamproj.game.net.packet.StartGame;
import uk.ac.aston.teamproj.game.net.packet.Winner;
import uk.ac.aston.teamproj.game.screens.CreateScreen;
import uk.ac.aston.teamproj.game.screens.JoinScreen;
import uk.ac.aston.teamproj.game.screens.GameInProgressScreen;
import uk.ac.aston.teamproj.game.screens.LobbyScreen;
import uk.ac.aston.teamproj.game.screens.PlayScreen;
import uk.ac.aston.teamproj.game.screens.ServerErrorScreen;
import uk.ac.aston.teamproj.game.screens.TokenErrorScreen;

public class MPClient {

	public static Client client;
	public static int clientID;
	public int sessionID;

	public MainGame game;
	private String name;
	private boolean isTokenWrong = false;
	private boolean isLate = false;
	private boolean isHost = false;
	private boolean isReady = false;
	private boolean isFull = false;

	public MPClient(String ip, String name, final MainGame game) {
		this.name = name;
		this.game = game;

		client = new Client();
		client.start();

		Network.register(client);

		try {
			client.connect(60000, ip, Network.TCP_PORT, Network.UDP_PORT);
			requestLogin();
			if (game.getScreen() instanceof CreateScreen)
				isHost = true;

		} catch (Exception e) {
			game.setScreen(new ServerErrorScreen(game));
		}

		client.addListener(new ThreadedListener(new Listener() {

			public void connected(Connection connection) {
				// get text here
			}

			public void received(Connection connection, Object object) {

				if (object instanceof Login) {
					Login packet = (Login) object;
					clientID = packet.id;
				}

				if (object instanceof JoinGameSession) {
					JoinGameSession packet = (JoinGameSession) object;
					// start the game
					isTokenWrong = packet.errorToken; //checking for wrong token entry
					isLate = packet.joinedLate; //checking for late game session joiners
					isFull = packet.isFull;
					//Checking if wrong token has been entered
					isReady = true;
				}

				if (object instanceof SessionInfo) {
					SessionInfo packet = (SessionInfo) object;
					System.out.println("Total players: " + packet.playerIDs.size());
					// totalPlayers = packet.playerIDs.size();
					System.out.print("Players: ");
					LobbyScreen.currentPlayers = new ArrayList<>();
					for (int i = 0; i < packet.playerIDs.size(); i++) {
						Integer id = packet.playerIDs.get(i);
						String name = packet.playerNames.get(i);
						System.out.print("[" + id + " - " + name + "] ");
						// playerName = packet.playerNames.get(i);
						// playerID = packet.playerNames.get(i);
						// n.add(packet.playerNames.get(i));
						LobbyScreen.currentPlayers.add(new Player(id, name));
					}
					System.out.println();
					System.out.println("Map: " + packet.mapPath);
					PlayScreen.mapPath = packet.mapPath;
					PlayScreen.sessionID = packet.token;
					PlayScreen.myID = packet.playerID;
					isReady = true;
				}

				if (object instanceof StartGame) {
					StartGame packet = (StartGame) object;
					PlayScreen.players = new ArrayList<Player>();
					for (int i = 0; i < packet.playerIDs.size() && i < packet.playerNames.size(); i++) {
						Player p = new Player(packet.playerIDs.get(i), packet.playerNames.get(i));
						PlayScreen.players.add(p);

					}

					LobbyScreen.isGameAboutToStart = true;
				}

//				if (object instanceof ErrorPacket) {
//					ErrorPacket packet = (ErrorPacket) object;
//					// Other errors included here
//				}

				if (object instanceof PlayerInfo) {
					PlayerInfo packet = (PlayerInfo) object;
					for (Player p : PlayScreen.players) {
						if (p.getID() == packet.playerID) {
							p.setPosX(packet.posX);
							p.setLives(packet.lives);
							p.setCoins(packet.coins);
						}
					}
				}

				if (object instanceof Winner) {
					Winner packet = (Winner) object;
					PlayScreen.winner = packet.winnerName;
				}

				if (object instanceof IceEffect) {
					PlayScreen.player.setIceEffect();
				}
			}
		}));

	}

	public void requestLogin() {
		Login login = new Login();
		login.name = name;
		client.sendTCP(login);
	}
	
	public boolean isTokenWrong() {
		return isTokenWrong;
	}
	
	public boolean isLate() {
		return isLate;
	}
	
	public boolean isHost() {
		return isHost;
	}
	
	public boolean isReady() {
		return isReady;
	}
	
	public boolean isFull() {
		return isFull;
	}
}
