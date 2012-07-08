package okj.easy.graphics.wrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class AttributeSet {
	
	float x = 0;
	float layout_x = -1;
	
	float y = 0;
	float layout_y = -1;
	
	float width = 0;
	float layout_width = -1;
	
	float height = 0;
	float layout_height = -1;
	
	float originX=-1;
	float layout_originX = -1;
	
	float originY=-1;
	float layout_originY = -1;
	
	float angle = 0;
	
	float WHratio = -1;
	float HWratio = -1;
	
	Color color = new Color();
	int mode = -1;
	
	String text;
	Color textColor = new Color();
	Color textDownColor = new Color();
	float textSize;
	Align textAlign;
	
	boolean focusable = true;
	boolean enable = true;
	boolean visible = true;

	/**
	 * Align of the View (it will textAlign all the components which is in the view)
	 */
	
	public  enum Align{
		TOP,BOTTOM,LEFT,RIGHT,CENTER,CENTER_HORIZONTAL,CENTER_VERTICAL
	}
	
	/*
	 * Two these attribute for view auto resize when screen size change 
	 */
	Vector2 sizeWhenResize = new Vector2(-1, -1);
	Vector2 positionWhenResize = new Vector2(-1, -1);
	
	public AttributeSet(){		
		color.set(1, 1, 1, 1);
		text = null;
		textColor.set(Color.BLACK);
		textSize = 0;
		
		focusable = true;
		enable = true;
		visible = true;
		
		textAlign = Align.CENTER;
	}

	
	public AttributeSet(float x, float y, float width,float height){
		set(x, y, width, height, x+width/2, y+height/2);

		color.set(1, 1, 1, 1);
		text = null;
		textColor.set(Color.BLACK);
		textSize = 0;
		
		focusable = true;
		enable = true;
		visible = true;
		
		textAlign = Align.CENTER;
	}
	

	/**
	 * 
	 * @param x		x position
	 * @param y		y position
	 * @param width	view's width
	 * @param height view's height
	 * @param originX view's pivot at x for scale and rotate
	 * @param originY view's pivot at y for scale and rotate
	 */
	public void set(float x,float y,float width,float height,float originX,float originY){
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setOriginX(originX);
		setOriginY(originY);
	}
	
	
	
	public Vector2 getSizeWhenResize() {
		return sizeWhenResize;
	}


	public void setSizeWhenResize(Vector2 sizeWhenResize) {
		this.sizeWhenResize = sizeWhenResize;
	}


	public Vector2 getPositionWhenResize() {
		return positionWhenResize;
	}


	public void setPositionWhenResize(Vector2 positionWhenResize) {
		this.positionWhenResize = positionWhenResize;
	}

	/*******************************************************
	 * Read position from XML
	 *******************************************************/

	public float getX() {
		return x;
	}


	public void setX(float x) {
		if(x >= 0 && x <= 1){
			setLayoutX(x);
			this.x = Gdx.graphics.getWidth()*x;
			return;
		}
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		if(y >= 0 && y <= 1){
			setLayoutY(y);
			this.y = Gdx.graphics.getHeight()*y;
			return;
		}
		this.y = y;
	}

	/*******************************************************
	 * Read size from XML
	 *******************************************************/

	public float getWidth() {
		return width;
	}


	public void setWidth(float width) {
		if(width >= 0 && width <= 1){
			setLayoutWidth(width);
			this.width = Gdx.graphics.getWidth()*width;
			return;
		}
		this.width = width;
	}


	public float getHeight() {
		return height;
	}


	public void setHeight(float height) {
		if(height >= 0 && height <= 1){
			setLayoutHeight(height);
			this.height = Gdx.graphics.getHeight()*height;
			return;
		}
		this.height = height;
	}

	/*******************************************************
	 * Read origin from XML
	 *******************************************************/

	public float getOriginX() {
		return originX;
	}


	public void setOriginX(float originX) {
		if(originX >= 0 && originX <= 1){
			setLayoutOriginX(originX);
			this.originX = Gdx.graphics.getWidth()*originX;
			return;
		}
		this.originX = originX;
	}


	public float getOriginY() {
		return originY;
	}


	public void setOriginY(float originY) {
		if(originY >= 0 && originY <= 1){
			setLayoutOriginY(originY);
			this.originX = Gdx.graphics.getHeight()*originY;
			return;
		}
		this.originY = originY;
	}


	/*******************************************************
	 * Read size from XML
	 *******************************************************/

	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public Color getTextColor() {
		return textColor;
	}


	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}


	public float getTextSize() {
		return textSize;
	}


	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	public Align getTextAlign() {
		return textAlign;
	}


	public void setTextAlign(Align textAlign) {
		this.textAlign = textAlign;
	}
	


	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public void setPosition(float x,float y){
		this.x = x;
		this.y = y;
	}

	public void setSize(float width,float height){
		this.width = width;
		this.height = height;
	}


	public float getLayoutX() {
		return layout_x;
	}


	private void setLayoutX(float layout_x) {
		this.layout_x = layout_x;
	}


	public float getLayoutY() {
		return layout_y;
	}


	private void setLayoutY(float layout_y) {
		this.layout_y = layout_y;
	}


	public float getLayoutWidth() {
		return layout_width;
	}


	private void setLayoutWidth(float layout_width) {
		this.layout_width = layout_width;
	}


	public float getLayoutHeight() {
		return layout_height;
	}


	private void setLayoutHeight(float layout_height) {
		this.layout_height = layout_height;
	}


	public float getLayoutoriginX() {
		return layout_originX;
	}

	private void setLayoutOriginX(float layout_originX) {
		this.layout_originX = layout_originX;
	}

	public float getLayoutOriginY() {
		return layout_originY;
	}

	private void setLayoutOriginY(float layout_origionY) {
		this.layout_originY = layout_origionY;
	}

	public float getWHratio() {
		return WHratio;
	}

	public void setWHratio(float wHratio) {
		WHratio = wHratio;
	}


	public float getHWratio() {
		return HWratio;
	}


	public void setHWratio(float hWratio) {
		HWratio = hWratio;
	}
	
	public boolean isFocusable() {
		return focusable;
	}


	public boolean isEnable() {
		return enable;
	}


	public boolean isVisible() {
		return visible;
	}


	public Color getTextDownColor() {
		return textDownColor;
	}


	public void setTextDownColor(Color textDownColor) {
		this.textDownColor = textDownColor;
	}
	
	public void setMode(int x){
		this.mode = x;
	}
	
	public int getMode(){
		return this.mode;
	}
	
}
