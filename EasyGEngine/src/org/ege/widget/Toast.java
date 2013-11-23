package org.ege.widget;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import okj.easy.core.eAdmin;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Toast extends Actor {

	public static final int FADE = 1;
	public static final int MOVE_HORIZONTAL = 2;
	public static final int MOVE_VERTICAL = 3;

	ToastStyle mStyle;

	HAlignment mAlign = HAlignment.CENTER;
	Runnable mCompleted = null;

	Color tmp;

	Toast() {
		super();
		mStyle = new ToastStyle();
		mStyle.font = new BitmapFont();
	}

	Toast(ToastStyle style) {
		super();

		mStyle = style;

		if (mStyle.font == null)
			mStyle.font = new BitmapFont();
	}

	public static Toast newToast() {
		return new Toast();
	}

	public static Toast newToast(TextureRegion background, String text,
			float time) {
		return new Toast().setBackground(background).setText(text)
				.setTime(time);
	}

	public static Toast newToast(TextureRegion background, BitmapFont font,
			String text, float time) {
		return new Toast().setBackground(background).setText(text)
				.setTime(time).setFont(font);
	}

	public static Toast newToast(TextureRegion background, String text,
			float wrappedWidth, float time) {
		return new Toast().setBackground(background).setText(text)
				.setTime(time).setWrappedWidth(wrappedWidth);
	}

	public static Toast newToast(TextureRegion background, BitmapFont font,
			String text, float wrappedWidth, float time) {
		return new Toast().setBackground(background).setText(text)
				.setTime(time).setWrappedWidth(wrappedWidth).setFont(font);
	}

	public static Toast newToast(ToastStyle style) {
		return new Toast(style);
	}

	/*******************************************************
	 * 
	 *******************************************************/

	public Toast setCompleteListener(Runnable listener) {
		mCompleted = listener;
		return this;
	}

	public Toast setFont(BitmapFont font) {
		mStyle.font = font;
		return this;
	}

	public Toast setText(String text) {
		mStyle.text = text;
		return this;
	}

	public Toast setFontSize(float size) {
		float ratio = size / mStyle.font.getCapHeight();
		mStyle.font.scale(ratio);
		return this;
	}

	public Toast setFontColor(Color color) {
		mStyle.font.setColor(color);
		return this;
	}

	public Toast setFontColor(float r, float g, float b, float a) {
		mStyle.font.setColor(r, g, b, a);
		return this;
	}

	public Toast setTime(float time) {
		mStyle.time = time;
		return this;
	}

	public Toast setWrappedWidth(float wrappedWidth) {
		mStyle.wrapped = wrappedWidth;
		return this;
	}

	public Toast setFontAlign(HAlignment align) {
		mAlign = align;
		return this;
	}

	public Toast setBackground(TextureRegion background) {
		mStyle.backgound = background;
		return this;
	}

	public void show(Layout layout) {
		if (mStyle.time == 0)
			mStyle.time = 1f;
		if (mStyle.wrapped == 0)
			mStyle.wrapped = eAdmin.toastWidth();

		float t = mStyle.time / 3;
		setColor(1, 1, 1, 0);
		addAction(sequence(fadeIn(t), delay(t), fadeOut(t), run(new Runnable() {

			@Override
			public void run() {
				hide();
				if (mCompleted != null)
					mCompleted.run();
			}
		})));

		setHeight(mStyle.font.getWrappedBounds(mStyle.text, mStyle.wrapped).height);
		setWidth(mStyle.wrapped);
		setX(eAdmin.uiWidth() / 2 - mStyle.wrapped / 2);
		setY(eAdmin.uiHeight() / 3 + getHeight() / 2);

		setZIndex(Integer.MAX_VALUE);
		layout.addActor(this);
	}

	private void hide() {
		tmp = null;
		remove();
	}

	/*******************************************************
	 * 
	 *******************************************************/

	@Override
	public void draw(Batch batch, float parentAlpha) {
		final Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		final float x = getX();
		final float y = getY();

		tmp = mStyle.font.getColor();
		mStyle.font.setColor(tmp.r, tmp.g, tmp.b, color.a * parentAlpha);

		if (mStyle.backgound != null)
			batch.draw(mStyle.backgound, x - 13, y - getHeight() - 13,
					getWidth() + 26, getHeight() + 26);
		mStyle.font.drawWrapped(batch, mStyle.text, x, y, mStyle.wrapped,
				mAlign);
	}

	/***************************************************
	 * @author trung
	 */
	public static class ToastStyle {
		public TextureRegion backgound;
		public BitmapFont font;

		public String text = "";
		public float time = 1.3f;
		public float wrapped;
	}

}
