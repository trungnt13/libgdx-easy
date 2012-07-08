package okj.easy.core.utils;

import org.ege.utils.OnRecycleListener;

import vn.easy.content.ComponentName;

import com.badlogic.gdx.utils.Array;


public class BridgePool implements OnRecycleListener<Bridge>{
	public final int max;

	protected final Array<Bridge> freeObjects;
	private Array<Bridge> usingBridge;
	
	/** Creates a pool with an initial capacity of 16 and no maximum. */
	public BridgePool () {
		this(16, Integer.MAX_VALUE);
	}

	/** Creates a pool with the specified initial capacity and no maximum. */
	public BridgePool (int initialCapacity) {
		this(initialCapacity, Integer.MAX_VALUE);
	}

	/** @param max The maximum number of free objects to store in this pool. */
	public BridgePool (int initialCapacity, int max) {
		freeObjects = new Array<Bridge>(false, initialCapacity);
		usingBridge = new Array<Bridge>(false, initialCapacity);
		this.max = max;
	}

	/***************************************************************
	 * 
	 ***************************************************************/

	protected Bridge newObject(String firstName,String lastName) {
		return new  Bridge(firstName,lastName);
	}
	
	
	public Bridge obtain(Class<?> firstClass,Class<?> secondClass){
		Bridge tmp =  freeObjects.size == 0 ? newObject(firstClass.getName(),secondClass.getName()) : 
											  freeObjects.pop().set(new ComponentName(firstClass.getName(), secondClass.getName()));
		this.usingBridge.add(tmp);
		return tmp;
	}
	
	public Bridge obtain(String firstName,String secondName){
		Bridge tmp = freeObjects.size == 0 ? newObject(firstName,secondName) : 
											 freeObjects.pop().set(new ComponentName(firstName, secondName));
		this.usingBridge.add(tmp);
		return tmp;
	}

	/***************************************************************
	 * 
	 ***************************************************************/
	
	public void free (Bridge object) {
		if (object == null) throw new IllegalArgumentException("object cannot be null.");
		if (freeObjects.size < max) freeObjects.add(object);
		this.usingBridge.removeValue(object, false);
	}

	/** Puts the specified objects in the pool.
	 * @see #free(Object) */
	public void free (Array<Bridge> objects) {
		for (int i = 0, n = Math.min(objects.size, max - freeObjects.size); i < n; i++)
			freeObjects.add(objects.get(i));
		for(int i = 0; i < objects.size;i++){
			this.usingBridge.removeValue(objects.get(i), false);
		}
	}
	
	/***************************************************************
	 * 
	 ***************************************************************/
	
	/** Removes all free objects from this pool. */
	public void clear () {
		freeObjects.clear();
		this.usingBridge.clear();
	}
	
	public Array<Bridge> getUsingBridges(){
		return this.usingBridge;
	}

	@Override
	public void RecycleObject(Bridge recycleObject) {
		free(recycleObject);
	}
	
}
