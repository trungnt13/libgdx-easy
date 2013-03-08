package vn.easy.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class LogoDrawer
{
    private final static int[][] qpLogo = {
	    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0, 0, 0 },
	    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0, 0, 0 },
	    { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0, 0, 0 },
	    { 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1,
		    1, 1, 0, 0, 1, 1, 1 },
	    { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1,
		    1, 1, 0, 0, 1, 1, 1 },
	    { 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1,
		    1, 1, 0, 0, 1, 1, 1 },
	    { 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1,
		    1, 1, 0, 0, 1, 1, 1 },
	    { 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1,
		    1, 1, 0, 0, 1, 1, 1 },
	    { 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1,
		    1, 1, 1, 1, 1, 1, 1 },
	    { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1,
		    1, 1, 1, 1, 1, 1, 1 },
	    { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
		    1, 1, 1, 0, 1, 1, 1 },
	    { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 1, 1, 1 },
	    { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 1, 1, 1 },
	    { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 1, 1, 1 },
	    { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 0, 1, 1, 1, 0 },
	    { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 1, 1, 1, 0, 0 },
	    { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0, 0, 0 },
	    { 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0, 0, 0 },
	    { 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0, 0, 0 },
	    { 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 0, 0, 0, 0, 0 }
    };
    private final String qpText = "http://qplay.vn";

    private static float SLEEP_INTERVAL = 0.1f;
    private float mCountTime = 0;

    public static final byte FAST_ANIMATING = 1;
    public static final byte NORMAL_ANIMATING = 2;
    public static final byte SLOW_ANIMATING = 3;

    // ===============================================

    private final float[] color = { 0, 0, 0 };

    private int i = 1, t = 0, left, top;
    private final int rd, width, height;

    private int x = 0, y = 0, xText = 0, yText = 0;

    private final int mPixelSize;
    private final int mPixelDistance;

    private final float sizeText;

    private boolean mStopAnimation = false;
    private boolean isRunning = false;

    // switch between two animation
    private boolean changed = false;

    // ===============================================

    private final ShapeRenderer renderer;
    private Runnable mCompleteListener = null;

    public LogoDrawer()
    {
	renderer = new ShapeRenderer();

	/* =============================== */

	i = 1;
	rd = MathUtils.random(0, 2);

	/* =============================== */

	width = Gdx.graphics.getWidth();
	height = Gdx.graphics.getHeight();

	renderer.setProjectionMatrix(new Matrix4().setToOrtho2D(0, height, width, -height));

	/* =============================== */

	int sizeLarge = (width < height) ? width : height;
	if (sizeLarge >= 480) {
	    mPixelDistance = 10;
	    mPixelSize = 5;
	    sizeText = 25f * qpText.length();
	} else if (sizeLarge >= 320) {
	    mPixelDistance = 6;
	    mPixelSize = 3;
	    sizeText = 18f * qpText.length();
	} else {
	    mPixelDistance = 4;
	    mPixelSize = 2;
	    sizeText = 12f * qpText.length();
	}

	x = width >> 1;
	y = height >> 1;
	x = x - 36 * (mPixelDistance) / 2;
	y = (int) ((float) height * 0.382) - 20 * mPixelDistance / 2;
	xText = (int) ((width >> 1) - sizeText / 2);
	yText = (int) ((float) height * 0.764f - sizeText / 2);
	left = x;
	top = y;
    }

    /******************************************************
	 * 
	 ******************************************************/

    public void draw (float delta)
    {
	drawLogoQplay();
	update(delta);
    }

    private void drawLogoQplay ()
    {
	if (i <= 0)
	    return;
	renderer.begin(ShapeType.Filled);
	renderer.setColor(color[0], color[1], color[2], 1);
	if (rd == 0) {
	    for (int q = 1; q <= 35 - (11 - i) * 3; q++) {
		for (int p = 1; p <= 20; p++) {
		    if (qpLogo[p - 1][q - 1] == 1) {
			left = q * mPixelDistance + x;
			top = p * mPixelDistance + y;
			renderer.rect(left, top, mPixelSize, mPixelSize);
		    }
		}
	    }
	}
	else if (rd == 1) {
	    for (int q = 1; q <= 35; q++) {
		for (int p = 1; p <= 20 - (11 - i) * 2; p++) {
		    if (qpLogo[p - 1][q - 1] == 1) {
			left = q * mPixelDistance + x;
			top = p * mPixelDistance + y;
			renderer.rect(left, top, mPixelSize, mPixelSize);
		    }
		}
	    }
	} else {
	    for (int q = 1; q <= 35 - (11 - i) * 3; q++) {
		for (int p = 1; p <= 20 - (11 - i) * 2; p++) {
		    if (qpLogo[p - 1][q - 1] == 1) {
			left = q * mPixelDistance + x;
			top = p * mPixelDistance + y;
			renderer.rect(left, top, mPixelSize, mPixelSize);
		    }
		}
	    }
	}
	renderer.end();
    }

    private void update (float delta)
    {
	if (mStopAnimation)
	    return;

	isRunning = true;

	mCountTime += delta;
	if (mCountTime >= SLEEP_INTERVAL) {
	    mCountTime = 0;
	    if (i > 0) {
		if ((i <= 11) && !changed) {
		    color[0] = (float) (i * 20 + 30) / 255;
		    color[1] = (float) (i * 15) / 255;
		    color[2] = 0;

		    if (i < 11)
			i++;
		    if (i == 11) {
			if (t < 10)
			    t++;
			else
			    changed = true;
		    }
		} else if (changed) {
		    i--;
		    color[0] = (float) (i * 20 + 30) / 255;
		    color[1] = (float) (i * 15) / 255;
		    color[2] = 0;
		}
	    }
	}

	if (changed && i == 0) {
	    stopAnimation();
	}
    }

    /******************************************************
	 * 
	 ******************************************************/

    public void postCompleteAction (Runnable runnable)
    {
	// if (isRunning)
	// throw new GdxRuntimeException("You can not config the LogoDrawer while it is drawing");
	this.mCompleteListener = runnable;
    }

    public void stopAnimation ()
    {
	mStopAnimation = true;
	isRunning = false;
	renderer.dispose();
	if (mCompleteListener != null)
	    mCompleteListener.run();
    }

    public void setDuration (byte logoDuration)
    {
	if (isRunning)
	    throw new GdxRuntimeException("You can not config the LogoDrawer while it is drawing");

	switch (logoDuration) {
	    case FAST_ANIMATING:
		SLEEP_INTERVAL = 0.066f;
		break;
	    case SLOW_ANIMATING:
		SLEEP_INTERVAL = 0.13f;
		break;
	    default:
		SLEEP_INTERVAL = 0.1f;
	}
    }

    public boolean isRunning ()
    {
	return isRunning;
    }
}
