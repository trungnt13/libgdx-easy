package stu.tnt.ai.kbs;

import java.util.ArrayList;

import stu.tnt.ai.AIConst;
import stu.tnt.ai.kbs.data.Valueable;

/**
 * Step become true when all valueable in it are true
 */
public class Step extends Valueable {
	private final ArrayList<Valueable> mValueables = new ArrayList<Valueable>();
	private int trueCount = 0;

	private final KnowledgeBase kbs;

	private Action mAction;

	Step(KnowledgeBase kbs) {
		this.kbs = kbs;
	}

	public void bind(String stepName, String actionName,
			String... valueableName) {
		for (String string : valueableName) {
			final Valueable val = kbs().findValueable(string);
			if (val.isBinded()) {
				mValueables.add(val);
			}
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

	// ///////////////////////////////////////////////////////////////
	// override methods
	// ///////////////////////////////////////////////////////////////

	@Override
	protected void resetInternal() {
		trueCount = 0;
		mAction = null;
		mValueables.clear();
	}

	@Override
	protected String prefix() {
		return AIConst.Step_Prefix;
	}

	@Override
	protected boolean isTrueInternal() {
		trueCount = 0;
		for (Valueable val : mValueables) {
			if (val.isTrue())
				++trueCount;
		}

		if (trueCount == mValueables.size()) {
			return true;
		}
		return false;
	}
}
