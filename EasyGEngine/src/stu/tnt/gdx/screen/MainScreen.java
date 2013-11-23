package stu.tnt.gdx.screen;

import stu.tnt.gdx.core.Screen;

public abstract class MainScreen extends Screen
{
    private boolean PAUSE = false;

    @Override
    public void show ()
    {
	if (PAUSE) {
	    resume();
	    return;
	}

	onCreate();
    }

    @Override
    public void resize (int width, int height)
    {
	super.resize(width, height);
	onResize(width, height);
    }

    @Override
    public void destroy (int destroyMode)
    {
	super.destroy(destroyMode);
	onDestroy();
    }

    @Override
    public void resume ()
    {
	super.resume();
	this.PAUSE = false;
	onResume();
    }

    @Override
    public void pause ()
    {
	super.pause();
	this.PAUSE = true;
	onPause();
    }

    @Override
    public void update (float delta)
    {
	super.update(delta);
	if (!PAUSE)
	    onUpdate(delta);
    }

    /**************************************************
     * Main method
     **************************************************/

    /**
     * This method will create all information before your screen start
     */
    public abstract void onCreate ();

    /**
     * This method call when the size of screen changed
     * 
     * @param width
     * @param height
     */
    public abstract void onResize (int width, int height);

    /**
     * Call when the game is Resume from pause
     */
    public abstract void onResume ();

    /**
     * Call when the game is pause or when setScreen in HIDE mode
     */
    public abstract void onPause ();

    /**
     * Only be called when you call setScreen(RELEASE mode)
     */
    public abstract void onDestroy ();

    public abstract void onUpdate (float delta);

    public final boolean isPause ()
    {
	return PAUSE;
    }
}
