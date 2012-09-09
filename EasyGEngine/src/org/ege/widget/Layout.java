package org.ege.widget;

import okj.easy.core.eAdmin;

import org.ege.utils.Pauseable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Values;
import com.badlogic.gdx.utils.Scaling;

/**
 * Layout is more flexible by using multi panel to present final UI
 * @author Trung
 *
 */
public class Layout extends Stage implements Pauseable {

	private static int						LAYOUT_ID		= 999;

	private int								PANEL_ID		= 0;
	private final ObjectMap<Integer, Panel>	mPanelList		= new ObjectMap<Integer, Panel>(13);
	private final Panel						mDefaultPanel	= new Panel();

	// ==================================================

	public final int						ID;

	public float							alpha			= 1f;

	final Image								mBackground;

	public Layout (boolean strecth, SpriteBatch batch) {
		super(eAdmin.uiWidth(), eAdmin.uiHeight(), strecth, batch);
		addPanel(mDefaultPanel);
		mDefaultPanel.setBounds(0, 0, eAdmin.uiWidth(), eAdmin.uiHeight());
		
		ID = LAYOUT_ID++;

		mBackground = new Image();
		mBackground.setBounds(0, 0, eAdmin.uiWidth(), eAdmin.uiHeight());
		mBackground.setTouchable(Touchable.disabled);
		mBackground.setScaling(Scaling.stretch);
		mBackground.setZIndex(0);

		eAdmin.einput.addProcessor(ID, this);

		addActor(mBackground);
	}

	/*******************************************************
	 * 
	 *******************************************************/

	public void setBackground (Drawable drawable) {
		if (mBackground.getStage() == null)
			getRoot().addActorAt(0, mBackground);
		mBackground.setDrawable(drawable);
	}

	public Image getBackground () {
		return mBackground;
	}

	/*******************************************************
	 * 
	 *******************************************************/

	public void addAction (Action action) {
		mBackground.addAction(action);
		getRoot().addAction(action);
	}

	public Layout setEnable (boolean enable) {
		mBackground.setTouchable(Touchable.enabled);
		return this;
	}

	/*******************************************************
	 * Panel Manage
	 *******************************************************/

	public int addPanel (Panel panel) {
		mPanelList.put(PANEL_ID, panel);
		addPanelToRoot(panel);
		return PANEL_ID++;
	}

	public Panel createPanel () {
		Panel panel = new Panel();
		mPanelList.put(PANEL_ID++, panel);
		mCurPanel = panel;
		addPanelToRoot(panel);
		return panel;
	}

	public int createPanelId () {
		Panel panel = new Panel();
		mPanelList.put(PANEL_ID, panel);
		mCurPanel = panel;
		addPanelToRoot(panel);
		return PANEL_ID++;
	}
	
	public void setVisiblePanel (int... panelIds) {
		for (int i : panelIds)
			mPanelList.get(i).setVisible(false);
	}

	public void setVisiblePanel (Panel... list) {
		Values<Panel> vals = mPanelList.values();
		while (vals.hasNext())
			vals.next().setVisible(false);
		for (Panel panel : list)
			panel.setVisible(true);
	}

	public void setToDefault () {
		mCurPanel = mDefaultPanel;
		Values<Panel> vals = mPanelList.values();
		while (vals.hasNext())
			vals.next().setVisible(false);
		mDefaultPanel.setVisible(true);
	}

	public void setToCurrent () {
		Values<Panel> vals = mPanelList.values();
		while (vals.hasNext())
			vals.next().setVisible(false);
		mCurPanel.setVisible(true);
	}

	private void addPanelToRoot (Panel panel) {
		getRoot().addActor(panel);
	}

	/*******************************************************
	 * Actor manage
	 *******************************************************/
	private Panel	mCurPanel	= mDefaultPanel;

	@Override
	public void addActor (Actor actor) {
		mCurPanel.addActor(actor);
	}

	public void setCurrentPanel (Panel panel) {
		if (mPanelList.containsValue(panel, true))
			mCurPanel = panel;
	}

	/*******************************************************
	 * 
	 *******************************************************/
	@Override
	public void Pause () {
		eAdmin.einput.removeProcessor(ID);
	}

	@Override
	public void Resume () {
		eAdmin.einput.addProcessor(ID, this);
	}

	/*******************************************************
	 * 
	 *******************************************************/

	public void clear () {
		super.clear();
		eAdmin.einput.removeProcessor(ID);
	}

	@Override
	public void dispose () {
		super.dispose();
		eAdmin.einput.removeProcessor(ID);
	}

}
