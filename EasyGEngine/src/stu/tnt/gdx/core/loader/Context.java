package stu.tnt.gdx.core.loader;

import java.util.Iterator;

import stu.tnt.gdx.core.ResourceContext;
import stu.tnt.gdx.core.eAdmin;
import stu.tnt.gdx.utils.ArrayDeque;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

/**
 * Cached data path before its loaded
 * 
 * @FileName: Context.java
 * @CreateOn: Sep 15, 2012 - 11:06:15 AM
 * @Author: TrungNT
 */
public class Context implements ResourceContext {

	public final String name;

	final ObjectMap<String, Data> mDataMap = new ObjectMap<String, Data>();
	final ArrayDeque<String> mUnloadedData = new ArrayDeque<String>();

	boolean isDisposed = false;
	protected final AssetManager assets;

	public Context(String name, AssetManager assets) {
		this.name = name;
		this.assets = assets;

		if (assets == eAdmin.econtext.manager)
			eAdmin.econtext.addContext(this);
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
	public <T> void load(String linkName, Class<T> clazz) {
		mDataMap.put(linkName, new Data<T>(clazz, null));
		mUnloadedData.add(linkName);
	}

	/**
	 * Prepend an array of data to data list
	 * 
	 * @param data
	 *            data's array
	 */
	public <T> void load(String linkName, Class<T> clazz,
			AssetLoaderParameters<T> param) {
		mDataMap.put(linkName, new Data<T>(clazz, param));
		mUnloadedData.add(linkName);
	}

	/**
	 * Make sure all resource associate with this context is loaded
	 */
	public void load() {
		String tmp = null;
		while (mUnloadedData.size() > 0) {
			tmp = mUnloadedData.poll();
			Data data = mDataMap.get(tmp);
			if (data.param != null)
				assets.load(tmp, data.clazz, data.param);
			else
				assets.load(tmp, data.clazz);
		}
	}

	/**
	 * remove the given data
	 * 
	 * @param data
	 *            the data you want
	 */
	public void unload() {
		// ============= unload all data =============
		Iterator<String> dataName = mDataMap.keys();
		while (dataName.hasNext())
			assets.unload(dataName.next());

		// ============= put to unloaded list =============
		for (String s : mDataMap.keys())
			if (!mUnloadedData.contains(s))
				mUnloadedData.add(s);
	}

	public void unload(String... linkName) {
		for (String s : linkName) {
			if (assets.isLoaded(s, mDataMap.get(s).clazz)) {
				mUnloadedData.add(s);
				assets.unload(s);
			}
		}
	}

	/**
	 * unmanage given data
	 */
	public void remove(String... listName) {
		for (String linkName : listName) {
			mDataMap.remove(linkName);
			mUnloadedData.remove(linkName);
			assets.unload(linkName);
		}
	}

	/**
	 * Ask for data is contaning in the list
	 * 
	 * @param data
	 *            the given data
	 * @return true if list contain data,otherwise false
	 */
	public boolean contain(String assetName) {
		return mDataMap.containsKey(assetName);
	}

	public boolean isUnloaded(String assetName) {
		return mUnloadedData.contains(assetName);
	}

	public boolean isLoaded(String assetName) {
		return assets.isLoaded(assetName, mDataMap.get(assetName).clazz);
	}

	Data<?> getContextData(String assetName) {
		return mDataMap.get(assetName);
	}

	/**
	 * Get the size of data list
	 * 
	 * @return data list's size
	 */
	public int size() {
		return mDataMap.size;
	}

	public int unloadedSize() {
		return mUnloadedData.size();
	}

	/**
	 * If this art have 1 asset have not been loaded , it will be false
	 * 
	 * @return true if all assets was loaded, false otherwise
	 */
	public boolean isTotallyLoaded() {
		for (Entry<String, Data> entries : mDataMap.entries()) {
			if (!assets.isLoaded(entries.key, entries.value.clazz))
				return false;
		}
		return true;
	}

	public boolean isTotallyUnloaded() {
		for (Entry<String, Data> entries : mDataMap.entries()) {
			if (assets.isLoaded(entries.key, entries.value.clazz))
				return false;
		}
		return true;
	}

	/**
	 * You must call this method constantly until all assets done loading
	 */
	public boolean update() {
		return assets.update();
	}

	/**
	 * Get the current loading process on this art
	 * 
	 * @return the progress (0 to 1)
	 */
	public float getProgress() {
		return assets.getProgress();
	}

	public <T> T get(String name, Class<T> clazz) {
		return assets.get(name, clazz);
	}

	public boolean isDisposed() {
		return isDisposed;
	}

	public void clear() {
		// ============= unload all data =============
		Iterator<String> dataName = mDataMap.keys();
		while (dataName.hasNext())
			assets.unload(dataName.next());

		// ============= clear data list =============
		mUnloadedData.clear();
		mDataMap.clear();
	}

	/**
	 * delete all data
	 */
	public void dispose() {
		isDisposed = true;

		if (assets == eAdmin.econtext.manager)
			eAdmin.econtext.removeContext(this);

		// ============= unload all data =============
		Iterator<String> dataName = mDataMap.keys();
		while (dataName.hasNext())
			assets.unload(dataName.next());

		// ============= clear data list =============
		mUnloadedData.clear();
		mDataMap.clear();
	}
}
