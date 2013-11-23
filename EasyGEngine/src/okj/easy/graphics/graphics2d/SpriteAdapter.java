package okj.easy.graphics.graphics2d;

import org.ege.utils.Updater;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class SpriteAdapter implements SpriteBackend {

	@Override
	public void reset() {

	}

	@Override
	public Rectangle getBoundingRectangle() {

		return null;
	}

	@Override
	public float[] getBoundingFloatRect(float offset) {

		return null;
	}

	@Override
	public Circle getBoundingCircle() {

		return null;
	}

	@Override
	public void setBounds(float x, float y, float width, float height) {

	}

	@Override
	public void setSize(float width, float height) {

	}

	@Override
	public void setPosition(float x, float y) {

	}

	@Override
	public void setX(float x) {

	}

	@Override
	public void setY(float y) {

	}

	@Override
	public void translate(float xAmount, float yAmount) {

	}

	@Override
	public void translateX(float xAmount) {

	}

	@Override
	public void translateY(float yAmount) {

	}

	@Override
	public void setOrigin(float originX, float originY) {

	}

	@Override
	public void setRotation(float degree) {

	}

	@Override
	public void rotate(float degree) {

	}

	@Override
	public void setScale(float scaleXY) {

	}

	@Override
	public void setScale(float scaleX, float scaleY) {

	}

	@Override
	public void scale(float amount) {

	}

	@Override
	public void setColor(float r, float g, float b, float a) {

	}

	@Override
	public void setColor(Color color) {

	}

	@Override
	public float[] getVertices() {

		return null;
	}

	@Override
	public float getX() {

		return 0;
	}

	@Override
	public float getCenterX() {

		return 0;
	}

	@Override
	public float getY() {

		return 0;
	}

	@Override
	public float getCenterY() {

		return 0;
	}

	@Override
	public float getWidth() {

		return 0;
	}

	@Override
	public float getHeight() {

		return 0;
	}

	@Override
	public float getOriginX() {

		return 0;
	}

	@Override
	public float getOriginY() {

		return 0;
	}

	@Override
	public float getRotation() {

		return 0;
	}

	@Override
	public float getScaleX() {

		return 0;
	}

	@Override
	public float getScaleY() {

		return 0;
	}

	@Override
	public void postUpdater(Updater updater) {

	}

	@Override
	public void noUpdater() {

	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void draw(SpriteBatch batch) {

	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {

	}

	@Override
	public int sizeUpdater() {
		return 0;
	}

	@Override
	public void removeUpdater(Updater updater) {
		// TODO Auto-generated method stub

	}

}