package uk.ac.aston.teamproj.singleplayer;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.screens.MainMenuScreen;
import uk.ac.aston.teamproj.game.tools.SingleMapManager;
import uk.ac.aston.teamproj.superclass.CreateScreen;

public class SingleCreateScreen extends CreateScreen {

	public SingleCreateScreen(MainGame game) {
		super(game);
	}

	protected void createSession() {
		// pass in map data
		SinglePlayerScreen.mapPath = SingleMapManager.getMapByIndex(mapIdx).getPath();
		game.setScreen(new SinglePlayerScreen(game));
	}
	
	protected void backToMenu() {
		game.setScreen(new MainMenuScreen(game));
	}
	
	protected void decreaseMapIdx() {
		mapIdx = SingleMapManager.getTotalMaps() - 1;
	}
	
	protected void getMapIdx() {
		mapIdx = (mapIdx + 1) % SingleMapManager.getTotalMaps();
	}
	
	protected boolean isLocClientReady() {
		return false;
	}
	

}

