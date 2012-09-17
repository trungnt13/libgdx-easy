package okj.easy.core.utils;

import com.badlogic.gdx.Preferences;

/**
 * A simple class to handle some callback from User Activity
 * 
 * @FileName: IActivityHandler.java
 * @CreateOn: Sep 15, 2012 - 11:14:22 AM
 * @Author: TrungNT
 */
public interface IActivityHandler {

	/**************************************************
	 * Android special functional
	 **************************************************/

	/**
	 * Create default android toast
	 * 
	 * @param text
	 * @param duration
	 */
	public void onToast (String text, int duration);

	/**************************************************
	 * functional method
	 **************************************************/

	/**
	 * Request for share
	 */
	public void onShare ();

	/**
	 * request for download
	 */
	public void onDownload ();

	/**************************************************
	 * Admob method
	 **************************************************/

	/**
	 * Show the admob
	 */
	public void onShowAdmob ();

	/**
	 * Disable the admob (GONE)
	 */
	public void onPaidAdmob ();

	/**************************************************
	 * functional method
	 **************************************************/

	/**
	 * Request for payment dialog
	 */
	public void onRequestPayment ();

	/**
	 * Set payment listener
	 * 
	 * @param listener
	 *            {@link OnPaymentListener}
	 */
	public void setPaymentListener (OnPaymentListener listener);

	/**
	 * Load the special preferences of your company
	 * 
	 * @param package_dir
	 * @param name
	 * @return
	 */
	public Preferences onLoadPreferences (String package_dir, String name);

	/**
	 * The same as onLoadPreferences (String package_dir, String name) but
	 * <p>
	 * your package dir and pref name will be declared in android activity
	 * 
	 * @return
	 */
	public Preferences onLoadPreferences ();

	/**************************************************
	 * 
	 **************************************************/

	/**
	 * This interface to handle payment event from activity
	 * 
	 * @FileName: IActivityHandler.java
	 * @CreateOn: Sep 16, 2012 - 3:42:14 PM
	 * @Author: TrungNT
	 */
	public static interface OnPaymentListener {
		/**
		 * 
		 * @param type
		 *            {@link PaymentType}
		 * @param value
		 *            the value of purchase
		 */
		public void onPaymentSuccess (PaymentType type, int value);

		/**
		 * 
		 * @param type
		 *            type {@link PaymentType}
		 * @param value
		 *            the value of purchase
		 */
		public void onPaymentFailure (PaymentType type, int value);

		/**
		 * 
		 * @param type
		 *            type {@link PaymentType}
		 * @param value
		 *            the value of purchase
		 */
		public void onPaymentDeny (PaymentType type, int value);
	}

	/**
	 * 
	 * @FileName: IActivityHandler.java
	 * @CreateOn: Sep 16, 2012 - 3:44:54 PM
	 * @Author: TrungNT
	 */
	public static enum PaymentType {
		// tin nhan
		PAYMENT_TYPE_SMS,
		// the cao
		PAYMENT_TYPE_SCRATCH,
		// coupon
		PAYMENT_TYPE_COUPON
	}
}
