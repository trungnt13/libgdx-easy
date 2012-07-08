package group.easy.tests;

import okj.easy.core.GameScreen;

import com.badlogic.gdx.Gdx;

public class BridgeTest1 extends GameScreen{

	@Override
	public Layout onCreateLayout() {
		return null;
	}

	@Override
	public void onLoadResource() {
		newBridge(BridgeTest1.class, BridgeTest2.class).put("test1", "This is a test");
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public void onUpdate(float delta) {
		if(Gdx.input.justTouched())
			setScreen(new BridgeTest2(), RELEASE);
	}

	@Override
	public void onRender(float delta) {
	}

	@Override
	public void onResize(int width, int height) {
	}

	@Override
	public void onResume() {
	}

}
