package okj.easy.admin;

import java.util.ArrayList;

import okj.easy.admin.eInput.MotionCallBack.MotionEvent;
import okj.easy.input.OnBackKeyListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.ObjectMap;



public class eInput implements InputProcessor{
	public static MotionEvent currentMotion;

	private final ArrayList<InputProcessor> mInputProcessors = new ArrayList<InputProcessor>();
	private final ObjectMap<Integer, InputProcessor> mInputMap = new ObjectMap<Integer, InputProcessor>();
	
	private final ArrayList<MotionCallBack> mMotionList = new ArrayList<MotionCallBack>();
	private final ArrayList<KeyCallBack> mKeysList = new ArrayList<KeyCallBack>();
	
	//	==============================================	
	
	private float viewFrustumWidth;
	private float viewFrustumHeight;

	OnBackKeyListener mBackKeyListener = null;
	
	int tmp;
	
	//	==============================================	

	private int mLastTouchPointer;
	
	public eInput () {
	}

	public void setInputViewport(float viewWidth,float viewHeight){
		viewFrustumWidth = viewWidth;
		viewFrustumHeight = viewHeight;
	}
	
	/**
	 * Add a new processor at a specified index( the index must in range [0,size]
	 * @param index your index
	 * @param processor your processor
	 * 
	 */
	public void addProcessor(int index, InputProcessor processor) {
		if(!mInputMap.containsKey(index)){
			mInputProcessors.add(processor);
			mInputMap.put(index,processor);
		}else{
			removeProcessor(index);
			addProcessor(index, processor);
		}
	}
	
	/**
	 * Remove a processor at specified index( The index must in range [0,size])
	 * @param index your index
	 */
	public void removeProcessor(int index) {
		InputProcessor input = mInputMap.remove(index);
		mInputProcessors.remove(input);
	}

	
	public final void registerBackKeyListener(OnBackKeyListener listener){
		mBackKeyListener = listener;
	}
	
	public void unregisterBackKeyListener(){
		mBackKeyListener = null;
	}

	/**
	 * @return the number of mInputProcessors in this multiplexer
	 */
	public int size() {
		return mInputProcessors.size();
	}
	
	/**
	 * Clear the processor list
	 */
	public void clear () {
		mInputProcessors.clear();
		mInputMap.clear();
		mMotionList.clear();
		mKeysList.clear();
	}

	/**
	 * Get the array list of mInputProcessors
	 * @return Array<InputProcessor>
	 */
	ArrayList<InputProcessor> getProcessors() {
		return mInputProcessors;
	}
	
	public InputProcessor findInputById(int id){
		return mInputMap.get(id);
	}
	
	/***************************************************************
	 * 
	 ***************************************************************/
	
	/** Called when a key was pressed
	 * 
	 * @param keycode one of the constants in {@link Input.Keys}
	 * @return whether the input was processed */
	public boolean keyDown (int keycode) {
		tmp = mInputProcessors.size();
		for (int i = 0; i < tmp; i++)
			mInputProcessors.get(i).keyDown(keycode);

		tmp = mKeysList.size();
		for (int i = 0; i < tmp; i++)
			mKeysList.get(i).onKeyDown(keycode);
		
		return true;
	}
	/** Called when a key was released
	 * 
	 * @param keycode one of the constants in {@link Input.Keys}
	 * @return whether the input was processed */
	public boolean keyUp (int keycode) {
		if(mBackKeyListener != null && keycode == Keys.BACK)
			return mBackKeyListener.BackKeyPressed();
		
		tmp = mInputProcessors.size();
		for (int i = 0; i <  tmp; i++)
			mInputProcessors.get(i).keyUp(keycode);
		
		tmp = mKeysList.size();
		for (int i = 0; i < tmp; i++)
			mKeysList.get(i).onKeyUp(keycode);
		
		return true;
	}
	
