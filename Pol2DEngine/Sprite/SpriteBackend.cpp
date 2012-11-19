/*
* Sprite.cpp
*
*  Created on: Sep 29, 2012
*      Author: Trung
*/


#include "SpriteBackend.h"
#include <string.h>

using namespace Entity2D;
using namespace BasicDataStructures;
using namespace Math2D;

//////////////////////////////////////////////////////////////////////////

/************************************************************************/
/* SpriteClass                                                          */
/************************************************************************/

Sprite::Sprite(Manager *parent){
	this->parent = parent;
	this->bounding = NULL;
	defBounding = new float[4];

	x = 0;
	y = 0;

	centerX = 0;
	centerY = 0;

	width = 0 ;
	height = 0;

	originX = 0;
	originY =0;

	originWidth = 0;
	originHeight = 0;

	rotation = 0;

	scaleX = 1;
	scaleY = 1;

	dirty = false;
	isCollide = true;
}

Sprite::~Sprite(){
	if(parent != NULL){
		parent->unmanage(this);
		parent = NULL;
	}
	this->bounding = NULL;
	delete defBounding;
}

//	=====================================================
//	setter

void Sprite::setBounds(float x,float y,float width,float height){
	this->x = x;
	this->y = y;
	this->width = width;
	this->height = height;

	if (rotation != 0 || scaleX != 1 || scaleY != 1)
		dirty = true;
}

void Sprite::setOrigin(float originX,float originY){
	this->originX = originX;
	this->originY = originY;
	dirty = true;
}

void Sprite::setOriginSize(float originWidth,float originHeight){
	this->originWidth = originWidth;
	this->originHeight = originHeight;
}

void Sprite::setPosition(float x,float y){
	this->x = x;
	this->y = y;
}

void Sprite::setRotation(float rotation){
	this->rotation = rotation;
	dirty = true;
}

void Sprite::rotate(float amount){
	this->rotation += amount;
	dirty = true;
}

void Sprite::setScale(float xy){
	this->scaleX = xy;
	this->scaleY = xy;
	dirty = true;
}


void Sprite::setScale(float x,float y){
	this->scaleX = x;
	this->scaleY = y;
	dirty = true;
}

void Sprite::setSize(float width,float height){
	this->width = width;
	this->height = height;

	if (rotation != 0 || scaleX != 1 || scaleY != 1)
		dirty = true;
}

void Sprite::scale(float xy){
	this->scaleX += xy;
	this->scaleY += xy;
	dirty = true;
}

void Sprite::setX(float x){
	this->x = x;
}

void Sprite::setY(float y){
	this-> y = y;
}

void Sprite::translate(float xAmount,float yAmount){
	this->x += xAmount;
	this->y += yAmount;
}

void Sprite::translateX(float xAmount){
	this->x += xAmount;
}

void Sprite::translateY(float yAmount){
	this->y += yAmount;
}

void Sprite::setCollision(bool isCollision){
	this->isCollide = isCollision;
}

//	=====================================================
//	getter

bool Sprite::isCollision(){
	return this->isCollide;
}

int Sprite::getNumberOfBounding(){
	if(bounding == NULL)
		return 0;
	return bounding->size();
}

int Sprite::getBoundingVertices(int index,float *vertices){
	if(bounding  == NULL || bounding->size() == 0){
		memcpy(vertices,defBounding,4*sizeof(float));
		return 4;
	}

	//vertices length of current polygon
	int size = 0;
	// the origin vertices of polygon
	float *localVertices;

	// process index polygon
	Polygon *polygon = bounding->get(index);
	size = polygon->getSize();
	localVertices = polygon->getVertices();

	bool scale = scaleX != 1 || scaleY != 1;
	float rotation = this->rotation;
	float cos = cosf(toRadian(rotation));
	float sin = sinf(toRadian(rotation));

	// scale for the first time
	if(width != originWidth || height != originHeight){
		for (int i = 0, n = size; i < n; i += 2) {
			// resize the polygon
			float scaleX = this->width/originWidth;
			float scaleY = this->height/originHeight;
			float x = localVertices[i] ;
			float y = localVertices[i + 1] ;
			x *= scaleX;
			y *= scaleY;
			vertices[i] =  x;
			vertices[i + 1] = y;

			// calculate transform polygon
			x = vertices[i] - originX;
			y = vertices[i + 1] - originY;
			// scale if needed
			if (scale) {
				x *= this->scaleX;
				y *= this->scaleY;
			}
			// rotate if needed
			if (rotation != 0) {
				float oldX = x;
				x = cos * x - sin * y;
				y = sin * oldX + cos * y;
			}
			vertices[i] = this->x + x + originX;
			vertices[i + 1] = this->y + y + originY;
		}
	}else{
		for (int i = 0, n = size; i < n; i += 2) {
			float x = localVertices[i] - originX;
			float y = localVertices[i + 1] - originY;

			// scale if needed
			if (scale) {
				x *= scaleX;
				y *= scaleY;
			}

			// rotate if needed
			if (rotation != 0) {
				float oldX = x;
				x = cos * x - sin * y;
				y = sin * oldX + cos * y;
			}

			vertices[i] = this->x + x + originX;
			vertices[i + 1] = this->y + y + originY;
		}
	}
	return size;
}

