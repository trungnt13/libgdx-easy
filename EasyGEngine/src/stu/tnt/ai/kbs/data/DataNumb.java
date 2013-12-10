package stu.tnt.ai.kbs.data;

import stu.tnt.ai.AIConst;

public class DataNumb extends DataRaw<Number> {

	DataNumb(String name) {
		super(name);
	}

	@Override
	protected void copyInternal(Number raw) {
		currValue = raw;
	}

	@Override
	protected void pushInternal(Number raw) {
		currValue = currValue.doubleValue() + raw.doubleValue();
	}

	@Override
	public Number defData() {
		return 0;
	}

	@Override
	public String prefix() {
		return AIConst.DataNumb_Prefix;
	}
}
