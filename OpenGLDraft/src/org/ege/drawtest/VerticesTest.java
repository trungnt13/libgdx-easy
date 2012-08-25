package org.ege.drawtest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import okj.easy.core.GameScreen;

import org.ege.gl.AxisDrawer;
import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;

public class VerticesTest extends GameScreen{
	
	@Override
	public void onCreateLayout () {
	}

	private ByteBuffer buffer ;
	
	private FloatBuffer mPosition ;
	private ByteBuffer mColor ;
	private ShortBuffer mIndies;
	
	//	===============================================
	
	private FloatBuffer mAxis;
	private ByteBuffer mAxisColor;
	private ByteBuffer mAxisIndices;
	
	//	===============================================
	Texture tex;
	PerspectiveCamera cam;
	
	AxisDrawer drawer = new AxisDrawer();
	@Override
	public void onLoadResource () {
		byte max = (byte)255;
		
		/* 	==========================	*/
		buffer = ByteBuffer.allocateDirect(6*4*3);
		buffer.order(ByteOrder.nativeOrder());
		mAxis = buffer.asFloatBuffer();
		mAxis.put(new float[]{
				0,0,0,
				133,0,0,
				
				0,0,0,
				0,133,0,
				
				0,0,0,
				0,0,133
		});
		mAxis.flip();
		
		mAxisColor = ByteBuffer.allocateDirect(6*4);
		mAxisColor.order(ByteOrder.nativeOrder());
		mAxisColor.put(new byte[]{
				max,0,0,max,
				max,0,0,max,
				
				0,max,0,max,
				0,max,0,max,
				
				0,0,max,max,
				0,0,max,max
		});
		mAxisColor.flip();
		
		mAxisIndices = ByteBuffer.allocateDirect(6);
		mAxisIndices.order(ByteOrder.nativeOrder());
		mAxisIndices.put(new byte[]{
				0,1,
				2,3,
				4,5
		});
		mAxisIndices.flip();
		/* 	==========================	*/
	
		buffer = ByteBuffer.allocateDirect(8*3*4);
		buffer.order(ByteOrder.nativeOrder());
		mPosition = buffer.asFloatBuffer();
		
		float[] vertices = {
			0.5f,0.5f,0.5f,
			0.5f,-0.5f,0.5f,
			-0.5f,0.5f,0.5f,
			-0.5f,-0.5f,0.5f,
			
			0.5f,0.5f,-0.5f,
			0.5f,-0.5f,-0.5f,
			-0.5f,0.5f,-0.5f,
			-0.5f,-0.5f,-0.5f,
		};
		
		mPosition.clear();
		mPosition.put(vertices);
		mPosition.flip();
		
		/* 	==========================	*/

		mColor  = ByteBuffer.allocateDirect(8*4);
		mColor.order(ByteOrder.nativeOrder());
		mColor.put(new byte[]{
			max,0,0,max,
			0,max,0,max,
			0,0,max,max,
			
			max,0,max,max,
			max,max,0,max,
			0,max,max,max,
			max,max,max,max,
		});
		mColor.flip();
		
		/* 	==========================	*/

		buffer = ByteBuffer.allocateDirect(3*2*6*2);
		buffer.order(ByteOrder.nativeOrder());
		mIndies = buffer.asShortBuffer();
		mIndies.put(new short[]{
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
		});
		mIndies.flip();
	}

