package uk.ac.aston.teamproj.game.net.packet;

import java.util.ArrayList;

public class SessionInfo {
	public boolean gameOver;
	public int playerID;
	public ArrayList<Integer> playerIDs;
	public ArrayList<String> playerNames;
	public String mapPath;
	public String token;
	public boolean hasStarted;
}