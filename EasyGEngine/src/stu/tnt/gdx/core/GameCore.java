
package stu.tnt.gdx.core;

import stu.tnt.gdx.core.Timer.Task;
import stu.tnt.gdx.core.loader.SafeLoader;
import stu.tnt.gdx.core.utils.Bridge;
import stu.tnt.gdx.core.utils.BridgePool;
import stu.tnt.gdx.graphics.graphics2d.NWorld;
import stu.tnt.gdx.screen.LoadingScreen;
import stu.tnt.gdx.utils.E;
import stu.tnt.gdx.utils.EasyNativeLoader;
import stu.tnt.gdx.utils.exception.EasyGEngineRuntimeException;
import stu.tnt.gdx.widget.Dialog;
import stu.tnt.platform.DesktopHandler;
import stu.tnt.platform.IActivityHandler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.PauseableThread;

/**
 * @FileName: GameCore.java
 * @CreateOn: Sep 15, 2012 - 11:05:26 AM
 * @Author: TrungNT
 */
public abstract class GameCore implements ApplicationListener {
	// ============= Screen mangage =============

	/** This screen is the main screen which is visible all time */
	protected Screen screen;
	/**
	 * when the game is call onPause from activity core save it current screen to this reference
	 */
	private Screen mSavedScreen;

	/** The main loading screen use to load and reload openGL context */
	private LoadingScreen mStartScreen;

	/**
	 * This flag show that the game core is come back from pause and now it is resume
	 * <p>
	 * 
	 * @True if is on from running turn to onPause
	 * @False if is on from onPause back to onResume
	 */
	private boolean isStarted = false;

	/** Flag show that the game is pause */
	private boolean isPause = false;

	private Class<? extends SafeLoader> mDefaultLoader;

	// ============= Utils manage =============
	private final BridgePool bridgePool;

	final IActivityHandler mActivity;

	// ============= Schedule code =============
	final Timer mSchedulerTimer;
	final ThreadManager mThreadManager;

	// ============= Runnable for synchoronize schedule =============
	final Array<Runnable> runnables = new Array();
	final Array<Runnable> executedRunnables = new Array();

	/***********************************************************
	 * Constructors
	 * ***********************************************************/

	public GameCore (IActivityHandler activity) {
		this.mActivity = activity;

		bridgePool = new BridgePool(13);
		Bridge.registerRecyleListener(bridgePool);

		mSchedulerTimer = new Timer();
		mThreadManager = new ThreadManager();
	}

	public GameCore () {
		this(new DesktopHandler());
	}

	// ============= default loader method =============
	/**
	 * The safe loader is use to change screen in safe mode when you not sure all contexts are loaded
	 * 
	 * @param yourSafeLoader
	 */
	public void setDefaultSafeLoader (Class<? extends SafeLoader> yourSafeLoader) {
		mDefaultLoader = yourSafeLoader;
	}

	/**************************************************************
	 * ApplicationListener methods
	 * **************************************************************/

	@Override
	public void create () {
		eAdmin.egame = GameCore.this;
		eAdmin.einput = new eInput();
		eAdmin.egraphics = new eGraphics();
		eAdmin.eaudio = new eAudio();
		eAdmin.econtext = new eContext();

		Screen.setCoreProcessor(this);
		Screen.batch = new SpriteBatch();
		Screen.layout = null;

		EasyNativeLoader.load();
		mSchedulerTimer.reset();

		Gdx.input.setInputProcessor(eAdmin.einput);

		Dialog.DIALOG_NUMBER = 0;
		onGameConfig();
	}

	protected void CreateNativeWorld (int size_of_NSprite, int size_of_NSpriteA) {
		eAdmin.eworld = new NWorld(size_of_NSprite, size_of_NSpriteA);
	}

	@Override
	public void resize (int width, int height) {
		eAdmin.egraphics.resolve(width, height);
		if (screen != null) screen.resize(width, height);
		onGameChanged(width, height);
	}

