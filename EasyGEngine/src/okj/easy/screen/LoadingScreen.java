package okj.easy.screen;

import okj.easy.core.Screen;
import okj.easy.core.eAdmin;

import org.ege.utils.E;
import org.ege.widget.Layout;

import com.badlogic.gdx.assets.AssetErrorListener;

public abstract class LoadingScreen extends Screen implements AssetErrorListener {

	protected float			progress;
	private boolean			isDone;
	boolean					autoChangeScreen	= true;

	/**
	 * Flag show that this is the first time load,otherwise this is a reload
	 */
	boolean					mFirstTimeLoad		= true;

	private OnChangedScreen	mNewScreen;

	public void setAutoChangeScreen (boolean isAuto) {
		this.autoChangeScreen = isAuto;
	}

	public LoadingScreen setChangedScreenListener (OnChangedScreen newScreen) {
		this.mNewScreen = newScreen;
		return this;
	}

	/**********************************************************
	 * 
	 **********************************************************/
	@Override
	public void show () {
		progress = 0;
		isDone = false;

		if (mFirstTimeLoad)
			onLoadData();
		else {
			if (isLayoutCreated()) {
				Layout layout = getScreenLayout();
				layout.createSafeModePanel();
			}
		}

		onCreate();
	}

	@Override
	public void update (float delta) {
		super.update(delta);
		isDone = eAdmin.econtext.update() & (eAdmin.econtext.getQueueAssets() == 0);
		isDone = isDone & eAdmin.eaudio.update() & (eAdmin.eaudio.getQueueAssets() == 0);

		progress = (eAdmin.econtext.getProgress() + eAdmin.eaudio.getProgress()) / 2;

		if (isDone & autoChangeScreen) {
			mFirstTimeLoad = false;
			setScreen(mNewScreen.screenChanged(), E.screen.RELEASE);
			return;
		}
	}

	@Override
	public void destroy (int destroyMode) {
		eAdmin.einput.unregisterBackKeyListener();
		batch.flush();
		if (isLayoutCreated()) {
			Layout layout = getScreenLayout();
			// if in restore mode
			layout.restore();
			if (!mFirstTimeLoad)
				layout.clear();
		}
		onDestroy();
	}

	/**********************************************************
	 * 
	 **********************************************************/

	public abstract void onCreate ();

	/**
	 * You only should call ResourceContext.load() in this method .This will
	 * help you ensure that context only load one time event when you come back
	 * from onResume()
	 */
	public abstract void onLoadData ();

	public abstract void onDestroy ();

	/**********************************************************
	 * Loading method
	 **********************************************************/

	@Override
	public void error (String fileName, Class type, Throwable throwable) {
	}

	public void enableBugTracker () {
		eAdmin.econtext.manager.setErrorListener(this);
		eAdmin.eaudio.manager.setErrorListener(this);
	}

	public static interface OnChangedScreen {
		public Screen screenChanged ();
	}
}
