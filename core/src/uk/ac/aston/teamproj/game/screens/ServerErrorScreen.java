package uk.ac.aston.teamproj.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.scenes.SoundManager;
import uk.ac.aston.teamproj.singleplayer.SinglePlayerScreen;

/**
 * @author Junaid, Marcus, Suleman
 * @since 17.03.2021
 * @date 17/03/2021
 */

public class ServerErrorScreen implements Screen {

	private Viewport viewport;
	private Stage stage;
	private Skin skin; // skin for buttons
	private Skin skin2; // skin for buttons
	private TextureAtlas buttonsAtlasSingle; // the sprite-sheet containing all buttons
	private TextureAtlas buttonsAtlasBack; // the sprite-sheet containing all buttons
	private ImageButton[] optionButtons;
	@SuppressWarnings("unused")
	private MainGame game;

	public ServerErrorScreen(MainGame game) {

		this.game = game;
		viewport = new FitViewport(MainGame.V_WIDTH / 6, MainGame.V_HEIGHT / 6, new OrthographicCamera());
		stage = new Stage(viewport, ((MainGame) game).batch);

		buttonsAtlasSingle = new TextureAtlas("buttons/buttons.pack");
		skin = new Skin(buttonsAtlasSingle);
		buttonsAtlasBack = new TextureAtlas("buttons/new_buttons.pack");
		skin2 = new Skin(buttonsAtlasBack);
		optionButtons = new ImageButton[2];

		initializeButtons();
		populateTable();

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	private void initializeButtons() {
		ImageButtonStyle style;

		// SinglePlayer Button
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("single_player_inactive"); // set default image
		style.over = skin.getDrawable("single_player_active"); // set image for mouse over

		ImageButton singlePlayerBtn = new ImageButton(style);
		singlePlayerBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				Sound sound = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
				SoundManager.playSound(sound);
				System.out.println("SinglePlayer");
				ServerErrorScreen.this.dispose();
				game.setScreen(new SinglePlayerScreen(game));
				return true;
			}
		});

		// Go Back Button
		style = new ImageButtonStyle();
		style.up = skin2.getDrawable("back_inactive"); // set default image
		style.over = skin2.getDrawable("back_active"); // set image for mouse over
		ImageButton backBtn = new ImageButton(style);
		backBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

				Sound sound = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
				SoundManager.playSound(sound);
				System.out.println("Back");
				ServerErrorScreen.this.dispose();
				game.setScreen(new MainMenuScreen(game));
				return true;
			}
		});

		optionButtons[0] = singlePlayerBtn;
		optionButtons[1] = backBtn;

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
		stage.act(delta);
	}

	public void populateTable() {

		Table table = new Table();
		table.top();
		table.setFillParent(true);

		// Set background textures
		Texture background = new Texture("buttons/ServerScreen.png");
		table.background(new TextureRegionDrawable(new TextureRegion(background)));

		// singlePlayBtn
		ImageButton singlePlayerBtn = optionButtons[0];
		table.add(singlePlayerBtn).height(22f).width(120).pad(4).padTop(110);
		table.row();

		// backBtn
		ImageButton backBtn = optionButtons[1];
		table.add(backBtn).height(22f).width(120).pad(4);
		table.row();

		// Adding table to screen
		stage.addActor(table);
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

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		buttonsAtlasSingle.dispose();
		buttonsAtlasBack.dispose();
	}

}