	private float delta;

	@Override
	public void render () {
		if (isPause) return;
		delta = Gdx.graphics.getDeltaTime();

		// ============= Start schedule =============
		synchronized (runnables) {
			executedRunnables.clear();
			executedRunnables.addAll(runnables);
			runnables.clear();

			for (int i = 0; i < executedRunnables.size; i++) {
				try {
					executedRunnables.get(i).run();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}

		// ============= Update screen =============
		if (screen != null) {
			screen.onRender(delta);
			screen.update(delta);
		}
		onGameRender(delta);
	}

	@Override
	public void pause () {
		isPause = true;

		onGamePause();

		// stop scheduler
		mSchedulerTimer.stop();

		// save context if you want to reload
		isStarted = false;
		mSavedScreen = screen;

		// call pause
		if (screen != null) screen.pause();
	}

	@Override
	public void resume () {
		isPause = false;
		onGameResume();

		// reload context
		if (!isStarted) {
			screen = mStartScreen;
			mStartScreen.setChangedScreenListener(new LoadingScreen.OnChangedScreen() {
				@Override
				public Screen screenChanged () {
					// re-start schedule
					mSchedulerTimer.start();

					return mSavedScreen;
				}
			});
			mStartScreen.show();
			mStartScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			isStarted = true;
			return;
		}
	}

	@Override
	public void dispose () {
		if (screen != null) screen.destroy(E.screen.RELEASE);
		isStarted = false;

		onGameDestroy();

		// ------ clear all list ------
		this.bridgePool.clear();
		runnables.clear();
		executedRunnables.clear();

		// ------ dispose native ------
		eAdmin.eworld.dispose();
	}

	/**************************************************************
	 * Main GameCore methods
	 * **************************************************************/

	/** Config all game components */
	protected abstract void onGameConfig ();

	/**
	 * Listen to surface size
	 * 
	 * @param width the screen width
	 * @param height the screen height
	 */
	protected abstract void onGameChanged (int width, int height);

	/**
	 * Render all stuff here
	 * 
	 * @param DeltaTime the time each frame repeat
	 */
	protected abstract void onGameRender (float delta);

	/** Your pause stuff here ,such as: save your preferences */
	public abstract void onGamePause ();

	/** This method do the same function as resume */
	protected abstract void onGameResume ();

	/** This method will release memory */
	protected abstract void onGameDestroy ();

	/**********************************************
	 * Screen changing methods
	 * **********************************************/
	private boolean isLoading = false;

	protected void setStartScreen (LoadingScreen loadScreen) {
		if (isStarted) throw new EasyGEngineRuntimeException("One GameCore cant set start screen twice");
		this.mStartScreen = loadScreen;
		setScreen(loadScreen, E.screen.RELEASE);
		isStarted = true;
	}

	/**
	 * Set the current screen to the new screen
	 * 
	 * @param screen new Screen
	 * @param destroyMode destroyMode of old Screen
	 */
	void setScreen (Screen screen, int destroyMode) {
		/*
		 * check if the current screen is the loading screen , if is the loading screen, the show method of next screen only call
		 * for the first time load
		 */
		isLoading = (this.screen instanceof LoadingScreen);
		if (isLoading) isLoading = isLoading & !((LoadingScreen)this.screen).isFirstTimeLoad();

		if (this.screen != null) this.screen.destroy(destroyMode);
		this.screen = screen;
		if (this.screen != null) {
			if (!isLoading)
				screen.show();
			else
				mSavedScreen.resume();
			screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	void setScreen (Screen screen, ResourceContext... contexts) {
		final ResourcePack pack = new ResourcePack(SafeLoader.name, contexts);
		if (pack.isTotallyLoaded()) {
			setScreen(screen, E.screen.RELEASE);
			return;
		}
		if (mDefaultLoader == null) throw new EasyGEngineRuntimeException("The default safe loader is null");
		SafeLoader loader;
		try {
			loader = mDefaultLoader.newInstance();
			loader.setLoaderInfo(screen, pack);
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
		if (mDefaultLoader == null) throw new EasyGEngineRuntimeException("The default safe loader is null");
		SafeLoader loader;
		try {
			loader = mDefaultLoader.newInstance();
			loader.setLoaderInfo(screen, pack);
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

	/**************************************************************
	 * Bridge Manage method
	 * **************************************************************/

	public Bridge newBridge (Class<?> firstClass, Class<?> secondClass) {
		Bridge tmp = getBridge(firstClass, secondClass);
		if (tmp == null) return this.bridgePool.obtain(firstClass, secondClass);
		return tmp;
	}

	public Bridge newBridge (String name) {
		Bridge tmp = getBridge(name);
		if (tmp == null) return this.bridgePool.obtain(name);
		return tmp;
	}

	private Bridge getBridge (Class<?> firstClass, Class<?> secondClass) {
		return bridgePool.getUsingBridges().get(firstClass.getName() + secondClass.getName());
	}

	private Bridge getBridge (String name) {
		return bridgePool.getUsingBridges().get(name);
	}

	/**************************************************************
	 * Activity handler methods
	 * **************************************************************/

	public IActivityHandler getActivity () {
		return mActivity;
	}

	public boolean isBindActivity () {
		return mActivity == null;
	}

	/**************************************************************
	 * Schedule method for timer
	 * **************************************************************/

	public void postTask (Task task) {
		mSchedulerTimer.postTask(task);
	}

	public void schedule (Task task) {
		mSchedulerTimer.scheduleTask(task);
	}

	public void schedule (Task task, float delaySeconds) {
		mSchedulerTimer.scheduleTask(task, delaySeconds);
	}

	public void schedule (Task task, float delaySeconds, float intervalSeconds) {
		mSchedulerTimer.scheduleTask(task, delaySeconds, intervalSeconds);
	}

	public void schedule (Task task, float delaySeconds, float intervalSeconds, int repeatCount) {
		mSchedulerTimer.scheduleTask(task, delaySeconds, intervalSeconds, repeatCount);
	}

	public void stopScheduler () {
		mSchedulerTimer.stop();
	}

	public void startScheduler () {
		mSchedulerTimer.start();
	}

	public void clearScheduler () {
		mSchedulerTimer.clear();
	}

	public void removeTask (Task task) {
		mSchedulerTimer.removeTask(task);
	}

	public void postRunnable (Runnable runnable) {
		synchronized (runnables) {
			runnables.add(runnable);
			Gdx.graphics.requestRendering();
		}
	}

	/**************************************************************
	 * Thread manager
	 * **************************************************************/

	public int newThreadId (Runnable runnable) {
		return mThreadManager.obtainForID(runnable);
	}

	public PauseableThread newThread (Runnable runnable) {
		return mThreadManager.obtainForThread(runnable);
	}

	public boolean startThread (int id) {
		return mThreadManager.startThread(id);
	}

	public boolean stopThread (int id) {
		return mThreadManager.stopThread(id);
	}

	/**
	 * Pause a given thread which have your id
	 * 
	 * @param id
	 * @return true if successful pause , otherwise false
	 */
	public boolean pauseThread (int id) {
		return mThreadManager.pauseThread(id);
	}

	/**
	 * Resume a given thread which have your id
	 * 
	 * @param id
	 * @return true if successful resume , otherwise false
	 */
	public boolean resumeThread (int id) {
		return mThreadManager.resumeThread(id);
	}

	public boolean containThread (int id) {
		return mThreadManager.containThread(id);
	}

	public boolean containThread (PauseableThread thread) {
		return mThreadManager.containThread(thread);
	}

	public int sizeOfThread () {
		return mThreadManager.size();
	}

	public int sizeOfRunningThread () {
		return mThreadManager.sizeOfAlive();
	}
}
