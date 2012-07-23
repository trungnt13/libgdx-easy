package org.ege.widget;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class Panel extends Group{
	final Image mBackground ;
	PanelStyle mStyle;

	boolean isStarting;
	
	public Panel (){
		super();
		mStyle = new PanelStyle();
		
		mBackground = new Image();
		mBackground.setBounds(0, 0, getWidth(), getHeight());
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setScaling(Scaling.stretch);
		mBackground.setZIndex(Integer.MIN_VALUE);
		
		addActor(mBackground);
	}
	
	public Panel (PanelStyle style){
		super();
		mStyle = style;
		
		mBackground = new Image();
		mBackground.setBounds(0, 0, getWidth(), getHeight());
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setScaling(Scaling.stretch);
		mBackground.setZIndex(Integer.MIN_VALUE);

		addActor(mBackground);
	}
	
	public Panel (Drawable region){
		super();
		mStyle = new PanelStyle();
		
		mBackground = new Image(region);
		mBackground.setBounds(0, 0, getWidth(), getHeight());
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setScaling(Scaling.stretch);
		mBackground.setZIndex(Integer.MIN_VALUE);
		
		addActor(mBackground);
	}

	
	/*********************************************************
	 * 
	 *********************************************************/

	
	public void setBackground(Drawable drawable){
		mBackground.setDrawable(drawable);
	}
	
	public Image getBackground(){
		return mBackground;
	}
	
	/**********************************************************
	 * 
	 **********************************************************/
	
	public static class PanelStyle{
		public Drawable background;
	}
}
