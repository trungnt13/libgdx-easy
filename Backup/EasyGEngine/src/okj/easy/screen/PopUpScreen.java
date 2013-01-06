package okj.easy.screen;

public abstract class PopUpScreen extends MainScreen {
	protected MainScreen	parent;

	public PopUpScreen (MainScreen parent) {
		this.parent = parent;
	}

	@Deprecated
	public void onPause () {
	}

	@Override
	public void onRender (float delta) {
		parent.onRender(delta);
	}

	@Deprecated
	public void onResume () {
	}

}
