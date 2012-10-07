/*
*  okj_easy_graphics_graphics2d_NSpriteDef.cpp
*
*  Created on: 10/6/2012 8:52:56 PM
*      Author: TrungNT
*/

#include "okj_easy_graphics_graphics2d_NSpriteDef.h"

using namespace Entity2D;
using namespace Math2D;

/*
* Class:     okj_easy_graphics_graphics2d_NSpriteDef
* Method:    equal
* Signature: (JJ)Z
*/
JNIEXPORT jboolean JNICALL Java_okj_easy_graphics_graphics2d_NSpriteDef_equal
	(JNIEnv *env, jobject obj, jlong spriteDef1, jlong spriteDef2){
		SpriteDef *def1 = (SpriteDef*)spriteDef1;
		SpriteDef *def2 = (SpriteDef*)spriteDef2;

		return def1->equal(def2);
}

/*
* Class:     okj_easy_graphics_graphics2d_NSpriteDef
* Method:    addBounding
* Signature: (J[FI[II)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NSpriteDef_addBounding
	(JNIEnv *env, jobject obj, jlong spriteDef, jfloatArray vertices, jint verticesSize, jintArray noIndex, jint noIndexSize){
		SpriteDef *def = (SpriteDef*)spriteDef;
		float *verts = (float*)env->GetPrimitiveArrayCritical(vertices,0);
		int *index = (int*)env->GetPrimitiveArrayCritical(noIndex,0);

		Polygon *pol = new Polygon(verts,verticesSize);
		pol->setNoIndex(index,noIndexSize);
		def->addBounding(pol);

		env->ReleasePrimitiveArrayCritical(vertices,verts,0);
		env->ReleasePrimitiveArrayCritical(noIndex,index,0);
}

/*
* Class:     okj_easy_graphics_graphics2d_NSpriteDef
* Method:    removeBounding
* Signature: (JI)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NSpriteDef_removeBounding
	(JNIEnv *env, jobject obj, jlong spriteDef, jint index){
		SpriteDef *def = (SpriteDef*)spriteDef;
		def->removeBounding(index);
}

/*
* Class:     okj_easy_graphics_graphics2d_NSpriteDef
* Method:    size
* Signature: (J)I
*/
JNIEXPORT jint JNICALL Java_okj_easy_graphics_graphics2d_NSpriteDef_size
	(JNIEnv *env, jobject obj, jlong spriteDef){
		SpriteDef *def = (SpriteDef*)spriteDef;
		return def->size();
}

/*
* Class:     okj_easy_graphics_graphics2d_NSpriteDef
* Method:    clearBounding
* Signature: (J)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NSpriteDef_clearBounding
	(JNIEnv *env, jobject obj, jlong spriteDef){
		SpriteDef *def = (SpriteDef*)spriteDef;
		def->clearBounding();
}