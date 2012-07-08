
package org.ege.utils;

import okj.easy.graphics.graphics2d.wrapper.EntityAttribute.Direction;

import org.ege.utils.exception.EasyGEngineRuntimeException;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/** 
 * @author Ngo Trong Trung*/
public class GraphicsHelper {
	
	public static TextureRegion[] split (Texture texture,int regionNumber,int tileWidth,int tileHeight,int padding){
		TextureRegion tmp = new TextureRegion(texture);
		int x = tmp.getRegionX();
		int y = tmp.getRegionY();
		int width = tmp.getRegionWidth();
		int height = tmp.getRegionHeight();
		
		int pad = 0;
		
		if(padding > 0)
			pad = padding;
		
		if (width < 0) {
			x = x - width;
			width = -width;
		}

		if (height < 0) {
			y = y - height;
			height = -height;
		}
		/**
		 * Row is a int so when i devide like this the excessive area will be dismiss
		 * . But remember the excessive area must be smaller than tile width and tile height
		 */
		int rows = (height+pad) / (tileHeight+pad);
		int cols = (width+ pad) / (tileWidth+pad);

		TextureRegion[] tiles = new TextureRegion[regionNumber];
		
		int count = 0;
		int startX = x;	
		
		for(int i = 0;i < rows;i++){
			for(int j =0;j < cols;j++){
				if(count < regionNumber){
					tiles[count] = new TextureRegion(texture,x,y,tileWidth,tileHeight);
					count++;
				}
				x += tileWidth + pad;
			}
			x = startX;
			y += tileHeight + pad;
		}
		
		return tiles;
	}
	
	public static TextureRegion[] split (TextureRegion region,int totalRegionNumber,int numberOfRegionInARow,int numberOfRegionInAColumn,int padding){
		Texture texture = region.getTexture();
		
		TextureRegion[] tmp = new TextureRegion[totalRegionNumber];
		int count = 0;
		
		final int firstX = region.getRegionX();
		final int firstY = region.getRegionY();
		
		int tileWidth = (region.getRegionWidth()-(padding*(numberOfRegionInARow-1)))/numberOfRegionInARow;
		int tileHeight = (region.getRegionHeight()-(padding*(numberOfRegionInAColumn-1)))/numberOfRegionInAColumn;
		
		for(int i = 0;i < numberOfRegionInAColumn;i++){
			for(int j = 0;j < numberOfRegionInARow;j++){
				tmp[count] = new TextureRegion(texture,firstX + (j*(tileWidth+padding)), firstY+ (i*(tileHeight+padding)),
													   tileWidth					    , tileHeight);
				count++;
				if(count >= totalRegionNumber)
					return tmp;
			}
		}
		return tmp;
	}
	
	public static TextureRegion[] regionConvert(Array<AtlasRegion> regions){
		if(! (regions.get(0) instanceof TextureRegion))
			throw new EasyGEngineRuntimeException("Your List not instance of TextureRegion");
		TextureRegion[] tmp = new TextureRegion[regions.size];
		for(int i = 0,n = regions.size;i < n;i++){
			tmp[i] = (TextureRegion) regions.get(i);
		}
		return tmp;
	}
	
	
	public static String getOppositeDirection(String direction){
		if(direction == Direction.UP)
			return Direction.DOWN;
		else if(direction == Direction.DOWN)
			return Direction.UP;
		
		if(direction == Direction.LEFT)
			return Direction.RIGHT;
		else if(direction == Direction.RIGHT)
			return Direction.LEFT;
		
		if(direction == Direction.TOP_LEFT)
			return Direction.DOWN_RIGHT;
		else if(direction == Direction.DOWN_RIGHT)
			return Direction.TOP_LEFT;
		
		if(direction == Direction.TOP_RIGHT)
			return Direction.DOWN_LEFT;
		else if(direction == Direction.DOWN_LEFT)
			return Direction.TOP_RIGHT;
		
		return null;
	}
	
