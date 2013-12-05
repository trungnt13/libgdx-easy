package stu.tnt.gdx.graphics.graphics2d;

import stu.tnt.gdx.utils.Updater;
import stu.tnt.gdx.utils.exception.EasyGEngineRuntimeException;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * NManager.java
 * 
 * Created on: Oct 7, 2012 Author: Trung
 */
public class NManager implements Disposable {
	public final long address;
	protected final NWorld world;
	private boolean isDispose;

	// =========================================
	// sprite params

	final Array<NativeSpriteBackend> mSpriteList = new Array<NativeSpriteBackend>(
			13);
	// ============= for safe traverse =============
	private static final Array<NativeSpriteBackend> CloneList = new Array<NativeSpriteBackend>(
			100);

	NManager(long address, NWorld world) {
		this.address = address;
		this.world = world;
	}

	public Array<NativeSpriteBackend> CloneList() {
		if (isDispose)
			return null;

		Array<NativeSpriteBackend> clone = new Array<NativeSpriteBackend>(
				mSpriteList.size);
		clone.addAll(mSpriteList);
		return clone;
	}

	public Array<NativeSpriteBackend> List() {
		if (isDispose)
			return null;

		return mSpriteList;
	}

	/****************************************************** manager processor ******************************************************/
	public void postUpdater(Updater update) {
		if (isDispose)
			return;

		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.postUpdater(update);
	}

	public void update(float delta) {
		if (isDispose)
			return;

		CloneList.clear();
		CloneList.addAll(mSpriteList);
		for (NativeSpriteBackend s : CloneList) {
			s.update(delta);
		}
	}

	public void draw(Batch batch) {
		if (isDispose)
			return;

		for (NativeSpriteBackend s : mSpriteList)
			s.draw(batch);
	}

	/****************************************************** sprite manager ******************************************************/

	/** Create new sprite which is manage by this manager */
	public NSprite newSprite() {
		if (isDispose)
			return null;

		return world.newSprite(this);
	}

	/** Create new sprite which is manage by this manager */
	public NSpriteA newSpriteA() {
		return world.newSpriteA(this);
	}

	public <T extends NSprite> T newSprite(Class<T> type) {
		if (isDispose)
			return null;

		return world.newSprite(type, this);
	}

	public <T extends NSpriteA> T newSpriteA(Class<T> type) {
		if (isDispose)
			return null;

		return world.newSpriteA(type, this);
	}

	/**
	 * 1. Check sprite is pooled ,or alread contained 2. unmanage sprite from
	 * its manager 3. manage it
	 */
	public void manage(NativeSpriteBackend sprite) {
		if (isDispose)
			return;

		if (sprite.isPooled || mSpriteList.contains(sprite, true))
			return;

		if (sprite.manager != null)
			sprite.manager.unmanage(sprite);

		sprite.manager = this;
		mSpriteList.add(sprite);
		manage(address, sprite.address);
	}

	/**
	 * {@link NWorld}
	 * 
	 * @param sprite
	 */
	public void remove(NativeSpriteBackend sprite) {
		if (isDispose)
			return;

		sprite.reset();
	}

	public int size() {
		final int size = size(address);
		if (mSpriteList.size != size)
			throw new EasyGEngineRuntimeException("Size sync between native : "
					+ size + " and java : " + mSpriteList.size + " is wrong");
		return size;
	}

	public boolean contain(NSprite sprite) {
		return mSpriteList.contains(sprite, true);
	}

	/** remove from java list and native list */
	void unmanage(NativeSpriteBackend sprite) {
		if (isDispose)
			return;

		mSpriteList.removeValue(sprite, true);
		sprite.unmanage();
	}

	/****************************************************** manager self method ******************************************************/

	public void clear() {
		if (isDispose)
			return;

		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.reset();
		mSpriteList.clear();
		clear(address);
	}

	public void dispose() {
		if (isDispose)
			return;

		isDispose = true;
		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.dispose();

		mSpriteList.clear();
		world.mManagerMap.remove(address);
		world.DisposeManager(address);
	}

	public boolean isDisposed() {
		return isDispose;
	}

	/****************************************************** Native method ******************************************************/

	private native int size(long address);

	private native void manage(long managerAddress, long spriteAddress);

	private native void clear(long managerAddress);
}
