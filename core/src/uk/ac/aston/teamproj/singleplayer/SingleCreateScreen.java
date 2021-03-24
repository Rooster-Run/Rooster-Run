package uk.ac.aston.teamproj.singleplayer;

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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.scenes.SoundManager;
import uk.ac.aston.teamproj.game.screens.MainMenuScreen;

public class SingleCreateScreen implements Screen {
		
	private Label lbl_ip, lbl_name;
	private LabelStyle lbl_style;
	private TextField txt_ip, txt_name;
	public static String ip = MainGame.IP, name = "Player 1"; // change with user input
//	public static String ip = MainGame.IP, name = "Player 1"; //UNCOMMENT WHEN SERVER IS LIVE
	private Skin txt_skin;
	private TextButtonStyle btn_style;
	private MainGame game;
	private Viewport viewport;
	private Stage stage;
	private TextureAtlas buttonsAtlas; //the sprite-sheet containing all buttons
	private Skin skin; //skin for buttons
	private ImageButton[] optionButtons;
	
	//level picking tools
	private ImageButton leftBtn;
	private ImageButton rightBtn;
	
	//font
	private BitmapFont font;
	
	private final static int NUM_MAPS = 2;
	private String[] mapsPaths = new String[NUM_MAPS];
	private Texture[] mapsImages = new Texture[NUM_MAPS];
	private Image mapPreview = new Image();
	private int mapIdx = 0;

	public SingleCreateScreen(MainGame game) {
		this.game = game;
		viewport = new FitViewport(MainGame.V_WIDTH/6, MainGame.V_HEIGHT/6, new OrthographicCamera());
		stage = new Stage(viewport, ((MainGame) game).batch);
		
		
		//font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/RetroGaming.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 20;
		parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:";
		//e.g. abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?: 
		// These characters should not repeat! 
		font = generator.generateFont(parameter);
		font.setColor(Color.WHITE);
		generator.dispose();
		
		
		
		lbl_style = new Label.LabelStyle();
		lbl_style.font = font;
		
		txt_skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		btn_style = new TextButton.TextButtonStyle();
		btn_style.font = new BitmapFont();
		
		
		buttonsAtlas = new TextureAtlas("buttons/new_buttons.pack");
		skin = new Skin(buttonsAtlas);
		optionButtons = new ImageButton[3];
		
		initializeButtons();		
		populateTable();
		
		mapsImages[0] = new Texture("easymap.png");
		mapsImages[1] = new Texture("hardmap.png");
		mapsPaths[0] = "map_beginner_fix";
		mapsPaths[1] = "map_hard";
	}
	
	private void initializeButtons() {		
		ImageButtonStyle style;
		
		//Continue Button
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("start_inactive");  //set default image
		style.over = skin.getDrawable("start_active");  //set image for mouse over
		
		ImageButton startBtn = new ImageButton(style);
		startBtn.addListener(new InputListener() {
	            @Override	            
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

	            	Sound sound = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
	            	SoundManager.playSound(sound); 
	            	
	    			txt_name.setTextFieldListener(new TextField.TextFieldListener() {
	    				
	    				@Override
	    				public void keyTyped(TextField textField, char c) {
	    					Sound sound2 = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
	    	                sound2.play(1F);
	    					name = textField.getText();
	    				}
	    			});
	    			
	    			// pass in map data
	    		
	    			SinglePlayerScreen.mapPath = mapsPaths[mapIdx];
	    			game.setScreen(new SinglePlayerScreen(game));
	            	return true;
		
		}});
		
		
		//Go Back Button
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("back_inactive");  //set default image
		style.over = skin.getDrawable("back_active");  //set image for mouse over
		
		ImageButton backBtn = new ImageButton(style);
		backBtn.addListener(new InputListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

	            	Sound sound = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
	            	SoundManager.playSound(sound);
	            	System.out.println("Back");
	            	SingleCreateScreen.this.dispose();
	            	game.setScreen(new MainMenuScreen(game));
	            	return true;
	            }	       
		});
		
		optionButtons[0] = startBtn;
		optionButtons[1] = backBtn;
		
		// Left Button
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("left_inactive");  //set default image
		style.over = skin.getDrawable("left_active");  //set image for mouse over
		
		leftBtn = new ImageButton(style);
		leftBtn.addListener(new InputListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Sound sound = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
	            	SoundManager.playSound(sound);
	            	if (mapIdx > 0) {
	            		mapIdx --;
	            	} else {
	            		mapIdx = NUM_MAPS - 1;
	            	}
	            	return true;
	            }					       
		});
		
		// Right Button
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("right_inactive");  //set default image
		style.over = skin.getDrawable("right_active");  //set image for mouse over
		
		rightBtn = new ImageButton(style);
		rightBtn.addListener(new InputListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	Sound sound = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
	            	SoundManager.playSound(sound);
	            	mapIdx = (mapIdx + 1) % NUM_MAPS;
	            	return true;
	            }					       
		});
	}
	
	private void populateTable() {
		Table table = new Table();		
		table.center();
		table.setFillParent(true);
		
		//draw the background
		Texture background = new Texture("buttons/multiplayer_menu_bg.jpg");
		table.background(new TextureRegionDrawable(new TextureRegion(background)));
		
		//initialise Label
		lbl_name = new Label("Name: " , lbl_style);
		
		//initialise TextField
		txt_name = new TextField(name, txt_skin);
		
		
		//add contents to table
		table.row();
		table.add(lbl_name).right().expandX().padBottom(50);
		table.add(txt_name).width(200).pad(4).padBottom(50);
		table.row();
		
		
		
		// ************** OPTIONS SUB-TABLE*******************
		Table optionsTable = new Table();
		
		// startBtn
		ImageButton singleBtn = optionButtons[0];
		optionsTable.add(singleBtn).height(22f).width(120).pad(4);
		optionsTable.row();
		
		// backBtn
		ImageButton backBtn = optionButtons[1];
		optionsTable.add(backBtn).height(22f).width(120).pad(4);
		optionsTable.row();		
		
		// ************** LEVELS SUB-TABLE*******************		
		Table levelsTable = new Table();
		
		levelsTable.add(leftBtn).height(22f).width(24.8f).pad(4).padLeft(15f);
		levelsTable.add(mapPreview).height(80).width(120).pad(4);
		levelsTable.add(rightBtn).height(22f).width(24.8f).pad(4);
		
		// Add both tables
		table.add(levelsTable).padBottom(20);
		table.add(optionsTable).padBottom(20);
		table.row();
		
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
    public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,  0,  0 , 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		stage.act(delta);
		
		mapPreview.setDrawable(new TextureRegionDrawable(new TextureRegion(mapsImages[mapIdx])));
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
	
	private String getName() {
		return txt_name.getText();
	}
}
