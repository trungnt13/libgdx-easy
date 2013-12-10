package stu.tnt.ai.kbs;

import java.util.ArrayList;

import stu.tnt.Updateable;
import stu.tnt.ai.AIConst;
import stu.tnt.ai.kbs.Action.ActionCompleted;
import stu.tnt.gdx.utils.ArrayDeque;

/**
 * Execute Step in Plan step by step
 */
public class Plan implements Updateable, ActionCompleted {
	private ArrayList<Step> mSteps = new ArrayList<Step>();
	private final ArrayDeque<Step> mProcessingSteps = new ArrayDeque<Step>();

	/**
	 * higher is better
	 */
	private int mPriority = 0;

	private String mName;

	private boolean isProcessing;

	private boolean isCurrentActionCompleted = false;

	private Goal mGoal;
	private final KnowledgeBase kbs;

	Plan(KnowledgeBase kbs, String planName, int priority, String... stepName) {
		this.kbs = kbs;
		bind(planName, priority, stepName);
	}

	private final void bind(String planName, int priority, String... stepName) {
		isProcessing = false;
		mSteps = null;
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
			Step first = mProcessingSteps.getFirst();
			if (first.isTrue())
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
			if (cur != null) {
				if (cur.isTrue()) {
					mProcessingSteps.pollFirst();
					Action action = cur.action();
					action.setActionCallback(this);
					if (mGoal != null)
						mGoal.executeAction(cur.action());
					isCurrentActionCompleted = false;
				}
			}
			// end of steps
			else {
				mProcessingSteps.clear();
				for (Step s : mSteps) {
					mProcessingSteps.addLast(s);
				}
				isProcessing = false;
				isCurrentActionCompleted = false;
			}
		}
	}

	@Override
	public void actionCompleted() {
		isCurrentActionCompleted = true;
	}
}
