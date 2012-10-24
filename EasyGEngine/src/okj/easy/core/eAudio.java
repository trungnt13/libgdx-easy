package okj.easy.core;

import java.util.Iterator;

import okj.easy.audio.DeeJayBackend;

import com.badlogic.gdx.assets.AssetLoaderParameters.LoadedCallback;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader.MusicParameter;
import com.badlogic.gdx.assets.loaders.SoundLoader.SoundParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * 
 * @FileName: eAudio.java
 * @CreateOn: Sep 15, 2012 - 11:12:00 AM
 * @Author: TrungNT
 */
public class eAudio implements LoadedCallback, DeeJayBackend
{
	public final AssetManager manager;

	static final ObjectMap<String, Album> mAlbumMap = new ObjectMap<String, Album>();

	static final ObjectMap<String, Sound> mSoundPool = new ObjectMap<String, Sound>();
	static final ObjectMap<String, Music> mMusicPool = new ObjectMap<String, Music>();

	private float mMusicVolume = 1f;
	private float mSoundVolume = 1f;

	final MusicParameter mMusicParam = new MusicParameter();
	final SoundParameter mSoundParam = new SoundParameter();

	public static enum AudioType
	{
		SOUND, MUSIC
	}

	public eAudio() {
		mMusicParam.loadedCallback = this;
		mSoundParam.loadedCallback = this;

		manager = new AssetManager();
	}

	Sound getSound (String name)
	{
		return mSoundPool.get(name);
	}

	Music getMusic (String name)
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

	/**
	 * Reload album
	 * 
	 * @param albumName
	 */
	public void reloadAlbum (String albumName)
	{
		mAlbumMap.get(albumName).reload();
	}

	/**
	 * Unload all data of album but still store them for reload
	 * 
	 * @param albumName
	 */
	public void unloadAlbum (String albumName)
	{
		mAlbumMap.get(albumName).unload();
	}

	/**
	 * Remove the album from audio stop manage it
	 * 
	 * @param albumName
	 */
	public void removeAlbum (String albumName)
	{
		mAlbumMap.remove(albumName);
	}

	/**
	 * Clear all asset data in given album
	 * 
	 * @param albumName
	 */
	public void clearAlbum (String albumName)
	{
		Album tmp = mAlbumMap.get(albumName);
		unloadAlbum(tmp);
		tmp.mDataMap.clear();
		tmp.mUnloadedData.clear();
	}

	/*******************************************************
	 * load audio data associate with album
	 ******************************************************/

	/**
	 * Load audio data
	 * 
	 * @param assetName
	 * @param audioType
	 */
	void load (String assetName, AudioType audioType)
	{
		switch (audioType) {
			case MUSIC:
				manager.load(assetName, Music.class, mMusicParam);
				break;
			case SOUND:
				manager.load(assetName, Sound.class, mSoundParam);
				break;
		}
	}

	/**
	 * Reload the whole album
	 * 
	 * @param album
	 */
	void loadAlbum (Album album)
	{
		Iterator<String> assetList = album.mDataMap.keys();
		String tmp = null;
		while (assetList.hasNext()) {
			tmp = assetList.next();
			load(tmp, album.mDataMap.get(tmp));
		}
	}

	/**
	 * Unload the whole album
	 * 
	 * @param album
	 */
	void unloadAlbum (Album album)
	{
		Iterator<String> assetList = album.mDataMap.keys();
		while (assetList.hasNext()) {
			unload(assetList.next());
		}
	}

	/**
	 * REmove the whole album
	 * 
	 * @param album
	 */
	void removeAlbum (Album album)
	{
		unloadAlbum(album);
		mAlbumMap.remove(album.name);
	}

