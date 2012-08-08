/*
 * SpriteProcessor.cpp
 *
 *  Created on: Aug 07, 2012
 *      Author: trung
 */

#include "SpriteProcessor.h"

// inline method
inline bool checkProcess(int j,float originX,float originY,float x,float y){
	switch (j){
		case 0:
			return ((TOP)checker).check(originX,originY,x,y);
		case 1:
			return ((LEFT)checker).check(originX,originY,x,y);
		case 2:
			return ((RIGHT)checker).check(originX,originY,x,y);
		case 3:
			return ((BOTTOM)checker).check(originX,originY,x,y);
	}
	return 0;
}

inline void selectionSort(int* result,float* distance,int size){
	int jMin;
	float tmpF;
	int tmpI;

	for(int i = 0;i < size-1;i++){
		jMin = i;
		for(int j= i+1;j < size;j++){
			if(distance[j] < distance[jMin])
				jMin = j;
		}
		//swap i vs jMin
		if(jMin != i){
			tmpI = result[i];
			result[i] = result[jMin];
			result[jMin] = tmpI;

			tmpF = distance[i];
			distance[i] = distance[jMin];
			distance[jMin] = tmpF;
		}
	}
}

// direction method
void getNearestSprite(int* result,int numberOfResult,int direction,float* origin,float* list,int size){
	for(int  i = 0;i < numberOfResult;i++)
		result[i] = -1;

	if(numberOfResult > size/2)
		return;

	// initialize information
	float originX = origin[0];
	float originY = origin[1];
	int number = size/2;
	float distance[number];
	int order[number];

	// process
	for(int i = 0;i < number;i++){
		order[i] = i;
		distance[i] = calDistance(originX,originY,list[2*i],list[2*i+1]);
	}
	selectionSort(order,distance,number);

	// save the result
	int check = 1;
	int count = 0;
	for(int i =0 ;i < number;i++){
		for(int j = 0;j < 4;j++){
			if( (direction >> j) % 2 != 0)
				check &= checkProcess(j,originX,originY,list[2*i],list[2*i+1]);
		}

		if(check){
			result[count++] = order[i];
			if(count >= numberOfResult)
				return;
		}else
			check = 1;
	}
}

void getNearestSprite(int* result,int numberOfResult,int direction,float originX,float originY,float* list,int size){
	for(int  i = 0;i < numberOfResult;i++)
		result[i] = -1;

	if(numberOfResult > size/2)
		return;

	// initialize information
	int number = size/2;
	float distance[number];
	int order[number];

	// process
	for(int i = 0;i < number;i++){
		order[i] = i;
		distance[i] = calDistance(originX,originY,list[2*i],list[2*i+1]);
	}
	selectionSort(order,distance,number);

	// save the result
	int check = 1;
	int count = 0;
	for(int i =0 ;i < number;i++){
		for(int j = 0;j < 4;j++){
			if( (direction >> j) % 2 != 0)
				check &= checkProcess(j,originX,originY,list[2*i],list[2*i+1]);
		}

		if(check){
			result[count++] = order[i];
			printf("origin %f ",originY);
			printf("sprite %f \n",list[2*i+1]);
			if(count >= numberOfResult)
				return;
		}else
			check = 1;
	}
}


// no direction method

void getNearestSprite(int* result,int numberOfResult,float* origin,float* list,int size){
	for(int  i = 0;i < numberOfResult;i++)
		result[i] = -1;

	if(numberOfResult > size/2)
		return;

	// initialize information
	float originX = origin[0];
	float originY = origin[1];
	int number = size/2;
	float distance[number];
	int order[number];

	// process
	for(int i = 0;i < number;i++){
		order[i] = i;
		distance[i] = calDistance(originX,originY,list[2*i],list[2*i+1]);
	}
	selectionSort(order,distance,number);

	for(int i =0 ;i < numberOfResult;i++)
		result[i] = order[i];
}

void getNearestSprite(int* result,int numberOfResult,float originX,float originY,float* list,int size){
	for(int  i = 0;i < numberOfResult;i++)
		result[i] = -1;

	if(numberOfResult > size/2)
		return;

	// initialize information
	int number = size/2;
	float distance[number];
	int order[number];

	// process
	for(int i = 0;i < number;i++){
		order[i] = i;
		distance[i] = calDistance(originX,originY,list[2*i],list[2*i+1]);
	}
	selectionSort(order,distance,number);

	for(int i =0 ;i < numberOfResult;i++)
		result[i] = order[i];
}


