
package stu.tnt.gdx.screen;

import stu.tnt.gdx.core.ApplicationContext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;

public abstract class Renderer {
	public static final GL20 gl = Gdx.gl;
	public static final GL30 gl30 = Gdx.gl30;
	public static final GL20 gl20 = Gdx.gl20;

	protected final ApplicationContext context;

	public Renderer (ApplicationContext context) {
		this.context = context;
	}

	public abstract void enable ();

	public abstract void render (float delta);

	public void apply (float delta) {
		enable();
		render(delta);
	}
}
