package uk.ac.aston.teamproj.game.net;

public class Player {
	private int id;
	private String name;
	private float posX = 0;
	
	public Player(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void setPosX(float posX) {
		this.posX = posX;
	}
	
	public String getInfo() {
		return "[" + id + " - " + name + "]";
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public float getPosX() {
		return posX;
	}
}
