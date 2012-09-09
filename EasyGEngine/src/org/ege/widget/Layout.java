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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

/**
 * Layout is more flexible by using multi panel to present final UI
 * 
 * @author Trung
 * 
 */
public class Layout extends Stage implements Pauseable {

	private static int			LAYOUT_ID				= 999;

	// ================================================
	// Panel manage

	private final Panel			mDefaultPanel			= new Panel();

	// for safemode enable
	private Panel				mSafeModePanel			= null;

	/**
	 * We only use this in safemode to save the last UI when pause and resume
	 */
	private final Array<Actor>	mCurrentVisiblePanel	= new Array<Actor>(6);
	private boolean				isSafeModeEnable		= false;

	// ==================================================

	public final int			ID;

	public float				alpha					= 1f;

	final Image					mBackground;

	public Layout (boolean strecth, SpriteBatch batch) {
		super(eAdmin.uiWidth(), eAdmin.uiHeight(), strecth, batch);
		addPanel(mDefaultPanel);
		mCurPanel = mDefaultPanel;
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
			mDefaultPanel.addActorAt(0, mBackground);
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

	/**
	 * Add track to new panel
	 * 
	 * @param panel
	 */
	public void addPanel (Panel panel) {
		addPanelToRoot(panel);
	}

	/**
	 * Create new panel track to it and set it as current for add function
	 * 
	 * @return
	 */
	public Panel createPanel () {
		Panel panel = new Panel();
		mCurPanel = panel;
		addPanelToRoot(panel);
		return panel;
	}

	/**
	 * Turn this panel into safe mode, only use a backup panel (mSafeModePanel)
	 * 
	 * @return
	 */
	public Panel createSafeModePanel () {
		// save visible list
		mCurrentVisiblePanel.clear();
		Array<Actor> list = getActors();
		for (Actor a : list)
			if (a.isVisible())
				mCurrentVisiblePanel.add(a);

		// create safe panel
		if (mSafeModePanel == null)
			mSafeModePanel = new Panel();

		mSafeModePanel.setVisible(true);
		mCurPanel = mSafeModePanel;
		addPanelToRoot(mSafeModePanel);

		isSafeModeEnable = true;
		return mSafeModePanel;
	}

	/**
	 * Restore the panel state to the before enable safe mode
	 */
	public void restore () {
		if (isSafeModeEnable) {
			mSafeModePanel.clear();
			getRoot().removeActor(mSafeModePanel);
			for (Actor a : mCurrentVisiblePanel)
				a.setVisible(true);
			mCurPanel = mDefaultPanel;
			isSafeModeEnable = false;
		}
	}

	public void removePanel (Panel panel) {
		getRoot().removeActor(panel);
	}

	public void setVisiblePanel (Panel... list) {
		Array<Actor> root = getActors();
		for (Actor a : root)
			a.setVisible(false);
		for (Panel panel : list)
			panel.setVisible(true);
	}

	public void setToDefault () {
		mCurPanel = mDefaultPanel;
		Array<Actor> root = getActors();
		for (Actor a : root)
			a.setVisible(false);
		mDefaultPanel.setVisible(true);
	}

	public void setToCurrent () {
		Array<Actor> root = getActors();
		for (Actor a : root)
			a.setVisible(false);
		mCurPanel.setVisible(true);
	}

	private void addPanelToRoot (Panel panel) {
		getRoot().addActor(panel);
	}

	// ==========================================
	// getter method

	public Panel getCurrentPanel () {
		return mCurPanel;
	}

	public Panel getDefaultPanel () {
		return mDefaultPanel;
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
		if (getActors().contains(panel, true))
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

	/**
	 * This method will remove all panel (just keep default panel ) and then
	 * reset the default panel
	 */
	public void clear () {
		super.clear();

		mDefaultPanel.clear();
		mDefaultPanel.setVisible(true);

		mCurPanel = mDefaultPanel;
		addPanelToRoot(mDefaultPanel);

		eAdmin.einput.removeProcessor(ID);
	}

	@Override
	public void dispose () {
		super.dispose();
		eAdmin.einput.removeProcessor(ID);
	}

}
