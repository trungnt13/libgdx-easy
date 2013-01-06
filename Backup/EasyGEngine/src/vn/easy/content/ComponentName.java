package vn.easy.content;



/**
 * 
 * @author Android Open Source Project
 *
 */
public class ComponentName implements Comparable<ComponentName>{
	private final String mFirstName;
	private final String mSecondName; 
	
	// ---------------------------------------------------------------------
	
	public ComponentName(String firstName,String secondName){
		if(firstName == null || secondName == null)
			throw new NullPointerException("There is one Name is null");
		this.mFirstName = firstName;
		this.mSecondName = secondName;
	}
	
	public ComponentName(Class<?> firstClass,Class<?> secondClass){
		this.mFirstName = firstClass.getName();
		this.mSecondName = secondClass.getName();
	}
	
	// ---------------------------------------------------------------------

	public String getFirstName(){
		return this.mFirstName;
	}

	
	public String getSecondName(){
		return this.mSecondName;
	}
	
	 @Override
	    public boolean equals(Object obj) {
	        try {
	            if (obj != null) {
	                ComponentName other = (ComponentName)obj;
	                // Note: no null checks, because mPackage and mClass can
	                // never be null.
	                return mFirstName.equals(other.mFirstName)
	                        && mSecondName.equals(other.mSecondName);
	            }
	        } catch (ClassCastException e) {
	        
	        }
	        return false;
	   }
	
	 public boolean equals(Class<?> firstClass,Class<?> secondClass){
		 if(firstClass.getName().equals(mFirstName) &&
		     secondClass.getName().equals(mSecondName) ){
			 return true;
		 }
		 return false;
	 }
	 
	 public boolean equals(String firstName,String secondName){
		 if(firstName.equals(mFirstName) &&
		     secondName.equals(mSecondName) ){
			 return true;
		 }
		 return false;
	 }
	 
	@Override
	public int compareTo(ComponentName that) {
		int v;
		v = this.mFirstName.compareTo(that.mFirstName);
		if(v != 0)
			return v;
		return this.mSecondName.compareTo(that.mSecondName);
	}
	
	 @Override
	    public int hashCode() {
	        return mFirstName.hashCode() + mSecondName.hashCode();
	    }
	 
	// ---------------------------------------------------------------------
	 
	 /**
	     * Return a String that unambiguously describes both the package and
	     * class names contained in the ComponentName.  You can later recover
	     * the ComponentName from this string through
	     * {@link #unflattenFromString(String)}.
	     * 
	     * @return Returns a new String holding the package and class names.  This
	     * is represented as the package name, concatenated with a '/' and then the
	     * class name.
	     * 
	     * @see #unflattenFromString(String)
	     */
	    public String flattenToString() {
	        return mFirstName + "/" + mSecondName;
	    }
	    
	    /**
	     * Recover a ComponentName from a String that was previously created with
	     * {@link #flattenToString()}.  It splits the string at the first '/',
	     * taking the part before as the package name and the part after as the
	     * class name.  As a special convenience (to use, for example, when
	     * parsing component names on the command line), if the '/' is immediately
	     * followed by a '.' then the final class name will be the concatenation
	     * of the package name with the string following the '/'.  Thus
	     * "com.foo/.Blah" becomes package="com.foo" class="com.foo.Blah".
	     * 
	     * @param str The String that was returned by flattenToString().
	     * @return Returns a new ComponentName containing the package and class
	     * names that were encoded in <var>str</var>
	     * 
	     * @see #flattenToString()
	     */
	    public static ComponentName unflattenFromString(String str) {
	        int sep = str.indexOf('/');
	        if (sep < 0 || (sep+1) >= str.length()) {
	            return null;
	        }
	        String pkg = str.substring(0, sep);
	        String cls = str.substring(sep+1);
	        if (cls.length() > 0 && cls.charAt(0) == '.') {
	            cls = pkg + cls;
	        }
	        return new ComponentName(pkg, cls);
	    }   
}
