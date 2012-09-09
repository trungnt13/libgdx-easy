package okj.easy.core;

import okj.easy.core.utils.Bridge;
import okj.easy.screen.SafeLoader;

import org.ege.utils.E;
import org.ege.widget.Layout;
import org.ege.widget.StyleAtlas;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public abstract class Screen implements ApplicationContext {
	protected static GameCore	mGameCore;

	public final SpriteBatch	batch		= new SpriteBatch();
	private Layout				layout		= null;

	public final Matrix4		projection	= new Matrix4();
	public final Matrix4		transform	= new Matrix4();

	public final Matrix4		tmpMat		= new Matrix4();

	static void setCoreProcessor (GameCore core) {
		mGameCore = core;
	}

	/** Called when this screen becomes the current screen for a {@link Game}. */
	public abstract void show ();

	/** @see ApplicationListener#resize(int, int) */
	public void resize (int width, int height) {
		eAdmin.apply(batch);
		if (layout != null)
			eAdmin.apply(layout);
	}

	/**
	 * Refresh all screen component
	 * 
	 * @param delta
	 *            The time in seconds since the last render.
	 */
	public void update (float delta) {
		if (layout != null)
			layout.act(delta);
	}

	/**
	 * Called when the screen should render itself.
	 * 
	 * @param delta
	 *            The time in seconds since the last render.
	 */
	public abstract void onRender (float delta);

	/** @see ApplicationListener#pause() */
	public void pause () {
		if (layout != null)
			layout.Pause();
	}

	/** @see ApplicationListener#resume() */
	public void resume () {
		if (layout != null)
			layout.Resume();
	}

	/**
	 * Call when user change current scree
	 * 
	 * @param destroyMode
	 *            if RELEASE the layout will be clear and batch is flush,else if
	 *            HIDE the pause() will be call
	 */
	public void destroy (int destroyMode) {
		if (destroyMode == E.screen.HIDE) {
			pause();
			return;
		}
		eAdmin.einput.unregisterBackKeyListener();
		if (layout != null)
			layout.clear();

		batch.flush();
	}

	/**************************************************************************
	 * Layout manager
	 **************************************************************************/

	public Layout getScreenLayout () {
		if (layout == null)
			layout = new Layout(false, batch);
		eAdmin.einput.addProcessor(layout.ID, layout);
		layout.setToDefault();
		return layout;
	}

	public void drawLayout () {
		layout.draw();
	}

	/************************************************************
	 * Bridge method
	 ************************************************************/

	public Bridge newBridge (Class<?> firstClass, Class<?> secondClass) {
		return mGameCore.newBridge(firstClass, secondClass);
	}

	public Bridge newBridge (String name) {
		return mGameCore.newBridge(name);
	}

	/************************************************************
	 * Screen transistion method
	 ************************************************************/

	/**
	 * Set screen which lead to the new screen and the destroy mode of old
	 * screen
	 * 
	 * @param screen
	 *            new screen
	 * @param destroyMode
	 *            destroy mode(RELEASE for totally destroy , HIDE -just make it
	 *            invisible)
	 */
	protected void setScreen (Screen screen, int destroyMode) {
		mGameCore.setScreen(screen, destroyMode);
	}

	/**
	 * set screen in SafeMode which will ensure that all context associate with
	 * that screen will be loaded
	 */
	protected void setScreen (Screen screen, SafeLoader loader, ResourcePack pack) {
		mGameCore.setScreen(screen, loader, pack);
	}

	/**
	 * set screen in SafeMode which will ensure that all context associate with
	 * that screen will be loaded
	 */
	protected void setScreen (Screen screen, SafeLoader loader, ResourceContext... contexts) {
		mGameCore.setScreen(screen, loader, contexts);
	}

	/**
	 * set screen in SafeMode which will ensure that all context associate with
	 * that screen will be loaded
	 */
	protected void setScreen (Screen screen, ResourceContext... contexts) {
		mGameCore.setScreen(screen, contexts);
	}

	/**
	 * set screen in SafeMode which will ensure that all context associate with
	 * that screen will be loaded
	 */
	protected void setScreen (Screen screen, ResourcePack pack) {
		mGameCore.setScreen(screen, pack);
	}

	/***************************************************************************
	 * eInput Method
	 **************************************************************************/

	public final InputProcessor findInputById (int id) {
		return eAdmin.einput.findInputById(id);
	}

	/***************************************************************************
	 * eContext Method
	 **************************************************************************/

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
		if (name == null)
			throw new NullPointerException("Context name cant be null");
		return eAdmin.econtext.findContextByName(name);
	}

	public final <T> T findDataByName (String linkName, Class<T> clazz) {
		if (linkName == null)
			throw new NullPointerException("LinkName cant be null");
		return eAdmin.econtext.findDataByName(linkName, clazz);
	}

	public final void unloadContext (String artName) {
		eAdmin.econtext.unloadContext(artName);
	}

	public final void clearContext (String artName) {
		eAdmin.econtext.clearContext(artName);
	}

	public final void removeContext (String artName) {
		eAdmin.econtext.removeContext(artName);
	}

	public final void reloadContext (String artName) {
		eAdmin.econtext.reloadContext(artName);
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

	/***************************************************************************
	 * eAudio Method
	 **************************************************************************/

	public final Album findAlbumByName (String name) {
		if (name != null)
			return eAdmin.eaudio.findAlbumByName(name);
		return null;
	}

	public final int totalAData () {
		return eAdmin.eaudio.totalAData();
	}

	public final void addAlbum (Album album) {
		eAdmin.eaudio.addAlbum(album);
	}

	public final void unloadAlbum (String albumName) {
		eAdmin.eaudio.unloadAlbum(albumName);
	}

	public final void removeAlbum (String albumName) {
		eAdmin.eaudio.removeAlbum(albumName);
	}

	public final void reloadAlbum (String albumName) {
		eAdmin.eaudio.reloadAlbum(albumName);
	}

	public final void clearAlbum (String albumName) {
		eAdmin.eaudio.clearAlbum(albumName);
	}

	/***************************************************************************
	 * Quick GL Method
	 **************************************************************************/

	public final void glClear (int glmask) {
		Gdx.gl.glClear(glmask);
	}

	public final void glClearColor (float r, float g, float b, float a) {
		Gdx.gl.glClearColor(r, g, b, a);
	}

	public final void disableBlend () {
		Gdx.gl.glDisable(GL10.GL_BLEND);
	}

	public final void enableBlend () {
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}

	/***************************************************************************
	 * Quick Memory Method
	 **************************************************************************/

	public final void release (Vector2 v) {
		v = null;
	}

}
