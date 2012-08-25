package org.ege.drawtest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.ege.gl.AxisDrawer;
import org.ege.starter.TestScreen;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.utils.BufferUtils;

public class MultiTextureTest extends TestScreen{
    private FloatBuffer mFVertexBuffer;
    private int mVertexName;
    
    private ByteBuffer  mTfan1;
    private int mIndicesName1;
    
    private ByteBuffer  mTfan2;
	private int mIndicesName2;
	
	PerspectiveCamera cam;
	
	final static IntBuffer handle = BufferUtils.newIntBuffer(1);
	int mName;

	/*	================================================	*/

	AxisDrawer axis;
	
	@Override
	public void onLoadResource () {
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

		// gl20
		if(gl20 != null)
			initGl20(vertices,tfan1,tfan2);

		// gl11 and 1
		if(gl10!= null)
			initGL10(vertices,tfan1,tfan2);
	}

	
	private void initGl20 (float[] vertices, byte[] tfan1, byte[] tfan2) {
		gl20.glGenBuffers(1, handle);
		mName = handle.get(0);
		handle.flip();
		
		 ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
	     vbb.order(ByteOrder.nativeOrder());
	     mFVertexBuffer = vbb.asFloatBuffer();
	     mFVertexBuffer.put(vertices);
	     mFVertexBuffer.position(0);
	        
		gl20.glBindBuffer(GL20.GL_ARRAY_BUFFER, mName);
		gl20.glBufferData(GL20.GL_ARRAY_BUFFER, vertices.length*4, mFVertexBuffer, GL20.GL_STATIC_DRAW);

		//
		
        mTfan1 = ByteBuffer.allocateDirect(tfan1.length);
        mTfan1.put(tfan1);
        mTfan1.position(0);
        
        mTfan2 = ByteBuffer.allocateDirect(tfan2.length);
        mTfan2.put(tfan2);
        mTfan2.position(0);
	}


	private void initGL10 (float[] vertices,byte[] tfan1,byte[] tfan2) {
		        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		        vbb.order(ByteOrder.nativeOrder());
		        mFVertexBuffer = vbb.asFloatBuffer();
		        mFVertexBuffer.put(vertices);
		        mFVertexBuffer.position(0);
		        
		        mTfan1 = ByteBuffer.allocateDirect(tfan1.length);
		        mTfan1.put(tfan1);
		        mTfan1.position(0);
		        
		        mTfan2 = ByteBuffer.allocateDirect(tfan2.length);
		        mTfan2.put(tfan2);
		        mTfan2.position(0);
		        
		        axis = new AxisDrawer();
		        
		        // gen VRAM buffer 
		        IntBuffer tmp = BufferUtils.newIntBuffer(1);
		        gl11.glGenBuffers(1, tmp);
		        mVertexName  = tmp.get(0);
		        tmp.clear();
		        
		        gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertexName);
		        gl11.glBufferData(GL11.GL_ARRAY_BUFFER, vertices.length*4, mFVertexBuffer, GL11.GL_STATIC_DRAW);
		        gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		        //
		        gl11.glGenBuffers(1, tmp);
		        mIndicesName1 = tmp.get(0);
		        tmp.clear();
		        
