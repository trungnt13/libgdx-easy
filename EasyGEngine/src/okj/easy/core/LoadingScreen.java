package okj.easy.core;

import okj.easy.admin.Screen;
import okj.easy.admin.eAdmin;
import okj.easy.core.utils.Bridge;

import com.badlogic.gdx.assets.AssetErrorListener;

public abstract class LoadingScreen extends Screen implements AssetErrorListener{
	
	GameScreen nextScreen ;
	GameCore mGameCore;
	
	protected float progress = 0;
	protected boolean isDone = false;
	boolean autoChangeScreen = true;
	
	public LoadingScreen(){
		this.isDone = false;
	}

	@Override
	public void show(GameCore gameCore) {
		this.mGameCore = gameCore;
		
		projection.setToOrtho(0,eAdmin.gameWidth(), 0,eAdmin.gameHeight() , -1, 1);
		
		batch.setProjectionMatrix(projection);
		
		onCreateRenderingComponent();
		
		onLoadData();
	}
	

	public void setAutoChangeScreen(boolean isAuto){
		this.autoChangeScreen = isAuto;
	}
	
	/**********************************************************
	 * 
	 **********************************************************/
	
	/**
	 * Create rendering component for drawing loading screen (just a dynamic component and 
	 * you should release them right after this screen by call override dispose)
	 */
	public abstract void onCreateRenderingComponent();
	
	/**
	 * Loading the album to play audio
	 * @return
	 */
	public abstract void onLoadData();

	@Override
	public void render(float delta) {
		if(isDone & autoChangeScreen)
			setScreen(onChangedScreen(), RELEASE);
		if(batch != null)
			onRender(delta);
	}

	/**********************************************************
	 * 
	 **********************************************************/

	public abstract void onRender(float delta);

	@Override
	public void update(float delta) {
		isDone = eAdmin.econtext.update() & (eAdmin.econtext.getQueueAssets() == 0) ;
		isDone = isDone & eAdmin.eaudio.update() & (eAdmin.eaudio.getQueueAssets() == 0);
		
		progress = (   eAdmin.econtext.getProgress() 
					+  eAdmin.eaudio.getProgress() ) /2;
	}

	/**********************************************************
	 * 
	 **********************************************************/
	
	@Override
	public void resize(int width, int height) {
		onSizeChanged(width,height);
	}

	public abstract void onSizeChanged(int width, int height) ;

	public abstract Screen onChangedScreen();

	/**********************************************************
	 * 
	 **********************************************************/

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
	
	}


	/**********************************************************
	 * 
	 **********************************************************/

	/**
	 * You should call override this method for effective release memory(just release the rendering resource)
	 */
	@Override
	public void destroy(int destroyMode) {
		batch.flush();
		onDestroy();
	}

	public abstract void onDestroy () ;


	/**********************************************************
	 * 
	 **********************************************************/
	
	/**
	 * Set screen which lead to the new screen and the destroy mode of old screen
	 * @param screen new screen
	 * @param destroyMode destroy mode(RELEASE for totally destroy , HIDE -just make it invisible)
	 */
	private void setScreen(Screen screen,int destroyMode) {
		mGameCore.setScreen(screen,destroyMode);
	}

	@Override
	public void error(String fileName, Class type, Throwable throwable) {
		
	}
	
	/************************************************************
	 * Bridge method
	 ************************************************************/
	public Bridge newBridge(Class<?> firstClass,Class<?> secondClass){
		return mGameCore.newBridge(firstClass, secondClass);
	}
	
	public Bridge newBridge(String name){
		return mGameCore.newBridge(name);
	}
}
