package stu.tnt.gdx.graphics.graphics2d;

import static com.badlogic.gdx.graphics.g2d.Batch.C1;
import static com.badlogic.gdx.graphics.g2d.Batch.C2;
import static com.badlogic.gdx.graphics.g2d.Batch.C3;
import static com.badlogic.gdx.graphics.g2d.Batch.C4;
import static com.badlogic.gdx.graphics.g2d.Batch.U1;
import static com.badlogic.gdx.graphics.g2d.Batch.U2;
import static com.badlogic.gdx.graphics.g2d.Batch.U3;
import static com.badlogic.gdx.graphics.g2d.Batch.U4;
import static com.badlogic.gdx.graphics.g2d.Batch.V1;
import static com.badlogic.gdx.graphics.g2d.Batch.V2;
import static com.badlogic.gdx.graphics.g2d.Batch.V3;
import static com.badlogic.gdx.graphics.g2d.Batch.V4;
import static com.badlogic.gdx.graphics.g2d.Batch.X1;
import static com.badlogic.gdx.graphics.g2d.Batch.X2;
import static com.badlogic.gdx.graphics.g2d.Batch.X3;
import static com.badlogic.gdx.graphics.g2d.Batch.X4;
import static com.badlogic.gdx.graphics.g2d.Batch.Y1;
import static com.badlogic.gdx.graphics.g2d.Batch.Y2;
import static com.badlogic.gdx.graphics.g2d.Batch.Y3;
import static com.badlogic.gdx.graphics.g2d.Batch.Y4;

import java.util.ArrayList;

import stu.tnt.gdx.utils.Animator;
import stu.tnt.gdx.utils.Updater;
import stu.tnt.gdx.utils.exception.EasyGEngineRuntimeException;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.NumberUtils;

/**
 * SpriteCAS : <b>SYNCHRONIZE COMPOSITE ANIMATE Sprite</b>
 * 
 * @author Ngo Trong Trung
 */
public class SpriteCA implements SpriteBackend, Disposable, Animator
{
    static public final int STATE_TIME = 0;
    static public final int FRAME_DURATION = 1;
    static public final int FRAME_LENGTH = 2;
    static public final int FRAME_NUMBER = 3;
    static public final int COLOR = 4;
    static public final int ROTATION = 5;
    static public final int ORIGIN_X = 6;
    static public final int ORIGIN_Y = 7;
    static public final int SCALE_X = 8;
    static public final int SCALE_Y = 9;

    static public final int VERTICES = 20;

    private final ArrayList<TextureRegion[]> mRegions;
    private final float[] mInfo;
    private final float[] mVertices = new float[VERTICES];
    private final float[] rect = new float[4];

    final int length;
    private int size = 0;
    int idx = 0;

    IntArray mRunnable;
    IntArray mDrawable;
    public static final int SPRITE_SIZE = 5;
    private boolean RUN;

    // ---------------------------------------------------

    float x;
    float y;
    float originX;
    float originY;

    float scaleX = 1;
    float scaleY = 1;

    float rotation;
    float width;
    float height;

    boolean dirty = false;

    private Color color = Color.WHITE;
    float alpha = 1;

    // ---------------------------------------------------

    private TextureRegion[] tmpRegion;
    private final float[] tmpInfo = new float[SPRITE_SIZE];

    // ---------------------------------------------------

    private final Rectangle mBound = new Rectangle();

    private Array<Updater> mUpdater = new Array<Updater>(0);

    public SpriteCA()
    {
	this(13);
    }

    protected SpriteCA(int size)
    {
	mInfo = new float[size * SPRITE_SIZE];
	mRegions = new ArrayList<TextureRegion[]>(size);
	length = size;

	mDrawable = new IntArray(false, size);
	mRunnable = new IntArray(false, size);

	setSize(100, 100);
	setOrigin(50, 50);
    }

    /**********************************************************
	 * 
	 **********************************************************/

