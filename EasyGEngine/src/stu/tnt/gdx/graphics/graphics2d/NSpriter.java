package stu.tnt.gdx.graphics.graphics2d;

import stu.tnt.gdx.utils.Animator;
import stu.tnt.gdx.utils.Updater;
import stu.tnt.gdx.utils.exception.EasyGEngineRuntimeException;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IdentityMap;
import com.badlogic.gdx.utils.IdentityMap.Values;

/**
 * NSpriter.java {@link NManager}
 * 
 * Created on: Oct 12, 2012 Author: Trung
 */
public class NSpriter extends NManager implements Animator, SpriteBackend,
		Disposable {

	// =========================================
	// sprite params
	private final IdentityMap<NativeSpriteBackend, NScaler> mScaler;

	// ========================================
	// Config
	private final Array<NativeSpriteBackend> mDrawable;
	private final Array<NativeSpriteBackend> mRunnable;

	// =======================================
	// param

	private boolean RUN;

	private NativeSpriteBackend mOriginSprite;
	private float mOriginWidth;
	private float mOriginHeight;

	// =======================================

	private float x;
	private float y;
	private float w;
	private float h;

	private Array<Updater> mUpdater = new Array<Updater>(0);

	/**
	 * Construct a spriter with given sprite limit ( should override this
	 * method)
	 * 
	 * @param limit
	 */
	protected NSpriter(long address, NWorld world) {
		super(address, world);

		mScaler = new IdentityMap<NativeSpriteBackend, NSpriter.NScaler>(13);

		mDrawable = new Array<NativeSpriteBackend>(13);
		mRunnable = new Array<NativeSpriteBackend>(13);
	}

	/******************************************************** NManager method ********************************************************/

	@Deprecated
	/**
	 * This method shouldn't be call directly 
	 */
	public void clear() {
		super.clear();
	}

	@Override
	public void remove(NativeSpriteBackend sprite) {
		super.remove(sprite);
		mDrawable.removeValue(sprite, true);
		mRunnable.removeValue(sprite, true);
	}

	public void removeFromDrawableRunnable(NativeSpriteBackend sprite) {
		mDrawable.removeValue(sprite, true);
		mRunnable.removeValue(sprite, true);
	}

	/******************************************************** Layer manage ********************************************************/

	private NScaler calculateScaler(NativeSpriteBackend sprite, float x,
			float y, float width, float height) {

		NScaler scale = null;
		if (mScaler.get(sprite) == null) {
			scale = new NScaler();
			mScaler.put(sprite, scale);
		} else
			scale = mScaler.get(sprite);

		scale.sprite = sprite;
		scale.xRatio = x / mOriginWidth;
		scale.yRatio = y / mOriginHeight;
		scale.widthRatio = width / mOriginWidth;
		scale.heightRatio = height / mOriginHeight;

		return scale;
	}

	public void bindOriginLayer(NativeSpriteBackend sprite, float originWidth,
			float originHeight) {
		manage(sprite);

		w = mOriginWidth = originWidth;
		h = mOriginHeight = originHeight;
		this.x = 0;
		this.y = 0;
		mOriginSprite = sprite;

		calculateScaler(sprite, 0, 0, w, h);

		refresh();
	}

	public void bindOriginLayer(NativeSpriteBackend sprite) {
		manage(sprite);

		w = mOriginWidth = (sprite.getWidth() == 0 ? 1 : sprite.getWidth());
		h = mOriginHeight = (sprite.getHeight() == 0 ? 1 : sprite.getHeight());
		this.x = 0;
		this.y = 0;
		mOriginSprite = sprite;

		calculateScaler(sprite, 0, 0, w, h);

		refresh();
	}

	public void bindLayer(NativeSpriteBackend sprite, float x, float y,
			float width, float height) {
		manage(sprite);

		if (mSpriteList.size == 0) {
			w = mOriginWidth = sprite.getWidth();
			h = mOriginHeight = sprite.getHeight();
			this.x = sprite.getX();
			this.y = sprite.getY();
			mOriginSprite = sprite;
			calculateScaler(sprite, 0, 0, w, h);
		} else {
			final NScaler scale = calculateScaler(sprite, x, y, width, height);
			scale.apply();
		}
	}

	public void bindLayer(NativeSpriteBackend sprite) {
		manage(sprite);

		if (mSpriteList.size == 0) {
			bindOriginLayer(sprite);
		} else {
			final NScaler scale = calculateScaler(sprite, sprite.getX(),
					sprite.getY(), sprite.getWidth(), sprite.getHeight());
			scale.apply();
		}
	}

	public void bindLayer(NativeSpriteBackend sprite, int id, float x, float y,
			float width, float height) {
		if (id > mSpriteList.size)
			return;
		else if (id == mSpriteList.size) {
			bindLayer(sprite, x, y, width, height);
		}

		manage(sprite);
		mScaler.remove(mSpriteList.removeIndex(id));

		if (id == 0) {
			w = mOriginWidth = sprite.getWidth();
			h = mOriginHeight = sprite.getHeight();
			this.x = sprite.getX();
			this.y = sprite.getY();
			mOriginSprite = sprite;
			calculateScaler(sprite, 0, 0, w, h);
			refresh();
		} else {
			final NScaler scale = calculateScaler(sprite, x, y, width, height);
			scale.apply();
		}
	}

	public void apply(NativeSpriteBackend sprite, float x, float y,
			float width, float height) {
		if (!mSpriteList.contains(sprite, true))
			return;

		if (sprite != mOriginSprite) {
			NScaler scale = mScaler.get(sprite);
			scale.xRatio = x / mOriginWidth;
			scale.yRatio = y / mOriginHeight;
			scale.widthRatio = width / mOriginWidth;
			scale.heightRatio = height / mOriginHeight;

			scale.apply();
		} else {
			setSize(width, height);
		}
	}

	public void apply(NativeSpriteBackend sprite, float width, float height) {
		if (!mSpriteList.contains(sprite, true))
			return;

		if (sprite != mOriginSprite) {
			NScaler scale = mScaler.get(sprite);
			scale.widthRatio = width / mOriginWidth;
			scale.heightRatio = height / mOriginHeight;

			scale.apply();
		} else {
			setSize(width, height);
		}
	}

	public NativeSpriteBackend getSprite(int id) {
		return mSpriteList.get(id);
	}

	public int getLayerId(NativeSpriteBackend sprite) {
		return mSpriteList.indexOf(sprite, true);
	}

	/******************************************************** Color method ********************************************************/

	public void setColor(float r, float g, float b, float a) {
		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.setColor(r, g, b, a);
	}

	public void setColor(Color color) {
		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.setColor(color);
	}

	public void setColor(Color[] color, int[] layer) {
		if (color.length != layer.length)
			throw new EasyGEngineRuntimeException(
					"Color length must be the same with layer length");

		int j = 0;
		for (NativeSpriteBackend sprite : mSpriteList) {
			sprite.setColor(color[j]);
			j++;
		}
	}

	public void setColor(Color color, int[] layer) {
		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.setColor(color);
	}

	public void setColor(float r, float g, float b, float a, int[] layer) {
		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.setColor(r, g, b, a);
	}

	public NSpriter setColor(Color color, int layer) {
		mSpriteList.get(layer).setColor(color);
		return this;
	}

	public NSpriter setColor(float r, float g, float b, float a, int layer) {
		mSpriteList.get(layer).setColor(r, g, b, layer);
		return this;
	}

	/******************************************************** Configuration ********************************************************/

	@Override
	public void setBounds(float x, float y, float width, float height) {
		final float deltaX = x - this.x;
		final float deltaY = y - this.y;

		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;

		mOriginSprite.setSize(width, height);
		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.translate(deltaX, deltaY);
		refresh();
	}

	@Override
	public void setSize(float width, float height) {
		this.w = width;
		this.h = height;

		mOriginSprite.setSize(width, height);
		refresh();
	}

	@Override
	public void setPosition(float x, float y) {
		final float deltaX = x - this.x;
		final float deltaY = y - this.y;
		this.x = x;
		this.y = y;

		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.translate(deltaX, deltaY);
	}

	public void setX(float x) {
		final float deltaX = x - this.x;
		this.x = x;

		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.translateX(deltaX);
	}

	@Override
	public void setY(float y) {
		final float deltaY = y - this.y;
		this.y = y;

		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.translateY(deltaY);
	}

	@Override
	public void translate(float xAmount, float yAmount) {
		this.x += xAmount;
		this.y += yAmount;

		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.translate(xAmount, yAmount);
	}

	@Override
	public void translateX(float xAmount) {
		this.x += xAmount;

		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.translateX(xAmount);
	}

	@Override
	public void translateY(float yAmount) {
		this.y += yAmount;

		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.translateY(yAmount);
	}

	// ------------------------------------------------------

	@Override
	public void setOrigin(float originX, float originY) {
		mOriginSprite.setOrigin(originX, originY);
		float newOriginX;
		float newOriginY;
		for (NativeSpriteBackend sprite : mSpriteList) {
			newOriginX = sprite.getX() - x;
			newOriginY = sprite.getY() - y;
			sprite.setOrigin(originX - newOriginX, originY - newOriginY);
		}
	}

	public void setOrigin(int layer, float originX, float originY) {
		mSpriteList.get(layer).setOrigin(originX, originY);
	}

	// ------------------------------------------------------

	@Override
	public void setRotation(float degree) {
		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.setRotation(degree);
	}

	public NSpriter setRotation(int layer, float degree) {
		mSpriteList.get(layer).setRotation(degree);
		return this;
	}

	// ------------------------------------------------------

	@Override
	public void rotate(float degree) {
		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.rotate(degree);
	}

	public void rotate(int layer, float degree) {
		mSpriteList.get(layer).rotate(degree);
	}

	// ------------------------------------------------------

	@Override
	public void setScale(float scaleXY) {
		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.setScale(scaleXY);
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.setScale(scaleX, scaleY);
	}

	@Override
	public void scale(float amount) {
		for (NativeSpriteBackend sprite : mSpriteList)
			sprite.scale(amount);
	}

	public void setScale(int layer, float scaleXY) {
		mSpriteList.get(layer).setScale(scaleXY);
	}

	public void setScale(int layer, float scaleX, float scaleY) {
		mSpriteList.get(layer).setScale(scaleX, scaleY);
	}

	public void scale(int layer, float amount) {
		mSpriteList.get(layer).scale(amount);
	}

	/******************************************************** Getter ********************************************************/

	@Override
	public float[] getVertices() {
		return mOriginSprite.getVertices();
	}

	@Override
	public float getX() {
		return x;
	}

	public float getX(int layer) {
		return mSpriteList.get(layer).getX();
	}

	@Override
	public float getCenterX() {
		return mOriginSprite.getCenterX();
	}

	public float getCenterX(int layer) {
		return mSpriteList.get(layer).getCenterX();
	}

	@Override
	public float getY() {
		return y;
	}

	public float getY(int layer) {
		return mSpriteList.get(layer).getY();
	}

	@Override
	public float getCenterY() {
		return mOriginSprite.getCenterY();
	}

	public float getCenterY(int layer) {
		return mSpriteList.get(layer).getCenterY();
	}

	@Override
	public float getWidth() {
		return w;
	}

	public float getWidth(int layer) {
		return mSpriteList.get(layer).getWidth();
	}

	@Override
	public float getHeight() {
		return h;
	}

	public float getHeight(int layer) {
		return mSpriteList.get(layer).getHeight();
	}

	@Override
	public float getOriginX() {
		return mOriginSprite.getOriginX();
	}

	public float getOriginX(int layer) {
		return mSpriteList.get(layer).getOriginX();
	}

	@Override
	public float getOriginY() {
		return mOriginSprite.getOriginY();
	}

	public float getOriginY(int layer) {
		return mSpriteList.get(layer).getOriginY();
	}

	@Override
	public float getRotation() {
		return mOriginSprite.getRotation();
	}

	public float getRotation(int layer) {
		return mSpriteList.get(layer).getRotation();
	}

	@Override
	public float getScaleX() {
		return mOriginSprite.getScaleX();
	}

	public float getScaleX(int layer) {
		return mSpriteList.get(layer).getScaleX();
	}

	@Override
	public float getScaleY() {
		return mOriginSprite.getScaleY();
	}

	public float getScaleY(int layer) {
		return mSpriteList.get(layer).getScaleY();
	}

	public int getSize() {
		return mSpriteList.size;
	}

	/********************************************************
	 * 
	 ********************************************************/

	@Deprecated
	public Rectangle getBoundingRectangle() {
		return null;
	}

	@Deprecated
	public float[] getBoundingFloatRect(float offset) {
		return null;
	}

	@Deprecated
	public Circle getBoundingCircle() {
		return null;
	}

	/******************************************************** Drawing method ********************************************************/

	public NSpriter setDrawableLayer(NativeSpriteBackend... list) {
		mDrawable.clear();
		for (NativeSpriteBackend s : list) {
			if (mSpriteList.contains(s, true) && !mDrawable.contains(s, true))
				mDrawable.add(s);
		}
		return this;
	}

	public NSpriter addDrawableLayer(NativeSpriteBackend s) {
		if (mSpriteList.contains(s, true) && !mDrawable.contains(s, true))
			mDrawable.add(s);
		return this;
	}

	public NSpriter removeDrawableLayer(NativeSpriteBackend... list) {
		for (NativeSpriteBackend s : list)
			mDrawable.removeValue(s, true);
		return this;
	}

	public Array<NativeSpriteBackend> getDrawbleLayer() {
		return mDrawable;
	}

	public void clearDrawable() {
		stop();
		mDrawable.clear();
	}

	public void draw(Batch batch) {
		for (NativeSpriteBackend s : mDrawable) {
			s.draw(batch);
		}
	}

	@Override
	public void draw(Batch batch, float alpha) {
		for (NativeSpriteBackend s : mDrawable)
			s.draw(batch, alpha);
	}

	/******************************************************** Animator method ********************************************************/

	public NSpriter setRunnableLayer(NativeSpriteBackend... list) {
		mRunnable.clear();
		for (NativeSpriteBackend s : list) {
			if (mSpriteList.contains(s, true) && !mRunnable.contains(s, true))
				mRunnable.add(s);
		}
		return this;
	}

	public NSpriter addRunnableLayer(NativeSpriteBackend s) {
		if (mSpriteList.contains(s, true) && !mRunnable.contains(s, true))
			mRunnable.add(s);
		return this;
	}

	public NSpriter removeRunnableLayer(NativeSpriteBackend... list) {
		for (NativeSpriteBackend s : list)
			mRunnable.removeValue(s, true);
		return this;
	}

	public void clearRunnable() {
		mRunnable.clear();
	}

	public Array<NativeSpriteBackend> getRunnableLayer() {
		return mRunnable;
	}

	public void setFrameDuration(float frameDuration) {
		for (NativeSpriteBackend s : mRunnable)
			((Animator) s).setFrameDuration(frameDuration);
	}

	public void start() {
		RUN = true;
		for (NativeSpriteBackend s : mRunnable)
			((Animator) s).start();
	}

	public void start(float frameDuration) {
		RUN = true;
		for (NativeSpriteBackend s : mRunnable)
			((Animator) s).start(frameDuration);
	}

	@Override
	public void start(float frameDuration, Animation.PlayMode playMode) {
		RUN = true;
		for (NativeSpriteBackend s : mRunnable)
			((Animator) s).start(frameDuration, playMode);
	}

	@Override
	public void pause() {
		RUN = false;
	}

	@Override
	public boolean isRunning() {
		return RUN;
	}

	public void stop() {
		RUN = false;
		resetFrame();
	}

	public void switchState() {
		RUN = !RUN;
	}

	public void resetFrame() {
		for (NativeSpriteBackend s : mRunnable)
			((Animator) s).resetFrame();
	}

	public void resetFrame(NativeSpriteBackend... layers) {
		for (NativeSpriteBackend s : layers)
			((Animator) s).resetFrame();
	}

	public void update(float delta) {
		for (int i = 0; i < mSpriteList.size; i++)
			mSpriteList.get(i).update(delta);

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

	public void postUpdater(Updater updater) {
		if (mUpdater.contains(updater, true))
			return;

		updater.start();
		this.mUpdater.add(updater);
	}

	public int sizeUpdater() {
		return mUpdater.size;
	}

	@Override
	public void removeUpdater(Updater updater) {
		mUpdater.removeValue(updater, true);
	}

	public void noUpdater() {
		this.mUpdater.clear();
	}

	/********************************************************
	 * 
	 ********************************************************/

	@Override
	public void reset() {
		stop();

		clear();
		mScaler.clear();
		mDrawable.clear();
		mRunnable.clear();
	}

	@Override
	public void dispose() {
		super.dispose();

		mScaler.clear();
		mDrawable.clear();
		mRunnable.clear();
		mOriginSprite = null;
	}

	private void refresh() {
		Values<NScaler> list = mScaler.values();
		for (NScaler s : list)
			s.apply();
	}

	class NScaler {
		float xRatio;
		float yRatio;
		float widthRatio;
		float heightRatio;

		NativeSpriteBackend sprite;

		void apply() {
			sprite.setBounds(x + xRatio * w, y + yRatio * h, widthRatio * w,
					heightRatio * h);
		}

		String info() {
			return "xRatio : " + xRatio + " " + "yRatio : " + yRatio + " "
					+ "widthRatio : " + widthRatio + " " + "heightRatio : "
					+ heightRatio + " ";
		}
	}

	/******************** deprecated ********************/
	@Deprecated
	public int getFrameNumber() {
		return 0;
	}

	@Deprecated
	public TextureRegion[] getFrames() {
		return null;
	}
}
