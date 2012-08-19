package org.ege.gl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class AxisDrawer {
	Vector3 src;
	Vector3 Ox;
	Vector3 Oy;
	Vector3 Oz;
	
	ShapeRenderer render;
	
	public AxisDrawer(){
		render = new ShapeRenderer();
		src = new Vector3();
		Ox = new Vector3(1000, 0, 0);
		Oy = new Vector3(000, 1000, 0);
		Oz = new Vector3(000, 0, 1000);
	}

	public void setProjectionMatrix(Matrix4 m){
		render.setProjectionMatrix(m);
	}
	
	public void setTransformationMatrix(Matrix4 m){
		render.setTransformMatrix(m);
	}
	
	public void render(){
		final GL10 gl = Gdx.gl10;
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		render.begin(ShapeType.Line);
		render.setColor(Color.GREEN);
		render.line(src.x, src.y, src.z, Ox.x, Ox.y, Ox.z);
		render.setColor(Color.YELLOW);
		render.line(src.x, src.y, src.z, Oy.x, Oy.y, Oy.z);
		render.setColor(Color.RED);
		render.line(src.x, src.y, src.z, Oz.x, Oz.y, Oz.z);
		render.end();
	}
}
