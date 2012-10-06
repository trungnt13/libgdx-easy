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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.NumberUtils;
import com.badlogic.gdx.utils.SpriteBackend;
import com.badlogic.gdx.utils.Updater;

public class NSprite implements SpriteBackend, Disposable {
	public final long address;

	private final NWorld world;
	NManager manager = null;
	NSpriteDef def = null;

	// ========================================

	private final float vertices[] = new float[E.sprite.VERTICES_SIZE];
	boolean isPooled = false;

	private Updater mUpdater = Updater.instance;
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

	NSprite(long address, NWorld world, NManager manager) {
		this.address = address;

		this.world = world;
		this.manager = manager;
	}

	public void setSpriteDef (NSpriteDef def) {
		world.spriteAddSpriteDef(this, def.name);
	}

	public void setSpriteDef (String spriteDefName) {
		world.spriteAddSpriteDef(this, def.name);
	}

	public void setManager (NManager manager) {
		manager.manage(this);
	}

	/************************************************************
	 * Texture manager method
	 ************************************************************/

	public Texture getTexture () {
		return texture;
	}

	public void setTexture (Texture texture) {
		this.texture = texture;
		final int width = texture.getWidth();
		final int height = texture.getHeight();
		setRegion(0, 0, width, height);
		setSize(width, height);
		setOrigin(width >> 1, height >> 1);
	}

	// ===========================================
	// region method

	/**
	 * @param width The width of the texture region. May be negative to flip the sprite when drawn.
	 * @param height The height of the texture region. May be negative to flip the sprite when
	 *            drawn.
	 */
	public void setRegion (int x, int y, int width, int height) {
		float invTexWidth = 1f / texture.getWidth();
		float invTexHeight = 1f / texture.getHeight();
		setRegion(x * invTexWidth, y * invTexHeight, (x + width) * invTexWidth, (y + height)
				* invTexHeight);
		regionWidth = Math.abs(width);
		regionHeight = Math.abs(height);
	}

	/**
	 * Sets the texture to that of the specified region and sets the coordinates relative to the
	 * specified region.
	 */
	public void setRegion (TextureRegion region, int x, int y, int width, int height) {
		texture = region.getTexture();
		setRegion(region.getRegionX() + x, region.getRegionY() + y, width, height);
	}

