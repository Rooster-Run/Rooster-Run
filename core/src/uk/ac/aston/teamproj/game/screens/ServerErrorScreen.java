package uk.ac.aston.teamproj.game.screens;

import com.badlogic.gdx.graphics.Texture;
import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.superclass.ErrorScreen;
/**
 * @author Junaid, Marcus, Suleman
 * @since 17.03.2021
 * @date 17/03/2021
 */

public class ServerErrorScreen extends ErrorScreen {

	public ServerErrorScreen(MainGame game) {
		super(game);
	}
	
	@Override
	protected void initialiseBackground() {
		background = new Texture("background_screens/ServerScreen.png");
		
	}

}
