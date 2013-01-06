package org.ege.widget;

import java.util.List;

import org.ege.utils.Debug;
import org.ege.utils.E;
import org.ege.utils.Properties;
import org.ege.utils.Refreshable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ObjectMap;
import com.esotericsoftware.tablelayout.Cell;
import com.esotericsoftware.tablelayout.Value;



public abstract class SwipeView extends Table implements Refreshable,Debug,Disposable{
    
	private int orientation;
	
	private final FlickPane 	mFlickScrollPane ;
	private final FlickTable	mTable;
	
	
	private int mCurrentRow;
	private int mCurrentCol;
	
	private final Interpolation mAutoScrollInterpolation = Interpolation.elasticOut;
	
	private SwipeEffect mSwipeEffect;
	
	/**
	 * Auto scroll to focus on one child at center
	 */
	private boolean isAutoFocusScroll = false;
	
	/**
	 * Make a child map
	 */
	private final ObjectMap<Integer, Array<Actor>> RCChildMap = new ObjectMap<Integer, Array<Actor>>();
	
	//	------------------------------------------------------------
	//	Auto focus data
	
	private float currentScroll;
	private float targetScroll;
	
	
	private boolean justPanning = false;
	private boolean startAutoScroll = false;
	private float timer = 0;
	
	private float previousVelX = 0;
	private boolean checkAutoFocusX = false;
	
	private boolean checkAutoFocusY = false;
	private float previousVelY = 0;
	
	//	-------------------------------------------------------------
	//	Swipe effect data to get current and last focus
	
	float cellSize ;
	float spacing ;
	
	float containerSize ;
	
	private int mCurrentFocusID = 0;
	private int mLastFocusID = -1;
	private Array<Actor> tmp;
	
	//	-------------------------------------------------------------
//	// Data for auto gen table property
	IntArray mUnFocusID = new IntArray(2);
	int tmpInt;

	public SwipeView(){
		super();
		mTable = new FlickTable();
		mFlickScrollPane = new FlickPane(mTable);
		mFlickScrollPane.setFlickScroll(true);
	}

	
	public SwipeView(Drawable backgroundRegion){
		super();
		setBackground(backgroundRegion);
		
		mTable = new FlickTable();
		mFlickScrollPane = new FlickPane(mTable);
		mFlickScrollPane.setFlickScroll(true);
	}
	
	public SwipeView(Skin skin){
		super();
		
		mTable = new FlickTable();
		mFlickScrollPane = new FlickPane(mTable,skin);
		mFlickScrollPane.setFlickScroll(true);
	}
	
	public SwipeView (ScrollPaneStyle style){
		super();
		
		mTable = new  FlickTable();
		mFlickScrollPane = new FlickPane(mTable, style);
		mFlickScrollPane.setFlickScroll(true);
	}

	public abstract void initialize(final FlickTable flickTable);
	
	/****************************************************************
	 * Container method
	 ****************************************************************/
	
	@Override
	public void invalidate () {
		super.invalidate();
		refresh();
	}
	
	public void show(Stage stage){
		initialize(mTable);
		
		add(mFlickScrollPane).align(Align.center).expand();
		
		stage.addActor(this);
	}
	
	public Table parse(Properties property){
		property.apply(this);
		return this;
	}
	/****************************************************************
	 * Calculate method
	 ****************************************************************/
	
	private void generateContainerData(){
		if(orientation == 0)
			return;
		switch (orientation) {
			case E.orientation.HORIZONTAL:
				final float containerPadLeft = getPadLeft();
				final float containerPadRight =getPadRight();
				containerSize = (int)(getPrefWidth() - containerPadLeft - containerPadRight);
//				D.out("container " + containerPadLeft + " " + containerPadRight + "  " + containerSize);
				break;
			case E.orientation.VERTICAL:
				final float containerPadTop = getPadTop();
				final float containerPadBottom = getPadBottom();
				containerSize = (int)(getPrefHeight() - containerPadBottom - containerPadTop);
				break;
		}
	}


