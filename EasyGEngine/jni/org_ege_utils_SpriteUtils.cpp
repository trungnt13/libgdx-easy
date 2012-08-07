/*
 * org_ege_test_SpriteUtils.cpp
 *
 *  Created on: Aug 07, 2012
 *      Author: trung
 */

#include "org_ege_utils_SpriteUtils.h"
#include "SpriteProcessor.h"

JNIEXPORT void JNICALL Java_org_ege_utils_SpriteUtils_getNearestSprite___3II_3F_3FI
  (JNIEnv *env, jclass clazz, jintArray resultSet, jint numberOfResult, jfloatArray originSprite, jfloatArray spriteList, jint spriteSize){
	int* result = (int*)env->GetPrimitiveArrayCritical(resultSet,0);
	float* origin = (float*)env->GetPrimitiveArrayCritical(originSprite,0);
	float* list = (float*)env->GetPrimitiveArrayCritical(spriteList,0);
	getNearestSprite(result,numberOfResult,origin,list,spriteSize);
	env->ReleasePrimitiveArrayCritical(resultSet,result,0);
	env->ReleasePrimitiveArrayCritical(originSprite,origin,0);
	env->ReleasePrimitiveArrayCritical(spriteList,list,0);
}


JNIEXPORT void JNICALL Java_org_ege_utils_SpriteUtils_getNearestSprite___3IIFF_3FI
  (JNIEnv *env, jclass clazz, jintArray resultSet, jint numberOfResult, jfloat originSpriteX, jfloat originSpriteY, jfloatArray spriteList, jint spriteSize){
	int* result = (int*)env->GetPrimitiveArrayCritical(resultSet,0);
	float* list = (float*)env->GetPrimitiveArrayCritical(spriteList,0);
	getNearestSprite(result,numberOfResult,originSpriteX,originSpriteY,list,spriteSize);
	env->ReleasePrimitiveArrayCritical(resultSet,result,0);
	env->ReleasePrimitiveArrayCritical(spriteList,list,0);
}
