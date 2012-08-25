package org.ege.opengl20;

import java.nio.IntBuffer;

import org.ege.starter.TestScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.D;

public class Shader2DTest extends TestScreen{

	int vsHandle;
	int fsHandle;
	int progHanle;
	
	@Override
	public void onLoadResource () {
		String vertex_shader = Gdx.files.internal("data/shaders/batch-2d-vs.glsl").readString();
		String fragment_shader = Gdx.files.internal("data/shaders/batch-2d-fs.glsl").readString();
		
		D.out(vertex_shader+" " + fragment_shader);
		
		// init
		vsHandle = gl20.glCreateShader(GL20.GL_VERTEX_SHADER);
		fsHandle = gl20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		progHanle = gl20.glCreateProgram();
		
		compileShader(vertex_shader,fragment_shader);
		attachToProgram();
	}

	private void attachToProgram () {
		IntBuffer tmp = BufferUtils.newIntBuffer(1);
		int tmpInt;

		gl20.glAttachShader(progHanle, vsHandle);
		gl20.glAttachShader(progHanle, fsHandle);
		gl20.glLinkProgram(progHanle);
		
		gl20.glGetProgramiv(progHanle, GL20.GL_LINK_STATUS, tmp);
		tmpInt = tmp.get(0);
		D.out("link program : " + (tmpInt ==  0 ? "fail" : "success") );
		if(tmpInt == 0)
			D.out("Program Bug : " + gl20.glGetProgramInfoLog(progHanle));
		
	}

	private void compileShader (String vertex_shader, String fragment_shader) {
		IntBuffer tmp = BufferUtils.newIntBuffer(1);
		int tmpInt;
		
		// compile vertex shader
		gl20.glShaderSource(vsHandle, vertex_shader);
		gl20.glCompileShader(vsHandle);
		gl20.glGetShaderiv(vsHandle, GL20.GL_COMPILE_STATUS, tmp);
		tmpInt = tmp.get(0);
		D.out("vertex compile : " + (tmpInt ==  0 ? "fail" : "success") );
		if(tmpInt == 0)
			D.out("vertex buf :\n" + gl20.glGetShaderInfoLog(vsHandle));
		
		// compile fragment shader
		gl20.glShaderSource(fsHandle, fragment_shader);
		gl20.glCompileShader(fsHandle);
		gl20.glGetShaderiv(fsHandle, GL20.GL_COMPILE_STATUS, tmp);
		tmpInt = tmp.get(0);
		D.out("fragment compile : " + (tmpInt ==  0 ? "fail" : "success"));
		if(tmpInt == 0)
			D.out("fragment shader : \n" + gl20.glGetShaderInfoLog(fsHandle));
	}

	@Override
	public void onResize (int width, int height) {
	}

	@Override
	public void onUpdate (float delta) {
	}

	@Override
	public void onRender (float delta) {
	}

}
