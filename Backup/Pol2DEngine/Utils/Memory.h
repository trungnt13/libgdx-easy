/*
*  Memory.h
*
*  Created on: 10/3/2012 1:36:33 PM
*      Author: TrungNT
*/

#ifndef MEMMORY_H_
#define MEMMORY_H_

typedef signed char	int8;
typedef signed short int16;
typedef signed int int32;
typedef unsigned char uint8;
typedef unsigned short uint16;
typedef unsigned int uint32;
typedef float float32;
typedef double float64;

/************************************************************************/
/* disposed when object no longger require                              */
/************************************************************************/
class Disposable {
private:
	virtual void dispose() =0 ;
};


#endif /* MEMMORY_H_ */
