
package stu.tnt.platform;

import com.badlogic.gdx.Preferences;

/**
 * 
 * @author trungnt13
 *
 */
public interface IActivityHandler {
	public void onDownload ();

	public Preferences onLoadPreferences ();

	public Preferences onLoadPreferences (String arg0, String arg1);

	public void onLogoutHighScore ();

	public void onPaidAdmob ();

	public void onRequestPayment ();

	public void onShare ();

	public void onShowAdmob ();

	public void onToast (String arg0, int arg1);

	public void onTrackPageView (String arg0);

	public void postHighScore (long arg0);

	public void postVisitRunnable (Runnable arg0);

	public void setHighscoreListener (OnHighscoreListener arg0);

	public void setPaymentListener (OnPaymentListener arg0);
}
