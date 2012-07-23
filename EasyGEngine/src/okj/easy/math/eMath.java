package okj.easy.math;


import okj.easy.math.geometry.Line;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public final class eMath {
	public static final int INFINITE = -10000;
	
	public static final Vector2 UNIT_VECTOR_Ox = new Vector2(1, 0);
	public static final Vector2 UNIT_VECTOR_0y = new Vector2(0, 1);

	public static final int COUNTER_CLOCKWISE = 1;
	public static final int CLOCKWISE = 2;
	
	private static final Vector2 v1 = new Vector2();
	private static final Vector2 v2 = new Vector2();
	/**
	 * Noone can instance this class
	 */
	private eMath() {
		
	}
	
	/***************************************************************************
	 * 
	 ***************************************************************************/
	public static float calVectorAngle(Vector2 lineStart,Vector2 lineEnd){
		if(lineEnd.x == lineStart.x){
			if(lineEnd.y > lineStart.y)
				return 90;
			if (lineEnd.y < lineStart.y)
				return 270;
		}
		
		if(lineEnd.y == lineStart.y){
			if(lineStart.x < lineEnd.x)
				return 360;
			if(lineStart.x > lineEnd.x)
				return 180;
		}
		
		if(lineEnd.x > lineStart.x)
			return (float) (180.0f/MathUtils.PI* Math.atan((lineEnd.y - lineStart.y)/(lineEnd.x - lineStart.x)));
		if(lineEnd.x < lineStart.x)
			return (float) (180.0f/MathUtils.PI* Math.atan((lineEnd.y - lineStart.y)/(lineEnd.x - lineStart.x))) - 180;
		
		return 0;
	}
	
	public static float calVectorAngle(float x,float y,float x1,float y1){
		if(x1 == x){
			if(y1 > y)
				return 90;
			if (y1 < y)
				return 270;
		}
		
		if(y1 == y){
			if(x < x1)
				return 360;
			if(x > x1)
				return 180;
		}
		
		if(x1 > x)
			return (float) (180.0f/MathUtils.PI* Math.atan((y1 - y)/(x1 - x)));
		if(x1 < x)
			return (float) (180.0f/MathUtils.PI* Math.atan((y1 - y)/(x1 - x))) - 180;
		
		return 0;
	}
	
	public static float calModule(Vector2 src,Vector2 dst){
		return (float) Math.sqrt((src.x-dst.x)*(src.x-dst.x) + (src.y-dst.y)*(src.y-dst.y));
	}
	
	public static float calModule(float x1,float y1,float x2,float y2){
		return (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	public static Vector2 calMiddle(Vector2 v1,Vector2 v2){
		return new Vector2( (v1.x+v2.x)/2, (v1.y+v2.y)/2);
	}
	
	public static float getXinLine(int y,Vector2 src,Vector2 dst){
		float delta = ( (float)(src.x-dst.x) / (float)(src.y-dst.y) );
		return (float)(delta* (y-src.y)+ src.x);
	}
	
	public static float getYinLine(float x,Vector2 src,Vector2 dst){
		float delta = ( (float)(src.y - dst.y) / (float)(src.x-dst.x) );
		return (float)(delta * (x - src.x)+src.y);
	}
	
	public static float getXinLine(float y,Vector2 src,Vector2 dst){
		float delta = ( (float)(src.x-dst.x) / (float)(src.y-dst.y) );
		return (int)(delta* (y-src.y)+ src.x);
	}
	
	
	/**
	 * Calculate angle between two line, (x,y)-(x1-y1)  and (x,y)-(x2,y2)
	 * @param x 
	 * @param y
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return the angle in degree
	 */
	public static float CalAngle(float x,float y,float x1, float y1,float x2, float y2){
		float a;
		float b;
		float c;
		
		Vector2 BASE = new Vector2(x, y);
		Vector2 FP = new Vector2(x1, y1);
		Vector2 SP = new Vector2(x2, y2);
		
		a = calModule(BASE, FP);
		b = calModule(BASE, SP);
		c = calModule(FP, SP);
		
		float cos;
		
		cos = (a*a + b*b - c*c)/(2*a*b);
		
		return (float) (Math.acos(cos) * 180 / Math.PI);	
	}
	
	public static Vector2 getCrossPointOf2Line(Line line,Line line1){
		Vector2 n= line.getNormalVector();
		float a = n.x;
		float b = n.y;
		float c = line.getConstantOfLine();
		
		Vector2 n1 = line1.getNormalVector();
		float a1 = n1.x;
		float b1 = n1.y;
		float c1 = line1.getConstantOfLine();
		
		Gdx.app.log("shit", "" + a +" " + b + " "  + c+
					        "  " + a1+ " " + b1 + " "  + c1);
		if(a*b1 == b*a1)
			return null;
		else 
			return new Vector2( (b*c1 - c*b1)/(b1*a - b*a1),
							    (a1*c - c1*a)/(b1*a - b*a1) );
	}
	
	/***************************************************************************
	 * 
	 ***************************************************************************/
	
	public static Vector2 solveTheQuadricFunction(float a,float b,float c){
		if(a == 0)
			return new Vector2(-c / b, -c / b);
		else{
			return new Vector2((float) (-b + Math.sqrt(b*b - 4*a*c) ) / (2*a), 
							 	(float) (-b - Math.sqrt(b*b - 4*a*c) ) / (2*a));
		}
			
	}
	
	/**
	 * Return 1 if the srcAngle should increase to reach dstAngle, otherwise -1
	 * @param srcANGLE
	 * @param dstANGLE
	 * @return
	 */
	public static int calShortestPathBetweenTwoAngle(float srcANGLE,float dstANGLE){
		if(dstANGLE <= 180){
			if(srcANGLE > 180){
				float x1 = 360 - srcANGLE;
				float y1= dstANGLE;
				
				float x2 = srcANGLE - 180;
				float y2 = 180-dstANGLE;
				if((x1+y1) >= (x2+y2)){
					return -1;
				}else
					return 1;
			}else {
				if(srcANGLE < dstANGLE)
					return 1;
				else 
					return -1;
			}
		}else{
			if(srcANGLE > 180){
				if(srcANGLE < dstANGLE)
					return 1;
				else 
					return -1;
			}else {
				float x1 = 360 - dstANGLE;
				float y1 = srcANGLE;
				
				float x2 = dstANGLE - 180;
				float y2 = 180-srcANGLE;
				if((x1+y1) >= (x2+y2)){
					return 1;
				}else 
					return -1;
			}
		}
	}
	
	public static void swap(float a,float b){
		float tmp = a;
		a = b;
		b = tmp;
	}
	
	public static float minDivisibleNumber(float yourDividend,float divisor){
		return yourDividend - (yourDividend % divisor);
	}
	
	public static float maxDivisibleNumber(float yourDividend,float divisor){
		return yourDividend + (divisor  - (yourDividend % divisor));
	}
	
	/**
	 * This method is more special if your number is power of two
	 * it will return a number >= 0 ,otherwise it will return < 0
	 * @param number
	 * @return
	 */
	public static int isPowerOfTwo(int number){
		if(!MathUtils.isPowerOfTwo(number))
			return -1;
		for(int i = 0;i < 32;i++){
			if((number>>i) == 0)
				return i-1;
		}
		return 0;
	}
}
