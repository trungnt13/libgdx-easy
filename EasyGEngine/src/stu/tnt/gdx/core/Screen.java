
package stu.tnt.gdx.core;

import stu.tnt.gdx.core.Timer.Task;
import stu.tnt.gdx.core.loader.Album;
import stu.tnt.gdx.core.loader.Context;
import stu.tnt.gdx.core.utils.Bridge;
import stu.tnt.gdx.utils.D;
import stu.tnt.gdx.utils.E;
import stu.tnt.gdx.widget.Layout;
import stu.tnt.gdx.widget.StyleAtlas;
import stu.tnt.platform.IActivityHandler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.PauseableThread;

/** @FileName: Screen.java
 * @CreateOn: Sep 15, 2012 - 11:11:22 AM
 * @Author: TrungNT */
public abstract class Screen implements ApplicationContext {
	protected static GameCore mGameCore;

	public static SpriteBatch batch = null;
	private SpriteCache cache = null;
	static Layout layout = null;

	public final Matrix4 projection = new Matrix4();
	public final Matrix4 transform = new Matrix4();

	public final Matrix4 tmpMat = new Matrix4();

	static void setCoreProcessor (GameCore core) {
		mGameCore = core;
	}

	/** Called when this screen becomes the current screen for a {@link Game}. */
	public abstract void show ();

	/** @see ApplicationListener#resize(int, int) */
	public void resize (int width, int height) {
		eAdmin.apply(batch);
		if (layout != null) eAdmin.apply(layout);
		if (cache != null) eAdmin.apply(cache);
	}

	/** Refresh all screen component
	 * 
	 * @param delta The time in seconds since the last render. */
	public void update (float delta) {
		if (layout != null) layout.act(delta);
	}

	/** Called when the screen should render itself.
	 * 
	 * @param delta The time in seconds since the last render. */
	public abstract void onRender (float delta);

	/** @see ApplicationListener#pause() */
	public void pause () {
		if (layout != null) layout.Pause();
	}

	/** @see ApplicationListener#resume() */
	public void resume () {
		if (layout != null) layout.Resume();
	}

	/** Call when user change current scree
	 * 
	 * @param destroyMode if RELEASE the layout will be clear and batch is flush,else if HIDE the pause() will be call */
	public void destroy (int destroyMode) {
		if (destroyMode == E.screen.HIDE) {
			pause();
			return;
		}
		eAdmin.einput.unregisterBackKeyListener();

		if (layout != null) layout.clear();
		if (cache != null) cache.clear();

		batch.flush();
	}

	/************************************************************************** Layout manager **************************************************************************/

	public Layout getScreenLayout () {
		if (layout == null) layout = new Layout(false);
		eAdmin.einput.addProcessor(layout.ID, layout);
		return layout;
	}

	public void drawLayout () {
		D.out("Layout " + layout.getViewport().getCamera());
		layout.draw();
	}

	public boolean isLayoutCreated () {
		return layout != null;
	}

	/************************************************************ Screen transistion method ************************************************************/

	/** Set screen which lead to the new screen and the destroy mode of old screen
	 * 
	 * @param screen new screen
	 * @param destroyMode destroy mode(RELEASE for totally destroy , HIDE -just make it invisible) */
	protected void setScreen (Screen screen, int destroyMode) {
		mGameCore.setScreen(screen, destroyMode);
	}

	/** set screen in SafeMode which will ensure that all context associate with that screen will be loaded */
	protected void setScreen (Screen screen, ResourceContext... contexts) {
		mGameCore.setScreen(screen, contexts);
	}

	/** set screen in SafeMode which will ensure that all context associate with that screen will be loaded */
	protected void setScreen (Screen screen, ResourcePack pack) {
		mGameCore.setScreen(screen, pack);
	}

	/*************************************************************************** eInput Method **************************************************************************/

	public final InputProcessor findInputById (int id) {
		return eAdmin.einput.findInputById(id);
	}

	/*************************************************************************** eContext Method **************************************************************************/

	public final StyleAtlas styleQuery (String name) {
		return eAdmin.econtext.styleQuery(name);
	}

	public final <T> T getStyle (String styleName, Class<T> type) {
		return eAdmin.econtext.getStyle(styleName, type);
	}

	public final <T> T optional (String resourceName, Class<T> type) {
		return eAdmin.econtext.optional(resourceName, type);
	}

	public final StyleAtlas getStyleAtlas (String name) {
		return eAdmin.econtext.getStyleAtlas(name);
	}

	public final void stopStyleQuery () {
		eAdmin.econtext.stopQuery();
	}

	/* --------------------------------------------- */

	public final Context findContextByName (String name) {
		if (name == null) throw new NullPointerException("Context name cant be null");
		return eAdmin.econtext.findContextByName(name);
	}

	public final <T> T findDataByName (String linkName, Class<T> clazz) {
		if (linkName == null) throw new NullPointerException("LinkName cant be null");
		return eAdmin.econtext.findDataByName(linkName, clazz);
	}

