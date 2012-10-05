package okj.easy.graphics.graphics2d;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class NManager implements Disposable {
	public final long address;
	private final NWorld world;

	// =========================================
	// sprite params
	final Array<NSprite> mSpriteList = new Array<NSprite>(13);

	NManager(long address, NWorld world) {
		this.address = address;
		this.world = world;
	}

	/******************************************************
	 * sprite manager
	 ******************************************************/

	public NSprite obtain () {
		return null;
	}

	public void manage (NSprite sprite) {

	}

	public void reset (NSprite sprite) {

	}

	/******************************************************
	 * manager self method
	 ******************************************************/
	
	public void clear(){
		
	}

	public void dispose () {
		world.deleteManager(address);
	}
}
