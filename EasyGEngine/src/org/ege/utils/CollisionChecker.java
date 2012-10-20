package org.ege.utils;

public final class CollisionChecker
{

	private CollisionChecker() {

	}

	/*****************************************************
	 * Grid simulation native method
	 *****************************************************/

	public static native void setGridData (int boundWidth, int boundHeight, int maxCol, int maxRow);

	public static native void project (float[] vector2, float x, float y);

	public static native int project (float x, float y);

	public static native void unproject (float[] vector2, int col, int row);

	public static native void unproject (float[] vector2, int id);

	public static native void toGridPos (float[] vector2, int id);

	public static native int toMappingId (int col, int row);

	/*****************************************************
	 * Sprite Manager
	 *****************************************************/

	private static int tmp = -1;

	/**
	 * @Note you should carefull when use this method , when the size2 = 0 it will automatically
	 *       <p>
	 *       think that you want to check collision inside the array1 so the result will maybe not
	 *       <p>
	 *       ass you expected
	 * @param boundingRectArray1 the bounding float rect of list 1
	 * @param size1 the size of bounding float of list 1
	 * @param boundingRectArray2 the bounding float rect of list 2
	 * @param size2 the size of bounding float of list 2
	 * @return the number of collision happen * 2
	 */
	public static int process (int[] resultSet, float[] boundingRectArray1, int size1,
			float[] boundingRectArray2,
			int size2)
	{
		return checkCollision(resultSet, boundingRectArray1, size1, boundingRectArray2, size2);
	}

	/*****************************************************
	 * Sprite Manager
	 *****************************************************/
	/**
	 * @Note this is unsafe method process directly with native method without any check
	 * @param result
	 * @param boundingRectArray1
	 * @param size1
	 * @param boundingRectArray2
	 * @param size2
	 * @return
	 */
	public static native int checkCollision (int[] result, float[] boundingRectArray1, int size1,
			float[] boundingRectArray2, int size2);

}
