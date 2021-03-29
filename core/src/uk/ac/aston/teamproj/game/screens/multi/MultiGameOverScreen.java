package uk.ac.aston.teamproj.game.screens.multi;


import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.screens.GameOverScreen;

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
		return "Coins Collected: " + MultiPlayScreen.getPlayer().getCoins();
	}

	@Override
	public void resetSession() {
		MultiPlayScreen.resetSession();
		
	}



}