    public void bindLayer (TextureRegion[] regionLayer)
    {
	if (size > length)
	    throw new EasyGEngineRuntimeException("Out bound of sprite length");
	mRegions.add(regionLayer);
	++size;
	idx = (size - 1) * SPRITE_SIZE;

	final float[] info = this.mInfo;

	info[idx++] = 0;
	info[idx++] = 0;
	info[idx++] = regionLayer.length;
	info[idx++] = 0;
	info[idx++] = color.toFloatBits();
    }

    public SpriteCA bindLayer (TextureRegion[]... regions)
    {
	for (TextureRegion[] region : regions)
	    bindLayer(region);
	return this;
    }

    public void swapLayer (int firstLayer, int secondLayer)
    {
	if (size <= firstLayer || size <= secondLayer || firstLayer == secondLayer)
	    return;

	tmpRegion = mRegions.get(firstLayer);

	mRegions.remove(firstLayer);
	mRegions.add(firstLayer,
		mRegions.get(secondLayer > firstLayer ? secondLayer - 1 : secondLayer));

	mRegions.remove(secondLayer);
	mRegions.add(secondLayer, tmpRegion);

	idx = SPRITE_SIZE * firstLayer;
	int i = SPRITE_SIZE * secondLayer;

	final float[] info = this.mInfo;

	System.arraycopy(info, idx, tmpInfo, 0, SPRITE_SIZE);
	System.arraycopy(info, i, info, idx, SPRITE_SIZE);
	System.arraycopy(tmpInfo, 0, info, i, SPRITE_SIZE);

    }

    public void removeTopLayer ()
    {
	idx = size--;
	mRegions.remove(idx);

	mDrawable.removeValue(idx);
	mRunnable.removeValue(idx);
    }

    public void removeFirstLayer ()
    {
	--size;
	mRegions.remove(0);
	System.arraycopy(mInfo, SPRITE_SIZE, mInfo, 0, size * SPRITE_SIZE);

	mDrawable.removeValue(0);
	mRunnable.removeValue(0);

	final int[] drawable = mDrawable.items;
	idx = drawable.length;
	for (int i = 0; i < idx; i++)
	    --drawable[i];
	mDrawable.shrink();

	final int[] runnable = mRunnable.items;
	idx = runnable.length;
	for (int i = 0; i < idx; i++)
	    --runnable[i];
	mRunnable.shrink();
    }

    public void removeLayer (int layer)
    {
	if (layer < 0 || layer >= size)
	    return;
	mRegions.remove(layer);
	idx = layer * SPRITE_SIZE;

	System.arraycopy(mInfo, idx + SPRITE_SIZE, mInfo, idx, (size - layer) * SPRITE_SIZE);
	--size;

	mDrawable.removeValue(layer);

	final int[] drawable = mDrawable.items;
	idx = 0;
	for (int i : drawable) {
	    if (i > layer)
		--drawable[idx];
	    ++idx;
	}
	mDrawable.shrink();

	mRunnable.removeValue(layer);

	final int[] runnable = mRunnable.items;
	idx = 0;
	for (int i : runnable) {
	    if (i > layer)
		--runnable[idx];
	    ++idx;
	}
	mRunnable.shrink();
    }

    /**********************************************************
	 * 
	 **********************************************************/

    public void setBounds (float x, float y, float width, float height)
    {
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;

	final float[] vertices = this.mVertices;

	vertices[X1] = x;
	vertices[Y1] = y;

	vertices[X2] = x;
	vertices[Y2] = y + height;

	vertices[X3] = x + width;
	vertices[Y3] = y + height;

	vertices[X4] = x + width;
	vertices[Y4] = y;
    }

    public void setSize (float width, float height)
    {
	this.width = width;
	this.height = height;

	float x2 = x + width;
	float y2 = y + height;

	final float[] vertices = this.mVertices;

	vertices[X2] = x;
	vertices[Y2] = y2;

	vertices[X3] = x2;
	vertices[Y3] = y2;

	vertices[X4] = x2;
	vertices[Y4] = y;
    }

