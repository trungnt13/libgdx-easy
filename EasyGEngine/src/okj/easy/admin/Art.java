package okj.easy.admin;

import java.util.Iterator;

import org.ege.utils.ArrayDeque;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.utils.ObjectMap;

public class Art {
	
	protected final String name;
	
	final ObjectMap<String, GraphicsData<?>> mDataMap = new ObjectMap<String,  GraphicsData<?>>();
	final ArrayDeque<String> mUnloadedData = new ArrayDeque<String>();
	
	boolean isTotallyUnloaded = false;
	
	public Art(String name){
		this.name = name;
		eAdmin.egraphics.addArt(this);
	}
	
	public String name(){
		return this.name;
	}
	
	/*************************************************************
	 * Data manage method
	 *************************************************************/
	
	/**
	 * Prepend an array of data to data list
	 * @param data data's array
	 */
	public <T> void load(String linkName,Class<T> clazz){
		mDataMap.put(linkName, new GraphicsData<T>(clazz, null));
		eAdmin.egraphics.loadArtData(linkName, clazz);
	}
	
	/**
	 * Prepend an array of data to data list
	 * @param data data's array
	 */
	public <T> void load(String linkName,Class<T> clazz,AssetLoaderParameters<T> param){
		mDataMap.put(linkName,new GraphicsData<T>(clazz, param));
		eAdmin.egraphics.loadArtData(linkName, clazz,param);
	}
	
	public void reload(){
		if(isTotallyUnloaded){
			eAdmin.egraphics.loadArt(this);
			isTotallyUnloaded = false;
		}else{
			String tmp = null;
			while(mUnloadedData.size() > 0){
				tmp = mUnloadedData.poll();
				eAdmin.egraphics.loadGraphicsData(tmp, mDataMap.get(tmp));
			}
		}
	}
	
	/**
	 * remove the given data
	 * @param data the data you want
	 */
	public void unload(){
		if(isTotallyUnloaded)
			return;
		eAdmin.egraphics.unloadArt(this);
		mUnloadedData.clear();
		isTotallyUnloaded = true;
	}
	
	public void unload(String...linkName){
		if(isTotallyUnloaded)
			return;
		for(String s:linkName){
			if(eAdmin.egraphics.isLoaded(s, mDataMap.get(s).classType)){
				mUnloadedData.add(s);
				eAdmin.egraphics.unloadDirectly(s);
			}
		}
	}
	
	public void remove(){
		eAdmin.egraphics.removeArt(this);
		mDataMap.clear();
		mUnloadedData.clear();
		isTotallyUnloaded = false;
	}
	
	public void remove(String...listName){
		for(String linkName:listName){
			mDataMap.remove(linkName);
			mUnloadedData.remove(linkName);
			eAdmin.egraphics.unloadDirectly(linkName);
		}
	}
	
	/**
	 * Ask for data is contaning in the list
	 * @param data the given data
	 * @return true if list contain data,otherwise false
	 */
	public boolean contain(String assetName){
		return mDataMap.containsKey(assetName);
	}
	
	public boolean isUnloaded(String assetName){
		return mUnloadedData.contains(assetName);
	}
	
	public boolean isLoaded(String assetName){
		return eAdmin.egraphics.isLoaded(assetName, mDataMap.get(assetName).classType);
	}
	
	public boolean isTotallyUnloaded(){
		return isTotallyUnloaded;
	}
	
	GraphicsData<?> getGraphicData(String assetName){
		return mDataMap.get(assetName);
	}
	
	/**
	 * Get the size of data list 
	 * @return data list's size
	 */
	public int size(){
		return mDataMap.size;
	}
	
	public int unloadedSize(){
		return mUnloadedData.size();
	}
	
	/**
	 * If this art have 1 asset have not been loaded , it will be false
	 * @return true if all assets was loaded, false otherwise
	 */
	public boolean isTotalLoaded(){
		Iterator<String> assetList = mDataMap.keys();
		String tmp = null;
		while(assetList.hasNext()){
			tmp = assetList.next();
			if(!eAdmin.egraphics.isLoaded(tmp,mDataMap.get(tmp).classType))
				return false;
		}
		return true;
	}
	
	/**
	 * You must call this method constantly until all assets done loading
	 */
	public boolean update(){
		return eAdmin.egraphics.update();
	}

	/** 
	 * Get the current loading process on this art
	 * @return the progress (0 to 1)
	 */
	public float getProgress(){
		return eAdmin.egraphics.getProgress();
	}
	
	
	public void clear(){
		eAdmin.egraphics.unloadArt(this);
		mUnloadedData.clear();
		mDataMap.clear();
	}
	
	/**
	 * Strongly release the art (make it disappear:D)
	 */
	public void dispose(){
		eAdmin.egraphics.removeArt(this);
		mDataMap.clear();
		mUnloadedData.clear();
	}
	
	class GraphicsData<T> {
		Class<T> classType = null;
		AssetLoaderParameters<T> param = null;
		
		GraphicsData (Class<T> clazz,AssetLoaderParameters<T> param) {
			classType = clazz;
			this.param = param;
		}
	}
}
