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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.tools.MultiMapManager;
import uk.ac.aston.teamproj.game.tools.SoundManager;

public abstract class CreateScreen implements Screen{

	protected Label lbl_name;
	protected LabelStyle lbl_style;
	protected TextField txt_name;
	protected String name = "Player 1"; // change with user input
	protected Skin txt_skin;
	protected TextButtonStyle btn_style;
	protected MainGame game;
	protected Viewport viewport;
	protected Stage stage;
	protected TextureAtlas buttonsAtlas; //the sprite-sheet containing all buttons
	protected Skin skin; //skin for buttons
	protected ImageButton[] optionButtons;

	//level picking tools
	protected ImageButton leftBtn;
	protected ImageButton rightBtn;

	//font
	protected BitmapFont font;
	protected Image mapPreview = new Image();
	protected int mapIdx = 0;

	public CreateScreen(MainGame game) {
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
	}

	
	protected abstract void createSession(); 
	
	protected abstract void backToMenu();
	
	protected abstract void decreaseMapIdx();
	
	protected abstract void getMapIdx();
	
	protected abstract boolean isLocClientReady();
	
	protected void initializeButtons() {		
		ImageButtonStyle style;
		
		//Continue Button
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("start_inactive");  //set default image
		style.over = skin.getDrawable("start_active");  //set image for mouse over
		
		ImageButton startBtn = new ImageButton(style);
		startBtn.addListener(new InputListener() {
	            @Override	            
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

	            	SoundManager.playSound(SoundManager.POP); 
	            	
	    			txt_name.setTextFieldListener(new TextField.TextFieldListener() {
	    				
	    				@Override
	    				public void keyTyped(TextField textField, char c) {
	    					SoundManager.playSound(SoundManager.POP);
	    					name = textField.getText();
	    				}
	    			});
	    			
	    			
	    			createSession();
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
	            	SoundManager.playSound(SoundManager.POP);
	            	System.out.println("Back");
	            	dispose();
	            	
	            	backToMenu();
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
	            	SoundManager.playSound(SoundManager.POP);
	            	if (mapIdx > 0) {
	            		mapIdx --;
	            	} else {
	            		decreaseMapIdx();
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
	            	SoundManager.playSound(SoundManager.POP);
	            	
	            	getMapIdx();
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
		txt_name.setMaxLength(10);


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
		
		if (isLocClientReady()) {		
		} else {		
			Gdx.gl.glClearColor(0,  0,  0 , 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			stage.draw();
			stage.act(delta);
			
			mapPreview.setDrawable(new TextureRegionDrawable(new TextureRegion(MultiMapManager.getMapByIndex(mapIdx).getImage())));	
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
		buttonsAtlas.dispose();
		skin.dispose();
		txt_skin.dispose();
		stage.dispose();
	}
}
