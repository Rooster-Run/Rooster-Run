package uk.ac.aston.teamproj.game.screens;

import java.util.ArrayList;

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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
import uk.ac.aston.teamproj.game.net.packet.LeftGameSession;
import uk.ac.aston.teamproj.game.net.packet.StartGame;
import uk.ac.aston.teamproj.game.net.packet.TerminateSession;
import uk.ac.aston.teamproj.game.tools.SoundManager;


public class LobbyScreen implements Screen {
	
	Image[] playerIcons = new Image[4];
	Label[] nameLabels = new Label[4];
	

	private MainGame game;
	private Viewport viewport;
	private Stage stage;

	//Buttons
	private TextureAtlas buttonsAtlas; // the sprite-sheet containing all buttons
	private TextureAtlas newButtonAtlas;
	private Skin skin; // skin for buttons
	private Skin new_Skin;
	private ImageButton playBtn, backBtn;
	
	
	public static boolean isGameAboutToStart = false;

	public static ArrayList<Player> currentPlayers = new ArrayList<Player>();
	private boolean isHost;
	private Texture[] textures;
	private Image background;
	//font
	private BitmapFont font;
	
	public LobbyScreen(MainGame game, boolean isHost) {
		this.game = game;
		this.isHost = isHost;
		
		viewport = new FitViewport(MainGame.V_WIDTH / 6, MainGame.V_HEIGHT / 6, new OrthographicCamera());
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
		
		//backrgound
		background = new Image(new Texture("buttons/lobbyBck.png"));
		background.setBounds(0, 0, MainGame.V_WIDTH / 6, MainGame.V_HEIGHT / 6);
		
		//icon textures
		initTextures();
	
		//Buttons
		buttonsAtlas = new TextureAtlas("buttons/buttons.pack");
		newButtonAtlas = new TextureAtlas("buttons/new_buttons.pack");
		skin = new Skin(buttonsAtlas);
		new_Skin = new Skin(newButtonAtlas);
		
		initializeButtons();
		populateBackgroundTable();		
	}

	
	private void initTextures() {
		textures = new Texture[4];
		for(int i = 0; i < textures.length; i++) {
			textures[i] = (new Texture("progress_bar/player" + i + ".png"));
		}
	}
	
	//Methods to retrieve info from MPClient
	private String getTotal() {
		if(currentPlayers.size() == 4) {
			return "LOBBY FULL!";
		} else {
			return "";
		}
	}
	
	private String getToken() {
		return PlayScreen.sessionID;
	}
	
	private void populateTable() {
		Table table2 = new Table();		
		table2.bottom();		

		table2.setFillParent(true);
		table2.add(playBtn).height(22f).width(120).padLeft(270).padTop(500);
	
		stage.addActor(table2);
		Gdx.input.setInputProcessor(stage);
		
	}
	
	private void isHost() {
		boolean firstPlayerInArray = false;
		if (currentPlayers != null && currentPlayers.size() > 0) {
			firstPlayerInArray = (PlayScreen.myID == currentPlayers.get(0).getID());
		}
		
        if (isHost || firstPlayerInArray) {
        	populateTable();
            populateBackTable();
        } else {
            populateBackTable();
        }
    }
	
	private void populateBackgroundTable() {
		Table table2 = new Table();
		table2.bottom();
		table2.setFillParent(true);
		Texture background = new Texture("buttons/multBackgroundBlur.png");
		table2.background(new TextureRegionDrawable(new TextureRegion(background)));
		stage.addActor(table2);
		Gdx.input.setInputProcessor(stage);
		}
	
