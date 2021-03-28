package uk.ac.aston.teamproj.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.MPClient;
import uk.ac.aston.teamproj.game.screens.PlayScreen;
import uk.ac.aston.teamproj.singleplayer.SinglePlayerScreen;

public class Mud extends RectangularObject {

	public Mud(World world, TiledMap map, Rectangle bounds) {
		super(world, map, bounds);
		
		fixture.setUserData(this);
		setCategoryFilter(MainGame.MUD_BIT);
	}
	
	@Override
	public void onHit() {
		Gdx.app.log(String.valueOf(MPClient.clientID), "Mud Collision");
		//set category to destroyed bit
		//setCategoryFilter(MainGame.DESTROYED_BIT);
		//getCell().setTile(null);
		PlayScreen.currentSpeed = 0.5f;
		PlayScreen.startTimer = true;
		PlayScreen.buffDuration = PlayScreen.prevUpdateTime + 5000;
		
		SinglePlayerScreen.currentSpeed = 0.5f;
		SinglePlayerScreen.startTimer = true;
		SinglePlayerScreen.buffDuration = SinglePlayerScreen.prevUpdateTime + 5000;
	} 	

	@Override
	public TiledMapTileLayer.Cell getCell() {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
		return layer.getCell((int) (body.getPosition().x * MainGame.PPM/96), 
				(int) (body.getPosition().y * MainGame.PPM/96));
	}
}