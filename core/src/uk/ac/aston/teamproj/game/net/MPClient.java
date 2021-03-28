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
import uk.ac.aston.teamproj.game.screens.MultiCreateScreen;
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
			if (game.getScreen() instanceof MultiCreateScreen)
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
					System.out.println("Total players: " + packet.getPlayerIDs().size());
					// totalPlayers = packet.playerIDs.size();
					System.out.print("Players: ");
					LobbyScreen.currentPlayers = new ArrayList<>();
					for (int i = 0; i < packet.getPlayerIDs().size(); i++) {
						Integer id = packet.getPlayerIDs().get(i);
						String name = packet.getPlayerNames().get(i);
						System.out.print("[" + id + " - " + name + "] ");
						// playerName = packet.playerNames.get(i);
						// playerID = packet.playerNames.get(i);
						// n.add(packet.playerNames.get(i));
						LobbyScreen.currentPlayers.add(new Player(id, name));
					}
					System.out.println();
					System.out.println("Map: " + packet.getMapPath());
					PlayScreen.mapPath = packet.getMapPath();
					PlayScreen.sessionID = packet.getToken();
					PlayScreen.myID = packet.getPlayerID();
					isReady = true;
				}

				if (object instanceof StartGame) {
					StartGame packet = (StartGame) object;
					PlayScreen.players = new ArrayList<Player>();
					for (int i = 0; i < packet.getPlayerIDs().size() && i < packet.getPlayerNames().size(); i++) {
						Player p = new Player(packet.getPlayerIDs().get(i), packet.getPlayerNames().get(i));
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
						if (p.getID() == packet.getPlayerID()) {
							p.setPosX(packet.getPosX());
							p.setLives(packet.getLives());
							p.setCoins(packet.getCoins());
						}
					}
				}

				if (object instanceof Winner) {
					Winner packet = (Winner) object;
					PlayScreen.winner = packet.getWinnerName();
				}

				if (object instanceof IceEffect) {
					PlayScreen.player.setIceEffect();
				}
			}
		}));

	}

	public void requestLogin() {
		Login login = new Login();
		login.setName(name);
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
