/*
* Vector2.h
*
*  Created on: Jul 13, 2012
*      Author: trung
*/

#ifndef VECTOR2_H_
#define VECTOR2_H_

#include <math.h>

namespace Math2D{
	/************************************************************************/
	/* Simple vector 2 can use to represent point or vector				    */
	/************************************************************************/
	class Vector2 {
	public:
		float x;
		float y;

		Vector2();
		Vector2(float,float);

		Vector2 set(float,float);
		Vector2 set(Vector2);

		Vector2 sub(Vector2);
		Vector2 add(Vector2);
		Vector2 nor();
		float dot(Vector2);
		float dst(Vector2);
		float dst2(Vector2);
		float crs(Vector2);
		Vector2 mul (float);

		Vector2 sub(float,float);
		Vector2 add(float,float);
		float dot(float,float);
		float dst(float,float);
		float dst2(float,float);
		float crs(float,float);

		float len();
		float len2();
	};

	static Vector2 tmp;
	static const Vector2 X(1,0);
	static const Vector2 Y(0,1);

	/************************************************************************/
	/* Minimum translation vector use for overlap polygon                   */
	/************************************************************************/
	class MinimumTranslationVector {
	public:
		Vector2 normal;
		float depth;
		MinimumTranslationVector(){
			depth = 0;
		}
	};
}// end of namespace
#endif /* VECTOR2_H_ */
