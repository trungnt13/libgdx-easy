package stu.tnt.ai;

public final class AIConst {

	/*--------  --------*/

	public static final String DataNumb_Prefix = "NUMB_";

	public static final String toDataNumb(String arg) {
		return DataNumb_Prefix + arg;
	}

	/*--------  --------*/

	public static final String DataBool_Prefix = "BOOL_";

	public static final String toDataBool(String arg) {
		return DataBool_Prefix + arg;
	}

	/*--------  --------*/

	public static final String DataString_Prefix = "STRING_";

	public static final String toDataString(String arg) {
		return DataString_Prefix + arg;
	}

	/*--------  --------*/

	public static final String Information_Prefix = "INFO_";

	public static final String toInfo(String arg) {
		return Information_Prefix + arg;
	}

	/*--------  --------*/

	public static final String Belief_Prefix = "BELIEF_";

	public static final String toBelief(String arg) {
		return Belief_Prefix + arg;
	}

	/*--------  --------*/

	public static final String Step_Prefix = "STEP_";

	public static final String toStep(String arg) {
		return Step_Prefix + arg;
	}

	/*--------  --------*/

	public static final String Action_Prefix = "ACTION_";

	public static final String toAction(String arg) {
		return Action_Prefix + arg;
	}

	/*--------  --------*/

	public static final String Plan_Prefix = "PLAN_";

	public static final String toPlan(String arg) {
		return Plan_Prefix + arg;
	}

}
