package okj.easy.graphics.graphics2d;

import org.ege.utils.SpriteBackend;
import org.ege.utils.Updater;
import org.ege.utils.exception.EasyGEngineRuntimeException;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Animator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FloatArray;

/**
 * SpriteCAA : <b>ASYNCHRONIZE COMPOSITE ANIMATE Sprite</b>
 * 
 * @author Ngo Trong Trung
 */
public class Spriter implements SpriteBackend, Disposable, Animator
{
    private final SpriteBackend[] mSpriteList;
    private final Scaler[] mScaler;

    // Config
    private final int[] mDrawable;
    private int drawableSize = 0;

    private final int[] mRunnable;
    private int runnbaleSize = 0;

    private final int[] mCollision;
    private int collisionSize = 0;

    // param

    private boolean RUN;

    private final int limit;
    private int size = 0;

    private SpriteBackend mOriginSprite;
    private float mOriginWidth;
    private float mOriginHeight;

    private final FloatArray rect = new FloatArray(4);
    // --------------------------------------------------

    private int idx = 0;

    // --------------------------------------------------

    private float x;
    private float y;
    private float w;
    private float h;

    private Array<Updater> mUpdater = new Array<Updater>(0);

    /**
     * Construct a default spriter with size 13 child sprite
     */
    public Spriter()
    {
	this(13);
    }

    /**
     * Construct a spriter with given sprite limit ( should override this
     * method)
     * 
     * @param limit
     */
    protected Spriter(int limit)
    {
	this.limit = limit;

	mSpriteList = new SpriteBackend[limit];

	mScaler = new Scaler[limit];

	mDrawable = new int[limit];
	mRunnable = new int[limit];
	mCollision = new int[limit];

	for (int i = 0; i < limit; i++) {
	    mRunnable[i] = mDrawable[i] = -1;
	}
    }

    /********************************************************
     * Layer manage
     ********************************************************/

    private Scaler calculateScaler (int id, SpriteBackend sprite, float x, float y, float width,
	    float height)
    {
	if (mScaler[id] == null) {
	    mScaler[id] = new Scaler();
	}

	final Scaler scale = mScaler[id];

	scale.sprite = sprite;
	scale.xRatio = x / mOriginWidth;
	scale.yRatio = y / mOriginHeight;
	scale.widthRatio = width / mOriginWidth;
	scale.heightRatio = height / mOriginHeight;

	return scale;
    }

    public void bindOriginLayer (SpriteBackend sprite)
    {
	w = mOriginWidth = sprite.getWidth();
	h = mOriginHeight = sprite.getHeight();
	this.x = sprite.getX();
	this.y = sprite.getY();
	mOriginSprite = sprite;

	calculateScaler(0, sprite, 0, 0, w, h);
	mSpriteList[0] = sprite;

	if (size == 0)
	    ++size;
	else
	    refresh();
    }

    public void bindLayer (float x, float y, float width, float height, SpriteBackend sprite)
    {
	if (size > limit)
	    throw new EasyGEngineRuntimeException("Out bound of sprite limit");

	if (size == 0) {
	    w = mOriginWidth = sprite.getWidth();
	    h = mOriginHeight = sprite.getHeight();
	    this.x = sprite.getX();
	    this.y = sprite.getY();
	    mOriginSprite = sprite;
	    calculateScaler(0, sprite, 0, 0, w, h);
	} else {
	    final Scaler scale = calculateScaler(size, sprite, x, y, width, height);
	    scale.apply();
	}

	mSpriteList[size++] = sprite;
    }

    public Spriter bindLayer (float[] attributes, SpriteBackend... sprites)
    {
	if (attributes.length / 4 != sprites.length)
	    throw new EasyGEngineRuntimeException(
		    "Attributes length must be equal regions.length * 4");
	int i = 0;
	for (SpriteBackend sprite : sprites) {
	    idx = i * 4;
	    bindLayer(attributes[idx++], attributes[idx++], attributes[idx++], attributes[idx++],
		    sprite);
	    ++i;
	}
	return this;
    }

    public void bindLayer (int id, float x, float y, float width, float height, SpriteBackend sprite)
    {
	if (size > limit)
	    throw new EasyGEngineRuntimeException("Out bound of sprite limit");

	if (id > size)
	    return;
	else if (id == size) {
	    bindLayer(x, y, width, height, sprite);
	    return;
	}

	mSpriteList[id] = sprite;

	if (id == 0) {
	    w = mOriginWidth = sprite.getWidth();
	    h = mOriginHeight = sprite.getHeight();
	    this.x = sprite.getX();
	    this.y = sprite.getY();
	    mOriginSprite = sprite;
	    calculateScaler(0, sprite, 0, 0, w, h);
	    refresh();
	} else {
	    final Scaler scale = calculateScaler(id, sprite, x, y, width, height);
	    scale.apply();
	}
    }

