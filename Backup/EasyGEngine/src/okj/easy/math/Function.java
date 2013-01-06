package okj.easy.math;



public abstract class Function {

	public static final double MIN = 1e-8;
	FunctionSet set;
	
	public Function(FunctionSet attributes){
		setAttributes(attributes);
	}
	
	
	public abstract void setAttributes(FunctionSet attr);
	
	public abstract double getFactor(double x);
	
	public abstract double derivative(double x);
	
	public abstract double f(double x);
}
