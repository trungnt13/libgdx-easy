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

	public NSprite newSprite () {
		return world.newSprite(this);
	}

	public void manage (NSprite sprite) {
		if (sprite.isPooled || mSpriteList.contains(sprite, true))
			return;

		mSpriteList.add(sprite);

		if (sprite.manager != null)
			sprite.manager.mSpriteList.removeValue(sprite, true);
		sprite.manager = this;

		manage(address, sprite.address);
	}

	/**
	 * 
	 * {@link NWorld}
	 * @param sprite
	 */
	public void remove (NSprite sprite) {
		sprite.manager = null;
		sprite.reset();
	}

	public int size () {
		return mSpriteList.size;
	}

	public boolean contain (NSprite sprite) {
		return mSpriteList.contains(sprite, true);
	}

	/******************************************************
	 * manager self method
	 ******************************************************/

	public void clear () {
		world.poolSprite(this);
		clear(address);
	}

	public void dispose () {
		world.directDeleteManager(this);
	}

	/******************************************************
	 * Native method
	 ******************************************************/

	private native void manage (long managerAddress, long spriteAddress);

	private native void clear (long managerAddress);

}
