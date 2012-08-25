package org.ege.drawtest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.ege.starter.TestScreen;
import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.D;

public class TextureMappingTest extends TestScreen{

	int VERTICES_SIZE = (3+2)*8*4  ;
	
	ByteBuffer buffer = ByteBuffer.allocateDirect(VERTICES_SIZE);
	Pixmap pixmap;
	Pixmap pixmap1;
	FloatBuffer mVertices ;
	ShortBuffer mIndices;
	
	int[] textures = new int[3];
	Texture tmp;
	
	PerspectiveCamera cam;
	
	@Override
	public void onLoadResource () {
		buffer.order(ByteOrder.nativeOrder());
		mVertices = buffer.asFloatBuffer();
		float[] vertices = new float[]{
				1f,1f,1f, 1.0f,0.0f,
				1f,-1f,1f, 0.0f,0.0f,
				-1f,1f,1f, 0.0f,1.0f,
				-1f,-1f,1f, 1.0f,1.0f,
				
				1f,1f,-1f, 1.0f,0.0f,
				1f,-1f,-1f,0.0f,0.0f,
				-1f,1f,-1f, 0.0f,1.0f,
				-1f,-1f,-1f, 1.0f,1.0f,
		};
		mVertices.put(vertices);
		mVertices.flip();
		
		buffer = ByteBuffer.allocateDirect(36*2);
		buffer.order(ByteOrder.nativeOrder());
		mIndices = buffer.asShortBuffer();
		short[] indices = new short[]{
				//front
				1,0,3,3,0,2,
				//back
				7,5,4,7,4,6,
				//left
				3,2,7,7,2,6,
				//right
				1,0,5,5,0,4,
				//bottom
				5,1,7,7,1,3,
				//top
				4,0,6,6,0,2
		};
		mIndices.put(indices);
		mIndices.flip();
	
		/*	====================================	*/
		pixmap = new Pixmap(Gdx.files.internal("data/badlogic.jpg"));
		pixmap1 = new Pixmap(Gdx.files.internal("data/stones.jpg"));
		
		gl10.glGenTextures(2, textures,0);
		gl10.glPixelStorei(GL10.GL_UNPACK_ALIGNMENT, 1);
		gl10.glEnable(GL10.GL_TEXTURE_2D);
		
		//tex0
		gl10.glActiveTexture(GL10.GL_TEXTURE0);
		gl10.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl10.glTexImage2D(GL10.GL_TEXTURE_2D, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
		
		gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		//tex1
		gl10.glActiveTexture(GL10.GL_TEXTURE1);
		gl10.glBindTexture(GL10.GL_TEXTURE_2D, textures[1]);
		gl10.glTexImage2D(GL10.GL_TEXTURE_2D, 0, pixmap1.getGLInternalFormat(), pixmap1.getWidth(), pixmap1.getHeight(), 0, pixmap1.getGLFormat(), pixmap1.getGLType(), pixmap1.getPixels());

		gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		gl10.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_BLEND);
	}

	@Override
	public void onResize (int width, int height) {
		gl10.glViewport(0, 0, width, height);
		cam = new PerspectiveCamera(67, width, height);
		cam.near = 1;
		cam.far = -100;
		cam.position.set(0, 4, 3);
		cam.direction.set(0, -1, -1);
		cam.update();

		
		gl10.glMatrixMode(GL10.GL_PROJECTION);
		gl10.glLoadIdentity();
		gl10.glLoadMatrixf(cam.projection.val, 0);
		gl10.glMatrixMode(GL10.GL_MODELVIEW);
		gl10.glLoadIdentity();
		gl10.glLoadMatrixf(cam.view.val, 0);
		
		gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl10.glVertexPointer(3, GL10.GL_FLOAT, 5*4, mVertices);
		mVertices.position(3);
		gl10.glTexCoordPointer(2, GL10.GL_FLOAT, 5*4, mVertices);
		
//		gl10.glDisable(GL10.GL_CULL_FACE);
		gl10.glEnable(GL10.GL_DEPTH_TEST);
//		gl10.glEnable(GL10.GL_BLEND);
//		gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}


	@Override
	public void onUpdate (float delta) {
	}

	private float tt = 0;
	@Override
	public void onRender (float delta) {
		glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
		// set up first texture
//		gl10.glClientActiveTexture(GL10.GL_TEXTURE1);
//		gl10.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);
		
		// set up second texture
//		gl10.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_DECAL);

		// draw
		gl10.glPushMatrix();
		gl10.glTranslatef(0, 0, -tt/30);
		gl10.glRotatef(tt+=20*delta, 0, 1, 0);
//		gl10.glBindTexture(GL10.GL_TEXTURE_2D,2);
		gl10.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_SHORT, mIndices);
		gl10.glPopMatrix();
	}
}
