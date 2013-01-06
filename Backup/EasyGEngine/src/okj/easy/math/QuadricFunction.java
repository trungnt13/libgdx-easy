package okj.easy.math;


public class QuadricFunction extends Function{
	float a;
	float b;
	float c;
	float angle;
	
	boolean mode_loop = false;
	
	public QuadricFunction(FunctionSet attributes) {
		super(attributes);
	}

	@Override
	public void setAttributes(FunctionSet attr){
		if(attr.size() >= 3)
			this.set = attr;
		else 
			this.set = new FunctionSet(new float[3]);
		this.a = attr.attr[0];
		this.b = attr.attr[1];
		this.c = attr.attr[2];
	}
	
	@Override
	public double getFactor(double x) {
		if(x == 0)
			x += 0.000000001;
		return Math.atan(derivative(x));
	}

	@Override
	public double derivative(double x) {
		if(x == 0)
			x += 0.000000001;
		double h = x * MIN;
		return (f(x+h)-f(x-h)) / (2*h);
	}

	@Override
	public double f(double x) {
		return (a*x*x + b*x + c);
	}

	public double f1(double x) {
		return 0;
	}

}
