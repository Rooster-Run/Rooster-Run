package uk.ac.aston.teamproj.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.tools.SoundManager;

/**
 * 
 * created by Parmo on 5.11.20
 *
 */

public class EndPlane extends RectangularObject {
	
	public EndPlane(World world, TiledMap map, Rectangle bounds) {
		super(world, map, bounds);
		
		fixture.setUserData(this);
		setCategoryFilter(MainGame.PLANE_BIT);
	}

	@Override
	public void onHit() {
        SoundManager.playSound(SoundManager.FIRSTPLACE);
	}
		
	@Override
	public TiledMapTileLayer.Cell getCell() {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
		return layer.getCell((int) (body.getPosition().x * MainGame.PPM/96), 
				(int) (body.getPosition().y * MainGame.PPM/96));
	}
}
