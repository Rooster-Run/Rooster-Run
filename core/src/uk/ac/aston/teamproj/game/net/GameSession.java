package uk.ac.aston.teamproj.game.net;

import java.util.ArrayList;

public class GameSession {
	
	private String token;
	private int hostID;
	private boolean active;
	private ArrayList<Integer> players;
	
	public GameSession(String token) {
		this.token = token;
		players = new ArrayList<>();
	}
	
	public void addPlayer(int id) {
		players.add(id);
	}
	
	public void setHost(int id) {
		this.hostID = id;
	}
	
	public ArrayList<Integer> getPlayers() {
		return players;
	}
	
	public String getToken() {
		return token;
	}
	
	public int getHost() {
		return hostID;
	}
}
