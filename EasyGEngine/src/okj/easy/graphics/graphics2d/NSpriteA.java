package okj.easy.graphics.graphics2d;

import static com.badlogic.gdx.graphics.g2d.Animation.LOOP;
import static com.badlogic.gdx.graphics.g2d.Animation.LOOP_PINGPONG;
import static com.badlogic.gdx.graphics.g2d.Animation.LOOP_RANDOM;
import static com.badlogic.gdx.graphics.g2d.Animation.LOOP_REVERSED;
import static com.badlogic.gdx.graphics.g2d.Animation.NORMAL;
import static com.badlogic.gdx.graphics.g2d.Animation.REVERSED;
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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Animator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.NumberUtils;
import com.badlogic.gdx.utils.Updateable;

/**
 * 
 * NSpriteA.java
 * 
 * Created on: Oct 12, 2012
 * Author: Trung
 */
public class NSpriteA extends NativeSpriteBackend implements Animator
{

	private final float vertices[] = new float[E.sprite.VERTICES_SIZE];

	private Array<Updateable> mUpdater = new Array<Updateable>(0);
	private Color color;

	// ========================================
	// texture region params
	TextureRegion[] keyFrames;

	float mFrameDuration;
	float mStateTime;
	float mAnimationDuration;

	private int frameNumber;

	private int mPlayMode = NORMAL;

	private Texture mCurrentTexture;

	private boolean isRunning = false;

	/************************************************************
	 * Constructor
	 ************************************************************/

	protected NSpriteA(long address, NWorld world) {
		super(address, world);

		setColor(1, 1, 1, 1);
		setSize(50, 50);
		setOrigin(25, 25);
	}

	/***********************************************************
	 * Animator controller
	 ***********************************************************/

	public void setFrameDuration (float frameDuration)
	{
		this.mFrameDuration = frameDuration;
	}

	public void setPlayMode (int playMode)
	{
		mPlayMode = playMode;
	}

	public void setLooping (boolean looping)
	{
		if (looping && (mPlayMode == NORMAL || mPlayMode == REVERSED)) {
			if (mPlayMode == NORMAL)
				mPlayMode = LOOP;
			else
				mPlayMode = LOOP_REVERSED;
		} else if (!looping && !(mPlayMode == NORMAL || mPlayMode == REVERSED)) {
			if (mPlayMode == LOOP_REVERSED)
				mPlayMode = REVERSED;
			else
				mPlayMode = LOOP;
		}
	}

	public void setKeyFrames (TextureRegion[] keyFrame)
	{
		this.keyFrames = keyFrame;
		setRegion(keyFrames[0]);
		setTexture(keyFrame);
	}

	public void setKeyFrames (Array keyFrame)
	{
		keyFrames = new TextureRegion[keyFrame.size];
		for (int i = 0; i < keyFrames.length; i++)
			keyFrames[i] = (TextureRegion) keyFrame.get(i);
		setRegion(keyFrames[0]);
		setTexture(keyFrames);
	}

	public void start ()
	{
		isRunning = true;
	}

	public void start (float frameDuration)
	{
		isRunning = true;
		mFrameDuration = frameDuration;
	}

	public void start (float frameDuration, int playMode)
	{
		isRunning = true;
		mFrameDuration = frameDuration;
		mPlayMode = playMode;
	}

	public void stop ()
	{
		isRunning = false;
		mStateTime = 0;
		setRegion(keyFrames[0]);
	}

	public void pause ()
	{
		isRunning = false;
	}

	public void switchState ()
	{
		isRunning = !isRunning;
	}

	public void resetFrame ()
	{
		mStateTime = 0;
		setRegion(keyFrames[0]);
	}

	/**
	 * Get current frame number ( unsafe method)
	 * 
	 * @return
	 */
	public int getFrameNumber ()
	{
		return frameNumber;
	}

	public TextureRegion[] getKeyFrames ()
	{
		return this.keyFrames;
	}

	@Override
	public boolean isRunning ()
	{
		return isRunning;
	}

	/************************************************************
	 * Texture manager method
	 ************************************************************/

	public void setRegions (Array region)
	{
		keyFrames = new TextureRegion[region.size];
		for (int i = 0; i < keyFrames.length; i++)
			keyFrames[i] = (TextureRegion) region.get(i);

		setRegion(keyFrames[0]);
		setTexture(keyFrames);
	}

	public void setRegions (TextureRegion[] region)
	{
		keyFrames = region;
		setRegion(region[0]);
		setTexture(keyFrames);
	}

	private void setRegion (TextureRegion region)
	{
		final float u = region.getU();
		final float v = region.getV();
		final float u2 = region.getU2();
		final float v2 = region.getV2();

		final float[] vertices = this.vertices;

		vertices[U1] = u;
		vertices[V1] = v2;

		vertices[U2] = u;
		vertices[V2] = v;

		vertices[U3] = u2;
		vertices[V3] = v;

		vertices[U4] = u2;
		vertices[V4] = v2;
	}

	private void setTexture (TextureRegion[] texture)
	{
		mCurrentTexture = texture[0].getTexture();
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
	public void postUpdater (Updateable updater)
	{
		if (mUpdater.contains(updater, true))
			return;

		updater.start();
		mUpdater.add(updater);
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

		// animation process
		if (!isRunning || mFrameDuration == 0) {
			// ============= update updatable =============
			for (int i = 0, n = mUpdater.size; i < n; i++) {
				final Updateable tmp = mUpdater.get(i);

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

		mStateTime += delta;

		frameNumber = (int) (mStateTime / mFrameDuration);

		switch (mPlayMode) {
			case NORMAL:
				frameNumber = Math.min(keyFrames.length - 1, frameNumber);
				break;
			case LOOP:
				frameNumber = frameNumber % keyFrames.length;
				break;
			case LOOP_PINGPONG:
				frameNumber = frameNumber % (keyFrames.length * 2);
				if (frameNumber >= keyFrames.length)
					frameNumber = keyFrames.length - 1 - (frameNumber - keyFrames.length);
				break;
			case LOOP_RANDOM:
				frameNumber = MathUtils.random(keyFrames.length - 1);
				break;
			case REVERSED:
				frameNumber = Math.max(keyFrames.length - frameNumber - 1, 0);
				break;
			case LOOP_REVERSED:
				frameNumber = frameNumber % keyFrames.length;
				frameNumber = keyFrames.length - frameNumber - 1;
				break;

			default:
				// play normal otherwise
				frameNumber = Math.min(keyFrames.length - 1, frameNumber);
				break;
		}
		setRegion(keyFrames[frameNumber]);

		// updater
		// ============= update updatable =============
		for (int i = 0, n = mUpdater.size; i < n; i++) {
			final Updateable tmp = mUpdater.get(i);

			if (!tmp.isStoped())
				tmp.update(this, delta);
			else {
				mUpdater.removeValue(tmp, true);
				--i;
				--n;
			}
		}
	}

	@Override
	public void draw (SpriteBatch batch)
	{
		batch.draw(mCurrentTexture, vertices, 0, E.sprite.VERTICES_SIZE);
	}

	@Override
	public void draw (SpriteBatch batch, float alpha)
	{
		final Color color = getColor();
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

		stop();
		noUpdater();
	}

}
