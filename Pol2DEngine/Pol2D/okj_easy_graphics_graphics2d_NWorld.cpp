/*
*  okj_easy_graphics_graphics2d_NWorld.cpp
*
*  Created on: 10/4/2012 5:24:49 PM
*      Author: TrungNT
*/

#include "okj_easy_graphics_graphics2d_NWorld.h"
using namespace Entity2D;

static WorldManager *world = 0;

static jclass worldClass = 0;
static jmethodID collideID = 0;

class JniCollide:public CollideListener{
private:
	JNIEnv *env;
	jobject obj;
public:
	JniCollide(JNIEnv *env,jobject obj){
		this->env = env;
		this->obj = obj;
	}

	void collide(long long sprite1,long long sprite2){
		if(collideID != 0)
			env->CallVoidMethod(obj,collideID,sprite1,sprite2);		
	}
};
/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    CreateWorld
* Signature: ()J
*/
JNIEXPORT jlong JNICALL Java_okj_easy_graphics_graphics2d_NWorld_CreateWorld
	(JNIEnv *env, jobject obj){
		if(!worldClass){
			worldClass = (jclass)env->NewGlobalRef(env->GetObjectClass(obj));
			collideID = env->GetMethodID(worldClass,"collide","(JJ)V");
		}

		if(!world)
			world =new WorldManager();
		else{
			delete world;
			world = new WorldManager();
		}
}

/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    DisposeWorld
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_DisposeWorld
	(JNIEnv *env, jobject obj){
		delete world;
}

/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    CreateNSprite
* Signature: ()J
*/
JNIEXPORT jlong JNICALL Java_okj_easy_graphics_graphics2d_NWorld_CreateNSprite
	(JNIEnv *env, jobject obj){
		return (jlong)world->mainList->CreateSprite();
}

/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    CreateManager
* Signature: ()J
*/
JNIEXPORT jlong JNICALL Java_okj_easy_graphics_graphics2d_NWorld_CreateManager
	(JNIEnv *env, jobject obj){
		return (jlong)world->CreateManager();
}

/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    DisposeManager
* Signature: (J)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_DisposeManager
	(JNIEnv *env, jobject obj, jlong manager){
		Manager *m = (Manager*)manager;
		world->DeleteManager(m);
}

/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    CreateSpriteDef
* Signature: ()J
*/
JNIEXPORT jlong JNICALL Java_okj_easy_graphics_graphics2d_NWorld_CreateSpriteDef
	(JNIEnv *env, jobject obj){
		return (jlong)world->CreateSpriteDef();
}

/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    NSpriteAddNSpriteDef
* Signature: (JJ)J
*/
JNIEXPORT jlong JNICALL Java_okj_easy_graphics_graphics2d_NWorld_NSpriteAddNSpriteDef
	(JNIEnv *env, jobject obj, jlong spriteAddress, jlong spriteDefAddress){
		Sprite *sprite  = (Sprite*)spriteAddress;
		SpriteDef *def = (SpriteDef*)spriteDefAddress;
		sprite->setSpriteDef(def);
}

/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    DisposeSpriteDef
* Signature: (J)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_DisposeSpriteDef
	(JNIEnv *env, jobject obj, jlong spriteDef){
		SpriteDef *def = (SpriteDef*)spriteDef;
		world->DeleteSpriteDef(def);
}

/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    processCollision
* Signature: (JJI)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_processCollision__JJI
	(JNIEnv *env, jobject obj, jlong manager1, jlong manager2, jint mode){
	Manager *m1 = (Manager*)manager1;
	Manager *m2 = (Manager*)manager2;
	JniCollide listener(env,obj);
	world->ProcessCollision(m1,m2,mode,&listener);
}
/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    processCollision
* Signature: (JI)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_processCollision__JI
	(JNIEnv *env, jobject obj, jlong manager, jint mode){
		Manager *m1 = (Manager*)manager;
		JniCollide listener(env,obj);
		world->ProcessCollision(m1,mode,&listener);
}

