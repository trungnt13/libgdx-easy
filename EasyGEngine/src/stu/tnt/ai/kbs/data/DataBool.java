package stu.tnt.ai.kbs.data;

import stu.tnt.ai.AIConst;

public class DataBool extends DataRaw<Boolean> {
	DataBool(String name) {
		super(name);
	}

	@Override
	protected void copyInternal(Boolean raw) {
		currValue = raw;
	}

	@Override
	protected void pushInternal(Boolean raw) {
		currValue &= raw;
	}

	@Override
	public Boolean defData() {
		return false;
	}

	@Override
	public String prefix() {
		return AIConst.DataBool_Prefix;
	}

}
