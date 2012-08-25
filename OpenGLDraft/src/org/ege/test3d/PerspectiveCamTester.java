package org.ege.test3d;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.D;

public class PerspectiveCamTester implements ApplicationListener{

	public static void main(String[] argv){
		new LwjglApplication(new PerspectiveCamTester(), "", 640, 480, false);
	}
	
	private static final int TARGET_WIDTH = 480;
	private static final float UNIT_TO_PIXEL = TARGET_WIDTH * 0.15f;
	
	Texture texture;
	OrthographicCamera cam;
	SpriteBatch batch;
	final Sprite[][] sprites = new Sprite[10][10];
	final Matrix4 matrix = new Matrix4();

	//	=========================================================
	
	Sprite tree;
	SpriteBatch batch1;
	final Matrix4 matrix1 = new Matrix4();

	//	=========================================================

	Vector3 src;
	Vector3 Ox;
	Vector3 Oy;
	Vector3 Oz;
	
	ShapeRenderer render;
	
	@Override
	public void create () {
		texture = new Texture(Gdx.files.internal("data/badlogicsmall.jpg"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		float unitsOnX = (float)Math.sqrt(2) * TARGET_WIDTH / (UNIT_TO_PIXEL);
		float pixelsOnX = Gdx.graphics.getWidth() / unitsOnX;
		float unitsOnY = Gdx.graphics.getHeight() / pixelsOnX;
		D.out(unitsOnX + " " + unitsOnY + " " + pixelsOnX);
		cam = new OrthographicCamera(unitsOnX, unitsOnY, 40);
		cam.position.mul(30);
		cam.near = 1;
		cam.far = 1000;
		
		for (int z = 0; z < 10; z++) {
			for (int x = 0; x < 10; x++) {
				sprites[x][z] = new Sprite(texture);
				sprites[x][z].setPosition(x, z);
				sprites[x][z].setSize(1, 1);
			}
		}
		
		matrix.setToRotation(axis, 0);

		batch = new SpriteBatch();

		Gdx.input.setInputProcessor(new IsoCamController(cam));

		/*	=============================================	*/
		
		batch1 = new SpriteBatch();

		matrix1.setToRotation(new Vector3(1, 0, 0), 0);
		tree = new Sprite(new Texture(Gdx.files.internal("data/tree.png")));
		tree.setPosition(3, 0);
		tree.setSize(0.8f, 1f);

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

	private float rotate = 0;
	private Vector3 axis = Vector3.X;
	@Override
	public void render () {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		cam.update();
		matrix.setToRotation(axis,rotate+= 5*Gdx.graphics.getDeltaTime());

		batch.setProjectionMatrix(cam.combined);
		batch.setTransformMatrix(matrix);

		render.setProjectionMatrix(cam.combined);
		render.setTransformMatrix(matrix);
		
		batch.begin();
		for (int z = 0; z < 10; z++) {
			for (int x = 0; x < 10; x++) {
				sprites[x][z].draw(batch);
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
		
		/*	========================================	*/
		
		batch1.setProjectionMatrix(cam.combined);
		batch1.setTransformMatrix(matrix1);
		
		batch1.begin();
		tree.draw(batch1);
		batch1.end();
		
		checkTileTouched();
	}

	final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
	final Vector3 intersection = new Vector3();
	Sprite lastSelectedTile = null;

	private void checkTileTouched () {
		if (Gdx.input.justTouched()) {
			Ray pickRay = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
			Intersector.intersectRayPlane(pickRay, xzPlane, intersection);
			System.out.println(intersection);
			int x = (int)intersection.x;
			int z = (int)intersection.z;
			if (x >= 0 && x < 10 && z >= 0 && z < 10) {
				if (lastSelectedTile != null) lastSelectedTile.setColor(1, 1, 1, 1);
				Sprite sprite = sprites[x][z];
				sprite.setColor(1, 0, 0, 1);
				lastSelectedTile = sprite;
			}
		}
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

	public class IsoCamController extends InputAdapter {
		final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
		final Vector3 intersection = new Vector3();
		final Vector3 curr = new Vector3();
		final Vector3 last = new Vector3(-1, -1, -1);
		final Vector3 delta = new Vector3();
		final Camera camera;

		public IsoCamController (Camera camera) {
			this.camera = camera;
		}

		@Override
		public boolean touchDragged (int x, int y, int pointer) {
			Ray pickRay = camera.getPickRay(x, y);
			Intersector.intersectRayPlane(pickRay, xzPlane, curr);

			if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
				pickRay = camera.getPickRay(last.x, last.y);
				Intersector.intersectRayPlane(pickRay, xzPlane, delta);
				delta.sub(curr);
				camera.position.add(delta.x, 0, delta.z);
			}
			last.set(x, y, 0);
			return false;
		}

		@Override
		public boolean touchUp (int x, int y, int pointer, int button) {
			last.set(-1, -1, -1);
			return false;
		}
	}
}
