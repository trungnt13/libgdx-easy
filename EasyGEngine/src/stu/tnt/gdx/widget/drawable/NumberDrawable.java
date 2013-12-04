package stu.tnt.gdx.widget.drawable;

import stu.tnt.gdx.graphics.graphics2d.NumberDrawer;
import stu.tnt.gdx.utils.E;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

public class NumberDrawable extends BaseDrawable {
	private NumberDrawer mDrawer;
	private int numb;
	private float x;
	private float y;

	public NumberDrawable(NumberDrawer drawer) {
		mDrawer = drawer;
	}

	public NumberDrawable(NumberDrawer drawer, int number) {
		mDrawer = drawer;
		numb = number;
	}

	public void setDrawer(NumberDrawer drawer) {
		mDrawer = drawer;
	}

	public void setNumber(int number) {
		numb = number;
	}

	public NumberDrawer getDrawer() {
		return mDrawer;
	}

	public int getNumber() {
		return numb;
	}

	public void draw(Batch batch, float x, float y, float width, float height) {
		if (mDrawer == null)
			return;

		if (mDrawer.getOrientaion() == E.orientation.HORIZONTAL) {
			this.x = x - width;
			this.y = y - height / 2;
		} else {
			this.x = x - width / 2;
			this.y = y + height;
		}

		mDrawer.setPosition(this.x, this.y);
		mDrawer.draw(batch, numb);
	}
}
