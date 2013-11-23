package stu.tnt.gdx.widget;

public class Attributes {
	public float startX ;
	public float startY ;
	
	public float x ;
	public float y ;
	
	public float width ;
	public float height ;
	
	public void set(float startX,float startY,float dstX,float dstY,float width,float height){
		this.startX = startX;
		this.startY = startY;
		this.x = dstX;
		this.y = dstY;
		this.width = width;
		this.height = height;
	}
	
	public String info(){
		return "" + startX + "   " + startY + "  "  + x + "  " + y  + "  " + width + "  " + height ;
	}
}
