package uk.ac.aston.teamproj.superclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import uk.ac.aston.teamproj.game.sprites.Bomb;
import uk.ac.aston.teamproj.game.sprites.Rooster;
import uk.ac.aston.teamproj.game.tools.B2WorldCreator;
import uk.ac.aston.teamproj.game.tools.Map;
import uk.ac.aston.teamproj.game.tools.SingleMapManager;

public abstract class PlayScreen implements Screen {

	private static final String DEFAULT_MAP_PATH = "map_beginner_fix";

	protected MainGame game;
	protected TextureAtlas atlas; // sprite sheet that wraps all images
	Texture texture;

	// Aspect ratio
	protected OrthographicCamera gamecam;
	private Viewport gamePort;

	// Tiled map variables
	private TmxMapLoader mapLoader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	// Box2d variables
	protected World world;
	private Box2DDebugRenderer b2dr;

	// counts the number of consecutive jumps for each rooster
	protected static final int MAX_JUMPS = 2;
	protected int jumpCount = 0;

	// speed
	public static float currentSpeed = 1.0f;
	public static boolean startTimer;
	public static long buffDuration;

	private HashMap<Bomb, Float> toExplode = new HashMap<>();

	public static int myID;
	public static String sessionID; // i.e. token
	public static ArrayList<Player> players;
	public static String mapPath;

	public static String winner;

	protected int camPos;
	protected Map levelMap;
	
	public static long prevUpdateTime;

	public PlayScreen(MainGame game) {
		this.game = game;
		this.atlas = new TextureAtlas("new_sprite_sheet/new_chicken3.pack");
		getMap();
		

		// camera Position
		camPos = levelMap.getCamPosition();

		// for single player, leave blank in multiplayer
		initialisePlayer();

		gamecam = new OrthographicCamera();

		// Create a FitViewport to maintain virtual aspect ratio despite screen size
		gamePort = new FitViewport(MainGame.V_WIDTH / MainGame.PPM, MainGame.V_HEIGHT / MainGame.PPM, gamecam);

		// Create progress bar and tab
		initialiseHUD();

		// Load our map and setup our map renderer
		mapLoader = new TmxMapLoader();
		String correctMapPath = (mapPath != null) ? mapPath : DEFAULT_MAP_PATH;
		map = mapLoader.load(correctMapPath + ".tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / MainGame.PPM);
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		
		// Vector of gravity no grav rn, sleep objects at rest = true - box2d does not
		// calculate physics simulations on objects that are in rest.
		world = new World(new Vector2(0, -10), true);
		b2dr = new Box2DDebugRenderer();
		b2dr.setDrawBodies(false);
		new B2WorldCreator(world, map);

		initialiseRooster();

		initialiseCollisions();

		prevUpdateTime = System.currentTimeMillis();
	}

	public abstract void handleInput(float dt);

	
	/*
	 * This is where we are going to do all the updating in the game world. First
	 * thing we check if there is any inputs happening
	 */
	public void update(float dt) {

		// Handle user input first
		handleInput(dt);
		world.step(1 / 60f, 6, 2);
		
		updatePlayerPosition(dt);
		
		trackPlayerCam();
		
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
		
		//Bomb animation
		updateBombExplosionAnimation(dt);
		
		// send position to server
		sendLocationToServer();
		
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
	
	@Override
	public void render(float delta) {
		
		// separate our update logic from render
		update(delta);

		// clear the game screen with Black
		Gdx.gl.glClearColor(0, 0, 0, 0); // Colour and alpha
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Actually clears the screen
		
		// render our game map
		renderer.render();

		// renderer our Box2DDebugLines
		b2dr.render(world, gamecam.combined);

		// render rooster image
		game.batch.setProjectionMatrix(gamecam.combined); // render only what the game camera can see
		game.batch.begin();
		drawPlayer();
		game.batch.end();
		drawProgressbar();
		setScreenConditions();
		
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
	public void dispose() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();	
		disposeHUD();
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}
	
	protected abstract boolean gameOver();
	
	protected abstract boolean gameFinished();

	public void makeBombExplode(Bomb bomb) {
		float startTime = Gdx.graphics.getDeltaTime();
		toExplode.put(bomb, startTime);
	}
	
	public void resetJumpCount1() {
		jumpCount = 0;
	}
	
	public String getMapPath() {
		return mapPath;
	}
	
	public abstract void getMap();
	
	public abstract void disposeHUD();
	
	public abstract void setScreenConditions();
	
	public abstract void drawProgressbar();
	
	public abstract void drawPlayer();
	
	public abstract void sendLocationToServer();
	
	public abstract void trackPlayerCam();
	
	public abstract void updatePlayerPosition(float dt);

	public abstract void initialiseCollisions();

	public abstract void initialiseRooster();

	public abstract void initialiseHUD();

	public abstract void initialisePlayer();





}
