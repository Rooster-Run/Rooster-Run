package uk.ac.aston.teamproj.game.screens;

import java.util.ArrayList;

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
import uk.ac.aston.teamproj.game.net.MPClient;
import uk.ac.aston.teamproj.game.net.Player;
import uk.ac.aston.teamproj.game.net.packet.StartGame;

/**
 * @author Suleman
 * @since 08.03.2021
 * @date 08/03/2021
 */

public class LobbyScreen implements Screen {

	private MainGame game;
	private Viewport viewport;
	private Stage stage;

	private TextureAtlas buttonsAtlas; // the sprite-sheet containing all buttons
	private Skin skin; // skin for buttons
	private ImageButton[] buttons;
	
	public static boolean isGameAboutToStart = false;

	public static ArrayList<Player> names = new ArrayList<Player>();
	
	public LobbyScreen(MainGame game) {
		this.game = game;
		viewport = new FitViewport(MainGame.V_WIDTH / 6, MainGame.V_HEIGHT / 6, new OrthographicCamera());
		stage = new Stage(viewport, ((MainGame) game).batch);

		buttonsAtlas = new TextureAtlas("buttons/buttons.pack");
		skin = new Skin(buttonsAtlas);
		buttons = new ImageButton[1];
		
		
		
		


	}
	
	private String getToken() {
		return MPClient.token;
	}
	
	private int getTotal() {
		return MPClient.totalPlayers;
	}
	
	private String numPlayers() {
		return String.valueOf(getTotal());
	}
	
	private String getPlayers() {
		String name = null;
		for(int i = 0; i < names.size(); i++) {
		name = names.get(i).getName();
		}
		return name;
	}
	
	

	private void initializeButtons() {
		ImageButtonStyle style;

		// play Button
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("play_inactive"); // set default image
		style.over = skin.getDrawable("play_active"); // set image for mouse over

		ImageButton playbtn = new ImageButton(style);
		playbtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// do something
				// plays button sounds
				Sound sound = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
				sound.play(1F);
				System.out.println("START GAME");
				StartGame packet = new StartGame();
				packet.token = PlayScreen.sessionID;
				MPClient.client.sendTCP(packet);
				return true;
			}
			});
		
		buttons[0] = playbtn;

	}

	private void populateTable() {
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
        ;

		//Display text for token, name, num players 
		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		Label showToken = new Label (getToken(), font);
		table.add(showToken);
		table.row().expandY();
//		Label showName0 = new Label (names.get(0), font);
//		table.add(showName0);
//		table.row().expandY();
//		Label showName1 = new Label (names.get(1), font);
//		table.add(showName1);
//		table.row();
//		Label showName2 = new Label (names.get(2), font);
//		table.add(showName2);
//		table.row();
//		Label showName3 = new Label (names.get(3), font);
//		table.add(showName3);
//		table.row();
		Label showNumplayers = new Label (numPlayers(), font);
		table.add(showNumplayers);
		table.row().expandY();
		
		
		
		// draw the background
		Texture background = new Texture("buttons/lobbyBck.png");
		table.background(new TextureRegionDrawable(new TextureRegion(background)));

		// draw all buttons
		ImageButton singleBtn = buttons[0];
		table.add(singleBtn).height(22f).width(100).pad(4).padLeft(265).padTop(125);
		table.row().expandY();
		for (int i = 1; i < buttons.length; i++) {
			ImageButton button = buttons[i];
			table.add(button).height(22f).width(120).pad(4).padLeft(200);
			table.row().expandY();
		}

		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		
		//ArrayList
		System.out.println(getPlayers());
		
		
		//Displaying content on screen
		initializeButtons();
		populateTable();
		
		
		if (!isGameAboutToStart) {
			Gdx.gl.glClearColor(0f, 0f, 0f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
			stage.draw();
			stage.act(delta);
		} else {
			dispose();
			isGameAboutToStart = false;	// reset for next time
			game.setScreen(new LoadingScreen(game));
		}
		
		
		
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
		buttonsAtlas.dispose();
	}

}