package okj.easy.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.GLU;

public abstract class Renderer {
	public static final GLCommon gl = Gdx.gl;
	public static final GL10 gl10 = Gdx.gl10;
	public static final GL11 gl11 = Gdx.gl11;
	public static final GL20 gl20 = Gdx.gl20;
	public static final GLU  glu = Gdx.glu;
	
	protected final ApplicationContext mContext;
	
	public Renderer(ApplicationContext context){
		this.mContext = context;
	}
	
	public abstract void enable();
	
	public abstract void render(float delta);
	
	public abstract void disable();
}

