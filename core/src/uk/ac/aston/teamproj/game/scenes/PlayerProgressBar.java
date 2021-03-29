package uk.ac.aston.teamproj.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.screens.multi.MultiPlayScreen;


/**
 * The Class PlayerProgressBar.
 */
public class PlayerProgressBar implements Disposable {
	
	/** The Constant BAR_WIDTH. */
	private static final float BAR_WIDTH = 400;
	
	/** The Constant BAR_HEIGHT. */
	private static final float BAR_HEIGHT = 32;
	
	/** The Constant PLAYER_RADIUS. */
	private static final float PLAYER_RADIUS = 30;
	
	/** The stage. */
	private Stage stage;
	
	/** The viewport. */
	private Viewport viewport;
	
	/** The bar. */
	// progress bar
	private Image bar;
		
	/** The coin. */
	// coins
	private Image coin;
	
	/** The coins label. */
	private Label coinsLabel;
	
	/** The coins collected. */
	private int coinsCollected = 0;
	
	/** The hearts. */
	// lives
	private Image[] hearts = new Image[3];
	
	/** The relative positions. */
	// players
	private final float[] relativePositions;
	
	/** The player icons. */
	private final Image[] playerIcons;
	
	/** The player index. */
	private int playerIndex;
	
	/** The map size. */
	private float mapSize;
	
	/**
	 * Instantiates a new player progress bar.
	 *
	 * @param sb the sb
	 * @param mapLength the map length
	 */
	public PlayerProgressBar(SpriteBatch sb, int mapLength) {
		viewport = new FitViewport(MainGame.V_WIDTH / 3, MainGame.V_HEIGHT / 3, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		//map size
		mapSize = mapLength;
		
		// progress bar
		bar = new Image(new Texture("progress_bar/grey_bar.png"));		
		bar.setColor(1f, 1f, 1f, 0.5f);
		bar.setBounds(10, 370, BAR_WIDTH, BAR_HEIGHT);
		
		// coins
		coin = new Image(new Texture("progress_bar/coin.png"));
		coin.setColor(1f, 1f, 1f, 0.6f);
		coin.setBounds(500, 370, 32, 32);
		coinsLabel = new Label(String.format("\t%02d", coinsCollected), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		coinsLabel.setColor(1f,  1f,  1f,  0.6f);
		coinsLabel.setX(540);
		coinsLabel.setY(376);
		coinsLabel.setFontScale(1.8f);
		
		// lives
		Texture heartTexture = new Texture("progress_bar/heart.png");
		hearts[0] = new Image(heartTexture);
		hearts[1] = new Image(heartTexture);
		hearts[2] = new Image(heartTexture);
		
		for (int i = 0, x = 650; i < hearts.length; i++, x += 40) {
			hearts[i].setColor(1f, 1f, 1f, 0.6f);
			hearts[i].setBounds(x, 374, 32, 25);
		}
		
		// players
		this.relativePositions = new float[MultiPlayScreen.players.size()];
		this.playerIcons = new Image[MultiPlayScreen.players.size()];
		for (int i = 0; i < MultiPlayScreen.players.size(); i++) {
			playerIcons[i] = new Image(new Texture("progress_bar/player" + (i+1) + ".png"));
			if (MultiPlayScreen.players.get(i).getID() == MultiPlayScreen.myID) {
				playerIcons[i].setColor(1f, 1f, 1f, 1f);
				playerIndex = i;
			} else {
				playerIcons[i].setColor(1f, 1f, 1f, 0.5f);
			}
		}

	}

	/**
	 * Draw.
	 */
	public void draw() {		
		
		Group group = new Group();
		group.addActor(bar);
		for (int i = 0; i < MultiPlayScreen.players.size(); i ++) {
			playerIcons[i].setBounds(12 + relativePositions[i], 372f, PLAYER_RADIUS, PLAYER_RADIUS + 3);
			if (i != playerIndex)
				group.addActor(playerIcons[i]);
		}
		group.addActor(playerIcons[playerIndex]);
		
		for (Image life : hearts)
			group.addActor(life);
		group.addActor(coin);
		group.addActor(coinsLabel);
		
		stage.addActor(group);
		stage.draw();
		stage.act();
	}
	
	/**
	 * Dispose.
	 */
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	/**
	 * Update.
	 */
	public void update() {		
		for (int i = 0; i < MultiPlayScreen.players.size(); i++) {
			float actualPosition = (MultiPlayScreen.players.get(i).getPosX()* MainGame.PPM) / 100;
			float percentage = (actualPosition * 100) / mapSize;
			
			relativePositions[i] = (percentage * (BAR_WIDTH - PLAYER_RADIUS/2)) / 100;
		}
		
		coinsCollected = MultiPlayScreen.getPlayer().getCoins();
		coinsLabel.setText(String.format("%02d", coinsCollected));
		
		int lives = MultiPlayScreen.getPlayer().getLives();
		for (int i = lives; i < 3; i++) 
			hearts[i].setVisible(false);
	}
}