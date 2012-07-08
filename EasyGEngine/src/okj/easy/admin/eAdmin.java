package okj.easy.admin;

import okj.easy.core.GameCore;

import com.badlogic.gdx.Gdx;

public class eAdmin{
	public static GameCore egame;
	public static eInput einput;
	public static eAudio eaudio;
	public static eGraphics egraphics;
	public static eContext	econtext;

	private eAdmin(){
		
	}
	
	public static void destroy(){
		egraphics.dispose();
		eaudio.dispose();
	}
	
	/***************************************************************************
	 * GameCore method 
	 **************************************************************************/
	
	public static int getWidth(){
		return Gdx.graphics.getWidth();
	}
	
	public static int getHeight(){
		return Gdx.graphics.getHeight();
	}
	/***************************************************************************
	 * eContext Method
	 **************************************************************************/
	
	
	
	/***************************************************************************
	 * Asset manager
	 **************************************************************************/
	
}
