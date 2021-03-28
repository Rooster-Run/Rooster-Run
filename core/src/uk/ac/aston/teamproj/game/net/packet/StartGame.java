package uk.ac.aston.teamproj.game.net.packet;

import java.util.ArrayList;


public class StartGame {
	private String token;
	private ArrayList<Integer> playerIDs;
	private ArrayList<String> playerNames;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @return the playerIDs
	 */
	public ArrayList<Integer> getPlayerIDs() {
		return playerIDs;
	}
	/**
	 * @return the playerNames
	 */
	public ArrayList<String> getPlayerNames() {
		return playerNames;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @param playerIDs the playerIDs to set
	 */
	public void setPlayerIDs(ArrayList<Integer> playerIDs) {
		this.playerIDs = playerIDs;
	}
	/**
	 * @param playerNames the playerNames to set
	 */
	public void setPlayerNames(ArrayList<String> playerNames) {
		this.playerNames = playerNames;
	}
}
