package stu.tnt.gdx.widget;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class Panel extends Group {
	final Image	mBackground;
	PanelStyle	mStyle;

	boolean		isStarting;

	public Panel () {
		super();
		mStyle = new PanelStyle();

		mBackground = new Image();
		mBackground.setBounds(0, 0, getWidth(), getHeight());
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setScaling(Scaling.stretch);
		mBackground.setZIndex(0);

		addActor(mBackground);
	}

	public Panel (PanelStyle style) {
		super();
		mStyle = style;

		mBackground = new Image();
		mBackground.setBounds(0, 0, getWidth(), getHeight());
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setScaling(Scaling.stretch);
		mBackground.setZIndex(0);

		addActor(mBackground);
	}

	public Panel (Drawable region) {
		super();
		mStyle = new PanelStyle();

		mBackground = new Image(region);
		mBackground.setBounds(0, 0, getWidth(), getHeight());
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setScaling(Scaling.stretch);
		mBackground.setZIndex(0);

		addActor(mBackground);
	}

	/*********************************************************
	 * 
	 *********************************************************/

	@Override
	public void setWidth (float width) {
		super.setWidth(width);
		mBackground.setWidth(width);
	}

	@Override
	public void setHeight (float height) {
		super.setHeight(height);
		mBackground.setHeight(height);
	}

	@Override
	public void setSize (float width, float height) {
		super.setSize(width, height);
		mBackground.setSize(width, height);
	}

	@Override
	public void size (float size) {
		super.size(size);
		mBackground.size(size);
	}

	@Override
	public void size (float width, float height) {
		super.size(width, height);
		mBackground.size(width, height);
	}

	@Override
	public void setBounds (float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
		mBackground.setBounds(0, 0, width, height);
	}

	/*********************************************************
	 * 
	 *********************************************************/

	public void setBackground (Drawable drawable) {
		mBackground.setDrawable(drawable);
	}

	public Image getBackground () {
		return mBackground;
	}

	/**********************************************************
	 * 
	 **********************************************************/

	public static class PanelStyle {
		public Drawable	background;
	}
}
