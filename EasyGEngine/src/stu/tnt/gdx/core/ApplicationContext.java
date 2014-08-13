
package stu.tnt.gdx.core;

import stu.tnt.gdx.core.Timer.Task;
import stu.tnt.gdx.core.loader.Album;
import stu.tnt.gdx.core.loader.Context;
import stu.tnt.gdx.widget.StyleAtlas;
import stu.tnt.platform.IActivityHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.PauseableThread;

/**
 * 
 * @FileName: ApplicationContext.java
 * @CreateOn: Sep 15, 2012 - 11:06:05 AM
 * @Author: TrungNT
 */
public interface ApplicationContext {
	public static final GL20 gl = Gdx.gl;
	public static final GL20 gl20 = Gdx.gl20;
	public static final GL30 gl30 = Gdx.gl30;

	/***************************************************************************
	 * eInput Method
	 **************************************************************************/

	public InputProcessor findInputById (int id);

	/***************************************************************************
	 * eContext Method
	 **************************************************************************/

	public StyleAtlas styleQuery (String name);

	public <T> T getStyle (String styleName, Class<T> type);

	public <T> T optional (String resourceName, Class<T> type);

	public StyleAtlas getStyleAtlas (String name);

	public void stopStyleQuery ();

	/* ================================================ */

	public Context findContextByName (String name);

	public <T> T findDataByName (String linkName, Class<T> clazz);

	/* ================================================ */

	public TextureAtlas atlasQuery (String name);

	public TextureRegion findGRegionByName (String name);

	public TextureRegion findGRegionByName (String name, int index);

	public TextureRegion[] findGRegionsByName (String name);

	/* ================================================ */

	public <T> void load (String linkName, Class<T> clazz);

	public <T> void load (String linkName, Class<T> clazz, AssetLoaderParameters<T> param);

	public <T> T get (String fileName, Class<T> clazz);

	public boolean isLoaded (String linkName, Class<?> clazz);

	public <T> void unload (String linkName);

	public int totalGData ();

	/***************************************************************************
	 * eAudio Method
	 **************************************************************************/

	public Album findAlbumByName (String name);

	public int totalAData ();

	/***************************************************************************
	 * Quick GL Method
	 **************************************************************************/

	public void glClear (int glmask);

	public void glClearColor (float r, float g, float b, float a);

	/***************************************************************************
	 * GameCore method
	 **************************************************************************/

	public IActivityHandler getActivity ();

	public void post (Task task);

	public void schedule (Task task);

	public void schedule (int fps, Task task);

	public void schedule (Task task, float delaySeconds);

	public void schedule (Task task, float delaySeconds, float intervalSeconds);

	public void schedule (Task task, float delaySeconds, float intervalSeconds, int repeatCount);

	public int newThreadId (Runnable runnable);

	public PauseableThread newThread (Runnable runnable);

	public boolean startThread (int id);

	public boolean stopThread (int id);

	public boolean pauseThread (int id);

	public boolean resumeThread (int id);

	public boolean containThread (int id);

	public boolean containThread (PauseableThread thread);
}
