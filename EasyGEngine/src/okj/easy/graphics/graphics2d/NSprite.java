package okj.easy.graphics.graphics2d;

import static com.badlogic.gdx.graphics.g2d.SpriteBatch.C1;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.C2;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.C3;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.C4;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.U1;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.U2;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.U3;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.U4;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.V1;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.V2;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.V3;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.V4;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.X1;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.X2;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.X3;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.X4;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.Y1;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.Y2;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.Y3;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.Y4;

import org.ege.utils.E;
import org.ege.utils.Updater;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.NumberUtils;

/**
 * 
 * NSprite.java
 * 
 * Created on: Oct 7, 2012
 * Author: Trung
 */
public class NSprite extends NativeSpriteBackend
{
    private final float vertices[] = new float[E.sprite.VERTICES_SIZE];

    private Array<Updater> mUpdater = new Array<Updater>(0);
    private Color color;

    // ========================================
    // texture region params
    Texture texture;
    float u, v;
    float u2, v2;
    int regionWidth, regionHeight;

    /************************************************************
     * Constructor
     ************************************************************/

    protected NSprite(long address, NWorld world)
    {
	super(address, world);

	setColor(1, 1, 1, 1);
    }

    /************************************************************
     * Texture manager method
     ************************************************************/

    public Texture getTexture ()
    {
	return texture;
    }

    public void setTexture (Texture texture)
    {
	this.texture = texture;
	final int width = texture.getWidth();
	final int height = texture.getHeight();

	setRegion(0, 0, width, height);
	setSize(width, height);
	setOrigin(width >> 1, height >> 1);
	setOriginSize(address, width, height);
    }

    // ===========================================
    // region method

    /**
     * @param width
     *            The width of the texture region. May be negative to flip the sprite when drawn.
     * @param height
     *            The height of the texture region. May be negative to flip the sprite when
     *            drawn.
     */
    public void setRegion (int x, int y, int width, int height)
    {
	float invTexWidth = 1f / texture.getWidth();
	float invTexHeight = 1f / texture.getHeight();
	setRegion(x * invTexWidth, y * invTexHeight, (x + width) * invTexWidth, (y + height)
		* invTexHeight);
	regionWidth = Math.abs(width);
	regionHeight = Math.abs(height);
	setOriginSize(address, regionWidth, regionHeight);
    }

    /**
     * Sets the texture to that of the specified region and sets the coordinates relative to the
     * specified region.
     */
    public void setRegion (TextureRegion region, int x, int y, int width, int height)
    {
	texture = region.getTexture();
	setRegion(region.getRegionX() + x, region.getRegionY() + y, width, height);
	setOriginSize(address, width, height);
    }

    public void setRegion (float u, float v, float u2, float v2)
    {
	this.u = u;
	this.v = v;
	this.u2 = u2;
	this.v2 = v2;
	regionWidth = Math.round(Math.abs(u2 - u) * texture.getWidth());
	regionHeight = Math.round(Math.abs(v2 - v) * texture.getHeight());
	setOriginSize(address, regionWidth, regionHeight);

	float[] vertices = this.vertices;
	vertices[U1] = u;
	vertices[V1] = v2;

	vertices[U2] = u;
	vertices[V2] = v;

	vertices[U3] = u2;
	vertices[V3] = v;

	vertices[U4] = u2;
	vertices[V4] = v2;
    }

    /** Sets the texture and coordinates to the specified region. */
    public void setRegion (TextureRegion region)
    {
	texture = region.getTexture();
	setRegion(region.getU(), region.getV(), region.getU2(), region.getV2());
	setOriginSize(address, regionWidth, regionHeight);
    }

    public void setU (float u)
    {
	this.u = u;
	regionWidth = Math.round(Math.abs(u2 - u) * texture.getWidth());
	setOriginSize(address, regionWidth, regionHeight);

	vertices[U1] = u;
	vertices[U2] = u;
    }

    public void setV (float v)
    {
	this.v = v;
	regionHeight = Math.round(Math.abs(v2 - v) * texture.getHeight());
	setOriginSize(address, regionWidth, regionHeight);

	vertices[V2] = v;
	vertices[V3] = v;
    }

    public void setU2 (float u2)
    {
	this.u2 = u2;
	regionWidth = Math.round(Math.abs(u2 - u) * texture.getWidth());
	setOriginSize(address, regionWidth, regionHeight);

	vertices[U3] = u2;
	vertices[U4] = u2;
    }

