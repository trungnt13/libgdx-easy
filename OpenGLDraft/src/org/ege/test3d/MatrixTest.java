package org.ege.test3d;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.EulerCamera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.D;


public class MatrixTest implements  ApplicationListener,InputProcessor{

	public static void main(String[] argv){
		new LwjglApplication(new MatrixTest(), "", 400, 400, false);
	}
	
	Matrix4 m = new Matrix4();
	Vector3 v = new Vector3();
	
	EulerCamera cam;
	PerspectiveCamera cam1;
	
	ShapeRenderer render;
	
	Vector3 src;
	Vector3 Ox;
	Vector3 Oy;
	Vector3 Oz;
	@Override
	public void create () {
		m.rotate(Vector3.X, 35).rotate(Vector3.Y, 45);
		v.set(1, 1, 1);
		v.mul(m);
		D.out(v);
		
		m.setToRotation(Vector3.Y, 45);
		v.set(1, 1, 1);
		v.mul(m);
		m.setToRotation(Vector3.X, 35);
		v.mul(m);
		D.out(v);
		
		//	==================================================
	
		render = new ShapeRenderer();
		src= new Vector3(0,0,0);
		Ox= new Vector3(300, 0, 0);
		Oy= new Vector3(0, 300, 0);
		Oz= new Vector3(0, 0, 300);
		
		Gdx.input.setInputProcessor(this);
	}

	private float tmp = 0;
	
	@Override
	public void resize (int width, int height) {
		// eular
		cam = new EulerCamera(45, 4,4);
		cam.position.set(2,2,10);
		cam.direction.set(0,0,-1);
//		cam.pitch(100);
		cam.update();
		
		
		// perspective
		cam1 = new PerspectiveCamera(45, 4, 4);
		cam1.position.set(4, 4, 4);
		cam1.direction.set(-1, -1, -1);
		cam1.update();
		
		render.setProjectionMatrix(cam.combined);
		
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		final float delta =Gdx.graphics.getDeltaTime();
		//
		cam.update();
		render.setProjectionMatrix(cam.combined);
		//
		render.begin(ShapeType.Line);
		render.setColor(Color.WHITE);
		render.line(src.x, src.y, src.z, Ox.x, Ox.y, Ox.z);
		render.setColor(Color.YELLOW);
		render.line(src.x, src.y, src.z, Oy.x, Oy.y, Oy.z);
		render.setColor(Color.BLUE);
		render.line(src.x, src.y, src.z, Oz.x, Oz.y, Oz.z);
		render.end();
		
		render.begin(ShapeType.FilledRectangle);
		render.setColor(Color.RED);
		render.filledRect(3,0, 2, 2, Color.ORANGE, Color.RED, Color.YELLOW, Color.BLUE);
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

	private float lastX;
	private float lastY;
	
	@Override
	public boolean keyDown (int keycode) {
		return false;
		
	}

	@Override
	public boolean keyUp (int keycode) {
		return false;
		
	}

	@Override
	public boolean keyTyped (char character) {
		return false;
		
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		lastX = screenX;
		lastY = screenY;
		return false;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		return false;
		
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		float deltaX = screenX-lastX;
		float delatY = lastY - screenY;
		
		cam.rotate(deltaX, delatY);
		D.out(deltaX + " " + delatY);
		lastX = screenX;
		lastY = screenY;
		return false;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		return false;
		
	}

	@Override
	public boolean scrolled (int amount) {
		return false;
		
	}

}
