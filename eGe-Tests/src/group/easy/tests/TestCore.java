package group.easy.tests;

import okj.easy.core.GameCore;
import okj.easy.core.Screen;

public class TestCore extends GameCore{
	Screen tmp;
	
	public TestCore(Screen screen){
		tmp = screen;
	}
	
	@Override
	protected void onGameCreate() {
		setScreen(tmp, Screen.RELEASE);
	}

	@Override
	protected void onGameChanged(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onGameRender(float DeltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onGameResume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onGameDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGamePause() {
	}

}
