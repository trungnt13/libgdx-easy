package stu.tnt.ai.kbs;

import stu.tnt.ai.AIConst;

public class Action implements Runnable {
	private ActionCompleted mCallback;
	private boolean isInNewThread;
	private Goal mGoal;
	public final String Name;
	private final Runnable mRun;

	public Action(String Name, boolean isInNewThread, Runnable run) {
		if (!Name.contains(AIConst.Action_Prefix)) {
			throw new RuntimeException("Action name must contain prefix: "
					+ AIConst.Action_Prefix);
		}

		this.Name = Name;
		this.mRun = run;
		this.isInNewThread = isInNewThread;
	}

	public void run() {
		mRun.run();
		if (mCallback != null)
			mCallback.actionCompleted();
	}

	// ///////////////////////////////////////////////////////////////
	// getter setter
	// ///////////////////////////////////////////////////////////////

	public Goal goal() {
		return mGoal;
	}

	void setGoal(Goal goal) {
		this.mGoal = goal;
	}

	public ActionCompleted getActionCallback() {
		return mCallback;
	}

	public void setActionCallback(ActionCompleted mCallback) {
		this.mCallback = mCallback;
	}

	public boolean isUseThread() {
		return isInNewThread;
	}

	// ///////////////////////////////////////////////////////////////
	// helper thread
	// ///////////////////////////////////////////////////////////////

	public static interface ActionCompleted {
		public void actionCompleted();
	}

}