	void unload (String assetName)
	{
		if (mSoundPool.containsValue(assetName, false)) {
			stopSound(assetName);
			mSoundPool.remove(assetName);
		} else {
			stopMusic(assetName);
			mMusicPool.remove(assetName);
		}
		manager.unload(assetName);
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

	public boolean isLoaded (String assetName, AudioType audioType)
	{
		switch (audioType) {
			case SOUND:
				return manager.isLoaded(assetName, Sound.class);
			case MUSIC:
				return manager.isLoaded(assetName, Music.class);
			default:
				return false;
		}
	}

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
		mMusicVolume = volume;
		Iterator<Music> musics = mMusicPool.values();
		while (musics.hasNext()) {
			musics.next().setVolume(volume);
		}
	}

	public void setSoundVolume (float volume)
	{
		mSoundVolume = volume;
	}

	/********************************************************
	 * Music method
	 *******************************************************/

	@Override
	public void playMusic (String audioName)
	{
		Music music = mMusicPool.get(audioName);
		music.setVolume(mMusicVolume);
		music.play();
	}

	@Override
	public void playMusic (String audioName, boolean looping)
	{
		Music music = mMusicPool.get(audioName);
		music.setVolume(mMusicVolume);
		music.setLooping(looping);
		music.play();
	}

	public void playMusic (String audioName, float volume)
	{
		Music music = mMusicPool.get(audioName);
		music.setVolume(mMusicVolume);
		music.play();
	}

	@Override
	public void pause (String audioName)
	{
		mMusicPool.get(audioName).pause();
	}

	public void stopMusic (String musicName)
	{
		mMusicPool.get(musicName).stop();
	}

	@Override
	public boolean isPlaying (String audioName)
	{
		return mMusicPool.get(audioName).isPlaying();
	}

	@Override
	public void setLooping (String audioName, boolean isLooping)
	{
		mMusicPool.get(audioName).setLooping(isLooping);
	}

	@Override
	public boolean isLooping (String audioName)
	{
		return mMusicPool.get(audioName).isLooping();
	}

	@Override
	public void setMusicVolume (String musicName, float volume)
	{
		mMusicPool.get(musicName).setVolume(volume);
	}

	/********************************************************
	 * Sound method
	 *******************************************************/

	@Override
	public long playLoopingSound (String audioName)
	{
		Sound sound = mSoundPool.get(audioName);
		return sound.loop();
	}

	@Override
	public long playLoopingSound (String audioName, float volume)
	{
		Sound sound = mSoundPool.get(audioName);
		return sound.loop(volume);

	}

	public long playSound (String soundName)
	{
		return mSoundPool.get(soundName).play(mSoundVolume);
	}

	public long playSound (String soundName, float volume)
	{
		return mSoundPool.get(soundName).play(volume);
	}

	public long playSound (String soundName, float pitch, float pan)
	{
		Sound tmp = mSoundPool.get(soundName);
		long id = tmp.play();
		tmp.setPitch(id, pitch);
		tmp.setPan(id, pan, 1);
		return id;
	}

	public long playSound (String soundName, float volume, float pitch, float pan)
	{
		Sound tmp = mSoundPool.get(soundName);
		long id = tmp.play();
		tmp.setPitch(id, pitch);
		tmp.setPan(id, pan, volume);
		return id;
	}

	@Override
	public long playLoopingSound (String soundName, float volume, float pitch, float pan)
	{
		Sound sound = mSoundPool.get(soundName);
		return sound.loop(volume, pitch, pan);
	}

	@Override
	public void stopSound (String audioName)
	{
		mSoundPool.get(audioName).stop();
	}

	public void stopSound (String soundName, long ID)
	{
		mSoundPool.get(soundName).stop(ID);
	}

	@Override
	public void setPan (String soundName, long ID, float volume, float pan)
	{
		mSoundPool.get(soundName).setPan(ID, pan, volume);
	}

	@Override
	public void setPitch (String soundName, long ID, float pitch)
	{
		mSoundPool.get(soundName).setPitch(ID, pitch);
	}

	@Override
	public void setLooping (String soundName, long ID, boolean isLooping)
	{
		mSoundPool.get(soundName).setLooping(ID, isLooping);
	}

	public void setSoundVolume (String soundName, long ID, float volume)
	{
		mSoundPool.get(soundName).setVolume(ID, volume);
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
}
