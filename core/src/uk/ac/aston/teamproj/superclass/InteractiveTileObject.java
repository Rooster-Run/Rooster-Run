package uk.ac.aston.teamproj.superclass;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class InteractiveTileObject {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Shape2D bounds;
	protected Body body;
	protected Fixture fixture;
	protected BodyDef bdef;
	protected FixtureDef fdef;
	protected Shape shape;

	public InteractiveTileObject(World world, TiledMap map, Shape2D bounds) {
		this.world = world;
		this.map = map;
		this.bounds = bounds;
		
		bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.StaticBody;
		fdef = new FixtureDef();
		
		initBodyDef();
		body = world.createBody(bdef);
		initShape();
		
		fdef.shape = shape;
		fixture = body.createFixture(fdef);
	}
	
	public void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();		
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
	}
	
	protected abstract void onHit();	
	
	protected abstract TiledMapTileLayer.Cell getCell();
	
	protected abstract void initBodyDef();
	
	protected abstract void initShape();
}