int* Sprite::getBoundingNoIndex(int index){
	Polygon *pol = bounding->get(index);
	return pol->getNoIndex();
}

int Sprite::getBoundingNoIndexSize(int index){
	Polygon *pol = bounding->get(index);
	return pol->getNoIndexSize();
}

void Sprite::getVertices(float *vertices){
	if (dirty) {
		dirty = false;

		float localX = -originX;
		float localY = -originY;
		float localX2 = localX + width;
		float localY2 = localY + height;
		float worldOriginX = this->x - localX;
		float worldOriginY = this->y - localY;
		if (scaleX != 1 || scaleY != 1) {
			localX *= scaleX;
			localY *= scaleY;
			localX2 *= scaleX;
			localY2 *= scaleY;
		}
		if (rotation != 0) {

			float cos = cosf(toRadian(rotation));
			float sin = sinf(toRadian(rotation));
			float localXCos = localX * cos;
			float localXSin = localX * sin;
			float localYCos = localY * cos;
			float localYSin = localY * sin;
			float localX2Cos = localX2 * cos;
			float localX2Sin = localX2 * sin;
			float localY2Cos = localY2 * cos;
			float localY2Sin = localY2 * sin;

			float x1 = localXCos - localYSin + worldOriginX;
			float y1 = localYCos + localXSin + worldOriginY;
			vertices[X1] = x1;
			vertices[Y1] = y1;

			float x2 = localXCos - localY2Sin + worldOriginX;
			float y2 = localY2Cos + localXSin + worldOriginY;
			vertices[X2] = x2;
			vertices[Y2] = y2;

			float x3 = localX2Cos - localY2Sin + worldOriginX;
			float y3 = localY2Cos + localX2Sin + worldOriginY;
			vertices[X3] = x3;
			vertices[Y3] = y3;

			vertices[X4] = x1 + (x3 - x2);
			vertices[Y4] = y3 - (y2 - y1);
		} else {
			float x1 = localX + worldOriginX;
			float y1 = localY + worldOriginY;
			float x2 = localX2 + worldOriginX;
			float y2 = localY2 + worldOriginY;

			vertices[X1] = x1;
			vertices[Y1] = y1;

			vertices[X2] = x1;
			vertices[Y2] = y2;

			vertices[X3] = x2;
			vertices[Y3] = y2;

			vertices[X4] = x2;
			vertices[Y4] = y1;
		}
	}

	float minx = vertices[X1];
	float miny = vertices[Y1];
	float maxx = vertices[X1];
	float maxy = vertices[Y1];

	minx = minx > vertices[X2] ? vertices[X2] : minx;
	minx = minx > vertices[X3] ? vertices[X3] : minx;
	minx = minx > vertices[X4] ? vertices[X4] : minx;

	maxx = maxx < vertices[X2] ? vertices[X2] : maxx;
	maxx = maxx < vertices[X3] ? vertices[X3] : maxx;
	maxx = maxx < vertices[X4] ? vertices[X4] : maxx;

	miny = miny > vertices[Y2] ? vertices[Y2] : miny;
	miny = miny > vertices[Y3] ? vertices[Y3] : miny;
	miny = miny > vertices[Y4] ? vertices[Y4] : miny;

	maxy = maxy < vertices[Y2] ? vertices[Y2] : maxy;
	maxy = maxy < vertices[Y3] ? vertices[Y3] : maxy;
	maxy = maxy < vertices[Y4] ? vertices[Y4] : maxy;


	centerX = (minx+maxx)/2;
	centerY = (miny+maxy)/2;

	// save x and width
	defBounding[0] = minx;
	defBounding[1] = miny;
	defBounding[2] = maxx-minx;
	defBounding[3] = maxy-miny;
}

