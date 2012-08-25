package org.ege.starter;

import org.ege.utils.E;

import okj.easy.admin.Screen;
import okj.easy.admin.eGraphics;
import okj.easy.core.GameCore;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopStarter extends GameCore{
	
	public static void main(String[] argv){
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = R.SCREEN_WIDTH;
		config.height = R.SCREEN_HEIGHT;
		config.useGL20 = R.GL20;
		new LwjglApplication(new DesktopStarter(), config);
	}
	
	@Override
	protected void onGameConfig () {
		eGraphics.setGameResolution(R.GAME_WIDTH,R.GAME_HEIGHT);
		eGraphics.setUiResolution(E.resolution.MANUAL_RESOLUTION_MODE, R.SCREEN_WIDTH,R.SCREEN_HEIGHT);
	}

	@Override
	protected void onGameChanged (int width, int height) {
		try {
			setScreen((Screen)R.SCREEN.newInstance(), Screen.RELEASE);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onGameRender (float delta) {
	}

	@Override
	public void onGamePause () {
	}

	@Override
	protected void onGameResume () {
	}

	@Override
	protected void onGameDestroy () {
	}
}
