/*
 * CollisionProcessor.h
 *
 *  Created on: Jul 14, 2012
 *      Author: trung
 */

#ifndef COLLISIONPROCESSOR_H_
#define COLLISIONPROCESSOR_H_

#include <Math/eMath.h>

static  Math2D::Grid mGrid(0,0);
static  Math2D::GridDataStorage mData;

static const int MAX_COLLISION = 20000;

static float process1[4];
static float process2[4];

void setGridData(int,int,int,int);

int checkCollision(int*,float*,int,float*,int);

#endif /* COLLISIONPROCESSOR_H_ */
