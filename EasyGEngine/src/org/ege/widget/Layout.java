package org.ege.widget;

import okj.easy.admin.eAdmin;

import org.ege.utils.Orientation;
import org.ege.utils.Pauseable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public  class Layout extends Stage implements Pauseable{
	public static final byte MULTI_RESOLUTION_MODE = 0;
	public static final byte FIXED_RESOLUTION_MODE = 1;
	public static final byte FIXED_MULTI_RESOLUTION_MODE = 2;
	public static final byte MANUAL_RESOLUTION_MODE = 3;
	
	private static int LAYOUT_ID = 999;
	
	public static int UI_HEIGHT = 320;
	public static int UI_WIDTH = 480;
	public static float TOAST_WIDTH = 220;
	public static Orientation ORIENTATION = Orientation.LANDSCAPE;
	
	private static byte mCurrentMode = FIXED_RESOLUTION_MODE;
	
	public static final void $Calculate(int screenWIdth,int screenHEight){
		switch (mCurrentMode) {
			case FIXED_RESOLUTION_MODE:
				if(screenWIdth > screenHEight){
					ORIENTATION = Orientation.LANDSCAPE;
					UI_WIDTH = 800;
					UI_HEIGHT = 500;
				}else{
					ORIENTATION = Orientation.PORTRAIT;
					UI_WIDTH = 500;
					UI_HEIGHT = 800;
				}
				break;
			case MULTI_RESOLUTION_MODE:
				UI_WIDTH =  screenWIdth;
				UI_HEIGHT = screenHEight;
				if(screenWIdth > screenHEight){
					ORIENTATION = Orientation.LANDSCAPE;
				}else{
					ORIENTATION = Orientation.PORTRAIT;
				}
				break;
			case FIXED_MULTI_RESOLUTION_MODE:
				float ratio = (float)screenWIdth / (float)screenHEight;
				UI_WIDTH =  (int) (ratio * 320);
				UI_HEIGHT = 320;
				if(screenWIdth > screenHEight){
					ORIENTATION = Orientation.LANDSCAPE;
				}else{
					ORIENTATION = Orientation.PORTRAIT;
				}
				break;
			case MANUAL_RESOLUTION_MODE:
				if(screenWIdth > screenHEight){
					ORIENTATION = Orientation.LANDSCAPE;
				}else{
					ORIENTATION = Orientation.PORTRAIT;
				}
				break;
		}
		TOAST_WIDTH = 2f/3f *(float)UI_WIDTH;
	}

	public static final void setResolutionHandle(byte resolutionMode){
		mCurrentMode = resolutionMode;
	}
	
	//	-----------------------------------------------------
	
	public final int ID;
	
	public float alpha = 1f;
	
	final Image mBackground;
	
	public Layout (boolean stretch) {
		super(UI_WIDTH, UI_HEIGHT, stretch);
		ID = LAYOUT_ID ++;
		
		mBackground = new Image();
		mBackground.setBounds(0, 0, getWidth(), getHeight());
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setScaling(Scaling.stretch);
		mBackground.setZIndex(Integer.MIN_VALUE);
		
		eAdmin.einput.addProcessor(ID, this);
		
		addActor(mBackground);
	}
	
	public Layout (LayoutStyle style){
		super(UI_WIDTH, UI_HEIGHT, style.stretch);
		ID = LAYOUT_ID ++;
		
		mBackground = new Image(style.background);
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setBounds(0, 0, UI_WIDTH, UI_HEIGHT);
		mBackground.setZIndex(Integer.MIN_VALUE);
		mBackground.setScaling(Scaling.stretch);
		
		eAdmin.einput.addProcessor(ID, this);
		
		addActor(mBackground);
	}

	public Layout (boolean strecth,SpriteBatch batch){
		super(UI_WIDTH, UI_HEIGHT, strecth, batch);
		ID = LAYOUT_ID ++;
		
		mBackground = new Image();
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setBounds(0, 0, UI_WIDTH, UI_HEIGHT);
		mBackground.setZIndex(Integer.MIN_VALUE);
		mBackground.setScaling(Scaling.stretch);

		eAdmin.einput.addProcessor(ID, this);

		addActor(mBackground);
	}
	
	public Layout (LayoutStyle style,SpriteBatch batch){
		super(UI_WIDTH, UI_HEIGHT, style.stretch, batch);
		
		ID = LAYOUT_ID ++;
		
		mBackground = new Image(style.background);
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setBounds(0, 0, UI_WIDTH, UI_HEIGHT);
		mBackground.setZIndex(Integer.MIN_VALUE);
		mBackground.setScaling(Scaling.stretch);
		
		eAdmin.einput.addProcessor(ID, this);
		
		addActor(mBackground);
	}
	
	public void setBackground(Drawable drawable){
		mBackground.setDrawable(drawable);
	}

	
	public Image getBackground(){
		return mBackground;
	}
	
	public void addAction(Action action){
		mBackground.addAction(action);
		getRoot().addAction(action);
	}

	public Layout setEnable(boolean enable){
		mBackground.setTouchable(Touchable.enabled);
		return this;
	}
	
	
	@Override
	public void Pause () {
		eAdmin.einput.removeProcessor(ID);
	}

	@Override
	public void Resume () {
		eAdmin.einput.addProcessor(ID, this);
	}

	@Override
	public void dispose () {
		super.dispose();
		eAdmin.einput.removeProcessor(ID);
	}
	
	public static class LayoutStyle {
		public Drawable background;
		
		public boolean stretch;
	}
}
