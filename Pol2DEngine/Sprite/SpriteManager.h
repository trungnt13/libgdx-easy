/*
*  SpriteManager.h
*
*  Created on: 10/2/2012 3:37:43 PM
*      Author: TrungNT
*/

#ifndef SPRITEMANAGER_H_
#define SPRITEMANAGER_H_

#include "SpriteBackend.h"
#include <Utils/utils.h>
#include <Math/eMath.h>

namespace Entity2D{
	static const int COLLISION_MODE_DOUBLE = 2;
	static const int COLLISION_MODE_TRIPLE = 3;
	static const int COLLISION_MODE_FOURTH = 4;
	static const int COLLISION_MODE_HIGHEST = 5;

	/************************************************************************/
	/* CollideListenerClass                                                 */
	/************************************************************************/

	class CollideListener{
	public:
		CollideListener(){

		}
		~CollideListener(){

		}

		virtual void collide(long long sprite1,long long sprite2){

		}
	};

	/************************************************************************/
	/* WorldManagerClass                                                    */
	/************************************************************************/

	class WorldManager{
	private:
		BasicDataStructures::List<Manager*> mManagerList;
		BasicDataStructures::List<SpriteDef*> mSpriteDefList;
		Math2D::GridAdvance *mGrid;

		bool isCollisionCheckingEnable;
	public:
		Manager *mainList;

		WorldManager();
		~WorldManager();

		/**
		Manager
		*/
		Manager* CreateManager();
		bool ContainManager(Manager*);
		void DeleteManager(Manager*);

		/**
		SpriteDef
		*/
		SpriteDef* CreateSpriteDef();
		bool ContainSpriteDef(SpriteDef*);
		void DeleteSpriteDef(SpriteDef*);

		/**
		Collision
		*/
		void SetEnableCollisionChecking(bool);
		void CollisionConfig(int,int,int,int,int,int);
		void ProcessCollision(Manager*,Manager*,CollideListener*);
		void ProcessCollision(Manager*,CollideListener*);

	};
} // end of namespace

#endif /* SPRITEMANAGER_H_ */