    public void setPosition (float x, float y)
    {
	translate(x - this.x, y - this.y);
    }

    public void setX (float x)
    {
	translateX(x - this.x);
    }

    public void setY (float y)
    {
	translateY(y - this.y);
    }

    public void translateX (float xAmount)
    {
	this.x += xAmount;

	final float[] vertices = this.mVertices;
	vertices[X1] += xAmount;
	vertices[X2] += xAmount;
	vertices[X3] += xAmount;
	vertices[X4] += xAmount;
    }

    public void translateY (float yAmount)
    {
	this.y += yAmount;

	final float[] vertices = this.mVertices;
	vertices[Y1] += yAmount;
	vertices[Y2] += yAmount;
	vertices[Y3] += yAmount;
	vertices[Y4] += yAmount;
    }

    public void translate (float xAmount, float yAmount)
    {
	x += xAmount;
	y += yAmount;

	float[] vertices = this.mVertices;
	vertices[X1] += xAmount;
	vertices[Y1] += yAmount;

	vertices[X2] += xAmount;
	vertices[Y2] += yAmount;

	vertices[X3] += xAmount;
	vertices[Y3] += yAmount;

	vertices[X4] += xAmount;
	vertices[Y4] += yAmount;
    }

    public void setAlpha (float alpha)
    {
	this.alpha = alpha;
	final float[] info = this.mInfo;
	for (int i = 0; i < size; i++) {
	    final Color color = getColor(i);
	    color.a *= alpha;
	    idx = i * SPRITE_SIZE;
	    info[idx + COLOR] = color.toFloatBits();
	}
    }

    /* ------------------------------------------- */

    public void setOrigin (float originX, float originY)
    {
	this.originX = originX;
	this.originY = originY;
	dirty = true;
    }

    public void setRotation (float degree)
    {
	rotation = degree;
	dirty = true;
    }

    public void rotate (float degree)
    {
	rotation += degree;
	dirty = true;
    }

    public void setScale (float scaleXY)
    {
	this.scaleX = scaleXY;
	this.scaleY = scaleXY;
	dirty = true;
    }

    public void setScale (float scaleX, float scaleY)
    {
	this.scaleX = scaleX;
	this.scaleY = scaleY;
	dirty = true;
    }

    public void scale (float amount)
    {
	this.scaleX += amount;
	this.scaleY += amount;
	dirty = true;
    }

    /*************************************************************
	 * 
	 *************************************************************/

    public void setColor (float r, float g, float b, float a)
    {
	for (int i = 0; i < length; i++)
	    setColor(r, g, b, a, i);
    }

    public void setColor (Color color)
    {
	for (int i = 0; i < length; i++)
	    setColor(color, i);
    }

    public void setColor (Color[] color, int[] layer)
    {
	if (color.length != layer.length)
	    throw new EasyGEngineRuntimeException("Color length must be the same with layer length");

	final float[] info = this.mInfo;
	int j = 0;
	for (int i : layer) {
	    idx = i * SPRITE_SIZE;
	    info[idx + COLOR] = color[j].toFloatBits();
	    j++;
	}
    }

    public void setColor (Color color, int[] layer)
    {
	final float[] info = this.mInfo;
	for (int i : layer) {
	    idx = i * SPRITE_SIZE;
	    info[idx + COLOR] = color.toFloatBits();
	}
    }

    public void setColor (float r, float g, float b, float a, int[] layer)
    {
	final float[] info = this.mInfo;
	final int intBits = ((int) (255 * a) << 24) | ((int) (255 * b) << 16)
		| ((int) (255 * g) << 8) | ((int) (255 * r));
	final float floatBits = NumberUtils.intToFloatColor(intBits);

	for (int i : layer) {
	    idx = i * SPRITE_SIZE;
	    info[idx + COLOR] = floatBits;
	}
    }

    public SpriteCA setColor (Color color, int layer)
    {
	final float[] info = this.mInfo;
	idx = layer * SPRITE_SIZE;
	info[idx + COLOR] = color.toFloatBits();
	return this;
    }

