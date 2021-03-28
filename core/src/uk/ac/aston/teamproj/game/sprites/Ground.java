package uk.ac.aston.teamproj.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import uk.ac.aston.teamproj.game.MainGame;

public class Ground extends RectangularObject {

	
	public Ground(World world, TiledMap map, Rectangle bounds) {
		super(world, map, bounds);

		fixture.setUserData(this);
		setCategoryFilter(MainGame.GROUND_BIT);
	}

	@Override
	public void onHit() {
		
	}

	@Override
	public Cell getCell() {
		return null;
	}

}