	private void generateFlickData(){
		if(mTable.getCells().size() == 0 || orientation == 0)
			return;
		switch (orientation) {
			case E.orientation.HORIZONTAL:
				final Cell sampleCell = mTable.getCells().get(0);
				cellSize =  sampleCell.getPrefWidth();
				spacing = 	sampleCell.getSpaceRight();
//				D.out("flick " +cellSize + " " + spacing + " " );
				break;
			case E.orientation.VERTICAL:
				final Cell sampleCell1 = mTable.getCells().get(0);
				cellSize = sampleCell1.getPrefHeight();
				spacing = sampleCell1.getSpaceBottom();
				break;
		}
	}

	@Override
	public void refresh () {
		generateContainerData();
		generateFlickData();
	}

	/****************************************************************
	 * Swipe method
	 ****************************************************************/
	
	
	public SwipeView putDefaultProperty(Properties property){
		property.pad(0);
		property.apply(mTable.defaults());
		return this;
	}
	
	public void addSwipeEffect(SwipeEffect effect){
		mSwipeEffect = effect;
		
		if(mCurrentFocusID == 0 && mTable.getCells().size() > 1)
			switch (orientation) {
				case E.orientation.HORIZONTAL:
					for(int i =0 ;i <= mCurrentRow;i++)
						effect.CurrentFocusChild(getChild(i, 0));
					break;
				case E.orientation.VERTICAL:
					final int max = mCurrentRow ;
					for(int i =0 ;i < max ;i++)
						effect.CurrentFocusChild(getChild(0, i));
					break;
			}
	}
	
	public void setAutoFocusScroll(boolean isAuto){
		if(orientation != E.orientation.HORIZONTAL && orientation != E.orientation.VERTICAL )
			isAutoFocusScroll = false;
		else
			isAutoFocusScroll = isAuto;
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if(isAutoFocusScroll){
			if(previousVelX != mFlickScrollPane.getVelocityX()){
				checkAutoFocusX = true;
				previousVelX = mFlickScrollPane.getVelocityX();
			}
			
			if(previousVelY != mFlickScrollPane.getVelocityY()){
				checkAutoFocusY = true;
				previousVelY = mFlickScrollPane.getVelocityY();
			}
			
			if(mFlickScrollPane.isPanning()){
				justPanning = true;
			}
			
			if(!mFlickScrollPane.isFlinging() && !mFlickScrollPane.isPanning()){
				switch (orientation) {
					case E.orientation.HORIZONTAL:
						if(checkAutoFocusX || justPanning)
							autoScrollX();
						break;
					case E.orientation.VERTICAL:
						if(checkAutoFocusY || justPanning)
							autoScrollY();
						break;
				}
			}else{
				startAutoScroll = false;
				timer = 0;
			}
		}
	}

