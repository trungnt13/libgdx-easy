/*
 * SpriteProcessor.h
 *
 *  Created on: Aug 07, 2012
 *      Author: trung
 */

#ifndef  SPRITEPROCESSOR_H_
#define  SPRITEPROCESSOR_H_

#include "utils.h"

class TOP {
public:
	bool check(float originX,float originY,float x,float y){
		return y > originY;
	}
};

class LEFT{
public:
	bool check(float originX,float originY,float x,float y){
		return x < originX;
	}
};

class RIGHT {
public:
	bool check(float originX,float originY,float x,float y){
		return x > originX;
	}
};

class BOTTOM{
public:
	bool check(float originX,float originY,float x,float y){
		return y < originY;
	}
};

class Checker : public TOP,
				 public LEFT,
				 public RIGHT,
				 public BOTTOM{
public:
	bool check(float,float,float,float);
};

// direction method

void getNearestSprite(int*,int,int,float*,float*,int);

void getNearestSprite(int*,int,int,float,float,float*,int);

// no direction method

void getNearestSprite(int*,int,float*,float*,int);

void getNearestSprite(int*,int,float,float,float*,int);

#endif /* SPRITEPROCESSOR_H_ */
