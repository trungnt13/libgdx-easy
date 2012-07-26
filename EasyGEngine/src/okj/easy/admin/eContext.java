package okj.easy.admin;


import org.ege.assets.DataLoader;
import org.ege.assets.StyleLoader;
import org.ege.assets.StyleLoader.StyleParameter;
import org.ege.utils.DataSSD;
import org.ege.utils.Factory;
import org.ege.utils.Pool;
import org.ege.utils.exception.EasyGEngineRuntimeException;
import org.ege.widget.StyleAtlas;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class eContext {
	public static AssetManager manager;
	
	/**
	 * if queryMode = 0 , findStyleById will return style from
	 * main styleAtlas, otherwise , 
	 * return style from styleAtlasAlter
	 */
	private boolean isSetLoader = false;
	
	static StyleAtlas mCurStyleAtlas = null;
	
	public eContext(){
		manager = new AssetManager();
		manager.setLoader(DataSSD.class,new DataLoader(new InternalFileHandleResolver())); 
	}
	
	/*******************************************************************************
	 * Style manager 
	 ********************************************************************************/

	public StyleAtlas atlasQuery(String name){
		return mCurStyleAtlas = manager.get(name, StyleAtlas.class);
	}
	
	/**
	 * Safe Get
	 * @param styleName
	 * @param type
	 * @return
	 */
	public <T> T get(String styleName,Class<T> type){
		if(mCurStyleAtlas == null)
			throw new EasyGEngineRuntimeException("Plz  use eAdmin.eContext.atlasQuery(...) mode first");
		return mCurStyleAtlas.get(styleName, type);
	}
	
	/**
	 * Unsafe get
	 * @param resourceName
	 * @param type
	 * @return
	 */
	public <T> T optional(String resourceName,Class<T> type){
		if(mCurStyleAtlas == null)
			throw new EasyGEngineRuntimeException("Plz  use eAdmin.eContext.atlasQuery(...) mode first");
		return mCurStyleAtlas.optional(resourceName, type);
	}
	
	public StyleAtlas getQueryStyle(){
		return mCurStyleAtlas;
	}

	public StyleAtlas getStyle(String name){
		return manager.get(name, StyleAtlas.class);
	}
	
	public void stopQuery(){
		mCurStyleAtlas = null;
	}
	
	public void setLoader(Resolution...resolutions){
		if(resolutions.length > 0){
			ResolutionFileResolver resolver = new ResolutionFileResolver(new InternalFileHandleResolver(), resolutions);
			manager.setLoader(StyleAtlas.class	, new StyleLoader(resolver));
		}else{
			manager.setLoader(StyleAtlas.class	, new StyleLoader(new InternalFileHandleResolver()));
		}
		isSetLoader = true;
	}
	
	public boolean isSetLoader(){
		return isSetLoader;
	}
	
	public void load(String dataPath){
		manager.load(dataPath, StyleAtlas.class);
	}
	
	public void load(String dataPath,boolean isUseSuffixFOrTExture){
		if(isUseSuffixFOrTExture)
			load(dataPath);
		else{
			String tmp = dataPath;
			tmp = tmp.replace(".json", ".png");
			StyleParameter param = new StyleParameter(tmp);
			manager.load(dataPath, StyleAtlas.class, param);
		}
	}
	
	public void load(String datapaht,StyleParameter param){
		manager.load(datapaht, StyleAtlas.class, param);
	}
	
	public void unload(String dataPath){
		manager.unload(dataPath);
	}
	
	public boolean update(){
		return manager.update();
	}

	public boolean isLoaded(String dataPath){
		return manager.isLoaded(dataPath, StyleAtlas.class);
	}
	
	public void finishLoading(){
		manager.finishLoading();
	}
	
	public int getLoadedAssets(){
		return manager.getLoadedAssets();
	}
	
	public int getQueueAssets(){
		return manager.getQueuedAssets();
	}
	
	public float getProgress(){
		return manager.getProgress();
	}
	
	/*******************************************************************************
	 * Custom data manager 
	 ********************************************************************************/

	public <T, P extends AssetLoaderParameters<T>> void setDataLoader(Class<T> type, AssetLoader<T, P> loader){
		manager.setLoader(type, loader);
	}
	
	public  <T> T getData(String name,Class<T> type){
		return manager.get(name, type);
	}
	
	public <T> void loadData(String name,Class<T> type){
		manager.load(name, type);
	}

	public <T> void loadData(String name,Class<T> type,AssetLoaderParameters<T> param){
		manager.load(name, type,param);
	}

	public void unloadData(String name){
		manager.unload(name);
	}
	
	public boolean isDataLoaded(String name){
		return manager.isLoaded(name);
	}
	 /*******************************************************************************
	  * Pool Manager 
	  ********************************************************************************/
	 
	 private static final ObjectMap<Class<?>, Pool<?>> mPools = new ObjectMap<Class<?>, Pool<?>>();
	 private static Pool mCurrentPool;
	 
	 public Pool poolQuery(Class type){
		 mCurrentPool = mPools.get(type);
		 if(mCurrentPool == null)
			 throw new EasyGEngineRuntimeException("You haven't make any pool for class : " + type.getName());
		 return mCurrentPool;
	 }
	 
	 public <T> void makePool(final Class<T> type,Factory<T> factory){
		 mPools.put(type, new Pool<T>(factory) );
	 }
	
	 public <T> void makePool(int initCapacity,Class<T> type,Factory<T> factory){
		 mPools.put(type, new Pool<T>(initCapacity,factory));
	 }
	 
	 public <T> T newObject(){
		 return (T) mCurrentPool.obtain();
	 }

	 public <T> T newObject(Object...objects){
		 return (T) mCurrentPool.obtain(objects);
	 }

	 public <T> T newObject(Class<T> type){
		 return (T) mPools.get(type).obtain();
	 }

	 public <T> T newObject(Class<T> type,Object...objects){
		 return (T) mPools.get(type).obtain(objects);
	 }
	 
	 public <T> void free(T obj){
		 mCurrentPool.free(obj);
	 }

	 public <T> void free(Array<T> objs){
		 mCurrentPool.free(objs);
	 }

	 public <T> void free(Class<T> type,T obj){
		 ((Pool<T>)mPools.get(type)).free(obj);
	 }

	 public <T> void free(Class<T> type,Array<T> objs){
		 ((Pool<T>)mPools.get(type)).free(objs);
	 }

	 public int poolSize(){
		 return mCurrentPool.size();
	 }
	 
	 public <T> int poolSize(Class<T> type){
		 return mPools.get(type).size();
	 }
	 
	 public void clearPool(){
		 mCurrentPool.clear();
	 }
	 
	 public void clearPool(Class<?> type){
		 mPools.get(type).clear();
	 }
}
