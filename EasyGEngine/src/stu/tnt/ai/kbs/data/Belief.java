package stu.tnt.ai.kbs.data;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import stu.tnt.ai.AIConst;
import stu.tnt.ai.kbs.KnowledgeBase;

public class Belief extends Valueable {
	private final KnowledgeBase kbs;

	private final HashMap<Information, Boolean> mExpectedValue = new HashMap<Information, Boolean>();

	public Belief(KnowledgeBase kbs) {
		this.kbs = kbs;
	}

	public void bind(String message, String[] informations, boolean[] values) {
		if (informations.length != values.length)
			throw new RuntimeException(
					"Information length and Values length must be the same!");

		for (int i = 0; i < informations.length; i++) {
			String mess = informations[i];
			boolean val = values[i];

			Information info = kbs.findInformation(mess);
			if (!info.isBinded())
				continue;

			mExpectedValue.put(info, val);
		}

		setMessage(message);
	}

	// ///////////////////////////////////////////////////////////////
	// override methods
	// ///////////////////////////////////////////////////////////////

	@Override
	protected void resetInternal() {
		mExpectedValue.clear();
	}

	@Override
	protected String prefix() {
		return AIConst.Belief_Prefix;
	}

	@Override
	protected boolean isTrueInternal() {
		final Set<Entry<Information, Boolean>> entries = mExpectedValue
				.entrySet();
		for (Entry<Information, Boolean> entry : entries) {
			if (entry.getValue() != entry.getKey().isTrue())
				return false;
		}
		return true;
	}
}
