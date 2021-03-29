package uk.ac.aston.teamproj.game.screens.single;

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
import uk.ac.aston.teamproj.game.screens.GameFinishedScreen;
import uk.ac.aston.teamproj.game.screens.MainMenuScreen;
import uk.ac.aston.teamproj.game.tools.SoundManager;

/**
 * @author Suleman
 * @since 15.03.2021
 * @date 15/03/2021
 */

public class SingleGameFinishedScreen extends GameFinishedScreen {
	
	public SingleGameFinishedScreen(MainGame game) {
		super(game);
	}
	
	@Override
	protected void addLabelWinnerIs() {
		Label.LabelStyle label = new Label.LabelStyle(font, Color.WHITE);
        Label gameOverLabel = new Label ("You Won", label);
        winnerLabelTable.add(gameOverLabel).expandX().padLeft(180).padTop(50);
	}
	
	@Override
	protected void addLabelName() {}
}