	public void setRegion (float u, float v, float u2, float v2) {
		this.u = u;
		this.v = v;
		this.u2 = u2;
		this.v2 = v2;
		regionWidth = Math.round(Math.abs(u2 - u) * texture.getWidth());
		regionHeight = Math.round(Math.abs(v2 - v) * texture.getHeight());

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
	public void setRegion (TextureRegion region) {
		texture = region.getTexture();
		setRegion(region.getU(), region.getV(), region.getU2(), region.getV2());
	}

	public void setU (float u) {
		this.u = u;
		regionWidth = Math.round(Math.abs(u2 - u) * texture.getWidth());

		vertices[U1] = u;
		vertices[U2] = u;
	}

	public void setV (float v) {
		this.v = v;
		regionHeight = Math.round(Math.abs(v2 - v) * texture.getHeight());

		vertices[V2] = v;
		vertices[V3] = v;
	}

	public void setU2 (float u2) {
		this.u2 = u2;
		regionWidth = Math.round(Math.abs(u2 - u) * texture.getWidth());

		vertices[U3] = u2;
		vertices[U4] = u2;
	}

	public void setV2 (float v2) {
		this.v2 = v2;
		regionHeight = Math.round(Math.abs(v2 - v) * texture.getHeight());

		vertices[V1] = v2;
		vertices[V4] = v2;
	}

	public void flip (boolean x, boolean y) {
		float[] vertices = this.vertices;

		if (x) {
			float temp = u;
			u = u2;
			u2 = temp;

			temp = vertices[U1];
			vertices[U1] = vertices[U3];
			vertices[U3] = temp;
			temp = vertices[U2];
			vertices[U2] = vertices[U4];
			vertices[U4] = temp;
		}
		if (y) {
			float temp = v;
			v = v2;
			v2 = temp;

			vertices[V1] = vertices[V3];
			vertices[V3] = temp;
			temp = vertices[V2];
			vertices[V2] = vertices[V4];
			vertices[V4] = temp;
		}

	}

	public void scroll (float xAmount, float yAmount) {
		final float[] vertices = this.vertices;
		if (xAmount != 0) {
			float u = (vertices[U1] + xAmount) % 1;
			float u2 = u + getWidth() / texture.getWidth();
			this.u = u;
			this.u2 = u2;
			vertices[U1] = u;
			vertices[U2] = u;
			vertices[U3] = u2;
			vertices[U4] = u2;
		}
		if (yAmount != 0) {
			float v = (vertices[V2] + yAmount) % 1;
			float v2 = v + getHeight() / texture.getHeight();
			this.v = v;
			this.v2 = v2;
			vertices[V1] = v2;
			vertices[V2] = v;
			vertices[V3] = v;
			vertices[V4] = v2;
		}
	}

	// ============================================
	// getter

	public boolean isFlipX () {
		return u > u2;
	}

	public boolean isFlipY () {
		return v > v2;
	}

	public float getU () {
		return u;
	}

	public float getV () {
		return v;
	}

	public float getU2 () {
		return u2;
	}

	public float getV2 () {
		return v2;
	}

	public int getRegionX () {
		return Math.round(u * texture.getWidth());
	}

	public int getRegionY () {
		return Math.round(v * texture.getHeight());
	}

	/** Returns the region's width. */
	public int getRegionWidth () {
		return regionWidth;
	}

	/** Returns the region's height. */
	public int getRegionHeight () {
		return regionHeight;
	}

	/************************************************************
	 * Java sprite method
	 ************************************************************/

	// ==============================================
	// setter

	@Override
	public void setBounds (float x, float y, float width, float height) {
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
	public void setSize (float width, float height) {
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
	public void setPosition (float x, float y) {
		setPosition(address, x, y);

		if (isDirty(address))
			return;

		float x2 = getWidth() + x;
		float y2 = getHeight() + y;
		final float vertices[] = this.vertices;

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
	public void setX (float x) {
		setX(address, x);

		if (isDirty(address))
			return;

		float x2 = getWidth() + x;
		final float vertices[] = this.vertices;

		vertices[X1] = x;
		vertices[X2] = x;
		vertices[X3] = x2;
		vertices[X4] = x2;
	}

	@Override
	public void setY (float y) {
		setY(address, y);

		if (isDirty(address))
			return;

		float y2 = getWidth() + y;
		final float vertices[] = this.vertices;

		vertices[Y1] = y;
		vertices[Y2] = y;
		vertices[Y3] = y2;
		vertices[Y4] = y2;
	}

	@Override
	public void translate (float xAmount, float yAmount) {
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
	public void translateX (float xAmount) {
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
	public void translateY (float yAmount) {
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
	public void setOrigin (float originX, float originY) {
		setOrigin(address, originX, originY);
	}

	@Override
	public void setRotation (float degree) {
		setRotation(address, degree);
	}

	@Override
	public void rotate (float degree) {
		rotate(address, degree);
	}

	@Override
	public void setScale (float scaleXY) {
		setScale(address, scaleXY);
	}

	@Override
	public void setScale (float scaleX, float scaleY) {
		setScale(address, scaleX, scaleY);
	}

	@Override
	public void scale (float amount) {
		scale(address, amount);
	}

	@Override
	public void setColor (float r, float g, float b, float a) {
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
	public void setColor (Color tint) {
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
	public float[] getVertices () {
		return vertices;
	}

	@Override
	public float getX () {
		return getX(address);
	}

	@Override
	public float getCenterX () {
		return getCenterX(address);
	}

	@Override
	public float getY () {
		return getY(address);
	}

	@Override
	public float getCenterY () {
		return getCenterY(address);
	}

	@Override
	public float getWidth () {
		return getWidth(address);
	}

	@Override
	public float getHeight () {
		return getHeight(address);
	}

	@Override
	public float getOriginX () {
		return getOriginX(address);
	}

	@Override
	public float getOriginY () {
		return getOriginY(address);
	}

	@Override
	public float getRotation () {
		return getRotation(address);
	}

	@Override
	public float getScaleX () {
		return getScaleX(address);
	}

	@Override
	public float getScaleY () {
		return getScaleY(address);
	}

	@Deprecated
	public Rectangle getBoundingRectangle () {
		return null;
	}

	@Deprecated
	public float[] getBoundingFloatRect (float offset) {
		return null;
	}

	@Deprecated
	public Circle getBoundingCircle () {
		return null;
	}

	public boolean isPooled () {
		return isPooled;
	}

	/**
	 * Returns the color of this sprite. Changing the returned color will have no affect,
	 * {@link #setColor(Color)} or {@link #setColor(float, float, float, float)} must be used.
	 */
	public Color getColor () {
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
	public void postUpdater (Updater updater) {
		this.mUpdater = updater;
	}

	@Override
	public void noUpdater () {
		this.mUpdater = Updater.instance;
	}

	@Override
	public void update (float delta) {
		mUpdater.update(this, delta);
	}

	@Override
	public void draw (SpriteBatch batch) {
		getVertices(address, vertices);
		batch.draw(texture, vertices, 0, E.sprite.VERTICES_SIZE);
	}

	@Override
	public void draw (SpriteBatch batch, float alpha) {
		Color color = getColor();
		float oldAlpha = color.a;
		color.a *= alpha;
		setColor(color);
		draw(batch);
		color.a = oldAlpha;
		setColor(color);
	}

	@Override
	public void reset () {
		if (isPooled)
			return;

		vertices[X1] = 0;
		vertices[X2] = 0;
		vertices[X3] = 0;
		vertices[X4] = 0;

		vertices[Y1] = 0;
		vertices[Y2] = 0;
		vertices[Y3] = 0;
		vertices[Y4] = 0;

		vertices[C1] = 0;
		vertices[C2] = 0;
		vertices[C3] = 0;
		vertices[C4] = 0;

		world.poolSprite(this);
		reset(address);

		isPooled = true;
	}

	public void dispose () {
		world.deleteSprite(this);
		dispose(address);
		isPooled = true;
	}

	void resetWithoutWorldCallback () {
		if (isPooled)
			return;

		vertices[X1] = 0;
		vertices[X2] = 0;
		vertices[X3] = 0;
		vertices[X4] = 0;

		vertices[Y1] = 0;
		vertices[Y2] = 0;
		vertices[Y3] = 0;
		vertices[Y4] = 0;

		vertices[C1] = 0;
		vertices[C2] = 0;
		vertices[C3] = 0;
		vertices[C4] = 0;

		reset(address);

		isPooled = true;
	}

	void disposeWithoutWorldCallback () {
		dispose(address);
		isPooled = true;
	}

	/******************************************************
	 * Native method
	 ******************************************************/

	// ==============================================
	// setter

	private final native void setBounds (long address, float x, float y, float width, float height);

	private final native void setSize (long address, float width, float height);

	private final native void setPosition (long address, float x, float y);

	private final native void setX (long address, float x);

	private final native void setY (long address, float y);

	private final native void translate (long address, float xAmount, float yAmount);

	private final native void translateX (long address, float xAmount);

	private final native void translateY (long address, float yAmount);

	private final native void setOrigin (long address, float originX, float originY);

	private final native void setRotation (long address, float degree);

	private final native void rotate (long address, float degree);

	private final native void setScale (long address, float scaleXY);

	private final native void setScale (long address, float scaleX, float scaleY);

	private final native void scale (long address, float amount);

	// =======================================================
	// getter

	private final native float[] getVertices (long address, float[] vertices);

	private final native float getX (long address);

	private final native float getCenterX (long address);

	private final native float getY (long address);

	private final native float getCenterY (long address);

	private final native float getWidth (long address);

	private final native float getHeight (long address);

	private final native float getOriginX (long address);

	private final native float getOriginY (long address);

	private final native float getRotation (long address);

	private final native float getScaleX (long address);

	private final native float getScaleY (long address);

	private final native Rectangle getBoundingRectangle (long address);

	private final native float[] getBoundingFloatRect (long address, float offset);

	private final native Circle getBoundingCircle (long address);

	private final native boolean isDirty (long address);

	// ===============================================
	// processor

	private final native void reset (long address);

	private final native void dispose (long address);

}
