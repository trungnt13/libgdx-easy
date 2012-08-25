package org.ege.globj;

import org.ege.starter.R;
import org.ege.starter.TestScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class FBOTest extends TestScreen{
	FrameBuffer mBuffer;
	ShaderProgram sp;
	
	Texture tex;
	Mesh mesh;
	
	SpriteBatch batch;
	
	@Override
	public void onLoadResource () {
		mesh = new Mesh(true, 3, 0, new VertexAttribute(Usage.Position, 3, "a_Position"), new VertexAttribute(Usage.ColorPacked, 4,
				"a_Color"), new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		float c1 = Color.toFloatBits(255, 0, 0, 255);
		float c2 = Color.toFloatBits(255, 0, 0, 255);
			
		float c3 = Color.toFloatBits(0, 0, 255, 255);
			

		mesh.setVertices(new float[] {
				-0.5f, -0.5f, 0, c1, 0, 	0, 
				 0.5f, -0.5f, 0, c2, 1, 	0, 
				 0, 	0.5f, 0, c3, 0.5f,  1});
		tex = new Texture(Gdx.files.internal("data/badlogic.jpg"));
		
		batch = new SpriteBatch();
		mBuffer = new FrameBuffer(Format.RGB565, 128, 128, false);
		createShader(Gdx.graphics);
	}

	private void createShader (Graphics graphics) {
		String vertexShader = 
				"attribute vec4 a_Position;    \n" + 
				"attribute vec4 a_Color;\n" + 
				"attribute vec2 a_texCoords;\n"+
				"varying vec4 v_Color;" +
				"varying vec2 v_texCoords; \n" +
				"void main()                  \n" + 
				"{                            \n" + 
				"   v_Color = a_Color;"+ 
				"   v_texCoords = a_texCoords;\n" + 
				"   gl_Position =   a_Position;  \n" + "}                            \n";
		String fragmentShader = 
				"#ifdef GL_ES\n" + 
				"precision mediump float;\n" + 
				"#endif\n" + 
				"varying vec4 v_Color;\n"+ 
				"varying vec2 v_texCoords; \n" + 
				"uniform sampler2D u_texture;\n" +
				"void main()                                  \n" + 
				"{                                            \n"+ 
				"  gl_FragColor = v_Color * texture2D(u_texture, v_texCoords);\n" 
				+ "}";
		sp = new ShaderProgram(vertexShader, fragmentShader);
		if (sp.isCompiled() == false) throw new IllegalStateException(sp.getLog());
	}

	@Override
	public void onResize (int width, int height) {
	}

	@Override
	public void onUpdate (float delta) {
	}

	@Override
	public void onRender (float delta) {
		mBuffer.begin();
		Gdx.gl20.glViewport(0, 0, mBuffer.getWidth(), mBuffer.getHeight());
		Gdx.gl20.glClearColor(0f, 1f, 0f, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
		tex.bind();
		sp.begin();
		sp.setUniformi("u_texture", 0);
		mesh.render(sp, GL20.GL_TRIANGLES);
		sp.end();
		mBuffer.end();

		Gdx.graphics.getGL20().glViewport(0, 0, R.SCREEN_WIDTH, R.SCREEN_HEIGHT);
		Gdx.gl20.glClearColor(1f, 0f, 0f, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(tex,0,0);
		batch.draw(mBuffer.getColorBufferTexture(), 0, 0, 256, 256, 0, 0, mBuffer.getColorBufferTexture().getWidth(),
				mBuffer.getColorBufferTexture().getHeight(), false, true);
		batch.end();
	}

}
