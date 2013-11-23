package stu.tnt.gdx.core;

import java.util.Iterator;

import stu.tnt.gdx.audio.DeeJayBackend;
import stu.tnt.gdx.core.loader.Album;
import stu.tnt.gdx.core.loader.UnloadedCallback;

import com.badlogic.gdx.assets.AssetLoaderParameters.LoadedCallback;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader.MusicParameter;
import com.badlogic.gdx.assets.loaders.SoundLoader.SoundParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * 
 * @FileName: eAudio.java
 * @CreateOn: Sep 15, 2012 - 11:12:00 AM
 * @Author: TrungNT
 */
public class eAudio implements LoadedCallback, DeeJayBackend, UnloadedCallback
{
    public final AssetManager manager;

    static final ObjectMap<String, Album> mAlbumMap = new ObjectMap<String, Album>();

    static final ObjectMap<String, Sound> mSoundPool = new ObjectMap<String, Sound>();
    static final ObjectMap<String, Music> mMusicPool = new ObjectMap<String, Music>();

    static final Array<Sound> LoopingSoundList = new Array<Sound>(6);
    static final Array<Music> LoopingMusicList = new Array<Music>(3);

    final MusicParameter mMusicParam = new MusicParameter();
    final SoundParameter mSoundParam = new SoundParameter();

    public eAudio()
    {
	mMusicParam.loadedCallback = this;
	mSoundParam.loadedCallback = this;

	manager = new AssetManager();
    }

    public Sound getSound (String name)
    {
	return mSoundPool.get(name);
    }

    public Music getMusic (String name)
    {
	return mMusicPool.get(name);
    }

    /*******************************************************
     * Manage album
     ******************************************************/

    /**
     * Add new album to eAudio for manage
     * 
     * @param album
     * @return
     */
    public boolean addAlbum (Album album)
    {
	if (album != null && !mAlbumMap.containsValue(album, true)) {
	    mAlbumMap.put(album.name, album);
	    return true;
	}
	return false;
    }

    public void removeAlbum (Album album)
    {
	mAlbumMap.remove(album.name);

	if (!album.isDisposed())
	    album.dispose();
    }

    public void removeAlbum (String albumName)
    {
	Album album = mAlbumMap.remove(albumName);

	if (!album.isDisposed())
	    album.dispose();
    }

    /*********************************************************************
     * Query
     *********************************************************************/

    public Album findAlbumByName (String name)
    {
	return mAlbumMap.get(name);
    }

    public int totalAData ()
    {
	return (mSoundPool.size + mMusicPool.size);
    }

    /*********************************************************************
	 * 
	 *********************************************************************/

    public float getProgress ()
    {
	return manager.getProgress();
    }

    /**
     * Get the number of loaded asset
     * 
     * @return loaded assets
     */
    public int getLoadedAssets ()
    {
	return manager.getLoadedAssets();
    }

    /**
     * Get the assets which will be load soon
     * 
     * @return number of assets in queue
     */
    public int getQueueAssets ()
    {
	return manager.getQueuedAssets();
    }

    /**
     * Clear all asset of the manager
     */
    void superClear ()
    {
	manager.clear();
    }

    /**
     * Destroy the manager (game down)
     */
    void dispose ()
    {
	manager.dispose();
    }

    /**
     * Call manager update to know loading state
     * 
     * @return true if loading done,otherwise false
     */
    public boolean update ()
    {
	return manager.update();
    }

    /*************************************************************
	 * 
	 *************************************************************/
    public void setMusicVolume (float volume)
    {
	Iterator<Music> musics = mMusicPool.values();
	while (musics.hasNext()) {
	    musics.next().setVolume(volume);
	}
    }

    /********************************************************
     * Music method
     *******************************************************/

    @Override
    public void playMusic (String audioName)
    {
	Music music = mMusicPool.get(audioName);
	if (music == null)
	    return;
	music.setVolume(1);
	music.play();
    }

    @Override
    public void playMusic (String audioName, float volume, boolean looping)
    {
	Music music = mMusicPool.get(audioName);
	if (music == null)
	    return;
	music.setVolume(volume);
	music.setLooping(looping);
	music.play();
    }

    @Override
    public void playMusic (String audioName, boolean looping)
    {
	Music music = mMusicPool.get(audioName);
	if (music == null)
	    return;

	music.setVolume(1);
	music.setLooping(looping);
	music.play();
    }

