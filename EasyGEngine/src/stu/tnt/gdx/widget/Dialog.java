package stu.tnt.gdx.widget;

import stu.tnt.gdx.core.Timer;
import stu.tnt.gdx.core.eAdmin;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public abstract class Dialog extends Window {
	public static int DIALOG_NUMBER = 0;

	Attributes mAttr;
	boolean isStarting = true;

	float mAutoHideTime = -1;

	public Dialog(Skin skin) {
		this("", skin.get(DialogStyle.class));
	}

	public Dialog(String title, Skin skin) {
		this(title, skin.get(DialogStyle.class));
	}

	public Dialog(String windowtitle, DialogStyle style) {
		super(windowtitle, style);

		mAttr = style.attr;

		setBounds(mAttr.startX, mAttr.startY, mAttr.width, mAttr.height);
	}

	public Dialog setAutoHide(float time) {
		mAutoHideTime = time;
		return this;
	}

	protected abstract void init(final Dialog d);

	public void show(Layout layout) {
		DIALOG_NUMBER++;
		init(this);
		setZIndex(Integer.MAX_VALUE);
		layout.addActor(this);
		if (mAutoHideTime > 0)
			eAdmin.egame.schedule(new Timer.Task() {

				@Override
				public void run() {
					hide();
				}
			}, mAutoHideTime);
	}

	public void hide() {
		DIALOG_NUMBER--;
		clear();
		remove();
	}

	public Dialog setFontColor(Color color) {
		getStyle().titleFont.setColor(color);
		return this;
	}

	public Dialog setFontColor(float r, float g, float b, float a) {
		getStyle().titleFont.setColor(r, g, b, a);
		return this;
	}

	public float getDstX() {
		if (mAttr == null)
			return getX();
		return mAttr.x;
	}

	public float getDstY() {
		if (mAttr == null)
			return getY();
		return mAttr.y;
	}

	public float getStartX() {
		if (mAttr == null)
			return getX();
		return mAttr.startX;
	}

	public float getStartY() {
		if (mAttr == null)
			return getY();
		return mAttr.startY;
	}

	public static class DialogStyle extends WindowStyle {
		public Attributes attr;
	}
}
