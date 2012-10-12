/*
* eMath.cpp
*
*  Created on: Jul 13, 2012
*      Author: trung
*/


#include "eMath.h"

using namespace Math2D;

inline float absf(float a){
	return (a < 0) ? -a :a ;
}

float calDistance(float x,float y,float x1,float y1){
	return sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
}

bool overlapConvexPolygons(float *verts1,int verts1Size,int *noIndex1,int index1Size,float *verts2,int verts2Size,int *noIndex2,int index2Size,MinimumTranslationVector* mtv){
	float overlap = 0x7f7fffff;
	float smallestAxisX = 0;
	float smallestAxisY = 0;

	int vertices;
	int stop = 0;

	// ============================================
	// Get polygon1 axes
	int numAxes1 = verts1Size;
	for (int i = 0; i < numAxes1; i += 2) {

		// chheck the non-index edges of polygon 1
		vertices = i / 2;
		stop = false;
		for (int j = 0 ;j < index1Size;j++)
			if (vertices == noIndex1[j])
				stop = true;
		if (stop)
			continue;

		// process store the edges
		float x1 = verts1[i];
		float y1 = verts1[i + 1];
		float x2 = verts1[(i + 2) % numAxes1];
		float y2 = verts1[(i + 3) % numAxes1];

		// calculate the axis
		float axisX = y1 - y2;
		float axisY = -(x1 - x2);

		// normalize the axis
		float length = (float) sqrt(axisX * axisX + axisY * axisY);
		axisX /= length;
		axisY /= length;

		// -- Begin check for separation on this axis --//

		// Project polygon1 onto this axis
		float min1 = (axisX * verts1[0]) + (axisY * verts1[1]);
		float max1 = min1;
		for (int j = 2; j < verts1Size; j += 2) {
			float p = (axisX * verts1[j]) + (axisY * verts1[j + 1]);
			if (p < min1) {
				min1 = p;
			} else if (p > max1) {
				max1 = p;
			}
		}

		// Project polygon2 onto this axis
		float min2 = (axisX * verts2[0]) + (axisY * verts2[1]);
		float max2 = min2;
		for (int j = 2; j < verts2Size; j += 2) {
			float p = (axisX * verts2[j]) + (axisY * verts2[j + 1]);
			if (p < min2) {
				min2 = p;
			} else if (p > max2) {
				max2 = p;
			}
		}

		if (!((min1 < min2 && max1 > min2) || (min2 < min1 && max2 > min1))) {
			return false;
		} else {
			float o = min(max1, max2) - max(min1, min2);
			if ((min1 < min2 && max1 > max2) || (min2 < min1 && max2 > max1)) {
				float mins = absf(min1 - min2);
				float maxs = absf(max1 - max2);
				if (mins < maxs) {
					axisX = -axisX;
					axisY = -axisY;
					o += mins;
				} else {
					o += maxs;
				}
			}
			if (o < overlap) {
				overlap = o;
				smallestAxisX = axisX;
				smallestAxisY = axisY;
			}
		}
		// -- End check for separation on this axis --//
	}

	// ============================================
	// Get polygon2 axes
	int numAxes2 = verts2Size;
	for (int i = 0; i < numAxes2; i += 2) {
		// chheck the non-index edges of polygon 1
		vertices = i / 2;
		stop = false;
		for (int j = 0;j < index2Size; j++)
			if (vertices == noIndex2[j])
				stop = true;
		if (stop)
			continue;

		// process store the polygon 2 edge
		float x1 = verts2[i];
		float y1 = verts2[i + 1];
		float x2 = verts2[(i + 2) % numAxes2];
		float y2 = verts2[(i + 3) % numAxes2];

		// calculate the axis
		float axisX = y1 - y2;
		float axisY = -(x1 - x2);

		// normalize the axis
		float length = (float) sqrt(axisX * axisX + axisY * axisY);
		axisX /= length;
		axisY /= length;

		// -- Begin check for separation on this axis --//

		// Project polygon1 onto this axis
		float min1 = (axisX * verts1[0]) + (axisY * verts1[1]);
		float max1 = min1;
		for (int j = 2; j < verts1Size; j += 2) {
			float p = (axisX * verts1[j]) + (axisY * verts1[j + 1]);
			if (p < min1) {
				min1 = p;
			} else if (p > max1) {
				max1 = p;
			}
		}

		// Project polygon2 onto this axis
		float min2 = (axisX * verts2[0]) + (axisY * verts2[1]);
		float max2 = min2;
		for (int j = 2; j < verts2Size; j += 2) {
			float p = (axisX * verts2[j]) + (axisY * verts2[j + 1]);
			if (p < min2) {
				min2 = p;
			} else if (p > max2) {
				max2 = p;
			}
		}

		if (!((min1 < min2 && max1 > min2) || (min2 < min1 && max2 > min1))) {
			return false;
		} else {
			float o = min(max1, max2) - max(min1, min2);

			if ((min1 < min2 && max1 > max2) || (min2 < min1 && max2 > max1)) {
				float mins = absf(min1 - min2);
				float maxs = absf(max1 - max2);
				if (mins < maxs) {
					axisX = -axisX;
					axisY = -axisY;
					o += mins;
				} else {
					o += maxs;
				}
			}

			if (o < overlap) {
				overlap = o;
				smallestAxisX = axisX;
				smallestAxisY = axisY;
			}
		}
		// -- End check for separation on this axis --//
	}
	if (mtv != NULL) {
		mtv->normal.set(smallestAxisX, smallestAxisY);
		mtv->depth = overlap;
	}

	return true;
}

bool overlapRectangle(float *r1,float *r2){
	if (r1[0] < r2[0] + r2[2] && 
		r1[0] + r1[2] > r2[0] && 
		r1[1] < r2[1] + r2[3] && 
		r1[1] + r1[3] > r2[1])
		return true;
	else
		return false;
}