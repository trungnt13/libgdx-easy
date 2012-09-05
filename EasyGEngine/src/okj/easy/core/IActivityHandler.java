package okj.easy.core;

import com.badlogic.gdx.Preferences;

/**
 * A simple class to handle some callback from User Activity
 * @author Trung
 *
 */
public interface IActivityHandler {
	
	/**************************************************
	 * 	functional method
	 **************************************************/
	
	public void onShare();
	
	public void onDownload();

	/**************************************************
	 * 	Admob method
	 **************************************************/

	public void onShowAdmob();
	
	public void onPaidAdmob();
	
	
	/**************************************************
	 * 	functional method
	 **************************************************/

	public void onRequestPayment();
	
	public void setPaymentListener(OnPaymentListener listener);
	
	public Preferences onLoadPreferences (String package_dir,String name);
	
	/**************************************************
	 * 
	 **************************************************/
	
	public static interface OnPaymentListener {
		public void onPaymentSuccess (String value);
		
		public void onPaymentFailure (String value);
		
		public void onPaymentDeny (String value);
	}
}
