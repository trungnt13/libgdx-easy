package org.ege.widget;

public class ScalingSet {
	public float layoutx = 0;
	public float layouty = 0;
	
	public float layoutwidth = -1;
	public float layoutheight = -1;
	
	public float whratio = -1;
	public float hwratio = -1;
	
	public ScalingSet set(float  x,float y,float width,float height,float whratio,float hwratio){
		layoutx = x;
		layouty = y;
		layoutwidth =width;
		layoutheight = height;
		this.whratio = whratio;
		this.hwratio = hwratio;
		return this;
	}
}
