package uk.ac.aston.teamproj.game.net.packet;

public class LeftGameSession {
	private int playerID;
	private String token;
	private boolean isHost;
	/**
	 * @return the playerID
	 */
	public int getPlayerID() {
		return playerID;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @return the isHost
	 */
	public boolean isHost() {
		return isHost;
	}
	/**
	 * @param playerID the playerID to set
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @param isHost the isHost to set
	 */
	public void setHost(boolean isHost) {
		this.isHost = isHost;
	}
}