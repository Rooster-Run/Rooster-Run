package uk.ac.aston.teamproj.game.net.packet;

public class PlayerInfo {
	private String token;
	private Integer playerID;
	private float posX;
	private int lives;
	private int coins;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @return the playerID
	 */
	public Integer getPlayerID() {
		return playerID;
	}
	/**
	 * @return the posX
	 */
	public float getPosX() {
		return posX;
	}
	/**
	 * @return the lives
	 */
	public int getLives() {
		return lives;
	}
	/**
	 * @return the coins
	 */
	public int getCoins() {
		return coins;
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
	public void setPlayerID(Integer playerID) {
		this.playerID = playerID;
	}
	/**
	 * @param posX the posX to set
	 */
	public void setPosX(float posX) {
		this.posX = posX;
	}
	/**
	 * @param lives the lives to set
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}
	/**
	 * @param coins the coins to set
	 */
	public void setCoins(int coins) {
		this.coins = coins;
	}
}
