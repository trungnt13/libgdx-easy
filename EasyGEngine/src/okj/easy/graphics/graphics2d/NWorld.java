package okj.easy.graphics.graphics2d;

import org.ege.utils.Factory;
import org.ege.utils.Pool;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.LongMap;

public class NWorld {
	private final LongMap<NSprite> mSpriteMap = new LongMap<NSprite>(100);

	private final Pool<NSprite> mSpritePool = new Pool<NSprite>(50,
			new Factory<NSprite>() {
				@Override
				public NSprite newObject () {
					return createNSprite();
				}

				@Override
				public NSprite newObject (Object... objects) {
					// TODO Auto-generated method stub
					return null;
				}
			});

	// =============================================
	// manager management

	private final NManager mMainManager = newManager();
	private final LongMap<NManager> mManagerMap = new LongMap<NManager>(3);

	/************************************************************
	 * Sprite associate method
	 ************************************************************/

	private final NSprite createNSprite () {
		final NSprite sprite = new NSprite(CreateNSprite(), this, mMainManager);
		return sprite;
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

	public void deleteManager (long address) {
		if (address == mMainManager.address)
			return;
		directDeleteManager(mManagerMap.remove(address));
	}

	private final void directDeleteManager (NManager manager) {
		if (manager == null)
			return;

		final Array<NSprite> list = manager.mSpriteList;
		for (NSprite sprite : list) {
			sprite.dispose();
		}
		list.clear();

		DisposeManager(manager.address);
	}

	// =================================================
	// native
	private final native long CreateManager ();

	private final native void DisposeManager (long address);
}