	@Override
	public void act (float delta) {
		super.act(delta);
		
		if(orientation == 0)
			return;
		
		if(mFlickScrollPane.isFlinging() || mFlickScrollPane.isPanning()){
				switch (orientation) {
					case E.orientation.HORIZONTAL:
						//Caculate current focus ID
						mCurrentFocusID = (int) ((mFlickScrollPane.getScrollX() + spacing/2  + (containerSize/2))  
												/ 
										  (cellSize + spacing));
//						D.out((mCurrentFocusID + "  " + mFlickScrollPane.getScrollX()+ " result " + (mFlickScrollPane.getScrollX() + spacing/2  + (containerSize/2))));
						tmpInt = getChildSize();
						if( mCurrentFocusID >= tmpInt)
							mCurrentFocusID = tmpInt - 1;
						
						// check unfocus array
						if(mUnFocusID.contains(mCurrentFocusID)){
							if(mCurrentFocusID < table().getCells().size()/2)
								mCurrentFocusID++;
							else
								mCurrentFocusID--;
						}
						
						// If nothign return;
						if(mCurrentFocusID == mLastFocusID ||  mSwipeEffect == null )
							return;
						
						//run swipe effect
						for(int i = 0 ; i <= mCurrentRow; i++){
							mSwipeEffect.CurrentFocusChild(getChild(i, mCurrentFocusID));
							if(mLastFocusID > 0 )
								mSwipeEffect.PreviousFocusChild(getChild(i, mLastFocusID));
							else
								mSwipeEffect.PreviousFocusChild(getChild(i, 0));
						}
						mLastFocusID = mCurrentFocusID;
						break;
						
					case E.orientation.VERTICAL:
						//Caculate current focus ID
						mCurrentFocusID = (int) ((mFlickScrollPane.getScrollY()  + (spacing/2) + (containerSize/2))  
												/ 
										  (cellSize +  spacing ) );
					
						tmpInt = getChildSize();
						if( mCurrentFocusID >= tmpInt)
							mCurrentFocusID = tmpInt - 1;
						
						// check unfocus array
						if(mUnFocusID.contains(mCurrentFocusID)){
							if(mCurrentFocusID < table().getCells().size()/2)
								mCurrentFocusID++;
							else
								mCurrentFocusID--;
						}
						
						// If nothign return;
						if(mCurrentFocusID == mLastFocusID ||  mSwipeEffect == null)
							return;
						
						//run swipe effect
						tmp = getChildList(mCurrentFocusID);
						for(int i = 0 ; i < tmp.size; i++){
							mSwipeEffect.CurrentFocusChild(tmp.get(i));
						}
						if(mLastFocusID > 0 )
							tmp  = getChildList(mLastFocusID);
						else 
							tmp = getChildList(0);
						for(int i = 0 ; i < tmp.size; i++){
							mSwipeEffect.PreviousFocusChild(tmp.get(i));
						}
						mLastFocusID = mCurrentFocusID;
					break;
				}
		}
		
		if(startAutoScroll){
			timer += delta ;
			float mdelta = targetScroll - currentScroll;
			float alpha = mAutoScrollInterpolation.apply(Math.min(1f, timer/1.5f));
			
			switch (orientation) {
				case E.orientation.HORIZONTAL:
					mFlickScrollPane.setScrollX(currentScroll+ mdelta*alpha);
					break;
				case E.orientation.VERTICAL:
					mFlickScrollPane.setScrollY(currentScroll+ mdelta*alpha);
					break;
			}
			
			if(timer >= 1.5f){
				startAutoScroll = false;
				timer = 0;
			}
		}
	}

	private void autoScrollX(){
		currentScroll = mFlickScrollPane.getScrollX();
		
		targetScroll = (mCurrentFocusID*(cellSize+spacing))
							-
					   ((containerSize/2) - (cellSize/2));
			
		startAutoScroll = true;
		checkAutoFocusX = false;
		justPanning = false;
		
	}
	
	private void autoScrollY(){
		currentScroll = mFlickScrollPane.getScrollY();
		
		targetScroll = (mCurrentFocusID*(cellSize+spacing))
							-
					    ((containerSize/2) - (cellSize/2));

		startAutoScroll = true;
		justPanning = false;
		checkAutoFocusY = false;
	}
	
	/****************************************************************
	 * Child method
	 ****************************************************************/
	public void addUnfocusID(int...id){
		for(int i:id)
			mUnFocusID.add(i);
	}
	
	public void removeUnfocusID(int...id){
		for(int i :id)
			mUnFocusID.removeValue(i);
	}
	
	public int getCurrentFocusID(){
		return mCurrentFocusID;
	}
	
	public int getChildSize(){
		return mTable.getCells().size();
	}
	
	public ObjectMap<Integer, Array<Actor>> getRCChildMap(){
		return RCChildMap;
	}
	
	public Array<Actor> getChildList(int givenRow){
		return RCChildMap.get(givenRow);
	}
	
	public Actor getChild(int givenRow,int givenCol){
		return getChildList(givenRow).get(givenCol);
	}
	



