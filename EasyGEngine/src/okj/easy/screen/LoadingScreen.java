package okj.easy.screen;

import okj.easy.core.Screen;
import okj.easy.core.eAdmin;

import org.ege.utils.E;
import org.ege.utils.exception.EasyGEngineRuntimeException;
import org.ege.widget.Layout;

import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.utils.D;

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
				D.out(" restore to old layout " + layout.getDefaultPanel().getChildren().size);
			}
		}

		int size = eAdmin.econtext.getQueueAssets() + eAdmin.eaudio.getQueueAssets();
		final OnChangedScreen tmp = this.mNewScreen;
		onCreate();

		// check for sercurity flag at onCreate();
		if (mNewScreen != tmp)
			throw new EasyGEngineRuntimeException("Can't change the next screen at onCreate()");
		if (size != eAdmin.econtext.getQueueAssets() + eAdmin.eaudio.getQueueAssets())
			throw new EasyGEngineRuntimeException("Can't load data at onCreate()");
	}

	@Override
	public void update (float delta) {
		super.update(delta);
		isDone = eAdmin.econtext.update() & (eAdmin.econtext.getQueueAssets() == 0);
		isDone = isDone & eAdmin.eaudio.update() & (eAdmin.eaudio.getQueueAssets() == 0);

		progress = (eAdmin.econtext.getProgress() + eAdmin.eaudio.getProgress()) / 2;

		if (isDone & autoChangeScreen) {
			setScreen(mNewScreen.screenChanged(), E.screen.RELEASE);
			mFirstTimeLoad = false;
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
			if (mFirstTimeLoad)
				layout.clear();
			else
				layout.restore();
		}
		onDestroy();
	}

	/**********************************************************
	 * 
	 **********************************************************/

	/**
	 * The method setChangedScreenListener should be called at onLoadData (not
	 * at this method)
	 */
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

	public boolean isFirstTimeLoad () {
		return mFirstTimeLoad;
	}

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
