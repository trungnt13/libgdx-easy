package stu.tnt.platform;

import stu.tnt.gdx.utils.D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class DesktopHandler implements IActivityHandler {
	@Override
	public void onDownload() {
		D.out(" On Download");
	}

	@Override
	public Preferences onLoadPreferences() {
		return Gdx.app.getPreferences("Desktop");
	}

	@Override
	public Preferences onLoadPreferences(String arg0, String arg1) {
		return Gdx.app.getPreferences(arg0 + "." + arg1);
	}

	@Override
	public void onLogoutHighScore() {
		D.out(" Logout high score");
	}

	@Override
	public void onPaidAdmob() {
		D.out(" Paid admob");
	}

	@Override
	public void onRequestPayment() {
		D.out(" Request Payment");
	}

	@Override
	public void onShare() {
		D.out(" Share");
	}

	@Override
	public void onShowAdmob() {
		D.out(" Show admob");
	}

	@Override
	public void onToast(String arg0, int arg1) {
		D.out(" On Toast  " + arg0 + "  \n LENGTH:" + arg1);
	}

	@Override
	public void onTrackPageView(String arg0) {
		D.out(" TRack Page View " + arg0);
	}

	@Override
	public void postHighScore(long arg0) {
		D.out(" Post High Score");
	}

	@Override
	public void postVisitRunnable(Runnable arg0) {
		D.out(" Post Visit RUnnable");
	}

	@Override
	public void setHighscoreListener(OnHighscoreListener arg0) {
		D.out(" High Score LIstener");
	}

	@Override
	public void setPaymentListener(OnPaymentListener arg0) {
		D.out(" Payment LIstener");
	}

}
