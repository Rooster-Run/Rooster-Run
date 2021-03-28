package uk.ac.aston.teamproj.game.net.packet;

public class Winner {
	private String token;
	private int playerID;
	private String winnerName;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @return the playerID
	 */
	public int getPlayerID() {
		return playerID;
	}
	/**
	 * @return the winnerName
	 */
	public String getWinnerName() {
		return winnerName;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @param playerID the playerID to set
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	/**
	 * @param winnerName the winnerName to set
	 */
	public void setWinnerName(String winnerName) {
		this.winnerName = winnerName;
	}
}
