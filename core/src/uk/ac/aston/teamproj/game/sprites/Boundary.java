package uk.ac.aston.teamproj.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import uk.ac.aston.teamproj.game.MainGame;

public class Boundary extends InteractiveTileObject {

	public Boundary(World world, TiledMap map, Rectangle bounds) {
		super(world, map, bounds);
		
		fixture.setUserData(this);
		setCategoryFilter(MainGame.BOUNDARY_BIT);
	}

	@Override
	public void onHit() {
		//do-nothing
	}

	@Override
	public Cell getCell() {
		return null;
	}

}
