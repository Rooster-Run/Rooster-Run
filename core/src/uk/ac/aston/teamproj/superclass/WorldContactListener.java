package uk.ac.aston.teamproj.superclass;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.screens.MultiPlayScreen;
import uk.ac.aston.teamproj.game.sprites.Bomb;
import uk.ac.aston.teamproj.game.sprites.Brick;
import uk.ac.aston.teamproj.game.sprites.Coin;
import uk.ac.aston.teamproj.game.sprites.EndPlane;
import uk.ac.aston.teamproj.game.sprites.IceCube;
import uk.ac.aston.teamproj.game.sprites.Lightning;
import uk.ac.aston.teamproj.game.sprites.Mud;
import uk.ac.aston.teamproj.game.sprites.RectangularObject;
import uk.ac.aston.teamproj.game.tools.MultiWorldContactListener;
import uk.ac.aston.teamproj.singleplayer.SinglePlayScreen;
import uk.ac.aston.teamproj.singleplayer.SingleWorldContactListener;

public abstract class WorldContactListener implements ContactListener {
	
	protected PlayScreen playScreen;
	
	public WorldContactListener(PlayScreen playScreen) {
		this.playScreen = playScreen;
	}	
	
	@Override 
	public void endContact(Contact contact) {}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		
		// check if rooster is colliding with the ground
		if (fixA.getUserData() == "legs" || fixB.getUserData() == "legs") {
			Fixture beak = (fixA.getUserData() == "legs")? fixA : fixB;
			Fixture object = (beak == fixA)? fixB : fixA; //the other object
			
			//check if other object is an interactive one
			if (object.getUserData() != null && RectangularObject.class.isAssignableFrom(object.getUserData().getClass())) {
				playScreen.resetJumpCount1();
			}
		}
		
		switch (cDef) {
			case (MainGame.ROOSTER_BIT | MainGame.BOMB_BIT):			
				Fixture bombFixture = (fixA.getFilterData().categoryBits == MainGame.BOMB_BIT) ? fixA : fixB;
				Bomb bomb = ((Bomb) bombFixture.getUserData());
				
				bomb.onHit();
				playScreen.makeBombExplode(bomb);
				if (WorldContactListener.this instanceof SingleWorldContactListener)
					SinglePlayScreen.player.decreaseLives();
				else if (WorldContactListener.this instanceof MultiWorldContactListener)
					MultiPlayScreen.player.decreaseLives();
				break;

			case (MainGame.ROOSTER_BIT | MainGame.BRICK_BIT):
				Fixture brickFixture = (fixA.getFilterData().categoryBits == MainGame.BRICK_BIT) ? fixA : fixB;
				((Brick) brickFixture.getUserData()).onHit();
				break;

			case (MainGame.ROOSTER_BIT | MainGame.COIN_BIT):
				
				if (WorldContactListener.this instanceof SingleWorldContactListener)
					SinglePlayScreen.player.updateCoins(1);
				else if (WorldContactListener.this instanceof MultiWorldContactListener)
					MultiPlayScreen.player.updateCoins(1);

				Fixture coinFixture = (fixA.getFilterData().categoryBits == MainGame.COIN_BIT) ? fixA : fixB;
				((Coin) coinFixture.getUserData()).onHit();
				
				break;

			case (MainGame.ROOSTER_BIT | MainGame.LIGHTNING_BIT):			
				Fixture lightningFixture = (fixA.getFilterData().categoryBits == MainGame.LIGHTNING_BIT) ? fixA : fixB;
				((Lightning) lightningFixture.getUserData()).onHit();
				break;
				
			case (MainGame.ROOSTER_BIT | MainGame.PLANE_BIT):
				if (WorldContactListener.this instanceof SingleWorldContactListener)
					SinglePlayScreen.player.onFinish();
				else if (WorldContactListener.this instanceof MultiWorldContactListener)
					MultiPlayScreen.player.onFinish();
				
				Fixture PlaneFixture = (fixA.getFilterData().categoryBits == MainGame.PLANE_BIT)? fixA : fixB;
				((EndPlane) PlaneFixture.getUserData()).onHit();
				break;
			
			case (MainGame.ROOSTER_BIT | MainGame.MUD_BIT):
				Fixture mudFixture = (fixA.getFilterData().categoryBits == MainGame.MUD_BIT) ? fixA : fixB;
				((Mud) mudFixture.getUserData()).onHit();
				break;
				
			case (MainGame.ROOSTER_BIT | MainGame.ICE_BIT):			
				Fixture iceCubeFixture = (fixA.getFilterData().categoryBits == MainGame.ICE_BIT) ? fixA : fixB;
				((IceCube) iceCubeFixture.getUserData()).onHit();
				reactToIceCollision();
				break;

			default:
				break;
		}			
	}
	
	protected abstract void reactToIceCollision();
}