package uk.ac.aston.teamproj.game.net.packet;

public class JoinGameSession {
	private String token;
	private String name;
	private boolean errorToken;
	private boolean joinedLate;
	private boolean isFull;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the errorToken
	 */
	public boolean isErrorToken() {
		return errorToken;
	}
	/**
	 * @return the joinedLate
	 */
	public boolean isJoinedLate() {
		return joinedLate;
	}
	/**
	 * @return the isFull
	 */
	public boolean isFull() {
		return isFull;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param errorToken the errorToken to set
	 */
	public void setErrorToken(boolean errorToken) {
		this.errorToken = errorToken;
	}
	/**
	 * @param joinedLate the joinedLate to set
	 */
	public void setJoinedLate(boolean joinedLate) {
		this.joinedLate = joinedLate;
	}
	/**
	 * @param isFull the isFull to set
	 */
	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}
}
