package stu.tnt.gdx.utils.mapping;

import java.util.Iterator;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * You will add object to this map in order : 0 -1 -2 -3 -4 -5 -6 ....
 * @author Ngo Trong TRung
 *
 * @param <E>
 */
public class CountUpMap<E> {
	private final ObjectMap<Integer, E> mMap = new ObjectMap<Integer, E>(); 
	private int mNum = 0;
	
	public void put(E e){
		mMap.put(mNum, e);
		mNum ++;
	}
	
	public E getValue(int id){
		return mMap.get(id);
	}
	
	public int getKey(E e){
		return mMap.findKey(e, true);
	}
	
	public E removeValue(E e){
		mNum--;
		return mMap.remove(mMap.findKey(e, true));
	}
	
	public E remove(int id){
		mNum--;
		return mMap.remove(id);
	}
	
	public Iterator<E> values(){
		return mMap.values();
	}

	public int size(){
		return mNum;
	}
	
	public void clear(){
		mMap.clear();
		mNum = 0;
	}
	
}
