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

class Test{
public :
	int value;
	Test(int v){
		this->value = v;
	}
};
List<long> list;
int main(){
	

	Test *t1 = new Test(10);
	Test *t2 = t1;
	t2 = NULL;
	printf("Shit : %d",t1->value);
	return 1;
}

