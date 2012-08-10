package okj.easy.admin;

import org.ege.utils.exception.EasyGEngineRuntimeException;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class eGraphics {
	
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
	
}
