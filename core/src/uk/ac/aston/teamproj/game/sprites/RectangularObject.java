package uk.ac.aston.teamproj.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import uk.ac.aston.teamproj.game.MainGame;

/**
 *   Edited by Parmo on 5.11.20
 *      Mods: fixture variable, on head hit event
 *      New methods: setCategoryFilter(), getCell()
 */

public abstract class RectangularObject extends InteractiveTileObject{	
		
	public RectangularObject(World world, TiledMap map, Rectangle bounds) {
		super(world, map, bounds);		
	}	
	
	protected void initBodyDef() {
		Rectangle rect = ((Rectangle) bounds);
		bdef.position.set((rect.getX() + rect.getWidth()/2)/MainGame.PPM, (rect.getY() + rect.getHeight()/2) / MainGame.PPM);
	}
	
	protected void initShape() {
		Rectangle rect = ((Rectangle) bounds);
		shape = new PolygonShape();		
		((PolygonShape) shape).setAsBox(rect.getWidth() / 2 / MainGame.PPM, rect.getHeight() / 2 / MainGame.PPM);
	}
}
