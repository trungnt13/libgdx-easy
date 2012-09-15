package okj.easy.core;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntMap.Entries;
import com.badlogic.gdx.utils.IntMap.Entry;
import com.badlogic.gdx.utils.IntMap.Values;
import com.badlogic.gdx.utils.PauseableThread;

public final class ThreadManager {
	private static int						ID				= 0;

	private final IntMap<PauseableThread>	mThreadPool		= new IntMap<PauseableThread>(13);
	private final IntMap<Runnable>			mRunnablePool	= new IntMap<Runnable>(13);

	ThreadManager () {
	}

	int obtainForID (Runnable runnable) {
		mThreadPool.put(ID, new PauseableThread(runnable));
		mRunnablePool.put(ID, runnable);
		return ID++;
	}

	PauseableThread obtainForThread (Runnable runnable) {
		PauseableThread thread = new PauseableThread(runnable);
		mThreadPool.put(ID, thread);
		mRunnablePool.put(ID++, runnable);
		return thread;
	}

	/*****************************************************
	 * 
	 *****************************************************/

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
		mRunnablePool.remove(id);
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

	/*****************************************************
	 * query
	 *****************************************************/

	boolean containThread (int id) {
		return mThreadPool.containsKey(id);
	}

	boolean containThread (PauseableThread thread) {
		return mThreadPool.containsValue(thread, true);
	}

	/*****************************************************
	 * 
	 *****************************************************/

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

	void clear () {
		synchronized (mThreadPool) {
			Entries<PauseableThread> entries = mThreadPool.entries();
			for (Entry<PauseableThread> pt : entries) {
				pt.value.stopThread();
				mThreadPool.remove(pt.key);
			}
		}
	}
}
