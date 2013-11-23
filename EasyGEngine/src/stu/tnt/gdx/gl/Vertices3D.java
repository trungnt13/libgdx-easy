package stu.tnt.gdx.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.BufferUtils;

public class Vertices3D {
	private final boolean hasColor;
	private final boolean hasTexCoords;
	private final int vertexSize;
	
	private final FloatBuffer vertices;
	private final ShortBuffer indices;
	
	public Vertices3D (int maxVertices,int maxIndices,boolean color,boolean texture){
		this.hasColor = color;
		this.hasTexCoords = texture;
		this.vertexSize = (maxVertices+ (hasColor ? 4: 0) + (hasTexCoords ? 2 : 0)) *4;
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(maxVertices*vertexSize);
		buffer.order(ByteOrder.nativeOrder());
		vertices = buffer.asFloatBuffer();
		
		if(maxIndices > 0){
			buffer = ByteBuffer.allocateDirect(maxIndices*2);
			buffer.order(ByteOrder.nativeOrder());
			indices = buffer.asShortBuffer();
		}else
			indices = null;
	}
	
	public void setVertices(float[] vertices,int offset,int length){
		this.vertices.clear();
		BufferUtils.copy(vertices, this.vertices, length, offset);
	}

	public void setVertices(float[] vertices){
		this.vertices.clear();
		BufferUtils.copy(vertices, this.vertices, vertices.length, 0);
	}

	public void setIndices(short[] indices,int offset,int length)	{
		this.indices.clear();
		this.indices.put(indices, offset, length);
		this.indices.flip();
	}

	public void setIndices(short[] indices)	{
		this.indices.clear();
		this.indices.put(indices);
		this.indices.flip();
	}

	public void draw(int primitive,int offset,int numVertices){
		final GL10 gl = Gdx.gl10;
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertices.position(0);
		gl.glVertexPointer(3, GL10.GL_FLOAT, vertexSize, vertices);
		
		if(hasColor){
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			vertices.position(3);
			gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
		}
		
		if(hasTexCoords){
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			vertices.position(hasColor ? 7:3);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		}
		
		if(indices != null){
			indices.position(offset);
			gl.glDrawElements(primitive, numVertices, GL10.GL_UNSIGNED_SHORT, indices);
		}else
			gl.glDrawArrays(primitive, offset, numVertices);
		
		if(hasColor)
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		if(hasTexCoords)
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	
	public void draw(int primitive){
		final GL10 gl = Gdx.gl10;
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertices.position(0);
		gl.glVertexPointer(3, GL10.GL_FLOAT, vertexSize, vertices);
		
		if(hasColor){
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			vertices.position(2);
			gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
		}
		
		if(hasTexCoords){
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			vertices.position(hasColor ? 5:2);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		}

		if(indices != null){
			indices.position(0);
			gl.glDrawElements(primitive, indices.limit(), GL10.GL_UNSIGNED_SHORT, indices);
		}
		
		if(hasColor)
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		if(hasTexCoords)
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	public void draw(int primitive,int numVertices){
		final GL10 gl = Gdx.gl10;
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertices.position(0);
		gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		
		if(hasColor){
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			vertices.position(2);
			gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
		}
		
		if(hasTexCoords){
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			vertices.position(hasColor ? 6:2);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		}
		
		if(indices != null){
			indices.position(0);
			gl.glDrawElements(primitive, numVertices, GL10.GL_UNSIGNED_SHORT, indices);
		}else
			gl.glDrawArrays(primitive, 0, numVertices);
		
		if(hasColor)
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		if(hasTexCoords)
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
}
