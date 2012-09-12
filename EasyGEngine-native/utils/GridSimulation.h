/*
 * GridSimulation.h
 *
 *  Created on: Jul 12, 2012
 *      Author: trung
 */

#ifndef GRIDSIMULATION_H_
#define GRIDSIMULATION_H_

#include "math/eMath.h"

class Grid{
private:
	static const int FIRST_16_ZERO_BIT = 0x0000ffff;
	int mBoundWidth;
	int mBoundHeight;

	int mGridWidth;
	int mGridHeight;

public:
	Grid(int,int);
	Grid(int,int,int,int);
	Grid();

	void setMaxGrid(int,int);
	void setBounds(int,int,int,int);

	int getBoundWidth();
	int getBoundHeight();
	int getGridWidth();
	int getGridHeight();

	Vector2 project(Vector2*,float,float);
	int project(float,float);

	Vector2 unproject(Vector2*,int,int);
	Vector2 unproject(Vector2*,float,float);
	Vector2 unproject(Vector2*,int);
	Vector2 unproject(Vector2*,float);

	Vector2 toGridPos(Vector2*,int);
	int toMappingId(int,int);
};

class GridDataStorage{
private:
	int* mDataList;
public:
	int length;

	GridDataStorage();
	GridDataStorage(Grid);

	void generate(Grid);
	int getOrder(int);

	~GridDataStorage(){
		delete [] mDataList;
	}
};
#endif /* GRIDSIMULATION_H_ */
