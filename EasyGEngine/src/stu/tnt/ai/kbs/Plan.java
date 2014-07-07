package stu.tnt.ai.kbs;

import java.util.ArrayList;

import stu.tnt.Updateable;
import stu.tnt.ai.AIConst;
import stu.tnt.ai.agent.AgentDebuger;
import stu.tnt.ai.kbs.Action.ActionCompleted;
import stu.tnt.gdx.utils.ArrayDeque;

/**
 * Execute Step in Plan step by step
 */
public class Plan implements Updateable, ActionCompleted {
	private final ArrayList<Step> mSteps = new ArrayList<Step>();
	private final ArrayDeque<Step> mProcessingSteps = new ArrayDeque<Step>();
	private static final float DELAY_FOR_GIVE_UP_STEP = 10f;

	/**
	 * higher is better
	 */
	private int mPriority = 0;

	private String mName;

	private boolean isProcessing;

	private boolean isCurrentActionCompleted = false;
	private Step lastStep = null;

	private Goal mGoal;
	private PlanCompleted mListener;

	private final KnowledgeBase kbs;

	private float countDown = -1;

	Plan(KnowledgeBase kbs, String planName, int priority, String... stepName) {
		this.kbs = kbs;
		bind(planName, priority, stepName);
	}

	private final void bind(String planName, int priority, String... stepName) {
		isProcessing = false;
		mSteps.clear();
		mProcessingSteps.clear();
		mPriority = 0;

		if (!planName.contains(AIConst.Plan_Prefix))
			throw new RuntimeException("Plan Name must contain prefix: "
					+ planName);

		this.mName = planName;
		this.mPriority = priority;

		for (String string : stepName) {
			final Step step = kbs.findStep(string);
			if (step.isBinded()) {
				mSteps.add(step);
				mProcessingSteps.addLast(step);
			}
		}
	}

	public String getGoalName() {
		return mGoal.getGoalName();
	}

	public void setGoal(Goal mGoal) {
		this.mGoal = mGoal;
	}

	public void setCompletedListener(PlanCompleted listener) {
		this.mListener = listener;
	}

	public boolean isProcessing() {
		return isProcessing;
	}

	public int getPriority() {
		return mPriority;
	}

	public String getPlanName() {
		return mName;
	}

	public boolean isApplicable() {
		if (!isProcessing) {
			Step first = mProcessingSteps.peekFirst();
			if (first != null && first.isTrue())
				return true;
		}
		return false;
	}

	// ///////////////////////////////////////////////////////////////
	// Override methods
	// ///////////////////////////////////////////////////////////////

	public void startPlan() {
		isProcessing = true;
		isCurrentActionCompleted = true;
	}

	@Override
	public void update(float delta) {
		if (isProcessing && isCurrentActionCompleted) {
			Step cur = mProcessingSteps.getFirst();
			// contain new step
			if (cur != null && cur.isTrue()) {
				lastStep = mProcessingSteps.pollFirst();
				countDown = 0;
				Action action = cur.action();
				action.setActionCallback(this);
				isCurrentActionCompleted = false;

				AgentDebuger.log("Execute Step:" + lastStep.getMessage()
						+ "  Action:" + action.Name + " from plan:"
						+ getPlanName());
				if (mGoal != null)
					mGoal.executeAction(cur.action());
			}
			// end of steps (completed)
			else {
				resetPlan();
			}
		}

		// check out condition
		if (lastStep != null) {
			if (lastStep.isOut()) {
				AgentDebuger.log("Plan is out:" + lastStep.getMessage()
						+ " from plan:" + getPlanName());

				resetPlan();
			}
		}

		if (countDown >= 0) {
			countDown += delta;
			if (countDown > DELAY_FOR_GIVE_UP_STEP) {
				AgentDebuger.log("Plan is coundown:" + lastStep.getMessage()
						+ " from plan:" + getPlanName() + "  is process:"
						+ isProcessing + "  is action complete:"
						+ isCurrentActionCompleted);
				resetPlan();
			}
		}
	}

	private final void resetPlan() {
		mProcessingSteps.clear();
		for (Step s : mSteps) {
			mProcessingSteps.addLast(s);
		}
		lastStep = null;
		isProcessing = false;
		countDown = -1;
		isCurrentActionCompleted = false;
		mListener.planCompleted(this);
	}

	@Override
	public void actionCompleted() {
		isCurrentActionCompleted = true;
	}

	public String toString() {
		StringBuilder tmp = new StringBuilder();
		tmp.append("Name:" + getPlanName() + "  Priority:" + mPriority
				+ "  IsProcess:" + isProcessing + "  IsCurrentAction:"
				+ isCurrentActionCompleted + "  Goal:" + mGoal.getGoalName());

		for (Step s : mSteps) {
			tmp.append(s.getMessage() + " - ");
		}
		tmp.append("\n");
		for (Step s : mProcessingSteps) {
			tmp.append(s.getMessage() + " - ");
		}

		return tmp.toString();
	}

	// ///////////////////////////////////////////////////////////////
	// interaction interface
	// ///////////////////////////////////////////////////////////////
	static interface PlanCompleted {
		public void planCompleted(Plan plan);
	}
}
