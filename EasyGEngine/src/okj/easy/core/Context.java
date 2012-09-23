package okj.easy.core;

import org.ege.utils.ArrayDeque;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

/**
 * 
 * @FileName: Context.java
 * @CreateOn: Sep 15, 2012 - 11:06:15 AM
 * @Author: TrungNT
 */
public class Context implements ResourceContext {

	public final String				name;

	final ObjectMap<String, Data>	mDataMap			= new ObjectMap<String, Data>();
	final ArrayDeque<String>		mUnloadedData		= new ArrayDeque<String>();

	boolean							isTotallyUnloaded	= false;

	/**
	 * When this mode is enable , the load function just push to references not
	 * load the data until you calll reload
	 */
	boolean							isReferencesMode	= false;

	public Context (String name) {
		this.name = name;
		eAdmin.econtext.addContext(this);
	}

	/**
	 * When this mode enable , the references of data will only be store not
	 * load
	 */
	public Context (String name, boolean isRefStore) {
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

	/*************************************************************
	 * Data manage method
	 *************************************************************/

	/**
	 * Prepend an array of data to data list
	 * 
	 * @param data
	 *            data's array
	 */
	public <T> void load (String linkName, Class<T> clazz) {
		if (!isReferencesMode) {
			mDataMap.put(linkName, new Data<T>(clazz, null));
			eAdmin.econtext.loadContextData(linkName, clazz);
		} else {
			mDataMap.put(linkName, new Data<T>(clazz, null));
			mUnloadedData.add(linkName);
		}
	}

	/**
	 * Prepend an array of data to data list
	 * 
	 * @param data
	 *            data's array
	 */
	public <T> void load (String linkName, Class<T> clazz, AssetLoaderParameters<T> param) {
		if (!isReferencesMode) {
			mDataMap.put(linkName, new Data<T>(clazz, param));
			eAdmin.econtext.loadContextData(linkName, clazz, param);
		} else {
			mDataMap.put(linkName, new Data<T>(clazz, null));
			mUnloadedData.add(linkName);
		}
	}

	public void reload () {
		if (isTotallyUnloaded) {
			eAdmin.econtext.loadContext(this);
			isTotallyUnloaded = false;
		} else {
			String tmp = null;
			while (mUnloadedData.size() > 0) {
				tmp = mUnloadedData.poll();
				eAdmin.econtext.loadGraphicsData(tmp, mDataMap.get(tmp));
			}
		}
	}

	/**
	 * remove the given data
	 * 
	 * @param data
	 *            the data you want
	 */
	public void unload () {
		if (isTotallyUnloaded)
			return;
		eAdmin.econtext.unloadContext(this);
		mUnloadedData.clear();
		isTotallyUnloaded = true;
	}

	public void unload (String... linkName) {
		if (isTotallyUnloaded)
			return;
		for (String s : linkName) {
			if (eAdmin.econtext.isLoaded(s, mDataMap.get(s).clazz)) {
				mUnloadedData.add(s);
				eAdmin.econtext.unloadDirectly(s);
			}
		}
	}

	public void remove (String... listName) {
		for (String linkName : listName) {
			mDataMap.remove(linkName);
			mUnloadedData.remove(linkName);
			eAdmin.econtext.unloadDirectly(linkName);
		}
	}

	/**
	 * Ask for data is contaning in the list
	 * 
	 * @param data
	 *            the given data
	 * @return true if list contain data,otherwise false
	 */
	public boolean contain (String assetName) {
		return mDataMap.containsKey(assetName);
	}

	public boolean isUnloaded (String assetName) {
		return mUnloadedData.contains(assetName);
	}

	public boolean isLoaded (String assetName) {
		return eAdmin.econtext.isLoaded(assetName, mDataMap.get(assetName).clazz);
	}

	Data<?> getContextData (String assetName) {
		return mDataMap.get(assetName);
	}

	/**
	 * Get the size of data list
	 * 
	 * @return data list's size
	 */
	public int size () {
		return mDataMap.size;
	}

	public int unloadedSize () {
		return mUnloadedData.size();
	}

	/**
	 * If this art have 1 asset have not been loaded , it will be false
	 * 
	 * @return true if all assets was loaded, false otherwise
	 */
	public boolean isTotallyLoaded () {
		if (isTotallyUnloaded)
			return true;
		for (Entry<String, Data> entries : mDataMap.entries()) {
			if (!eAdmin.econtext.isLoaded(entries.key, entries.value.clazz))
				return false;
		}
		return true;
	}

	public boolean isTotallyUnloaded () {
		for (Entry<String, Data> entries : mDataMap.entries()) {
			if (eAdmin.econtext.isLoaded(entries.key, entries.value.clazz))
				return false;
		}
		return true;
	}

	/**
	 * You must call this method constantly until all assets done loading
	 */
	public boolean update () {
		return eAdmin.econtext.update();
	}

	/**
	 * Get the current loading process on this art
	 * 
	 * @return the progress (0 to 1)
	 */
	public float getProgress () {
		return eAdmin.econtext.getProgress();
	}

	public <T> T get (String name, Class<T> clazz) {
		return eAdmin.econtext.get(name, clazz);
	}

	public void clear () {
		eAdmin.econtext.unloadContext(this);
		mUnloadedData.clear();
		mDataMap.clear();
		isTotallyUnloaded = false;
	}

	/**
	 * Strongly release the art (make it disappear:D)
	 */
	public void dispose () {
		eAdmin.econtext.removeContext(this);
		mDataMap.clear();
		mUnloadedData.clear();
		isTotallyUnloaded = false;
	}
}
