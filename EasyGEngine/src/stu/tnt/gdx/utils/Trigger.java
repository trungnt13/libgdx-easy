package stu.tnt.gdx.utils;

/**
 * Trigger.java
 * 
 * Created on: Jan 31, 2013
 * Author: Trung
 */
public class Trigger
{
    private float counter;
    public float delay;

    public Trigger(float delay)
    {
	this.delay = delay;
    }

    public Trigger()
    {

    }

    public void update (float delta)
    {
	counter += delta;
    }

    public boolean getValue ()
    {
	if (counter >= delay) {
	    counter = 0;
	    return true;
	}
	return false;
    }
}
