package uk.ac.aston.teamproj.game.net;

import java.util.ArrayList;
import java.util.Iterator;




/**
 * The Class GameSession.
 */
public class GameSession {
	
	/** The Constant SESSION_LIMIT. */
	private static final int SESSION_LIMIT = 4;
	
	/** The token. */
	private String token;
	
	/** The host ID. */
	private int hostID;
	
	/** The players. */
	private ArrayList<Player> players;
	
	/** The has started. */
	private boolean hasStarted;
	
	/** The map path. */
	private String mapPath;
	
	/** The winner. */
	private String winner;
	
	/**
	 * Instantiates a new game session.
	 *
	 * @param token the token
	 * @param mapPath the map path
	 */
	public GameSession(String token, String mapPath) {
		this.token = token;
		this.mapPath = mapPath;
		players = new ArrayList<>();
	}
	
	/**
	 * Adds the player.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public void addPlayer(int id, String name) {
			players.add(new Player(id, name));
	}
	
	/**
	 * Checks if is full.
	 *
	 * @return true, if is full
	 */
	public boolean isFull() {
		if(players.size() >= SESSION_LIMIT) {
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the host.
	 *
	 * @param id the new host
	 */
	public void setHost(int id) {
		this.hostID = id;
	}
	
	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Gets the player I ds.
	 *
	 * @return the player I ds
	 */
	public ArrayList<Integer> getPlayerIDs() {
		ArrayList<Integer> list = new ArrayList<>();
		for (Player p : players) {
			list.add(p.getID());
		}
		return list;
	}
	
	/**
	 * Gets the player names.
	 *
	 * @return the player names
	 */
	public ArrayList<String> getPlayerNames() {
		ArrayList<String> list = new ArrayList<>();
		for (Player p : players) {
			list.add(p.getName());
		}
		return list;
	}
	
	/**
	 * Sets the checks for started.
	 *
	 * @param started the new checks for started
	 */
	public void setHasStarted(boolean started) {
		hasStarted = started;
	}
	
	/**
	 * Gets the checks for started.
	 *
	 * @return the checks for started
	 */
	public boolean getHasStarted () {
		return hasStarted; 
	}
	
	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	
	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public int getHost() {
		return hostID;
	}
	
	/**
	 * Gets the map path.
	 *
	 * @return the map path
	 */
	public String getMapPath() {
		return mapPath;
	}
	
	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return players.isEmpty();
	}
	
	/**
	 * Gets the player by ID.
	 *
	 * @param id the id
	 * @return the player by ID
	 */
	public Player getPlayerByID(int id) {
		for(Player player: players) {
			if(player.getID() == id) {
				return player;
			}
		}
		return null;
	}
	
	/**
	 * Sets the winner.
	 *
	 * @param winner the winner
	 * @return true, if successful
	 */
	public boolean setWinner(String winner) {
		if (this.winner == null) {
			this.winner = winner;
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the winner name.
	 *
	 * @return the winner name
	 */
	public String getWinnerName() {
		return winner;
	}
	
	/**
	 * Removes the player by ID.
	 *
	 * @param id the id
	 */
	public boolean removePlayerByID(int id) {
		if (players != null) {
			for (Iterator<Player> iter = players.iterator(); iter.hasNext();) {
				if(iter.next().getID() == id) {
					iter.remove();
					return true;
				}
			}
		}
		return false;
	}
}
