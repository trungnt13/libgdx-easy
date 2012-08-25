package org.ege.opengl20;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.ege.starter.TestScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.D;

public class ShaderBeginerTest extends TestScreen{

	//	===================================================

	ShaderProgram shade;
	
	int vertHandle;
	int fragHandle;
	int programHandle;

	//	===================================================

	// vertex info
	int projection_matrix_Uniform ;
	int modelview_matrix_Uniform ;
	FloatBuffer matrix = BufferUtils.newFloatBuffer(4*4);
	
	int a_Vertex_Attribute;
	int a_Color_Attribute;
	FloatBuffer vec = BufferUtils.newFloatBuffer(3);
	
	// fragment info

	//	===================================================
	
	// VBO
	int vertexBufferId;
	ByteBuffer indices1 = BufferUtils.newByteBuffer(18);
	ByteBuffer indices2 = BufferUtils.newByteBuffer(18);

	//	===================================================

	//texture
	int textureHandle;
	
	//	===================================================

	PerspectiveCamera cam;
	@Override
	public void onLoadResource () {
		
		 /*	===============================================	*/

		String vert = Gdx.files.internal("data/shaders/my-first-vert.glsl").readString();
		String frag = Gdx.files.internal("data/shaders/my-first-frag.glsl").readString();
//		D.out(vert + "     " + frag);
		
		// Create shader
		vertHandle = gl20.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragHandle = gl20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		programHandle = gl20.glCreateProgram();
		
		// load the damn source code
		gl20.glShaderSource(vertHandle, vert);
		gl20.glCompileShader(vertHandle);
		
		gl20.glShaderSource(fragHandle, frag);
		gl20.glCompileShader(fragHandle);
		
		// check if compile success
		IntBuffer tmp = BufferUtils.newIntBuffer(1);
		gl20.glGetShaderiv(vertHandle, GL20.GL_COMPILE_STATUS, tmp);
		D.out(tmp.get(0));
		D.out(gl20.glGetShaderInfoLog(vertHandle));
		
		// if success link it in your program
		gl20.glAttachShader(programHandle, vertHandle);
		gl20.glAttachShader(programHandle, fragHandle);
		gl20.glLinkProgram(programHandle);
		
		// check if shader success link to program
		tmp.clear();
		gl20.glGetProgramiv(programHandle, GL20.GL_LINK_STATUS, tmp);
		D.out(tmp.get(0));
		D.out(gl20.glGetProgramInfoLog(programHandle));
		
		// uniform fetch
//		IntBuffer params = BufferUtils.newIntBuffer(1);
//		IntBuffer type = BufferUtils.newIntBuffer(1);
//		for (int i = 0; i < 2; i++) {
//			params.clear();
//			params.put(0, 256);
//			type.clear();
//			String name = Gdx.gl20.glGetActiveUniform(programHandle, i, params, type);
//			D.out( name + " " + gl20.glGetUniformLocation(programHandle, name));
//		}
		
		 /*	===============================================	*/

		
		// load VBO
		 float vertices[] = 
		        {
		                -1.0f,  1.0f, 1.0f, 1,1,0,1,
		                 1.0f,  1.0f, 1.0f, 0,1,1,1,
		                 1.0f, -1.0f, 1.0f, 0,0,0,1,
		                -1.0f, -1.0f, 1.0f, 1,0,1,1,
		                 
		                -1.0f,  1.0f,-1.0f, 1,0,0,1,
		                 1.0f,  1.0f,-1.0f, 0,1,0,1,
		                 1.0f, -1.0f,-1.0f, 0,0,1,1,
		                -1.0f, -1.0f,-1.0f, 0,0,0,1
		        }; 

		 byte tfan1[] =					
		        	{
		        		1,0,3,
		        		1,3,2,
		        		1,2,6,
		        		1,6,5,
		        		1,5,4,
		        		1,4,0
		        		
		        		
		        	};

		 byte tfan2[] =					
		        	{
		        		7,4,5,
		        		7,5,6,
		        		7,6,2,
		        		7,2,3,
		        		7,3,0,
		        		7,0,4
		        	};
		 //
		 IntBuffer tmpInt = BufferUtils.newIntBuffer(1);
		 gl20.glGenBuffers(1, tmpInt);
		 vertexBufferId = tmpInt.get(0);
		 
		 gl20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vertexBufferId);
		 FloatBuffer fb = BufferUtils.newFloatBuffer(vertices.length);
		 BufferUtils.copy(vertices,fb,vertices.length,0);
		 gl20.glBufferData(GL20.GL_ARRAY_BUFFER, vertices.length*4, fb, GL20.GL_STATIC_DRAW);
		 gl20.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
		 //
		 indices1.put(tfan1);
		 indices1.flip();
		 //
		 indices2.put(tfan2);
		 indices2.flip();
		 
