package uk.ac.aston.teamproj.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import uk.ac.aston.teamproj.game.MainGame;

public abstract class CircularObject extends InteractiveTileObject {		
	
	public CircularObject(World world, TiledMap map, Ellipse bounds) {
		super(world, map, bounds);
	}
	
	protected void initBodyDef() {
		Ellipse circle = ((Ellipse) bounds);
		bdef.position.set((circle.x + circle.width/2)/MainGame.PPM, (circle.y + circle.height/2) / MainGame.PPM);
	}
	
	protected void initShape() {
		Ellipse circle = ((Ellipse) bounds);
		shape = new CircleShape();		
		shape.setRadius(circle.width/2/MainGame.PPM);
	}	
}
