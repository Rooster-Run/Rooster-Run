package uk.ac.aston.teamproj.game.net;

public class Player {

	private final static int DEFAULT_LIVES = 3;
	
	private int id;
	private String name;
	private float posX = 0;
	
	private int lives = DEFAULT_LIVES;
	private int coins = 0;
	
	public boolean playing;
	
	public Player(int id, String name) {
		this.id = id;
		this.name = name;
		this.playing = true;
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
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void setCoins(int coins) {
		this.coins = coins;
	}
	
	public int getCoins() {
		return coins;
	}
	
	
}
