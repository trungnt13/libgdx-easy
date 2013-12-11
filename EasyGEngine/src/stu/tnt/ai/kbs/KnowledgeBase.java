package stu.tnt.ai.kbs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import stu.tnt.Updateable;
import stu.tnt.ai.AIConst;
import stu.tnt.ai.kbs.data.Belief;
import stu.tnt.ai.kbs.data.DataRaw;
import stu.tnt.ai.kbs.data.DataRaw.RecordingFrequency;
import stu.tnt.ai.kbs.data.DataSet;
import stu.tnt.ai.kbs.data.Information;
import stu.tnt.ai.kbs.data.Information.InfoApplicable;
import stu.tnt.ai.kbs.data.Valueable;
import stu.tnt.gdx.utils.Factory;
import stu.tnt.gdx.utils.Pool;

public class KnowledgeBase implements Updateable {

	/*-------- data base info --------*/
	private final DataSet mDataSet = new DataSet();
	private final HashMap<String, Information> mInfoMap = new HashMap<String, Information>();

	private final HashMap<String, Belief> mBeliefMap = new HashMap<String, Belief>();

	/*-------- rule base info --------*/
	private final HashMap<String, Step> mStepMap = new HashMap<String, Step>();
	private final HashMap<String, Plan> mPlanMap = new HashMap<String, Plan>();

	private final HashMap<String, Goal> mGoalMap = new HashMap<String, Goal>();
	private final ArrayList<Goal> mGoalList = new ArrayList<Goal>();

	/*-------- action --------*/
	private final HashMap<String, Action> mActionMap = new HashMap<String, Action>();

	/*-------- conflict --------*/
	private final ConflictResolution mConflictResolution = new ConflictResolution();

	/*-------- pool --------*/
	private final Pool<Information> mInfoPool = new Pool<Information>(
			new Factory<Information>() {
				@Override
				public Information newObject() {
					return new Information(KnowledgeBase.this);
				}

				@Override
				public Information newObject(Object... objects) {
					return new Information(KnowledgeBase.this);
				}

			});

	private final Pool<Belief> mBeliefPool = new Pool<Belief>(
			new Factory<Belief>() {

				@Override
				public Belief newObject() {
					return new Belief(KnowledgeBase.this);
				}

				@Override
				public Belief newObject(Object... objects) {
					return new Belief(KnowledgeBase.this);
				}
			});

	private final Pool<Step> mStepPool = new Pool<Step>(new Factory<Step>() {
		@Override
		public Step newObject() {
			return new Step(KnowledgeBase.this);
		}

		@Override
		public Step newObject(Object... objects) {
			return new Step(KnowledgeBase.this);
		}
	});

	/*-------- creator --------*/
	private final InformationCreator infoCreator = new InformationCreator();
	private final BeliefCreator beliefCreator = new BeliefCreator();

	private final GoalCreator goalCreator = new GoalCreator();
	private final PlanCreator planCreator = new PlanCreator();
	private final StepCreator stepCreator = new StepCreator();

	/*-------- constructor --------*/
	private boolean isCreateData = false;
	private boolean isCreateKnowledge = false;
	private boolean isStart = true;

	public KnowledgeBase() {
	}

