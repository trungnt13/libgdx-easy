/*
*  okj_easy_graphics_graphics2d_NManager.cpp
*
*  Created on: 10/4/2012 5:24:11 PM
*      Author: TrungNT
*/

#include "okj_easy_graphics_graphics2d_NManager.h"
using namespace Entity2D;

/*
 * Class:     okj_easy_graphics_graphics2d_NManager
 * Method:    manage
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NManager_manage
  (JNIEnv *env, jobject obj, jlong managerAddr, jlong spriteAddr){
	  Manager *manager =  (Manager*)managerAddr;
	  Sprite *sprite = (Sprite*)spriteAddr;
	  manager->manage(sprite);
}

/*
 * Class:     okj_easy_graphics_graphics2d_NManager
 * Method:    clear
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NManager_clear
  (JNIEnv *env, jobject obj, jlong managerAddr){
	  Manager *manager =(Manager*)managerAddr;
	  manager->clear();
}