	public static String[] getNearbyDirection(String direction){
		if(direction == Direction.UP)
			return new String[]{Direction.TOP_LEFT,Direction.TOP_RIGHT,Direction.LEFT,Direction.RIGHT};
		
		if(direction == Direction.DOWN)
			return new String[]{Direction.DOWN_LEFT,Direction.DOWN_RIGHT,Direction.LEFT,Direction.RIGHT};
		
		if(direction == Direction.DOWN_RIGHT)
			return new String[]{Direction.RIGHT,Direction.DOWN,Direction.DOWN_LEFT,Direction.TOP_RIGHT};
		
		if(direction == Direction.DOWN_LEFT)
			return new String[]{Direction.LEFT,Direction.DOWN,Direction.DOWN_RIGHT,Direction.TOP_LEFT};
		
		if(direction == Direction.TOP_RIGHT)
			return new String[]{Direction.UP,Direction.RIGHT,Direction.TOP_LEFT,Direction.DOWN_RIGHT};
		
		if(direction == Direction.TOP_LEFT)
			return new String[]{Direction.UP,Direction.LEFT,Direction.DOWN_LEFT,Direction.TOP_RIGHT};
		
		if(direction == Direction.RIGHT)
			return new String[]{Direction.DOWN_RIGHT,Direction.TOP_RIGHT,Direction.UP,Direction.DOWN};
		
		if(direction == Direction.LEFT)
			return new String[]{Direction.TOP_LEFT,Direction.DOWN_LEFT,Direction.UP,Direction.DOWN};
		
		return null;
	}
	
	/**
	 * THis function depend on the number of element in Direction List has the
	 * same size with origin direction and calculate the available way to
	 * turn (+1 or -1 its angle)
	 * @param oriDirection 
	 * @param directionList
	 * @return +1 if this oriDirection should be increase to reach the direction list,
	 * otherwise -1
	 */
	public static int getSideOfDirection(String oriDirection,String... directionList){
		int up = 0;
		int down = 0;
		if(oriDirection == Direction.RIGHT){
			for(int i = 0;i< directionList.length;i++){
				if(directionList[i] == Direction.DOWN_RIGHT)
					down++;
				else if(directionList[i] == Direction.DOWN)
					down++;
				else if(directionList[i] == Direction.TOP_RIGHT)
					up++;
				else if(directionList[i] == Direction.UP)
					up++;
			}
		}
		else if(oriDirection == Direction.LEFT){
			for(int i = 0;i< directionList.length;i++){
				if(directionList[i] == Direction.DOWN_LEFT)
					up++;
				else if(directionList[i] == Direction.DOWN)
					up++;
				else if(directionList[i] == Direction.TOP_LEFT)
					down++;
				else if(directionList[i] == Direction.UP)
					down++;
			}
		}
		else if(oriDirection == Direction.UP){
			for(int i = 0;i< directionList.length;i++){
				if(directionList[i] == Direction.TOP_LEFT)
					up++;
				else if(directionList[i] == Direction.LEFT)
					up++;
				else if(directionList[i] == Direction.TOP_RIGHT)
					down++;
				else if(directionList[i] == Direction.RIGHT)
					down++;
			}
		}
		else if(oriDirection == Direction.DOWN){
			for(int i = 0;i< directionList.length;i++){
				if(directionList[i] == Direction.DOWN_RIGHT)
					up++;
				else if(directionList[i] == Direction.RIGHT)
					up++;
				else if(directionList[i] == Direction.DOWN_LEFT)
					down++;
				else if(directionList[i] == Direction.LEFT)
					down++;
			}
		}
		else if(oriDirection == Direction.TOP_LEFT){
			for(int i = 0;i< directionList.length;i++){
				if(directionList[i] == Direction.UP)
					down++;
				else if(directionList[i] == Direction.LEFT)
					up++;
				else if(directionList[i] == Direction.TOP_RIGHT)
					down++;
				else if(directionList[i] == Direction.DOWN_LEFT)
					up++;
			}
		}
		else if(oriDirection == Direction.TOP_RIGHT){
			for(int i = 0;i< directionList.length;i++){
				if(directionList[i] == Direction.DOWN_RIGHT)
					down++;
				else if(directionList[i] == Direction.RIGHT)
					down++;
				else if(directionList[i] == Direction.TOP_LEFT)
					up++;
				else if(directionList[i] == Direction.UP)
					up++;
			}
		}
		else if(oriDirection == Direction.DOWN_LEFT){
			for(int i = 0;i< directionList.length;i++){
				if(directionList[i] == Direction.LEFT)
					down++;
				else if(directionList[i] == Direction.DOWN)
					up++;
				else if(directionList[i] == Direction.DOWN_RIGHT)
					up++;
				else if(directionList[i] == Direction.TOP_LEFT)
					down++;
			}
		}
		else {
			for(int i = 0;i< directionList.length;i++){
				if(directionList[i] == Direction.RIGHT)
					up++;
				else if(directionList[i] == Direction.DOWN)
					down++;
				else if(directionList[i] == Direction.TOP_RIGHT)
					up++;
				else if(directionList[i] == Direction.DOWN_LEFT)
					down++;
			}
		}
		if(up > down)
			return up;
		else 
			return -down;
	
	}
}
