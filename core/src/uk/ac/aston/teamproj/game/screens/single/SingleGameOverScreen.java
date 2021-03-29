package uk.ac.aston.teamproj.game.screens.single;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.screens.GameOverScreen;

public class SingleGameOverScreen extends GameOverScreen {
	
	public SingleGameOverScreen(MainGame game) {
		super(game);
	}
	
	public String showCoins() {
		return "Coins Collected: " + SinglePlayScreen.getPlayer().getCoins();
	}

	@Override
	public void resetSession() {
		//do nothing
		}


}
