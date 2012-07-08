package okj.easy.graphics.graphics2d;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Composite Sprite , a sprite which contain many other sprite and
 * draw at the same time. The main Sprite will be the first layout(as a background),
 * which mean all other sprite will be draw on the main sprite
 * @author trung
 *
 */
public class SpriteC extends Sprite{
	private final ArrayList<Sprite> sprites;

	public static final byte SYN = 0;
	public static final byte ASYN = 1;
	
	private byte mode = 0;
	
	public SpriteC(){
		sprites = new ArrayList<Sprite>();
		setColor(1, 1, 1, 1);
	}
	
	public SpriteC(Texture texture){
		this(new TextureRegion(texture));
	}
	
	public SpriteC(TextureRegion region){
		sprites = new ArrayList<Sprite>();
		setRegion(region);
		setColor(1, 1, 1, 1);
		setSize(Math.abs(region.getRegionWidth()), Math.abs(region.getRegionHeight()));
		setOrigin(getWidth() / 2, getHeight() / 2);
	}
	
	public SpriteC(Texture texture, int srcWidth,int srcHeight){
		this(new TextureRegion(texture,0,0,srcWidth,srcHeight));
	}
	
	public SpriteC(Texture texture,int srcX,int srcY,int srcWidth,int srcHeight){
		this(new TextureRegion(texture,srcX,srcY,srcWidth,srcHeight));
	}
	
	public SpriteC(TextureRegion region,int srcX,int srcY,int srcWidth,int srcHeight){
		sprites = new ArrayList<Sprite>();
		setRegion(region, srcX, srcY, srcWidth, srcHeight);
		setColor(1, 1, 1, 1);
		setSize(Math.abs(srcWidth), Math.abs(srcHeight));
		setOrigin(getWidth() / 2, getHeight() / 2);
	}
	
	/********************************************************
	 * SpriteC specify method 
	 *******************************************************/
	public void setMode(byte spriteCMode){
		this.mode = spriteCMode;
	}
	
	/**
	 * If you want to add Asyn-sprite you should call setMode(byte spriteMode)
	 * before you add sprite
	 * @param sprite
	 */
	public void addSprite(Sprite sprite){
		this.sprites.add(sprite);
		if(mode == SYN){
			sprite.setPosition(getX(), getY());
			sprite.setSize(getWidth(), getHeight());
			sprite.setRotation(getRotation());
		}
	}
	
	
	public void removeSprite(Sprite sprite){
		this.sprites.remove(sprite);
	}
	
	public void removeSprite(int index){
		this.sprites.remove(index);
	}

	public ArrayList<Sprite> sprites(){
		return this.sprites;
	}
	/********************************************************
	 * 
	 *******************************************************/

	@Override
	public void setBounds(float x, float y, float width, float height) {
		super.setBounds(x, y, width, height);
		if(mode == SYN)
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).setBounds(x, y, width, height);
			}
	}

	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		if(mode == SYN)
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).setSize(width, height);
			}
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		if(mode == SYN)
				for (int i = 0;i < sprites.size();i++) {
					sprites.get(i).setPosition(x, y);
				}
	}

	@Override
	public void translate(float xAmount, float yAmount) {
		super.translate(xAmount, yAmount);
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).translate(xAmount, yAmount);
			}
	}

	@Override
	public void translateX(float xAmount) {
		super.translateX(xAmount);
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).translateX(xAmount);
			}
	}

	@Override
	public void translateY(float yAmount) {
		super.translateY(yAmount);
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).translateY(yAmount);
			}
	}

	@Override
	public void setOrigin(float originX, float originY) {
		super.setOrigin(originX, originY);
		if(mode == SYN)
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).setOrigin(originX, originY);
			}
	}

	@Override
	public void setRotation(float degrees) {
		super.setRotation(degrees);
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).setRotation(degrees);
			}
	}

	@Override
	public void rotate(float degrees) {
		super.rotate(degrees);
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).rotate(degrees);
			}
	}

	@Override
	public void rotate90(boolean clockwise) {
		super.rotate90(clockwise);
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).rotate90(clockwise);
			}
	}

	@Override
	public void setScale(float scaleXY) {
		super.setScale(scaleXY);
		if(mode == SYN)
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).setScale(scaleXY);
			}
	}

	@Override
	public void setScale(float scaleX, float scaleY) {
		super.setScale(scaleX, scaleY);
		if(mode == SYN)
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).setScale(scaleX, scaleY);
			}
	}

	@Override
	public void scale(float amount) {
		super.scale(amount);
		if(mode == SYN)
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).scale(amount);
			}
	}


	@Override
	public void flip(boolean x, boolean y) {
		super.flip(x, y);
		if(mode == SYN)
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).flip(x, y);
			}
	}

	@Override
	public void scroll(float xAmount, float yAmount) {
		super.scroll(xAmount, yAmount);
		if(mode == SYN)
			for (int i = 0;i < sprites.size();i++) {
				sprites.get(i).scroll(xAmount, yAmount);
			}
	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
		for (int i = 0;i < sprites.size();i++) {
			sprites.get(i).draw(spriteBatch);
		}
	}

	@Override
	public void draw(SpriteBatch spriteBatch, float alphaModulation) {
		super.draw(spriteBatch, alphaModulation);
		for (int i = 0;i < sprites.size();i++) {
			sprites.get(i).draw(spriteBatch, alphaModulation);
		}
	} 
	
}