    public void setV2 (float v2)
    {
	this.v2 = v2;
	regionHeight = Math.round(Math.abs(v2 - v) * texture.getHeight());
	setOriginSize(address, regionWidth, regionHeight);

	vertices[V1] = v2;
	vertices[V4] = v2;
    }

    // ============================================
    // getter

    public boolean isFlipX ()
    {
	return u > u2;
    }

    public boolean isFlipY ()
    {
	return v > v2;
    }

    public float getU ()
    {
	return u;
    }

    public float getV ()
    {
	return v;
    }

    public float getU2 ()
    {
	return u2;
    }

    public float getV2 ()
    {
	return v2;
    }

    public int getRegionX ()
    {
	return Math.round(u * texture.getWidth());
    }

    public int getRegionY ()
    {
	return Math.round(v * texture.getHeight());
    }

    /** Returns the region's width. */
    public int getRegionWidth ()
    {
	return regionWidth;
    }

    /** Returns the region's height. */
    public int getRegionHeight ()
    {
	return regionHeight;
    }

    /************************************************************
     * Java sprite method
     ************************************************************/

    // ==============================================
    // setter

    @Override
    public void setBounds (float x, float y, float width, float height)
    {
	setBounds(address, x, y, width, height);

	if (isDirty(address))
	    return;

	float x2 = x + width;
	float y2 = y + height;
	final float[] vertices = this.vertices;
	vertices[X1] = x;
	vertices[Y1] = y;

	vertices[X2] = x;
	vertices[Y2] = y2;

	vertices[X3] = x2;
	vertices[Y3] = y2;

	vertices[X4] = x2;
	vertices[Y4] = y;

    }

    @Override
    public void setSize (float width, float height)
    {
	setSize(address, width, height);

	if (isDirty(address))
	    return;

	float x = getX();
	float y = getY();
	float x2 = x + width;
	float y2 = y + height;
	final float[] vertices = this.vertices;

	vertices[X1] = x;
	vertices[Y1] = y;

	vertices[X2] = x;
	vertices[Y2] = y2;

	vertices[X3] = x2;
	vertices[Y3] = y2;

	vertices[X4] = x2;
	vertices[Y4] = y;

    }

    @Override
    public void setPosition (float x, float y)
    {
	translate(x - getX(), y - getY());
    }

    @Override
    public void setX (float x)
    {
	translateX(x - getX());
    }

    @Override
    public void setY (float y)
    {
	translateY(y - getY());
    }

    @Override
    public void translate (float xAmount, float yAmount)
    {
	translate(address, xAmount, yAmount);

	if (isDirty(address))
	    return;

	final float[] vertices = this.vertices;
	vertices[X1] += xAmount;
	vertices[Y1] += yAmount;

	vertices[X2] += xAmount;
	vertices[Y2] += yAmount;

	vertices[X3] += xAmount;
	vertices[Y3] += yAmount;

	vertices[X4] += xAmount;
	vertices[Y4] += yAmount;
    }

    @Override
    public void translateX (float xAmount)
    {
	translateX(address, xAmount);

	if (isDirty(address))
	    return;

	final float[] vertices = this.vertices;
	vertices[X1] += xAmount;
	vertices[X2] += xAmount;
	vertices[X3] += xAmount;
	vertices[X4] += xAmount;
    }

    @Override
    public void translateY (float yAmount)
    {
	translateY(address, yAmount);

	if (isDirty(address))
	    return;

	final float[] vertices = this.vertices;
	vertices[Y1] += yAmount;
	vertices[Y2] += yAmount;
	vertices[Y3] += yAmount;
	vertices[Y4] += yAmount;
    }

    @Override
    public void setOrigin (float originX, float originY)
    {
	setOrigin(address, originX, originY);
    }

    @Override
    public void setRotation (float degree)
    {
	setRotation(address, degree);
    }

    @Override
    public void rotate (float degree)
    {
	rotate(address, degree);
    }

    @Override
    public void setScale (float scaleXY)
    {
	setScale(address, scaleXY);
    }

    @Override
    public void setScale (float scaleX, float scaleY)
    {
	setScale(address, scaleX, scaleY);
    }

