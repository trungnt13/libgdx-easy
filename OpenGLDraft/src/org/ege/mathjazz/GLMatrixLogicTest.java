package org.ege.mathjazz;

import org.ege.starter.Application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.D;

public class GLMatrixLogicTest extends Application{

	public static void main(String[] argv){
		new LwjglApplication(new GLMatrixLogicTest(), "",480,320, false);
	}
	
	Matrix4 mat4;
	Matrix3 mat3;
	//  0.9999999   0.0   -0.008429378   0.0   0.0   1.0   0.0   0.0   0.008429378   0.0   0.9999999   0.0   13.0   13.0   13.0   1.0
	//  0.9999999   0.0   -0.008429378   0.0   0.0   1.0   0.0   0.0   0.008429378   0.0   0.9999999   0.0   13.0   13.0   13.0   1.0

	// GPU matrix mul : 72222175 73671980 66654134 72171348 225475458
	// CPU matrix mul : 41333376 21238892 41911452 52731554 107217323

	@Override
	public void create () {
		// result matrix multiplicaion fastest in JNI with CPU
		mat4 = new Matrix4();
		mat4.setToTranslation(13, 13, 13);
		Matrix4 tmp = new Matrix4();
		tmp.setToRotation(Vector3.Y, 45);
		
		long cur = System.nanoTime();
		
		
		Gdx.gl10.glMatrixMode(GL10.GL_PROJECTION);
		Gdx.gl10.glLoadMatrixf(mat4.val, 0);
		for(int i =0 ;i < 100000;i++)
			Gdx.gl10.glMultMatrixf(tmp.val, 0);
		
		D.out(mat4.val);
		D.out(System.nanoTime()-cur);
		
		// 
		mat3 = new Matrix3(mat4);
		mat3.set(mat4.toNormalMatrix());
		
		mat4.idt();
		mat4.setToOrtho2D(0, 0, 500, 800);
		D.out(mat4.val);
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void render () {
	}
	
}
