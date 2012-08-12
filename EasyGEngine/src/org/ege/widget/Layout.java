package org.ege.widget;

import okj.easy.admin.eAdmin;

import org.ege.utils.Pauseable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public  class Layout extends Stage implements Pauseable{
	private static int LAYOUT_ID = 999;

	//	-----------------------------------------------------
	
	public final int ID;
	
	public float alpha = 1f;
	
	final Image mBackground;
	
	public Layout (boolean stretch) {
		super(eAdmin.uiWidth(), eAdmin.uiHeight(), stretch);
		ID = LAYOUT_ID ++;
		
		mBackground = new Image();
		mBackground.setBounds(0, 0, getWidth(), getHeight());
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setScaling(Scaling.stretch);
		mBackground.setZIndex(0);
		
		eAdmin.einput.addProcessor(ID, this);
		
		addActor(mBackground);
	}
	
	public Layout (LayoutStyle style){
		super(eAdmin.uiWidth(), eAdmin.uiHeight(), style.stretch);
		ID = LAYOUT_ID ++;
		
		mBackground = new Image(style.background);
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setBounds(0, 0, eAdmin.uiWidth(), eAdmin.uiHeight());
		mBackground.setZIndex(Integer.MIN_VALUE);
		mBackground.setScaling(Scaling.stretch);
		
		eAdmin.einput.addProcessor(ID, this);
		
		addActor(mBackground);
	}

	public Layout (boolean strecth,SpriteBatch batch){
		super(eAdmin.uiWidth(), eAdmin.uiHeight(), strecth, batch);
		ID = LAYOUT_ID ++;
		
		mBackground = new Image();
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setBounds(0, 0, eAdmin.uiWidth(), eAdmin.uiHeight());
		mBackground.setZIndex(Integer.MIN_VALUE);
		mBackground.setScaling(Scaling.stretch);

		eAdmin.einput.addProcessor(ID, this);

		addActor(mBackground);
	}
	
	public Layout (LayoutStyle style,SpriteBatch batch){
		super(eAdmin.uiWidth(), eAdmin.uiHeight(), style.stretch, batch);
		
		ID = LAYOUT_ID ++;
		
		mBackground = new Image(style.background);
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setBounds(0, 0, eAdmin.uiWidth(), eAdmin.uiHeight());
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
