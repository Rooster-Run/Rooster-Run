package uk.ac.aston.teamproj.game.tools;

import com.badlogic.gdx.graphics.Texture;

public class Map {
	
	private Texture image;
	private String path;
	private int length;

	public Map(Texture image, String path, int length) {
		this.image = image;
		this.path = path;
		this.length = length;
	}
	
	public String getPath() {
		return path;
	}
	
	public int getLength() {
		return length;
	}
	
	public Texture getImage() {
		return image;
	}
	
	public int getCamPosition() {
		int camPos;
		switch (length) {
			case 300:
				camPos = 27600;
				break;
			case 380:
				camPos = 35275;
				break;
			case 180:
				camPos = 16000;
				break;
			default:
				camPos = 50000;
		}
		
		return camPos;
	}
}
