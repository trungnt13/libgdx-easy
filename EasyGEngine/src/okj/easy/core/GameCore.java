package okj.easy.core;

import okj.easy.admin.Screen;
import okj.easy.admin.eAdmin;
import okj.easy.admin.eAudio;
import okj.easy.admin.eContext;
import okj.easy.admin.eGraphics;
import okj.easy.admin.eInput;
import okj.easy.core.utils.Bridge;
import okj.easy.core.utils.BridgePool;

import org.ege.utils.EasyNativeLoader;
import org.ege.utils.Timer;
import org.ege.widget.Dialog;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public abstract class GameCore implements ApplicationListener{
	
	//	----------------------------------------------------------
	
	protected Screen screen;

	private final BridgePool bridgePool;
	
	//	----------------------------------------------------------

	public GameCore(){
		bridgePool = new BridgePool(13);
		Bridge.registerRecyleListener(bridgePool);
	}
	
	/* ---------------------------------------------------------- */
	
	@Override
	public void create() {
		eAdmin.egame  = GameCore.this;
		eAdmin.einput = new eInput();
		eAdmin.egraphics = new eGraphics();
		eAdmin.eaudio = new eAudio();
		eAdmin.econtext = new eContext();
		
		EasyNativeLoader.load();
		Timer.instance.reset();
		
		Gdx.input.setInputProcessor(eAdmin.einput);
		
		Dialog.DIALOG_NUMBER = 0;
		onGameConfig();
	}
	/**
	 * Initialize all game components
	 */
	protected abstract void onGameConfig();

	/* ---------------------------------------------------------- */
	
	@Override
	public void resize(int width, int height) {
		eGraphics.resolve(width, height);
		if(screen != null)
			screen.resize(width, height);
		onGameChanged(width,height);
	}
	
	/**
	 * Listen to surface size
	 * @param width the screen width
	 * @param height the screen height
	 */
	protected abstract void onGameChanged(int width, int height);

	/* ---------------------------------------------------------- */
	private float delta;
	
	@Override
	public void render() {
		delta = Gdx.graphics.getDeltaTime();
		if(screen != null){
			screen.render(delta);
			screen.update(delta);
		}
		onGameRender(delta);
	}
	
	/**
	 * Render all stuff here
	 * @param DeltaTime the time each frame repeat
	 */
	protected abstract void onGameRender(float delta);
	
	/* ---------------------------------------------------------- */
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		if(screen != null)
			screen.pause();
		onGamePause();
	}

	public abstract void onGamePause();

	/* ---------------------------------------------------------- */
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		if(screen != null)
			screen.resume();
		onGameResume();
	}
	
	/**
	 * This method do the same function as resume
	 */
	protected abstract void onGameResume();
	
	/* ---------------------------------------------------------- */

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		if(screen != null)
			screen.destroy(Screen.RELEASE);
		onGameDestroy();
		this.bridgePool.clear();
	}
	/**
	 * This method will release memory
	 */
	protected abstract void onGameDestroy();

	/* ---------------------------------------------------------- */

	/**
	 * Set the current screen to the new screen 
	 * @param screen new Screen
	 * @param destroyMode destroyMode of old Screen
	 */
	public void setScreen(Screen screen,int destroyMode){
		if(this.screen != null)
			this.screen.destroy(destroyMode);
		this.screen = screen;
		if(this.screen != null){
			screen.show(this);
			screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}
	
	public Screen getScreen(){
		return screen;
	}
		
	/* ---------------------------------------------------------- */

	protected Bridge newBridge(Class<?> firstClass,Class<?> secondClass){
		Bridge tmp = getBridge(firstClass, secondClass);
		if(tmp == null)
			return this.bridgePool.obtain(firstClass, secondClass);
		return tmp;
	}
	
	protected Bridge newBridge(String name){
		Bridge tmp = getBridge(name);
		if(tmp == null)
			return this.bridgePool.obtain(name);
		return tmp;
	}
	
	private Bridge getBridge(Class<?> firstClass,Class<?> secondClass){
		int size = bridgePool.getUsingBridges().size;
		for(int i = 0; i< size;i++){
			if(bridgePool.getUsingBridges().get(i).equals(firstClass, secondClass))
				return bridgePool.getUsingBridges().get(i);
		}
		return null;
	}
	
	private Bridge getBridge(String name){
		final int size = bridgePool.getUsingBridges().size;
		for(int i =0;i < size;i++){
			if(bridgePool.getUsingBridges().get(i).equals(name))
				return bridgePool.getUsingBridges().get(i);
		}
		return null;
	}

	/**********************************************
	 * 
	 **********************************************/
}
