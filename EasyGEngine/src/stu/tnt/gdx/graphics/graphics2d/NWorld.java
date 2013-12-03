package stu.tnt.gdx.graphics.graphics2d;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import stu.tnt.gdx.utils.Factory;
import stu.tnt.gdx.utils.Pool;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.LongMap;
import com.badlogic.gdx.utils.LongMap.Values;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * NWorld.java
 * 
 * Created on: Oct 7, 2012 Author: Trung
 */
public final class NWorld implements Disposable {
	private long address;

	// ============= Sprite mapping and pooling =============
	final LongMap<NativeSpriteBackend> mSpriteMap = new LongMap<NativeSpriteBackend>(
			100);

	private final ObjectMap<Class<?>, Pool<?>> mSpritePoolMap = new ObjectMap<Class<?>, Pool<?>>(
			1);
	private final ObjectMap<Class<?>, Pool<?>> mSpriteAPoolMap = new ObjectMap<Class<?>, Pool<?>>(
			1);

	private CollideListener collideListener;

	// ============= Manager mapping =============
	private final NManager mMainManager;
	final LongMap<NManager> mManagerMap = new LongMap<NManager>(3);

	// ============= SpriteDef manager =============
	private final ObjectMap<String, NSpriteDef> mSpriteDefMap = new ObjectMap<String, NSpriteDef>(
			3);

	/*********************************************************** Constructors ***********************************************************/

	public NWorld(int pool_Size_Of_NSprite, int pool_Size_Of_NSpriteA) {
		address = CreateWorld();

		// create default sprite pool
		Pool<NSprite> sprite = new Pool<NSprite>(pool_Size_Of_NSprite,
				new Factory<NSprite>() {
					@Override
					public NSprite newObject() {
						return createNSprite();
					}

					@Override
					public NSprite newObject(Object... objects) {
						return null;
					}
				});
		mSpritePoolMap.put(NSprite.class, sprite);

		// create default spriteA pool
		Pool<NSpriteA> spriteA = new Pool<NSpriteA>(pool_Size_Of_NSpriteA,
				new Factory<NSpriteA>() {
					@Override
					public NSpriteA newObject() {
						return createNSpriteA();
					}

					@Override
					public NSpriteA newObject(Object... objects) {
						return null;
					}
				});
		mSpriteAPoolMap.put(NSpriteA.class, spriteA);

		// create default manager
		mMainManager = newManager();
		mManagerMap.put(mMainManager.address, mMainManager);
	}

	// ============= WOrld method =============

	@Override
	public void dispose() {
		Values<NManager> val = mManagerMap.values();

		for (NManager m : val) {
			if (m != null)
				m.dispose();
		}
		DisposeWorld();
	}

	private final native long CreateWorld();

	private final native void DisposeWorld();

	public NManager getMainList() {
		return mMainManager;
	}

	public int getSpriteSize() {
		return mSpriteMap.size;
	}

	public int getSpriteDefSize() {
		return mSpriteDefMap.size;
	}

	/************************************************************ Sprite associate method ************************************************************/
	// =====================================
	// NSPrite vs NSpriteA pool manager

	public <T extends NSprite> Pool<T> makeNSpritePool(Class<T> type,
			Factory<T> factory, int maxSize) {
		final Pool<T> pool = new Pool<T>(maxSize, factory);
		mSpritePoolMap.put(type, pool);
		return pool;
	}

	public <T extends NSpriteA> Pool<T> makeNSpriteAPool(Class<T> type,
			Factory<T> factory, int maxSize) {
		final Pool<T> pool = new Pool<T>(maxSize, factory);
		mSpriteAPoolMap.put(type, pool);
		return pool;
	}

	// =====================================
	// NSprite