    public SpriteCA setColor (float r, float g, float b, float a, int layer)
    {
	final float[] info = this.mInfo;
	final int intBits = ((int) (255 * a) << 24) | ((int) (255 * b) << 16)
		| ((int) (255 * g) << 8) | ((int) (255 * r));
	idx = layer * SPRITE_SIZE;
	info[idx + COLOR] = NumberUtils.intToFloatColor(intBits);
	return this;
    }

    /*************************************************************
	 * 
	 *************************************************************/
    public float getCenterY ()
    {
	final float[] vertices = SpriteCA.this.mVertices;

	float miny = vertices[Y1];
	float maxy = vertices[Y1];

	miny = miny > vertices[Y2] ? vertices[Y2] : miny;
	miny = miny > vertices[Y3] ? vertices[Y3] : miny;
	miny = miny > vertices[Y4] ? vertices[Y4] : miny;

	maxy = maxy < vertices[Y2] ? vertices[Y2] : maxy;
	maxy = maxy < vertices[Y3] ? vertices[Y3] : maxy;
	maxy = maxy < vertices[Y4] ? vertices[Y4] : maxy;

	return (miny + maxy) / 2;
    }

    public float getCenterX ()
    {
	final float[] vertices = SpriteCA.this.mVertices;

	float minx = vertices[X1];
	float maxx = vertices[X1];

	minx = minx > vertices[X2] ? vertices[X2] : minx;
	minx = minx > vertices[X3] ? vertices[X3] : minx;
	minx = minx > vertices[X4] ? vertices[X4] : minx;

	maxx = maxx < vertices[X2] ? vertices[X2] : maxx;
	maxx = maxx < vertices[X3] ? vertices[X3] : maxx;
	maxx = maxx < vertices[X4] ? vertices[X4] : maxx;

	return (minx + maxx) / 2;
    }

    public int getSize ()
    {
	return size;
    }

    public int getLength ()
    {
	return length;
    }

    public Color getColor (int layer)
    {
	if (layer < 0 || layer >= size)
	    return null;

	idx = layer * SPRITE_SIZE;
	idx = NumberUtils.floatToIntBits(mInfo[idx + COLOR]);
	final Color color = this.color;

	color.r = (idx & 0xff) / 255f;
	color.g = ((idx >>> 8) & 0xff) / 255f;
	color.b = ((idx >>> 16) & 0xff) / 255f;
	color.a = ((idx >>> 24) & 0xff) / 255f;
	return color;
    }

    public Rectangle getBoundingRectangle ()
    {
	final float[] vertices = SpriteCA.this.mVertices;

	float minx = vertices[X1];
	float miny = vertices[Y1];
	float maxx = vertices[X1];
	float maxy = vertices[Y1];

	minx = minx > vertices[X2] ? vertices[X2] : minx;
	minx = minx > vertices[X3] ? vertices[X3] : minx;
	minx = minx > vertices[X4] ? vertices[X4] : minx;

	maxx = maxx < vertices[X2] ? vertices[X2] : maxx;
	maxx = maxx < vertices[X3] ? vertices[X3] : maxx;
	maxx = maxx < vertices[X4] ? vertices[X4] : maxx;

	miny = miny > vertices[Y2] ? vertices[Y2] : miny;
	miny = miny > vertices[Y3] ? vertices[Y3] : miny;
	miny = miny > vertices[Y4] ? vertices[Y4] : miny;

	maxy = maxy < vertices[Y2] ? vertices[Y2] : maxy;
	maxy = maxy < vertices[Y3] ? vertices[Y3] : maxy;
	maxy = maxy < vertices[Y4] ? vertices[Y4] : maxy;

	mBound.x = minx;
	mBound.y = miny;
	mBound.width = maxx - minx;
	mBound.height = maxy - miny;

	return mBound;
    }

