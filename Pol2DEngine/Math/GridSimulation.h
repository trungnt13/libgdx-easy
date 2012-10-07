/*
* GridSimulation.h
*
*  Created on: Jul 12, 2012
*      Author: trung
*/

#ifndef GRIDSIMULATION_H_
#define GRIDSIMULATION_H_

#include "Vector2.h"

namespace Math2D{

	class Grid {
	private:
		static const int FIRST_16_ZERO_BIT = 0x0000ffff;
		int mBoundWidth;
		int mBoundHeight;

		int mGridWidth;
		int mGridHeight;

	public:
		Grid (int, int);
		Grid (int, int, int, int);
		Grid ();

		void setMaxGrid (int, int);
		void setBounds (int, int, int, int);

		int getBoundWidth ();
		int getBoundHeight ();
		int getGridWidth ();
		int getGridHeight ();

		Math2D::Vector2 project (Math2D::Vector2*, float, float);
		int project (float, float);

		Math2D::Vector2 unproject (Math2D::Vector2*, int, int);
		Math2D::Vector2 unproject (Math2D::Vector2*, float, float);
		Math2D::Vector2 unproject (Math2D::Vector2*, int);
		Math2D::Vector2 unproject (Math2D::Vector2*, float);

		Math2D::Vector2 toGridPos (Math2D::Vector2*, int);
		int toMappingId (int, int);
	};

	/************************************************************************/
	/*                                                                      */
	/************************************************************************/

	class GridDataStorage {
	private:
		int* mDataList;
	public:
		int length;

		GridDataStorage ();
		GridDataStorage (Grid);

		void generate (Grid);
		int getOrder (int);

		~GridDataStorage () {
			delete[] mDataList;
		}
	};
} // end of namespace
#endif /* GRIDSIMULATION_H_ */
