package stu.tnt.ai.kbs.data;

import stu.tnt.ai.AIConst;

public class DataString extends DataRaw<String> {
	DataString(String name) {
		super(name);
	}

	@Override
	protected void copyInternal(String raw) {
		currValue = raw;
	}

	@Override
	protected void pushInternal(String raw) {
		currValue.concat(raw);
	}

	@Override
	public String defData() {
		return "";
	}

	@Override
	public String prefix() {
		return AIConst.DataString_Prefix;
	}

}
