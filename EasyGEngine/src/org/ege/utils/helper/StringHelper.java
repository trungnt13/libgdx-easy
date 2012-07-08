package org.ege.utils.helper;

import java.util.ArrayList;


public class StringHelper {
	public static ArrayList<String> readLine(String str){
		int leng = str.length();
		ArrayList<String> tmp = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0;i < leng;i++){
			if(str.charAt(i) != '\n'){
				builder.append(str.charAt(i));
			}else{
				if(builder.toString().length() > 0)
					tmp.add(builder.toString());
				builder.setLength(0);
			}
		}
		return tmp;
	}
	
	public static String removeString(String oriString,String removeString){
		if(oriString.contains(removeString)){		
			return  oriString.replace(removeString, "");
		}
		return null;
	}
	
	public static int atoi(String oriString){
		StringBuilder tmp = new StringBuilder();
		tmp.append('0');
		for(int i = 0; i <  oriString.length();i++){
			if(isNumber(oriString.charAt(i))){
				tmp.append(oriString.charAt(i));
			}
		}
		return Integer.parseInt(tmp.toString());
	}
	
	/**
	 * This method to read a string into array of number 
	 * Such as: "+0+1" -> int[ ]{0,1} ; "-1+1" -> int[ ]{-1,1}
	 * @param str this string should be Symmetric and the first element will be '+' or '-' , 
	 * such as "+0+1" or "-123+694" -> int[ ]{-123,+694} 
	 * @param numberOfElementInString the number of element such as: "+1-2+3" have *"three
	 * element"* int[ ]{1,-2,3}
	 * @return array if integer
	 */
	public static int[] toNumber(String str,int numberOfElementInString){
		int numb = numberOfElementInString;
		int[] tmp = new int[numb];
		
		int sizeOf1Element = str.length()/numb;
		for(int i = 0; i< numb;i++){
			String temp = str.substring(i * sizeOf1Element,  sizeOf1Element * (i+1));
			if(temp.charAt(0) == '-')
				tmp[i] = -1 * atoi(temp);
			else
				tmp[i] = 1 * atoi(temp);
		}
		return tmp;
	}
	
	public static boolean isNumber(char c){
		switch (c) {
		case '0':
			return true;
		case '1':
			return true;
		case '2':
			return true;
		case '3':
			return true;
		case '4':
			return true;
		case '5':
			return true;
		case '6':
			return true;
		case '7':
			return true;
		case '8':
			return true;
		case '9':
			return true;
		default:
			return false;
		}
	}
}
