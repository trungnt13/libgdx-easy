package org.ege.widget;


import org.ege.utils.E;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ProcessBar extends Actor{
	ProcessBarStyle mStyle;
	TextureRegion tmp;
	
	OnProcessListener mProcessListener;
	
	// the first region width and height of process bar
	private float mFirstWidth;
	private float mFirstHeight;
	
	// the final width height when draw of process bar
	private float mDstWidth;
	private float mDstHeight;
	
	private float mCurrentTime;
	private float mCurrentPercent;
	
	private float mStateTime;
	private int mKeyFrame;
	
	
	boolean isAnimation = false;
	
	public ProcessBar(ProcessBarStyle style){
		super();
		readStyle(style);
	}
	
	private void readStyle(ProcessBarStyle style){
		mStyle = style;
		
		mCurrentPercent = (style.mode == ProcessMode.COUNT_UP) ? 0 : 1;
		mCurrentTime = (style.mode == ProcessMode.COUNT_UP) ? 0 : mStyle.time;
		setRotation((style.orientation == E.orientation.LANDSCAPE) ? 0 : 90);
		
		mFirstWidth = mStyle.process[0].getRegionWidth();
		mFirstHeight = mStyle.process[0].getRegionHeight();
	}

	/************************************************************
	 * 
	 ***********************************************************/
	
	public void setProcessListener(OnProcessListener listener){
		mProcessListener = listener;
	}
	
	public void setMaxProcessTime(float maxProcessTime){
		mStyle.time = maxProcessTime;
	}
	
	public void increaseProcess(float percent){
		if(percent < 0 || percent > 1 || mCurrentPercent < 0 || mCurrentPercent > 1)
			return;
		
		mCurrentPercent += mStyle.mode.value * percent;
		
		if(mStyle.time > -1)
			mCurrentTime += mStyle.mode.value*percent*mStyle.time;
		
		if(mProcessListener != null)
			mProcessListener.ProcessValue(this, mCurrentPercent);
		
		mDstHeight = getHeight();
		mDstWidth = mCurrentPercent * getWidth();
	}
	
	public void setProcess(float currentPercent){
		if(mCurrentPercent < 0 || mCurrentPercent > 1)
			return;
		mCurrentPercent = currentPercent;
		if(mStyle.time > -1)
			mCurrentTime = currentPercent*mStyle.time;
		
		mDstHeight = getHeight();
		mDstWidth = mCurrentPercent * getWidth();
	}
	
	public void setRegion(TextureRegion background,TextureRegion...process){
		mStyle.background = background;
		mStyle.process = process;
	}
	
	public float getProcess(){
		return mCurrentPercent;
	}
	
	public float getTime (){
		return mCurrentTime;
	}
	
	/************************************************************
	 * 
	 ***********************************************************/
	
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		final Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a);
		
		final float scaleX = getScaleX();
		final float scaleY = getScaleY();
		final float rotation  = getRotation();
		
		if(scaleX == 1 && scaleY == 1 && rotation == 0){
			batch.draw(mStyle.background,getX(),getY(),getWidth(),getHeight());
			tmp = mStyle.process[mKeyFrame];
			tmp.setRegion((int)tmp.getRegionX(), (int)tmp.getRegionY(), 
						  (int)(mCurrentPercent*mFirstWidth), (int)mFirstHeight);
			batch.draw(tmp,getX(),getY(),mDstWidth,mDstHeight);
		}else{
			batch.draw(mStyle.background,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),scaleX,scaleY,90);
			tmp = mStyle.process[mKeyFrame];
			tmp.setRegion((int)tmp.getRegionX(), (int)tmp.getRegionY(), 
						  (int)(mCurrentPercent*mFirstWidth), (int)mFirstHeight);
			batch.draw(tmp,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),scaleX,scaleY,90);
		}
	}

	@Override
	public void act (float delta) {
		super.act(delta);
		if(isAnimation){
			if(mStyle.time > 0 && mCurrentTime >= 0 && mCurrentTime <= mStyle.time){
				mCurrentTime += mStyle.mode.value * delta;
				mCurrentPercent = mCurrentTime/mStyle.time;
				if(mProcessListener != null)
					mProcessListener.ProcessValue(this, mCurrentPercent);
			}
			
			mStateTime += delta;
			if(mStyle.frameduration > 0){
				mKeyFrame = (int) (mStateTime/mStyle.frameduration);
				mKeyFrame = mKeyFrame % mStyle.process.length;
			}else
				mKeyFrame =  0;
		}
	}

	/************************************************************
	 * 
	 ***********************************************************/
	
	public void startAnimation(){
		isAnimation = true;
	}
	
	public void startAnimation(float frameDuration){
		mStyle.frameduration = frameDuration;
		isAnimation = true;
	}
	
	public void stopAnimation(){
		isAnimation = false;
		mStateTime = 0;
		mKeyFrame = 0;
	}
	
	public void reset(){
		isAnimation = false;
		
		mStateTime= 0;
		mKeyFrame = 0;
		
		switch (mStyle.mode) {
			case COUNT_DOWN:
				mCurrentPercent = 1f;
				mCurrentTime = mStyle.time;
				break;
			case COUNT_UP:
				mCurrentPercent = 0f;
				mCurrentTime = 0;
				break;

			default:
				break;
		}
	}
	
	public boolean isAnimating(){
		return isAnimation;
	}
	/************************************************************
	 * 
	 ***********************************************************/
	
	
	public static class ProcessBarStyle {
		public TextureRegion background;
		
		public TextureRegion process[];
		public float frameduration;
		
		// time = -1 mean dont use time
		public float time = -1;
	
		
		public ProcessMode mode = ProcessMode.COUNT_UP;
		public int orientation = E.orientation.LANDSCAPE;
	}
	
	public static interface OnProcessListener {
		public void ProcessValue(ProcessBar processBar,float currentPercent);
	}
	
	public static enum ProcessMode{
		COUNT_UP(1),
		COUNT_DOWN(-1);
		
		final int value;
		private ProcessMode (int value) {
			this.value = value;
		}
	}
}