	@Override
	public void onResize (int width, int height) {
		gl10.glViewport(0, 0, width, height);

		/*	===============================	*/

		cam = new PerspectiveCamera(67, width, height);
		cam.position.set(0, 5, 3);
		cam.direction.set(0, -1, -1);
		cam.update();

		
		/*	===============================	*/

		gl10.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		/*	===============================	*/

		gl10.glEnable(GL10.GL_LIGHTING);
//		gl10.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, new float[]{1f,0f,0f,1f},0);
//		gl10.glEnable(GL10.GL_LIGHT0);
		
//		gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, new float[]{0,5,3,1},0);
//		gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, new float[]{1,1,1,1}, 0);
//		gl10.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, new float[]{1,1,1,1}, 0);

		gl10.glEnable(GL10.GL_LIGHT1);
		gl10.glLightf(GL10.GL_LIGHT1, GL10.GL_LINEAR_ATTENUATION, 0.25f);
		gl10.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, new float[]{0,4,0,0}, 0);
		gl10.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, new float[]{1,1,1,1}, 0);
		gl10.glLightf(GL10.GL_LIGHT1, GL10.GL_SPOT_CUTOFF, 90);
		gl10.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPOT_DIRECTION, new float[]{0,-1,0,1},0);
		gl10.glLightf(GL10.GL_LIGHT1, GL10.GL_SPOT_EXPONENT, 1.0f);

		/*	===============================	*/

//		gl10.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 5);
//		gl10.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, new float[]{1,1,1,0},0);
		
		/*	===============================	*/

		gl10.glEnable(GL10.GL_DEPTH_TEST);
//		gl10.glDepthMask(false);
//		gl10.glEnable(GL10.GL_NORMALIZE);
//		gl10.glEnable(GL10.GL_CULL_FACE);

		/*	===============================	*/

		gl10.glMatrixMode(GL10.GL_PROJECTION);
		gl10.glLoadMatrixf(cam.projection.val, 0);
		gl10.glMatrixMode(GL10.GL_MODELVIEW);
		gl10.glLoadMatrixf(cam.view.val, 0);
//		float aspect = (float)width/(float)height;
//		float near =.1f;
//		float far = 1000f;
//		// in radian
//		float fieldOfView= 67f*MathUtils.degreesToRadians;
//		float size = near*(float)(Math.tan((double)(fieldOfView/2.0f)));
//		gl10.glFrustumf(-size, size, -size/aspect, size/aspect, near, far);
		
		/*	===============================	*/

		gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// stride - distance from each vertex to the next one
		// this is openGL state as dont change if dont call
//		gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, mPosition);
		
		gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);

	}

	@Override
	public void onResume () {
	}

	@Override
	public void onPause () {
	}

	@Override
	public void onUpdate (float delta) {
	}
	
	private float tmp;
	
	@Override
	public void onRender (float delta) {
		glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
//		gl10.glColor4f(1, 0, 0, 1);
//		gl10.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

		
		drawCube(delta);
		drawAxis(delta);
		
	}

	private void drawAxis (float delta) {
		gl10.glEnable(GL10.GL_LIGHTING);
		gl10.glShadeModel(GL10.GL_SMOOTH);
		gl10.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, mColor);
		gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, mPosition);
		gl10.glMatrixMode(GL10.GL_MODELVIEW);
		gl10.glPushMatrix();
//		gl10.glTranslatef(0.1f, 0.1f, 0f);
		gl10.glRotatef(tmp+=delta*13, 0, 1, 0);
//		gl10.glRotatef(tmp += delta, 0, 1, 0);
		gl10.glDrawElements(GL10.GL_TRIANGLES, 36, GL11.GL_UNSIGNED_SHORT, mIndies);
		gl10.glPopMatrix();
	}

	// 0x - red
	// 0y - green
	// 0z - blue
	private void drawCube (float delta) {
		gl10.glDisable(GL10.GL_TEXTURE_2D);
		gl10.glDisable(GL10.GL_LIGHTING);
		gl10.glShadeModel(GL10.GL_FLAT);
		
		gl10.glLineWidth(2);
		gl10.glVertexPointer(3, GL10.GL_FLOAT, 0, mAxis);
		gl10.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, mAxisColor);
		gl10.glPushMatrix();
		gl10.glRotatef(tmp, 0, 1, 0);
		gl10.glDrawElements(GL10.GL_LINES, 6, GL10.GL_UNSIGNED_BYTE, mAxisIndices);
		gl10.glPopMatrix();
	}

	@Override
	public void onDestroy () {
	}

}
