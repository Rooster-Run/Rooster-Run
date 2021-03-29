package uk.ac.aston.teamproj.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.MPClient;
import uk.ac.aston.teamproj.game.screens.MultiPlayScreen;
import uk.ac.aston.teamproj.singleplayer.SinglePlayScreen;

public class Mud extends RectangularObject {

	public Mud(World world, TiledMap map, Rectangle bounds) {
		super(world, map, bounds);
		
		fixture.setUserData(this);
		setCategoryFilter(MainGame.MUD_BIT);
	}
	
	@Override
	public void onHit() {

		MultiPlayScreen.currentSpeed = 0.5f;
		MultiPlayScreen.startTimer = true;
		MultiPlayScreen.buffDuration = MultiPlayScreen.prevUpdateTime + 5000;
		
		SinglePlayScreen.currentSpeed = 0.5f;
		SinglePlayScreen.startTimer = true;
		SinglePlayScreen.buffDuration = SinglePlayScreen.prevUpdateTime + 5000;
	} 	

	@Override
	public TiledMapTileLayer.Cell getCell() {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
		return layer.getCell((int) (body.getPosition().x * MainGame.PPM/96), 
				(int) (body.getPosition().y * MainGame.PPM/96));
	}
}