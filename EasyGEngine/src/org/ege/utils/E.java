package org.ege.utils;

/**
 * This class store all constant of easy engine
 * @author trung
 */
public final class E {
	private E(){
	}
	
	//	===================================================
	
	public static final class direction{
		public static final int TOP = 1<<0;
		public static final int LEFT = 1<<1;
		public static final int RIGHT = 1<<2;
		public static final int BOTTOM = 1<<3;
	}
	
	public static final class orientation{
		public static final int HORIZONTAL= 1;
		public static final int VERTICAL = 2;
		public static final int LANDSCAPE = 1;
		public static final int PORTRAIT = 2;
	}
}
