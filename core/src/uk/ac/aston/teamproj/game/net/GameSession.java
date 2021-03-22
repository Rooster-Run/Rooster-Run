package uk.ac.aston.teamproj.game.net;

import java.util.ArrayList;

public class GameSession {
	
	private String token;
	private int hostID;
	private ArrayList<Player> players;
	
	private String mapPath;
	
	public GameSession(String token, String mapPath) {
		this.token = token;
		this.mapPath = mapPath;
		players = new ArrayList<>();
	}
	
	public void addPlayer(int id, String name) {
		players.add(new Player(id, name));
	}
	
	public void setHost(int id) {
		this.hostID = id;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public ArrayList<Integer> getPlayerIDs() {
		ArrayList<Integer> list = new ArrayList<>();
		for (Player p : players) {
			list.add(p.getID());
		}
		return list;
	}
	
	public ArrayList<String> getPlayerNames() {
		ArrayList<String> list = new ArrayList<>();
		for (Player p : players) {
			list.add(p.getName());
		}
		return list;
	}
	
	public String getToken() {
		return token;
	}
	
	public int getHost() {
		return hostID;
	}
	
	public String getMapPath() {
		return mapPath;
	}
	
	public boolean isEmpty() {
		return players.isEmpty();
	}
}
