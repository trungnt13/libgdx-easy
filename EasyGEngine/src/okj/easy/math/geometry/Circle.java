//package okj.easy.math.geometry;
//
//import java.io.Serializable;
//
//import okj.easy.math.Function;
//import okj.easy.math.eMath;
//
//import com.badlogic.gdx.math.Vector2;
//
///**
// * A convenient 2D circle class.
// * @author mzechner
// * @author trung
// */
//
//public class Circle implements Serializable{
//	
//	public float x;
//	public float y;
//	public float radius;
//	
//	public Circle (){
//		
//	}
//
//	public Circle (Vector2 firstPoint,Vector2 midPoint,Vector2 endPoint){
//		Vector2 tmp = new Vector2();
//		float x = (firstPoint.x + midPoint.x)/2;
//		float y = (firstPoint.y + midPoint.y)/2;
//		
//		tmp.set(firstPoint.x-midPoint.x, firstPoint.y-midPoint.y);
//		Line first = new Line(x, y, tmp);
//		
//		x = (endPoint.x + midPoint.x)/2;
//		y = (endPoint.y + midPoint.y)/2;
//		tmp.set(endPoint.x-midPoint.x, endPoint.y-midPoint.y);
//		Line second = new Line(x, y, tmp);
//		
//		
//		tmp = first.calCrossPoint(second);
//		if(tmp != null){
//			this.x = tmp.x;
//			this.y = tmp.y;
//			radius = eMath.calModule(tmp, midPoint);
//		}else{
//			this.x = 0;
//			this.y = 0;
//			radius = 0;
//		}
//	}
//	
//	public Circle(float x, float y, float radius) {
//		set(x, y, radius);
//	}
//	
//	public Circle(Vector2 position, float radius) {
//		set(position.x, position.y, radius);
//	}
//
//	public void set (float x, float y, float radius) {
//		this.x = x;
//		this.y = y;
//		this.radius = radius;
//	}
//
//	public boolean contains(float x, float y) {
//		x = this.x - x;
//		y = this.y - y;
//		return x*x + y*y <= radius * radius;
//	}
//	
//	public boolean contains(Vector2 point) {
//		float x = this.x - point.x;
//		float y = this.y - point.y;
//		return x*x + y*y <= radius * radius;
//	}
//	
//	 public boolean intersects(Circle c) {
//	     return eMath.Module(new Vector2(x, y),new Vector2(c.x, c.y)) <= radius + c.radius;
//	 }
//	 
//	 public double area()      { return Math.PI * radius * radius; }
//	 
//	 public double perimeter() { return 2 * Math.PI * radius;      }
//	 
//	 public boolean overlaps(Circle circle)
//	 {
//		 float a = this.radius + circle.radius;
//	     final double dx = this.x - circle.x;
//	     final double dy = this.y - circle.y;
//	     return a * a > (dx * dx + dy * dy);
//	 }
//	 
//	 public double getTangentAngleAt(Vector2 point){
//		 if(point.y > y){
//			 double derivative = (f(point.x + Function.MIN, 1) - f(point.x - Function.MIN, 1))/ (2 * Function.MIN);
//			 derivative =  Math.atan(derivative);
//			 return derivative;
//		 }else{
//			 double derivative = (f(point.x + Function.MIN, -1) - f(point.x - Function.MIN, -1))/ (2 * Function.MIN);
//			 derivative =  Math.atan(derivative);
//			 return derivative;	
//		 }
//			 
//	 }
//	 
//	 public double d(Vector2 point){
//		 if(point.y > y)
//			 return (f(point.x + Function.MIN, 1) - f(point.x - Function.MIN, 1))/ (2 * Function.MIN);
//		 return (f(point.x + Function.MIN, -1) - f(point.x - Function.MIN, -1))/ (2 * Function.MIN);
//	 }
//	 
//	 public double f(double X,float ySign){
//		 if(ySign > 0)
//			 return  (Math.sqrt(radius*radius - (X - x ) * (X - x)) + y);
//		 else
//			 return  (-Math.sqrt(radius*radius - (X - x ) * (X - x)) + y);
//	 }
//	 
//	 public boolean isOnTheCircle(Vector2 point){
//		 float a = x;
//		 float b = y;
//		 float r2 = radius * radius;
//		 if(((point.x -a)*(point.x -a) + (point.y-b)*(point.y-b) - r2 ) != 0)
//			 return false;
//		 return true;
//	 }
//}

