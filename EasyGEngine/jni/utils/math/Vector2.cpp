/*
 * Vector2.cpp
 *
 *  Created on: Jul 13, 2012
 *      Author: trung
 */
#include "Vector2.h"

Vector2::Vector2(){
	x = 0;
	y = 0;
}

Vector2::Vector2(float x,float y){
	this->x = x;
	this->y = y;
}

Vector2 Vector2::set(Vector2 v){
	this->x = v.x;
	this->y = v.y;
	return *this;
}

Vector2 Vector2::set(float x,float y){
	this->x = x;
	this->y = y;
	return *this;
}

//	Operator

Vector2 Vector2::sub(float x,float y){
	this->x -= x;
	this->y -= y;
	return *this;
}

Vector2 Vector2::add(float x,float y){
	this->x += x;
	this->y += y;
	return *this;
}

Vector2 Vector2::nor(){
	float length = len();
	if(length != 0){
		this->x /= length;
		this->y /= length;
	}
	return *this;
}

float Vector2::dot(Vector2 v){
	return x * v.x + y * v.y;
}

Vector2 Vector2::mul(float scalar){
	x *= scalar;
	y *= scalar;
	return *this;
}

float Vector2::dst (Vector2 v) {
	float x_d = v.x - x;
	float y_d = v.y - y;
	return sqrt(x_d * x_d + y_d * y_d);
}

float Vector2::crs(Vector2 v){
	return this->x*v.y - this->y*v.x;
}

//

Vector2 Vector2::sub(Vector2 v){
	this->x -= v.x;
	this->y -= v.y;
	return *this;
}

Vector2 Vector2::add(Vector2 v){
	this->x += v.x;
	this->y += v.y;
	return *this;
}


float Vector2::dot(float x,float y){
	return this->x * x + this->y * y;
}

float Vector2::dst (float x,float y) {
	float x_d = x - this->x;
	float y_d = y - this->y;
	return sqrt(x_d * x_d + y_d * y_d);
}

float Vector2::crs(float x,float y){
	return this->x*y - this->y*x;
}

float Vector2::len(){
	return sqrt(x*x+y*y);
}

float Vector2::len2(){
	return x*x+y*y;
}
