package okj.easy.graphics.graphics2d;

import org.ege.utils.Factory;
import org.ege.utils.Pool;

import com.badlogic.gdx.utils.LongMap;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * 
 * NWorld.java
 * 
 * Created on: Oct 7, 2012
 * Author: Trung
 */
public final class NWorld {
	private long address;

	// ===========================================
	private final LongMap<NativeSpriteBackend> mSpriteMap = new LongMap<NativeSpriteBackend>(100);

	private final Pool<NSprite> mNSpritePool;
	private final Pool<NSpriteA> mNSpriteAPool;

	private CollideListener collideListener;

	// =============================================
	// manager management

	private final NManager mMainManager;
	final LongMap<NManager> mManagerMap = new LongMap<NManager>(3);

	// =============================================
	// sprite def manager

	// count the current number of sprite owning that sprite def
	private final ObjectMap<String, NSpriteDef> mSpriteDefMap = new ObjectMap<String, NSpriteDef>(3);

	public NWorld(int pool_Size_Of_NSprite, int pool_Size_Of_NSpriteA) {
		address = CreateWorld();

		mNSpritePool = new Pool<NSprite>(pool_Size_Of_NSprite, new Factory<NSprite>() {
			@Override
			public NSprite newObject () {
				return createNSprite();
			}

			@Override
			public NSprite newObject (Object... objects) {
				return null;
			}
		});

		mNSpriteAPool = new Pool<NSpriteA>(pool_Size_Of_NSpriteA, new Factory<NSpriteA>() {
			@Override
			public NSpriteA newObject () {
				return createNSpriteA();
			}

			@Override
			public NSpriteA newObject (Object... objects) {
				return null;
			}
		});

		mMainManager = newManager();
		mManagerMap.put(mMainManager.address, mMainManager);
	}

	// =====================================
	// world manager

	private final native long CreateWorld ();

	private final native void DisposeWorld ();

	public NManager getMainList () {
		return mMainManager;
	}

	public int getSpriteSize () {
		return mSpriteMap.size;
	}

	public int getSpriteDefSize () {
		return mSpriteDefMap.size;
	}

	/************************************************************
	 * Sprite associate method
	 ************************************************************/

	// =====================================
	// NSprite

