package okj.easy.core.utils;

import java.util.ArrayList;
import java.util.Iterator;

import okj.easy.core.Screen;

import org.ege.utils.OnRecycleListener;

import com.badlogic.gdx.utils.ObjectMap;

public class Bridge {

	static OnRecycleListener<Bridge>	listener	= null;

	// -------------------------------------------------------------

	private String						mName;
	private ObjectMap<String, Object>	mMap;

	// -------------------------------------------------------------

	/*-------------------------------------------------------------*/

	Bridge (Class<?> firstClass, Class<?> secondClass) {
		mName = firstClass.getName() + secondClass.getName();
		mMap = new ObjectMap<String, Object>();
	}

	Bridge (String name) {
		mName = name;
		mMap = new ObjectMap<String, Object>();
	}

	Bridge set (String name) {
		this.mName = name;
		return this;
	}

	Bridge set (Class c1, Class c2) {
		mName = c1.getName() + c2.getName();
		return this;
	}

	public static void registerRecyleListener (OnRecycleListener<Bridge> listeners) {
		if (listener != null)
			return;
		listener = listeners;
	}

	public static void unregisterRecycleListener () {
		listener = null;
	}

	/*-------------------------------------------------------------*/

	public int size () {
		return this.mMap.size;
	}

	public String getName () {
		return mName;
	}

	public boolean isEmpty () {
		return mMap.size == 0 ? true : false;
	}

	public boolean containsKey (String key) {
		return mMap.containsKey(key);
	}

	public boolean equals (Bridge obj) {
		return obj.mName.equals(mName);
	}

	public boolean equals (Class<?> firstClass, Class<?> secondClass) {
		return mName.equals(firstClass.getName() + secondClass.getName());
	}

	public boolean equals (String name) {
		return mName.equals(name);
	}

	/*-------------------------------------------------------------*/

	public ObjectMap<String, Object> getMap () {
		return this.mMap;
	}

	public Iterator<String> keySet () {
		return this.mMap.keys();
	}

	public Iterator<Object> valueSet () {
		return this.mMap.values();
	}

	/*-------------------------------------------------------------*/

	public void putAll (ObjectMap<String, Object> map) {
		this.mMap.putAll(map);
	}

	public void putAll (Bridge bridge) {
		this.mMap.putAll(bridge.getMap());
	}

	public void putBoolean (String key, boolean value) {
		mMap.put(key, value);
	}

	public void putChar (String key, char value) {
		mMap.put(key, value);
	}

	public void putShort (String key, short value) {
		mMap.put(key, value);
	}

	public void putInt (String key, int value) {
		mMap.put(key, value);
	}

	public void putLong (String key, long value) {
		mMap.put(key, value);
	}

	public void putFloat (String key, float value) {
		mMap.put(key, value);
	}

	public void putDouble (String key, double value) {
		mMap.put(key, value);
	}

	public void putString (String key, String value) {
		mMap.put(key, value);
	}

	public void putByte (String key, byte value) {
		mMap.put(key, value);
	}

	public void putCharSequence (String key, CharSequence value) {
		mMap.put(key, value);
	}

	public void putNextScreen (String key, Screen nextScreen) {
		mMap.put(key, nextScreen);
	}

	public <K, V> void putMap (String key, ObjectMap<K, V> value) {
		mMap.put(key, value);
	}

	/**
	 * You should more carefull about data type when use this method, I prefer
	 * to use : (int)1, 1.0f,1L ...
	 * 
	 * @param key
	 *            string key of object
	 * @param value
	 *            your object
	 */
	public void put (String key, Object value) {
		mMap.put(key, value);
	}

