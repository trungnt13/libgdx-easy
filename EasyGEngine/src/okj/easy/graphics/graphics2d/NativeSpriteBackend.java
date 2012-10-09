package okj.easy.graphics.graphics2d;

import org.ege.utils.SpriteBackend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Updater;

/**
 * 
 * NativeSpriteBackend.java
 * 
 * Created on: Oct 7, 2012
 * Author: Trung
 */
public abstract class NativeSpriteBackend implements SpriteBackend, Disposable {
	public final long address;

	final NWorld world;
	NManager manager = null;
	NSpriteDef def = null;

	// =========================================

	boolean isPooled = false;
	boolean isDisposed = false;
	boolean dirty = false;

	/*****************************************************
	 * Constructor
	 *****************************************************/

	NativeSpriteBackend(long address, NWorld world) {
		this.address = address;
		this.world = world;
	}

	public void noSpriteDef () {
		if (this.def != null)
			this.def.mSpriteCount--;

		noSpriteDef(address);
		this.def = null;
	}

	public void setSpriteDef (NSpriteDef def) {
		if (this.def != null)
			this.def.mSpriteCount--;

		def.mSpriteCount++;
		setSpriteDef(address, def.address);

		this.def = def;
	}

	public void setSpriteDef (String spriteDefName) {
		if (this.def != null)
			this.def.mSpriteCount--;

		NSpriteDef def = world.getSpriteDef(spriteDefName);
		def.mSpriteCount++;
		setSpriteDef(address, def.address);

		this.def = def;
	}

	public NSpriteDef getSpriteDef () {
		return def;
	}

	public void setManager (NManager manager) {
		manager.manage(this);
	}

	public NManager getManager () {
		return manager;
	}

	/** this is not safe method just for testing */
	public float[] getTransformedBounding (int index) {
		return getTransformedBounding(address, index);
	}

	// =========================================
	// native manager

	public abstract boolean isPooled ();

	// ==========================================
	// texture manager

	public abstract Texture getTexture ();

	public abstract void setTexture (Texture texture);

	/**
	 * @param width The width of the texture region. May be negative to flip the sprite when drawn.
	 * @param height The height of the texture region. May be negative to flip the sprite when
	 *            drawn.
	 */
	public abstract void setRegion (int x, int y, int width, int height);

	/**
	 * Sets the texture to that of the specified region and sets the coordinates relative to the
	 * specified region.
	 */
	public abstract void setRegion (TextureRegion region, int x, int y, int width, int height);

	public abstract void setRegion (float u, float v, float u2, float v2);

	/** Sets the texture and coordinates to the specified region. */
	public abstract void setRegion (TextureRegion region);

	/*****************************************************
	 * SpriteBackend
	 *****************************************************/

	// =================================================
	// setter

	public abstract void setBounds (float x, float y, float width, float height);

	public abstract void setSize (float width, float height);

	public abstract void setPosition (float x, float y);

	public abstract void setX (float x);

	public abstract void setY (float y);

	public abstract void translate (float xAmount, float yAmount);

	public abstract void translateX (float xAmount);

	public abstract void translateY (float yAmount);

	public abstract void setOrigin (float originX, float originY);

	public abstract void setRotation (float degree);

	public abstract void rotate (float degree);

	public abstract void setScale (float scaleXY);

	public abstract void setScale (float scaleX, float scaleY);

	public abstract void scale (float amount);

	public abstract void setColor (float r, float g, float b, float a);

	public abstract void setColor (Color color);

	// =================================================
	// getter

	public abstract float[] getVertices ();

	public abstract float getX ();

	public abstract float getCenterX ();

	public abstract float getY ();

	public abstract float getCenterY ();

	public abstract float getWidth ();

	public abstract float getHeight ();

	public abstract float getOriginX ();

	public abstract float getOriginY ();

	public abstract float getRotation ();

	public abstract float getScaleX ();

	public abstract float getScaleY ();

	// =================================================
	// processor

	public abstract void postUpdater (Updater updater);

	public abstract void noUpdater ();

	public abstract void update (float delta);

	public abstract void draw (SpriteBatch batch);

	public abstract void draw (SpriteBatch batch, float alpha);

	// ==============================================
	// dispose

	public abstract void dispose ();

	public abstract void reset ();

	abstract void resetWithoutWorldCallback ();

	abstract void disposeWithoutWorldCallback ();

	void unmanage () {
		unmanage(address);
	}

	/******************************************************
	 * Native method
	 ******************************************************/

	// ==============================================
	// setter

	protected final native void setOriginSize (long address, float width, float height);

	protected final native void setBounds (long address, float x, float y, float width, float height);

	protected final native void setSize (long address, float width, float height);

	protected final native void setPosition (long address, float x, float y);

	protected final native void setX (long address, float x);

	protected final native void setY (long address, float y);

	protected final native void translate (long address, float xAmount, float yAmount);

	protected final native void translateX (long address, float xAmount);

	protected final native void translateY (long address, float yAmount);

	protected final native void setOrigin (long address, float originX, float originY);

	protected final native void setRotation (long address, float degree);

	protected final native void rotate (long address, float degree);

	protected final native void setScale (long address, float scaleXY);

	protected final native void setScale (long address, float scaleX, float scaleY);

	protected final native void scale (long address, float amount);

	// =======================================================
	// getter

	protected final native void getVertices (long address, float[] vertices);

	protected final native float getX (long address);

	protected final native float getCenterX (long address);

	protected final native float getY (long address);

	protected final native float getCenterY (long address);

	protected final native float getWidth (long address);

	protected final native float getHeight (long address);

	protected final native float getOriginX (long address);

	protected final native float getOriginY (long address);

	protected final native float getRotation (long address);

	protected final native float getScaleX (long address);

	protected final native float getScaleY (long address);

	private final native float[] getTransformedBounding (long address, int index);

	// ===============================================
	// processor

	protected final native void reset (long address);

	protected final native void dispose (long address);

	private final native void unmanage (long address);

	private final native void setSpriteDef (long address, long spritedef);

	private final native void noSpriteDef (long address);
}