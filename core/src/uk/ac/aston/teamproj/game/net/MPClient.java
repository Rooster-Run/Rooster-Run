package uk.ac.aston.teamproj.game.net;

import java.util.ArrayList;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.packet.CreateGameSession;
import uk.ac.aston.teamproj.game.net.packet.JoinGameSession;
import uk.ac.aston.teamproj.game.net.packet.Login;
import uk.ac.aston.teamproj.game.net.packet.PlayerPosition;
import uk.ac.aston.teamproj.game.net.packet.SessionInfo;
import uk.ac.aston.teamproj.game.net.packet.StartGame;
import uk.ac.aston.teamproj.game.screens.LoadingScreen;
import uk.ac.aston.teamproj.game.screens.LobbyScreen;
import uk.ac.aston.teamproj.game.screens.PlayScreen;

public class MPClient {
	
	private String ip = "localhost";

	public static Client client;
	public static int clientID;
	public int sessionID;
	
	public MainGame game;
	private String name;
	private String mapPath;

	
	public MPClient(String ip, String name, MainGame game) {
		this.name = name;
		this.game = game;
		
		client = new Client();
		client.start();
		
		Network.register(client);
		
		try {
			client.connect(60000, ip, Network.TCP_PORT, Network.UDP_PORT);
			requestLogin();
			game.setScreen(new LobbyScreen(game));
		} catch (Exception e) {
			System.err.println("Error. Cannot reach the server.");
		}
		
		client.addListener(new ThreadedListener(new Listener() {
			
			public void connected(Connection connection) {
				// get text here
			}
			
			public void received(Connection connection, Object object) {
				
				if(object instanceof Login) {
					Login packet = (Login) object;
					clientID = packet.id;
					System.out.println("I have successfully connected to the server and my clientID is: " + clientID);
				}
				
				if(object instanceof CreateGameSession) {
					CreateGameSession packet = (CreateGameSession) object;
					System.out.println("The lobby has been created. You can invite players with the following code: " + packet.token);
				}
				
				if(object instanceof JoinGameSession) {
					JoinGameSession packet = (JoinGameSession) object;
					// start the game
					System.out.println(packet.token);
				}
				
				if(object instanceof SessionInfo) {
					SessionInfo packet = (SessionInfo) object;
					System.out.println("Total players: " + packet.playerIDs.size());
					System.out.print("Players: ");
					for (int i = 0 ; i < packet.playerIDs.size(); i++) {
						System.out.print("[" + packet.playerIDs.get(i) + " - " + packet.playerNames.get(i) +  "] ");
					}
					System.out.println();
					System.out.println("Map: " + packet.mapPath);
					PlayScreen.mapPath = packet.mapPath;
					PlayScreen.sessionID = packet.token;
					PlayScreen.myID = packet.playerID;
				}
				
				if(object instanceof StartGame) {
					StartGame packet = (StartGame) object;
					PlayScreen.players = new ArrayList<Player>();
					for (int i = 0; i < packet.playerIDs.size() && i < packet.playerNames.size(); i++) {
						Player p = new Player(packet.playerIDs.get(i), packet.playerNames.get(i));
						PlayScreen.players.add(p);
					}
					
					LobbyScreen.isGameAboutToStart = true;
				}
				
				if(object instanceof PlayerPosition) {
					PlayerPosition packet = (PlayerPosition) object;
					for (Player p : PlayScreen.players) {
						if (p.getID() == packet.playerID) {
							p.setPosX(packet.posX);
						}
					}
				}
			}

		}));
		
	}
	

	public void requestLogin() {
		Login login = new Login();
		login.name = name;
		client.sendTCP(login);
	}

}
