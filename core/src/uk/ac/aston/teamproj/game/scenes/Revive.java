//package uk.ac.aston.teamproj.game.scenes;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.scenes.scene2d.Group;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.InputListener;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
//import com.badlogic.gdx.utils.Disposable;
//import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.badlogic.gdx.utils.viewport.Viewport;
//import uk.ac.aston.teamproj.game.MainGame;
//import uk.ac.aston.teamproj.game.screens.PlayScreen;
//import uk.ac.aston.teamproj.game.tools.SoundManager;
//
//public class Revive implements Disposable {
//	
//	private Stage stage;
//	private Viewport viewport;
//	
//	private ImageButton reviveBtn;
//	
//	
//	public Revive(SpriteBatch sb) {
//		viewport = new FitViewport(MainGame.V_WIDTH / 3, MainGame.V_HEIGHT / 3, new OrthographicCamera());
//		stage = new Stage(viewport, sb);
//		
//		ImageButtonStyle style;
//
//		TextureAtlas buttonsAtlas1 = new TextureAtlas("buttons/OptionsButtons.pack");
//		Skin skin1 = new Skin(buttonsAtlas1);		style = new ImageButtonStyle();
//		style.up = skin1.getDrawable("audio_on_inactive"); // set default image
//		style.over = skin1.getDrawable("audio_on_active"); // set image for mouse over
//
//		reviveBtn = new ImageButton(style);
//		reviveBtn.addListener(new InputListener() {
//			@Override
//			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//				System.out.println("Sound");
//				return true;
//			}
//		});
//		
//		Table table = new Table();
//		table.top();
//		table.setFillParent(true);
//		table.add(reviveBtn);
//		stage.addActor(table);
//	}
//
//	public void draw() {	
//		stage.draw();
//		stage.act();
//	}
//	
//	@Override
//	public void dispose() {
//		stage.dispose();
//	}
//	
//
//}