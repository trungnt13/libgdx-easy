package stu.tnt.ai.agent;

import stu.tnt.Updateable;
import stu.tnt.ai.kbs.KnowledgeBase;

public class GameAgent implements Updateable {
	private final KnowledgeBase kbs;
	private static GameAgent mGameAgent = null;

	private GameAgent() {
		kbs = new KnowledgeBase();
	}

	public static final KnowledgeBase kbs() {
		return mGameAgent.kbs;
	}

	@Override
	public void update(float delta) {
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

	public static final void updateAgent(float delta) {
		mGameAgent.update(delta);
	}
}
