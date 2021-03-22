package uk.ac.aston.teamproj.game.screens;

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
import uk.ac.aston.teamproj.game.net.MPClient;
import uk.ac.aston.teamproj.game.net.Player;
import uk.ac.aston.teamproj.game.net.packet.PlayerInfo;
import uk.ac.aston.teamproj.game.net.packet.SessionInfo;
import uk.ac.aston.teamproj.game.net.packet.TerminateSession;
import uk.ac.aston.teamproj.game.scenes.SingleProgressBar;
import uk.ac.aston.teamproj.game.scenes.PlayersTab;
import uk.ac.aston.teamproj.game.sprites.Bomb;
import uk.ac.aston.teamproj.game.sprites.SingleRooster;
import uk.ac.aston.teamproj.game.tools.B2WorldCreator;
import uk.ac.aston.teamproj.game.tools.SingleWorldContactListener;

public class SinglePlayerScreen implements Screen {

	private static final String DEFAULT_MAP_PATH = "map_beginner_fix";

	private MainGame game;
	private TextureAtlas atlas; // sprite sheet that wraps all images
	Texture texture;

	// Aspect ratio
	private OrthographicCamera gamecam;
	private Viewport gamePort;

	// Tiled map variables
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	// Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr;

	// Sprites
	public static SingleRooster player;

	// counts the number of consecutive jumps for each SingleRooster
	private static final int MAX_JUMPS = 2;
	private int jumpCount = 0;
	
	// speed 
	public static float currentSpeed = 1.0f;
	public static boolean startTimer;
	public static long buffDuration;

	private HashMap<Bomb, Float> toExplode = new HashMap<>();
		
	public static int myID;
	public static String sessionID;	// i.e. token
	public static ArrayList<Player> players;
	public static String mapPath;
	Player p;
	
	public static long prevUpdateTime;
	
//	private final SingleProgressBar progressBar;
//	private final PlayersTab tab;
//	private boolean isTabOn = false; 
	
	public SinglePlayerScreen(MainGame game) {
//		System.out.println("Size is: " + players.size() + "!!");
		this.game = game;
		this.atlas = new TextureAtlas("new_sprite_sheet/new_chicken2.pack");
		
		//ArrayList
//		players = new ArrayList<Player>();
//		p = new Player(0, "");
//		players.add(p);

		// Create a cam to follow chicken in the game world
		gamecam = new OrthographicCamera();

		// Create a FitViewport to maintain virtual aspect ratio despite screen size
		gamePort = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gamecam);

		// Create progress bar and tab
//		progressBar = new SingleProgressBar(game.batch);
//		tab = new PlayersTab(game.batch);
		
		// Load our map and setup our map renderer
		mapLoader = new TmxMapLoader();
		String correctMapPath = (mapPath != null)? mapPath : DEFAULT_MAP_PATH;
		map = mapLoader.load(correctMapPath + ".tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);

		// Initially set our game cam to be centered correctly at the start of the map
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		// Vector of gravity no grav rn, sleep objects at rest = true - box2d does not
		// calculate physics simulations on objects that are in rest.
		world = new World(new Vector2(0, -10), true);
		b2dr = new Box2DDebugRenderer();
		b2dr.setDrawBodies(false);

		new B2WorldCreator(world, map);

		// Create SingleRooster in the world
		player = new SingleRooster(world, this);

		// make the world react of object collision

		world.setContactListener(new SingleWorldContactListener(this));

//		prevUpdateTime = System.currentTimeMillis();		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	public void handleInput(float dt) {
		// If our user is holding down mouse over camera throughout the game world.
		if (player.currentState != SingleRooster.State.DEAD) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && jumpCount < MAX_JUMPS) {
				 //plays button swoosh sound
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("electric-transition-super-quick-www.mp3"));
                sound.play(1F);
                player.b2body.setLinearVelocity(player.b2body.getLinearVelocity().x, 3f);
				jumpCount++;
			}

			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.b2body.setLinearVelocity(currentSpeed, player.b2body.getLinearVelocity().y);
			}

			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.b2body.setLinearVelocity(-currentSpeed, player.b2body.getLinearVelocity().y);
			}
			
