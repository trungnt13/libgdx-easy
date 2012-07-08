package org.ege.utils.exception;

public class WrongSizeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8488835281818704234L;
	
	public WrongSizeException(){
		super();
	}
	
	public WrongSizeException(String msg){
		super(msg);
	}
}
