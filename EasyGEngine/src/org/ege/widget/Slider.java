package org.ege.widget;

import org.ege.utils.E;
import org.ege.widget.callback.OnValueListener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Slider extends Actor {
	SliderStyle mStyle;

	int mOrientaion;
	OnValueListener mValueListener;

	private float rotation;
	// ----------------------------------------------

	public float min;
	public float max;

	public float step;
	private float value;

	// ----------------------------------------------

	private final float mKnobSizePercent;

	private final float mKnobWHRatio;

	private float mKnobX;
	private float mKnobY;

	private float mKnobWidth;
	private float mKnobHeight;

	private final float mDelta;
	private float mCurrentValuePercent;

	// ----------------------------------------------
	private TextureRegion tmp;
	private int regionWidth;

	private float mProcessWidth;
	private float mProcessHeight;

	private final int mFirstWidth;
	private final int mFirstHeight;

	public float mFrameDuration;
	private float mStateTime = 0;
	private int mKeyFrame = 0;

	// ----------------------------------------------

	boolean isFocusing = false;
	boolean isAnimating = false;

	public Slider(SliderStyle style) {
		super();

		mStyle = style;

		setBounds(0, 0, 100, 13);

		setRotation(mOrientaion == E.orientation.LANDSCAPE ? 0 : 90);

		/* ----------------------------- */

		min = style.min;
		max = style.max;

		step = style.step;
		value = style.value;

		mDelta = max - min;
		mCurrentValuePercent = value / mDelta;
		/* ----------------------------- */

		mKnobSizePercent = style.knobsize;

		mKnobWHRatio = (float) mStyle.knob.getRegionWidth()
				/ (float) mStyle.knob.getRegionHeight();

		mKnobHeight = getHeight() * mKnobSizePercent;
		mKnobWidth = mKnobWHRatio * mKnobHeight;

		/* ----------------------------- */

		if (mStyle.process != null) {
			mFirstWidth = mStyle.process[0].getRegionWidth();
			mFirstHeight = mStyle.process[0].getRegionHeight();
		} else {
			mFirstWidth = 0;
			mFirstHeight = 0;
		}

		if (mOrientaion == E.orientation.LANDSCAPE) {
			mKnobX = (mCurrentValuePercent * (getWidth() - mKnobWidth))
					+ getX();
			mKnobY = getY() + getHeight() / 2 - mKnobHeight / 2;
			mProcessWidth = mKnobX + mKnobWidth / 2 - getX();
			mProcessHeight = getHeight();
		} else {
			mKnobX = getX() + getHeight() / 2 - mKnobHeight / 2;
			mKnobY = (mCurrentValuePercent * (getWidth() - mKnobWidth))
					+ getY();
			mProcessWidth = mKnobY + mKnobWidth / 2 - getY();
			mProcessHeight = getHeight();
		}

		addListener(new SliderController());
	}

	/*****************************************************
	 * 
	 *****************************************************/
	public void setRegion(TextureRegion background, TextureRegion knob,
			TextureRegion... process) {
		mStyle.background = background;
		mStyle.knob = knob;
		mStyle.process = process;
	}

	public void setValueListner(OnValueListener listener) {
		mValueListener = listener;
	}

	public float getValue() {
		return (float) Math.floor(value / step) * step;
	}

	/*****************************************************
	 * 
	 *****************************************************/

	private void calculatePositionAndValue(float x) {
		final float width = getWidth();
		final float height = getHeight();

		if (x < 0)
			value = min;
		else if (x > width)
			value = max;
		else
			value = x / width * mDelta;

		mCurrentValuePercent = value / mDelta;

		mKnobHeight = height * mKnobSizePercent;
		mKnobWidth = mKnobWHRatio * mKnobHeight;

		if (mOrientaion == E.orientation.LANDSCAPE) {
			mKnobX = (mCurrentValuePercent * (width - mKnobWidth)) + getX();
			mKnobY = getY() + height / 2 - mKnobHeight / 2;
			mProcessWidth = mKnobX + mKnobWidth / 2 - getX();
			mProcessHeight = height;
		} else {
			mKnobX = getX() + height / 2 - mKnobHeight / 2;
			mKnobY = (mCurrentValuePercent * (width - mKnobWidth)) + getY();
			mProcessWidth = mKnobY + mKnobWidth / 2 - getY();
			mProcessHeight = height;
		}
	}

	public void startAnimation(float FrameDuration) {
		mFrameDuration = FrameDuration;
		isAnimating = true;
	}

	public void disableAnimation() {
		isAnimating = false;

		mStateTime = 0;
		mKeyFrame = 0;
	}

	/*****************************************************
	 * 
	 *****************************************************/

	public void setValue(float value) {
		calculatePositionAndValue(value / mDelta * getWidth());
	}

	public void setMax(float max) {
		this.max = max;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public void setStep(float step) {
		this.step = step;
	}

	/*****************************************************
	 * 
	 *****************************************************/

	@Override
	public void draw(Batch batch, float parentAlpha) {
		final Color color = getColor();
		final float x = getX();
		final float y = getY();
		final float width = getWidth();
		final float height = getHeight();

		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		if (mOrientaion == E.orientation.LANDSCAPE) {
			rotation = 0;
			mKnobX = (mCurrentValuePercent * (width - mKnobWidth)) + x;
			mKnobY = y + height / 2 - mKnobHeight / 2;
		} else {
			rotation = 90;
			mKnobX = x + height / 2 - mKnobHeight / 2;
			mKnobY = (mCurrentValuePercent * (width - mKnobWidth)) + y;
		}

		if (getScaleX() == 1 && getScaleY() == 1 && rotation == 0) {
			batch.draw(mStyle.background, x, y, width, height);
			tmp = mStyle.process[mKeyFrame];
			regionWidth = (int) (mCurrentValuePercent * mFirstWidth);
			if (regionWidth < mFirstWidth - mKnobWidth)
				tmp.setRegion((int) tmp.getRegionX(), (int) tmp.getRegionY(),
						(int) (mCurrentValuePercent * mFirstWidth)
								+ (int) (mKnobWidth / 2), (int) mFirstHeight);
			else
				tmp.setRegion((int) tmp.getRegionX(), (int) tmp.getRegionY(),
						(int) (mCurrentValuePercent * mFirstWidth)
								+ (int) (mKnobWidth / 4), (int) mFirstHeight);
			batch.draw(tmp, x, y, mProcessWidth, mProcessHeight);
			batch.draw(mStyle.knob, mKnobX, mKnobY, mKnobWidth, mKnobHeight);
			tmp.setRegion(tmp.getRegionX(), tmp.getRegionY(), mFirstWidth,
					mFirstHeight);
		} else {
			batch.draw(mStyle.background, x, y, getOriginX(), getOriginY(),
					width, height, getScaleX(), getScaleY(), 90);
			tmp = mStyle.process[mKeyFrame];
			regionWidth = (int) (mCurrentValuePercent * mFirstWidth);
			if (regionWidth < mFirstWidth - mKnobWidth)
				tmp.setRegion((int) tmp.getRegionX(), (int) tmp.getRegionY(),
						(int) (mCurrentValuePercent * mFirstWidth)
								+ (int) (mKnobWidth / 2), (int) mFirstHeight);
			else
				tmp.setRegion((int) tmp.getRegionX(), (int) tmp.getRegionY(),
						(int) (mCurrentValuePercent * mFirstWidth)
								+ (int) (mKnobWidth / 4), (int) mFirstHeight);
			batch.draw(tmp, x, y, getOriginX(), getOriginY(), mProcessWidth,
					mProcessHeight, getScaleX(), getScaleY(), 90);
			batch.draw(mStyle.knob, mKnobX, mKnobY, getOriginX(), getOriginY(),
					mKnobWidth, mKnobHeight, getScaleX(), getScaleY(), 90);
			tmp.setRegion(tmp.getRegionX(), tmp.getRegionY(), mFirstWidth,
					mFirstHeight);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (isAnimating) {
			mStateTime += delta;
			if (mFrameDuration > 0) {
				mKeyFrame = (int) (mStateTime / mFrameDuration);
				mKeyFrame = mKeyFrame % mStyle.process.length;
			} else
				mKeyFrame = 0;
		}
	}

	/*****************************************************
	 * 
	 *****************************************************/
	class SliderController extends InputListener {

		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			if (hit(x, y, true) != null) {
				isFocusing = true;
				calculatePositionAndValue(x);
				if (mValueListener != null)
					mValueListener.ValueChanged(Slider.this, getValue());
				return true;
			}
			return false;
		}

		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			isFocusing = false;
		}

		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer) {
			if (isFocusing) {
				calculatePositionAndValue(x);
				if (mValueListener != null)
					mValueListener.ValueChanged(Slider.this, getValue());
			}
		}

		@Override
		public boolean mouseMoved(InputEvent event, float x, float y) {
			return super.mouseMoved(event, x, y);
		}

	}

	public void dispose() {
		mStyle = null;
		mValueListener = null;
	}

	public String info() {
		if (mStyle.process == null)
			return "min: " + min + " max: " + max + " step: " + step
					+ " value: " + value + " mCurrentValuePercent: "
					+ mCurrentValuePercent + " mDelta " + mDelta
					+ " mProcessWidth " + mProcessWidth + " mProcessHeight "
					+ mProcessHeight;
		else
			return "min: " + min + " max: " + max + " step: " + step
					+ " value: " + value + " mCurrentValuePercent: "
					+ mCurrentValuePercent + " mDelta " + mDelta
					+ " mProcessWidth " + mProcessWidth + " mProcessHeight "
					+ mProcessHeight + mStyle.process.length;

	}

	/*****************************************************
	 * 
	 *****************************************************/

	public static class SliderStyle {
		public TextureRegion background;
		public TextureRegion knob;
		public TextureRegion process[];

		public int orientation = E.orientation.LANDSCAPE;

		public float min = 0;
		public float max = 100;

		public float step = 1;
		public float value = 50;

		public float knobsize = 1f;
	}
}
