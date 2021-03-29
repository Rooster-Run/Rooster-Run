package uk.ac.aston.teamproj.game.screens.single;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.scenes.SingleProgressBar;
import uk.ac.aston.teamproj.game.screens.PlayScreen;
import uk.ac.aston.teamproj.game.sprites.SingleRooster;
import uk.ac.aston.teamproj.game.tools.Map;
import uk.ac.aston.teamproj.game.tools.SingleMapManager;
import uk.ac.aston.teamproj.game.tools.SingleWorldContactListener;


public class SinglePlayScreen extends PlayScreen {
	
	private SingleProgressBar progressBar;

	public SinglePlayScreen(MainGame game) {
		super(game);
	}

	

	@Override
	protected void disposeHUD() {
		progressBar.dispose();		
	}

	@Override
	protected void setScreenConditions() {
		if (gameOver()) {
			game.setScreen(new SingleGameOverScreen(game));
			dispose();
		} else if (gameFinished()) {
			game.setScreen(new SingleGameFinishedScreen(game));
			dispose();
		}		
	}

	@Override
	protected void drawProgressbar() {
		progressBar.draw();			
	}


	@Override
	protected void updateHUD(float dt) {
		progressBar.update();		
	}

	@Override
	protected void initialiseContactListener() {
		// make the world react of object collision
		getWorld().setContactListener(new SingleWorldContactListener(this));		
	}

	@Override
	protected void initialiseRooster() {
		player = new SingleRooster(getWorld(), this);		
	}

	@Override
	protected void initialiseHUD(int mapLength) {
		// Create progress bar
		progressBar = new SingleProgressBar(game.batch, mapLength);		
	}

	@Override
	protected Map getMap() {
		return SingleMapManager.getMapByPath(mapPath);		
	}
	
	protected void sendLocationToServer() {}
}
