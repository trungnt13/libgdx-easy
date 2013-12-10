package stu.tnt.ai.kbs.data;

import stu.tnt.ai.AIConst;
import stu.tnt.ai.kbs.KnowledgeBase;

/**
 * Purpose of this class is check if Source Object is applicable for Destination
 * Object
 */
public class Information extends Valueable {
	private KnowledgeBase kbs;
	private InfoApplicable checker;

	public Information(KnowledgeBase kbs) {
		this.kbs = kbs;
	}

	public void bind(InfoApplicable checker, String mess) {
		setMessage(mess);
		this.checker = checker;
	}

	// ///////////////////////////////////////////////////////////////
	// override methods
	// ///////////////////////////////////////////////////////////////

	@Override
	public void resetInternal() {
		checker = null;
	}

	/**
	 * interface for check info is true or false
	 */
	public static interface InfoApplicable {
		public boolean isApplicable(KnowledgeBase kbs);
	}

	@Override
	protected String prefix() {
		return AIConst.Information_Prefix;
	}

	@Override
	protected boolean isTrueInternal() {
		return checker.isApplicable(kbs);
	}
}
