package org.ege.gl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;

public class PointLight {
	private final float[] ambient = {
		0.2f,0.2f,0.2f,0.2f
	};
	
	private final float[] diffuse = {
		1.0f,1.0f,1.0f,1.0f
	};
	
	private final float[] specular = {
		0.0f,0.0f,0.0f,0.0f
	};
	// point light the last must be 1
	private final float[] position = {
		0,0,0,1
	};
	
	private int mLastLightId = 0;
	
	public PointLight setAmbient(float r,float g,float b,float a){
		ambient[0] = r;
		ambient[1] = g;
		ambient[2] = b;
		ambient[3] = a;
		return this;
	}
	
	public PointLight setAmbient(Color c){
		ambient[0] = c.r;
		ambient[1] = c.g;
		ambient[2] = c.b;
		ambient[3] = c.a;
		return this;
	}

	public PointLight setDiffuse(float r,float g,float b,float a){
		diffuse[0] = r;
		diffuse[1] = g;
		diffuse[2] = b;
		diffuse[3] = a;
		return this;
	}
	
	public PointLight setDiffuse(Color c){
		diffuse[0] = c.r;
		diffuse[1] = c.g;
		diffuse[2] = c.b;
		diffuse[3] = c.a;
		return this;
	}

	public PointLight setSpecular(float r,float g,float b,float a){
		specular[0] = r;
		specular[1] = g;
		specular[2] = b;
		specular[3] = a;
		return this;
	}
	
	public PointLight setSpecular(Color c){
		specular[0] = c.r;
		specular[1] = c.g;
		specular[2] = c.b;
		specular[3] = c.a;
		return this;
	}
	
	public PointLight setPosition(float x,float y,float z){
		position[0] = x;
		position[1] = y;
		position[2] = z;
		return this;
	}
	
	public void enable(GL10 gl,int lightId){
		mLastLightId = lightId;
		gl.glEnable(lightId);
		gl.glLightfv(lightId, GL10.GL_DIFFUSE, diffuse,0);
		gl.glLightfv(lightId, GL10.GL_AMBIENT, ambient,0);
		gl.glLightfv(lightId, GL10.GL_SPECULAR, specular,0);
		gl.glLightfv(lightId, GL10.GL_POSITION, position,0);
	}

	public void enable(int lightId){
		final GL10 gl  = Gdx.graphics.getGL10();
		mLastLightId = lightId;
		gl.glEnable(lightId);
		gl.glLightfv(lightId, GL10.GL_DIFFUSE, diffuse,0);
		gl.glLightfv(lightId, GL10.GL_AMBIENT, ambient,0);
		gl.glLightfv(lightId, GL10.GL_SPECULAR, specular,0);
		gl.glLightfv(lightId, GL10.GL_POSITION, position,0);
	}
	
	public void disable(GL10 gl){
		gl.glDisable(mLastLightId);
	}
	
	public void disable(){
		final GL10 gl  = Gdx.graphics.getGL10();
		gl.glDisable(mLastLightId);
	}

}
