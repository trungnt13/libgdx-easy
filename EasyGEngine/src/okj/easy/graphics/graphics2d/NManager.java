package okj.easy.graphics.graphics2d;

import org.ege.utils.exception.EasyGEngineRuntimeException;

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

	private final Array<NativeSpriteBackend> mSpriteList = new Array<NativeSpriteBackend>(13);

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

	/** Create new sprite which is manage by this manager */
	public NSprite newSprite () {
		return world.newSprite(this);
	}

	/**
	 * 1. Check sprite is pooled ,or alread contained
	 * 2. unmanage sprite from its manager
	 * 3. manage it
	 */
	public void manage (NativeSpriteBackend sprite) {
		if (sprite.isPooled || mSpriteList.contains(sprite, true))
			return;

		if (sprite.manager != null)
			sprite.manager.unmanage(sprite);

		sprite.manager = this;
		mSpriteList.add(sprite);
		manage(address, sprite.address);
	}

	/**
	 * 
	 * {@link NWorld}
	 * 
	 * @param sprite
	 */
	public void remove (NSprite sprite) {
		sprite.reset();
	}

	public int size () {
		final int size = size(address);
		if (mSpriteList.size != size)
			throw new EasyGEngineRuntimeException("Size sync between native : " + size
					+ " and java : " + mSpriteList.size + " is wrong");
		return size;
	}

	public boolean contain (NSprite sprite) {
		return mSpriteList.contains(sprite, true);
	}

	/** remove from java list and native list */
	void unmanage (NativeSpriteBackend sprite) {
		mSpriteList.removeValue(sprite, true);
		sprite.unmanage();
	}

	/******************************************************
	 * manager self method
	 ******************************************************/

	public void clear () {
		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.reset();
		mSpriteList.clear();
		clear(address);
	}

	public void dispose () {

		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.dispose();

		mSpriteList.clear();
		world.mManagerMap.remove(address);
		world.DisposeManager(address);
	}

	/******************************************************
	 * Native method
	 ******************************************************/

	private native int size (long address);

	private native void manage (long managerAddress, long spriteAddress);

	private native void clear (long managerAddress);
}
