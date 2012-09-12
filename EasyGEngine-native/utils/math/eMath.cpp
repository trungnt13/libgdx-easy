/*
 * eMath.cpp
 *
 *  Created on: Jul 13, 2012
 *      Author: trung
 */


#include "eMath.h"


float calDistance(float x,float y,float x1,float y1){
	return sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
}
