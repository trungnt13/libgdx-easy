package okj.easy.core;

import okj.easy.core.eAudio.AudioType;

import org.ege.utils.ArrayDeque;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

/**
 * 
 * @FileName: Album.java
 * @CreateOn: Sep 15, 2012 - 11:05:58 AM
 * @Author: TrungNT
 */
public class Album implements ResourceContext {
	final String						name;

	final ObjectMap<String, AudioType>	mDataMap			= new ObjectMap<String, AudioType>();
	final ArrayDeque<String>			mUnloadedData		= new ArrayDeque<String>();

	boolean								isTotallyUnloaded	= false;

	/**
	 * When this mode is enable , the load function just push to references not
	 * load the data until you calll reload
	 */
	boolean								isReferencesMode	= false;

	public Album (String name) {
		this.name = name;
		eAdmin.eaudio.addAlbum(this);
	}

	/**
	 * When this mode enable , the references of data will only be store not
	 * load
	 */
	public Album (String name, boolean isRefStore) {
		this(name);
		this.isReferencesMode = isRefStore;
	}

	/**
	 * When this mode enable , the references of data will only be store not
	 * load
	 * 
	 * @param isRefStore
	 */
	public void setRefStoreMode (boolean isRefStore) {
		this.isReferencesMode = isRefStore;
	}

	public Sound getSound (String name) {
		return eAdmin.eaudio.getSound(name);
	}

	public Music getMusic (String name) {
		return eAdmin.eaudio.getMusic(name);
	}

	/**************************************************************
	 * 
	 **************************************************************/

	/**
	 * Load the audio data
	 * 
	 * @param assetName
	 *            audio file name
	 * @param audioType
	 *            AudioType
	 */
	public void load (String assetName, AudioType audioType) {
		if (!isReferencesMode) {
			mDataMap.put(assetName, audioType);
			eAdmin.eaudio.load(assetName, audioType);
		} else {
			mDataMap.put(assetName, audioType);
			mUnloadedData.add(assetName);
		}
	}

	/**
	 * When the album is unload it need to be reload so all sound and music can
	 * work again
	 */
	public void reload () {
		if (isTotallyUnloaded) {
			eAdmin.eaudio.loadAlbum(this);
			isTotallyUnloaded = false;
		} else {
			String tmp = null;
			while (mUnloadedData.size() > 0) {
				tmp = mUnloadedData.poll();
				eAdmin.eaudio.load(tmp, mDataMap.get(tmp));
			}
		}
	}

	/**
	 * Unload the whole album
	 */
	public void unload () {
		if (isTotallyUnloaded)
			return;
		eAdmin.eaudio.unloadAlbum(this);
		isTotallyUnloaded = true;
		mUnloadedData.clear();
	}

	/**
	 * Unload the list of given file name
	 * 
	 * @param name
	 *            list of file name
	 */
	public void unload (String... name) {
		if (isTotallyUnloaded)
			return;
		for (String s : name) {
			if (eAdmin.eaudio.isLoaded(s, mDataMap.get(s))) {
				mUnloadedData.add(s);
				eAdmin.eaudio.unload(s);
			}
		}
	}

	/**
	 * Remove the given file from eAudio and unload them
	 * 
	 * @param assetList
	 *            file list
	 */
	public void remove (String... assetList) {
		for (String asset : assetList) {
			eAdmin.eaudio.unload(asset);
			mDataMap.remove(asset);
			mUnloadedData.remove(asset);
		}
	}

	public boolean contain (String linkName) {
		return mDataMap.containsKey(linkName);
	}

	public int size () {
		return mDataMap.size;
	}

	/**
	 * Get the number of unloaded data in album
	 * 
	 * @return
	 */
	public int unloadedSize () {
		return mUnloadedData.size();
	}

	public boolean isTotallyUnloaded () {
		for (Entry<String, AudioType> entries : mDataMap.entries()) {
			if (eAdmin.eaudio.isLoaded(entries.key, entries.value))
				return false;
		}
		return true;
	}

	@Override
	public boolean isTotallyLoaded () {
		if (isTotallyUnloaded)
			return true;
		for (Entry<String, AudioType> entries : mDataMap.entries()) {
			if (!eAdmin.eaudio.isLoaded(entries.key, entries.value))
				return false;
		}
		return true;
	}

	public ObjectMap<String, AudioType> toData () {
		return mDataMap;
	}

	/********************************************************************
	 * 
	 ********************************************************************/

	/**
	 * You must call this method constantly until all assets done loading
	 */
	public boolean update () {
		return eAdmin.eaudio.update();
	}

	/**
	 * Get the current loading process on this art
	 * 
	 * @return the progress (0 to 1)
	 */
	public float getProgress () {
		if (size() > 0)
			return eAdmin.eaudio.getProgress();
		return 1;
	}

	public void clear () {
		eAdmin.eaudio.unloadAlbum(this);
		mDataMap.clear();
		mUnloadedData.clear();
		isTotallyUnloaded = false;
	}

	public void dispose () {
		eAdmin.eaudio.removeAlbum(this);
		mDataMap.clear();
		mUnloadedData.clear();
		isTotallyUnloaded = false;
	}

}