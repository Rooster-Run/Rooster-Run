package uk.ac.aston.teamproj.game.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.MPClient;
import uk.ac.aston.teamproj.game.net.Player;
import uk.ac.aston.teamproj.game.net.packet.PlayerInfo;
import uk.ac.aston.teamproj.game.net.packet.TerminateSession;
import uk.ac.aston.teamproj.game.net.packet.Winner;
import uk.ac.aston.teamproj.game.scenes.PlayerProgressBar;
import uk.ac.aston.teamproj.game.scenes.PlayersTab;
//import uk.ac.aston.teamproj.game.scenes.Revive;
import uk.ac.aston.teamproj.game.sprites.Bomb;
import uk.ac.aston.teamproj.game.sprites.MultiRooster;
import uk.ac.aston.teamproj.game.tools.B2WorldCreator;
import uk.ac.aston.teamproj.game.tools.Map;
import uk.ac.aston.teamproj.game.tools.MultiMapManager;
import uk.ac.aston.teamproj.game.tools.MultiWorldContactListener;
import uk.ac.aston.teamproj.game.tools.SingleMapManager;
import uk.ac.aston.teamproj.game.tools.SoundManager;
import uk.ac.aston.teamproj.superclass.PlayScreen;
import uk.ac.aston.teamproj.superclass.Rooster;

public class MultiPlayScreen extends PlayScreen {

	// Sprites
	private PlayerProgressBar progressBar;
	private PlayersTab tab;
	private boolean isTabOn = false; 
	
	public static int myID;
	public static String sessionID; // i.e. token
	public static ArrayList<Player> players;
	
	public MultiPlayScreen(MainGame game) {
		super(game);		
	}

	protected void handleInput(float dt) {
		super.handleInput(dt);	
		if (player.currentState != Rooster.State.DEAD 
				&& player.currentState != Rooster.State.FROZEN
				&& player.currentState != Rooster.State.REVIVING) {			
			if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
				isTabOn = !isTabOn;
			}
		}
	}
	
	protected static void resetSession() {
		LobbyScreen.isGameAboutToStart = false;
	}
	
	private void terminateSession() {
		TerminateSession packet = new TerminateSession();
		packet.setId(myID);
		packet.setToken(MultiPlayScreen.sessionID);
		MPClient.client.sendTCP(packet);
	}
	

	@Override
	public void disposeHUD() {
		progressBar.dispose();
		tab.dispose();		
	}

	@Override
	protected void setScreenConditions() {
		if (gameOver()) {
			game.setScreen(new MultiGameOverScreen(game));
			terminateSession();
			dispose();
		} else if (gameFinished()) {
			Winner packet = new Winner();
			packet.setToken(sessionID);
			packet.setPlayerID(myID);
			MPClient.client.sendTCP(packet);
			game.setScreen(new MultiGameFinishedScreen(game));
			terminateSession();
			dispose();
		}		
	}

	@Override
	protected void drawProgressbar() {
		if (!isTabOn)
			progressBar.draw();
		else
			tab.draw();
	}



	@Override
	protected void updateHUD(float dt) {
		progressBar.update();
		tab.update();		
	}

	@Override
	protected void initialiseContactListener() {
		// make the world react of object collision
		getWorld().setContactListener(new MultiWorldContactListener(this));

		
	}

	@Override
	protected void initialiseRooster() {
		// Create rooster in the world
		player = new MultiRooster(getWorld(), this);
		
	}

	@Override
	protected void initialiseHUD(int mapLength) {
		// Create progress bar and tab
		progressBar = new PlayerProgressBar(game.batch, mapLength);
		tab = new PlayersTab(game.batch, mapLength);		
	}

	@Override
	protected Map getMap() {
		 return MultiMapManager.getMapByPath(mapPath);		
	}
	
	@Override
	protected void sendLocationToServer() {
			PlayerInfo packet = new PlayerInfo();
			packet.setPlayerID(myID);
			packet.setToken(sessionID);
			packet.setPosX(player.getPositionX());
			packet.setLives(player.getLives());
			packet.setCoins(player.getCoins());
			MPClient.client.sendTCP(packet);
	}

}
