package uk.ac.aston.teamproj.game.scenes;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.screens.PlayScreen;

/**
 * 
 * Edited by Parmo on 5.11.20 New methods: update()
 *
 */

public class Hud implements Disposable {

	/*
	 * When our game world moves we want our hud to stay the same. We're going to
	 * use a new camera and a new viewport specifically for the hud. (It stays
	 * locked there and only renders that part of the screen).
	 */
	public Stage stage;
	private Viewport viewport;
	
	private Label nameFixedLabel;
	
	//Coin image
	Texture coinTexture;
	Image coinsImage;
	
	Texture heartTexture;
	Image heartImage;

	private Integer score;
	private Label scoreFixedLabel;
	private Label scoreLabel;

	private Integer coins;
//	private Label coinsFixedLabel;
	private Label coinsLabel;
	
	private Label playerFixedLabel;
	
	private Table heartContainer;
	private Table coinContainer;
	 

	private Integer lives;
//	private Label livesFixedLabel;
	private Label livesLabel;
	
	public Hud(SpriteBatch sb) {
		
		
		coinTexture = new Texture("new_graphics/coin.png");
		coinsImage = new Image(coinTexture);
		coinsImage.setSize(24, 24);
		
		heartTexture = new Texture("new_graphics/heart-image.png");
		heartImage = new Image(heartTexture);
		heartImage.setSize(24, 24);
		
		//Created new container to hold the coins image
		coinContainer = new Table();
		coinContainer.addActor(coinsImage);
		
		heartContainer = new Table();
//		checkHearts();
		heartContainer.addActor(heartImage);

		coins = 0;
		score = 0;
		lives = 3;

		viewport = new FitViewport(MainGame.V_WIDTH / 3, MainGame.V_HEIGHT / 3, new OrthographicCamera());
		/*
		 * reuse the sb.
		 */
		stage = new Stage(viewport, sb);

		Table table = new Table();
		table.top(); // put it at the top of our stage
		table.setFillParent(true); // table is the same of our stage

		scoreLabel = new Label(String.format("\t%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		coinsLabel = new Label(String.format("\t%06d", coins), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		livesLabel = new Label(String.format("\t%01d", lives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		playerFixedLabel = new Label("\tPLAYERS: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		nameFixedLabel = new Label("\tPlayer 1: ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreFixedLabel = new Label("\tSCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//		coinsFixedLabel = new Label("COINS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//		livesFixedLabel = new Label("\tLIVES", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		table.add(playerFixedLabel).expandX().padTop(10);
		table.add(scoreFixedLabel).expandX().padTop(10);
		//table.add(coinsFixedLabel).expandX().padTop(10);
		//table.add(coinContainer).width(40).height(40).pad(10);
		table.add(coinsImage).height(24).width(24).expandX();
//		table.add(livesFixedLabel).expandX().padTop(10);
		table.add(heartImage).height(24).width(24).expandX();
		
		table.row(); // Everything beyond this will be in a new row
		
		table.add(nameFixedLabel).expandX().padLeft(6);
		table.add(scoreLabel).expandX().padLeft(4);
		table.add(coinsLabel).expandX().padLeft(10);
//		table.add(heartContainer).width(40).height(10).pad(10);
		table.add(livesLabel).expandX();

		// Finally we need to add table to our stage
		stage.addActor(table);
	}

	public void updateScore() {
		score++;
		scoreLabel.setText(String.format("%06d", score));
		;
	}

	public void updateCoins(int value) {
		PlayScreen.player.updateCoins(value);
		coins = PlayScreen.player.getCoins();
		coinsLabel.setText(String.format("%06d", coins));
	}

	public void updateLives() {
		if (PlayScreen.player.isDead()) {
			lives = 0;
		} else {
			lives = PlayScreen.player.getLives();
		}
			livesLabel.setText(String.format("%01d", lives));
//			checkHearts();
		}
	
	//Adding Three hearts
//	public void checkHearts() {
//		
//		for(int i = 0; i < images.size(); i++) {
//			Image h = images.get(i);
//			heartContainer.addActor(h);
//		}
//	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	public int getScore() {
		return score;
	}

	public int getCoins() {
		return coins;
	}

}