    public void playMusic (String audioName, float volume)
    {
	Music music = mMusicPool.get(audioName);
	if (music == null)
	    return;

	music.setVolume(1);
	music.play();
    }

    @Override
    public void pause (String audioName)
    {
	Music m = mMusicPool.get(audioName);
	if (m != null)
	    m.pause();
    }

    public void stopMusic (String musicName)
    {
	Music m = mMusicPool.get(musicName);
	if (m != null)
	    m.stop();
    }

    @Override
    public boolean isPlaying (String audioName)
    {
	Music m = mMusicPool.get(audioName);
	if (m == null)
	    return false;
	return m.isPlaying();
    }

    @Override
    public void setLooping (String audioName, boolean isLooping)
    {
	Music m = mMusicPool.get(audioName);
	if (m != null)
	    m.setLooping(isLooping);
    }

    @Override
    public boolean isLooping (String audioName)
    {
	Music m = mMusicPool.get(audioName);
	if (m == null)
	    return false;
	return m.isLooping();
    }

    @Override
    public void setMusicVolume (String musicName, float volume)
    {
	Music m = mMusicPool.get(musicName);
	if (m != null)
	    m.setVolume(volume);
    }

    /********************************************************
     * Sound method
     *******************************************************/

    @Override
    public long playLoopingSound (String audioName)
    {
	Sound sound = mSoundPool.get(audioName);
	if (sound == null)
	    return 0;
	return sound.loop();
    }

    @Override
    public long playLoopingSound (String audioName, float volume)
    {
	Sound sound = mSoundPool.get(audioName);
	if (sound == null)
	    return 0;
	return sound.loop(volume);

    }

    public long playSound (String soundName)
    {
	Sound s = mSoundPool.get(soundName);
	if (s == null)
	    return 0;
	return s.play(1);
    }

    public long playSound (String soundName, float volume)
    {
	Sound s = mSoundPool.get(soundName);
	if (s == null)
	    return 0;
	return s.play(volume);
    }

    public long playSound (String soundName, float pitch, float pan)
    {
	Sound tmp = mSoundPool.get(soundName);
	if (tmp == null)
	    return 0;
	long id = tmp.play();
	tmp.setPitch(id, pitch);
	tmp.setPan(id, pan, 1);
	return id;
    }

    public long playSound (String soundName, float volume, float pitch, float pan)
    {
	Sound tmp = mSoundPool.get(soundName);
	if (tmp == null)
	    return 0;
	long id = tmp.play();
	tmp.setPitch(id, pitch);
	tmp.setPan(id, pan, volume);
	return id;
    }

    @Override
    public long playLoopingSound (String soundName, float volume, float pitch, float pan)
    {
	Sound sound = mSoundPool.get(soundName);
	if (sound != null)
	    return sound.loop(volume, pitch, pan);
	else
	    return 0;
    }

    @Override
    public void stopSound (String audioName)
    {
	Sound s = mSoundPool.get(audioName);
	if (s != null)
	    s.stop();
    }

    public void stopSound (String soundName, long ID)
    {
	Sound s = mSoundPool.get(soundName);
	if (s != null)
	    s.stop(ID);
    }

    @Override
    public void setPan (String soundName, long ID, float volume, float pan)
    {
	Sound s = mSoundPool.get(soundName);
	if (s != null)
	    s.setPan(ID, pan, volume);
    }

    @Override
    public void setPitch (String soundName, long ID, float pitch)
    {
	Sound s = mSoundPool.get(soundName);
	if (s != null)
	    s.setPitch(ID, pitch);
    }

    @Override
    public void setLooping (String soundName, long ID, boolean isLooping)
    {
	Sound s = mSoundPool.get(soundName);
	if (s != null)
	    s.setLooping(ID, isLooping);
    }

    public void setSoundVolume (String soundName, long ID, float volume)
    {
	Sound s = mSoundPool.get(soundName);
	if (s != null)
	    s.setVolume(ID, volume);
    }

    /*************************************************************
	 * 
	 *************************************************************/

    @Override
    public void finishedLoading (AssetManager assetManager, String fileName, Class type)
    {
	if (type.equals(Sound.class))
	    mSoundPool.put(fileName, assetManager.get(fileName, Sound.class));
	else if (type.equals(Music.class))
	    mMusicPool.put(fileName, assetManager.get(fileName, Music.class));
    }

    @Override
    public void unloaded (String name, Class type)
    {
	if (type.equals(Sound.class))
	    mSoundPool.remove(name);
	else if (type.equals(Music.class))
	    mMusicPool.remove(name);
    }
}
