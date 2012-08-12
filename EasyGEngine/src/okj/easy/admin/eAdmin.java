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
		return eGraphics.game.GAME_WIDTH;
	}
	
	public static int gameHeight(){
		return eGraphics.game.GAME_HEIGHT;
	}
	
	public static int uiWidth(){
		return eGraphics.ui.UI_WIDTH;
	}
	
	public static int uiHeight(){
		return eGraphics.ui.UI_HEIGHT;
	}
	
	public static int orientaion(){
		return eGraphics.ORIENTATION;
	}

	public static float toastWidth(){
		return eGraphics.ui.TOAST_WIDTH;
	}
	/***************************************************************************
	 * eContext Method
	 **************************************************************************/
	
	
	
	/***************************************************************************
	 * Asset manager
	 **************************************************************************/
	
}
