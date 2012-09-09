package okj.easy.audio;

/**
 * This is audio controller
 * 
 * @author trung
 * 
 */

public interface DeeJayBackend {

	public void playMusic (String audioName);

	public void playMusic (String musicName, boolean looping);

	public void playMusic (String audioName, float volume);

	public void stopMusic (String soundName);

	public boolean isPlaying (String audioName);

	public void pause (String audioName);

	public void setMusicVolume (String musicName, float volume);

	public void setLooping (String audioName, boolean isLooping);

	// ----------------------------------------------------

	public long playSound (String audioName);

	public long playLoopingSound (String audioName);

	public long playSound (String audioName, float volume);

	public long playLoopingSound (String audioName, float volume);

	public long playSound (String soundName, float pitch, float pan);

	public long playSound (String soundName, float volume, float pitch, float pan);

	public long playLoopingSound (String soundName, float volume, float pitch, float pan);

	public void setPan (String soundName, long ID, float volume, float pan);

	public void setPitch (String soundName, long ID, float pitch);

	public void stopSound (String soundName);

	public void stopSound (String soundName, long ID);

	public void setLooping (String soundName, long ID, boolean isLooping);

	public boolean isLooping (String audioName);

	public void setSoundVolume (String soundName, long ID, float volume);
}
