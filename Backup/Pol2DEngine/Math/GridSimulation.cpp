/*
* GridSimulation.cpp
*
*  Created on: Jul 12, 2012
*      Author: trung
*/

#include "GridSimulation.h"

using namespace Math2D;

/************************************************************************/
/* GridAdvanceClass                                                     */
/************************************************************************/

void GridAdvance::setStartPosition(int startX,int startY){
	this->startX = startX;
	this->startY = startY;
}

void GridAdvance::setGridSize(int gridWidth,int gridHegiht){
	this->gridWidth = gridWidth;
	this->gridHeight = gridHegiht;
}

void GridAdvance::setGridSize(int boundWidth,int boundHeight,int cols,int row){
	this->gridWidth = boundWidth/cols;
	this->gridHeight = boundHeight/row;
}
// calculator

Vector2 GridAdvance::project(Vector2* result,float x,float y){
	return result->set(
		(float)( (x-startX)/gridWidth),
		(float)( (y-startY)/gridHeight) );
}

int GridAdvance::project(float x,float y){
	return (( 
		(int)( (x-startX)/gridWidth)<<16) | 
		(int)( (y-startY)/gridHeight) );
}

Vector2 GridAdvance::unproject(Vector2* result,int col,int row){
	return result->set(
		(int)(gridWidth*col)+startX,
		(int)(gridHeight*row)+startY);
}

Vector2 GridAdvance::unproject(Vector2* result,float Col,float Row){
	int col = (int)(Col);
	int row = (int)(Row);
	return result->set(
		(int)(gridWidth*col)+startX,
		(int)(gridHeight*row)+startY);
}

Vector2 GridAdvance::unproject(Vector2* result,int id){
	return result->set(
		(int)(gridWidth*(id>>16))				 + startX, 
		(int)(gridHeight*(id&FIRST_16_ZERO_BIT)) + startY);
}

Vector2 GridAdvance::unproject(Vector2* result,float ID){
	int id = (int)(ID);
	return result->set(
		(int)(gridWidth*(id>>16))				+ startX, 
		(int)(gridHeight*(id&FIRST_16_ZERO_BIT))+ startY);
}

Vector2 GridAdvance::toGridPos(Vector2 *result,int id){
	return result->set(id>>16, id & FIRST_16_ZERO_BIT);
}

int GridAdvance::toMappingId(int column,int row){
	return (column<<16 | row);
}

bool GridAdvance::fastCheck(float x,float y,float x1,float y1){
	//TODO
	if(x < startX || x1 < startX || y < startY || y1 < startY)
		return false;
	return ( this->project(x,y) == this->project(x1,y1) );
}
/************************************************************************/
/* GridClass                                                            */
/************************************************************************/

Grid::Grid(int boundWIdth,int boundHeight){
	mBoundWidth = boundWIdth;
	mBoundHeight = boundHeight;
}

Grid::Grid(int boundWidth,int boundHeight,int maxCol,int maxRow){
	mBoundHeight = boundHeight;
	mBoundWidth = boundWidth;

	mGridWidth = boundWidth/maxCol;
	mGridHeight = boundHeight/maxRow;
}

Grid::Grid(){
	mBoundWidth = 0;
	mBoundHeight =0;
	mGridHeight =0;
	mGridWidth = 0;
}
// get set

void Grid::setMaxGrid(int maxCol,int maxRow){
	mGridWidth = mBoundWidth/maxCol;
	mGridHeight = mBoundHeight/maxRow;
}

void Grid::setBounds(int boundWidth,int boundHeight,int maxCol,int maxRow){
	mBoundWidth = boundWidth;
	mBoundHeight = boundHeight;

	mGridWidth = boundWidth/maxCol;
	mGridHeight = boundHeight/maxRow;
}

int Grid::getBoundHeight(){
	return mBoundHeight;
}

int Grid::getBoundWidth(){
	return mBoundWidth;
}

int Grid::getGridHeight(){
	return mGridHeight;
}

int Grid::getGridWidth(){
	return mGridWidth;
}

// calculator

Vector2 Grid::project(Vector2* result,float x,float y){
	return result->set((float)(x/mGridWidth),
		(float)(y/mGridHeight));
}

int Grid::project(float x,float y){
	return (((int)(x/mGridWidth)<<16) | (int)(y/mGridHeight));
}

Vector2 Grid::unproject(Vector2* result,int col,int row){
	return result->set((int)(mGridWidth*col),(int)(mGridHeight*row));
}

Vector2 Grid::unproject(Vector2* result,float Col,float Row){
	int col = (int)(Col);
	int row = (int)(Row);
	return result->set((int)(mGridWidth*col),(int)(mGridHeight*row));
}

Vector2 Grid::unproject(Vector2* result,int id){
	return result->set((int)(mGridWidth*(id>>16)), (int)(mGridHeight*(id&FIRST_16_ZERO_BIT)));
}

Vector2 Grid::unproject(Vector2* result,float ID){
	int id = (int)(ID);
	return result->set((int)(mGridWidth*(id>>16)), (int)(mGridHeight*(id&FIRST_16_ZERO_BIT)));
}

Vector2 Grid::toGridPos(Vector2 *result,int id){
	return result->set(id>>16, id & FIRST_16_ZERO_BIT);
}

int Grid::toMappingId(int column,int row){
	return (column<<16 | row);
}


/************************************************************************/
/* GridDataStorageClass                                                 */
/************************************************************************/

GridDataStorage::GridDataStorage(){
	length  = 0;
}

GridDataStorage::GridDataStorage(Grid grid){
	generate(grid);
}

void GridDataStorage::generate(Grid grid){
	int maxRow = grid.getBoundHeight()/grid.getGridHeight();
	int maxCol = grid.getBoundWidth()/grid.getGridWidth();

	delete [] mDataList;
	length = maxRow*maxCol;
	mDataList = new int[length];

	int count = 0;
	for(int i =0 ; i < maxCol;i++){
		for(int j = 0;j < maxRow;j++){
			mDataList[count] = grid.toMappingId(i,j);
			count++;
		}
	}
}

int GridDataStorage::getOrder(int id){
	for(int i =0 ; i < length;i++)
		if(mDataList[i] == id)
			return i;
	return -1;
}
