package stu.tnt.ai.kbs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import stu.tnt.Updateable;

public class Goal implements Updateable {
	private static final ExecutorService mThreadPool = Executors
			.newFixedThreadPool(1);

	private HashMap<String, Plan> mPlanMap = new HashMap<String, Plan>();
	private ArrayList<Plan> mReadyPlan = new ArrayList<Plan>();

	private final KnowledgeBase kbs;

	/**
	 * higher is better
	 */
	private int mPriority = 0;

	private String mGoalName;

	Goal(KnowledgeBase kbs, String name, String... planNames) {
		this.kbs = kbs;
		this.mGoalName = name;
		bind(planNames);
	}

	private final void bind(String... planNames) {
		for (String string : planNames) {
			Plan p = kbs.findPlan(string);
			p.setGoal(this);
			mPlanMap.put(string, p);
		}
	}

	public KnowledgeBase kbs() {
		return kbs;
	}

	void executeAction(Action action) {
		action.setGoal(this);
		if (action.isUseThread()) {
			mThreadPool.execute(action);
		} else
			action.run();
	}

	public void executePlan(int numbOfPlans) {
		Collections.sort(mReadyPlan, kbs.conflictResolution().planComparator());
		for (int i = 0; i < numbOfPlans; i++) {
			mReadyPlan.get(i).startPlan();
		}
		mReadyPlan.clear();
	}

	// ///////////////////////////////////////////////////////////////
	// get set methods
	// ///////////////////////////////////////////////////////////////
	public int getPriority() {
		return mPriority;
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
		final Collection<Plan> set = mPlanMap.values();
		for (Plan p : set) {
			p.update(delta);
		}

		for (Plan plan : set) {
			if (plan.isApplicable() && !mReadyPlan.contains(plan)) {
				mReadyPlan.add(plan);
			}
		}
	}
}
