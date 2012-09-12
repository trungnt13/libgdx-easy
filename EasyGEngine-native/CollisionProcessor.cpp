/*
 * CollisionProcessor.cpp
 *
 *  Created on: Jul 13, 2012
 *      Author: trung
 */

#include "CollisionProcessor.h"


// inline method

inline int getId(float centerX,float centerY){
	int id = mGrid.project(centerX,centerY);
	return mData.getOrder(id);
}

inline void fast4BytesCopy(int* dest,int* src,int num){
	memcpy(dest,src,num*4);
}

inline void fast4BytesCopy(float* dest,float* src,int num){
	memcpy(dest,src,num*4);
}

inline void fastOverlap4BytesCopy(int* dest,int* src, int num){
	memmove(dest,src,4*num);
}

inline void fastOverlap4BytesCopy(float* dest,float* src, int num){
	memmove(dest,src,4*num);
}

inline bool isCollision(float* rect1,float* rect2){
	return !( (rect1[0] > (rect2[0] + rect2[2])) ||
			  ((rect1[0] + rect1[2]) < rect2[0]) ||
			  (rect1[1] > (rect2[1] + rect2[3])) ||
			  ((rect1[1] + rect1[3]) < rect2[1])
			);
}

// public method

void setGridData(int boundWIdth,int boundHeight,int maxCol,int maxRow){
	mGrid.setBounds(boundWIdth,boundHeight,maxCol,maxRow);
	mData.generate(mGrid);
}

int checkCollision(int* result,float* spriteList1,int size1,float* spriteList2,int size2){
	int resultSize = 0;
	int length = mData.length;
	// init first list data
	IntArray list1[length];

	// init second list data
	IntArray list2[length];

	/*	-------------------------------------	*/

	float centerX;
	float centerY;
	int id ;


	// generate first list data
	for(int i =0 ;i < size1;i += 4){
		centerX = spriteList1[i] + spriteList1[i+2]/2;
		centerY = spriteList1[i+1] + spriteList1[i+3]/2;
		id = getId(centerX,centerY);
		if(id >= 0)
			list1[id].add(i);
	}

	// generate second list data
	for(int i =0 ;i < size2;i += 4){
		centerX = spriteList2[i] + spriteList2[i+2]/2;
		centerY = spriteList2[i+1] + spriteList2[i+3]/2;
		id = getId(centerX,centerY);
		if(id >= 0)
			list2[id].add(i);
	}

	/*	-------------------------------------	*/

	int id1;
	int id2;

	if(size2  == 0){
		int listSize;
		for(int l = 0; l < length;l++){
			listSize = list1[l].size;
			for (int i= 0; i < listSize;i++){
				id1 = list1[l].get(i);
				fast4BytesCopy(process1,spriteList1+id1,4);
				for (int j = i + 1 ; j < listSize;j++){
					id2 = list1[l].get(j);
					fast4BytesCopy(process2,spriteList1+id2,4);
					if(isCollision(process1,process2)){
						if(resultSize < MAX_COLLISION -1){
							*(result+resultSize) = id1/4;
							*(result+resultSize+1) = id2/4;
							resultSize += 2;
						}
					}
				}
			}
		}
	}else{
		int list1Size;
		int list2Size;
		for(int l = 0; l < length;l++){
			list1Size = list1[l].size;
			list2Size = list2[l].size;
			for (int i= 0; i < list1Size;i++){
				id1 = list1[l].get(i);
				fast4BytesCopy(process1,spriteList1+id1,4);
				for (int j = 0; j < list2Size;j++){
					id2 = list2[l].get(j);
					fast4BytesCopy(process2,spriteList2+id2,4);
					if(isCollision(process1,process2)){
						if(resultSize < MAX_COLLISION -1){
							*(result+resultSize) = id1/4;
							*(result+resultSize+1) = id2/4;
							resultSize += 2;
						}
					}
				}
			}
		}
	}
	return resultSize;
}

