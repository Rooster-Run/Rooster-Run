package uk.ac.aston.teamproj.game.screens;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.scenes.Hud;

/**
 * @author Junaid, Marcus, Suleman
 * @since 17.03.2021
 * @date 17/03/2021
 */

public class ServerErrorScreen implements Screen {

	private Viewport viewport;
	private Stage stage;
	
	@SuppressWarnings("unused")
	private MainGame game;
	
	private ImageButton backBtn;
	
	public ServerErrorScreen(MainGame game) {

		this.game = game;
		viewport = new FitViewport(MainGame.V_WIDTH/6, MainGame.V_HEIGHT/6, new OrthographicCamera());
		stage = new Stage(viewport, ((MainGame) game).batch);
		
		initializeButton();
		populateTable();
		
		
	}
	
	private void initializeButton(){

		//Back Button
		TextureAtlas buttonsAtlas = new TextureAtlas("buttons/new_buttons.pack");
		Skin skin = new Skin(buttonsAtlas);
		ImageButtonStyle style = new ImageButtonStyle();
		
		style.up = skin.getDrawable("back_inactive");  //set default image
		style.over = skin.getDrawable("back_active");  //set image for mouse over
		
		backBtn = new ImageButton(style);
		backBtn.addListener(new InputListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	//Sets to playScreen

	            	 //plays button pop sound

	            	Sound sound = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
	                sound.play(1F);
	            	System.out.println("Back");
	            	ServerErrorScreen.this.dispose();
	            	game.setScreen(new MainMenuScreen(game));
	            	return true;
	            }	       
		});
	}
	
private void populateTable() {
		
		Table table = new Table();

		table.setFillParent(true);
		
		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		
		Label playAgainLabel = new Label ("Click back to return to the main menu!", font);
		
		table.row();
		table.add(playAgainLabel).expandX().padTop(80f);
		table.row();
		table.add(backBtn).height(22f).width(100).pad(4).padLeft(10).padTop(30);
		stage.addActor(table);	
		
		Texture background = new Texture("buttons/ServerScreen.png");
		table.background(new TextureRegionDrawable(new TextureRegion(background)));
		
		Gdx.input.setInputProcessor(stage);
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0,  0,  0 , 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
			
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
	}

}
