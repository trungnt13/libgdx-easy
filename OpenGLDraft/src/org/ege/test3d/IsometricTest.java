package org.ege.test3d;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class IsometricTest implements ApplicationListener{

	public static void main(String[] argv){
		new LwjglApplication(new IsometricTest(), "", 480, 320, false);
	}
	
	Texture tex;
	SpriteBatch batch;
	Sprite[][] list = new Sprite[10][10];
	OrthographicCamera cam;
	final Matrix4 matrix = new Matrix4();	
	//	=========================================================

	Vector3 src;
	Vector3 Ox;
	Vector3 Oy;
	Vector3 Oz;
	
	ShapeRenderer render;
	
	@Override
	public void create () {
		tex = new Texture(Gdx.files.internal("data/badlogic.jpg"));
		batch = new SpriteBatch();
		cam = new OrthographicCamera(10, 10 * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));	
		cam.position.set(5, 5, 10);
		cam.direction.set(-1, -1,-1f);
		cam.near = 1;
		cam.far = 1000;

		for(int x = 0;x < 10;x++){
			for(int z = 0; z < 10;z++){
				list[x][z] = new Sprite(tex);
				list[x][z].setPosition(x, z);
				list[x][z].setSize(1, 1);
			}
		}
		matrix.setToRotation(new Vector3(1, 0, 0), 0);
		/*	=============================================	*/

		render = new ShapeRenderer();
		src= new Vector3(0,0,0);
		Ox= new Vector3(8, 0, 0);
		Oy= new Vector3(0, 8, 0);
		Oz= new Vector3(0, 0, 8);
		
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		cam.update();
		
		batch.setProjectionMatrix(cam.combined);
		batch.setTransformMatrix(matrix);
		render.setProjectionMatrix(cam.combined);
		render.setTransformMatrix(matrix);
		
		batch.begin();
		for(int x = 0;x < 10;x++){
			for(int z = 0; z < 10;z++){
				list[x][z].draw(batch);
			}
		}
		batch.end();
		
		render.begin(ShapeType.Line);
		render.setColor(Color.WHITE);
		render.line(src.x, src.y, src.z, Ox.x, Ox.y, Ox.z);
		render.setColor(Color.YELLOW);
		render.line(src.x, src.y, src.z, Oy.x, Oy.y, Oy.z);
		render.setColor(Color.BLUE);
		render.line(src.x, src.y, src.z, Oz.x, Oz.y, Oz.z);
		render.end();
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}

}
