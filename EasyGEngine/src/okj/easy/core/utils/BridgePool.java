package okj.easy.core.utils;

import org.ege.utils.OnRecycleListener;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;


public class BridgePool implements OnRecycleListener<Bridge>{
	public final int max;

	private final Array<Bridge> freeObjects;
	private final ObjectMap<String, Bridge> usingBridge;
	
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
		usingBridge = new ObjectMap<String, Bridge>();
		this.max = max;
	}

	/***************************************************************
	 * 
	 ***************************************************************/

	protected Bridge newObject(String name) {
		return new  Bridge(name);
	}
	
	protected Bridge newObject(Class c1,Class c2){
		return new  Bridge(c1, c2);
	}
	
	public Bridge obtain(Class<?> firstClass,Class<?> secondClass){
		Bridge tmp =  freeObjects.size == 0 ? newObject(firstClass,secondClass) : 
											  freeObjects.pop().set(firstClass, secondClass);
		this.usingBridge.put(tmp.getName(), tmp);
		return tmp;
	}
	
	public Bridge obtain(String name){
		Bridge tmp = freeObjects.size == 0 ? newObject(name) : 
											 freeObjects.pop().set(name);
		this.usingBridge.put(tmp.getName(), tmp);
		return tmp;
	}

	/***************************************************************
	 * 
	 ***************************************************************/
	
	public void free (Bridge object) {
		if (object == null) throw new IllegalArgumentException("object cannot be null.");
		if (freeObjects.size < max) freeObjects.add(object);
		this.usingBridge.remove(object.getName());
	}

	/** Puts the specified objects in the pool.
	 * @see #free(Object) */
	public void free (Array<Bridge> objects) {
		for (int i = 0, n = Math.min(objects.size, max - freeObjects.size); i < n; i++)
			freeObjects.add(objects.get(i));
		for(int i = 0; i < objects.size;i++){
			this.usingBridge.remove(objects.get(i).getName());
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
	
	public ObjectMap<String, Bridge> getUsingBridges(){
		return this.usingBridge;
	}

	@Override
	public void RecycleObject(Bridge recycleObject) {
		free(recycleObject);
	}
	
}
