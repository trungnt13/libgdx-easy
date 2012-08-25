package org.ege.model;

import java.io.IOException;
import java.io.InputStream;

import okj.easy.admin.eAdmin;
import okj.easy.core.GameScreen;
import okj.easy.core.Renderer;

import org.ege.gl.AmbientLight;
import org.ege.gl.AxisDrawer;
import org.ege.gl.DirectionalLight;
import org.ege.starter.CamController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.keyframe.KeyframedModel;

public class EasyInvader extends GameScreen{

	Texture playerTex;
	Mesh	playerMesh;
	
	Texture invaderTex;
	Mesh	invaderMesh;

	Texture knightTex;
	KeyframedModel knightMesh;
	
	Texture background;
	
	PerspectiveCamera cam;

	AmbientLight alight;
	DirectionalLight dlight;
	
	Renderer invaderRender;
	Renderer playerRender;
	Renderer backgroundRender;

	final AxisDrawer drawer = new AxisDrawer();
	CamController controller ;
	
	@Override
	public void onCreateLayout () {
	}

	@Override
	public void onLoadResource () {
		playerTex = new Texture(Gdx.files.internal("data/invader/data/ship.png"));
		invaderTex = new Texture(Gdx.files.internal("data/invader/data/invader.png"));
		knightTex = new Texture(Gdx.files.internal("data/invader/data/knight.jpg"));
		try {
			InputStream in = Gdx.files.internal("data/invader/data/ship.obj").read();
			playerMesh = ObjLoader.loadObj(in);
			in.close();
		
			in = Gdx.files.internal("data/invader/data/invader.obj").read();
			invaderMesh = ObjLoader.loadObj(in);
			in.close();
		
			in = Gdx.files.internal("data/invader/data/knight.md2").read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		background = new Texture(Gdx.files.internal("data/invader/data/planet.jpg"));

		alight = new AmbientLight();
		alight.setColor(0.2f, 0.2f, 0.2f, 1);
		dlight = new DirectionalLight();
		dlight.setDirection(1, 0.5f, 0);
		createRenderer();
	}

	private void createRenderer () {
		backgroundRender  = new Renderer(this) {
			@Override
			public void render (float delta) {
				batch.begin();
				batch.draw(background,0,0,eAdmin.gameWidth(),eAdmin.gameHeight());
				batch.end();
			}
			@Override
			public void enable () {
				gl.glDisable(GL10.GL_CULL_FACE);
				gl.glDisable(GL10.GL_LIGHTING);
				gl.glDisable(GL10.GL_BLEND);
				
				gl.glEnable(GL10.GL_DITHER);
				
				batch.setProjectionMatrix(projection);
			}
		};
		playerRender = new Renderer(this) {
			@Override
			public void render (float delta) {
				playerTex.bind();
				gl10.glPushMatrix();
				gl10.glTranslatef(0, 0, 0);
				// this function rotate ModelView matrix include which is view matrix of PerspectiveCamera
				gl10.glRotatef(180, 0, 1, 0);
				playerMesh.render(GL10.GL_TRIANGLES);
				gl10.glPopMatrix();
			}
			@Override
			public void enable () {
				gl.glDisable(GL10.GL_DITHER);

				gl.glEnable(GL10.GL_BLEND);
				gl.glEnable(GL10.GL_TEXTURE_2D);
				gl.glEnable(GL10.GL_DEPTH_TEST);
				gl.glEnable(GL10.GL_CULL_FACE);
				gl.glEnable(GL10.GL_LIGHTING);
				gl.glEnable(GL10.GL_COLOR_MATERIAL);
				
				cam.update();
				cam.apply(gl10);
			}
		};
		invaderRender = new Renderer(this) {
			@Override
			public void render (float delta) {
				invaderTex.bind();
				for(int i = 0;i < 10;i ++){
					gl10.glPushMatrix();
					gl10.glTranslatef(5-i, 0, -10);
					invaderMesh.render(GL10.GL_TRIANGLES);
					gl10.glPopMatrix();
				}
			}
			@Override
			public void enable () {
			}
		};
	}
	
	@Override
	public void onResize (int width, int height) {
		cam = new PerspectiveCamera(67, width, height);
		cam.position.set(0, 6, 2);
		cam.direction.set(0, -1, -1);
		cam.update();
		
		controller = new CamController(cam);
		
		alight.enable();
		dlight.enable(GL10.GL_LIGHT0);
		
		projection.setToOrtho2D(0, 0, width, height);
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

	@Override
	public void onUpdate (float delta) {
	}

	@Override
	public void onRender (float delta) {
		glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	
		cam.position.add(0, 0, -delta/5);
		
		backgroundRender.apply(delta);
		playerRender.apply(delta);
		invaderRender.apply(delta);

//		drawer.render();
	}


}