	public NSprite newSprite () {
		NSprite sprite = mNSpritePool.obtain();
		sprite.isPooled = false;
		if (sprite.manager == null)
			mMainManager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public NSprite newSprite (NManager manager) {
		NSprite sprite = mNSpritePool.obtain();
		sprite.isPooled = false;
		manager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public NSprite newSprite (long managerAddress) {
		NManager manager = mManagerMap.get(managerAddress);
		if (manager == null)
			return null;

		NSprite sprite = mNSpritePool.obtain();
		sprite.isPooled = false;
		manager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	// ===================================
	// NSpriteA

	public NSpriteA newSpriteA () {
		NSpriteA sprite = mNSpriteAPool.obtain();
		sprite.isPooled = false;
		if (sprite.manager == null)
			mMainManager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public NSpriteA newSpriteA (NManager manager) {
		NSpriteA sprite = mNSpriteAPool.obtain();
		sprite.isPooled = false;
		manager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public NSpriteA newSpriteA (long managerAddress) {
		NManager manager = mManagerMap.get(managerAddress);
		if (manager == null)
			return null;

		NSpriteA sprite = mNSpriteAPool.obtain();
		sprite.isPooled = false;
		manager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	// ====================================
	// sprite generator

	private final NSprite createNSprite () {
		final NSprite sprite = new NSprite(CreateNSprite(), this);
		sprite.isPooled = false;
		mMainManager.manage(sprite);
		return sprite;
	}

	private final NSpriteA createNSpriteA () {
		final NSpriteA sprite = new NSpriteA(CreateNSprite(), this);
		sprite.isPooled = false;
		mMainManager.manage(sprite);
		return sprite;
	}

	// =====================================

	/**
	 * Remove sprite from manage map , and reset it put it to the pool {@link NSprite}
	 */
	void poolSprite (NativeSpriteBackend sprite) {
		if (sprite instanceof NSprite)
			mNSpritePool.freeNoReset((NSprite) sprite);

		mSpriteMap.remove(sprite.address);
	}

	/**
	 * delete given sprite from sprite map, and sprite pool
	 */
	void deleteSprite (NativeSpriteBackend sprite) {
		if (!sprite.isPooled())
			mSpriteMap.remove(sprite.address);
		else {
			if (sprite instanceof NSprite)
				mNSpritePool.delete((NSprite) sprite);
		}
	}

	// ===============================================
	// native method

	/**
	 * Create a native reference of sprite
	 * 
	 * @return native address of sprite
	 */
	private final native long CreateNSprite ();

	/************************************************************
	 * Manager associate method
	 ************************************************************/

	public NManager newManager () {
		NManager manager = new NManager(CreateManager(), this);
		mManagerMap.put(manager.address, manager);
		return manager;
	}

	public NSpriter newSpriter () {
		NSpriter spriter = new NSpriter(CreateManager(), this);
		mManagerMap.put(spriter.address, spriter);
		return spriter;
	}

	/**
	 * Direct delete manager
	 * 1. check manager = null
	 * 2. dispose sprite
	 * 3. clear manager list
	 * 4. dispose manager {@link NManager}
	 */
	public void delManager (long address) {
		if (address == mMainManager.address)
			return;

		mManagerMap.get(address).dispose();
	}

	/**
	 * Direct delete manager
	 * 1. check manager = null
	 * 2. dispose sprite
	 * 3. clear manager list
	 * 4. dispose manager {@link NManager}
	 */
	public void delManager (NManager manager) {
		if (manager == mMainManager)
			return;

		manager.dispose();
	}

	// =================================================
	// native

	private final native long CreateManager ();

	final native void DisposeManager (long address);

	/**************************************************************
	 * NSpriteDef
	 **************************************************************/

	public NSpriteDef newSpriteDef (String name) {
		final NSpriteDef def = new NSpriteDef(name, CreateSpriteDef(), this);
		mSpriteDefMap.put(name, def);
		return def;
	}

	public NSpriteDef getSpriteDef (String name) {
		return mSpriteDefMap.get(name);
	}

	/**
	 * The sprite is dispose only when no sprite use it
	 */
	public void delSpriteDef (String name) {
		mSpriteDefMap.get(name).dispose();
	}

	public void delSpriteDef (NSpriteDef def) {
		def.dispose();
	}

	void deleteSpriteDef (String name) {
		DisposeSpriteDef(mSpriteDefMap.remove(name).address);
	}

	// =================================================
	// native

	private final native long CreateSpriteDef ();

	private final native void DisposeSpriteDef (long spriteDefAddress);

	/************************************************************
	 * Collision Processor
	 ************************************************************/
	public void setCollideListener (CollideListener listner) {
		this.collideListener = listner;
	}

	public void ProcessCollision (long manager1, long manager2, CollideListener listener) {
		this.collideListener = listener;
		processCollision(manager1, manager2);
	}

	public void ProcessCollision (long manager1, long manager2) {
		if (collideListener == null)
			return;
		processCollision(manager1, manager2);
	}

	public void ProcessCollision (long manager, CollideListener listener) {
		this.collideListener = listener;
		processCollision(manager);
	}

	public void ProcessCollision (long manager) {
		if (collideListener == null)
			return;
		processCollision(manager);
	}

	private void collide (long address1, long address2) {
		collideListener.Collided(mSpriteMap.get(address1), mSpriteMap.get(address2));
	}

	// ==============================================
	// native method

	public final native void CollisionConfig (int worldX, int worldY, int worldWidth,
			int worldHeight,
			int cols, int row);

	private final native void processCollision (long manager1, long manager2);

	private final native void processCollision (long manager);
}
