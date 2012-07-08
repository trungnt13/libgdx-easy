package okj.easy.core;

import okj.easy.core.utils.Bridge;

import org.ege.widget.Layout;

public abstract class GameScreen extends Screen  {
	protected static  GameCore mGameCore;
	
	protected Layout layout = null;
	
	private boolean PAUSE = false;
	
	@Override
	public void show(GameCore gamecore) {
		if(this.PAUSE == true){
			resume();
			return;
		}
		
		mGameCore = gamecore;
			
		projection.setToOrtho(0,SCREEN_WIDTH, 0,SCREEN_HEIGHT , -1, 1);
	
		batch.setProjectionMatrix(projection);
		
		layout = onCreateLayout();
		onLoadResource();
	}

	/**
	 * This method will load the layout for User Interface 
	 * @return your layout
	 */	
	public abstract Layout onCreateLayout();
	
	
	/**
	 * This method for loading some add resource(maybe it is unnecessary if there loading sreen)
	 */		
	public abstract void onLoadResource();
	

	@Override
	public void destroy(int destroyMode) {
		if(destroyMode == RELEASE){
			if(layout != null){
				layout.dispose();
			}
			batch.flush();
			onDestroy();
		}else if(destroyMode == HIDE){
			pause();
		}
	}
	
	@Override
	public void resume() {
		if(layout != null){
			layout.Resume();
		}
		this.PAUSE = false;
		onResume();
	}

	public abstract void onResume();	
	
	@Override
	public void pause() {
		if(layout!= null)
			layout.Pause();
		this.PAUSE = true;
		onPause();
	}
	
	public abstract void onPause();

	/**
	 * Only be called when you call setScreen(RELEASE mode)
	 */
	public abstract void onDestroy();
	
	@Override
	public void update(float delta) {
		if(!PAUSE){
			onUpdate(delta);
			if(layout != null)
				layout.act(delta);
		}
	}

	public abstract void onUpdate(float delta); 
		

	@Override
	public void render(float delta) {
		onRender(delta);
	}

	/**
	 * render all stuff and the layout will be the highest layer
	 * @param delta Gdx.graphics.getDeltaTime()
	 */
	public abstract void onRender(float delta);
	

	/**
	 * Set screen which lead to the new screen and the destroy mode of old screen
	 * @param screen new screen
	 * @param destroyMode destroy mode(RELEASE for totally destroy , HIDE -just make it invisible)
	 */
	protected void setScreen(Screen screen,int destroyMode) {
		mGameCore.setScreen(screen,destroyMode);
	}
	

	@Override
	public void resize(int width, int height) {
		onResize(width,height);
	}
	
	public abstract void onResize(int width,int height);



	/************************************************************
	 * Bridge method
	 ************************************************************/
	public Bridge newBridge(Class<?> firstClass,Class<?> secondClass){
		return mGameCore.newBridge(firstClass, secondClass);
	}
	
	public Bridge newBridge(String firstName,String secondName){
		return mGameCore.newBridge(firstName, secondName);
	}
	
}