		 /*	===============================================	*/
		 //Now load the texture
		 //
		 Pixmap pixmap = new Pixmap(Gdx.files.internal("data/badlogic.jpg"));
		 IntBuffer texHandle = BufferUtils.newIntBuffer(1);
		 gl20.glGenTextures(1, texHandle);
		 textureHandle = tmp.get(0);
		 gl20.glPixelStorei(GL20.GL_UNPACK_ALIGNMENT, 4);
		 gl20.glBindTexture(GL20.GL_TEXTURE_2D, textureHandle);
		 gl20.glTexImage2D(GL20.GL_TEXTURE_2D, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(),0, pixmap.getGLFormat(),pixmap.getGLType(), pixmap.getPixels());
		 pixmap.dispose();
		 gl20.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_NEAREST);
		 gl20.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_LINEAR);
	}

	@Override
	public void onResize (int width, int height) {
		cam= new PerspectiveCamera(67, width, height);
		cam.position.set(0, 3, 3);
		cam.direction.set(0, -1, -1);
		cam.update();
		
		gl20.glUseProgram(programHandle);
		
		//send data to shader (only use after use glUseProgram
		//uniform data read only super global data
		projection_matrix_Uniform = gl20.glGetUniformLocation(programHandle, "projection_matrix");
		modelview_matrix_Uniform = gl20.glGetUniformLocation(programHandle, "modelview_matrix");
		
		matrix.clear();
		BufferUtils.copy(cam.projection.val,matrix,16,0);
		gl20.glUniformMatrix4fv(projection_matrix_Uniform, 1, false, matrix);
	
		matrix.clear();
//		cam.view.rotate(Vector3.Y, 90);
		BufferUtils.copy(cam.view.val, matrix, 16,0);
		gl20.glUniformMatrix4fv(modelview_matrix_Uniform, 1, false, matrix);
		
		// attribute data read only vertex shader data
//		gl20.glBindAttribLocation(programHandle, 13, "");
		a_Vertex_Attribute = gl20.glGetAttribLocation(programHandle, "a_Vertex");
		a_Color_Attribute = gl20.glGetAttribLocation(programHandle, "a_Color");
		
		gl20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vertexBufferId);
		gl20.glEnableVertexAttribArray(vertexBufferId);
		gl20.glVertexAttribPointer(a_Vertex_Attribute, 3, GL10.GL_FLOAT, false, 7*4, 0);
		gl20.glEnableVertexAttribArray(a_Color_Attribute);
		gl20.glVertexAttribPointer(a_Color_Attribute, 4, GL10.GL_FLOAT, false, 7*4, 3*4);
		
		// enable some state;
		gl20.glEnable(GL20.GL_DEPTH_TEST);
		gl20.glEnable(GL20.GL_CULL_FACE);
		
	}

	@Override
	public void onUpdate (float delta) {
	}

	private float rotation = 0;
	@Override
	public void onRender (float delta) {
		gl20.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		rotation += delta*13;
		cam.update();
		
		matrix.clear();
		cam.view.translate(0, 0, -rotation/10);
		cam.view.rotate(Vector3.Y, rotation);
		BufferUtils.copy(cam.view.val, matrix, 16,0);
		gl20.glUniformMatrix4fv(modelview_matrix_Uniform, 1, false, matrix);
		
		gl20.glDrawElements(GL20.GL_TRIANGLE_FAN, 18, GL20.GL_UNSIGNED_BYTE, indices1);
		gl20.glDrawElements(GL20.GL_TRIANGLE_FAN, 18, GL20.GL_UNSIGNED_BYTE, indices2);
		
	}

}
