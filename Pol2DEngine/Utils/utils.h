/*
 * utils.h
 *
 *  Created on: Jul 14, 2012
 *      Author: trung
 */

#ifndef UTILS_H_
#define UTILS_H_

#include "Array.h"
#include "List.h"
#include "Memory.h"

/************************************************************************/
/* interface show that object can be compare                            */
/************************************************************************/
template<class T>
class Comparator{
public:
	virtual bool equal(T) = 0;
};

#endif /* UTILS_H_ */
