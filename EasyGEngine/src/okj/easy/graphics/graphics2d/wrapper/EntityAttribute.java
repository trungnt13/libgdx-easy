package okj.easy.graphics.graphics2d.wrapper;



public final class EntityAttribute {
	
	public static final class Movement{
		public static final byte ANGLE_MODE = 0;
		public static final byte POSITION_MODE= 1;
	}
	
	public static final class Direction{
		public static final String UP = "+0+1";
		public static final String DOWN = "+0-1";
		public static final String LEFT = "-1+0";
		public static final String RIGHT = "+1+0";
		public static final String TOP_LEFT = "-1+1";
		public static final String DOWN_LEFT = "-1-1";
		public static final String TOP_RIGHT = "+1+1";
		public static final String DOWN_RIGHT = "+1-1";
		public static final String SILENT = "+0+0";
	}


	public final boolean isAnimation;
	
	public final boolean isDirectionTrace;

	public final byte movementMode;
	
	
	public EntityAttribute (boolean isAnimation,boolean isDirectionTrace, byte MovementMode) {
		this.isAnimation = isAnimation;
		this.isDirectionTrace = isDirectionTrace;
		this.movementMode = MovementMode;
	}
	
}
