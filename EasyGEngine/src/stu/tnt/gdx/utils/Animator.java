package stu.tnt.gdx.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A class make it easy to manage animation of sprite
 * 
 * @author Ngo Trong TRung
 */
public interface Animator {
	public void setFrameDuration(float frameDuration);

	public void start();

	public void start(float frameDuration);

	public void start(float frameDuration, Animation.PlayMode playMode);

	public void stop();

	public void pause();

	public void switchState();

	public void resetFrame();

	public void update(float delta);

	public boolean isRunning();

	public int getFrameNumber();

	public TextureRegion[] getFrames();
}