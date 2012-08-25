package org.ege.starter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;

public class CamController implements InputProcessor{
	private final Camera cam;
	
	public CamController (Camera cam){
		this.cam = cam;
		Gdx.input.setInputProcessor(this);
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

	private float lastX;
	private float lastY;
	
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
		float deltaX = screenX - lastX;
		float deltaY = lastY-screenY;
		
		if(Math.abs(deltaY) > Math.abs(deltaX))
			cam.rotate(-deltaY/5, 1, 0, 0);
		else
			cam.rotate(deltaX/5, 0, 1, 0);
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
