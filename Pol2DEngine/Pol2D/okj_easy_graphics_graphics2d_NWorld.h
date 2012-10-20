/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include <Sprite/Pol2D.h>
/* Header for class okj_easy_graphics_graphics2d_NWorld */

#ifndef _Included_okj_easy_graphics_graphics2d_NWorld
#define _Included_okj_easy_graphics_graphics2d_NWorld
#ifdef __cplusplus
extern "C" {
#endif
	/*
	* Class:     okj_easy_graphics_graphics2d_NWorld
	* Method:    CreateWorld
	* Signature: ()J
	*/
	JNIEXPORT jlong JNICALL Java_okj_easy_graphics_graphics2d_NWorld_CreateWorld
		(JNIEnv *, jobject);

	/*
	* Class:     okj_easy_graphics_graphics2d_NWorld
	* Method:    DisposeWorld
	* Signature: ()V
	*/
	JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_DisposeWorld
		(JNIEnv *, jobject);

	/*
	* Class:     okj_easy_graphics_graphics2d_NWorld
	* Method:    CreateNSprite
	* Signature: ()J
	*/
	JNIEXPORT jlong JNICALL Java_okj_easy_graphics_graphics2d_NWorld_CreateNSprite
		(JNIEnv *, jobject);

	/*
	* Class:     okj_easy_graphics_graphics2d_NWorld
	* Method:    CreateManager
	* Signature: ()J
	*/
	JNIEXPORT jlong JNICALL Java_okj_easy_graphics_graphics2d_NWorld_CreateManager
		(JNIEnv *, jobject);

	/*
	* Class:     okj_easy_graphics_graphics2d_NWorld
	* Method:    DisposeManager
	* Signature: (J)V
	*/
	JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_DisposeManager
		(JNIEnv *, jobject, jlong);

	/*
	* Class:     okj_easy_graphics_graphics2d_NWorld
	* Method:    CreateSpriteDef
	* Signature: ()J
	*/
	JNIEXPORT jlong JNICALL Java_okj_easy_graphics_graphics2d_NWorld_CreateSpriteDef
		(JNIEnv *, jobject);

	/*
	* Class:     okj_easy_graphics_graphics2d_NWorld
	* Method:    DisposeSpriteDef
	* Signature: (J)V
	*/
	JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_DisposeSpriteDef
		(JNIEnv *, jobject, jlong);

	/*
	* Class:     okj_easy_graphics_graphics2d_NWorld
	* Method:    StopCollisionProcessing
	* Signature: ()V
	*/
	JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_StopCollisionProcessing
		(JNIEnv *, jobject);

	/*
	* Class:     okj_easy_graphics_graphics2d_NWorld
	* Method:    ResumeCollisionProcessing
	* Signature: ()V
	*/
	JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_ResumeCollisionProcessing
		(JNIEnv *, jobject);

	/*
	* Class:     okj_easy_graphics_graphics2d_NWorld
	* Method:    CollisionConfig
	* Signature: (IIIIII)V
	*/
	JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_CollisionConfig
		(JNIEnv *, jobject, jint, jint, jint, jint, jint, jint);

	/*
	* Class:     okj_easy_graphics_graphics2d_NWorld
	* Method:    processCollision
	* Signature: (JJ)V
	*/
	JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_processCollision__JJ
		(JNIEnv *, jobject, jlong, jlong);

	/*
	* Class:     okj_easy_graphics_graphics2d_NWorld
	* Method:    processCollision
	* Signature: (J)V
	*/
	JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_processCollision__J
		(JNIEnv *, jobject, jlong);

#ifdef __cplusplus
}
#endif
#endif