    @Override
    public void scale (float amount)
    {
	scale(address, amount);
    }

    @Override
    public void setColor (float r, float g, float b, float a)
    {
	int intBits = ((int) (255 * a) << 24) | ((int) (255 * b) << 16) | ((int) (255 * g) << 8)
		| ((int) (255 * r));
	float color = NumberUtils.intToFloatColor(intBits);
	final float[] vertices = this.vertices;
	vertices[C1] = color;
	vertices[C2] = color;
	vertices[C3] = color;
	vertices[C4] = color;
    }

    @Override
    public void setColor (Color tint)
    {
	float color = tint.toFloatBits();
	final float[] vertices = this.vertices;
	vertices[C1] = color;
	vertices[C2] = color;
	vertices[C3] = color;
	vertices[C4] = color;
    }

    // =======================================================
    // getter

    @Override
    public float[] getVertices ()
    {
	return vertices;
    }

    @Override
    public float getX ()
    {
	return getX(address);
    }

    @Override
    public float getCenterX ()
    {
	return getCenterX(address);
    }

    @Override
    public float getY ()
    {
	return getY(address);
    }

    @Override
    public float getCenterY ()
    {
	return getCenterY(address);
    }

    @Override
    public float getWidth ()
    {
	return getWidth(address);
    }

    @Override
    public float getHeight ()
    {
	return getHeight(address);
    }

    @Override
    public float getOriginX ()
    {
	return getOriginX(address);
    }

    @Override
    public float getOriginY ()
    {
	return getOriginY(address);
    }

    @Override
    public float getRotation ()
    {
	return getRotation(address);
    }

    @Override
    public float getScaleX ()
    {
	return getScaleX(address);
    }

    @Override
    public float getScaleY ()
    {
	return getScaleY(address);
    }

    @Deprecated
    public Rectangle getBoundingRectangle ()
    {
	return null;
    }

    @Deprecated
    public float[] getBoundingFloatRect (float offset)
    {
	return null;
    }

    @Deprecated
    public Circle getBoundingCircle ()
    {
	return null;
    }

    /**
     * Returns the color of this sprite. Changing the returned color will have no affect,
     * {@link #setColor(Color)} or {@link #setColor(float, float, float, float)} must be used.
     */
    public Color getColor ()
    {
	if (color == null)
	    color = new Color();

	int intBits = NumberUtils.floatToIntColor(vertices[C1]);
	Color color = this.color;
	color.r = (intBits & 0xff) / 255f;
	color.g = ((intBits >>> 8) & 0xff) / 255f;
	color.b = ((intBits >>> 16) & 0xff) / 255f;
	color.a = ((intBits >>> 24) & 0xff) / 255f;
	return color;
    }

    // ===============================================
    // processor

    @Override
    public void postUpdater (Updater updater)
    {
	if (mUpdater.contains(updater, true))
	    return;

	updater.start();
	this.mUpdater.add(updater);
    }

    public int sizeUpdater ()
    {
	return mUpdater.size;
    }

    @Override
    public void noUpdater ()
    {
	this.mUpdater.clear();
    }

    @Override
    public void update (float delta)
    {
	if (isPooled)
	    return;

	getVertices(address, vertices);

	// ============= update updatable =============
	for (int i = 0, n = mUpdater.size; i < n; i++) {
	    final Updater tmp = mUpdater.get(i);

	    if (!tmp.isStoped())
		tmp.update(this, delta);
	    else {
		mUpdater.removeIndex(i);
		i--;
		n--;
	    }
	}
    }

    @Override
    public void draw (SpriteBatch batch)
    {
	batch.draw(texture, vertices, 0, E.sprite.VERTICES_SIZE);
    }

    @Override
    public void draw (SpriteBatch batch, float alpha)
    {
	Color color = getColor();
	float oldAlpha = color.a;
	color.a *= alpha;
	setColor(color);
	draw(batch);
	color.a = oldAlpha;
	setColor(color);
    }

    @Override
    public void reset ()
    {
	super.reset();

	vertices[X1] = 0;
	vertices[X2] = 0;
	vertices[X3] = 0;
	vertices[X4] = 0;

	vertices[Y1] = 0;
	vertices[Y2] = 0;
	vertices[Y3] = 0;
	vertices[Y4] = 0;

	setColor(1, 1, 1, 1);
	noUpdater();
    }

}
