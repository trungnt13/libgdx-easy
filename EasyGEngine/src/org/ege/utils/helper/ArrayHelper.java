package org.ege.utils.helper;


public class ArrayHelper {
	/**
	 * True if such as {1,2,3} -{3,1,2} 
	 * @param planes
	 * @return
	 */
	public static boolean compare(int[] array1,int[] array2){
			if(array1.length ==array2.length){
				int countTheSameNumber = 0;
				
				for(int j = 0 ; j < array1.length;j++){
					if(contain(array2, array1[j]))
						countTheSameNumber ++;
				}
				
				if(countTheSameNumber == array1.length)
					return true;
			}
		
		return false;
	}
	
	public static boolean contain(int[] array,int value){
		for(int i = 0;i < array.length;i++){
			if(array[i] == value)
				return true;
		}
		return false;
	}
}
