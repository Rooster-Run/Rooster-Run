package uk.ac.aston.teamproj.game.tools;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * @author Derrick
 * @since 01.03.2021
 * @date 01/03/2021
 */

public class SoundManager {

	public static final Sound POP = Gdx.audio.newSound(Gdx.files.internal("sounds/pop.mp3"));
	public static final Sound CLICK = Gdx.audio.newSound(Gdx.files.internal("sounds/menu_click.mp3"));
	public static final Sound LIGHTNING = Gdx.audio.newSound(Gdx.files.internal("sounds/lightening.mp3"));
	public static final Sound GAMEOVER = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.mp3"));
	public static final Sound FIRSTPLACE = Gdx.audio.newSound(Gdx.files.internal("sounds/firstplace.wav"));
	public static final Sound SWOOSH = Gdx.audio.newSound(Gdx.files.internal("sounds/electric-transition-super-quick-www.mp3"));
	public static final Sound COIN = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));
	public static final Sound BOMB = Gdx.audio.newSound(Gdx.files.internal("sounds/bomb.wav"));
	
	private static boolean audioOn = true;	

	public static void playSound(Sound sound) {
		if (audioOn != false) {
			sound.play(1F);
		}
	}

	public static void audioOn() {
		audioOn = true;
	}

	public static void audioOff() {
		audioOn = false;
	}
}
