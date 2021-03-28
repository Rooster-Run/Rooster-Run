package uk.ac.aston.teamproj.superclass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.screens.MainMenuScreen;
import uk.ac.aston.teamproj.game.tools.SoundManager;

public abstract class GameFinishedScreen implements Screen {
	
	private Viewport viewport;
	private Stage stage;
	private Skin skin; // skin for buttons
	private TextureAtlas buttonsAtlasBack; // the sprite-sheet containing all buttons
	private ImageButton[] optionButtons;
	private MainGame game;
	
	protected BitmapFont font;	
	protected Table winnerLabelTable = new Table();
	protected Table winnerNameTable = new Table();
	
	public GameFinishedScreen(MainGame game) {
		this.game = game;

		// font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/RetroGaming.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 20;
		parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";

		font = generator.generateFont(parameter);
		font.setColor(Color.WHITE);
		generator.dispose();
		
		viewport = new FitViewport(MainGame.V_WIDTH / 6, MainGame.V_HEIGHT / 6, new OrthographicCamera());
		stage = new Stage(viewport, ((MainGame) game).batch);

		buttonsAtlasBack = new TextureAtlas("buttons/new_buttons.pack");
		skin = new Skin(buttonsAtlasBack);
		optionButtons = new ImageButton[1];

		initializeButtons();
		populateTable();
	}
	
	private void populateTable() {
		
		winnerLabelTable = new Table();
		winnerLabelTable.top();
		winnerLabelTable.setFillParent(true);
		
		winnerNameTable = new Table();
		winnerNameTable.top();
		winnerNameTable.setFillParent(true);
		
        addLabelWinnerIs();
        addLabelName();        
		
		
		// Set background textures
		Texture background = new Texture("background_screens/Sky.png");
		winnerLabelTable.background(new TextureRegionDrawable(new TextureRegion(background)));
		winnerLabelTable.row();
		
		Table buttonTable = new Table();
		buttonTable.top();
		buttonTable.setFillParent(true);
		
		// backBtn
		ImageButton backBtn = optionButtons[0];
		buttonTable.add(backBtn).height(22f).width(120).padTop(175).padRight(240);
		buttonTable.row();
		
		// Adding table to screen
		stage.addActor(winnerLabelTable);
		stage.addActor(winnerNameTable);
		stage.addActor(buttonTable);
		Gdx.input.setInputProcessor(stage);
	}
	
	private void initializeButtons() {
		ImageButtonStyle style;

		//Back Button
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("back_inactive"); // set default image
		style.over = skin.getDrawable("back_active"); // set image for mouse over
		ImageButton backBtn = new ImageButton(style);
		backBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				SoundManager.playSound(SoundManager.POP);
				dispose();
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
	
	@Override public void show() {}	
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void hide() {}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		buttonsAtlasBack.dispose();
		font.dispose();
	}
	
	protected abstract void addLabelWinnerIs();
	protected abstract void addLabelName();
}
