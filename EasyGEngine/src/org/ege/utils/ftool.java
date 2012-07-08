package org.ege.utils;

public class ftool {
	private static byte key1 = 1;
	private static byte key2 = 1;
	private static byte key3 = 1;
	
	private ftool() {
		
	}
	
	private static boolean toBoolean(byte key){
		if(key == 1)
			return true;
		else
			return false;
	}
	
	public static boolean switch1(){
		return toBoolean(key1--);
	}

	public static void reset1(){
		key1 = 1;
	}

	public static boolean switch2(){
		return toBoolean(key2--);
	}

	public static void reset2(){
		key2 = 1;
	}

	public static boolean switch3(){
		return toBoolean(key3--);
	}

	public static void reset3(){
		key3 = 1;
	}

	public static boolean equal(int[] i1,int[] i2){
		if(i1.length != i2.length)
			return false;
		int count = 0;
		for(int i = 0;i < i1.length;i++){
			for(int j = 0; j < i2.length;j++){
				if(i1[i] == i2[j])
					count++;
			}
			if(count != 1)
				return false;
			count--;
		}
		return true;
	}
}
