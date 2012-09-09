package okj.easy.screen;

import okj.easy.core.ResourcePack;
import okj.easy.core.Screen;
import okj.easy.core.eAdmin;

import org.ege.utils.E;

public abstract class SafeLoader extends Screen {
	public static final String	name	= SafeLoader.class.getName();

	private Screen				mDstScreen;
	protected ResourcePack		resources;

	protected float				progress;

	@Override
	public void show () {
		onCreate();
	}

	@Override
	public void resize (int width, int height) {
		super.resize(width, height);
		onResize(width, height);
	}

	@Override
	public void update (float delta) {
		super.update(delta);
		eAdmin.econtext.update();
		eAdmin.eaudio.update();
		if (resources.isTotallyLoaded())
			setScreen(mDstScreen, E.screen.RELEASE);
		progress = (eAdmin.econtext.getProgress() + eAdmin.eaudio.getProgress()) / 2;
	}

	@Override
	public void destroy (int destroyMode) {
		super.destroy(destroyMode);
		onDestroy();
	}

	/*****************************************************
	 * Screen method
	 *****************************************************/

	public abstract void onCreate ();

	public abstract void onResize (int width, int height);

	public abstract void onDestroy ();

	public void enableSafeMode (Screen screen, ResourcePack pack) {
		mDstScreen = screen;

		resources = pack;
	}

}
