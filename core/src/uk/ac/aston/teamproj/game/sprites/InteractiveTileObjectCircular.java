package uk.ac.aston.teamproj.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import uk.ac.aston.teamproj.game.MainGame;

public abstract class InteractiveTileObjectCircular {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Ellipse bounds;
	protected Body body;
	
	protected Fixture fixture;
	
	public InteractiveTileObjectCircular(World world, TiledMap map, Ellipse bounds) {
		this.world = world;
		this.map = map;
		this.bounds = bounds;
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		
		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.x + bounds.width/2)/MainGame.PPM, (bounds.y + bounds.height/2) / MainGame.PPM);
		
		body = world.createBody(bdef);
		
		// Circle starts at the center
		shape.setRadius(bounds.width/2/MainGame.PPM);
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
