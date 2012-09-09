package okj.easy.screen;

import okj.easy.core.Screen;
import okj.easy.core.eAdmin;

import org.ege.utils.E;
import org.ege.widget.Layout;
import org.ege.widget.Panel;

import com.badlogic.gdx.assets.AssetErrorListener;

public abstract class LoadingScreen extends MainScreen implements AssetErrorListener {

	protected float			progress;
	private boolean			isDone;
	boolean					autoChangeScreen	= true;

	/**
	 * Flag show that this is the first time load,otherwise this is a reload
	 */
	boolean					mFirstTimeLoad		= true;
	private Panel			mPanel				= null;

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
		super.show();
		progress = 0;
		isDone = false;

		if (mFirstTimeLoad)
			onLoadData();
		else {
			Layout layout = getScreenLayout();
			if (mPanel == null)
				mPanel = layout.createPanel();
			else {
				layout.setCurrentPanel(mPanel);
				layout.setToCurrent();
			}
		}
	}

	@Override
	public void onUpdate (float delta) {
		isDone = eAdmin.econtext.update() & (eAdmin.econtext.getQueueAssets() == 0);
		isDone = isDone & eAdmin.eaudio.update() & (eAdmin.eaudio.getQueueAssets() == 0);

		progress = (eAdmin.econtext.getProgress() + eAdmin.eaudio.getProgress()) / 2;

		if (isDone & autoChangeScreen) {
			mFirstTimeLoad = false;
			
			setScreen(mNewScreen.screenChanged(), E.screen.RELEASE);
			return;
		}
	}

	/**********************************************************
	 * 
	 **********************************************************/
	/**
	 * You only should call ResourceContext.load() in this method .This will
	 * help you ensure that context only load one time event when you come back
	 * from onResume()
	 */
	public abstract void onLoadData ();

	@Deprecated
	public void onResume () {
	}

	@Deprecated
	public void onPause () {
	}

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
