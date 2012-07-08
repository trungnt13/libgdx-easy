package group.easy.tests;

import org.ege.utils.D;

import okj.easy.core.GameScreen;

public class BridgeTest2 extends GameScreen{

	@Override
	public Layout onCreateLayout() {
		return null;
		
	}

	@Override
	public void onLoadResource() {
		D.out(newBridge(BridgeTest1.class, BridgeTest2.class).getString("test"));
		D.out((String)newBridge(BridgeTest1.class, BridgeTest2.class).get("test1"));
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public void onUpdate(float delta) {
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
