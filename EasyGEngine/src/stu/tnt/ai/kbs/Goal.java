package stu.tnt.ai.kbs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import stu.tnt.Updateable;
import stu.tnt.ai.agent.AgentDebuger;
import stu.tnt.ai.kbs.Plan.PlanCompleted;

public class Goal implements Updateable, PlanCompleted {
	private static final ExecutorService mThreadPool = Executors
			.newFixedThreadPool(1);

	private HashMap<String, Plan> mPlanMap = new HashMap<String, Plan>();
	private ArrayList<Plan> mReadyPlan = new ArrayList<Plan>();

	private final KnowledgeBase kbs;

	/**
	 * higher is better
	 */
	private int mPriority = 0;
	private float mDelay = 0;
	private int maxSimultaneousPlan = 1;

	private String mGoalName;

	private float countDown = 0;
	private int executingPlan = 0;

	Goal(KnowledgeBase kbs, String name, int priority, int sumultaneous,
			float delay, String... planNames) {
		this.kbs = kbs;
		this.mGoalName = name;
		this.mPriority = priority;
		this.maxSimultaneousPlan = sumultaneous;
		this.mDelay = delay;
		bind(planNames);
	}

	private final void bind(String... planNames) {
		for (String string : planNames) {
			Plan p = kbs.findPlan(string);
			p.setGoal(this);
			p.setCompletedListener(this);
			mPlanMap.put(string, p);
		}
	}

	public KnowledgeBase kbs() {
		return kbs;
	}

	// ///////////////////////////////////////////////////////////////
	// control methods
	// ///////////////////////////////////////////////////////////////

	void executeAction(Action action) {
		action.setGoal(this);
		if (action.isUseThread()) {
			mThreadPool.execute(action);
		} else
			action.run();
	}

	private void executePlan(int numbOfPlans) {
		Collections.sort(mReadyPlan, kbs.conflictResolution().planComparator());
		for (int i = 0; i < numbOfPlans; i++) {
			final Plan p = mReadyPlan.get(i);
			AgentDebuger.log("Start Plan:" + p.getPlanName() + "  from goal:"
					+ p.getGoalName() + "  in total:" + numbOfPlans);
			p.startPlan();
		}
		mReadyPlan.clear();
		executingPlan = numbOfPlans;
	}

	// ///////////////////////////////////////////////////////////////
	// get set methods
	// ///////////////////////////////////////////////////////////////
	public int getPriority() {
		return mPriority;
	}

	public float getDelay() {
		return mDelay;
	}

	public float getSimultaneousPlan() {
		return maxSimultaneousPlan;
	}

	public int getNumbOfPlan() {
		return mPlanMap.size();
	}

	public String getGoalName() {
		return mGoalName;
	}

	// ///////////////////////////////////////////////////////////////
	// override methos
	// ///////////////////////////////////////////////////////////////

	@Override
	public void update(float delta) {

		// update plan
		final Collection<Plan> set = mPlanMap.values();
		for (Plan p : set) {
			p.update(delta);
		}

		// pick ready plan
		for (Plan plan : set) {
			if (plan.isApplicable() && !mReadyPlan.contains(plan)) {
				AgentDebuger.log("New Ready Plan:" + plan.getPlanName()
						+ "  from goal+" + plan.getGoalName());
				mReadyPlan.add(plan);
			}
		}

		countDown += delta;
		// start execuse
		if (countDown >= mDelay) {
			if (executingPlan < maxSimultaneousPlan) {
				executePlan(maxSimultaneousPlan - executingPlan);
			}
		}
	}

	@Override
	public void planCompleted(Plan plan) {
		--executingPlan;
	}

	public String toString() {
		StringBuilder tmp = new StringBuilder();
		tmp.append("Name:" + getGoalName() + "  Priority:" + mPriority
				+ "  Delay:" + mDelay + "  Max:" + maxSimultaneousPlan
				+ "  Executing:" + executingPlan + "\n");

		for (Plan p : mPlanMap.values()) {
			tmp.append(p.getPlanName() + " ");
		}
		tmp.append("\n");
		for (Plan p : mReadyPlan) {
			tmp.append(p.getPlanName() + " ");
		}
		return tmp.toString();
	}
}
