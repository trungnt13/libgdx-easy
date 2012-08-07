/*
 * SpriteProcessor.cpp
 *
 *  Created on: Aug 07, 2012
 *      Author: trung
 */

#include "SpriteProcessor.h"

// inline method

inline float calDistance(float x,float y,float x1,float y1){
	return sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
}

inline void selectionSort(int* result,float* distance,int size){
	int jMin;
	float tmpF;
	int tmpI;

	for(int i = 0;i < size-1;i++){
		jMin = i;
		for(int j= i+1;j < size;j++){
			if(distance[i] < distance[jMin])
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

// main method

void getNearestSprite(int* result,int numberOfResult,float* origin,float* list,int size){
	if(numberOfResult > size/2){
		for(int  i = 0;i < numberOfResult;i++)
			result[i] = -1;
		return;
	}

	// the smallest distance
	float distance[numberOfResult];

	// initialize information
	float originX = origin[0];
	float originY = origin[1];

	for(int i = 0;i < numberOfResult;i++){
		result[i] = i;
		distance[i] = calDistance(originX,originY,list[2*i],list[2*i+1]);
	}
	selectionSort(result,distance,numberOfResult);

	// process
	float tmp;
	for(int i = numberOfResult*2;i < size;i+=2){
		tmp = calDistance(originX,originY,list[2*i],list[2*i+1]);
		for(int j = 0;j < numberOfResult;j++){
			if(tmp < distance[j]){
				distance[j] = tmp;
				result[j] = i/2;
				j = numberOfResult;
			}
		}
	}
}

void getNearestSprite(int* result,int numberOfResult,float originX,float originY,float* list,int size){
	if(numberOfResult > size/2){
		for(int  i = 0;i < numberOfResult;i++)
			result[i] = -1;
		return;
	}

	// the smallest distance
	float distance[numberOfResult];

	// initialize information
	for(int i = 0;i < numberOfResult;i++){
		result[i] = i;
		distance[i] = calDistance(originX,originY,list[2*i],list[2*i+1]);
	}
	selectionSort(result,distance,numberOfResult);

	// process
	float tmp;
	for(int i = numberOfResult*2;i < size;i+=2){
		tmp = calDistance(originX,originY,list[2*i],list[2*i+1]);
		for(int j = 0;j < numberOfResult;j++){
			if(tmp < distance[j]){
				distance[j] = tmp;
				result[j] = i/2;
				j = numberOfResult;
			}
		}
	}
}