    public SpriteBackend getSprite (int id)
    {
	return mSpriteList[id];
    }

    public int getLayerId (SpriteBackend sprite)
    {
	final SpriteBackend[] list = Spriter.this.mSpriteList;
	for (int i = 0; i < size; i++)
	    if (list[i] == sprite)
		return i;
	return -1;
    }

    /********************************************************
     * Color method
     ********************************************************/

    public void setColor (float r, float g, float b, float a)
    {
	final SpriteBackend[] list = Spriter.this.mSpriteList;
	for (int i = 0; i < size; i++)
	    list[i].setColor(r, g, b, a);
    }

    public void setColor (Color color)
    {
	final SpriteBackend[] list = Spriter.this.mSpriteList;
	for (int i = 0; i < size; i++)
	    list[i].setColor(color);
    }

    public void setColor (Color[] color, int[] layer)
    {
	if (color.length != layer.length)
	    throw new EasyGEngineRuntimeException("Color length must be the same with layer length");

	final SpriteBackend[] list = Spriter.this.mSpriteList;
	int j = 0;
	for (int i : layer) {
	    list[i].setColor(color[j]);
	    j++;
	}
    }

    public void setColor (Color color, int[] layer)
    {
	if (layer.length > size)
	    throw new EasyGEngineRuntimeException("Layer length must  <= size");

	final SpriteBackend[] list = Spriter.this.mSpriteList;
	for (int i : layer)
	    list[i].setColor(color);
    }

    public void setColor (float r, float g, float b, float a, int[] layer)
    {
	if (layer.length > size)
	    throw new EasyGEngineRuntimeException("Layer length must  <= size");

	final SpriteBackend[] list = Spriter.this.mSpriteList;
	for (int i : layer)
	    list[i].setColor(r, g, b, a);
    }

    public Spriter setColor (Color color, int layer)
    {
	mSpriteList[layer].setColor(color);
	return this;
    }

    public Spriter setColor (float r, float g, float b, float a, int layer)
    {
	mSpriteList[layer].setColor(r, g, b, layer);
	return this;
    }

    /********************************************************
     * Configuration
     ********************************************************/

    @Override
    public void setBounds (float x, float y, float width, float height)
    {
	this.x = x;
	this.y = y;
	this.w = width;
	this.h = height;

	mOriginSprite.setBounds(x, y, width, height);
	refresh();
    }

    @Override
    public void setSize (float width, float height)
    {
	this.w = width;
	this.h = height;

	mOriginSprite.setSize(width, height);
	refresh();
    }

    @Override
    public void setPosition (float x, float y)
    {
	final float deltaX = x - this.x;
	final float deltaY = y - this.y;
	this.x = x;
	this.y = y;

	mOriginSprite.setPosition(x, y);

	for (int i = 1; i < size; i++)
	    mSpriteList[i].translate(deltaX, deltaY);
    }

    public void setX (float x)
    {
	final float deltaX = x - this.x;
	this.x = x;

	mOriginSprite.setX(x);

	for (int i = 1; i < size; i++)
	    mSpriteList[i].translateX(deltaX);
    }

    @Override
    public void setY (float y)
    {
	final float deltaY = y - this.y;
	this.y = y;

	mOriginSprite.setY(y);

	for (int i = 1; i < size; i++)
	    mSpriteList[i].translateY(deltaY);
    }

    @Override
    public void translate (float xAmount, float yAmount)
    {
	this.x += xAmount;
	this.y += yAmount;

	mOriginSprite.translate(xAmount, yAmount);
	for (int i = 1; i < size; i++)
	    mSpriteList[i].translate(xAmount, yAmount);
    }

    @Override
    public void translateX (float xAmount)
    {
	this.x += xAmount;

	mOriginSprite.translateX(xAmount);
	for (int i = 1; i < size; i++)
	    mSpriteList[i].translateX(xAmount);
    }

    @Override
    public void translateY (float yAmount)
    {
	this.y += yAmount;

	mOriginSprite.translateY(yAmount);
	for (int i = 1; i < size; i++)
	    mSpriteList[i].translateY(yAmount);
    }

    // ------------------------------------------------------