	/****************************************************************
	 * Simple get and set method
	 ****************************************************************/
	
	
	public int getCurrentRow(){
		return mCurrentRow;
	}
	
	public int getCurrentCol(){
		return mCurrentCol;
	}
	
	public boolean isAutoFocus(){
		return isAutoFocusScroll;
	}

	public int getOrientation(){
		return orientation;
	}
	
	public float getCellSize(){
		return cellSize;
	}
	
	public float getCellSpacing(){
		return spacing;
	}
	
	public float getContainerSize(){
		return containerSize;
	}
	
	public String info(){
		return "Orientation: " +orientation + " auto: " +isAutoFocusScroll +" row: " + mCurrentRow + " col: " + mCurrentCol;
	}
	/****************************************************************
	 * Table method
	 ****************************************************************/
	
	public Table table(){
		return mTable;
	}
	
	public Cell tableCell(){
		return mTable.getCells().get(0);
	}
	
	public Table spacing(int space){
		List<Cell> cellList = mTable.getCells();
		for(int i =0 ; i < cellList.size(); i++)
			cellList.get(i).space(space);
		generateFlickData();
		return mTable;
	}
	
	
	/****************************************************************
	 * Flick scroll pane method
	 ****************************************************************/
	
	/** Prevents scrolling out of the widget's bounds. Default is true. */
	public SwipeView setClamp(boolean isClamp){
		mFlickScrollPane.setClamp(isClamp);
		return this;
	}
	
	public SwipeView setSwipeOrientation(int orientation){
		if(orientation == E.orientation.HORIZONTAL ||orientation == E.orientation.LANDSCAPE){
			this.orientation = E.orientation.HORIZONTAL;
			mFlickScrollPane.setScrollingDisabled(false, true);
		}else if(orientation == E.orientation.VERTICAL ||orientation == E.orientation.PORTRAIT){
			this.orientation = E.orientation.VERTICAL;
			mFlickScrollPane.setScrollingDisabled(true, false);
		}else{
			mFlickScrollPane.setScrollingDisabled(true, true);
		}
		return this;
	}
	
	/** If true, the widget can be scrolled slightly past its bounds and will animate back to its bounds when scrolling is stopped.
	 * Default is true. */
	public SwipeView setOverscroll (boolean overscroll) {
		mFlickScrollPane.setOverscroll(overscroll,overscroll);
		return this;
	}

	/** Sets the overscroll distance in pixels and the speed it returns to the widgets bounds in seconds. Default is 50, 30, 200. */
	public SwipeView setupOverscroll (float distance, float speedMin, float speedMax) {
		mFlickScrollPane.setupOverscroll(distance, speedMin, speedMax);
		return this;
	}

	/** Forces the enabling of overscrolling in a direction, even if the contents do not exceed the bounds in that direction. */
	public SwipeView setForceOverscroll (boolean x, boolean y) {
		mFlickScrollPane.setForceOverscroll(x, y);
		return this;
	}

	/** Sets the amount of time in seconds that a fling will continue to scroll. Default is 1. */
	public SwipeView setFlingTime (float flingTime) {
		mFlickScrollPane.setFlingTime(flingTime);
		return this;
	}

	public SwipeView setVelocityX (float velocityX) {
		mFlickScrollPane.setVelocityX(velocityX);
		return this;
	}

	public SwipeView setVelocityY (float velocityY) {
		mFlickScrollPane.setVelocityY(velocityY);
		return this;
	}

	public SwipeView setFadeScrollBars (boolean fadeScrollBars) {
		mFlickScrollPane.setFadeScrollBars(fadeScrollBars);
		return this;
	}

	public SwipeView setupFadeScrollBars (float fadeAlphaSeconds, float fadeDelaySeconds) {
		mFlickScrollPane.setupFadeScrollBars(fadeAlphaSeconds, fadeDelaySeconds);
		return this;
	}
	
