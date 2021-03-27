package uk.ac.aston.teamproj.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.tools.SoundManager;

/**
 * @author Suleman, Junaid and Marcus
 * @since 15.03.2021
 * @date 15/03/2021
 */

public class GameFinishedScreen implements Screen {

	private Viewport viewport;
	private Stage stage;
	private Skin skin; // skin for buttons
	private TextureAtlas buttonsAtlasBack; // the sprite-sheet containing all buttons
	private ImageButton[] optionButtons;
	@SuppressWarnings("unused")
	private MainGame game;

	// font
	private BitmapFont font;
	
	private String winnerName;

	public GameFinishedScreen(MainGame game) {
		
		// font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/RetroGaming.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 20;
		parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
		// e.g. abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:
		// These characters should not repeat!

		font = generator.generateFont(parameter);
		font.setColor(Color.WHITE);
		generator.dispose();
		
		this.game = game;
		viewport = new FitViewport(MainGame.V_WIDTH / 6, MainGame.V_HEIGHT / 6, new OrthographicCamera());
		stage = new Stage(viewport, ((MainGame) game).batch);

		buttonsAtlasBack = new TextureAtlas("buttons/new_buttons.pack");
		skin = new Skin(buttonsAtlasBack);
		optionButtons = new ImageButton[1];

		initializeButtons();
		populateTable();
		getWinner();
		System.out.println(getWinner());
		PlayScreen.resetSession();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	private void initializeButtons() {
		ImageButtonStyle style;

		// Go Back Button
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("back_inactive"); // set default image
		style.over = skin.getDrawable("back_active"); // set image for mouse over
		ImageButton backBtn = new ImageButton(style);
		backBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				SoundManager.playSound(SoundManager.POP);
				System.out.println("Back");
				GameFinishedScreen.this.dispose();
				game.setScreen(new MainMenuScreen(game));
				return true;
			}
		});

		optionButtons[0] = backBtn;

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
		stage.act(delta);	
	}

	public void populateTable() {
		
		Table LabelWinnerIsTable = new Table();
		LabelWinnerIsTable.top();
		LabelWinnerIsTable.setFillParent(true);
		
		Table winnerNameTable = new Table();
		winnerNameTable.top();
		winnerNameTable.setFillParent(true);
		
		//LabelName
        Label.LabelStyle labelName = new Label.LabelStyle(font, Color.WHITE);
        Label winnerLabel = new Label (getWinner(), labelName);
        winnerNameTable.add(winnerLabel).expandX().padTop(100).padLeft(150);
        
		//LabelWinner
        Label.LabelStyle label = new Label.LabelStyle(font, Color.WHITE);
        Label gameOverLabel = new Label ("Winner:   ", label);
        LabelWinnerIsTable.add(gameOverLabel).expandX().padLeft(180).padTop(50);
		
		// Set background textures
		Texture background = new Texture("buttons/Sky.png");
		LabelWinnerIsTable.background(new TextureRegionDrawable(new TextureRegion(background)));
		LabelWinnerIsTable.row();
		
		Table buttonTable = new Table();
		buttonTable.top();
		buttonTable.setFillParent(true);
		
		// backBtn
		ImageButton backBtn = optionButtons[0];
		buttonTable.add(backBtn).height(22f).width(120).padTop(175).padRight(240);
		buttonTable.row();
		
		// Adding table to screen
		stage.addActor(LabelWinnerIsTable);
		stage.addActor(winnerNameTable);
		stage.addActor(buttonTable);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
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
	
	public String getWinner() {
		return PlayScreen.winner;
	}
	
//	public String getWinner() {
//		for(int i = 0; i < PlayScreen.players.size(); i++ ) {
//			if(PlayScreen.players.get(i).getWonState()) {
//				winnerName = PlayScreen.players.get(i).getName();
//			}
//		}
//		return winnerName;
//	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		buttonsAtlasBack.dispose();
	}
	

}
