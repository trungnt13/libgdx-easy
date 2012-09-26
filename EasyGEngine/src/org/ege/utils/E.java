package org.ege.utils;

/**
 * This class store all constant of easy engine
 * 
 * @FileName: E.java
 * @CreateOn: Sep 15, 2012 - 11:46:56 AM
 * @Author: TrungNT
 */
public final class E {
	private E () {
	}

	// ===================================================

	public static final class direction {
		public static final int	TOP		= 1 << 0;
		public static final int	LEFT	= 1 << 1;
		public static final int	RIGHT	= 1 << 2;
		public static final int	BOTTOM	= 1 << 3;
	}

	public static final class orientation {
		public static final int	HORIZONTAL	= 1;
		public static final int	VERTICAL	= 2;
		public static final int	LANDSCAPE	= 1;
		public static final int	PORTRAIT	= 2;
	}

	public static final class resolutions {
		/**
		 * This mode keep extractly the width and height of devide for UI
		 */
		public static final byte	MULTI_RESOLUTION_MODE		= 0;
		/**
		 * This mode keep the UI width = 500 and height = 800 (in portrait) and
		 * (800,500) for landscape
		 */
		public static final byte	FIXED_RESOLUTION_MODE		= 1;
		/**
		 * This mode keep the UI height = 800 but still keep the screen ratio by
		 * multi UI height with (witdh/height ratio)
		 */
		public static final byte	FIXED_MULTI_RESOLUTION_MODE	= 2;
		/**
		 * Whatever happen the UI width and height wont change only the
		 * orientaion change
		 */
		public static final byte	MANUAL_RESOLUTION_MODE		= 3;
	}

	public static final class screen {
		/**
		 * Set new screen and call destroy old screen
		 */
		public static final int	RELEASE	= 1;

		/**
		 * Set new screen and call pause old screen
		 */
		public static final int	HIDE	= 2;
	}

	public static final class toast {
		public static final int	LENGTH_LONG		= 1;
		public static final int	LENGTH_SHORT	= 0;
	}

	/**
	 * 
	 * @FileName: E.java
	 * @CreateOn: Sep 26, 2012 - 11:17:32 AM
	 * @Author: TrungNT
	 */
	public static final class animation {
		public static final int	NORMAL			= 0;
		public static final int	REVERSED		= 1;
		public static final int	LOOP			= 2;
		public static final int	LOOP_REVERSED	= 3;
		public static final int	LOOP_PINGPONG	= 4;
		public static final int	LOOP_RANDOM		= 5;
	}
}
