package stu.tnt.gdx.graphics.graphics2d;

import java.util.ArrayList;

import stu.tnt.gdx.core.eGraphics;
import stu.tnt.gdx.utils.CharUtils;
import stu.tnt.gdx.utils.E;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * <b>"NUMBER"</b> sprite, I use this sprite to draw number from image the image
 * should have form :
 * <p>
 * 0 1 2 3 4 5 6 7 8 9
 * <p>
 * 
 * @author trung
 * 
 */
public class NumberDrawer {
	private TextureRegion[] numbers;

	private Vector2 position = new Vector2();
	private Vector2 size = new Vector2();
	private Vector2 origin = new Vector2();

	private final Color color = new Color(1, 1, 1, 1);

	private float padding = 0;
	private float rotation;

	private ArrayList<Integer> number = new ArrayList<Integer>();

	private int orientation;

	public NumberDrawer(Texture numberTexture, int tileWidth) {
		numbers = eGraphics.split(numberTexture, 10, tileWidth,
				numberTexture.getHeight(), 0);
		set();
	}

	public NumberDrawer(Texture numberTexture, int tileWidth, int tileHeight) {
		numbers = eGraphics.split(numberTexture, 10, tileWidth, tileHeight, 0);
		set();
	}

	public NumberDrawer(Texture numberTexture, int tileWidth, int tileHeight,
			int padding) {
		numbers = eGraphics.split(numberTexture, 10, tileWidth, tileHeight,
				padding);
		set();
	}

	public NumberDrawer(TextureRegion numberRegion, int padding) {
		numbers = eGraphics.split(numberRegion, 10, 10, 1, padding);
		set();
	}

	public NumberDrawer(TextureRegion numberRegion) {
		numbers = eGraphics.split(numberRegion, 10, 10, 1, 0);
		set();
	}

	// -------------------------------------------------------------

	private void set() {
		setPosition(0, 0);
		setSize(numbers[0].getRegionWidth(), numbers[0].getRegionHeight());
		setOrigin(getX() + getWidth() / 2, getY() + getHeight() / 2);
		setRotation(0);
		setDrawingNumber(0);
		setPadding(0);
		setOrientation(E.orientation.HORIZONTAL);
	}

	public void setOrientation(int orientaion) {
		if (orientaion == E.orientation.VERTICAL
				|| orientaion == E.orientation.PORTRAIT)
			this.orientation = E.orientation.VERTICAL;
		else
			this.orientation = E.orientation.HORIZONTAL;
	}

	public void setDrawingNumber(String number) {
		this.number.clear();
		for (int i = 0; i < number.length(); i++) {
			this.number.add(CharUtils.toIntValue(number.charAt(i)));
		}
	}

	public void setDrawingNumber(int number) {
		if (number != 0 && getDrawingNumber() == number)
			return;
		setDrawingNumber(Integer.toString(number));
	}

	public void setPadding(float padding) {
		this.padding = padding;
	}

	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}

	public void setX(float x) {
		this.position.set(x, position.y);
	}

	public void setY(float y) {
		this.position.set(position.x, y);
	}

	public void setSize(float width, float height) {
		this.size.set(width, height);
	}

	public void setWidth(float width) {
		this.size.set(width, size.y);
	}

	public void setHeight(float height) {
		this.size.set(size.x, height);
	}

	public void setOrigin(float origX, float origY) {
		this.origin.set(origX, origY);
	}

	public void setOriginX(float origX) {
		this.origin.set(origX, origin.y);
	}

	public void setOriginY(float origY) {
		this.origin.set(origin.x, origY);
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	// ============= set color =============

	public void setColor(float r, float g, float b, float a) {
		color.set(r, g, b, a);
	}

	public void setColor(Color color) {
		this.color.set(color);
	}

	// -------------------------------------------------------------
	public float getWidth() {
		return this.size.x;
	}

	public float getHeight() {
		return this.size.y;
	}

	public float getX() {
		return this.position.x;
	}

	public float getY() {
		return this.position.y;
	}

	public float getOriginX() {
		return this.origin.x;
	}

	public float getOriginY() {
		return this.origin.y;
	}

	public Vector2 getSize() {
		return this.size;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public Vector2 getOrigin() {
		return this.origin;
	}

	public float getRotation() {
		return this.rotation;
	}

	public int getDrawingNumber() {
		int result = 0;
		for (int i = 0; i < number.size(); i++) {
			result = result * 10 + number.get(i);
		}
		return result;
	}

	public float getPadding() {
		return this.padding;
	}

	public int getOrientaion() {
		return this.orientation;
	}

	// ---------------------------------------------------------

	public void draw(Batch batch) {
		if (orientation == E.orientation.HORIZONTAL) {
			float startX = getX();
			for (int i = 0; i < number.size(); i++) {
				batch.draw(numbers[number.get(i)], startX, getY(),
						getOriginX(), getOriginY(), getWidth(), getHeight(),
						1f, 1f, getRotation());
				startX += getWidth() + padding;
			}
		} else {
			float startY = getY();
			for (int i = 0; i < number.size(); i++) {
				batch.draw(numbers[number.get(i)], getY(), startY,
						getOriginX(), getOriginY(), getWidth(), getHeight(),
						1f, 1f, getRotation());
				startY -= (getHeight() + padding);
			}
		}
	}

	public void draw(Batch batch, int yourNumber) {
		setDrawingNumber(yourNumber);
		// ============= save old color =============
		Color tmp = batch.getColor();
		batch.setColor(color);
		if (orientation == E.orientation.HORIZONTAL) {
			float startX = getX();
			for (int i = 0; i < number.size(); i++) {
				batch.draw(numbers[number.get(i)], startX, getY(),
						getOriginX(), getOriginY(), getWidth(), getHeight(),
						1f, 1f, getRotation());
				startX += getWidth() + padding;
			}
		} else {
			float startY = getY();
			for (int i = 0; i < number.size(); i++) {
				batch.draw(numbers[number.get(i)], getY(), startY,
						getOriginX(), getOriginY(), getWidth(), getHeight(),
						1f, 1f, getRotation());
				startY -= (getHeight() + padding);
			}
		}
		batch.setColor(tmp);
	}

}
