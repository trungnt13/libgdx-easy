package org.ege.utils;

public class SpriteUtils {
	private static final int[] mResultSet = new int[113];
	private static int mResultSize = 0; 
	
	/**
	 * Get the list of nearest sprite to the origin sprite
	 * @Note this is unsafe method process directly with native 
	 * @param resultSet  the result set you want
	 * @param numberOfResult the number of nearest sprite you want
	 * @param originSprite the center of origin sprite
	 * @param spriteList the list of sprite you want to check ( the list of center position of them) 
	 * @param spriteSize the size of that list ( such as you have 10 sprite want to check, so the size is 20)
	 */
	public static native void getNearestSprite(int[] resultSet,int numberOfResult,float[] originSprite,float[] spriteList,int spriteSize);

	/**
	 * Get the list of nearest sprite to the origin sprite
	 * @Note this is unsafe method process directly with native 
	 * @param resultSet  the result set you want
	 * @param numberOfResult the number of nearest sprite you want
	 * @param originSpriteX the center X of origin sprite
	 * @param originSpriteY the center Y of origin sprite
	 * @param spriteList the list of sprite you want to check ( the list of center position of them) 
	 * @param spriteSize the size of that list ( such as you have 10 sprite want to check, so the size is 20)
	 */
	public static native void getNearestSprite(int[] resultSet,int numberOfResult,float originSpriteX,float originSpriteY,float[] spriteList,int spriteSize);
}
