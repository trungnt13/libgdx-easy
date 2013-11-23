package stu.tnt.gdx.utils;

import stu.tnt.gdx.graphics.graphics2d.SpriteBackend;

/**
 * A update interface use for sprite
 * 
 * @author Ngo Trong Trung
 */
public abstract class Updater {
	public static final Updater instance = new Updater() {
		@Override
		public void update(final SpriteBackend sprite, float delta) {
		}
	};

	private boolean stop = false;

	public void start() {
		stop = false;
	}

	public void stop() {
		stop = true;
	}

	public boolean isStoped() {
		return stop;
	}

	public abstract void update(final SpriteBackend sprite, float delta);
}