	/**
	 * From 0.0f to 1.0f ( slow to fast)
	 */
	public SwipeView setFlingSensitive (float sensitive){
		mFlickScrollPane.setFlingSensitive(sensitive);
		return this;
	}
	
	public float getScrollX(){
		return mFlickScrollPane.getScrollX();
	}

	public float getScrollY(){
		return mFlickScrollPane.getScrollY();
	}

	public float getScrollPercentX(){
		return mFlickScrollPane.getScrollPercentX();
	}

	public float getScrollPercentY(){
		return mFlickScrollPane.getScrollPercentY();
	}

	/**************************************************************
	 * 
	 **************************************************************/

	@Override
	public void dispose () {
		Array<Actor> a = mTable.getChildren();
		final int size = a.size;
		for(int i =0 ;i < size;i++){
			if(a instanceof Disposable)
				((Disposable)a).dispose();
		}
		mTable.clear();
		mFlickScrollPane.clear();
		clear();
	}
	
	
	/**************************************************************
	 * 
	 **************************************************************/
	private void addChild(int row,Actor actor){
		Array<Actor> tmp = RCChildMap.get(row);
		if(tmp == null){
			tmp = new Array<Actor>();
			RCChildMap.put(row, tmp);
		}
		tmp.add(actor);
	}
	
	/**
	 * 
	 * @author trung
	 */
	public class FlickTable extends Table{
		private int row = 0;
		
		@Override
		public Cell add (String text) {
			Cell cell =  super.add(text).pad(0);
			addChild(row,(Actor) cell.getWidget());
			return cell;
		}

		@Override
		public Cell add (String text, String labelStyleName) {
			Cell cell =  super.add(text, labelStyleName).pad(0);
			addChild(row,(Actor) cell.getWidget());
			return cell;
		}

		@Override
		public Cell add () {
			Cell cell =  super.add().pad(0);
			addChild(row,(Actor) cell.getWidget());
			return cell;
		}

		@Override
		public Cell add (Actor actor) {
			addChild(row,actor);
			return super.add(actor).pad(0);
		}

		
		@Override
		public Cell row () {
			row ++;
			return super.row();
		}

		@Override
		public Table pad (Value pad) {
			return super.pad(0);
		}

		@Override
		public Table pad (Value top, Value left, Value bottom, Value right) {
			return super.pad(0,0,0,0);
			
		}

		@Override
		public Table padTop (Value padTop) {
			return super.padTop(0);
			
		}

		@Override
		public Table padLeft (Value padLeft) {
			return super.padLeft(0);
			
		}

		@Override
		public Table padBottom (Value padBottom) {
			return super.padBottom(0);
			
		}

		@Override
		public Table padRight (Value padRight) {
			return super.padRight(0);
			
		}

		@Override
		public Table pad (float pad) {
			return super.pad(0);
			
		}

		@Override
		public Table pad (float top, float left, float bottom, float right) {
			return super.pad(0, 0,0,0);
			
		}

		@Override
		public Table padTop (float padTop) {
			return super.padTop(0);
			
		}

		@Override
		public Table padLeft (float padLeft) {
			return super.padLeft(0);
			
		}

		@Override
		public Table padBottom (float padBottom) {
			return super.padBottom(0);
			
		}

		@Override
		public Table padRight (float padRight) {
			return super.padRight(0);
		}
	}
	

	/**
	 * 
	 * @author trung
	 */
	private static class FlickPane extends ScrollPane{
		public FlickPane (Actor widget, ScrollPaneStyle style) {
			super(widget,style);
		}
		
		public FlickPane (Actor widget){
			super(widget);
		}
		
		public FlickPane (Actor widget,Skin skin){
			super(widget,skin);
		}

		@Override
		public void setFlickScroll (boolean flickScroll) {
			super.setFlickScroll(true);
		}
	}

	public static interface SwipeEffect{
		public void PreviousFocusChild(Actor actor);
		
		public void CurrentFocusChild(Actor actor);
	}

}