    @Override
    public float[] getBoundingFloatRect (float offset)
    {
	final float[] vertices = SpriteCA.this.mVertices;

	float minx = vertices[X1];
	float miny = vertices[Y1];
	float maxx = vertices[X1];
	float maxy = vertices[Y1];

	minx = minx > vertices[X2] ? vertices[X2] : minx;
	minx = minx > vertices[X3] ? vertices[X3] : minx;
	minx = minx > vertices[X4] ? vertices[X4] : minx;

	maxx = maxx < vertices[X2] ? vertices[X2] : maxx;
	maxx = maxx < vertices[X3] ? vertices[X3] : maxx;
	maxx = maxx < vertices[X4] ? vertices[X4] : maxx;

	miny = miny > vertices[Y2] ? vertices[Y2] : miny;
	miny = miny > vertices[Y3] ? vertices[Y3] : miny;
	miny = miny > vertices[Y4] ? vertices[Y4] : miny;

	maxy = maxy < vertices[Y2] ? vertices[Y2] : maxy;
	maxy = maxy < vertices[Y3] ? vertices[Y3] : maxy;
	maxy = maxy < vertices[Y4] ? vertices[Y4] : maxy;

	rect[0] = minx + offset;
	rect[1] = miny + offset;
	rect[2] = maxx - minx - offset;
	rect[3] = maxy - miny - offset;

	return rect;
    }

    @Override
    public float[] getVertices ()
    {
	return this.mVertices;
    }

    public Circle getBoundingCircle ()
    {
	return null;
    }

    public float[] getVertices (int layer)
    {
	if (layer < 0 || layer >= size)
	    return null;

	final float[] vertices = SpriteCA.this.mVertices;
	final float[] info = SpriteCA.this.mInfo;
	final float[] tmpInfo = SpriteCA.this.tmpInfo;

	idx = layer * SPRITE_SIZE;
	if (dirty) {
	    dirty = false;

	    float localX = -originX;
	    float localY = -originY;
	    float localX2 = localX + width;
	    float localY2 = localY + height;
	    float worldOriginX = this.x - localX;
	    float worldOriginY = this.y - localY;

	    tmpInfo[0] = scaleX;
	    tmpInfo[1] = scaleY;

	    if (tmpInfo[0] != 1 || tmpInfo[1] != 1) {
		localX *= tmpInfo[0];
		localY *= tmpInfo[1];
		localX2 *= tmpInfo[0];
		localY2 *= tmpInfo[1];
	    }

	    tmpInfo[0] = rotation;

	    if (tmpInfo[0] != 0) {
		final float cos = MathUtils.cosDeg(tmpInfo[0]);
		final float sin = MathUtils.sinDeg(tmpInfo[0]);
		final float localXCos = localX * cos;
		final float localXSin = localX * sin;
		final float localYCos = localY * cos;
		final float localYSin = localY * sin;
		final float localX2Cos = localX2 * cos;
		final float localX2Sin = localX2 * sin;
		final float localY2Cos = localY2 * cos;
		final float localY2Sin = localY2 * sin;

		final float x1 = localXCos - localYSin + worldOriginX;
		final float y1 = localYCos + localXSin + worldOriginY;
		vertices[X1] = x1;
		vertices[Y1] = y1;

		final float x2 = localXCos - localY2Sin + worldOriginX;
		final float y2 = localY2Cos + localXSin + worldOriginY;
		vertices[X2] = x2;
		vertices[Y2] = y2;

		final float x3 = localX2Cos - localY2Sin + worldOriginX;
		final float y3 = localY2Cos + localX2Sin + worldOriginY;
		vertices[X3] = x3;
		vertices[Y3] = y3;

		vertices[X4] = x1 + (x3 - x2);
		vertices[Y4] = y3 - (y2 - y1);
	    } else {
		final float x1 = localX + worldOriginX;
		final float y1 = localY + worldOriginY;
		final float x2 = localX2 + worldOriginX;
		final float y2 = localY2 + worldOriginY;

		vertices[X1] = x1;
		vertices[Y1] = y1;

		vertices[X2] = x1;
		vertices[Y2] = y2;

		vertices[X3] = x2;
		vertices[Y3] = y2;

		vertices[X4] = x2;
		vertices[Y4] = y1;
	    }
	}

	tmpInfo[0] = info[idx + COLOR];
	vertices[C1] = tmpInfo[0];
	vertices[C2] = tmpInfo[0];
	vertices[C3] = tmpInfo[0];
	vertices[C4] = tmpInfo[0];

	final TextureRegion region = mRegions.get(layer)[(int) info[idx + FRAME_NUMBER]];
	vertices[U1] = region.getU();
	vertices[V1] = region.getV2();

	vertices[U2] = region.getU();
	vertices[V2] = region.getV();

	vertices[U3] = region.getU2();
	vertices[V3] = region.getV();

	vertices[U4] = region.getU2();
	vertices[V4] = region.getV2();

	return vertices;
    }

