package okj.easy.core;

import org.ege.widget.Layout;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.math.Matrix4;

/**
 * 
 * @FileName: eAdmin.java
 * @CreateOn: Sep 15, 2012 - 11:06:31 AM
 * @Author: TrungNT
 */
public class eAdmin {
	public static GameCore	egame;
	public static eInput	einput;
	public static eAudio	eaudio;
	public static eGraphics	egraphics;
	public static eContext	econtext;

	private eAdmin () {

	}

	public static void destroy () {
		econtext.dispose();
		eaudio.dispose();
	}

	/***************************************************************************
	 * GameCore method
	 **************************************************************************/

	/***************************************************************************
	 * GameCore method
	 **************************************************************************/

	public static int gameWidth () {
		return eGraphics.game.GAME_WIDTH;
	}

	public static int gameHeight () {
		return eGraphics.game.GAME_HEIGHT;
	}

	public static int uiWidth () {
		return eGraphics.ui.UI_WIDTH;
	}

	public static int uiHeight () {
		return eGraphics.ui.UI_HEIGHT;
	}

	public static int orientaion () {
		return eGraphics.ORIENTATION;
	}

	public static float toastWidth () {
		return eGraphics.ui.TOAST_WIDTH;
	}

	/***************************************************************************
	 * eGame Method
	 **************************************************************************/

	public static void apply (SpriteCache cache) {
		final Matrix4 proj = egame.getCurrentScreen().projection;
		proj.setToOrtho2D(0, 0, gameWidth(), gameHeight());
		cache.setProjectionMatrix(proj);
	}

	public static void apply (SpriteBatch batch) {
		final Matrix4 proj = egame.getCurrentScreen().projection;
		proj.setToOrtho2D(0, 0, gameWidth(), gameHeight());
		batch.setProjectionMatrix(proj);
	}

	public static void apply (Layout layout) {
		layout.setViewport(uiWidth(), uiHeight(), false);
	}
	/***************************************************************************
	 * Asset manager
	 **************************************************************************/

}