	public NSprite newSprite() {
		NSprite sprite = (NSprite) mSpritePoolMap.get(NSprite.class).obtain();
		sprite.isPooled = false;
		if (sprite.manager == null)
			mMainManager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public NSprite newSprite(NManager manager) {
		NSprite sprite = (NSprite) mSpritePoolMap.get(NSprite.class).obtain();
		sprite.isPooled = false;
		manager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public NSprite newSprite(long managerAddress) {
		NManager manager = mManagerMap.get(managerAddress);
		if (manager == null)
			return null;

		NSprite sprite = (NSprite) mSpritePoolMap.get(NSprite.class).obtain();
		sprite.isPooled = false;
		manager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	// ===================================
	// NSpriteA

	public NSpriteA newSpriteA() {
		NSpriteA sprite = (NSpriteA) mSpriteAPoolMap.get(NSpriteA.class)
				.obtain();
		sprite.isPooled = false;
		if (sprite.manager == null)
			mMainManager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public NSpriteA newSpriteA(NManager manager) {
		NSpriteA sprite = (NSpriteA) mSpriteAPoolMap.get(NSpriteA.class)
				.obtain();
		sprite.isPooled = false;
		manager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public NSpriteA newSpriteA(long managerAddress) {
		NManager manager = mManagerMap.get(managerAddress);
		if (manager == null)
			return null;

		NSpriteA sprite = (NSpriteA) mSpriteAPoolMap.get(NSpriteA.class)
				.obtain();
		sprite.isPooled = false;
		manager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	// ===================================
	// Sprite Customize

	public <T extends NSprite> T newSprite(Class<T> type) {
		T sprite = (T) mSpritePoolMap.get(type).obtain();
		sprite.isPooled = false;
		if (sprite.manager == null)
			mMainManager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public <T extends NSprite> T newSprite(Class<T> type, NManager manager) {
		T sprite = (T) mSpritePoolMap.get(type).obtain();
		sprite.isPooled = false;
		manager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public <T extends NSprite> T newSprite(Class<T> type, long managerAddress) {
		NManager manager = mManagerMap.get(managerAddress);
		if (manager == null)
			return null;

		T sprite = (T) mSpritePoolMap.get(type).obtain();
		sprite.isPooled = false;
		manager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public <T extends NSpriteA> T newSpriteA(Class<T> type) {
		T sprite = (T) mSpriteAPoolMap.get(type).obtain();
		sprite.isPooled = false;
		if (sprite.manager == null)
			mMainManager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public <T extends NSpriteA> T newSpriteA(Class<T> type, NManager manager) {
		T sprite = (T) mSpriteAPoolMap.get(type).obtain();
		sprite.isPooled = false;
		manager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	public <T extends NSpriteA> T newSpriteA(Class<T> type, long managerAddress) {
		NManager manager = mManagerMap.get(managerAddress);
		if (manager == null)
			return null;

		T sprite = (T) mSpriteAPoolMap.get(type).obtain();
		sprite.isPooled = false;
		manager.manage(sprite);
		mSpriteMap.put(sprite.address, sprite);
		return sprite;
	}

	// ====================================
	// sprite generator

	private final NSprite createNSprite() {
		final NSprite sprite = new NSprite(CreateNSprite(), this);
		return sprite;
	}

	private final NSpriteA createNSpriteA() {
		final NSpriteA sprite = new NSpriteA(CreateNSprite(), this);
		return sprite;
	}

	// ============= Associate with sprite =============

	/**
	 * Remove sprite from manage map , and reset it put it to the pool
	 * {@link NSprite}
	 */
	void poolSprite(NativeSpriteBackend sprite) {
		if (sprite instanceof NSprite) {
			final Pool pool = mSpritePoolMap.get(sprite.getClass());
			if (!pool.freeNoReset(sprite))
				sprite.dispose();
		} else if (sprite instanceof NSpriteA) {
			final Pool pool = mSpriteAPoolMap.get(sprite.getClass());
			if (!pool.freeNoReset(sprite))
				sprite.dispose();
		}
		mSpriteMap.remove(sprite.address);
	}

	/** delete given sprite from sprite map, and sprite pool */
	void deleteSprite(NativeSpriteBackend sprite) {
		if (!sprite.isPooled())
			mSpriteMap.remove(sprite.address);
		else {
			if (sprite instanceof NSprite) {
				final Pool pool = mSpritePoolMap.get(sprite.getClass());
				pool.delete(sprite);
			} else if (sprite instanceof NSpriteA) {
				final Pool pool = mSpriteAPoolMap.get(sprite.getClass());
				pool.delete(sprite);
			}

		}
	}

	// ============= Native method for sprite =============

	/**
	 * Create a native reference of sprite
	 * 
	 * @return native address of sprite
	 */
	public final native long CreateNSprite();

	/************************************************************ Manager associate method ************************************************************/

	public NManager newManager() {
		NManager manager = new NManager(CreateManager(), this);
		mManagerMap.put(manager.address, manager);
		return manager;
	}

	public NSpriter newSpriter() {
		NSpriter spriter = new NSpriter(CreateManager(), this);
		mManagerMap.put(spriter.address, spriter);
		return spriter;
	}

	public <T extends NSpriter> T newSpriter(Class<T> type) {
		T t = null;
		try {
			Constructor<T> cons = type.getDeclaredConstructor(long.class,
					NWorld.class);
			cons.setAccessible(true);
			t = cons.newInstance(CreateManager(), this);
			mManagerMap.put(t.address, t);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * Direct delete manager 1. check manager = null 2. dispose sprite 3. clear
	 * manager list 4. dispose manager {@link NManager}
	 */
	public void delManager(long address) {
		if (address == mMainManager.address)
			return;

		mManagerMap.get(address).dispose();
	}

	/**
	 * Direct delete manager 1. check manager = null 2. dispose sprite 3. clear
	 * manager list 4. dispose manager {@link NManager}
	 */
	public void delManager(NManager manager) {
		if (manager == mMainManager)
			return;

		manager.dispose();
	}

	// =================================================
	// native

	private final native long CreateManager();

	final native void DisposeManager(long address);

	/************************************************************** NSpriteDef **************************************************************/

	public NSpriteDef newSpriteDef(String name) {
		final NSpriteDef def = new NSpriteDef(name, CreateSpriteDef(), this);
		mSpriteDefMap.put(name, def);
		return def;
	}

	public NSpriteDef getSpriteDef(String name) {
		return mSpriteDefMap.get(name);
	}

	/** The sprite is dispose only when no sprite use it */
	public void delSpriteDef(String name) {
		mSpriteDefMap.get(name).dispose();
	}

	public void delSpriteDef(NSpriteDef def) {
		def.dispose();
	}

	void deleteSpriteDef(String name) {
		DisposeSpriteDef(mSpriteDefMap.remove(name).address);
	}

	// =================================================
	// native

	private final native long CreateSpriteDef();

	private final native void DisposeSpriteDef(long spriteDefAddress);

	/************************************************************ Collision Processor ************************************************************/
	public void setCollideListener(CollideListener listner) {
		this.collideListener = listner;
	}

	// ============= Collision with object =============
	public void ProcessCollision(NManager manager1, NManager manager2,
			CollideListener listener) {
		this.collideListener = listener;
		processCollision(manager1.address, manager2.address);
	}

	public void ProcessCollision(NManager manager1, NManager manager2) {
		if (collideListener == null)
			return;
		processCollision(manager1.address, manager2.address);
	}

	public void ProcessCollision(NManager manager, CollideListener listener) {
		this.collideListener = listener;
		processCollision(manager.address);
	}

	public void ProcessCollision(NManager manager) {
		if (collideListener == null)
			return;
		processCollision(manager.address);
	}

	// ============= Collision with address =============
	public void ProcessCollision(long manager1, long manager2,
			CollideListener listener) {
		this.collideListener = listener;
		processCollision(manager1, manager2);
	}

	public void ProcessCollision(long manager1, long manager2) {
		if (collideListener == null)
			return;
		processCollision(manager1, manager2);
	}

	public void ProcessCollision(long manager, CollideListener listener) {
		this.collideListener = listener;
		processCollision(manager);
	}

	public void ProcessCollision(long manager) {
		if (collideListener == null)
			return;
		processCollision(manager);
	}

	private void collide(long address1, long address2) {
		collideListener.Collided(mSpriteMap.get(address1),
				mSpriteMap.get(address2));
	}

	// ============= Collision native configuration =============

	public final native void StopCollisionProcessing();

	public final native void ResumeCollisionProcessing();

	public final native void CollisionConfig(int worldX, int worldY,
			int worldWidth, int worldHeight, int cols, int row);

	private final native void processCollision(long manager1, long manager2);

	private final native void processCollision(long manager);

}
