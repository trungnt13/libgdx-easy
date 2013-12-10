package stu.tnt.ai.kbs;

import stu.tnt.ai.AIConst;

public abstract class Action implements Runnable {
	private ActionCompleted mCallback;
	private boolean isInNewThread;
	private Goal mGoal;
	public final String Name;

	public Action(String Name, boolean isInNewThread) {
		if (!Name.contains(AIConst.Action_Prefix)) {
			throw new RuntimeException("Action name must contain prefix: "
					+ AIConst.Action_Prefix);
		}

		this.Name = Name;
		this.isInNewThread = isInNewThread;
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
