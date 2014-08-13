
package stu.tnt.platform;

public interface OnHighscoreListener {

	public void onSendScoreSuccess (String message);

	public void onSendScoreFailure (String message);

	public void onSendScoreDeny (String message);
}
