package stu.tnt.gdx.graphics.graphics2d;

import stu.tnt.gdx.graphics.wrapper.AttributeSet;
import stu.tnt.gdx.graphics.wrapper.AttributeSet.Align;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class CustomFont implements Disposable {
	private BitmapFont font;
	private Rectangle boundArea;

	private String text;

	private Color textColor = new Color();
	private Color textDownColor = new Color();

	private Align textAlign;

	private float textSize;

	private float scaleX;
	private float scaleY;
	private float x;
	private float y;

	private TextAlignAdapter adapter = null;

	private Color curColor;

	private float width = -1;
	private float height = -1;

	public CustomFont() {
		this.boundArea = new Rectangle();
	}

	public CustomFont(BitmapFont font, AttributeSet attr) {
		this();
		set(font, attr);
	}

	public void setAdapter(TextAlignAdapter adapter) {
		this.adapter = adapter;
	}

	public void set(BitmapFont font, AttributeSet attr) {
		this.font = font;
		this.text = attr.getText();

		this.textColor = attr.getTextColor();
		this.curColor = textColor;
		this.textDownColor = attr.getTextDownColor();

		this.textAlign = attr.getTextAlign();
		this.textSize = attr.getTextSize();

		this.boundArea = new Rectangle(attr.getX(), attr.getY(),
				attr.getWidth(), attr.getHeight());
	}

	public void setFont(BitmapFont font) {
		this.font = font;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public void setTextDownColor(Color textDownColor) {
		this.textDownColor = textDownColor;
	}

	public void setTextAlign(Align textAlign) {
		this.textAlign = textAlign;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public String getText() {
		return this.text;
	}

	public boolean isVisible() {
		if (this.font == null || this.text == null || this.textSize <= 0)
			return false;
		return true;
	}

	// public void invalidate (View parent)
	// {
	// // if(isVisible()){
	// // this.boundArea = parent.getBoundingRectangle();
	// // if(width == -1){
	// // width = font.getBounds(text).width;
	// // height = font.getBounds(text).height;
	// // }
	// // float WHratio = width/height;
	// // float HWratio = height/width;
	// //
	// // if(boundArea != null){
	// // if(textSize < boundArea.getHeight()){
	// // this.scaleY = textSize/height;
	// // this.scaleX = ((height*scaleY)*WHratio)/width;
	// // if(this.adapter == null)
	// // setPosition(boundArea.x + boundArea.width/2 - scaleX*width/2,
	// // boundArea.y + boundArea.height/2 + scaleY*height/2);
	// // else
	// // setPosition(adapter.calPosition(textAlign, scaleX*width,
	// scaleY*height).x,
	// // adapter.calPosition(textAlign, scaleX*width, scaleY*height).y);
	// // }else{
	// // this.scaleY = (boundArea.getHeight()/3*2)/height;
	// // this.scaleX = ((height*scaleY)*WHratio)/width;
	// // if(width * scaleX > boundArea.width){
	// // this.scaleX = boundArea.getWidth()/width;
	// // this.scaleY = ((width*scaleX)*HWratio)/height;
	// // }
	// // if(this.adapter == null)
	// // setPosition(boundArea.x + boundArea.width/2 - scaleX*width/2,
	// // boundArea.y + boundArea.height/2 + scaleY*height/2);
	// // else
	// // setPosition(adapter.calPosition(textAlign, scaleX*width,
	// scaleY*height).x,
	// // adapter.calPosition(textAlign, scaleX*width, scaleY*height).y);
	// // }
	// // }
	// // }
	// }

	public void downColor() {
		curColor = textDownColor;
	}

	public void upColor() {
		curColor = textColor;
	}

	public void draw(SpriteBatch batch) {
		font.setScale(scaleX, scaleY);
		font.setColor(curColor);
		font.draw(batch, text, x, y);
	}

	public void draw(SpriteBatch batch, float x, float y) {
		font.setScale(scaleX, scaleY);
		font.setColor(curColor);
		font.draw(batch, text, x, y);
	}

	@Override
	public void dispose() {
		this.adapter = null;
		this.boundArea = null;
		this.curColor = null;
		this.textColor = null;
		this.textDownColor = null;
		this.font = null;
		this.text = null;
	}
}
