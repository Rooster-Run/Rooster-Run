package uk.ac.aston.teamproj.game.net.packet;

import java.util.ArrayList;

public class SessionInfo {
	private boolean gameOver;
	private int playerID;
	private ArrayList<Integer> playerIDs;
	private ArrayList<String> playerNames;
	private String mapPath;
	private String token;
	/**
	 * @return the gameOver
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	/**
	 * @return the playerID
	 */
	public int getPlayerID() {
		return playerID;
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
	 * @return the mapPath
	 */
	public String getMapPath() {
		return mapPath;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param gameOver the gameOver to set
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	/**
	 * @param playerID the playerID to set
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
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
	/**
	 * @param mapPath the mapPath to set
	 */
	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
}