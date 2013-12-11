package stu.tnt.ai.kbs.data;

import java.util.Collection;
import java.util.HashMap;

import stu.tnt.Updateable;

public class DataSet implements Updateable {
	private final HashMap<String, DataRaw> mDataMap = new HashMap<String, DataRaw>();

	public DataSet() {
	}

	public void clear() {
		mDataMap.clear();
	}

	@Override
	public void update(float delta) {
		Collection<DataRaw> set = mDataMap.values();
		for (DataRaw d : set) {
			d.update(delta);
		}
	}

	// ///////////////////////////////////////////////////////////////
	// Data Record management methods
	// ///////////////////////////////////////////////////////////////

	public DataRaw createNewRecord(Class type, String name) {
		checkNameCondition(name);

		DataRaw data = null;
		if (type.equals(DataNumb.class)) {
			data = new DataNumb(name);
		} else if (type.equals(DataBool.class)) {
			data = new DataBool(name);
		} else if (type.equals(DataString.class)) {
			data = new DataString(name);
		}

		mDataMap.put(name, data);
		return data;
	}

	public DataRaw findDataRecord(String name) {
		checkNameCondition(name);
		return mDataMap.get(name);
	}

	public DataRaw deleteDataRecord(String name) {
		checkNameCondition(name);
		return mDataMap.remove(name);
	}

	private final void checkNameCondition(String name) {
		if (name == null)
			throw new RuntimeException("All Data Record must have name");
	}

	public String toString() {
		StringBuilder buidler = new StringBuilder();
		buidler.append("\n************* DataRaw *************\n");
		for (DataRaw d : mDataMap.values()) {
			buidler.append(d.Name + "  " + d.getClass() + " " + d.data());
			buidler.append("\n");
		}
		return buidler.toString();
	}
}
