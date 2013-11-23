package stu.tnt.gdx.graphics.graphics2d;

import stu.tnt.gdx.graphics.wrapper.AttributeSet.Align;

import com.badlogic.gdx.math.Vector2;

public abstract class TextAlignAdapter {
	public abstract Vector2 calPosition(Align align, float width, float height);
}