		        gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mIndicesName1);
		        gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, tfan1.length, mTfan1, GL11.GL_STATIC_DRAW);
		        gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		        //
		        gl11.glGenBuffers(1, tmp);
		        mIndicesName2	 = tmp.get(0);
		        tmp.clear();
		        
		        gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER,mIndicesName2);
		        gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, tfan2.length, mTfan2, GL11.GL_STATIC_DRAW);
		        gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	/*	================================================	*/

	@Override
	public void onResize (int width, int height) {
		gl.glViewport(0, 0, width, height);
		if(gl10 != null)
			configGL10(width,height);
		if(gl20 != null)
			configGL20(width,height);
	}


	private void configGL20 (int width, int height) {
		gl20.glBindBuffer(GL20.GL_ARRAY_BUFFER, mName);
		// example of using in GL11
//		gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
//		gl11.glVertexPointer(attribute.numComponents, GL10.GL_FLOAT, attributes.vertexSize, attribute.offset);
//
//
//		gl11.glEnableClientState(GL10.GL_COLOR_ARRAY);
//		gl11.glColorPointer(attribute.numComponents, colorType, attributes.vertexSize, attribute.offset);
//
//		gl11.glEnableClientState(GL10.GL_NORMAL_ARRAY);
//		gl11.glNormalPointer(GL10.GL_FLOAT, attributes.vertexSize, attribute.offset);
//
//		gl11.glClientActiveTexture(GL10.GL_TEXTURE0 + textureUnit);
//		gl11.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//		gl11.glTexCoordPointer(attribute.numComponents, GL10.GL_FLOAT, attributes.vertexSize, attribute.offset);
		
		// example of using gl20
//		gl20.glEnableVertexAttribArray(index)
//		gl20.glVertexAttribPointer(indx, size, type, normalized, stride, ptr)
//		D.out(Gdx.gl11);
	}


	private void configGL10 (int width, int height) {
		// perspective camera helper
		cam = new PerspectiveCamera(67, width, height);
		cam.position.set(0, 3, 3);
		cam.direction.set(0, -1, -1);
		cam.update();
		
//		gl10.glMatrixMode(GL10.GL_PROJECTION);
//		gl10.glLoadMatrixf(cam.projection.val,0);
//		
//		gl10.glMatrixMode(GL10.GL_MODELVIEW);
//		gl10.glLoadMatrixf(cam.view.val, 0);
		
//		D.out(cam.direction,cam.up,cam.position);
		
		// gl utils helper 
		gl10.glMatrixMode(GL10.GL_PROJECTION);
		gl10.glLoadIdentity();
		glu.gluPerspective(gl10, 67, (float)width/(float)height, 1, 100);
		gl10.glMatrixMode(GL10.GL_MODELVIEW);
		gl10.glLoadIdentity();
		glu.gluLookAt(gl10, cam.position.x, cam.position.y, cam.position.z,
							cam.direction.x, cam.direction.y, cam.direction.z, 
							0,1, 0f);
		// this is the same as;
//		gl10.glTranslatef(0, -3, -3);
//		gl10.glRotatef(-45, 1, 0, 0);
		
		gl10.glEnable(GL10.GL_DEPTH_TEST);
//		gl10.glHint(GL10.GL_POLYGON_SMOOTH_HINT, GL10.GL_FASTEST);
//		gl10.glEnable(GL10.GL_POINT_SMOOTH);
//		gl10.glShadeModel(GL10.GL_FLAT);
//		gl10.glEnable(GL10.GL_CULL_FACE);
//		gl10.glCullFace(GL10.GL_FRONT);
	}

	/*	================================================	*/

	@Override
	public void onUpdate (float delta) {
		
	}

	private float angle = 0;
//	private final IntBuffer shit = BufferUtils.newIntBuffer(16);
	
	@Override
	public void onRender (float delta) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		if(gl10 != null)
			drawGL10(delta);
    }

	private void drawGL10 (float delta) {
		angle += 13*delta;
		drawCubeGL10();
//		gl10.glPolygonMode(GL10.GL_FRONT_AND_BACK, GL10.GL_FILL);
	}


	private void drawCubeGL10 () {
		//old GL10 draw
//		gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		mFVertexBuffer.position(0);
//		gl10.glVertexPointer(3, GL10.GL_FLOAT, 7*4, mFVertexBuffer);
//		gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);
//		mFVertexBuffer.position(3);
//		gl10.glColorPointer(4, GL10.GL_FLOAT,7*4, mFVertexBuffer);
		
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertexName);
		gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 7*4, 0);
		gl11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		gl11.glColorPointer(4, GL11.GL_FLOAT, 7*4, 3*4);
		
		gl10.glPushMatrix();
		gl10.glTranslatef(0, 0, -angle/10);
		gl10.glRotatef(angle, 0, 0, 1);
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mIndicesName1);
	 	gl11.glDrawElements( GL10.GL_TRIANGLE_FAN, 6 * 3, GL10.GL_UNSIGNED_BYTE, 0);
	 	gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mIndicesName2);
    	gl11.glDrawElements( GL10.GL_TRIANGLE_FAN, 6 * 3, GL10.GL_UNSIGNED_BYTE, 0);	
    	axis.render();
    	gl10.glPopMatrix();

    	/*	===============================================	*/
    	
    	gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertexName);
		gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 7*4, 0);
		gl11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		gl11.glColorPointer(4, GL11.GL_FLOAT, 7*4, 3*4);
    	
    	gl10.glPushMatrix();
    	gl10.glTranslatef(-angle/10, 0, 0);
    	gl10.glRotatef(-angle, 1, 0, 0);
    	gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mIndicesName1);
    	gl11.glDrawElements(GL11.GL_TRIANGLE_FAN, 18, GL11.GL_UNSIGNED_BYTE, 0);
	 	gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mIndicesName2);
    	gl11.glDrawElements( GL10.GL_TRIANGLE_FAN, 6 * 3, GL10.GL_UNSIGNED_BYTE, 0);	
    	axis.render();
    	gl10.glPopMatrix();
	}
}