    public float getX ()
    {
	return x;
    }

    public float getY ()
    {
	return y;
    }

    public float getWidth ()
    {
	return width;
    }

    public float getHeight ()
    {
	return height;
    }

    @Override
    public float getOriginX ()
    {
	return originX;
    }

    @Override
    public float getOriginY ()
    {
	return originY;

    }

    @Override
    public float getRotation ()
    {
	return rotation;

    }

    @Override
    public float getScaleX ()
    {
	return scaleX;

    }

    @Override
    public float getScaleY ()
    {
	return scaleY;
    }

    /*************************************************************
	 * 
	 *************************************************************/

    public SpriteCA addDrawableLayer (int layer)
    {
	if (layer >= 0 && layer < size && !mDrawable.contains(layer))
	    mDrawable.add(layer);
	return this;
    }

    public SpriteCA addDrawableLayer (int[] layer)
    {
	for (int i : layer) {
	    if (i >= 0 && i < size && !mDrawable.contains(i))
		mDrawable.add(i);
	}
	return this;
    }

    public SpriteCA setDrawableLayer (int... layer)
    {
	mDrawable.clear();
	addDrawableLayer(layer);
	mDrawable.shrink();
	return this;
    }

    public SpriteCA removeDrawableLayer (int layer)
    {
	mDrawable.removeValue(layer);
	return this;
    }

    public SpriteCA removeDrawableLayer (int[] layer)
    {
	for (int i : layer) {
	    mDrawable.removeValue(i);
	}
	return this;
    }

    public int[] getDrawbleLayer ()
    {
	return mDrawable.items;
    }

    public void draw (SpriteBatch batch)
    {
	final int[] drawable = mDrawable.items;
	for (int i : drawable) {
	    batch.draw(mRegions.get(i)[0].getTexture(), getVertices(i), 0, 20);
	}
    }

    @Override
    public void draw (SpriteBatch batch, float alpha)
    {
	final int[] drawable = mDrawable.items;
	for (int i : drawable) {
	    batch.draw(mRegions.get(i)[0].getTexture(), getVertices(i), 0, 20);
	}
    }

    /*************************************************************
	 * 
	 *************************************************************/
    public SpriteCA addRunnableLayer (int layer)
    {
	if (layer >= 0 && layer < size)
	    mRunnable.add(layer);
	return this;
    }

    public void addRunnableLayer (int... layer)
    {
	for (int i : layer) {
	    if (i >= 0 && i < size)
		mRunnable.add(i);
	}
    }

    public void setRunnableLayer (int... layer)
    {
	mRunnable.clear();
	mRunnable.shrink();
	addRunnableLayer(layer);
    }

    public void removeRunnableLayer (int... layer)
    {
	for (int i : layer) {
	    mRunnable.removeValue(i);
	}
    }

    public int[] getRunnableLayer ()
    {
	return mRunnable.items;
    }

    public void setFrameDuration (float frameDuration)
    {
	final int[] runnable = this.mRunnable.items;
	final float[] info = this.mInfo;

	for (int i : runnable) {
	    idx = i * SPRITE_SIZE;
	    info[idx + FRAME_DURATION] = frameDuration;
	}
    }

