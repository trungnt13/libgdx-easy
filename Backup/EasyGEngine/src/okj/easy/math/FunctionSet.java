package okj.easy.math;

public class FunctionSet{
	public float[] attr;
	
	public FunctionSet(){
		
	}
	
	public FunctionSet(float[] attr){
		if(attr != null)
			this.attr = attr;
		else
			attr = new float[6];
	}
	
	public void set(float attribute[]){
		if(attribute.length <= 6)
			attr = attribute;
		else {
			for(int i =0; i<6 ;i++){
				attr[i] = attribute[i];
			}
		}
	}
	
	public boolean isValid(){
		for(int i =0; i < attr.length;i++){
			if(attr[i] != 0)
				return true;
		}
		return false;
	}
	
	public int size(){
		return this.attr.length;
	}
}
