package okj.easy.admin;

import java.util.Iterator;

import okj.easy.admin.Art.GraphicsData;

import org.ege.utils.GraphicsHelper;
import org.ege.utils.exception.EasyGEngineRuntimeException;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectMap;

public class eGraphics {
	public static AssetManager manager;
	
	ObjectMap<String, Art> mArtMap = new ObjectMap<String, Art>();
	ObjectMap<String, Class<?>> mUnArtData = new ObjectMap<String, Class<?>>();
	
	TextureAtlas mCurAtlas = null;

	public eGraphics(){
		manager = new AssetManager();
	}
	
	public <T> void load(String assetName,Class<T> clazzType){
		mUnArtData.put(assetName, clazzType);
		manager.load(assetName, clazzType);
	}
	
	public <T> void load(String assetName,Class<T> clazzType,AssetLoaderParameters<T> param){
		mUnArtData.put(assetName, clazzType);
		manager.load(assetName, clazzType,param);
	}
	
	public <T> void unload(String assetName){
		if(mUnArtData.containsKey(assetName))
			unloadDirectly(assetName);
	}
	/**************************************************************
	 * Public art manage method
	 **************************************************************/
	/**
	 * Add a art to manager and also add it to object map for fast query
	 * @param art your art
	 * @return true if art != null and success load art , otherwise false
	 */
	public boolean addArt(Art art){
		if(art != null && !mArtMap.containsValue(art,true)){
			mArtMap.put(art.name(), art);
			return true;
		}
		return false;
	}
	
	public void reloadArt(String artName){
		mArtMap.get(artName).reload();
	}
	
	
	public void unloadArt(String artName){
		mArtMap.get(artName).unload();
	}
	
	/**
	 * Remove all data which contain in the art from manager , even if that data hadn't been loaded(not recommended)
	 * @param art your art
	 */
	public void clearArt(String artName){
		Art art = mArtMap.get(artName);
		unloadArt(art);
		art.mDataMap.clear();
		art.mUnloadedData.clear();
		art.isTotallyUnloaded = false;
	}

	/**
	 * unload the given art and unload all it data from manager
	 * @param art your art
	 * @return true if success removed, otherwise false
	 */
	public void removeArt(String artName){
		mArtMap.get(artName).remove();
	}
	
	/**************************************************************
	 * Private art manage method
	 **************************************************************/
	/**
	 * load the data to manager,just for art
	 * @param data
	 */
	<T> void loadArtData(String linkName,Class<T> clazz) {
		manager.load(linkName, clazz);
	}
	
	/**
	 * load the data to manager,just for art
	 * @param data
	 */
	 <T> void loadArtData(String linkName,Class<T> clazz,AssetLoaderParameters<T> param) {
		manager.load(linkName, clazz,param);
	}
	
	
	
	<T> void loadGraphicsData(String linkName,GraphicsData<T> data){
		if(data.param != null)
			loadArtData(linkName, data.classType, data.param);
		else 
			loadArtData(linkName, data.classType);
	}
	
	void loadArt(Art art){
		Iterator<String> assetList = art.mDataMap.keys();
		String tmp = null;
		while(assetList.hasNext()){
			tmp = assetList.next();
			loadGraphicsData(tmp, art.getGraphicData(tmp));
		}
	}
	
	void unloadArt(Art art){
		Iterator<String> dataName  = art.mDataMap.keys();
		while(dataName.hasNext())
			unloadDirectly(dataName.next());
	}
	
	void removeArt(Art art){
		unloadArt(art);
		mArtMap.remove(art.name);
	}
	
	/**
	 * Directly unload the given data from the manager(carefull when you call this method)
	 * @param data unLoaded data
	 */
	void unloadDirectly(String linkName){
		manager.unload(linkName);
	}
	
	/***************************************************************************
	 *Query
	 ***************************************************************************/
	
	/**
	 * Find your art by its name
	 * @param name art's name
	 * @return found art(maybe null)
	 */
	public Art findArtByName(String name){
		return mArtMap.get(name);
	}
	
	public <T> T findGDataByName(String linkName,Class<T> clazz){
		return manager.get(linkName, clazz);
	}
	
	/***************************************************************************
	 *Get size of data
	 ***************************************************************************/
	
	public int totalData(){
		int size = 0;
		for (Iterator<Art> iterator = mArtMap.values(); iterator.hasNext();) {
			size += iterator.next().size();
		}
		return size;
	}
	
	/***************************************************************************
	 * AssetManager function
	 ***************************************************************************/
	
	/**
	 * Get current loading progress
	 * @return loading percentage (0.0 - 1.0)
	 */
	public synchronized float getProgress(){
		return manager.getProgress();
	}
	
	/**
	 * Get the number of loaded asset
	 * @return loaded assets
	 */
	public synchronized int getLoadedAssets(){
		return manager.getLoadedAssets();
	}
	
	/**
	 * Get the assets which will be load soon
	 * @return number of assets in queue
	 */
	public synchronized int getQueueAssets(){
		return manager.getQueuedAssets();
	}
	
	/**
	 * Clear all asset of the manager
	 */
	void superClear(){
		manager.clear();
	}
	
	/**
	 * Destroy the manager (game down)
	 */
	void dispose(){
		manager.dispose();
	}
	
	/**
	 * if the given data was loaded ...
	 * @param data given data
	 * @return true if it was loaded,otherwise false
	 */
	public boolean isLoaded(String linkName,Class<?> clazz){
		return manager.isLoaded(linkName, clazz);
	}
	
	/**
	 * get the art list which manager is manage
	 * @return the array list of art
	 */
	public Iterator<Art> toArts(){
		return mArtMap.values();
	}
	
	/**
	 * Set your own loader
	 * @param type MyAssetClass.class  ...
	 * @param loader new MyAssetLoader(new InternalFileHandleResolver())  ...
	 */
	public <T, P extends AssetLoaderParameters<T>> void setLoader (Class<T> type, AssetLoader<T, P> loader) {
		manager.setLoader(type, loader);
	}
	
	/**
	 * Call manager update to know loading state
	 * @return true if loading done,otherwise false
	 */
	public boolean update(){
		return manager.update();
	}
	
	/***************************************************************************
	 * Query function
	 ***************************************************************************/
	public void atlasQuery(String name){
		mCurAtlas = manager.get(name, TextureAtlas.class);
	}
	
	public TextureRegion findGRegionByName(String regionName){
		if(mCurAtlas == null)
			throw new EasyGEngineRuntimeException("Plz use eAdmin.egraphics.atlasQuery(...) first");
		return mCurAtlas.findRegion(regionName);
	}

	public TextureRegion findGRegionByName(String regionName,int index){
		if(mCurAtlas == null)
			throw new EasyGEngineRuntimeException("Plz use eAdmin.egraphics.atlasQuery(...) first");
		return mCurAtlas.findRegion(regionName,index);
	}

	public TextureRegion[] findGRegionsByName(String regionName){
		if(mCurAtlas == null)
			throw new EasyGEngineRuntimeException("Plz use eAdmin.egraphics.atlasQuery(...) first");
		return GraphicsHelper.regionConvert(mCurAtlas.findRegions(regionName));
	}
}