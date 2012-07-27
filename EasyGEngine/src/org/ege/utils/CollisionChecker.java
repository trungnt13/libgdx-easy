package org.ege.utils;


public final class CollisionChecker {
	
	private CollisionChecker(){
		
	}
	
	/*****************************************************
	 * Grid simulation native method
	 *****************************************************/

	public static native void setGridData(int boundWidth,int boundHeight,int maxCol,int maxRow);
		
	public static native void project(float[] vector2,float x,float y);
		
	public static native int project(float x,float y);
	
	public static native void unproject(float[] vector2,int col,int row);
	
	public static native void unproject(float[] vector2,int id);
	
	public static native void toGridPos(float[] vector2,int id);
	
	public static native int toMappingId(int col,int row);

	/*****************************************************
	 * Sprite Manager
	 *****************************************************/
	
	private static final int[] mResultSet = new int[20000];
	private static int mResultSize =-1 ;
	private static int tmp = -1;
	
	public static int process(float[] boundingRectArray1,int size1,float[] boundingRectArray2,int size2){
		mResultSize = checkCollision(mResultSet, boundingRectArray1,size1,boundingRectArray2,size2);
		return getResultSize();
	}
	
	private static int getResultSize(){
		if(mResultSize >= 0){
			tmp = mResultSize;
			mResultSize  = -1;
			return tmp;
		}
		return -1;
	}
	
	public static int[] getResultSet(){
		return mResultSet;
	}
	
	/*****************************************************
	 * Sprite Manager
	 *****************************************************/
	
	public static native int checkCollision(int[] result,float[] boundingRectArray1,int size1,float[] boundingRectArray2,int size2);

}
