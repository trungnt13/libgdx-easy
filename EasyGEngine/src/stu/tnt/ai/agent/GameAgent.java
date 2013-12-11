package stu.tnt.ai.agent;

import stu.tnt.ai.kbs.KnowledgeBase;

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

	public static final void dispose() {
		mGameAgent.kbs.clear();
		mGameAgent = null;
	}

	public static final KnowledgeBase kbs() {
		return mGameAgent.kbs;
	}

	public static final void updateAgent(float delta) {
		mGameAgent.update(delta);
	}
}
