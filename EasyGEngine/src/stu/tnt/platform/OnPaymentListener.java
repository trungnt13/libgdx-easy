
package stu.tnt.platform;

public interface OnPaymentListener {
	public void onPaymentSuccess (PaymentType type, int value);

	public void onPaymentFailure (PaymentType type, int value);

	public void onPaymentDeny (PaymentType type, int value);

}
