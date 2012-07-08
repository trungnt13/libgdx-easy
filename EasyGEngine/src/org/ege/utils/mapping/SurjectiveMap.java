package org.ege.utils.mapping;


import java.util.Iterator;

import com.badlogic.gdx.utils.ObjectMap;

public class SurjectiveMap<K,V> {
	
	private ObjectMap<K, V> mainMap = new ObjectMap<K, V>();
	private ObjectMap<V, K> helperMap = new ObjectMap<V, K>();
	
	private boolean keyIdentity = false;
	private boolean valueIdentity = false;
	
	/**
	 * Construct new Surjective Map with given configuration, remember:
	 * true is use " == " , false is use " equal(obj) "
	 * @param isKeyIdentity if true, uses " == " to compare the specified key , otherwise uses " equal " to compare the specified key
	 * @param isValueIdentity if true, uses " == " to compare the specified value , otherwise uses " equal " to compare the specified value
	 */
	public SurjectiveMap(boolean isKeyIdentity,boolean isValueIdentity){
		this.keyIdentity = isKeyIdentity;
		this.valueIdentity = isValueIdentity;
	}
	
	public  boolean put(K key,V value){
		if(key == null || value == null ) 
			return false;
		
		removeKey(key);
		removeValue(value);
		
		mainMap.put(key	, value);
		helperMap.put(value,key);
		return true;
	}
	
	public  boolean containKey(K key){
		return this.helperMap.containsValue(key, keyIdentity);
	}
	
	public  boolean containValue(V value){
		return this.mainMap.containsValue(value, valueIdentity);
	}
	
	public  K getKey(V value){
		return this.helperMap.get(value);
	}
	
	public  V getValue(K key){
		return this.mainMap.get(key);
	}
	
	public  boolean removeKey(K key){
		if(helperMap.containsValue(key, keyIdentity)){
			helperMap.remove(mainMap.get(key));
			mainMap.remove(key);
			return true;
		}
		return false;
	}
	
	public  boolean removeValue(V value){
		if(mainMap.containsValue(value, valueIdentity)){
			mainMap.remove(helperMap.get(value));
			helperMap.remove(value);
			return true;
		}
		return false;
	}
	
	public  void clear(){
		mainMap.clear();
		helperMap.clear();
	}
	
	public int size(){
		return this.mainMap.size;
	}
	
	public int size1(){
		return this.helperMap.size;
	}
	
	public Iterator<V> values(){
		return mainMap.values();
	}
	
	public Iterator<K> keys(){
		return mainMap.keys();
	}
}
