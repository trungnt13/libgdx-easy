package okj.easy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.D;

public class IDesktopHandler implements IActivityHandler {

	@Override
	public void onShare () {
		D.out("On Share !!!!");
	}

	@Override
	public void onDownload () {
		D.out("On Download !!!!");
	}

	@Override
	public void onShowAdmob () {
		D.out("On Show Admob !!!!");
	}

	@Override
	public void onPaidAdmob () {
		D.out("On Paid Admob !!!!");
	}

	@Override
	public void onRequestPayment () {
		D.out("On Request Payment !!!!");
	}

	@Override
	public void setPaymentListener (OnPaymentListener listener) {
		D.out("On Payment Listener !!!!");
	}

	@Override
	public Preferences onLoadPreferences (String package_dir, String name) {
		return Gdx.app.getPreferences(package_dir + "." + name);
	}

}
