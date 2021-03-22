package uk.ac.aston.teamproj.game.net.packet;

import java.util.ArrayList;

import uk.ac.aston.teamproj.game.net.Player;

public class SessionInfo {
	public boolean gameOver;
	public int playerID;
	public ArrayList<Integer> playerIDs;
	public ArrayList<String> playerNames;
	public String mapPath;
	public String token;
}
