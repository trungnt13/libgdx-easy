package okj.easy.math.simulation;

import com.badlogic.gdx.math.Vector2;
/**
 * The most flexiblity algorithm for simulation  grid
 * <b><p>
 * . <p>
 * . <p>
 * . <p>
 * 25	26	27	28	29	...<p>
 * 20	21	22	23	24	<p>
 * 15	16	17	18	19	<p>
 * 5	6	7	8	9	<p>
 * 00	1	2	3	4	<p>
 * </b>
 * 
 * @Note the input alway (column,row) and (xPosition,yPosition)
 * @author Ngo Trong Trung
 *
 */
public class GridSimulation {
	private int startX = 0;
	private int startY = 0;
	
	private int width = 10;
	private int height = 10;
	
	private int maxRow = 9;
	private int maxCol = 9;
	
	private final Vector2 mTemp = new Vector2();
	
	/**
	 * this variable decide the mapping method on the grid. 
	 * if (max row and column <= 9) , it will be 10 so from the id mapping such as
	 * 87 we get the: row = 87/10 = 8 and the column = 87% 10 = 7 . But if(max row and column
	 * <= 99),divisor must be 100 so we have: row = 87/100 = 0 and the column = 87 % 100 = 87.
	 * (one more example: row = 807/100 = 8 and the column = 807%100 = 7)
	 *  ****I just support for max number of row and column is 99****
	 */
	private int paddingHor = 0;
	
	private int paddingVer = 0;
	
	public GridSimulation(){
		
	}
	
	public GridSimulation(int startX,int startY,int paddingHor,int paddingVer){
		this.startX = startX;
		this.startY = startY;
		this.paddingHor = paddingHor;
		this.paddingVer = paddingVer;
	}
	
	public GridSimulation(int startX,int startY,int width,int height,int paddingHor,int paddingVer){
		this.startX = startX;
		this.startY = startY;
		this.paddingVer = paddingVer;
		this.paddingHor = paddingHor;
		this.width = width + paddingHor;
		this.height = height + paddingVer;
	}
	
	public void set(int startX,int startY,int width,int height,int paddingHor,int paddingVer,int maxRow,int maxCol){
		this.startX = startX;
		this.startY = startY;
		this.paddingVer = paddingVer;
		this.paddingHor = paddingHor;
		this.width = width + paddingHor;
		this.height = height + paddingVer;

		if(maxRow < 0 || maxCol < 0)
			return;
		this.maxRow = maxRow;
		this.maxCol = maxCol;
	}
	
	public GridSimulation setStartPosition(float startX,float startY){
		this.startX = (int) startX;
		this.startY = (int) startY;
		return this;
	}
	
	public GridSimulation setStartPosition(int startX,int startY){
		this.startX = startX;
		this.startY = startY;
		return this;
	}
	
	public GridSimulation setViewGrid(float width,float height){
		this.width = (int) (width + paddingHor);
		this.height = (int) (height + paddingVer);
		return this;
	}

	public GridSimulation setViewGrid(int width,int height){
		this.width = (width + paddingHor);
		this.height = (height + paddingVer);
		return this;
	}

	
	public GridSimulation setBound(int startX,int startY,int width,int height){
		this.startX = startX;
		this.startY = startY;
		this.width = width;
		this.height = height;
		return this;
	}

	public GridSimulation setBound(float startX,float startY,float width,float height){
		this.startX = (int) startX;
		this.startY = (int) startY;
		this.width = (int) width;
		this.height = (int) height;
		return this;
	}
	
	public GridSimulation setSize(float width,float height,float paddingVer,float paddingHor){
		this.width = (int) (width + paddingHor);
		this.height = (int) (height + paddingVer);
		
		this.paddingHor = (int) paddingHor;
		this.paddingVer = (int) paddingVer;
		return this;
	}
	
	public GridSimulation setSize(int width,int height,int paddingVer,int paddingHor){
		this.width = (width + paddingHor);
		this.height =(height + paddingVer);
		
		this.paddingHor = paddingHor;
		this.paddingVer = paddingVer;
		return this;
	}
	
	public GridSimulation setPadding(float paddingX,float paddingY){
		width = (int) (width - paddingHor + paddingX);
		height  = (int) (height - paddingVer + paddingY);
		
		paddingHor = (int) paddingX;
		paddingVer = (int) paddingY;
		return this;
	}
	
	public GridSimulation setPadding(int paddingX,int paddingY){
		width = (width - paddingHor + paddingX);
		height  =(height - paddingVer + paddingY);
		
		paddingHor =paddingX;
		paddingVer =paddingY;
		return this;
	}

	
	/**
	 * The default is 9 it mean there are no maximum of row and column, and it
	 * will never be zero!!
	 *    * Remember if your row >= mean your column must be > 10 ,too
	 * @param maxRows the maximum row number
	 * @param maxCols the maximum column number
	 */
	public void setMaxGrid(int maxCol,int maxRow){
		if(maxRow < 0 || maxCol < 0)
			return;
		this.maxRow = maxRow;
		this.maxCol = maxCol;
	}
	
	
	/**********************************************************
	 * 
	 **********************************************************/
	
