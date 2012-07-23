package com.badlogic.gdx.utils;

/**
 * A update interface use for sprite
 * @author Ngo Trong Trung
 */
public interface Updater {
	public static final Updater instance = new Updater() {
		@Override
		public void update (SpriteBackend sprite, float delta) {
		}
	};
	public void update(SpriteBackend sprite,float delta);
}