	/*-------------------------------------------------------------*/

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return an ArrayList<String> value, or null
	 */
	public ArrayList<Integer> getIntegerArrayList (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (ArrayList<Integer>) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return an ArrayList<String> value, or null
	 */
	public ArrayList<String> getStringArrayList (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (ArrayList<String>) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return a boolean[] value, or null
	 */
	public boolean[] getBooleanArray (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (boolean[]) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return a byte[] value, or null
	 */
	public byte[] getByteArray (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (byte[]) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return a short[] value, or null
	 */
	public short[] getShortArray (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (short[]) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return a char[] value, or null
	 */
	public char[] getCharArray (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (char[]) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return an int[] value, or null
	 */
	public int[] getIntArray (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (int[]) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return a long[] value, or null
	 */
	public long[] getLongArray (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (long[]) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return a float[] value, or null
	 */
	public float[] getFloatArray (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (float[]) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return a double[] value, or null
	 */
	public double[] getDoubleArray (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (double[]) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return a String[] value, or null
	 */
	public String[] getStringArray (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (String[]) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return a ObjectMap value, or null
	 */

	public <K, V> ObjectMap<K, V> getObjectMap (String key) {
		Object o = mMap.get(key);
		if (o == null)
			return null;
		try {
			return (ObjectMap<K, V>) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or false if no mapping
	 * of the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a boolean value
	 */
	public boolean getBoolean (String key) {
		return getBoolean(key, false);
	}

	/**
	 * Returns the value associated with the given key, or defaultValue if no
	 * mapping of the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a boolean value
	 */
	public boolean getBoolean (String key, boolean defaultValue) {
		Object o = mMap.get(key);
		if (o == null) {
			return defaultValue;
		}
		try {
			return (Boolean) o;
		} catch (ClassCastException e) {
			return defaultValue;
		}
	}

	/**
	 * Returns the value associated with the given key, or (byte) 0 if no
	 * mapping of the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a byte value
	 */
	public byte getByte (String key) {
		return getByte(key, (byte) 0);
	}

	/**
	 * Returns the value associated with the given key, or defaultValue if no
	 * mapping of the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a byte value
	 */
	public Byte getByte (String key, byte defaultValue) {
		Object o = mMap.get(key);
		if (o == null) {
			return defaultValue;
		}
		try {
			return (Byte) o;
		} catch (ClassCastException e) {
			return defaultValue;
		}
	}

	/**
	 * Returns the value associated with the given key, or false if no mapping
	 * of the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a char value
	 */
	public char getChar (String key) {
		return getChar(key, (char) 0);
	}

	/**
	 * Returns the value associated with the given key, or (char) 0 if no
	 * mapping of the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a char value
	 */
	public char getChar (String key, char defaultValue) {
		Object o = mMap.get(key);
		if (o == null) {
			return defaultValue;
		}
		try {
			return (Character) o;
		} catch (ClassCastException e) {
			return defaultValue;
		}
	}

	/**
	 * Returns the value associated with the given key, or (short) 0 if no
	 * mapping of the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a short value
	 */
	public short getShort (String key) {
		return getShort(key, (short) 0);
	}

	/**
	 * Returns the value associated with the given key, or defaultValue if no
	 * mapping of the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a short value
	 */
	public short getShort (String key, short defaultValue) {
		Object o = mMap.get(key);
		if (o == null) {
			return defaultValue;
		}
		try {
			return (Short) o;
		} catch (ClassCastException e) {
			return defaultValue;
		}
	}

	/**
	 * Returns the value associated with the given key, or 0 if no mapping of
	 * the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return an int value
	 */
	public int getInt (String key) {
		return getInt(key, 0);
	}

	/**
	 * Returns the value associated with the given key, or defaultValue if no
	 * mapping of the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return an int value
	 */
	public int getInt (String key, int defaultValue) {
		Object o = mMap.get(key);
		if (o == null) {
			return defaultValue;
		}
		try {
			return (Integer) o;
		} catch (ClassCastException e) {
			return defaultValue;
		}
	}

	/**
	 * Returns the value associated with the given key, or 0L if no mapping of
	 * the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a long value
	 */
	public long getLong (String key) {
		return getLong(key, 0L);
	}

	/**
	 * Returns the value associated with the given key, or defaultValue if no
	 * mapping of the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a long value
	 */
	public long getLong (String key, long defaultValue) {
		Object o = mMap.get(key);
		if (o == null) {
			return defaultValue;
		}
		try {
			return (Long) o;
		} catch (ClassCastException e) {
			return defaultValue;
		}
	}

	/**
	 * Returns the value associated with the given key, or 0.0f if no mapping of
	 * the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a float value
	 */
	public float getFloat (String key) {
		return getFloat(key, 0.0f);
	}

	/**
	 * Returns the value associated with the given key, or defaultValue if no
	 * mapping of the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a float value
	 */
	public float getFloat (String key, float defaultValue) {
		Object o = mMap.get(key);
		if (o == null) {
			return defaultValue;
		}
		try {
			return (Float) o;
		} catch (ClassCastException e) {
			return defaultValue;
		}
	}

	/**
	 * Returns the value associated with the given key, or 0.0 if no mapping of
	 * the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a double value
	 */
	public double getDouble (String key) {
		return getDouble(key, 0.0);
	}

	/**
	 * Returns the value associated with the given key, or defaultValue if no
	 * mapping of the desired type exists for the given key.
	 * 
	 * @param key
	 *            a String
	 * @return a double value
	 */
	public double getDouble (String key, double defaultValue) {
		Object o = mMap.get(key);
		if (o == null) {
			return defaultValue;
		}
		try {
			return (Double) o;
		} catch (ClassCastException e) {
			return defaultValue;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return a String value, or null
	 */
	public String getString (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (String) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * Returns the value associated with the given key, or null if no mapping of
	 * the desired type exists for the given key or a null value is explicitly
	 * associated with the key.
	 * 
	 * @param key
	 *            a String, or null
	 * @return a CharSequence value, or null
	 */
	public CharSequence getCharSequence (String key) {
		Object o = mMap.get(key);
		if (o == null) {
			return null;
		}
		try {
			return (CharSequence) o;
		} catch (ClassCastException e) {
			return null;
		}
	}

	/**
	 * This method will return Object and you should carefull when cast it to
	 * class
	 * 
	 * @param key
	 * @return
	 */
	public Object get (String key) {
		return mMap.get(key);
	}

	public void clear () {
		mMap.clear();
		listener.RecycleObject(this);
	}
}
