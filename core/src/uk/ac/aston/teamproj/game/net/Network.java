package uk.ac.aston.teamproj.game.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

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

public class Network {

	public static final int TCP_PORT = 54555;
	public static final int UDP_PORT = 54556;
	
	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Login.class);
		kryo.register(CreateGameSession.class);
		kryo.register(JoinGameSession.class);
		kryo.register(SessionInfo.class);
		kryo.register(java.util.ArrayList.class);
		kryo.register(StartGame.class);
		kryo.register(PlayerInfo.class);
		kryo.register(TerminateSession.class);
		kryo.register(Winner.class);
		kryo.register(IceEffect.class);
		kryo.register(LeftGameSession.class);
	}
	
}
