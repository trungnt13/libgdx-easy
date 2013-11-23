package stu.tnt.gdx.utils;

public class Sort  {
	private int[] numbers;
	private int number;

	public static void insertionSort(int[] list, int length) {
	    int firstOutOfOrder, location, temp;
	    for(firstOutOfOrder = 1; firstOutOfOrder < length; firstOutOfOrder++) { //Starts at second term, goes until the end of the array.
	        if(list[firstOutOfOrder] < list[firstOutOfOrder - 1]) { //If the two are out of order, we move the element to its rightful place.
	            temp = list[firstOutOfOrder];
	            location = firstOutOfOrder;
	           
	            do { //Keep moving down the array until we find exactly where it's supposed to go.
	                list[location] = list[location-1];
	                location--;
	            }
	            while (location > 0 && list[location-1] > temp);
	           
	            list[location] = temp;
	        }
	    }
	}
	
	public void sort(int[] values) {
		// Check for empty or null array
		if (values ==null || values.length==0){
			return;
		}
		this.numbers = values;
		number = values.length;
		quicksort(0, number - 1);
	}

	private void quicksort(int low, int high) {
		int i = low, j = high;
		// Get the pivot element from the middle of the list
		int pivot = numbers[low + (high-low)/2];

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (numbers[i] < pivot) {
				i++;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (numbers[j] > pivot) {
				j--;
			}

			// If we have found a values in the left list which is larger then
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i <= j) {
				exchange(i, j);
				i++;
				j--;
			}
		}
		// Recursion
		if (low < j)
			quicksort(low, j);
		if (i < high)
			quicksort(i, high);
	}

	private void exchange(int i, int j) {
		int temp = numbers[i];
		numbers[i] = numbers[j];
		numbers[j] = temp;
	}
}