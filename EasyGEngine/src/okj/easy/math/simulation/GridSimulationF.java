package okj.easy.math.simulation;

import java.util.ArrayList;

import okj.easy.core.eAdmin;

import org.ege.utils.E;

import com.badlogic.gdx.math.Vector2;

/**
 * GridSimulation with a <b>"FIXED"</b> row,or column or both 
 * @Note In the algorithm , the grid is count from 0 to N and it's grid position
 * depend on that counting and alse the maximun row or column
 * <p> such as (for landscape mode): 
 *<b><p>
 * 1	3	5	7	9 <p> 
 * 0	2	4	6	8
 * @author Ngo Trong Trung
 *</b>
 */
public class GridSimulationF extends SimulationAdapter{
	public static final byte TOP_LEFT = 1;
	public static final byte BOTTOM_LEFT = 2;
	
	
	//	----------------------------------------------------------

	private float startX = 0;
	private float startY = 0;
	
	private float width = 10;
	private float height = 10;
	
	private int maxRows = 9;
	private int maxCols = 9;
	
	int divisor = 10;
	
	float padding = 0;
	
	int orientation;

	//	----------------------------------------------------------

	public GridSimulationF(float startX,float startY,float padding,int orientation){
		this.startX = startX;
		this.startY = startY;
		this.padding = padding;
		this.orientation = orientation;
	}
	
	/**
	 * For infinite of row or column just set it to -1 : maxRows = -1 <=> maxRows = INFINITE
	 * @param width
	 * @param height
	 * @param maxRows
	 * @param maxCols
	 */
	public void setViewGrid(float width,float height,int maxRows,int maxCols){
		this.width = width + padding;
		this.height = height + padding;
		this.maxCols = maxCols;
		this.maxRows = maxRows;
	}
	
	

	/**
	 * unproject and return list of position depend on the number of view<p>
	 * you can choose the 0 point at the BOTTOM_LEFT or TOP_LEFT 
	 * @param numberOfView
	 * @param zeroPointInSytem
	 * @return
	 */
	public Vector2[] unproject(int numberOfView,byte zeroPointInSytem){
		ArrayList<Vector2> tmp = new ArrayList<Vector2>();
		if(orientation == E.orientation.LANDSCAPE){
			if(maxCols <= 0){
				for(int i = 0;i < numberOfView;i++){
					float y = startY + (height * (i % maxRows));
					float x = startX + (width * ( i / maxRows));
					tmp.add( new Vector2(x, y));
				}
			}else{
				int curCol = -1;
				boolean repeat = false;
				for(int i = 0; i< numberOfView;i++){
					float y = startY + (height * (i % maxRows));
					float x = startX + (width *  (i / maxRows));
					tmp.add( new Vector2(x, y));
					if(i % maxRows == 0)
						curCol ++;
					if(curCol == maxCols )
						repeat = true;
					if(repeat)
						return tmp.toArray(new Vector2[tmp.size()]);
				}
			}
		}else{
			if(maxRows <= 0){
				for(int i = 0;i < numberOfView;i++){
					float x = startX + (width * (i % maxCols));
					float y = startY + (height *(i / maxCols));
					tmp.add( new Vector2(x,y));
				}
			}else{
				int curRow = -1;
				boolean repeat = false;
				for(int i = 0;i < numberOfView;i++){
					float x = startX + (width * (i % maxCols));
					float y = startY + (height *(i / maxCols));
					tmp.add( new Vector2(x,y));
					if(i % maxRows == 0)
						curRow ++;
					if(curRow == maxRows -1)
						repeat = true;
					if(repeat)
						return tmp.toArray(new Vector2[tmp.size()]);
				}
			}
		}
		Vector2[] test = new Vector2[tmp.size()];
		tmp.toArray(test);
		if(zeroPointInSytem == TOP_LEFT)
			turnToTopLeftSystem(test);
		return test;
	}
	
	private void turnToTopLeftSystem(Vector2[] poss){
		for(int i = 0;i < poss.length;i++){
			poss[i].set(poss[i].x, eAdmin.gameWidth()-poss[i].y);
		}
	}



	@Override
	public int toMappingId(float xPosition, float yPosition) {
		float x = xPosition - startX;
		float y = yPosition - startY;
		x = x - (x % width);
		y = y - (y % height);
		if(orientation == E.orientation.LANDSCAPE){
			return (int) (maxRows*(x/width+1) - (maxRows - y/height));
		}else{
			return (int) (maxCols*(y/height+1) - (maxCols - x/width));
		}
	}

	public Vector2 toRealPos(int ID){
		if(orientation == E.orientation.LANDSCAPE){
			return new Vector2(startX + (width *(ID / maxRows)), 
							    startY + (height *(ID % maxRows)));
		}else
			return new Vector2(startX + (width *(ID % maxCols)), 
								startY + (height*(ID / maxCols)));
	}
	
	public float getGridWidth(){
		return this.width-padding;
	}
	
	public float getGridHeight(){
		return this.height-padding;
	}
	
	@Deprecated
	public Vector2 toGridPos(int ID) {
		return null;
	}

	@Deprecated
	public int toMappingId(int row, int column) {
		return 0;
	}

	@Deprecated
	public Vector2 unproject(float column, float row) {
		return null;
	}
	

	@Deprecated
	public Vector2 project(float x, float y) {
		return null;
		
	}

	@Deprecated
	public Vector2 unproject(int row, int column) {
		return null;
	}
}
