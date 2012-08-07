/*
 * SpriteProcessor.cpp
 *
 *  Created on: Aug 07, 2012
 *      Author: trung
 */

#include "SpriteProcessor.h"

// inline method

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

// main method

void getNearestSprite(int* result,int numberOfResult,float* origin,float* list,int size){
	if(numberOfResult > size/2){
		for(int  i = 0;i < numberOfResult;i++)
			result[i] = -1;
		return;
	}

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
	if(numberOfResult > size/2){
		for(int  i = 0;i < numberOfResult;i++)
			result[i] = -1;
		return;
	}

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


