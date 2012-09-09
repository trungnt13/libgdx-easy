package okj.easy.core;

import okj.easy.core.utils.Bridge;
import okj.easy.core.utils.BridgePool;
import okj.easy.screen.IActivityHandler;
import okj.easy.screen.IDesktopHandler;
import okj.easy.screen.LoadingScreen;
import okj.easy.screen.SafeLoader;

import org.ege.utils.E;
import org.ege.utils.EasyNativeLoader;
import org.ege.utils.Timer;
import org.ege.utils.exception.EasyGEngineRuntimeException;
import org.ege.widget.Dialog;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public abstract class GameCore implements ApplicationListener {

	// ===============================================
	// Screen manage

	/**
	 * This screen is the main screen which is visible all time
	 */
	protected Screen					screen;
	/**
	 * when the game is call onPause from activity core save it current screen
	 * to this reference
	 */
	private Screen						mSavedScreen;

	/**
	 * The main loading screen use to load and reload openGL context
	 */
	private LoadingScreen				mStartScreen;
	private boolean						isStarted	= false;

	private Class<? extends SafeLoader>	mDefaultLoader;

	// ==============================================
	// Utils manage

	private final BridgePool			bridgePool;

	private final IActivityHandler		mActivity;

	// ===============================================

	public GameCore (IActivityHandler activity) {
		this.mActivity = activity;

		bridgePool = new BridgePool(13);
		Bridge.registerRecyleListener(bridgePool);
	}

	public GameCore () {
		this(new IDesktopHandler());
	}

	/**
	 * The safe loader is use to change screen in safe mode when you not sure
	 * all contexts are loaded
	 * 
	 * @param yourSafeLoader
	 */
	public void setDefaultSafeLoader (Class<? extends SafeLoader> yourSafeLoader) {
		mDefaultLoader = yourSafeLoader;
	}

	/**************************************************************
	 * 
	 **************************************************************/

	@Override
	public void create () {
		eAdmin.egame = GameCore.this;
		eAdmin.einput = new eInput();
		eAdmin.egraphics = new eGraphics();
		eAdmin.eaudio = new eAudio();
		eAdmin.econtext = new eContext();
		Screen.setCoreProcessor(this);

		EasyNativeLoader.load();
		Timer.instance.reset();

		Gdx.input.setInputProcessor(eAdmin.einput);

		Dialog.DIALOG_NUMBER = 0;
		onGameConfig();
	}

	@Override
	public void resize (int width, int height) {
		eAdmin.egraphics.resolve(width, height);
		if (screen != null)
			screen.resize(width, height);
		onGameChanged(width, height);
	}

	private float	delta;

	@Override
	public void render () {
		delta = Gdx.graphics.getDeltaTime();
		if (screen != null) {
			screen.onRender(delta);
			screen.update(delta);
		}
		onGameRender(delta);
	}

	@Override
	public void pause () {
		onGamePause();

		// save context if you want to reload
		isStarted = false;
		mSavedScreen = screen;

		// call pause
		if (screen != null)
			screen.pause();
	}

	@Override
	public void resume () {
		onGameResume();

		// reload context
		if (!isStarted ) {
			setScreen(mStartScreen.setChangedScreenListener(new LoadingScreen.OnChangedScreen() {
				@Override
				public Screen screenChanged () {
					mSavedScreen.resume();
					return mSavedScreen;
				}
			}), E.screen.HIDE);
			isStarted = true;
			return;
		}

		// call resume
		if (screen != null)
			screen.resume();
	}

	@Override
	public void dispose () {
		if (screen != null)
			screen.destroy(E.screen.RELEASE);
		isStarted = false;
		onGameDestroy();
		this.bridgePool.clear();
	}

	/**************************************************************
	 * 
	 **************************************************************/

	/**
	 * Initialize all game components
	 */
	protected abstract void onGameConfig ();

	/**
	 * Listen to surface size
	 * 
	 * @param width
	 *            the screen width
	 * @param height
	 *            the screen height
	 */
	protected abstract void onGameChanged (int width, int height);

	/**
	 * Render all stuff here
	 * 
	 * @param DeltaTime
	 *            the time each frame repeat
	 */
	protected abstract void onGameRender (float delta);

	/**
	 * Your pause stuff here ,such as: save your preferences
	 */
	public abstract void onGamePause ();

	/**
	 * This method do the same function as resume
	 */
	protected abstract void onGameResume ();

	/**
	 * This method will release memory
	 */
	protected abstract void onGameDestroy ();

	/**********************************************
	 * 
	 **********************************************/

	/**
	 * Set the current screen to the new screen
	 * 
	 * @param screen
	 *            new Screen
	 * @param destroyMode
	 *            destroyMode of old Screen
	 */
	void setScreen (Screen screen, int destroyMode) {
		if (!isStarted)
			if (this.screen != null)
				this.screen.destroy(destroyMode);
		this.screen = screen;
		if (this.screen != null) {
			screen.show();
			screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	void setScreen (Screen screen, SafeLoader loader, ResourcePack pack) {
		if (pack.isTotallyLoaded()) {
			setScreen(screen, E.screen.RELEASE);
			return;
		}
		loader.enableSafeMode(screen, pack);
		setScreen(loader, E.screen.RELEASE);
	}

	void setScreen (Screen screen, SafeLoader loader, ResourceContext... contexts) {
		final ResourcePack pack = new ResourcePack(SafeLoader.name, contexts);
		if (pack.isTotallyLoaded()) {
			setScreen(screen, E.screen.RELEASE);
			return;
		}
		loader.enableSafeMode(screen, pack);
		setScreen(loader, E.screen.RELEASE);
	}

	void setScreen (Screen screen, ResourceContext... contexts) {
		final ResourcePack pack = new ResourcePack(SafeLoader.name, contexts);
		if (pack.isTotallyLoaded()) {
			setScreen(screen, E.screen.RELEASE);
			return;
		}
		if (mDefaultLoader == null)
			throw new EasyGEngineRuntimeException("The default safe loader is null");
		SafeLoader loader;
		try {
			loader = mDefaultLoader.newInstance();
			loader.enableSafeMode(screen, pack);
			setScreen(loader, E.screen.RELEASE);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	void setScreen (Screen screen, ResourcePack pack) {
		if (pack.isTotallyLoaded()) {
			setScreen(screen, E.screen.RELEASE);
			return;
		}
		if (mDefaultLoader == null)
			throw new EasyGEngineRuntimeException("The default safe loader is null");
		SafeLoader loader;
		try {
			loader = mDefaultLoader.newInstance();
			loader.enableSafeMode(screen, pack);
			setScreen(loader, E.screen.RELEASE);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public Screen getCurrentScreen () {
		return screen;
	}

	protected void setStartScreen (LoadingScreen loadScreen) {
		if (isStarted)
			throw new EasyGEngineRuntimeException("One GameCore cant set start screen twice");
		this.mStartScreen = loadScreen;
		setScreen(loadScreen, E.screen.RELEASE);
		isStarted = true;
	}


	/**************************************************************
	 * Bridge Manage method
	 **************************************************************/

	public Bridge newBridge (Class<?> firstClass, Class<?> secondClass) {
		Bridge tmp = getBridge(firstClass, secondClass);
		if (tmp == null)
			return this.bridgePool.obtain(firstClass, secondClass);
		return tmp;
	}

	public Bridge newBridge (String name) {
		Bridge tmp = getBridge(name);
		if (tmp == null)
			return this.bridgePool.obtain(name);
		return tmp;
	}

	private Bridge getBridge (Class<?> firstClass, Class<?> secondClass) {
		return bridgePool.getUsingBridges().get(firstClass.getName()+secondClass.getName());
	}

	private Bridge getBridge (String name) {
		return bridgePool.getUsingBridges().get(name);
	}

	/**************************************************************
	 * 
	 **************************************************************/

	public IActivityHandler getActivity () {
		return mActivity;
	}

	public boolean isBindActivity () {
		return mActivity == null;
	}
}