    @Override
    public void setOrigin (float originX, float originY)
    {
	mOriginSprite.setOrigin(originX, originY);
	float newOriginX;
	float newOriginY;
	for (int i = 1; i < size; i++) {
	    final SpriteBackend sprite = mSpriteList[i];
	    newOriginX = sprite.getX() - x;
	    newOriginY = sprite.getY() - y;
	    sprite.setOrigin(originX - newOriginX, originY - newOriginY);
	}
    }

    public void setOrigin (int layer, float originX, float originY)
    {
	mSpriteList[layer].setOrigin(originX, originY);
    }

    // ------------------------------------------------------

    @Override
    public void setRotation (float degree)
    {
	mOriginSprite.setRotation(degree);
	for (int i = 1; i < size; i++)
	    mSpriteList[i].setRotation(degree);
    }

    public Spriter setRotation (int layer, float degree)
    {
	mSpriteList[layer].setRotation(degree);
	return this;
    }

    // ------------------------------------------------------

    @Override
    public void rotate (float degree)
    {
	mOriginSprite.rotate(degree);
	for (int i = 1; i < size; i++)
	    mSpriteList[i].rotate(degree);
    }

    public void rotate (int layer, float degree)
    {
	mSpriteList[layer].rotate(degree);
    }

    // ------------------------------------------------------

    @Override
    public void setScale (float scaleXY)
    {
	for (SpriteBackend sprite : mSpriteList)
	    sprite.setScale(scaleXY);
    }

    @Override
    public void setScale (float scaleX, float scaleY)
    {
	for (SpriteBackend sprite : mSpriteList)
	    sprite.setScale(scaleX, scaleY);
    }

    @Override
    public void scale (float amount)
    {
	for (SpriteBackend sprite : mSpriteList)
	    sprite.scale(amount);
    }

    public void setScale (int layer, float scaleXY)
    {
	mSpriteList[layer].setScale(scaleXY);
    }

    public void setScale (int layer, float scaleX, float scaleY)
    {
	mSpriteList[layer].setScale(scaleX, scaleY);
    }

    public void scale (int layer, float amount)
    {
	mSpriteList[layer].scale(amount);
    }

    /********************************************************
     * Getter
     ********************************************************/

    @Override
    public float[] getVertices ()
    {
	return mOriginSprite.getVertices();
    }

    @Override
    public float getX ()
    {
	return x;
    }

    public float getX (int layer)
    {
	return mSpriteList[layer].getX();
    }

    @Override
    public float getCenterX ()
    {
	return mOriginSprite.getCenterX();
    }

    public float getCenterX (int layer)
    {
	return mSpriteList[layer].getCenterX();
    }

    @Override
    public float getY ()
    {
	return y;
    }

    public float getY (int layer)
    {
	return mSpriteList[layer].getY();
    }

    @Override
    public float getCenterY ()
    {
	return mOriginSprite.getCenterY();
    }

    public float getCenterY (int layer)
    {
	return mSpriteList[layer].getCenterY();
    }

    @Override
    public float getWidth ()
    {
	return w;
    }

    public float getWidth (int layer)
    {
	return mSpriteList[layer].getWidth();
    }

    @Override
    public float getHeight ()
    {
	return h;
    }

    public float getHeight (int layer)
    {
	return mSpriteList[layer].getHeight();
    }

    @Override
    public float getOriginX ()
    {
	return mOriginSprite.getOriginX();
    }

    public float getOriginX (int layer)
    {
	return mSpriteList[layer].getOriginX();
    }

    @Override
    public float getOriginY ()
    {
	return mOriginSprite.getOriginY();
    }

    public float getOriginY (int layer)
    {
	return mSpriteList[layer].getOriginY();
    }

    @Override
    public float getRotation ()
    {
	return mOriginSprite.getRotation();
    }

    public float getRotation (int layer)
    {
	return mSpriteList[layer].getRotation();
    }

    @Override
    public float getScaleX ()
    {
	return mOriginSprite.getScaleX();
    }

    public float getScaleX (int layer)
    {
	return mSpriteList[layer].getScaleX();
    }

    @Override
    public float getScaleY ()
    {
	return mOriginSprite.getScaleY();
    }

    public float getScaleY (int layer)
    {
	return mSpriteList[layer].getScaleY();
    }

    public int getLimit ()
    {
	return limit;
    }

    public int getSize ()
    {
	return size;
    }

    /********************************************************
	 * 
	 ********************************************************/

    @Override
    public Rectangle getBoundingRectangle ()
    {
	final Rectangle origin = mOriginSprite.getBoundingRectangle();
	for (int i = 0; i < collisionSize; i++)
	    origin.merge(mSpriteList[mCollision[i]].getBoundingRectangle());
	return origin;
    }

