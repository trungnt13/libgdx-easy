/*
*  Polygon.h
*
*  Created on: 10/1/2012 11:09:08 PM
*      Author: TrungNT
*/

#ifndef POLYGON_H_
#define POLYGON_H_
#include <Utils/utils.h>

#ifndef NULL
#ifdef __cplusplus
#define NULL    0
#else
#define NULL    ((void *)0)
#endif
#endif

namespace Math2D{
	class Polygon :public Comparator<Math2D::Polygon*> {
	protected:
		float* localVertices;
		int verticesSize;

		int *noIndex;
		int noIndexSize;
	public:
		Polygon(float*,int);
		~Polygon();

		//	===========================
		// vertices method
		float* getVertices();
		int getSize();

		//	============================
		// index method
		void setNoIndex(int*,int);
		int* getNoIndex();
		int getNoIndexSize();

		//	============================
		// comparator

		/**
		 This method will return true if local vertices of both 
		 polygon are equal
		 */
		bool equal(Polygon*);
	};

}// end of namespace
#endif /* POLYGON_H_ */
