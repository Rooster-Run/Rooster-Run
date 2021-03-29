package uk.ac.aston.teamproj.game.net.packet;

public class Login {
	private String name;
	private int ID;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the id
	 */
	public int getID() {
		return ID;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param id the id to set
	 */
	public void setID(int id) {
		this.ID = id;
	}
}
