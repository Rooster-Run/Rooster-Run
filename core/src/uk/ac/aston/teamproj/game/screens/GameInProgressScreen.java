package uk.ac.aston.teamproj.game.screens;

import com.badlogic.gdx.graphics.Texture;
import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.superclass.ErrorScreen;

/**
 * @author Marcus, Junaid, Suleman  
 * @date 18/03/2021
 */

public class GameInProgressScreen extends ErrorScreen{

	public GameInProgressScreen(MainGame game) {
			super(game);
	}
	
	@Override
	protected void initialiseBackground() {
		background = new Texture("background_screens/GameInProgressScreen.png");
	}
}