	/**
	 * project from position at (x,y) to column and row coordinate (lower-left is 0)
	 * @param x	x position 
	 * @param y	y position
	 * @return grid	(column,row)
	 */
	public  Vector2 project(float xPosition,float yPosition){
		final int X = (int) (xPosition - startX);
		final int Y = (int) (yPosition - startY);
		
		return new Vector2(X/width, Y/height);
	}

	public  Vector2 project(int xPosition,int yPosition){
		final int X = xPosition - startX;
		final int Y = yPosition - startY;
		
		return new Vector2(X/width, Y/height);
	}
	
	
	/**
	 * project from position at (x,y) to row and column coordinate (lower-left is 0)
	 * @param x	 x position 
	 * @param y	 y position
	 * @return grid	(column,row)
	 */
	public  Vector2 project(Vector2 result,float xPosition,float yPosition){
		final float X = xPosition - startX;
		final float Y = yPosition - startY;
		
		return result.set(X/width, Y/height);
	}


	public  Vector2 project(Vector2 result,int xPosition,int yPosition){
		final int X = xPosition - startX;
		final int Y = yPosition - startY;
		
		return result.set(X/width, Y/height);
	}
	
	/**
	 * Unproject from grid(row,column) to position in (x,y) with ( 0 is lower-left)
	 * @param row the row number
	 * @param column the column number
	 * @return position the position after unproject <b>(x,y)</b>
	 */
	public  Vector2 unproject(int column,int row){
		return new Vector2(column*width+startX, row*height+startY);
	}

	public  Vector2 unproject(Vector2 result,int column,int row){
		return result.set(column*width+startX, row*height+startY);
	}

	
	public Vector2 unproject(float column, float row) {
		return new Vector2(column*width+startX, row*height+startY);
	}

	public Vector2 unproject(Vector2 result,float column, float row) {
		return result.set(column*width+startX, row*height+startY);
	}

	/**********************************************************
	 * 
	 **********************************************************/
	
	/**
	 * Convert the given grid(column,row) to the id in the ball Map 
	 * which will be : row*10 + column
	 * @param row row number
	 * @param column column number
	 * @return id
	 */
	public int toMappingId(int column,int row){
		return (row*maxCol +  column);
	}
	
	/**
	 * Convert the given position (x,y ) to the id in the ball map
	 * . The position will be project first to be row and column and then
	 * i use : row * 10 + column 
	 * @param xPosition position  x
	 * @param yPosition position y
	 * @return id
	 */
	public int  toMappingId(float xPosition,float yPosition) {
		return toMappingId(project(mTemp, xPosition, yPosition));
	}
	
	/**
	 * Convert the given gri d(row,column) in type of Vector2 to the id in the ball
	 * Map wich will be: row * maxRow + column
	 * @param gridPosition x is column, y is row
	 * @return
	 */
	public int toMappingId(Vector2 gridPosition){
		return ((int)gridPosition.y*maxCol+(int)gridPosition.x);
	}
	
	/**
	 * Convert the given id ( in ball map) to  grid's position which is 
	 * row and column . We have :
	 * row = ID /10; 
	 * column = ID %10; 
	 * @param ID the ballmap's id
	 * @return Vector2 which contain grid position (column is x, row is y )
	 */
	public Vector2 toGridPos(int ID){
		return new Vector2(ID % maxCol,ID / maxCol);
	}

	
	public Vector2 toGridPos(Vector2 result,int ID){
		return result.set(ID % maxCol,ID / maxCol);
	}
	
	public Vector2 toRealPos(int ID){
		return unproject(ID%maxCol, ID/maxCol);
	}
	
	public Vector2 toRealPos(Vector2 result,int ID){
		return unproject(result, ID%maxCol, ID/maxCol);
	}

	/**********************************************************
	 * 
	 **********************************************************/

	public float getGridWidth(){
		return this.width-paddingHor;
	}
	
	public float getGridHeight(){
		return this.height - paddingVer;
	}

	public float getPaddingHor(){
		return this.paddingHor;
	}
	
	public float getPaddingVer(){
		return this.paddingVer;
	}

	/**********************************************************
	 * 
	 **********************************************************/

	public String info(){
		StringBuilder tmp = new StringBuilder();
		tmp.append("Grid Info:  " + startX + " | ");
		tmp.append(startY + " | ");
		tmp.append(width + " | ");
		tmp.append(height + " | ");
		tmp.append(paddingHor + " | ");
		tmp.append(paddingVer + " | ");
		tmp.append(maxRow + " | ");
		tmp.append(maxCol + " | ");
		return tmp.toString();
	}
}
