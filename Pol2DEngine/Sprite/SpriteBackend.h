/*
* SpriteBackend.h
*
*  Created on: Sep 29, 2012
*      Author: Trung
*/

#ifndef SPRITEBACKEND_H_
#define SPRITEBACKEND_H_
#include <Math/eMath.h>

#undef X1
#define X1 0
#undef Y1
#define Y1 1
#undef C1
#define C1 2
#undef U1
#define U1 3
#undef V1
#define V1 4

#undef X2
#define X2 5
#undef Y2
#define Y2 6
#undef C2
#define C2 7
#undef U2
#define U2 8
#undef V2
#define V2 9

#undef X3
#define X3 10
#undef Y3
#define Y3 11
#undef C3
#define C3 12
#undef U3
#define U3 13
#undef V3
#define V3 14

#undef X4
#define X4 15
#undef Y4
#define Y4 16
#undef C4
#define C4 17
#undef U4
#define U4 18
#undef V4
#define V4 19

namespace Entity2D {
	class SpriteDef;
	class Manager;

	/************************************************************************/
	/* backend of all 2d sprite                                            */
	/************************************************************************/

	class Sprite {
	private:
		//	====================================
		//	params
		float x,y;
		float centerX,centerY;
		float width,height;
		float originX,originY;
		float rotation;
		float scaleX,scaleY;

		//	[0,1,2,3] = [x,y,width,height]
		float *defBounding;

		int dirty;
	public:
		//	====================================
		//	params

		Manager *parent;
		SpriteDef *bounding;

		// ====================================
		// construct and destructor
		Sprite(Manager*);

		/*
		Dispose all sprite def, clear bounding list and
		unmanage it from parent
		*/
		~Sprite();

		//	====================================
		// setter

		void setBounds(float,float,float,float) ;
		void setSize(float,float) ;
		void setPosition(float,float) ;
		void setX(float) ;
		void setY(float) ;
		void translate(float,float) ;
		void translateX(float) ;
		void translateY(float) ;
		void setOrigin(float,float) ;
		void setRotation(float) ;
		void rotate(float);
		void setScale(float) ;
		void setScale(float,float)  ;
		void scale(float) ;

		//	======================================
		//	getter

		void getVertices(float*) ;

		/* Generate bounding of whole sprite into float array */
		int getBoundingVertices(int,float*);
		/* Get the number of polygon in the sprite bounding */
		int getNumberOfBounding();

		/* return the bounding no index of given position */
		int* getBoundingNoIndex(int);
		/* return the size of given no index */
		int getBoundingNoIndexSize(int);

		float getX() ;
		float getCenterX() ;
		float getY() ;
		float getCenterY() ;
		float getWidth() ;
		float getHeight() ;
		float getOriginX()  ;
		float getOriginY() ;
		float getRotation() ;
		float getScaleX()  ;
		float getScaleY()  ;
		bool isDirty();
		//	========================================
		// bounding control

		void reset();
		void setSpriteDef(SpriteDef*);
	};

	/************************************************************************/
	/* sprite definition contain sprite bounding info                       */
	/************************************************************************/

	class SpriteDef:public Comparator<SpriteDef*> {
	private:
		BasicDataStructures::List<Math2D::Polygon*> mPolygonList;
	public:
		SpriteDef();

		~SpriteDef();

		//	======================================
		// comparator

		bool equal(SpriteDef* def);

		//	=======================================
		//	bounding manager

		/*
		This method will intereact will jni
		*/
		void addBounding(Math2D::Polygon*);
		/*
		This method will intereact will jni
		*/
		void removeBounding(int index);
		/*
		This method will intereact will jni
		*/
		void clearBounding();

		int size();

		Math2D::Polygon* get(int);
	};

	/************************************************************************/
	/*                                                                      */
	/************************************************************************/
	class Manager{
	private:
		BasicDataStructures::List<Sprite*> mSpriteList;

	public :
		Manager();
		~Manager();

		//	====================================
		// sprite backend manager


		void manage(Sprite*);

		void unmanage(Sprite*);

		// return the number of sprite this manager manages
		int size();

		// get sprite with given index
		Sprite* get(int);

		/*
		this method will never be call from manager
		it use like a callback for sprite
		Clear all sprite and Pooled them
		*/
		void clear();

		void delAllSprite();
		//	====================================
		// sprite backend initializer

		/*
		This method will intereact with jni and from World
		and create sprite if manager exist
		*/
		Sprite* CreateSprite();
	};
} // end of namespace
#endif /* SPRITEBACKEND_H_ */
