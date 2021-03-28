package uk.ac.aston.teamproj.singleplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
import uk.ac.aston.teamproj.game.net.Player;
import uk.ac.aston.teamproj.game.net.packet.PlayerInfo;
import uk.ac.aston.teamproj.game.net.packet.SessionInfo;
import uk.ac.aston.teamproj.game.net.packet.TerminateSession;
import uk.ac.aston.teamproj.singleplayer.SingleProgressBar;
import uk.ac.aston.teamproj.game.screens.MultiGameFinishedScreen;
import uk.ac.aston.teamproj.game.screens.LobbyScreen;
import uk.ac.aston.teamproj.game.sprites.Bomb;
import uk.ac.aston.teamproj.game.sprites.Rooster;
import uk.ac.aston.teamproj.singleplayer.SingleRooster;
import uk.ac.aston.teamproj.superclass.PlayScreen;
import uk.ac.aston.teamproj.game.tools.B2WorldCreator;
import uk.ac.aston.teamproj.game.tools.Map;
import uk.ac.aston.teamproj.game.tools.SingleMapManager;
import uk.ac.aston.teamproj.game.tools.SoundManager;


public class SinglePlayScreen extends PlayScreen {
	
	// Sprites
	public static SingleRooster player;
	Player p;
	private SingleProgressBar progressBar;

	public SinglePlayScreen(MainGame game) {
	super(game);
	}

	public void handleInput(float dt) {
		// If our user is holding down mouse over camera throughout the game world.
		if (player.currentState != SingleRooster.State.DEAD && player.currentState != SingleRooster.State.REVIVING) {
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
		}

	}

	// TEMP
	protected boolean gameOver() {
		return player.currentState == SingleRooster.State.DEAD && player.getStateTimer() > 3;
	}
	
	protected boolean gameFinished() {
		return (player.currentState == SingleRooster.State.WON);
	}


	@Override
	public void disposeHUD() {
		progressBar.dispose();
		
	}

	@Override
	public void setScreenConditions() {
		if (gameOver()) {
			game.setScreen(new SingleGameOverScreen(game));
			dispose();
		} else if (gameFinished()) {
			game.setScreen(new SingleGameFinishedScreen(game));
			dispose();
		}
		
	}

	@Override
	public void drawProgressbar() {
		progressBar.draw();	
		
	}

	@Override
	public void drawPlayer() {
		player.draw(game.batch); // draw
		
	}

	@Override
	public void sendLocationToServer() {
		// send position to server
		long currentTime = System.currentTimeMillis();
		if (currentTime-prevUpdateTime >= 100) {
			prevUpdateTime = currentTime;
		}
	}

	@Override
	public void trackPlayerCam() {
		// Everytime chicken moves we want to track him with our game cam
				if (player.currentState != SingleRooster.State.DEAD) {
					if(player.getPositionX() < 1200 / MainGame.PPM) {
						gamecam.position.x = 1200 / MainGame.PPM;
					}else if (player.getPositionX() > (464)){
						gamecam.position.x = 464;
					}else if (player.getPositionX() > camPos / MainGame.PPM) {
						gamecam.position.x = camPos / MainGame.PPM;
					}
					else {
						gamecam.position.x = player.getPositionX();
					}
				}
		
	}

	@Override
	public void updatePlayerPosition(float dt) {
		player.update(dt);
		progressBar.update();
		
	}

	@Override
	public void initialiseCollisions() {
		// make the world react of object collision
		world.setContactListener(new SingleWorldContactListener(this));
		
	}

	@Override
	public void initialiseRooster() {
		// Create rooster in the world 
		player = new SingleRooster(world, this);
		
	}

	@Override
	public void initialiseHUD() {
		// Create progress bar and tab
		progressBar = new SingleProgressBar(game.batch, levelMap.getLength());
		
	}

	@Override
	public void initialisePlayer() {
		players = new ArrayList<Player>();
		p = new Player(0, "");
		players.add(p);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getMap() {
		this.levelMap = SingleMapManager.getMapByPath(mapPath);
		
	}

}
