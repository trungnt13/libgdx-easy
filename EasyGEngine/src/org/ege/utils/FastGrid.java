package org.ege.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * This Grid store <b>COLUMN</b> at the first 16 bit and <b>ROW</b> at the last 16 bit <p>
 * which mean it only can store 65536*65535 grid
 * @NOTE The return result and the input  will alway in these form: <p>
 * <b>(column,row)</b>	<p>
 * which is coordinate to <p>
 * <b>(xPosition,yPosition)</b>
 * @author trung
 *
 */
public class FastGrid {
	public static final int FIRST_16_ZERO_BIT = 0x0000ffff;

	private final int mBoundWidth;
	private final int mBoundHeight;
	
	private int mGridWith;
	private int mGridHeight;
	
	public FastGrid (int boundWidth,int boundHeight){
		this.mBoundHeight = boundHeight;
		this.mBoundWidth = boundWidth;
	}
	
	public FastGrid (int boundWidth,int boundHeight,int maxRow,int maxCol){
		this.mBoundHeight = boundHeight;
		this.mBoundWidth = boundWidth;
		
		this.mGridWith = boundWidth/maxCol;
		this.mGridHeight = boundHeight/maxRow;
	}
	
	public void setMaxGrid(int maxCol,int maxRow){
		this.mGridWith = mBoundWidth / maxCol;
		this.mGridHeight = mBoundHeight/maxRow;
	}
	
	public Vector2 project(Vector2 result,float x,float y){
		return result.set(x/mGridWith,y/mGridHeight);
	}
	
	public int project(float x,float y){
		return (((int)(x/mGridWith)<<16) | (int)y/mGridHeight);
	}
	
	public int project(int x,int y){
		return (((x/mGridWith)<<16) | (y/mGridHeight));
	}
	
	public Vector2 unproject(Vector2 result,int col,int row){
		return result.set(mGridWith*col, mGridHeight*row);
	}
	
	public Vector2 unproject(Vector2 result,int id){
		return result.set(mGridWith*(id>>16), mGridHeight*(id&FIRST_16_ZERO_BIT));
	}
	
	public Vector2 toGridPos(Vector2 result,int id){
		return result.set(id>>16, id & FIRST_16_ZERO_BIT);
	}
	
	public int toMappingId(int column,int row){
		return (column<<16 | row);
	}
}
