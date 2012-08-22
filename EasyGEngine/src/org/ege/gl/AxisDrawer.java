package org.ege.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.utils.BufferUtils;

public class AxisDrawer {
	FloatBuffer mVertexBuffer;
	ShortBuffer mIndices;
	
	final IntBuffer mHandle = BufferUtils.newIntBuffer(1);
	final int mVerticesName;
	final int mIndicesName;
	
	public AxisDrawer(){
		ByteBuffer tmp = ByteBuffer.allocateDirect((3+4)*6*4);
		tmp.order(ByteOrder.nativeOrder());
		mVertexBuffer = tmp.asFloatBuffer();
		
		// vertices
		float[] vertices = new float[]{
				//Ox - red
				0	,0,0,1,0,0,1,
				1000,0,0,1,0,0,1,
				//Oy - green
				0,0	  ,0,0,1,0,1,
				0,1000,0,0,1,0,1,
				//Oz - blue
				0,0,0	,0,0,1,1,
				0,0,1000,0,0,1,1,
		};
		mVertexBuffer.put(vertices);
		mVertexBuffer.flip();
		// indices
		tmp = ByteBuffer.allocateDirect(2*6);
		tmp.order(ByteOrder.nativeOrder());
		mIndices = tmp.asShortBuffer();
		short[] indices = new short[]{
				0,1,2,3,4,5
		};
		mIndices.put(indices);
		mIndices.flip();

		final GL11 gl = Gdx.gl11;

		//gen VRAM vertices buffer 
		gl.glGenBuffers(1, mHandle);
		mVerticesName = mHandle.get(0);
		mHandle.clear();
		gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVerticesName);
		gl.glBufferData(GL11.GL_ARRAY_BUFFER, vertices.length*4, mVertexBuffer, GL11.GL_STATIC_DRAW);
		gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);

		// gen VRAM indices buffer
		gl.glGenBuffers(1, mHandle);
		mIndicesName = mHandle.get(0);
		mHandle.clear();
		gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mIndicesName);
		gl.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, indices.length*2, mIndices, GL11.GL_STATIC_DRAW);
		gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
		
	}

	public void render(){
		final GL11 gl = Gdx.gl11;
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glLineWidth(2);
		gl.glEnable(GL10.GL_LINE_SMOOTH);
		
		gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVerticesName);
		gl.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, mIndicesName);
		gl.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 7*4, 0);
		gl.glEnableClientState(GL11.GL_COLOR_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 7*4, 3*4);
		gl.glDrawElements(GL11.GL_LINES, 6, GL11.GL_UNSIGNED_SHORT, 0);
		
		gl.glDisable(GL10.GL_LINE_SMOOTH);
	}
}