	private void populateBackTable() {
		Table table = new Table();
		table.bottom();
		table.setFillParent(true);
		table.add(backBtn).height(22f).width(120).padRight(270).padTop(500);
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}
	
	
	private void initializeButtons() {
		ImageButtonStyle style;
		style = new ImageButtonStyle();
		style.up = new_Skin.getDrawable("back_inactive");
		style.over = new_Skin.getDrawable("back_active");
		
		backBtn = new ImageButton(style);
		backBtn.addListener(new InputListener() {
				@Override
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					SoundManager.playSound(SoundManager.POP);
//					
//					LeftGameSession packet = new LeftGameSession();
//					packet.token = PlayScreen.sessionID;
//					packet.playerID = PlayScreen.myID;
					
					LeftGameSession packet = new LeftGameSession();
					packet.token = PlayScreen.sessionID;
					packet.playerID = PlayScreen.myID;
					LobbyScreen.this.dispose();
					PlayScreen.resetSession();
					game.setScreen(new MultiplayerMenuScreen(game));
					MPClient.client.sendTCP(packet);
					return true;
				}
		});
		
		
		
		style = new ImageButtonStyle();
		style.up = skin.getDrawable("play_inactive");
		style.over = skin.getDrawable("play_active");
		
		playBtn = new ImageButton(style);
		playBtn.addListener(new InputListener() {
				@Override
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            	SoundManager.playSound(SoundManager.POP);

					StartGame packet = new StartGame();
					packet.token = PlayScreen.sessionID;
					LobbyScreen.this.dispose();
					game.setScreen(new LoadingScreen(game));
					MPClient.client.sendTCP(packet);
					return true;
				}
		});
		
	}
	

	@Override
	public void show() {

	}

	Group group = new Group();
	
	@Override
	public void render(float delta) {	
		isHost();
		
		//Displaying content on screen if host hasn't pressed start	
		if (!isGameAboutToStart) {
			Gdx.gl.glClearColor(0f, 0f, 0f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
						
			//group = new Group();
			group.clear();
			Label.LabelStyle bitmapFont = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
			Label.LabelStyle labelFont = new Label.LabelStyle(font, Color.WHITE);
			
			//Token label
			Label tokenLabels = new Label(getToken(), bitmapFont);
			tokenLabels.setColor(1f, 1f, 1f, 1f);
			tokenLabels.setFontScale(1.2f);
			tokenLabels.setBounds(210, 147, 20, 20);
			group.addActor(tokenLabels);
			
			//TotalPlayers label 
			Label totalLabels = new Label("" + getTotal(), labelFont);
			totalLabels.setColor(1f, 1f, 1f, 1f);
			totalLabels.setFontScale(1.0f);
			totalLabels.setBounds(300, 185, 20, 20);
			group.addActor(totalLabels);
			
			
			for (int i = 0, j = 100; i < currentPlayers.size(); i++, j -= 25) {
					String name = currentPlayers.get(i).getName();
					playerIcons[i] = new Image(textures[i]);
					playerIcons[i].setColor(1, 1, 1, 1);
					playerIcons[i].setBounds(20, j+20, 20, 20);
					group.addActor(playerIcons[i]);

					nameLabels[i] = new Label (name, labelFont);
					nameLabels[i].setX(70);
					nameLabels[i].setY(j + 20);
					nameLabels[i].setFontScale(1.3f);
					nameLabels[i].setColor(1, 1, 1, 1);
					group.addActor(nameLabels[i]);
			}
			
//			//PlayButton
//			playBtn.setBounds(270, 10, 100, 20);
//			group.addActor(playBtn);
//			
//			//BackButton
//			backBtn.setBounds(30, 10, 100, 20);
//			group.addActor(backBtn);
			
			
			stage.addActor(group);
			stage.draw();
			stage.act(delta);
			
			
		//Host has started game
		} else {
			dispose();
			isGameAboutToStart = false;// reset for next time
			game.setScreen(new LoadingScreen(game));
		}	
		
//		//Checking if wrong token has been entered
//		if(MPClient.errorToken) {
//			game.setScreen(new TokenErrorScreen(game)); //Display an token error screen
//		}
//	
//		//Checking if a user tries to join game after it has started
//		if(MPClient.late) {
//			game.setScreen(new GameInProgressScreen(game)); //Display an game in progress error screen
//		}
		
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
		new_Skin.dispose();
		buttonsAtlas.dispose();
		newButtonAtlas.dispose();
	}

}
