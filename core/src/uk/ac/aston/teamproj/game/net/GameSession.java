package uk.ac.aston.teamproj.game.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import uk.ac.aston.teamproj.game.sprites.Bomb;

public class GameSession {
	
	private static final int SESSION_LIMIT = 4;
	private String token;
	private int hostID;
	private ArrayList<Player> players;
	private boolean hasStarted;
	private String mapPath;
	
	private String winner;
	
	public GameSession(String token, String mapPath) {
		this.token = token;
		this.mapPath = mapPath;
		players = new ArrayList<>();
	}
	
	public void addPlayer(int id, String name) {
			players.add(new Player(id, name));
	}
	
	public boolean isFull() {
		if(players.size() >= SESSION_LIMIT) {
			return true;
		}
		return false;
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
	
	public void setHasStarted(boolean started) {
		hasStarted = started;
	}
	
	public boolean getHasStarted () {
		return hasStarted; 
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
	
	public Player getPlayerByID(int id) {
		for(Player player: players) {
			if(player.getID() == id) {
				return player;
			}
		}
		return null;
	}
	
	public boolean setWinner(String winner) {
		if (this.winner == null) {
			this.winner = winner;
			return true;
		}
		return false;
	}
	
	public String getWinnerName() {
		return winner;
	}
	
	public void removePlayerByID(int id) {
		if (players != null) {
			for (Iterator<Player> iter = players.iterator(); iter.hasNext();) {
				if(iter.next().getID() == id) {
					iter.remove();
				}
			}
		}
	}
}
