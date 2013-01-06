package okj.easy.core.loader;

import java.util.Iterator;

import okj.easy.core.eAdmin;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.MusicLoader.MusicParameter;
import com.badlogic.gdx.assets.loaders.SoundLoader.SoundParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap.Entries;
import com.badlogic.gdx.utils.ObjectMap.Entry;

/**
 * 
 * @FileName: Album.java
 * @CreateOn: Sep 15, 2012 - 11:05:58 AM
 * @Author: TrungNT
 */
public final class Album extends Context
{
    private static final AssetLoaderParameters MusicParam = new MusicParameter();
    private static final AssetLoaderParameters SoundParam = new SoundParameter();
    private final UnloadedCallback unloaded;

    public Album(String name)
    {
	super(name, eAdmin.eaudio.manager);
	eAdmin.eaudio.addAlbum(this);

	MusicParam.loadedCallback = eAdmin.eaudio;
	SoundParam.loadedCallback = eAdmin.eaudio;
	this.unloaded = eAdmin.eaudio;
    }

    /**
     * When this mode enable , the references of data will only be store not
     * load
     */
    public Album(String name, boolean isWaitStore)
    {
	this(name);
	setWaitMode(isWaitMode);
    }

    @Override
    public <T> void load (String linkName, Class<T> clazz)
    {
	Data data = null;
	if (clazz.equals(Sound.class))
	    data = new Data<T>(clazz, SoundParam);
	else if (clazz.equals(Music.class))
	    data = new Data<T>(clazz, MusicParam);

	mDataMap.put(linkName, data);

	if (!isWaitMode)
	    assets.load(linkName, clazz, data.param);
	else
	    mUnloadedData.add(linkName);
    }

    @Override
    public <T> void load (String linkName, Class<T> clazz, AssetLoaderParameters<T> param)
    {
	this.load(linkName, clazz);
    }

    @Override
    public void unload ()
    {
	super.unload();

	// ============= unload all data =============
	Entries<String, Data> entries = mDataMap.entries();
	for (Entry<String, Data> entry : entries) {
	    unloaded.unloaded(entry.key, entry.value.clazz);
	}
    }

    @Override
    public void unload (String... linkName)
    {
	super.unload(linkName);

	// ============= unload all data =============
	for (String s : linkName) {
	    Data d = mDataMap.get(s);
	    unloaded.unloaded(s, d.clazz);
	}
    }

    @Override
    public void remove (String... listName)
    {
	super.remove(listName);

	// ============= unload all data =============
	for (String s : listName) {
	    Data d = mDataMap.get(s);
	    unloaded.unloaded(s, d.clazz);
	}
    }

    @Override
    public void dispose ()
    {
	super.dispose();

	Entries<String, Data> entries = mDataMap.entries();
	for (Entry<String, Data> entry : entries) {
	    unloaded.unloaded(entry.key, entry.value.clazz);
	}
    }
}