/*
*  test.cpp
*
*  Created on: 10/2/2012 10:22:58 AM
*      Author: TrungNT
*/

#include <stdio.h>
#include <Utils/List.h>

using namespace BasicDataStructures;

class interface{
public:
	int i;
public:
	virtual void set() = 0;

	void print(){
		printf("value is : %d \n",i);
	}
};

class object:public interface{
public:
	int *value;
	object(){
		value = new int[10];
		for(int i = 0 ;i < 10 ;i++)
			value[i] = i;
	}


	void set(){
		i = 1;
		printf("set object \n");
	}

	~object(){
		delete[] value;
		printf("object dead \n");
	}
};

List<long> list;
int main(){
	for(int i = 0 ;i < 10;i++){
		object *o = new object();
	}

	object *o = NULL;
	o->print();

	printf("shit \n");

	return 1;
}

