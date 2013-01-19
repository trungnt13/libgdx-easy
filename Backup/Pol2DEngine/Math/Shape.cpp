/*
*  Polygon.cpp
*
*  Created on: 10/1/2012 11:09:30 PM
*      Author: TrungNT
*/

#include "Shape.h"
#include <string.h>

using namespace Math2D;

/************************************************************************/
/* Polygon                                                              */
/************************************************************************/

Polygon::Polygon(float *vertices,int sizeOfVertices){
	this->localVertices = new float[sizeOfVertices];
	memcpy(localVertices,vertices,sizeOfVertices*sizeof(float));
	this->verticesSize = sizeOfVertices;

	

	noIndex = NULL;
	noIndexSize = 0;
}

Polygon::~Polygon(){
	delete localVertices;
	delete noIndex;
}


float* Polygon::getVertices(){
	return localVertices;
}

int Polygon::getSize(){
	return verticesSize;
}

void Polygon::setNoIndex(int *noIndex,int noIndexSize){
	if(this->noIndex != NULL)
		delete[] noIndex;

	this->noIndex = new int[noIndexSize];
	memcpy(this->noIndex,noIndex,noIndexSize*sizeof(int));
	this->noIndexSize = noIndexSize;
}

int* Polygon::getNoIndex(){
	return noIndex;
}

int Polygon::getNoIndexSize(){
	return noIndexSize;
}

bool Polygon::equal(Polygon* pol){
	if(verticesSize != pol->verticesSize)
		return false;

	float *local = pol->localVertices;

	for(int i = 0 ;i < verticesSize;i++)
		if(localVertices[i] != local[i])
			return false;
	return true;
}