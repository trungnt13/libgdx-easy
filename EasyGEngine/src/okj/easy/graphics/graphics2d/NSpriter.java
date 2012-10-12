package okj.easy.graphics.graphics2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Updater;

public class NSpriter extends NativeSpriteBackend {

	NSpriter(long address, NWorld world) {
		super(address, world);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Rectangle getBoundingRectangle () {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float[] getBoundingFloatRect (float offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Circle getBoundingCircle () {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPooled () {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBounds (float x, float y, float width, float height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSize (float width, float height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPosition (float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setX (float x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setY (float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void translate (float xAmount, float yAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void translateX (float xAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void translateY (float yAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOrigin (float originX, float originY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRotation (float degree) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rotate (float degree) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScale (float scaleXY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScale (float scaleX, float scaleY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scale (float amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor (float r, float g, float b, float a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor (Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public float[] getVertices () {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getX () {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCenterX () {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY () {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getCenterY () {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWidth () {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeight () {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getOriginX () {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getOriginY () {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRotation () {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getScaleX () {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getScaleY () {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void postUpdater (Updater updater) {
		// TODO Auto-generated method stub

	}

	@Override
	public void noUpdater () {
		// TODO Auto-generated method stub

	}

	@Override
	public void update (float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw (SpriteBatch batch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw (SpriteBatch batch, float alpha) {
		// TODO Auto-generated method stub

	}

}
