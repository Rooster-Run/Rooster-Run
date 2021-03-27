package uk.ac.aston.teamproj.game.tools;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * @author Derrick
 * @since 01.03.2021
 * @date 01/03/2021
 */

public class SoundManager implements Sound {

	public enum SoundType {
		POP,
		CLICK,
		LIGHTNING,
		GAMEOVER,
		FIRSTPLACE,
		SWOOSH,
	}
	public static boolean soundsPlaying = true;

	
	public static final Sound POP = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
	public static final Sound CLICK = Gdx.audio.newSound(Gdx.files.internal("menu_click.mp3"));
	public static final Sound LIGHTENING = Gdx.audio.newSound(Gdx.files.internal("lightening.mp3"));
	public static final Sound GAMEOVER = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
	public static final Sound FIRSTPLACE = Gdx.audio.newSound(Gdx.files.internal("firstplace.wav"));
	public static final Sound SWOOSH = Gdx.audio.newSound(Gdx.files.internal("electric-transition-super-quick-www.mp3"));
	public static final Sound COIN = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
	public static final Sound BOMB = Gdx.audio.newSound(Gdx.files.internal("bomb.wav"));
	  
	 

	public SoundManager() {
	}

	public static void playSound(SoundType sound) {
		if (soundsPlaying != false) {
			sound.play(1F);
		}

	}

	public static void SoundsOn() {
		soundsPlaying = true;
	}

	public static void SoundsOff() {
		soundsPlaying = false;
	}

	@Override
	public long play() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long play(float volume) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long play(float volume, float pitch, float pan) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long loop() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long loop(float volume) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long loop(float volume, float pitch, float pan) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

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
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop(long soundId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause(long soundId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume(long soundId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLooping(long soundId, boolean looping) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPitch(long soundId, float pitch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setVolume(long soundId, float volume) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPan(long soundId, float pan, float volume) {
		// TODO Auto-generated method stub

	}

}