	/** Called when a key was typed
	 * 
	 * @param character The character
	 * @return whether the input was processed */
	public boolean keyTyped (char character) {
		tmp = mInputProcessors.size();
		for (int i = 0; i < tmp; i++)
			mInputProcessors.get(i).keyTyped(character);
		
		tmp  = mKeysList.size();
		for (int i = 0; i < tmp; i++)
			mKeysList.get(i).onKeyTyped(character);
		
		return true;
	}
	
	/** Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Buttons#LEFT} on
	 * Android.
	 * 
	 * @param x The x coordinate, origin is in the upper left corner
	 * @param y The y coordinate, origin is in the upper left corner
	 * @param pointer the pointer for the event.
	 * @param button the button
	 * @return whether the input was processed */
	public boolean touchDown (int X, int Y, int pointer, int button) {
		mLastTouchPointer = pointer;
		
		int y = Gdx.graphics.getHeight()-Y;
		
		tmp = mInputProcessors.size();
		for (int i = 0; i < tmp; i++)
			mInputProcessors.get(i).touchDown(X, Y, pointer, button);
		
		tmp = mMotionList.size();
		for (int i = 0; i < tmp; i++)
			mMotionList.get(i).onTouchDown(X, y, pointer, button);
		
		return true;
	}
	
	/** Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Buttons#LEFT} on
	 * Android.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param pointer the pointer for the event.
	 * @param button the button
	 * @return whether the input was processed */
	public boolean touchUp (int X, int Y, int pointer, int button) {
		
		int y = Gdx.graphics.getHeight()-Y;
		
		tmp = mInputProcessors.size();
		for (int i = 0; i < tmp; i++)
			mInputProcessors.get(i).touchUp(X, Y, pointer, button);
		
		tmp = mMotionList.size();
		for (int i = 0; i < tmp; i++)
			mMotionList.get(i).onTouchUp(X, y, pointer, button);
		
		return true;
	}
	
	/** Called when a finger or the mouse was dragged.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param pointer the pointer for the event.
	 * @return whether the input was processed */
	public boolean touchDragged (int X, int Y, int pointer) {
		int y = Gdx.graphics.getHeight()-Y;
		tmp = mInputProcessors.size();
		for (int i = 0; i < tmp; i++)
			mInputProcessors.get(i).touchDragged(X, Y, pointer);
		
		tmp = mMotionList.size();
		for (int i = 0; i < tmp; i++)
			mMotionList.get(i).onTouchDragged(X, y, pointer);
		
		return true;
	}
	
	/** Called when the mouse was moved without any buttons being pressed. Will not be called on Android.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return whether the input was processed */
	@Override
	public boolean mouseMoved (int X, int Y) {
		int y = Gdx.graphics.getHeight()-Y;
		
		tmp = mInputProcessors.size();
		for (int i = 0; i < tmp; i++)
			mInputProcessors.get(i).mouseMoved(X, Y);
		
		tmp = mMotionList.size();
		for (int i = 0; i < tmp; i++)
			mMotionList.get(i).onTouchMoved(X, y);
		
		return true;	
	}
	
	/** Called when the mouse wheel was scrolled. Will not be called on Android.
	 * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
	 * @return whether the input was processed. */
	@Override
	public boolean scrolled (int amount) {
		tmp = mInputProcessors.size();
		for (int i = 0; i < tmp; i++)
			mInputProcessors.get(i).scrolled(amount);
		
		return true;
	}

	/**
	 * find the input by the given key with is specified when you create new Inputprocessor
	 * @param key  input's key
	 * @return 
	 */
	public InputProcessor findInputByID(int key){
		return mInputMap.get(key);
	}
	
	public void addMotionCallback(MotionCallBack callback){
		mMotionList.add((MotionCallBack) callback);
	}

	public void addKeyCallback(KeyCallBack callback){
		mKeysList.add(callback);
	}
	
	public void addCallback(MotionCallBack motionCallback,KeyCallBack keyCallback){
		mMotionList.add(motionCallback);
		mKeysList.add(keyCallback);
	}
	
