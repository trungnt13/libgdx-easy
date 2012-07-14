package okj.easy.core;

import okj.easy.admin.Album;
import okj.easy.admin.Art;
import okj.easy.admin.eAdmin;
import okj.easy.core.utils.Bridge;

import org.ege.widget.StyleAtlas;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public abstract class Screen{
	public static final SpriteBatch batch = new SpriteBatch(); 
	public static final SpriteCache cache = new SpriteCache();
	
	public static final Matrix4 projection = new Matrix4();
	public static final Matrix4 transform = new Matrix4();

	public static final Matrix4 zero = new Matrix4();
	
	public static final int RELEASE = 1;
	public static final int HIDE = 2;

	protected static int SCREEN_WIDTH;
	protected static int SCREEN_HEIGHT;
	
	Bridge mBridge;
	
	/** Called when the screen should render itself.
	 * @param delta The time in seconds since the last render. */
	public abstract void render (float delta);

	/** Called when this screen becomes the current screen for a {@link Game}. */
	public abstract void show (GameCore gamecore);
	/**
	 * Refresh all screen component
	 * @param delta The time in seconds since the last render.
	 */
	public abstract void update(float delta);
	
	/** @see ApplicationListener#resize(int, int) */
	public abstract void resize (int width, int height);


	/** @see ApplicationListener#pause() */
	public abstract void pause ();

	/** @see ApplicationListener#resume() */
	public abstract void resume ();

	/** Called when this screen should release all resources. */
	public abstract void destroy (int destroyMode);
	
	/***************************************************************************
	 * eInput Method
	 **************************************************************************/
	
	public final InputProcessor findInputById(int id){
		return eAdmin.einput.findInputById(id);
	}
	
	/***************************************************************************
	 * eContext Method
	 **************************************************************************/
	
	public final StyleAtlas styleQuery(String name){
		return eAdmin.econtext.atlasQuery(name);
	}
	
	public final <T> T get(String styleName,Class<T> type){
		return eAdmin.econtext.get(styleName, type);
	}

	public final <T> T optional(String resourceName,Class<T> type){
		return eAdmin.econtext.optional(resourceName, type);
	}
	
	public final StyleAtlas getQueryStyle(){
		return eAdmin.econtext.getQueryStyle();
	}
	
	public final StyleAtlas getStyle(String  name){
		return eAdmin.econtext.getStyle(name);
	}
	
	public final void stopStyleQuery(){
		eAdmin.econtext.stopQuery();
	}
	
	/***************************************************************************
	 * eGraphics Method
	 **************************************************************************/
	public final Art findArtByName(String name){
		if(name == null)
			throw new NullPointerException("Art name cant be null");
		return eAdmin.egraphics.findArtByName(name);
	}
	
	public final <T> T findGDataByName(String linkName,Class<T> clazz){
		if(linkName == null)
			throw new NullPointerException("LinkName cant be null");
		return eAdmin.egraphics.findGDataByName(linkName,clazz);
	}
	
	public final void atlasQuery(String name){
		eAdmin.egraphics.atlasQuery(name);
	}
	
	public final TextureRegion findGRegionByName(String name){
		return eAdmin.egraphics.findGRegionByName(name);
	}

	public final TextureRegion findGRegionByName(String name,int index){
		return eAdmin.egraphics.findGRegionByName(name,index);
	}

	public final TextureRegion[] findGRegionsByName(String name){
		return eAdmin.egraphics.findGRegionsByName(name);
	}

	public final void addArt(Art art){
		eAdmin.egraphics.addArt(art);
	}
	
	public final void unloadArt(String artName){
		eAdmin.egraphics.unloadArt(artName);
	}
	
	public final void clearArt(String artName){
		eAdmin.egraphics.clearArt(artName);
	}
	
	public final void removeArt(String artName){
		eAdmin.egraphics.removeArt(artName);
	}
	
	public final void reloadArt(String artName){
		eAdmin.egraphics.reloadArt(artName);
	}
	/*	--------------------------------------------------------	*/
	
	public final <T> void load(String linkName,Class<T> clazz){
		eAdmin.egraphics.load(linkName,clazz);
	}
	
	public final <T> void load(String linkName,Class<T> clazz,AssetLoaderParameters<T> param){
		eAdmin.egraphics.load(linkName,clazz,param);
	}
	
	public boolean isLoaded(String linkName,Class<?> clazz){
		return eAdmin.egraphics.isLoaded(linkName, clazz);
	}
	
	public final <T> void unload(String linkName){
		eAdmin.egraphics.unload(linkName);
	}
	
	public final int totalGData(){
		return eAdmin.egraphics.totalData();
	}
	
	/***************************************************************************
	 * eAudio Method
	 **************************************************************************/
	
	public final Album findAlbumByName(String name){
		if(name != null)
			return eAdmin.eaudio.findAlbumByName(name);
		return null;
	}
	
	public final int totalAData(){
		return eAdmin.eaudio.totalAData();
	}

	public final void addAlbum(Album album){
		eAdmin.eaudio.addAlbum(album);
	}
	
	public final void unloadAlbum(String albumName){
		eAdmin.eaudio.unloadAlbum(albumName);
	}
	
	public final void removeAlbum(String albumName){
		eAdmin.eaudio.removeAlbum(albumName);
	}
	
	public final void reloadAlbum(String albumName){
		eAdmin.eaudio.reloadAlbum(albumName);
	}
	
	public final void clearAlbum(String albumName){
		eAdmin.eaudio.clearAlbum(albumName);
	}
	
	/***************************************************************************
	 * Quick GL Method
	 **************************************************************************/
	
	public final void glClear(int glmask){
		Gdx.gl.glClear(glmask);
	}
	
	public final void glClearColor(float r,float g,float b,float a){
		Gdx.gl.glClearColor(r, g, b, a);
	}
	
	/***************************************************************************
	 * Quick Memory Method
	 **************************************************************************/

	public final void release (Vector2 v) {
		v = null;
	}
}
