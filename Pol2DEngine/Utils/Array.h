/*
* Array
*
*  Created on: Jul 14, 2012
*      Author: trung
*/

#ifndef ARRAY_
#define ARRAY_

#include <string.h>
#include <stdio.h>

class IntArray {
private:
	int capacity;
	int *storage;
public:
	int size;

	IntArray ();
	IntArray (int);

	~IntArray ();

	//	------------------

	void setCapacity (int);
	void ensureCapacity (int);
	void pack ();
	void trim ();

	//	------------------

	bool rangeCheck (int);
	void add (int, int);
	void add (int);
	void removeAt (int);
	void insertAt (int, int);

	//	------------------

	int get (int);
};

#endif /* ARRAY_ */
