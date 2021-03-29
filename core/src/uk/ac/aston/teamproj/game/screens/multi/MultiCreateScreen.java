package uk.ac.aston.teamproj.game.screens.multi;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.MPClient;
import uk.ac.aston.teamproj.game.net.packet.CreateGameSession;
import uk.ac.aston.teamproj.game.screens.CreateScreen;
import uk.ac.aston.teamproj.game.tools.MultiMapManager;

public class MultiCreateScreen extends CreateScreen {

	private MPClient locClient;

	public MultiCreateScreen(MainGame game) {
		super(game);
	}

	protected void createSession() {
		locClient = new MPClient(MainGame.IP, txt_name.getText(), game);
		CreateGameSession packet = new CreateGameSession();
		packet.setMapPath(MultiMapManager.getMapByIndex(mapIdx).getPath()); // pass in map 
		packet.setName(txt_name.getText()); // pass in player name
		MPClient.client.sendTCP(packet);
	}
	
	protected void backToMenu() {
		game.setScreen(new MultiplayerMenuScreen(game));
	}
	
	protected void decreaseMapIdx() {
		mapIdx = MultiMapManager.getTotalMaps() - 1;
	}
	
	protected void getMapIdx() {
		mapIdx = (mapIdx + 1) % MultiMapManager.getTotalMaps();
	}
	
	protected boolean isLocClientReady() {
		if (locClient != null && locClient.isReady()) {
			game.setScreen(new LobbyScreen(game, locClient.isHost()));
			this.dispose();
			return true;
		}
		return false;
	}


}
