package okj.easy.core;

import okj.easy.admin.eAdmin;
import okj.easy.admin.eAudio;
import okj.easy.admin.eContext;
import okj.easy.admin.eGraphics;
import okj.easy.admin.eInput;
import okj.easy.core.utils.Bridge;
import okj.easy.core.utils.BridgePool;

import org.ege.widget.Dialog;
import org.ege.widget.Layout;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public abstract class GameCore implements ApplicationListener{
	//	----------------------------------------------------------
	protected Screen screen;
	Layout mLayout;

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
		
		Gdx.input.setInputProcessor(eAdmin.einput);
		
		Screen.SCREEN_WIDTH = Gdx.graphics.getWidth();
		Screen.SCREEN_HEIGHT = Gdx.graphics.getHeight();
		
		mLayout = new Layout(true);
		Dialog.DIALOG_NUMBER = 0;
		onGameCreate();
	}
	/**
	 * Initialize all game components
	 */
	protected abstract void onGameCreate();

	/* ---------------------------------------------------------- */
	
	@Override
	public void resize(int width, int height) {
		Layout.$Calculate(width, height);
		Screen.SCREEN_WIDTH = width;
		Screen.SCREEN_HEIGHT = height;
		mLayout.setViewport(Layout.UI_WIDTH, Layout.UI_HEIGHT, true);
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

	@Override
	public void render() {
		final float delta = Gdx.graphics.getDeltaTime();
		if(screen != null){
			screen.render(delta);
			screen.update(delta);
		}
		mLayout.act(delta);
		mLayout.draw();
		onGameRender(delta);
	}
	
	/**
	 * Render all stuff here
	 * @param DeltaTime the time each frame repeat
	 */
	protected abstract void onGameRender(float DeltaTime);
	
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
		
	public Layout getLayout(){
		return mLayout;
	}
	/* ---------------------------------------------------------- */

	protected Bridge newBridge(Class<?> firstClass,Class<?> secondClass){
		Bridge tmp = getBridge(firstClass, secondClass);
		if(tmp == null)
			return this.bridgePool.obtain(firstClass, secondClass);
		return tmp;
	}
	
	protected Bridge newBridge(String firstName,String secondName){
		Bridge tmp = getBridge(firstName, secondName);
		if(tmp == null)
			return this.bridgePool.obtain(firstName, secondName);
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
	
	private Bridge getBridge(String firstName,String secondName){
		int size = bridgePool.getUsingBridges().size;
		for(int i =0;i < size;i++){
			if(bridgePool.getUsingBridges().get(i).equals(firstName, secondName))
				return bridgePool.getUsingBridges().get(i);
		}
		return null;
	}
	
}
