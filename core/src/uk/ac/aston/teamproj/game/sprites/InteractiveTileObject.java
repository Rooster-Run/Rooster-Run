package uk.ac.aston.teamproj.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
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

public abstract class InteractiveTileObject {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected Body body;
	
	protected Fixture fixture;
	
	public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
		this.world = world;
		this.map = map;
		this.bounds = bounds;
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.getX() + bounds.getWidth()/2)/MainGame.PPM, (bounds.getY() + bounds.getHeight()/2) / MainGame.PPM);
		
		body = world.createBody(bdef);
		
		// Rect starts at the center
		shape.setAsBox(bounds.getWidth() / 2 / MainGame.PPM, bounds.getHeight() / 2 / MainGame.PPM);
		fdef.shape = shape;
		fixture = body.createFixture(fdef);
	}
	
	public abstract void onHit();	
	
	public void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();
		
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
	}
	
	public abstract TiledMapTileLayer.Cell getCell();
	
}
