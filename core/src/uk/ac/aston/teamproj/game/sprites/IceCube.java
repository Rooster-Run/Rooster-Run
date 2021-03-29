package uk.ac.aston.teamproj.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.physics.box2d.World;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.MPClient;
import uk.ac.aston.teamproj.game.screens.MultiPlayScreen;
import uk.ac.aston.teamproj.singleplayer.SinglePlayScreen;

public class IceCube extends CircularObject {

	public IceCube(World world, TiledMap map, Ellipse bounds) {
		super(world, map, bounds);
		
		fixture.setUserData(this);
		setCategoryFilter(MainGame.ICE_BIT);
	}
	
	@Override
	public void onHit() {
		setCategoryFilter(MainGame.DESTROYED_BIT);
		getCell().setTile(null);
		
		MultiPlayScreen.startTimer = true;
		MultiPlayScreen.buffDuration = MultiPlayScreen.prevUpdateTime + 3000;
		
		SinglePlayScreen.startTimer = true;
		SinglePlayScreen.buffDuration = SinglePlayScreen.prevUpdateTime + 3000;
	
	} 	

	@Override
	public TiledMapTileLayer.Cell getCell() {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
		return layer.getCell((int) (body.getPosition().x * MainGame.PPM/96), 
				(int) (body.getPosition().y * MainGame.PPM/96));
	}
}