    @Override
    public float[] getBoundingFloatRect (float offset)
    {
	final FloatArray result = Spriter.this.rect;
	result.clear();
	for (int i = 0; i < collisionSize; i++) {
	    final float[] rect = mSpriteList[mCollision[i]].getBoundingFloatRect(offset);
	    result.addAll(rect);
	}
	if (result.size != result.items.length)
	    result.shrink();
	return result.items;
    }

    @Override
    public Circle getBoundingCircle ()
    {
	return null;
    }

    private boolean containCollision (int layer)
    {
	for (int i = 0; i < collisionSize; i++)
	    if (mCollision[i] == layer)
		return true;
	return false;
    }

    public Spriter addCollisionLayer (int layer)
    {
	if (collisionSize < limit && !containCollision(layer) && mSpriteList[layer] != null)
	    mCollision[collisionSize++] = layer;
	return this;
    }

    public Spriter setCollisionLayer (int... layer)
    {
	if (layer.length > limit)
	    return this;
	collisionSize = 0;
	for (int i : layer) {
	    if (mSpriteList[i] != null)
		mCollision[collisionSize++] = i;
	}
	return this;
    }

    public Spriter removeCollisionLayer (int layer)
    {
	int i;
	outer: for (i = 0; i < collisionSize; i++)
	    if (mCollision[i] == layer)
		break outer;
	System.arraycopy(mCollision, i + 1, mCollision, i, collisionSize - i);
	mCollision[--collisionSize] = -1;
	return this;
    }

    public Spriter removeCollsionLayer (int[] layer)
    {
	if (layer.length > collisionSize)
	    return this;

	for (int i : layer) {
	    removeCollisionLayer(i);
	}
	return this;
    }

    public int[] getCollisionLayer ()
    {
	return mCollision;
    }

    public void clearCollision ()
    {
	for (int i = 0; i < limit; i++)
	    mCollision[i] = -1;
	collisionSize = 0;
    }

    /********************************************************
	 * 
	 ********************************************************/

    private boolean containDrawable (int layer)
    {
	for (int i = 0; i < drawableSize; i++)
	    if (mDrawable[i] == layer)
		return true;

	return false;
    }

    public Spriter addDrawableLayer (int layer)
    {
	if (drawableSize < limit && !containDrawable(layer) && mSpriteList[layer] != null)
	    mDrawable[drawableSize++] = layer;
	return this;
    }

    public Spriter setDrawableLayer (int... layer)
    {
	if (layer.length > limit)
	    return this;

	drawableSize = 0;
	for (int i : layer) {
	    if (mSpriteList[i] != null)
		mDrawable[drawableSize++] = i;
	}
	return this;
    }

    public Spriter removeDrawableLayer (int layer)
    {
	int i;
	outer: for (i = 0; i < drawableSize; i++)
	    if (mDrawable[i] == layer)
		break outer;
	System.arraycopy(mDrawable, i + 1, mDrawable, i, drawableSize - i);
	mDrawable[--drawableSize] = -1;
	return this;
    }

    public Spriter removeDrawableLayer (int[] layer)
    {
	if (layer.length > drawableSize)
	    return this;

	for (int i : layer) {
	    removeDrawableLayer(i);
	}
	return this;
    }

    public int[] getDrawbleLayer ()
    {
	return mDrawable;
    }

    public void clearDrawable ()
    {
	stop();
	for (int i = 0; i < limit; i++)
	    mDrawable[i] = -1;
	drawableSize = 0;
    }

    public void draw (SpriteBatch batch)
    {
	final int[] drawable = Spriter.this.mDrawable;
	for (int i = 0; i < drawableSize; i++) {
	    mSpriteList[drawable[i]].draw(batch);
	}
    }

    @Override
    public void draw (SpriteBatch batch, float alpha)
    {
	final int[] drawable = Spriter.this.mDrawable;
	for (int i = 0; i < drawableSize; i++) {
	    mSpriteList[drawable[i]].draw(batch, alpha);
	}
    }

    /********************************************************
	 * 
	 ********************************************************/
    private boolean containRunnable (int layer)
    {
	for (int i = 0; i < runnbaleSize; i++)
	    if (mRunnable[i] == layer)
		return true;

	return false;
    }

    public Spriter addRunnableLayer (int layer)
    {
	if (runnbaleSize < limit && !containRunnable(layer) && mSpriteList[layer] != null)
	    mDrawable[runnbaleSize++] = layer;
	return this;
    }