	/* -------------------------------------------- */

	public final TextureAtlas atlasQuery (String name) {
		return eAdmin.econtext.atlasQuery(name);
	}

	public final TextureRegion findGRegionByName (String name) {
		return eAdmin.econtext.findGRegionByName(name);
	}

	public final TextureRegion findGRegionByName (String name, int index) {
		return eAdmin.econtext.findGRegionByName(name, index);
	}

	public final TextureRegion[] findGRegionsByName (String name) {
		return eAdmin.econtext.findGRegionsByName(name);
	}

	/* -------------------------------------------------------- */

	public final <T> void load (String linkName, Class<T> clazz) {
		eAdmin.econtext.load(linkName, clazz);
	}

	public final <T> void load (String linkName, Class<T> clazz, AssetLoaderParameters<T> param) {
		eAdmin.econtext.load(linkName, clazz, param);
	}

	public final <T> T get (String fileName, Class<T> clazz) {
		return eAdmin.econtext.get(fileName, clazz);
	}

	public boolean isLoaded (String linkName, Class<?> clazz) {
		return eAdmin.econtext.isLoaded(linkName, clazz);
	}

	public final <T> void unload (String linkName) {
		eAdmin.econtext.unload(linkName);
	}

	public final int totalGData () {
		return eAdmin.econtext.totalData();
	}

	/*************************************************************************** eAudio Method **************************************************************************/

	public final Album findAlbumByName (String name) {
		if (name != null) return eAdmin.eaudio.findAlbumByName(name);
		return null;
	}

	public final int totalAData () {
		return eAdmin.eaudio.totalAData();
	}

	/*************************************************************************** Quick GL Method **************************************************************************/

	public final void glClear (int glmask) {
		Gdx.gl.glClear(glmask);
	}

	public final void glClearColor (float r, float g, float b, float a) {
		Gdx.gl.glClearColor(r, g, b, a);
	}

	public final void disableBlend () {
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	public final void enableBlend () {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}

	/*************************************************************************** Quick Memory Method **************************************************************************/

	public final void release (Vector2 v) {
		v = null;
	}

	/*************************************************************************** GameCore method **************************************************************************/

	public Bridge newBridge (Class<?> firstClass, Class<?> secondClass) {
		return mGameCore.newBridge(firstClass, secondClass);
	}

	public Bridge newBridge (String name) {
		return mGameCore.newBridge(name);
	}

	@Override
	public IActivityHandler getActivity () {
		return mGameCore.mActivity;
	}

	@Override
	public void post (Task task) {
		mGameCore.mSchedulerTimer.postTask(task);
	}

	@Override
	public void schedule (Task task) {
		mGameCore.mSchedulerTimer.scheduleTask(task);
	}

	@Override
	public void schedule (Task task, float delaySeconds) {
		mGameCore.mSchedulerTimer.scheduleTask(task, delaySeconds);
	}

	public void schedule (int fps, Task task) {
		mGameCore.mSchedulerTimer.scheduleTask(fps, task);
	}

	@Override
	public void schedule (Task task, float delaySeconds, float intervalSeconds) {
		mGameCore.mSchedulerTimer.scheduleTask(task, delaySeconds, intervalSeconds);
	}

	@Override
	public void schedule (Task task, float delaySeconds, float intervalSeconds, int repeatCount) {
		mGameCore.mSchedulerTimer.scheduleTask(task, delaySeconds, intervalSeconds, repeatCount);
	}

	@Override
	public int newThreadId (Runnable runnable) {
		return mGameCore.mThreadManager.obtainForID(runnable);
	}

	@Override
	public PauseableThread newThread (Runnable runnable) {
		return mGameCore.mThreadManager.obtainForThread(runnable);
	}

	@Override
	public boolean startThread (int id) {
		return mGameCore.mThreadManager.startThread(id);
	}

	@Override
	public boolean stopThread (int id) {
		return mGameCore.mThreadManager.stopThread(id);
	}

	@Override
	public boolean pauseThread (int id) {
		return mGameCore.mThreadManager.pauseThread(id);
	}

	@Override
	public boolean resumeThread (int id) {
		return mGameCore.mThreadManager.resumeThread(id);
	}

	@Override
	public boolean containThread (int id) {
		return mGameCore.mThreadManager.containThread(id);
	}

	@Override
	public boolean containThread (PauseableThread thread) {
		return mGameCore.mThreadManager.containThread(thread);
	}

	/************************************************
	 * 
	 ************************************************/

	public SpriteCache getSpriteCache (int size) {
		if (cache == null) {
			cache = new SpriteCache(size, true);
			eAdmin.apply(cache);
		}
		return cache;
	}

	public SpriteCache getSpriteCache () {
		if (cache == null) {
			cache = new SpriteCache(100, true);
			eAdmin.apply(cache);
		}
		return cache;
	}

	public Matrix4 getTransformationMatrix () {
		return transform;
	}

	public Matrix4 getProjectionMatrix () {
		return projection;
	}
}
