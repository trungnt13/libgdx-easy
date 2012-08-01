package okj.easy.admin;

import okj.easy.core.GameCore;

public class eAdmin{
	public static GameCore egame;
	public static eInput einput;
	public static eAudio eaudio;
	public static eGraphics egraphics;
	public static eContext	econtext;

	private eAdmin(){
		
	}
	
	public static void destroy(){
		econtext.dispose();
		eaudio.dispose();
	}
	
	/***************************************************************************
	 * GameCore method 
	 **************************************************************************/
	
	public static int gameWidth(){
		return egame.gameWidth();
	}
	
	public static int gameHeight(){
		return egame.gameHeight();
	}
	
	/***************************************************************************
	 * eContext Method
	 **************************************************************************/
	
	
	
	/***************************************************************************
	 * Asset manager
	 **************************************************************************/
	
}