	/**
	 * Register a new callback to listen to input event
	 * @param callback new Call back
	 */
	public void addCallback(Callback callback){
		if(callback instanceof MotionCallBack && callback instanceof KeyCallBack){
			mMotionList.add((MotionCallBack)callback);
			mKeysList.add((KeyCallBack)callback);	
			return;
		}else if(callback instanceof MotionCallBack){
			mMotionList.add((MotionCallBack) callback);
			return;
		}else if(callback instanceof KeyCallBack){
			this.mKeysList.add((KeyCallBack) callback);
			return;
		}
	}
	
	
	public boolean removeMotionCallback(MotionCallBack callback){
		return mMotionList.remove(callback);
	}
	
	public boolean removeKeyCallback(KeyCallBack callback){
		return mKeysList.remove(callback);
	}
	
	public boolean removeCallback(MotionCallBack motionCallback,KeyCallBack keyCallback){
		return mMotionList.remove(motionCallback) && mMotionList.remove(keyCallback);
	}
	
	/**
	 * Unregister the callback from listener list 
	 * @param callback the callback
	 * @return true if success remove, otherwise false
	 */
	public boolean removeCallback(Callback callback){
		if(callback instanceof MotionCallBack && callback instanceof KeyCallBack){
			return mMotionList.remove((MotionCallBack) callback) &&
				    mKeysList.remove((KeyCallBack) callback);
		}
		else if(callback instanceof MotionCallBack){
			return mMotionList.remove((MotionCallBack) callback);	
		}
		else if(callback instanceof KeyCallBack){
			return mKeysList.remove((KeyCallBack) callback);
		}
		return false;
	}
	

	public boolean containKeyCallback(KeyCallBack callback){
		return mKeysList.contains(callback);
	}
	
	public boolean containMotionCallback(MotionCallBack callback){
		return mMotionList.contains(callback);
	}

	/***************************************************************
	 * Method from Gdx.input
	 ***************************************************************/
	
	public int getLastTouchPointer(){
		return mLastTouchPointer;
	}
	
	public float getAccelerometerX() {
		return Gdx.input.getAccelerometerX();
	}

	public  float getAccelerometerY() {
		return Gdx.input.getAccelerometerY();
	}

	public  float getAccelerometerZ() {
		return Gdx.input.getAccelerometerZ();
	}

	public  int getX() {
		return Gdx.input.getX();
	}

	public float getProjectedX(){
		return ((float)Gdx.input.getX()/(float)Gdx.graphics.getWidth())*viewFrustumWidth;
	}
	
	public float getProjectedY(){
		return (1-(float)Gdx.input.getY()/(float)Gdx.graphics.getHeight())*viewFrustumHeight;
	}
	
	public  int getX(int pointer) {
		return Gdx.input.getX(pointer);
	}

	public  int getDeltaX() {
		return Gdx.input.getDeltaX();
	}

	public  int getDeltaX(int pointer) {
		return Gdx.input.getDeltaX(pointer);
	}

	public  int getY() {
		return Gdx.graphics.getHeight()-Gdx.input.getY();
	}

	public  int getY(int pointer) {
		return Gdx.graphics.getHeight() -Gdx.input.getY(pointer);
	}

	public int getDeltaY() {
		return -Gdx.input.getDeltaY();
	}

	public  int getDeltaY(int pointer) {
		return -Gdx.input.getDeltaY(pointer);
	}

	public  boolean isTouched() {
		return Gdx.input.isTouched();
	}

	public  boolean justTouched() {
		return Gdx.input.justTouched();
	}

	public boolean isTouched(int pointer) {
		return Gdx.input.isTouched(pointer);
	}

	public boolean isButtonPressed(int button) {
		return Gdx.input.isButtonPressed(button);
	}

	public  boolean isKeyPressed(int key) {
		return Gdx.input.isKeyPressed(key);
	}

	public  void getTextInput(TextInputListener listener, String title,
			String text) {
		Gdx.input.getTextInput(listener, title, text);
	}

