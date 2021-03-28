package uk.ac.aston.teamproj.superclass;

import com.badlogic.gdx.Screen;

import uk.ac.aston.teamproj.game.sprites.Bomb;
import uk.ac.aston.teamproj.game.sprites.Rooster;

public abstract class PlayScreen implements Screen {
	public abstract void resetJumpCount1();
	public abstract void makeBombExplode(Bomb bomb);
}
