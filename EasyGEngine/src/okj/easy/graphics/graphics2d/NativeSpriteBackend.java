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

	/*****************************************************
	 * Constructor
	 *****************************************************/

	NativeSpriteBackend(long address, NWorld world) {
		this.address = address;
		this.world = world;
	}

	// =========================================
	// native manager
	public abstract void setSpriteDef (NSpriteDef def);

	public abstract void setSpriteDef (String spriteDefName);

	public abstract void setManager (NManager manager);

	public abstract boolean isPooled ();

	public abstract NManager getManager ();

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
}
