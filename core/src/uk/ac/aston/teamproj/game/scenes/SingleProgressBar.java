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
import uk.ac.aston.teamproj.game.screens.single.SinglePlayScreen;

// TODO: Auto-generated Javadoc
/**
 * The Class SingleProgressBar.
 */
public class SingleProgressBar implements Disposable {
	
	/** The map size. */
	private static float map_size;
	
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
	
	/** The relative position. */
	// players
	private float relativePosition;
	
	/** The player icon. */
	private Image playerIcon;
	
	/**
	 * Instantiates a new single progress bar.
	 *
	 * @param sb the sb
	 * @param mapLength the map length
	 */
	public SingleProgressBar(SpriteBatch sb, int mapLength) {
		viewport = new FitViewport(MainGame.V_WIDTH / 3, MainGame.V_HEIGHT / 3, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		//map size
		map_size = mapLength;
		System.out.println(SinglePlayScreen.mapPath);
		
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
		
		playerIcon = new Image(new Texture("progress_bar/player0.png"));
		playerIcon.setColor(1f, 1f, 1f, 1f);	
		
	}

	/**
	 * Draw.
	 */
	public void draw() {		
		
		Group group = new Group();
		group.addActor(bar);
		playerIcon.setBounds(12 + relativePosition, 372f, PLAYER_RADIUS, PLAYER_RADIUS + 3);		
		group.addActor(playerIcon);
		
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
		float actualPosition = (SinglePlayScreen.getPlayer().getPositionX()* MainGame.PPM) / 100;
		float percentage = (actualPosition * 100) / map_size;
		
		relativePosition = (percentage * (BAR_WIDTH - PLAYER_RADIUS/2)) / 100;
		
		coinsCollected = SinglePlayScreen.getPlayer().getCoins();
		coinsLabel.setText(String.format("%02d", coinsCollected));
		
		int lives = SinglePlayScreen.getPlayer().getLives();
		for (int i = lives; i < 3; i++) 
			hearts[i].setVisible(false);
	}
}