	public void clear() {
		mDataSet.clear();

		mInfoMap.clear();
		mInfoPool.clear();

		mBeliefMap.clear();
		mBeliefPool.clear();

		mStepMap.clear();
		mStepPool.clear();

		mPlanMap.clear();

		mGoalList.clear();
		mGoalMap.clear();
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	// ///////////////////////////////////////////////////////////////
	// create method
	// ///////////////////////////////////////////////////////////////
	public InformationCreator createDataRaw(DataPacking... dataPackings) {
		if (isCreateData)
			throw new RuntimeException("Data is only created once");
		isCreateData = true;
		for (DataPacking dataPacking : dataPackings) {
			DataRaw data = mDataSet.createNewRecord(dataPacking.type,
					dataPacking.name);
			data.setRecording(true, dataPacking.freg, dataPacking.param);
		}
		return infoCreator;
	}

	public StepCreator createActions(Action... actions) {
		if (!isCreateData)
			throw new RuntimeException("Data must be created first");
		if (isCreateKnowledge)
			throw new RuntimeException("Knowledge only created once");
		isCreateKnowledge = true;

		for (Action action : actions) {
			mActionMap.put(action.Name, action);
		}
		return stepCreator;
	}

	// ///////////////////////////////////////////////////////////////
	// query information methods
	// ///////////////////////////////////////////////////////////////

	public DataSet dataSet() {
		return mDataSet;
	}

	/*-------- data --------*/

	public DataRaw findData(String name) {
		return mDataSet.findDataRecord(name);
	}

	public Information findInformation(String mess) {
		return mInfoMap.get(mess);
	}

	public Belief findBelief(String mess) {
		return mBeliefMap.get(mess);
	}

	public Valueable findValueable(String mess) {
		if (mess.contains(AIConst.Information_Prefix))
			return findInformation(mess);
		else if (mess.contains(AIConst.Belief_Prefix))
			return findBelief(mess);
		return null;
	}

	/*-------- conflict --------*/

	public ConflictResolution conflictResolution() {
		return mConflictResolution;
	}

	/*-------- rule --------*/

	public Step findStep(String mess) {
		return mStepMap.get(mess);
	}

	public Plan findPlan(String planName) {
		return mPlanMap.get(planName);
	}

	public Goal findGoal(String goal) {
		return mGoalMap.get(goal);
	}

	/*-------- action --------*/

	public Action findAction(String actionName) {
		return mActionMap.get(actionName);
	}

	// ///////////////////////////////////////////////////////////////
	// override method
	// ///////////////////////////////////////////////////////////////

	public void update(float delta) {
		mDataSet.update(delta);

		if (!isStart)
			return;
		for (Goal g : mGoalList) {
			g.update(delta);
		}
	}

	// ///////////////////////////////////////////////////////////////
	// helper interface
	// ///////////////////////////////////////////////////////////////

	/*-------- step --------*/

	public class StepCreator {
		private StepCreator() {

		}

		public PlanCreator createSteps(StepPacking... packings) {
			for (StepPacking stepPacking : packings) {
				Step s = KnowledgeBase.this.mStepPool.obtain();
				s.bind(stepPacking.name, stepPacking.action, stepPacking.out,
						stepPacking.valuealbe);
				KnowledgeBase.this.mStepMap.put(s.getMessage(), s);
			}
			return KnowledgeBase.this.planCreator;
		}
	}

	public static class StepPacking {
		private String name;
		private String action;
		private String[] out;
		private String[] valuealbe;

		public StepPacking(String name, String action, String[] out,
				String... strings) {
			this.name = name;
			this.action = action;
			this.out = out;
			this.valuealbe = strings;
		}

		public StepPacking(String name, String action, String... strings) {
			this.name = name;
			this.action = action;
			this.out = null;
			this.valuealbe = strings;
		}

	}

	/*-------- plan --------*/
	public class PlanCreator {
		private PlanCreator() {

		}

		public GoalCreator createPlan(PlanPacking... packings) {
			for (PlanPacking p : packings) {
				Plan tmp = new Plan(KnowledgeBase.this, p.name, p.priority,
						p.stepNames);
				KnowledgeBase.this.mPlanMap.put(tmp.getPlanName(), tmp);
			}
			return KnowledgeBase.this.goalCreator;
		}
	}

	public static class PlanPacking {
		private String name;
		private int priority;
		private String[] stepNames;

		public PlanPacking(String name, int prio, String... strings) {
			this.name = name;
			priority = prio;
			stepNames = strings;
		}
	}

	/*-------- goal --------*/

	public class GoalCreator {
		private GoalCreator() {

		}

		public KnowledgeBase createGoal(GoalPacking... packings) {
			for (GoalPacking g : packings) {
				Goal goal = new Goal(KnowledgeBase.this, g.name, g.priority,
						g.simultaneous, g.delay, g.planName);
				KnowledgeBase.this.mGoalMap.put(goal.getGoalName(), goal);
				KnowledgeBase.this.mGoalList.add(goal);
			}
			Collections.sort(mGoalList, conflictResolution().goalComparator());
			return KnowledgeBase.this;
		}
	}

	public static class GoalPacking {
		private String name;
		private int priority;
		private int simultaneous;
		private float delay;
		private String[] planName;

		public GoalPacking(String name, int priority, int simultaneous,
				float delay, String... strings) {
			this.name = name;
			this.priority = priority;
			this.simultaneous = simultaneous;
			this.delay = delay;
			this.planName = strings;
		}
	}

	/*-------- data raw --------*/
	public static final class DataPacking {
		private Class type;
		private String name;
		private RecordingFrequency freg;
		private float param = 0;

		public DataPacking(Class type, String name, RecordingFrequency freg,
				float param) {
			this.type = type;
			this.name = name;
			this.freg = freg;
			this.param = param;
		}

		public DataPacking(Class type, String name, RecordingFrequency freg) {
			this.type = type;
			this.name = name;
			this.freg = freg;
		}

	}

	/*-------- info --------*/

	public class InformationCreator {
		private InformationCreator() {

		}

		public BeliefCreator createInformation(InfoPacking... info) {
			for (InfoPacking infoPacking : info) {
				Information tmp = KnowledgeBase.this.mInfoPool.obtain();
				tmp.bind(infoPacking.information, infoPacking.message);
				KnowledgeBase.this.mInfoMap.put(tmp.getMessage(), tmp);
			}

			return KnowledgeBase.this.beliefCreator;
		}
	}

	public static class InfoPacking {
		private String message;
		private InfoApplicable information;

		public InfoPacking(String mess, InfoApplicable info) {
			message = mess;
			information = info;
		}
	}

	/*-------- belief --------*/

	public class BeliefCreator {
		private BeliefCreator() {

		}

		public KnowledgeBase createBelief(BeliefPacking... packings) {
			for (BeliefPacking beliefPacking : packings) {
				Belief tmp = KnowledgeBase.this.mBeliefPool.obtain();
				tmp.bind(beliefPacking.message, beliefPacking.informations,
						beliefPacking.values);
				KnowledgeBase.this.mBeliefMap.put(tmp.getMessage(), tmp);
			}
			return KnowledgeBase.this;
		}
	}

	public static final class BeliefPacking {
		private String message;
		private String[] informations;
		private boolean[] values;

		public BeliefPacking(String mess, String[] infos, boolean[] vals) {
			message = mess;
			informations = infos;
			values = vals;
		}
	}

	// ///////////////////////////////////////////////////////////////
	// debug method
	// ///////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(mDataSet.toString());

		builder.append("\n *********** Information ***********\n");
		for (Information info : mInfoMap.values()) {
			builder.append(info.toString() + "\n");
		}

		builder.append("\n *********** Belief ***********\n");
		for (Belief b : mBeliefMap.values()) {
			builder.append(b.toString() + "\n");
		}

		builder.append("\n *********** Step ***********\n");
		for (Step b : mStepMap.values()) {
			builder.append(b.toString() + "\n");
		}

		builder.append("\n *********** Plan ***********\n");
		for (Plan b : mPlanMap.values()) {
			builder.append(b.toString() + "\n");
		}

		builder.append("\n *********** Goal ***********\n");
		for (Goal b : mGoalMap.values()) {
			builder.append(b.toString() + "\n");
		}

		return builder.toString();
	}
}
