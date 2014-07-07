package stu.tnt.ai.kbs;

import java.util.ArrayList;

import stu.tnt.ai.AIConst;
import stu.tnt.ai.kbs.data.Valueable;

/**
 * Step become true when all valueable in it are true
 */
public class Step extends Valueable {
	private final ArrayList<Valueable> mValueables = new ArrayList<Valueable>();

	/**
	 * this list indicates when step can be stopped
	 */
	private final ArrayList<Valueable> mOutValueables = new ArrayList<Valueable>();

	private final KnowledgeBase kbs;

	private Action mAction;

	Step(KnowledgeBase kbs) {
		this.kbs = kbs;
	}

	public void bind(String stepName, String actionName, String[] outValuabe,
			String... valueableName) {

		if (outValuabe != null)
			for (String string : outValuabe) {
				final Valueable val = kbs().findValueable(string);
				if (val != null && val.isBinded())
					mOutValueables.add(val);
			}

		for (String string : valueableName) {
			final Valueable val = kbs().findValueable(string);
			if (val != null && val.isBinded())
				mValueables.add(val);
		}

		mAction = kbs().findAction(actionName);
		setMessage(stepName);
	}

	public KnowledgeBase kbs() {
		return kbs;
	}

	public Action action() {
		return mAction;
	}

	public boolean isOut() {
		if (mOutValueables.size() == 0)
			return false;

		int trueCount = 0;
		for (Valueable val : mOutValueables) {
			if (val.isTrue())
				++trueCount;
		}

		if (trueCount == mOutValueables.size()) {
			return true;
		}
		return false;
	}

	// ///////////////////////////////////////////////////////////////
	// override methods
	// ///////////////////////////////////////////////////////////////

	@Override
	protected void resetInternal() {
		mAction = null;
		mValueables.clear();
	}

	@Override
	protected String prefix() {
		return AIConst.Step_Prefix;
	}

	@Override
	protected boolean isTrueInternal() {
		int trueCount = 0;
		for (Valueable val : mValueables) {
			if (val.isTrue())
				++trueCount;
		}

		if (trueCount == mValueables.size()) {
			return true;
		}
		return false;
	}

	public String toString() {
		StringBuilder tmp = new StringBuilder();
		tmp.append("Name: " + getMessage() + "\n");
		for (Valueable val : mValueables) {
			tmp.append(val.getMessage() + "\n");
		}
		return tmp.toString();
	}
}
