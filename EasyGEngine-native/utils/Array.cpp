/*
 * Array.cpp
 *
 *  Created on: Jul 14, 2012
 *      Author: trung
 */

#include "Array.h"

/************************************************
 * Int array
 ************************************************/

// Constructor and Destructor
IntArray::IntArray(){
	size = 0;
	capacity = 13;
	storage = new int[13];
}

IntArray::IntArray(int capacity){
	 this->capacity = capacity;
	 size = 0;
	 storage = new int[capacity];
}

IntArray::~IntArray(){
	delete [] storage;
}

// Capacity controller

void IntArray::setCapacity(int newCapacity) {
      int *newStorage = new int[newCapacity];
      memcpy(newStorage, storage, sizeof(int) * size);
      capacity = newCapacity;
      delete[] storage;
      storage = newStorage;
}

void IntArray::ensureCapacity(int minCapacity) {
      if (minCapacity > capacity) {
            int newCapacity = (capacity * 3) / 2 + 1;
            if (newCapacity < minCapacity)
                  newCapacity = minCapacity;
            setCapacity(newCapacity);
      }
}

void IntArray::pack() {
      if (size <= capacity / 2) {
            int newCapacity = (size * 3) / 2 + 1;
            setCapacity(newCapacity);
      }
}

void IntArray::trim() {
      int newCapacity = size;
      setCapacity(newCapacity);
}

// Array access

bool IntArray::rangeCheck(int index) {
      if (index < 0 || index >= size){
         printf("Exception: Out of bound");
         return false;
      }
      return true;
}

void IntArray::add(int index, int value) {
	if(rangeCheck(index)){
		storage[index] = value;
	}
}

void IntArray::add(int value) {
	if(size == capacity)
		setCapacity((size*3)/2 +1);
	storage[size++] = value;
}

int IntArray::get(int index) {
	if(rangeCheck(index))
		return storage[index];
	return 0xffffffff;
}

void IntArray::removeAt(int index) {
      if(!rangeCheck(index))
    	  return;
      int moveCount = size - index - 1;
      if (moveCount > 0)
            memmove(storage + index, storage + (index + 1), sizeof(int) * moveCount);
      size--;
      pack();
}

void IntArray::insertAt(int index, int value) {
      if (index < 0 || index > size)
            printf("Index out of bounds!");
      ensureCapacity(size + 1);
      int moveCount = size - index;
      if (moveCount != 0)
            memmove(storage + index + 1, storage + index, sizeof(int) * moveCount);
      storage[index] = value;
      size++;
}
