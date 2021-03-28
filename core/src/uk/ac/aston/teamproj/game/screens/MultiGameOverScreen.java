package uk.ac.aston.teamproj.game.screens;


import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.superclass.GameOverScreen;

/**
 * 
 * Created by Parmo on 8/11/2020
 *
 */

public class MultiGameOverScreen extends GameOverScreen {


	public MultiGameOverScreen(MainGame game) {
		super(game);
	}
	
	public String showCoins() {
		return "Coins Collected: " + PlayScreen.player.getCoins();
	}

	@Override
	public void resetSession() {
		PlayScreen.resetSession();
		
	}



}
