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
		return (jlong)world;
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
* Method:    StopCollisionProcessing
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_StopCollisionProcessing
	(JNIEnv *env, jobject obj){
		world->SetEnableCollisionChecking(false);
}

/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    ResumeCollisionProcessing
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_ResumeCollisionProcessing
	(JNIEnv *env, jobject obj){
		world->SetEnableCollisionChecking(true);
}

/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    CollisionConfig
* Signature: (IIIIII)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_CollisionConfig
	(JNIEnv *env, jobject obj, jint x , jint y, jint width, jint heigh, jint col, jint row){
		world->CollisionConfig(x,y,width,heigh,col,row);
}

/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    processCollision
* Signature: (JJ)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_processCollision__JJ
	(JNIEnv *env, jobject obj, jlong manager1, jlong manager2){
		Manager *m1 = (Manager*)manager1;
		Manager *m2 = (Manager*)manager2;
		JniCollide listener(env,obj);
		world->ProcessCollision(m1,m2,&listener);
}

/*
* Class:     okj_easy_graphics_graphics2d_NWorld
* Method:    processCollision
* Signature: (J)V
*/
JNIEXPORT void JNICALL Java_okj_easy_graphics_graphics2d_NWorld_processCollision__J
	(JNIEnv *env, jobject obj, jlong manager){
		Manager *m1 = (Manager*)manager;
		JniCollide listener(env,obj);
		world->ProcessCollision(m1,&listener);
}

