package uk.ac.aston.teamproj.game.net.packet;

public class CreateGameSession {
	private String token;
	private String name;
	private String mapPath;
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
	 * @return the mapPath
	 */
	public String getMapPath() {
		return mapPath;
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
	 * @param mapPath the mapPath to set
	 */
	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}
}
