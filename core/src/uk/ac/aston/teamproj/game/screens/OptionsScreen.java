package uk.ac.aston.teamproj.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.tools.SoundManager;

/**
 * @author Derrick
 * @since 01.03.2021
 * @date 01/03/2021
 */

public class OptionsScreen implements Screen {

	private LabelStyle lbl_style;

	public static String ip = "Localhost", name = "Player 1"; // change with user input
	private Skin txt_skin;
	private TextButtonStyle btn_style;
	private MainGame game;
	private Viewport viewport;
	private Stage stage;
	private TextureAtlas buttonsAtlas; // the sprite-sheet containing all buttons
	private Skin skin; // skin for buttons
	private TextureAtlas buttonsAtlas1; // the sprite-sheet containing all buttons
	private Skin skin2; // skin for buttons
	private TextureAtlas buttonsAtlas2; // the sprite-sheet containing all buttons
	private Skin skin1; // skin for buttons
	private ImageButton[] buttons;

	public OptionsScreen(MainGame game) {
		this.game = game;
		viewport = new FitViewport(MainGame.V_WIDTH / 6, MainGame.V_HEIGHT / 6, new OrthographicCamera());
		stage = new Stage(viewport, ((MainGame) game).batch);

		//
		lbl_style = new Label.LabelStyle();
		lbl_style.font = new BitmapFont();

		txt_skin = new Skin(Gdx.files.internal("uiskin.json"));

		btn_style = new TextButton.TextButtonStyle();
		btn_style.font = new BitmapFont();

		buttonsAtlas = new TextureAtlas("buttons/MyButtons.pack");
		skin = new Skin(buttonsAtlas);
		buttonsAtlas2 = new TextureAtlas("buttons/new_buttons.pack");
		skin2 = new Skin(buttonsAtlas2);

		buttonsAtlas1 = new TextureAtlas("buttons/OptionsButtons.pack");
		skin1 = new Skin(buttonsAtlas1);
		buttons = new ImageButton[3];

		initializeButtons();
		populateTable();
	}

	private void initializeButtons() {
		ImageButtonStyle style;

		// audio_on button
		style = new ImageButtonStyle();
		style.up = skin1.getDrawable("audio_on_inactive"); // set default image
		style.over = skin1.getDrawable("audio_on_active"); // set image for mouse over

		ImageButton soundBtn = new ImageButton(style);
		soundBtn.addListener(new InputListener() {
			@Override

			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				SoundManager.audioOn();

            	SoundManager.playSound(SoundManager.POP);

				System.out.println("Sound");
				return true;

			}
		});

		// audio_off button
		style = new ImageButtonStyle();
		style.up = skin1.getDrawable("audio_off_inactive"); // set default image
		style.over = skin1.getDrawable("audio_off_active"); // set image for mouse over

		ImageButton soundOffBtn = new ImageButton(style);
		soundOffBtn.addListener(new InputListener() {
			@Override

			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            	SoundManager.playSound(SoundManager.POP);
				SoundManager.audioOff();

				System.out.println("Sound");

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

            	SoundManager.playSound(SoundManager.POP);
				System.out.println("Back");
				OptionsScreen.this.dispose();
				game.setScreen(new MainMenuScreen(game));
				return true;
			}
		});

		buttons[0] = soundBtn;
		buttons[2] = backBtn;
		buttons[1] = soundOffBtn;

	}

	private void populateTable() {
		Table table = new Table();
		table.top();
		table.setFillParent(true);

		// draw the background
		Texture background = new Texture("buttons/main_menu_bg.jpg");
		table.background(new TextureRegionDrawable(new TextureRegion(background)));

		// initialise Label

		// table.add(singleBtn).height(17.5f).width(100).pad(4).padLeft(200).padTop(50);
		// draw all buttons
		ImageButton singleBtn = buttons[0];
		table.add(singleBtn).height(22f).width(120).pad(4).padLeft(200).padTop(60);
		table.row();
		for (int i = 1; i < buttons.length; i++) {
			ImageButton button = buttons[i];
			table.add(button).height(22f).width(120).pad(4).padLeft(200);
			table.row();
		}

		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
		stage.act(delta);
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
		buttonsAtlas.dispose();
		skin.dispose();
		txt_skin.dispose();
		stage.dispose();
	}
}
