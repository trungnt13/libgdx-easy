package okj.easy.graphics.graphics2d;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * 
 * NManager.java
 * 
 * Created on: Oct 7, 2012
 * Author: Trung
 */
public class NManager implements Disposable {
	public final long address;
	private final NWorld world;

	// =========================================
	// sprite params

	final Array<NativeSpriteBackend> mSpriteList = new Array<NativeSpriteBackend>(13);

	NManager(long address, NWorld world) {
		this.address = address;
		this.world = world;
	}

	/******************************************************
	 * manager processor
	 ******************************************************/

	public void update (float delta) {
		for (int i = 0; i < mSpriteList.size; i++)
			mSpriteList.get(i).update(delta);
	}

	public void draw (SpriteBatch batch) {
		for (NativeSpriteBackend s : mSpriteList)
			s.draw(batch);
	}

	/******************************************************
	 * sprite manager
	 ******************************************************/

	public NSprite newSprite () {
		return world.newSprite(this);
	}

	public void manage (NativeSpriteBackend sprite) {
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
	 * 
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
