package stu.tnt.ai.kbs;

import java.util.Comparator;

public final class ConflictResolution {
	private static final Comparator<Goal> DefGoalComparator = new Comparator<Goal>() {

		@Override
		public int compare(Goal o1, Goal o2) {
			if (o1.getPriority() > o2.getPriority())
				return 1;
			else if (o1.getPriority() < o2.getPriority())
				return -1;
			return o1.getNumbOfPlan() < o2.getNumbOfPlan() ? 1 : -1;
		}
	};

	private static final Comparator<Plan> DefPlanComparator = new Comparator<Plan>() {

		@Override
		public int compare(Plan o1, Plan o2) {
			if (o1.getPriority() > o2.getPriority())
				return 1;
			else if (o1.getPriority() < o2.getPriority())
				return -1;
			return o1.getPlanName().compareTo(o2.getPlanName());
		}
	};

	private Comparator<Goal> mGoalComparator = DefGoalComparator;
	private Comparator<Plan> mPlanComparator = DefPlanComparator;

	public Comparator<Goal> goalComparator() {
		return mGoalComparator;
	}

	public Comparator<Plan> planComparator() {
		return mPlanComparator;
	}

}