    public Spriter setRunnableLayer (int... layer)
    {
	if (layer.length > limit)
	    return this;

	runnbaleSize = 0;
	for (int i : layer) {
	    final SpriteBackend sprite = mSpriteList[i];
	    if (sprite != null && sprite instanceof Animator)
		mRunnable[runnbaleSize++] = i;
	}
	return this;
    }

    public Spriter removeRunnableLayer (int layer)
    {
	int i;
	outer: for (i = 0; i < runnbaleSize; i++)
	    if (mRunnable[i] == layer)
		break outer;
	System.arraycopy(mRunnable, i + 1, mRunnable, i, runnbaleSize - i);
	mRunnable[--runnbaleSize] = -1;
	return this;
    }

    public Spriter removeRunnableLayer (int[] layer)
    {
	if (layer.length > runnbaleSize)
	    return this;

	for (int i : layer) {
	    removeRunnableLayer(i);
	}
	return this;
    }

    public void clearRunnable ()
    {
	stop();
	for (int i = 0; i < limit; i++)
	    mRunnable[i] = -1;
	runnbaleSize = 0;
    }

    public int[] getRunnableLayer ()
    {
	return mRunnable;
    }

    public void setFrameDuration (float frameDuration)
    {
	final int[] runnable = this.mRunnable;

	for (int i = 0; i < runnbaleSize; i++) {
	    ((Animator) mSpriteList[runnable[i]]).setFrameDuration(frameDuration);
	}
    }

    public void setFrameDuration (float frameDuration, int[] layer)
    {
	for (int i : layer) {
	    if (i < 0 && i >= size)
		continue;
	    else {
		final SpriteBackend sprite = mSpriteList[i];
		if (sprite instanceof Animator)
		    ((Animator) sprite).setFrameDuration(frameDuration);
	    }
	}
    }

    public void setFrameDuration (float[] frameDurations, int[] layer)
    {
	if (frameDurations.length != layer.length)
	    return;
	int i = 0;
	for (int l : layer) {
	    if (l < 0 && l >= size) {
		++i;
		continue;
	    } else {
		final SpriteBackend sprite = mSpriteList[i];
		if (sprite instanceof Animator)
		    ((Animator) sprite).setFrameDuration(frameDurations[i]);
	    }
	}
    }

    public void start ()
    {
	RUN = true;
	for (int i = 0; i < runnbaleSize; i++)
	    ((Animator) mSpriteList[mRunnable[i]]).start();
    }

    public void start (float frameDuration)
    {
	RUN = true;
	for (int i = 0; i < runnbaleSize; i++) {
	    ((Animator) mSpriteList[mRunnable[i]]).start(frameDuration);
	}
    }

    @Override
    public void start (float frameDuration, int playMode)
    {
	RUN = true;
	for (int i = 0; i < runnbaleSize; i++)
	    ((Animator) mSpriteList[mRunnable[i]]).start(frameDuration, playMode);
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
	for (int i = 0; i < runnbaleSize; i++)
	    ((Animator) mSpriteList[mRunnable[i]]).resetFrame();
    }

    public void resetFrame (int... layer)
    {
	for (int i : layer)
	    ((Animator) mSpriteList[i]).resetFrame();
    }

    public void update (float delta)
    {
	if (!RUN) {
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
	    return;
	}

	final int[] runnable = this.mRunnable;
	for (int i = 0; i < runnbaleSize; i++)
	    ((Animator) mSpriteList[runnable[i]]).update(delta);
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

    public void noUpdater ()
    {
	this.mUpdater.clear();
    }

    /********************************************************
	 * 
	 ********************************************************/

    @Override
    public void reset ()
    {
	stop();

	setPosition(0, 0);
	setSize(0, 0);
	setOrigin(0, 0);
	setRotation(0);
	setScale(1);
    }

    @Override
    public void dispose ()
    {
	for (int i = 0; i < limit; i++) {
	    mScaler[i] = null;
	    mSpriteList[i] = null;
	}
	mOriginSprite = null;
    }

    private void refresh ()
    {
	for (int i = 0; i < size; i++) {
	    mScaler[i].apply();
	}
    }

    class Scaler
    {
	float xRatio;
	float yRatio;
	float widthRatio;
	float heightRatio;

	SpriteBackend sprite;

	void apply ()
	{
	    sprite.setBounds(x + xRatio * w, y + yRatio * h, widthRatio * w, heightRatio * h);
	}

	String info ()
	{
	    return "xRatio : " + xRatio + " " + "yRatio : " + yRatio + " " + "widthRatio : "
		    + widthRatio + " "
		    + "heightRatio : " + heightRatio + " ";
	}
    }
}
