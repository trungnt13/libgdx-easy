package okj.easy.core.utils;

import okj.easy.core.eAdmin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.D;

/**
 * 
 * @FileName: IDesktopHandler.java
 * @CreateOn: Sep 15, 2012 - 11:13:23 AM
 * @Author: TrungNT
 */
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

	@Override
	public Preferences onLoadPreferences () {
		return Gdx.app.getPreferences(eAdmin.egame.getClass().getName());
	}

	@Override
	public void onToast (String text, int duration) {
		D.out(" **** Toast ****  " + text + (duration == 1 ? "LONG_DURATION" : "SHORT_DURATION"));
	}

}
