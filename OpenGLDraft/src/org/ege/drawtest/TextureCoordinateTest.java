package org.ege.drawtest;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.ege.starter.TestScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.BufferUtils;

public class TextureCoordinateTest extends TestScreen{

	FloatBuffer vertices;
	ByteBuffer indices;
	
	Pixmap pixmap;
	int[] textures= new int[1];
	
	@Override
	public void onLoadResource () {
		vertices = BufferUtils.newFloatBuffer((2+2)*4);
		float[] ver = {
			-1,-1,0,0,
			 1,-1,1,0,
			 1, 1,1,1,
			-1, 1,0,1
		};
		vertices.put(ver);
		vertices.flip();
		
		/*	===========================================	*/
		
		indices = BufferUtils.newByteBuffer(4);
		indices.put(new byte[]{
				1,2,0,3
		});
		indices.flip();
		
		/* ============================================	*/
	
		pixmap = new Pixmap(Gdx.files.internal("data/badlogic.jpg"));
		
		
		ByteBuffer bb = pixmap.getPixels();
		byte[] b = new byte[bb.limit()]; 
		byte[] copy = new byte[bb.limit()];
		bb.get(b, 0, bb.limit());

		final ByteBuffer tmp = BufferUtils.newByteBuffer(b.length);

		// we flip Y the pixmap so the text-cor origin now at lower-left
		int row = 3*pixmap.getWidth();
		for(int j = 0;j < pixmap.getHeight();j++){
			int src= j*row;
			int dst = (pixmap.getHeight()-j-1)*row;
			System.arraycopy(b,src, copy, dst, row);
		}
		tmp.put(copy);
		tmp.flip();
		
		gl10.glEnable(GL10.GL_TEXTURE_2D);
		gl10.glGenTextures(1, textures, 0);
		gl10.glPixelStorei(GL10.GL_UNPACK_ALIGNMENT, 4);
		gl10.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
		gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		gl10.glTexImage2D(GL10.GL_TEXTURE_2D,0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), tmp);
		gl10.glTexImage2D(GL10.GL_TEXTURE_2D,1, pixmap.getGLInternalFormat(), 128, 128, 0, pixmap.getGLFormat(), pixmap.getGLType(), tmp);
		gl10.glTexImage2D(GL10.GL_TEXTURE_2D,2, pixmap.getGLInternalFormat(), 64, 64, 0, pixmap.getGLFormat(), pixmap.getGLType(), tmp);
		gl10.glTexImage2D(GL10.GL_TEXTURE_2D,3, pixmap.getGLInternalFormat(), 32, 32, 0, pixmap.getGLFormat(), pixmap.getGLType(), tmp);
		gl10.glTexImage2D(GL10.GL_TEXTURE_2D,4, pixmap.getGLInternalFormat(), 16, 16, 0, pixmap.getGLFormat(), pixmap.getGLType(), tmp);
		gl10.glTexImage2D(GL10.GL_TEXTURE_2D,5, pixmap.getGLInternalFormat(), 8, 8, 0, pixmap.getGLFormat(), pixmap.getGLType(), tmp);
		gl10.glTexImage2D(GL10.GL_TEXTURE_2D,6, pixmap.getGLInternalFormat(), 4, 4, 0, pixmap.getGLFormat(), pixmap.getGLType(), tmp);
		gl10.glTexImage2D(GL10.GL_TEXTURE_2D,7, pixmap.getGLInternalFormat(), 2, 2, 0, pixmap.getGLFormat(), pixmap.getGLType(), tmp);
		gl10.glTexImage2D(GL10.GL_TEXTURE_2D,8, pixmap.getGLInternalFormat(), 1, 1, 0, pixmap.getGLFormat(), pixmap.getGLType(), tmp);
		
		gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		
		/*	===========================================	*/
		
	}

	@Override
	public void onResize (int width, int height) {
		gl.glViewport(0, 0, width, height);
		
		OrthographicCamera cam = new OrthographicCamera();
		cam.setToOrtho(false, 4, 4*(float)height/(float)width);
		cam.update();
		
		gl10.glMatrixMode(GL10.GL_PROJECTION);
		gl10.glLoadMatrixf(cam.projection.val, 0);
		gl10.glMatrixMode(GL10.GL_MODELVIEW);
//		gl10.glLoadMatrixf(cam.view.val, 0);
		
		/*	===========================================	*/
		
		gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertices.position(0);
		gl10.glVertexPointer(2, GL10.GL_FLOAT, 4*4, vertices);
		vertices.position(2);
		gl10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl10.glTexCoordPointer(2, GL10.GL_FLOAT, 4*4, vertices);
		
		gl10.glDisable(GL10.GL_DEPTH_TEST);
		gl10.glEnable(GL10.GL_TEXTURE_2D);
		
	}

	@Override
	public void onUpdate (float delta) {
	}

	@Override
	public void onRender (float delta) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		gl10.glPushMatrix();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE, indices);
		gl10.glPopMatrix();
	}

}