float Sprite::getX(){
	return x;
}

float Sprite::getCenterX(){
	return centerX;
}

float Sprite::getY(){
	return y;
}

float Sprite::getCenterY(){
	return centerY;
}

float Sprite::getWidth(){
	return width;
}

float Sprite::getHeight(){
	return height;

}

float Sprite::getOriginX() {
	return originX;

}

float Sprite::getOriginY() {
	return originY;
}

float Sprite::getRotation() {
	return rotation;
}

float Sprite::getScaleX() {
	return scaleX;
}

float Sprite::getScaleY() {
	return scaleY;
}

void Sprite::setSpriteDef(SpriteDef* bounding){
	this->bounding = bounding;
}

void Sprite::reset(){
	parent = NULL;
	bounding = NULL;

	x = 0;
	y = 0;

	centerX = 0;
	centerY = 0;

	width = 0 ;
	height = 0;

	originX = 0;
	originY =0;

	rotation = 0;

	scaleX = 1;
	scaleY = 1;

	dirty = false;
	isCollide = true;

	defBounding[0] = -100000;
	defBounding[1] = -100000;
}

//////////////////////////////////////////////////////////////////////////

/************************************************************************/
/* SpriteDefClass                                                       */
/************************************************************************/

SpriteDef::SpriteDef(){
}

SpriteDef::~SpriteDef(){
	for(unsigned long i =0;i < mPolygonList.size();i++)
		delete mPolygonList[i];
	mPolygonList.clear();
}

//	=====================================================
//	comparator
bool SpriteDef::equal(SpriteDef* def){
	if (this->size() != def->size())
		return false;

	for(int i = 0;i < this->size();i++){
		if(!mPolygonList[i]->equal(def->mPolygonList[i]))
			return false;
	}
	return true;
}

//	======================================================
// bounding manager

void SpriteDef::addBounding(Polygon* polygon){
	unsigned long index = mPolygonList.getIndexOf(polygon);
	if(index != NaN)
		return;
	mPolygonList.insert(polygon);
}

void SpriteDef::removeBounding(int index){
	if(index >= 0 && index <= size())
		mPolygonList.del(index);
}

void SpriteDef::clearBounding(){
	for(unsigned long i =0;i < mPolygonList.size();i++){
		delete mPolygonList[i];
	}
	mPolygonList.clear();
}

int SpriteDef::size(){
	return mPolygonList.size();
}

Polygon* SpriteDef::get(int index){
	return mPolygonList[index];
}


//////////////////////////////////////////////////////////////////////////


/************************************************************************/
/* ManagerClass                                                         */
/************************************************************************/

Manager::Manager(){
}

Manager::~Manager(){
	mSpriteList.clear();
}

//	======================================================
//	sprite manage 

void Manager::manage(Sprite* sprite){
	unsigned long index = mSpriteList.getIndexOf(sprite);
	if(index != NaN)
		return;

	if(sprite->parent != NULL)
		sprite->parent->unmanage(sprite);

	sprite->parent = this;
	mSpriteList.insert(sprite);

}

void Manager::unmanage(Sprite* sprite){
	unsigned long index = mSpriteList.getIndexOf(sprite);
	if(index !=NaN){
		mSpriteList.del(index);
		sprite->parent = NULL;
	}
}

int Manager::size(){
	return mSpriteList.size();
}

Sprite* Manager::get(int index){
	return mSpriteList[index];
}

void Manager::clear(){
	mSpriteList.clear();
}

void Manager::delAllSprite(){
	for(unsigned long i = 0;i < mSpriteList.size();i++){
		delete mSpriteList[i];
	}
}
/************************************************************************/
/* sprite initialize method                                             */
/************************************************************************/

Sprite* Manager::CreateSprite(){
	Sprite *sprite = new Sprite(this);
	mSpriteList.insert(sprite);
	return sprite;
}
