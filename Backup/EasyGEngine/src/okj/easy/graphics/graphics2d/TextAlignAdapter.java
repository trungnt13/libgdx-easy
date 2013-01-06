package okj.easy.graphics.graphics2d;

import okj.easy.graphics.wrapper.AttributeSet.Align;

import com.badlogic.gdx.math.Vector2;

public abstract class TextAlignAdapter {
	
	public abstract Vector2 calPosition(Align align,float width,float height);
	
}
