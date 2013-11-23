package stu.tnt.gdx.utils.exception;

/**
 * This exception happen when your number out of range (0.0f -1.0f)
 * @author trung
 *
 */
public class NumberRangeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4634575563109123454L;

	public NumberRangeException(){
		super();
	}
	
	public NumberRangeException(String msg){
		super(msg);
	}
}
