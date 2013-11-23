package stu.tnt.gdx.widget.drawable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

/**
 * CompositeDrawable.java
 * Drawable contain two region, one upper, one downer and dont support rotation or
 * scale
 * Created on: Feb 3, 2013
 * Author: Trung
 */
public class CompositeDrawable extends BaseDrawable
{
    private TextureRegion baseRegion;
    private TextureRegion upperRegion;

    /** Creates an unitialized TextureRegionDrawable. The texture region must be set before use. */
    public CompositeDrawable()
    {
    }

    public CompositeDrawable(TextureRegion base)
    {
	setRegion(base);
    }

    public CompositeDrawable(TextureRegion base, TextureRegion upper)
    {
	setRegion(base);
	setRegionUpper(upper);
    }

    public CompositeDrawable(CompositeDrawable drawable)
    {
	super(drawable);
	setRegion(drawable.baseRegion);
	setRegionUpper(drawable.upperRegion);
    }

    public void draw (SpriteBatch batch, float x, float y, float width, float height)
    {
	batch.draw(baseRegion, x, y, width, height);
	if (upperRegion != null)
	    batch.draw(upperRegion, x, y, width, height);
    }

    public void setRegion (TextureRegion region)
    {
	this.baseRegion = region;
	setMinWidth(region.getRegionWidth());
	setMinHeight(region.getRegionHeight());
    }

    public void setRegionUpper (TextureRegion region)
    {
	this.upperRegion = region;
    }

    public TextureRegion getRegion ()
    {
	return baseRegion;
    }

    public TextureRegion getRegionUpper ()
    {
	return upperRegion;
    }
}