	public  void getPlaceholderTextInput(TextInputListener listener,
			String title, String placeholder) {
		Gdx.input.getPlaceholderTextInput(listener, title, placeholder);
	}

	public  void setOnscreenKeyboardVisible(boolean visible) {
		Gdx.input.setOnscreenKeyboardVisible(visible);
	}

	public void vibrate(int milliseconds) {
		Gdx.input.vibrate(milliseconds);
	}

	public  void vibrate(long[] pattern, int repeat) {
		Gdx.input.vibrate(pattern, repeat);
	}

	public  void cancelVibrate() {
		Gdx.input.cancelVibrate();
	}

	public float getAzimuth() {
		return Gdx.input.getAzimuth();
	}

	public  float getPitch() {
		return Gdx.input.getPitch();
	}

	public float getRoll() {
		return Gdx.input.getRoll();
	}

	public  void getRotationMatrix(float[] matrix) {
		Gdx.input.getRotationMatrix(matrix);
	}

	public long getCurrentEventTime() {
		return Gdx.input.getCurrentEventTime();
	}

	public  void setCatchBackKey(boolean catchBack) {
		Gdx.input.setCatchBackKey(catchBack);
	}

	public void setCatchMenuKey(boolean catchMenu) {
		Gdx.input.setCatchMenuKey(catchMenu);
	}

	public boolean isPeripheralAvailable(Peripheral peripheral) {
		return Gdx.input.isPeripheralAvailable(peripheral);
	}

	public int getRotation() {
		return Gdx.input.getRotation();
	}

	public Orientation getNativeOrientation() {
		return Gdx.input.getNativeOrientation();
	}

	public void setCursorCatched(boolean catched) {
		Gdx.input.setCursorCatched(catched);
	}

	public boolean isCursorCatched() {
		return Gdx.input.isCursorCatched();
	}

	public void setCursorPosition(int x, int y) {
		Gdx.input.setCursorPosition(x, y);
	}
	
	/***************************************************************
	 * 
	 ***************************************************************/
	
	public interface Callback{
		
	}
	
	public interface MotionCallBack extends Callback{
		public enum MotionEvent{
			ACTION_UP,ACTION_DOWN,ACTION_DRAG,ACTION_MOVE
		};
		/** Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Buttons#LEFT} on
		 * Android.
		 * 
		 * @param x The x coordinate, origin is in the upper left corner
		 * @param y The y coordinate, origin is in the upper left corner
		 * @param pointer the pointer for the event.
		 * @param button the button
		 * @return whether the input was processed */
		public boolean onTouchDown (int x, int y, int pointer, int button);

		/** Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Buttons#LEFT} on
		 * Android.
		 * 
		 * @param x The x coordinate
		 * @param y The y coordinate
		 * @param pointer the pointer for the event.
		 * @param button the button
		 * @return whether the input was processed */
		public boolean onTouchUp (int x, int y, int pointer, int button);

		/** Called when a finger or the mouse was dragged.
		 * 
		 * @param x The x coordinate
		 * @param y The y coordinate
		 * @param pointer the pointer for the event.
		 * @return whether the input was processed */
		public boolean onTouchDragged (int x, int y, int pointer);

		/** Called when the mouse was moved without any buttons being pressed. Will not be called on Android.
		 * 
		 * @param x The x coordinate
		 * @param y The y coordinate
		 * @return whether the input was processed */
		public boolean onTouchMoved (int x, int y);
	}
	
	public interface KeyCallBack extends Callback{
		public enum KeyEvent{
			
		};
		
		/** Called when a key was pressed
		 * 
		 * @param keycode one of the constants in {@link Input.Keys}
		 * @return whether the input was processed */
		public boolean onKeyDown (int keycode);

		/** Called when a key was released
		 * 
		 * @param keycode one of the constants in {@link Input.Keys}
		 * @return whether the input was processed */
		public boolean onKeyUp (int keycode);

		/** Called when a key was typed
		 * 
		 * @param character The character
		 * @return whether the input was processed */
		public boolean onKeyTyped (char character);
	}

}
