package org.ege.gl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;

public class AmbientLight {
	private final float[] color = {
		0.2f,0.2f,0.2f,0.2f	
	};
	
	public void setColor(float r,float g,float b,float a){
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = a;
	}
	
	public void setColor(Color colo){
		color[0] = colo.r;
		color[1] = colo.g;
		color[2] = colo.b;
		color[3] = colo.a;
	}
	
	public void enable(GL10 gl){
		gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, color, 0);
	}
	
	public void enable(){
		Gdx.gl10.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, color,0);
	}
}
