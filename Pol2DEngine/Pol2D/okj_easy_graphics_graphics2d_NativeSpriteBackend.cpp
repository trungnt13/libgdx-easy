/*
*  okj_easy_graphics_graphics2d_NativeSpriteBackend.cpp
*
*  Created on: 10/7/2012 3:33:31 PM
*      Author: TrungNT
*/

#include "okj_easy_graphics_graphics2d_NativeSpriteBackend.h"
using namespace Entity2D;
/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    setBounds
* Signature: (JFFFF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_setBounds
	(JNIEnv *env, jobject obj, jlong sprite, jfloat x, jfloat y, jfloat width, jfloat height){
		Sprite *s = (Sprite*)sprite;
		s->setBounds(x,y,width,height);
}
/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    setSize
* Signature: (JFF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_setSize
	(JNIEnv *env, jobject obj, jlong sprite, jfloat width, jfloat height){
		Sprite *s = (Sprite*)sprite;
		s->setSize(width,height);
}
/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    setPosition
* Signature: (JFF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_setPosition
	(JNIEnv *env, jobject obj, jlong sprite, jfloat x, jfloat y){
		Sprite *s = (Sprite*)sprite;
		s->setPosition(x,y);
}
/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    setX
* Signature: (JF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_setX
	(JNIEnv *env, jobject obj, jlong sprite, jfloat x){
		Sprite *s = (Sprite*)sprite;
		s->setX(x);
}
/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    setY
* Signature: (JF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_setY
	(JNIEnv *env, jobject obj, jlong sprite, jfloat y){
		Sprite *s = (Sprite*)sprite;
		s->setY(y);
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    translate
* Signature: (JFF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_translate
	(JNIEnv *env, jobject obj, jlong sprite, jfloat xAmount, jfloat yAmount){
		Sprite *s = (Sprite*)sprite;
		s->translate(xAmount,yAmount);
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    translateX
* Signature: (JF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_translateX
	(JNIEnv *env, jobject obj, jlong sprite, jfloat xAmount){
		Sprite *s = (Sprite*)sprite;
		s->translateX(xAmount);
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    translateY
* Signature: (JF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_translateY
	(JNIEnv *env, jobject obj, jlong sprite, jfloat yAmount){
		Sprite *s = (Sprite*)sprite;
		s->translateY(yAmount);
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    setOrigin
* Signature: (JFF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_setOrigin
	(JNIEnv *env, jobject obj, jlong sprite , jfloat originX, jfloat originY){
		Sprite *s = (Sprite*)sprite;
		s->setOrigin(originX,originY);
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    setRotation
* Signature: (JF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_setRotation
	(JNIEnv *env, jobject obj, jlong sprite, jfloat degrees){
		Sprite *s = (Sprite*)sprite;
		s->setRotation(degrees);
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    rotate
* Signature: (JF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_rotate
	(JNIEnv *env, jobject obj, jlong sprite, jfloat rotate){
		Sprite *s = (Sprite*)sprite;
		s->rotate((float)rotate);
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    setScale
* Signature: (JF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_setScale__JF
	(JNIEnv *env, jobject obj, jlong sprite, jfloat scaleXY){
		Sprite *s = (Sprite*)sprite;
		s->setScale(scaleXY);
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    setScale
* Signature: (JFF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_setScale__JFF
	(JNIEnv *env, jobject obj, jlong sprite, jfloat scaleX, jfloat scaleY){
		Sprite *s = (Sprite*)sprite;
		s->setScale(scaleX,scaleY);
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    scale
* Signature: (JF)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_scale
	(JNIEnv *env, jobject obj, jlong sprite, jfloat scale){
		Sprite *s = (Sprite*)sprite;
		s->scale(scale);
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    getVertices
* Signature: (J[F)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_getVertices
	(JNIEnv *env, jobject obj, jlong sprite, jfloatArray vertices){
		Sprite *s = (Sprite*)sprite;
		float* verticesf = (float*)env->GetPrimitiveArrayCritical(vertices, 0);
		s->getVertices(verticesf);
		env->ReleasePrimitiveArrayCritical(vertices, verticesf, 0);
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    getX
* Signature: (J)F
*/
JNIEXPORT jfloat JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_getX
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		return s->getX();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    getCenterX
* Signature: (J)F
*/
JNIEXPORT jfloat JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_getCenterX
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		return s->getCenterX();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    getY
* Signature: (J)F
*/
JNIEXPORT jfloat JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_getY
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		return s->getY();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    getCenterY
* Signature: (J)F
*/
JNIEXPORT jfloat JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_getCenterY
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		return s->getCenterY();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    getWidth
* Signature: (J)F
*/
JNIEXPORT jfloat JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_getWidth
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		return s->getWidth();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    getHeight
* Signature: (J)F
*/
JNIEXPORT jfloat JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_getHeight
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		return s->getHeight();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    getOriginX
* Signature: (J)F
*/
JNIEXPORT jfloat JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_getOriginX
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		return s->getOriginX();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    getOriginY
* Signature: (J)F
*/
JNIEXPORT jfloat JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_getOriginY
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		return s->getOriginY();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    getRotation
* Signature: (J)F
*/
JNIEXPORT jfloat JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_getRotation
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		return s->getRotation();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    getScaleX
* Signature: (J)F
*/
JNIEXPORT jfloat JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_getScaleX
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		return s->getScaleX();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    getScaleY
* Signature: (J)F
*/
JNIEXPORT jfloat JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_getScaleY
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		return s->getScaleY();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    isDirty
* Signature: (J)Z
*/
JNIEXPORT jboolean JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_isDirty
	(JNIEnv *env, jobject obj, jlong address){
		Sprite *sprite = (Sprite*)address;
		return sprite->isDirty();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    reset
* Signature: (J)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_reset
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		s->reset();
}

/*
* Class:     okj_easy_graphics_graphics2d_NativeSpriteBackend
* Method:    dispose
* Signature: (J)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NativeSpriteBackend_dispose
	(JNIEnv *env, jobject obj, jlong sprite){
		Sprite *s = (Sprite*)sprite;
		delete s;
}
