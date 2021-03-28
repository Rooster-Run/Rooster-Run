package uk.ac.aston.teamproj.game.screens;

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
import uk.ac.aston.teamproj.game.tools.SoundManager;
import uk.ac.aston.teamproj.superclass.GameFinishedScreen;
import uk.ac.aston.teamproj.superclass.PlayScreen;

/**
 * @author Suleman, Junaid and Marcus
 * @since 15.03.2021
 * @date 15/03/2021
 */

public class MultiGameFinishedScreen extends GameFinishedScreen {
	
	private Label winnerLabel;

	public MultiGameFinishedScreen(MainGame game) {
		super(game);
		MultiPlayScreen.resetSession();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		winnerLabel.setText(PlayScreen.winner);	
	}


	@Override
	protected void addLabelWinnerIs() {
		//LabelWinner
        Label.LabelStyle label = new Label.LabelStyle(font, Color.WHITE);
        Label gameOverLabel = new Label ("Winner:   ", label);
        winnerLabelTable.add(gameOverLabel).expandX().padLeft(180).padTop(50);
	}
	
	@Override
	protected void addLabelName() {
		Label.LabelStyle labelName = new Label.LabelStyle(font, Color.WHITE);
        winnerLabel = new Label (PlayScreen.winner, labelName);
        winnerNameTable.add(winnerLabel).expandX().padTop(100).padLeft(150);
	}
}