//			if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
//	        	isTabOn = !isTabOn;
//	        }
		}

	}

	/*
	 * This is where we are going to do all the updating in the game world. First
	 * thing we check if there is any inputs happening
	 */
	public void update(float dt) {
		// Handle user input first
		handleInput(dt);

		world.step(1 / 60f, 6, 2);

		// update player based on delta time
		player.update(dt);
//		progressBar.update();
//		tab.update();


		// Everytime chicken moves we want to track him with our game cam
		if (player.currentState != SingleRooster.State.DEAD) {
			gamecam.position.x = player.getPositionX();
		}

		// Update our gamecam with correct coordinates after changes
		gamecam.update();

		// tell our renderer to draw only what the camera sees in our game world.
		float width = gamecam.viewportWidth * gamecam.zoom;
		float height = gamecam.viewportHeight * gamecam.zoom;
		float w = width * Math.abs(gamecam.up.y) + height * Math.abs(gamecam.up.x);
		float h = height * Math.abs(gamecam.up.y) + width * Math.abs(gamecam.up.x);
		float x = gamecam.position.x - w / 2;
		float y = gamecam.position.y - h / 2;
		renderer.setView(gamecam.combined, x, y, w, h); // Only render what our game can see
//      renderer.setView(gamecam);

		updateBombExplosionAnimation(dt);
		
		// send position to server
//		long currentTime = System.currentTimeMillis();
//		if (currentTime-prevUpdateTime >= 100) {
//			prevUpdateTime = currentTime;
//			PlayerInfo packet = new PlayerInfo();
//			packet.playerID = myID;
//			packet.token = sessionID;
//			packet.posX = player.getPositionX();
//			packet.lives = player.getLives();
//			packet.coins = player.getCoins();
//			MPClient.client.sendTCP(packet);
//		}
		
		if(startTimer) {
			// 10 seconds convert back to normal speed
			if(prevUpdateTime >= buffDuration) {
				currentSpeed = 1.0f;
				startTimer = false;
			}
		}
	}
	
	private void updateBombExplosionAnimation(float delta) {
		for (Iterator<HashMap.Entry<Bomb, Float>> iter = toExplode.entrySet().iterator();
				iter.hasNext();) {
			HashMap.Entry<Bomb, Float> entry = iter.next();
			Bomb bomb = entry.getKey();
			@SuppressWarnings("rawtypes")
			Animation a = bomb.getAnimation();
			float time = entry.getValue();

			if (time <= 1f) { // if the animation is still running
				time += delta;
				toExplode.put(bomb, time);
				if (time < 0.9f) {
					TextureRegion region = (TextureRegion) a.getKeyFrame(time);
					bomb.getCell().setTile(new StaticTiledMapTile(region));
				} else
					bomb.getCell().setTile(null); // last frame in animation should be empty

			} else { // else if the animation is finished
				iter.remove();
			}
		}
	}
	
//	private void terminateSession() {
//		TerminateSession packet = new TerminateSession();
//		packet.token = SinglePlayerScreen.sessionID;
//		MPClient.client.sendTCP(packet);
//	}

	@Override
	public void render(float delta) {
		// separate our update logic from render
		update(delta);

		// clear the game screen with Black
		Gdx.gl.glClearColor(0, 0, 0, 1); // Colour and alpha
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Actually clears the screen

		// render our game map
		renderer.render();

		// renderer our Box2DDebugLines
		b2dr.render(world, gamecam.combined);

		// render SingleRooster image
		game.batch.setProjectionMatrix(gamecam.combined); // render only what the game camera can see
		game.batch.begin();
		player.draw(game.batch); // draw
		game.batch.end();
		
//		if (!isTabOn)
//		progressBar.draw();
//		else
//			tab.draw();
		
		if (gameOver()) {
			game.setScreen(new GameOverScreen(game));
			dispose();
		} else if (gameFinished()) {
			game.setScreen(new GameFinishedScreen(game));
//			terminateSession();
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		/*
		 * Its important that when we change the size of our screen on the desktop that
		 * the view point gets adjusted to know what the actual screen size is
		 */
		gamePort.update(width, height);
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

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
//		progressBar.dispose();
//		tab.dispose();
	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	// TEMP
	private boolean gameOver() {
		if(player.currentState == SingleRooster.State.DEAD && player.getStateTimer() > 3) {
//			SessionInfo packet = new SessionInfo();
//			packet.gameOver = true;
//			MPClient.client.sendTCP(packet);
			return true;
		} else {
			return false;
		}
	}

	private boolean gameFinished() {
		return (player.currentState == SingleRooster.State.WON);
	}

	public void makeBombExplode(Bomb bomb) {
		float startTime = Gdx.graphics.getDeltaTime();
		toExplode.put(bomb, startTime);
	}

	public void resetJumpCount1() {
		jumpCount = 0;
	}

}
