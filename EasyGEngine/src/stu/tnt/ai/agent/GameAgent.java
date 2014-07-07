package stu.tnt.ai.agent;

import stu.tnt.ai.kbs.KnowledgeBase;
import stu.tnt.ai.kbs.data.DataBool;
import stu.tnt.ai.kbs.data.DataNumb;
import stu.tnt.ai.kbs.data.DataString;

public class GameAgent {
	private final KnowledgeBase kbs;
	private static GameAgent mGameAgent = null;

	private GameAgent() {
		kbs = new KnowledgeBase();
	}

	private void update(float delta) {
		kbs.update(delta);
	}

	// ///////////////////////////////////////////////////////////////
	// static methods
	// ///////////////////////////////////////////////////////////////

	public static final GameAgent createGameAgent() {
		if (mGameAgent == null)
			mGameAgent = new GameAgent();
		return mGameAgent;
	}

	public static final void start() {
		kbs().setStart(true);
	}

	public static final void stop() {
		kbs().setStart(false);
	}

	public static final void dispose() {
		mGameAgent.kbs.clear();
		mGameAgent = null;
	}

	public static final KnowledgeBase kbs() {
		return mGameAgent.kbs;
	}

	public static final Object getData(String dataName) {
		return kbs().findData(dataName).data();
	}

	public static final void printData(String dataName) {
		AgentDebuger.log(kbs().findData(dataName).data().toString());
	}

	public static final void setNumb(String dataName, Number value) {
		((DataNumb) kbs().findData(dataName)).set(value);
	}

	public static final void setBool(String dataName, Boolean value) {
		((DataBool) kbs().findData(dataName)).set(value);
	}

	public static final void setString(String dataName, String value) {
		((DataString) kbs().findData(dataName)).set(value);
	}

	public static final void pushNumb(String dataName, Number value) {
		((DataNumb) kbs().findData(dataName)).push(value);
	}

	public static final void pushBool(String dataName, Boolean value) {
		((DataBool) kbs().findData(dataName)).push(value);
	}

	public static final void pushString(String dataName, String value) {
		((DataString) kbs().findData(dataName)).push(value);
	}

	public static final void updateAgent(float delta) {
		mGameAgent.update(delta);
	}
}
