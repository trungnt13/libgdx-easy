package org.ege.model;

import java.io.IOException;
import java.io.InputStream;

import okj.easy.core.GameScreen;
import okj.easy.core.Renderer;

import org.ege.gl.AmbientLight;
import org.ege.gl.DirectionalLight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.D;

public class LoadingObj extends GameScreen implements InputProcessor{

	PerspectiveCamera cam;
	Mesh mesh;
	Texture texture;
	Ship ship;

	Texture background;
	
	float[] direction = {1, 0.5f, 0, 0};
	
	Renderer shipRender;
	Renderer backgroundRenderer;
	
	AmbientLight aLight;
	DirectionalLight dLight;
	
	@Override
	public void onCreateLayout () {
		
	}

	@Override
	public void onLoadResource () {
		InputStream in = Gdx.files.internal("data/invader/data/ship.obj").read();
		mesh = ObjLoader.loadObj(in);
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		texture = new Texture(Gdx.files.internal("data/invader/data/ship.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		ship = new Ship();
	
		//	======================================
		
		background = new Texture(Gdx.files.internal("data/invader/data/planet.jpg"));
		
		Gdx.input.setInputProcessor(this);
	
		aLight = new AmbientLight();
		dLight = new DirectionalLight();
		aLight.setColor(0.2f, 0.2f, 0.2f, 1f);
		dLight.setDirection(1, -0.5f, 0);
		creatRender();
	}
	
	private void creatRender(){
		shipRender = new Renderer(this) {
			
			@Override
			public void render (float delta) {
				texture.bind();
				gl10.glPushMatrix();
				gl10.glTranslatef(ship.position.x, ship.position.y, ship.position.z);
				gl10.glRotatef(45 * (-Gdx.input.getAccelerometerY() / 5), 0, 0, 1);
				gl10.glRotatef(180, 0, 1, 0);
				mesh.render(GL10.GL_TRIANGLES);
				gl10.glPopMatrix();
			}
			
			@Override
			public void enable () {
				gl10.glDisable(GL10.GL_DITHER);
				gl10.glEnable(GL10.GL_TEXTURE_2D);
				gl10.glEnable(GL10.GL_DEPTH_TEST);
				gl10.glEnable(GL10.GL_CULL_FACE);
				
				cam.position.set(ship.position.x, 6, 2);
				cam.direction.set(ship.position.x, 0, -4).sub(cam.position).nor();
				cam.update();
				cam.apply(Gdx.gl10);
			}
			
		};

		backgroundRenderer = new Renderer(this) {
			
			@Override
			public void render (float delta) {
				batch.begin();
				batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
				batch.end();
			}
			
			@Override
			public void enable () {
				gl.glDisable(GL10.GL_TEXTURE_2D);
				gl.glDisable(GL10.GL_DEPTH_TEST);
				gl.glDisable(GL10.GL_CULL_FACE);
				gl10.glDisable(GL10.GL_LIGHTING);
				gl10.glEnable(GL10.GL_DITHER);
				gl10.glDisable(GL10.GL_BLEND);
				batch.setProjectionMatrix(projection);
			}
			
		};
	}

	@Override
	public void onResume () {
	}

	@Override
	public void onPause () {
	}

	@Override
	public void onDestroy () {
	}

	private boolean resize = false;
	
	@Override
	public void onResize (int width, int height) {
		final GL10 gl = Gdx.graphics.getGL10();
		gl.glEnable(GL10.GL_LIGHTING);
//		gl.glEnable(GL10.GL_LIGHT0);
//		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, direction, 0);
		gl.glEnable(GL10.GL_COLOR_MATERIAL);

		aLight.enable();
		dLight.enable(GL10.GL_LIGHT0);
		
		gl.glViewport(0, 0, width, height);

		cam = new PerspectiveCamera(67,width,height);
		projection.setToOrtho2D(0, 0, width, height);
	}
	
	@Override
	public void onUpdate (float delta) {
	}
	
	@Override
	public void onRender (float delta) {
		final GL10 gl = Gdx.graphics.getGL10();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		backgroundRenderer.apply(delta);
		shipRender.apply(delta);
	}

	
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
		return false;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		return false;
		
	}

	private float lastX;
	
	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		float deltaX = screenX-lastX;
		lastX =screenX;
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
	
	public class Ship {
		public static final float SHIP_RADIUS = 1;
		public static final float SHIP_VELOCITY = 20;
		public final Vector3 position = new Vector3(0, 0, 0);
		public int lives = 3;
		public boolean isExploding = false;
		public float explodeTime = 0;

		public void update (float delta) {
			if (isExploding) {
				explodeTime += delta;
			}
		}
	}
}
