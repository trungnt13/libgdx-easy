package okj.easy.admin;

import java.util.Iterator;

import okj.easy.admin.eAudio.AudioType;

import org.ege.utils.ArrayDeque;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class Album implements Disposable{
	final String name;

	final ObjectMap<String, AudioType> mDataMap = new ObjectMap<String, AudioType>();
	final ArrayDeque<String> mUnloadedData = new ArrayDeque<String>();
	
	boolean isTotallyUnloaded = false;
	
	public Album(String name){
		this.name = name;
		eAdmin.eaudio.addAlbum(this);
	}
	
	/**
	 * Get the unique name of album
	 * @return
	 */
	public String name(){
		return this.name;
	}
	
	/**************************************************************
	 * 
	 **************************************************************/
	
	/**
	 * Load the audio data
	 * @param assetName audio file name
	 * @param audioType AudioType
	 */
	public void load(String assetName,AudioType audioType){
		mDataMap.put(assetName, audioType);
		eAdmin.eaudio.load(assetName, audioType);
	}
	
	/**
	 * When the album is unload it need to be reload so all sound and music
	 * can work again
	 */
	public void reload(){
		if(isTotallyUnloaded){
			eAdmin.eaudio.loadAlbum(this);
			isTotallyUnloaded = false;
		}else{
			String tmp = null;
			while(mUnloadedData.size() > 0){
				tmp = mUnloadedData.poll();
				eAdmin.eaudio.load(tmp, mDataMap.get(tmp));
			}
		}
	}

	/**
	 * Unload the whole album 
	 */
	public void unload(){
		if(isTotallyUnloaded)
			return;
		eAdmin.eaudio.unloadAlbum(this);
		isTotallyUnloaded = true;
		mUnloadedData.clear();
	}
	
	/**
	 * Unload the list of given file name
	 * @param name list of file name
	 */
	public void unload(String... name){
		if(isTotallyUnloaded)
			return;
		for(String s:name){
			if(eAdmin.eaudio.isLoaded(s, mDataMap.get(s))){
				mUnloadedData.add(s);
				eAdmin.eaudio.unload(s);
			}
		}
	}
	
	/**
	 * Remove the whole album from eAudio and  unload all data
	 */
	public void remove(){
		eAdmin.eaudio.removeAlbum(this);
		mDataMap.clear();
		mUnloadedData.clear();
		isTotallyUnloaded = false;
	}
	
	/**
	 * Remove the given file from eAudio and unload them
	 * @param assetList	  file list
	 */
	public void remove(String...assetList){
		for(String asset:assetList){
			eAdmin.eaudio.unload(asset);
			mDataMap.remove(asset);
			mUnloadedData.remove(asset);
		}
	}
	
	
	public boolean contain(String linkName){
		return mDataMap.containsKey(linkName);
	}
	
	public int size(){
		return mDataMap.size;
	}
	
	/**
	 * Get the number of unloaded data in album
	 * @return
	 */
	public int unloadedSize(){
		return mUnloadedData.size();
	}
	
	public boolean isTotallyUnloaded(){
		return isTotallyUnloaded;
	}
	
	public ObjectMap<String, AudioType> toData(){
		return mDataMap;
	}
	
	/********************************************************************
	 * 
	 ********************************************************************/
	
	/**
	 * If this art have 1 asset have not been loaded , it will be false
	 * @return true if all assets was loaded, false otherwise
	 */
	public boolean isTotalLoaded(){
		Iterator<String> assetList = mDataMap.keys();
		String tmp = null;
		while(assetList.hasNext()){
			tmp = assetList.next();
			if(!eAdmin.eaudio.isLoaded(tmp,mDataMap.get(tmp)))
				return false;
		}
		return true;
	}
	
	/**
	 * You must call this method constantly until all assets done loading
	 */
	public boolean update(){
		return eAdmin.eaudio.update();
	}

	/** 
	 * Get the current loading process on this art
	 * @return the progress (0 to 1)
	 */
	public float getProgress(){
		if(size() > 0)
			return eAdmin.eaudio.getProgress();
		return 1;
	}

	public void clear(){
		eAdmin.eaudio.unloadAlbum(this);
		mDataMap.clear();
		mUnloadedData.clear();
		isTotallyUnloaded = false;
	}
	
	public void dispose(){
		eAdmin.eaudio.removeAlbum(this);
		mDataMap.clear();
		mUnloadedData.clear();
	}
}