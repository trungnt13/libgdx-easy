package okj.easy.core;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Values;
import com.badlogic.gdx.utils.PauseableThread;

public class ThreadManager {
	private static int						ID			= 0;

	private final IntMap<PauseableThread>	mThreadPool	= new IntMap<PauseableThread>();

	ThreadManager () {
	}

	int obtainForID (Runnable runnable) {
		mThreadPool.put(ID, new PauseableThread(runnable));
		return ID++;
	}

	Thread obtainForThread (Runnable runnable) {
		PauseableThread thread = new PauseableThread(runnable);
		mThreadPool.put(ID, thread);
		return thread;
	}

	boolean startThread (int id) {
		PauseableThread thread = mThreadPool.get(id);
		if (thread == null)
			return false;
		thread.start();
		return true;
	}

	boolean stopThread (int id) {
		PauseableThread thread = mThreadPool.get(id);
		if (thread == null)
			return false;
		thread.stopThread();
		mThreadPool.remove(id);
		return true;
	}

	boolean pauseThread (int id) {
		PauseableThread thread = mThreadPool.get(id);
		if (thread == null)
			return false;
		thread.onPause();
		return true;
	}

	boolean resumeThread (int id) {
		PauseableThread thread = mThreadPool.get(id);
		if (thread == null)
			return false;
		thread.onResume();
		return true;
	}

	int size () {
		synchronized (mThreadPool) {
			return mThreadPool.size;
		}
	}

	int sizeOfAlive () {
		synchronized (mThreadPool) {
			Values<PauseableThread> values = mThreadPool.values();
			int count = 0;
			for (PauseableThread pt : values)
				if (pt.isAlive())
					count++;
			return count;
		}
	}
}
