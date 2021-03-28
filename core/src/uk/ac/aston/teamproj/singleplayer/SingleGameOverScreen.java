package uk.ac.aston.teamproj.singleplayer;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.superclass.GameOverScreen;

public class SingleGameOverScreen extends GameOverScreen {
	
	public SingleGameOverScreen(MainGame game) {
		super(game);
	}
	
	public String showCoins() {
		return "Coins Collected: " + SinglePlayScreen.player.getCoins();
	}

	@Override
	public void resetSession() {
		//do nothing
		}


}
