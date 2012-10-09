/*
*  SpriteManager.cpp
*
*  Created on: 10/2/2012 3:37:30 PM
*      Author: TrungNT
*/

#include "SpriteManager.h"

using namespace Entity2D;
using namespace Math2D;

/************************************************************************/
/* WorldManagerClass                                                    */
/************************************************************************/
WorldManager::WorldManager(){
	mainList = new Manager();

	mGrid = new GridAdvance();
	mGrid->setStartPosition(0,0);
	mGrid->setGridSize(10000,10000);

	mManagerList.insert(mainList);
}

WorldManager::~WorldManager(){
	for(unsigned long i =0;i < mManagerList.size();i++){
		mManagerList[i]->delAllSprite();
		delete mManagerList[i];
	}

	for(unsigned long i =0;i < mSpriteDefList.size();i++){
		delete mSpriteDefList[i];
	}

	delete mGrid;

	mainList = NULL;
	mManagerList.clear();
	mSpriteDefList.clear();
}

//	======================================================
//	manager

Manager* WorldManager::CreateManager(){
	Manager *manager = new Manager();
	mManagerList.insert(manager);
	return manager;
}

bool WorldManager::ContainManager(Manager* manager){
	return mManagerList.getIndexOf(manager) != NaN;
}

void WorldManager::DeleteManager(Manager* manager){
	unsigned long index = mManagerList.getIndexOf(manager);
	if(index == NaN)
		return;

	mManagerList.del(index);
	delete manager;
}

//	========================================================
//	sprite def

SpriteDef* WorldManager::CreateSpriteDef(){
	SpriteDef* def = new SpriteDef();
	mSpriteDefList.insert(def);
	return def;
}

bool WorldManager::ContainSpriteDef(SpriteDef* def){
	return mSpriteDefList.getIndexOf(def) != NaN;
}

void WorldManager::DeleteSpriteDef(SpriteDef* def){
	unsigned long index = mSpriteDefList.getIndexOf(def);
	if(index == NaN)
		return;

	mSpriteDefList.del(index);
	delete def;
}

//	========================================================
//	processor
void WorldManager::CollisionConfig(int worldX,int worldY,int worldWidth,int worldHeight,int numberOfCols,int numberOfRows){
	mGrid->setStartPosition(worldX,worldY);
	mGrid->setGridSize(worldWidth,worldHeight,numberOfCols,numberOfRows);
}
void WorldManager::ProcessCollision(Manager *m1,Manager *m2,CollideListener *listener){
	float bound1[100];
	float bound2[100];

	// number of bonding polygon in sprite
	int nof1 = 0;
	int nof2 = 0;

	//traverse sprite list of manager 1
	for(int i = 0;i < m1->size();i++){
		Sprite *s1 = m1->get(i);

		//traverse sprite list of manager 2
		for(int j = 0;j < m2->size();j++){
			Sprite *s2 = m2->get(j);

			// check grid
			if(!mGrid->fastCheck(
				s1->getCenterX(),s1->getCenterY(),
				s2->getCenterX(),s2->getCenterY()) )
				goto outer;

			// get number of polygon in each sprite
			nof1 = s1->getNumberOfBounding();
			nof2 = s2->getNumberOfBounding();

			// process check bounding collide
			if(nof1 == 0 || nof2 == 0){
				//just check bounding rect
				s1->getBoundingVertices(0,bound1);
				s2->getBoundingVertices(0,bound2);
				if(overlapRectangle(bound1,bound2))
					listener->collide((long long)s1,(long long)s2);
			}else {
				//size of vertices in each polygon
				int size1 = 0;
				int size2 = 0;
				// traverse bounding list of sprite 1
				for(int m = 0; m < nof1;m++){
					// get bound of sprite 1 at given index
					size1 = s1->getBoundingVertices(m,bound1);
					for(int n = 0; n < nof2;n++){
						// get bound of sprite 2 at given index
						size2 = s2->getBoundingVertices(n,bound2);
						// check overlap convex polygons
						if(overlapConvexPolygons(
							bound1,size1,s1->getBoundingNoIndex(m),s1->getBoundingNoIndexSize(m),
							bound2,size2,s2->getBoundingNoIndex(n),s2->getBoundingNoIndexSize(n),NULL)){
								listener->collide((long long)s1,(long long)s2);
								goto outer;
						}
					}
				}
			}// end of collision checking between two sprite
outer:
			nof1 = 0;
			nof2 = 0;
		}// turn to next s2
	}// turn to next s1
}

void WorldManager::ProcessCollision(Manager* m,CollideListener *listener){

}