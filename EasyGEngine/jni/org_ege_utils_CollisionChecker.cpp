/*
 * org_ege_test_CollisionChecker.cpp
 *
 *  Created on: Jul 23, 2012
 *      Author: trung
 */

#include "org_ege_utils_CollisionChecker.h"

JNIEXPORT void JNICALL Java_org_ege_utils_CollisionChecker_setGridData
  (JNIEnv *env, jclass clazz, jint boundWidth, jint boundHeight, jint maxCol, jint maxRow){
	setGridData(boundWidth,boundHeight,maxCol,maxRow);
}

JNIEXPORT void Java_org_ege_utils_CollisionChecker_project___3FFF
  (JNIEnv *env, jclass clazz, jfloatArray result, jfloat x, jfloat y){
	float* array = (float*)env->GetPrimitiveArrayCritical(result,0);
	mGrid.project(&tmp,x,y);
	*array = tmp.x;
	*(array+1) = tmp.y;
	env->ReleasePrimitiveArrayCritical(result,array,0);
}

JNIEXPORT jint JNICALL Java_org_ege_utils_CollisionChecker_project__FF
  (JNIEnv *env, jclass clazz, jfloat x, jfloat y){
	return mGrid.project(x,y);
}

JNIEXPORT void JNICALL Java_org_ege_utils_CollisionChecker_unproject___3FII
  (JNIEnv *env, jclass clazz, jfloatArray result, jint col, jint row){
	float* array = (float*)env->GetPrimitiveArrayCritical(result,0);
	mGrid.unproject(&tmp,col,row);
	*array = tmp.x;
	*(array+1)  = tmp.y;
	env->ReleasePrimitiveArrayCritical(result,array,0);
}

JNIEXPORT void JNICALL Java_org_ege_utils_CollisionChecker_unproject___3FI
  (JNIEnv *env, jclass clazz, jfloatArray result, jint id){
	float* array = (float*)env->GetPrimitiveArrayCritical(result,0);
	mGrid.unproject(&tmp,id);
	*array = tmp.x;
	*(array+1)  = tmp.y;
	env->ReleasePrimitiveArrayCritical(result,array,0);
}

JNIEXPORT void JNICALL Java_org_ege_utils_CollisionChecker_toGridPos
  (JNIEnv *env, jclass clazz, jfloatArray result, jint id){
	float* array = (float*)env->GetPrimitiveArrayCritical(result,0);
	mGrid.toGridPos(&tmp,id);
	*array = tmp.x;
	*(array+1)  = tmp.y;
	env->ReleasePrimitiveArrayCritical(result,array,0);
}

JNIEXPORT jint JNICALL Java_org_ege_utils_CollisionChecker_toMappingId
  (JNIEnv *env, jclass clazz, jint col, jint row){
	return mGrid.toMappingId(col,row);
}

JNIEXPORT jint JNICALL Java_org_ege_utils_CollisionChecker_checkCollision
  (JNIEnv *env, jclass clazz, jintArray result, jfloatArray sprite1, jint size1, jfloatArray sprite2, jint size2){
	int* array = (int*)env->GetPrimitiveArrayCritical(result,0);
	float* array1 = (float*)env->GetPrimitiveArrayCritical(sprite1,0);
	float* array2 = (float*)env->GetPrimitiveArrayCritical(sprite2,0);
	int size = checkCollision(array,array1,size1,array2,size2);
	env->ReleasePrimitiveArrayCritical(result,array,0);
	env->ReleasePrimitiveArrayCritical(sprite1,array1,10);
	env->ReleasePrimitiveArrayCritical(sprite2,array2,0);
	return size;
}