    public void setFrameDuration (float frameDuration, int[] layer)
    {
	final float[] info = this.mInfo;

	for (int i : layer) {
	    if (i < 0 && i >= size)
		continue;
	    else {
		idx = i * SPRITE_SIZE;
		info[idx + FRAME_DURATION] = frameDuration;
	    }
	}
    }

    public void setFrameDuration (float[] frameDurations, int[] layer)
    {
	if (frameDurations.length != layer.length)
	    return;
	int i = 0;
	final float[] info = this.mInfo;
	for (int l : layer) {
	    if (l < 0 && l >= size) {
		++i;
		continue;
	    } else {
		idx = l * SPRITE_SIZE;
		info[idx + FRAME_DURATION] = frameDurations[i];
	    }
	}
    }

    public void start ()
    {
	RUN = true;
    }

    public void start (float frameDuration)
    {
	RUN = true;
	setFrameDuration(frameDuration);
    }

    @Override
    public void start (float frameDuration, int playMode)
    {
	RUN = true;
	setFrameDuration(frameDuration);
    }

    @Override
    public void pause ()
    {
	RUN = false;
    }

    @Override
    public boolean isRunning ()
    {
	return RUN;
    }

    public void stop ()
    {
	RUN = false;
	resetFrame();
    }

    public void switchState ()
    {
	RUN = !RUN;
    }

    public void resetFrame ()
    {
	final float[] info = this.mInfo;
	for (int i = 0; i < size; i++) {
	    idx = i * SPRITE_SIZE;
	    info[idx + FRAME_NUMBER] = 0;
	    info[idx + STATE_TIME] = 0;
	}
    }

    public void resetFrame (int[] layer)
    {
	final float[] info = this.mInfo;
	for (int i : layer) {
	    if (i >= 0 && i < size) {
		idx = i * SPRITE_SIZE;
		info[idx + FRAME_NUMBER] = 0;
		info[idx + STATE_TIME] = 0;
	    }
	}
    }

    public void update (float delta)
    {
	if (!RUN)
	    return;

	final int[] runnable = this.mRunnable.items;
	final float[] info = this.mInfo;
	for (int i : runnable) {
	    idx = i * SPRITE_SIZE;
	    if (info[idx + FRAME_DURATION] > 0) {
		info[idx + STATE_TIME] += delta;
		info[idx + FRAME_NUMBER] = (int) (info[idx + STATE_TIME] / info[idx
			+ FRAME_DURATION]);
		info[idx + FRAME_NUMBER] = (int) (info[idx + FRAME_NUMBER] % info[idx
			+ FRAME_LENGTH]);
	    }
	}
	// ============= update updatable =============
	for (int i = 0, n = mUpdater.size; i < n; i++) {
	    final Updater tmp = mUpdater.get(i);

	    if (!tmp.isStoped())
		tmp.update(this, delta);
	    else {
		mUpdater.removeValue(tmp, true);
		--i;
		--n;
	    }
	}
    }

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
    public void removeUpdater (Updater updater)
    {
	mUpdater.removeValue(updater, true);
    }

    public void noUpdater ()
    {
	this.mUpdater.clear();
    }

    /**********************************************************
	 * 
	 **********************************************************/

    @Override
    public void dispose ()
    {
	mRegions.clear();
	tmpRegion = null;
	mDrawable = null;
	mRunnable = null;
    }

    @Override
    public void reset ()
    {
	int size = mVertices.length;
	for (int i = 0; i < size; i++)
	    mVertices[i] = 0;

	size = mInfo.length;
	for (int i = 0; i < size; i++)
	    mInfo[i] = 0;

	stop();
	setPosition(0, 0);
	setSize(0, 0);
	setScale(1, 1);

	setColor(1, 1, 1, 1);
    }

    @Deprecated
    public int getFrameNumber ()
    {
	return 0;
    }

    @Deprecated
    public TextureRegion[] getFrames ()
    {
	return null;
    }
}
