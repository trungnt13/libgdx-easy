/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package stu.tnt.gdx.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Executes tasks in the future on the main loop thread.
 * 
 * @FileName: Timer.java
 * @CreateOn: Sep 15, 2012 - 11:10:47 AM
 * @Author: Nathan Sweet
 * @Author: TrungNT
 */
public final class Timer {
	static public final int CANCELLED = 0;
	static public final int FOREVER = -2;

	private final Array<Task> tasks = new Array(false, 13);

	private boolean stopped, posted;

	private final Runnable timerRunnable = new Runnable() {
		public void run() {
			update();
		}
	};

	/*********************************************************** Constructor ***********************************************************/

	Timer() {
	}

	/*********************************************************** Methods ***********************************************************/
	/** Schedules a task to occur once at the start of the next frame. */
	void postTask(Task task) {
		scheduleTask(task, 0, 0, 1);
	}

	/** Schedules a task to occur every frame until cancelled. */
	void scheduleTask(Task task) {
		scheduleTask(task, 0, 0, FOREVER);
	}

	/** Schedules a task to occur once after the specified delay. */
	void scheduleTask(Task task, float delaySeconds) {
		scheduleTask(task, delaySeconds, 0, 1);
	}

	/**
	 * Schedules a task to occur once after the specified delay and then
	 * repeatedly at the specified interval until cancelled.
	 */
	void scheduleTask(Task task, float delaySeconds, float intervalSeconds) {
		scheduleTask(task, delaySeconds, intervalSeconds, FOREVER);
	}

	/**
	 * Schedules a task to occur once after the specified delay and then a
	 * number of additional times at the specified interval.
	 */
	void scheduleTask(Task task, float delaySeconds, float intervalSeconds,
			int repeatCount) {
		if (task.repeatCount != CANCELLED) {
			Gdx.app.log("IllegalArgumentException",
					"The same task may not be scheduled twice.");
			return;
		}
		task.delaySeconds = delaySeconds;
		task.intervalSeconds = intervalSeconds;
		task.repeatCount = repeatCount;
		tasks.add(task);
		postRunnable();
	}

	/**
	 * Schedule a task base on frames per second for it
	 * 
	 * @param fps
	 *            the number of fps you want your task run
	 * @param task
	 */
	void scheduleTask(float fps, Task task) {
		float interval = 1 / fps;
		scheduleTask(task, 0, interval, FOREVER);
	}

	void removeTask(Task task) {
		tasks.removeValue(task, true);
		task.repeatCount = CANCELLED;
	}

	/**
	 * Stops the timer, tasks will not be executed and time that passes will not
	 * be applied to the task delays.
	 */
	void stop() {
		stopped = true;
		posted = false;
	}

	/** Starts the timer if it was stopped. */
	void start() {
		stopped = false;
		postRunnable();
	}

	void reset() {
		posted = false;
		stopped = false;
		clear();
	}

	/** Cancels all tasks. */
	void clear() {
		for (int i = 0, n = tasks.size; i < n; i++)
			tasks.get(i).cancel();
		tasks.clear();
	}

	private void postRunnable() {
		if (stopped || posted)
			return;
		posted = true;
		eAdmin.egame.postRunnable(timerRunnable);
	}

	void update() {
		if (stopped) {
			return;
		}

		float delta = Gdx.graphics.getDeltaTime();
		for (int i = 0, n = tasks.size; i < n; i++) {
			final Task task = tasks.get(i);
			task.delaySeconds -= delta;

			// ============= check if task need to execute =============
			if (task.delaySeconds > 0)
				continue;
			if (task.repeatCount != CANCELLED) {
				if (task.repeatCount == 1)
					task.repeatCount = CANCELLED;
				task.run();
			}

			// ============= Done execute task =============
			if (task.repeatCount == CANCELLED) {
				tasks.removeIndex(i);
				i--;
				n--;
			} else {
				task.delaySeconds = task.intervalSeconds;
				if (task.repeatCount > 0)
					task.repeatCount--;
			}
		}

		if (tasks.size == 0)
			posted = false;
		else
			eAdmin.egame.postRunnable(timerRunnable);
	}

	/**
	 * Runnable with a cancel method.
	 * 
	 * @see Timer
	 * @author Nathan Sweet
	 */
	static abstract public class Task implements Runnable {
		protected float delaySeconds;
		protected float intervalSeconds;
		protected int repeatCount = CANCELLED;

		/**
		 * If this is the last time the task will be ran or the task is first
		 * cancelled, it may be scheduled again in this method.
		 */
		abstract public void run();

		/**
		 * Cancels the task. It will not be executed until it is scheduled
		 * again. This method can be called at any time.
		 */
		public void cancel() {
			delaySeconds = 0;
			repeatCount = CANCELLED;
		}

		/**
		 * Returns true if this task is scheduled to be executed in the future
		 * by a timer.
		 */
		public boolean isScheduled() {
			return repeatCount != CANCELLED;
		}
	}

	static public class TaskGroup extends Task implements Disposable {
		private final Array<Task> taskList;
		private int tmp;

		public TaskGroup() {
			taskList = new Array<Timer.Task>();
		}

		public TaskGroup(int init) {
			taskList = new Array<Timer.Task>(init);
		}

		/*****************************************************
		 * 
		 *****************************************************/

		/** schedule task with given information */
		public TaskGroup add(Task task, float delaySeconds,
				float intervalSeconds, int repeatCount) {
			task.delaySeconds = delaySeconds;
			task.intervalSeconds = intervalSeconds;
			task.repeatCount = repeatCount;
			taskList.add(task);
			return this;
		}

		/** schedule task base on desire frames per second */
		public TaskGroup add(Task task, float fps) {
			task.delaySeconds = 0;
			task.intervalSeconds = 1 / fps;
			task.repeatCount = FOREVER;
			taskList.add(task);
			return this;
		}

		/** schedule task forever with max FPS */
		public TaskGroup add(Task task) {
			task.delaySeconds = 0;
			task.intervalSeconds = 0;
			task.repeatCount = FOREVER;
			taskList.add(task);
			return this;
		}

		/*****************************************************
		 * 
		 *****************************************************/

		public void start() {
			eAdmin.egame.postTask(this);
		}

		@Override
		public void run() {
			for (Task t : taskList) {
				tmp = t.repeatCount;
				t.repeatCount = CANCELLED;
				eAdmin.egame
						.schedule(t, t.delaySeconds, t.intervalSeconds, tmp);
			}
		}

		/*****************************************************
		 * 
		 *****************************************************/

		/** Cancel all tasks */
		public void cancel() {
			for (Task t : taskList)
				t.cancel();
		}

		public int size() {
			return taskList.size;
		}

		@Override
		public void dispose() {
			cancel();
			taskList.clear();
		}
	}
}
