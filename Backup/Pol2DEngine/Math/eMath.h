/*
* eMath.h
*
*  Created on: Jul 13, 2012
*      Author: trung
*/

#ifndef EMATH_H_
#define EMATH_H_

/************************************************************************/
/* define const and method                                              */
/************************************************************************/
#define PI 3.1415927
#define lengthOf(a) ( sizeof ( a ) / sizeof ( *a ) )
#define toRadian(a) (a*PI/180)

#ifndef NULL
#ifdef __cplusplus
#define NULL    0
#else
#define NULL    ((void *)0)
#endif
#endif

//////////////////////////////////////////////////////////////////////////

#include <math.h>
#include "Vector2.h"
#include "Shape.h"
#include "GridSimulation.h"

/************************************************************************/
/* Method declearation                                                  */
/************************************************************************/
// primitive converter
template<class T>
T max(T value1,T value2){
	return (value1>value2 ? value1:value2);
}

template<class T>
T min(T a,T b){
	return (a > b? b:a);
}

float calDistance(float,float,float,float);

bool overlapConvexPolygons(float*,int,int*,int,float*,int,int*,int,Math2D::MinimumTranslationVector*);

bool overlapRectangle(float*,float*);


#endif /* EMATH_H_ */
