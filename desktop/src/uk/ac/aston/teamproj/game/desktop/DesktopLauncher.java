package uk.ac.aston.teamproj.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import uk.ac.aston.teamproj.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Rooster Run";
		
//		TexturePacker.Settings settings = new TexturePacker.Settings(); 		
//		settings.pot = true;
//		settings.fast = true;
//		settings.combineSubdirectories = true;
//		settings.paddingX = 1;
//		settings.paddingY = 1;
//		settings.edgePadding = true;
//		
//		TexturePacker.process(settings, "assets/raw_textures", "assets/", "textures");

		new LwjglApplication(new MainGame(), config);
	}
}
