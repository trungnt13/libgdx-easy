package okj.easy.core;

import okj.easy.admin.Album;
import okj.easy.admin.Context;

import org.ege.widget.StyleAtlas;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface ApplicationContext {
	/***************************************************************************
	 * eInput Method
	 **************************************************************************/
	
	public InputProcessor findInputById(int id);
	
	/***************************************************************************
	 * eContext Method
	 **************************************************************************/
	
	public StyleAtlas styleQuery(String name);
	
	public <T> T getStyle(String styleName,Class<T> type);

	public <T> T optional(String resourceName,Class<T> type);
	
	public StyleAtlas getQueryStyle();
	
	public StyleAtlas getStyleAtlas(String  name);
	
	public void stopStyleQuery();
	
	/*	---------------------------------------------	*/
	
	public Context findContextByName(String name);
	
	public <T> T findDataByName(String linkName,Class<T> clazz);
	
	public void unloadContext(String artName);
	
	public void clearContext(String artName);
	
	public void removeContext(String artName);
	
	public void reloadContext(String artName);
	
	/*	--------------------------------------------	*/
	public TextureAtlas atlasQuery(String name);
	
	public TextureRegion findGRegionByName(String name);

	public TextureRegion findGRegionByName(String name,int index);

	public TextureRegion[] findGRegionsByName(String name);

	/*	--------------------------------------------------------	*/
	
	public <T> void load(String linkName,Class<T> clazz);
	
	public <T> void load(String linkName,Class<T> clazz,AssetLoaderParameters<T> param);
	
	public boolean isLoaded(String linkName,Class<?> clazz);
	
	public <T> void unload(String linkName);
	
	public int totalGData();
	
	/***************************************************************************
	 * eAudio Method
	 **************************************************************************/
	
	public Album findAlbumByName(String name);
	
	public int totalAData();

	public void addAlbum(Album album);
	
	public void unloadAlbum(String albumName);
	
	public void removeAlbum(String albumName);
	
	public void reloadAlbum(String albumName);
	
	public void clearAlbum(String albumName);
	
	/***************************************************************************
	 * Quick GL Method
	 **************************************************************************/
	
	public void glClear(int glmask);
	
	public void glClearColor(float r,float g,float b,float a);

}
