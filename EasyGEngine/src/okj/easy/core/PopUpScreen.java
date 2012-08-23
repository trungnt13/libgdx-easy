package okj.easy.core;


public abstract class PopUpScreen extends GameScreen{
	protected GameScreen parent;
	
	public PopUpScreen(GameScreen parent){
		this.parent = parent;
	}

	@Override
	public void destroy(int destroyMode) {
		clearLayout();
		batch.flush();
		onDestroy();
	}


	@Override
	public void onPause() {
		
	}

	@Override
	public void onRender(float delta) {
		parent.render(delta);
		onRenders(delta);
	}

	/**
	 * Dont call glClear at Rendering of PopUP (the parent screen will be clear)
	 * @param delta
	 */
	public abstract void onRenders(float delta) ;
		
	@Override
	public void onResume() {
		
	}

}
