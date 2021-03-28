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
import uk.ac.aston.teamproj.game.sprites.Rooster;
import uk.ac.aston.teamproj.game.tools.B2WorldCreator;
import uk.ac.aston.teamproj.game.tools.Map;
import uk.ac.aston.teamproj.game.tools.MultiMapManager;
import uk.ac.aston.teamproj.game.tools.MultiWorldContactListener;
import uk.ac.aston.teamproj.game.tools.SingleMapManager;
import uk.ac.aston.teamproj.game.tools.SoundManager;
import uk.ac.aston.teamproj.superclass.PlayScreen;

public class MultiPlayScreen extends PlayScreen {

	// Sprites
	public static Rooster player;
	private PlayerProgressBar progressBar;
	private PlayersTab tab;
	private boolean isTabOn = false; 
	
	public MultiPlayScreen(MainGame game) {
		super(game);
		
	}

	public void handleInput(float dt) {
		// If our user is holding down mouse over camera throughout the game world.
		if (player.currentState != Rooster.State.DEAD 
				&& player.currentState != Rooster.State.FROZEN
				&& player.currentState != Rooster.State.REVIVING) {			
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && jumpCount < MAX_JUMPS) {
				 //plays button swoosh sound
            	SoundManager.playSound(SoundManager.SWOOSH);
                player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 3f);
				jumpCount++;
			}

			
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.b2body.setLinearVelocity(currentSpeed, player.b2body.getLinearVelocity().y);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.b2body.setLinearVelocity(-currentSpeed, player.b2body.getLinearVelocity().y);
			}
			
			if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
	        	isTabOn = !isTabOn;
	        }
		}

	}


	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

//	@Override
//	public void dispose() {
//		map.dispose();
//		renderer.dispose();
//		world.dispose();
//		b2dr.dispose();

//	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	// TEMP
	protected boolean gameOver() {
		return player.currentState == Rooster.State.DEAD && player.getStateTimer() > 3;
	}
	
	public static void resetSession() {
//		sessionID = null;	// i.e. token
//		players = null;
//		mapPath = null;
		LobbyScreen.isGameAboutToStart = false;
	}
	
	private void terminateSession() {
		TerminateSession packet = new TerminateSession();
		packet.setId(MPClient.clientID);
		packet.setToken(MultiPlayScreen.sessionID);
		MPClient.client.sendTCP(packet);
	}
	
	protected boolean gameFinished() {
		return (player.currentState == Rooster.State.WON);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disposeHUD() {
		progressBar.dispose();
		tab.dispose();
		
	}

	@Override
	public void setScreenConditions() {
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
	public void drawProgressbar() {
		if (!isTabOn)
			progressBar.draw();
		else
			tab.draw();
	}

	@Override
	public void drawPlayer() {
		player.draw(game.batch); // draw
		
	}

	@Override
	public void sendLocationToServer() {
		long currentTime = System.currentTimeMillis();
		if (currentTime-prevUpdateTime >= 100) {
			prevUpdateTime = currentTime;
			PlayerInfo packet = new PlayerInfo();
			packet.setPlayerID(myID);
			packet.setToken(sessionID);
			packet.setPosX(player.getPositionX());
			packet.setLives(player.getLives());
			packet.setCoins(player.getCoins());
			MPClient.client.sendTCP(packet);
		}
		
	}

	@Override
	public void trackPlayerCam() {
		// Everytime chicken moves we want to track him with our game cam
		if (player.currentState != Rooster.State.DEAD) {
			if (player.getPositionX() < 1200 / MainGame.PPM) {
				gamecam.position.x = 1200 / MainGame.PPM;
			} else if (player.getPositionX() > camPos / MainGame.PPM) {
				gamecam.position.x = camPos / MainGame.PPM;
			} else {
				gamecam.position.x = player.getPositionX();
			}
		}

		
	}

	@Override
	public void updatePlayerPosition(float dt) {
		player.update(dt);
		progressBar.update();
		tab.update();
		
	}

	@Override
	public void initialiseCollisions() {
		// make the world react of object collision
		world.setContactListener(new MultiWorldContactListener(this));

		
	}

	@Override
	public void initialiseRooster() {
		// Create rooster in the world
		player = new Rooster(world, this);
		
	}

	@Override
	public void initialiseHUD() {
		// Create progress bar and tab
		progressBar = new PlayerProgressBar(game.batch, levelMap.getLength());
		tab = new PlayersTab(game.batch, levelMap.getLength());
		
	}

	@Override
	public void initialisePlayer() {
		//do nothing
		
	}

	@Override
	public void getMap() {
		 this.levelMap = MultiMapManager.getMapByPath(mapPath);
		
	}


}
