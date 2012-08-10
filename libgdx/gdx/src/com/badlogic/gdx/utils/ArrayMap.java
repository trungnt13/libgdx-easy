/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectMap.Entry;

/** An ordered or unordered map of objects. This implementation uses arrays to store the keys and values, which means
 * {@link #getKey(Object, boolean) gets} do a comparison for each key in the map. This may be acceptable for small maps and has the
 * benefits that keys and values can be accessed by index, which makes iteration fast. Like {@link Array}, if ordered is false,
 * this class avoids a memory copy when removing elements (the last element is moved to the removed element's position).
 * @author Nathan Sweet */
public class ArrayMap<K, V> {
	public K[] keys;
	public V[] values;
	public int size;
	public boolean ordered;

	private Entries entries;
	private Values valuesIter;
	private Keys keysIter;

	/** Creates an ordered map with a capacity of 16. */
	public ArrayMap () {
		this(true, 16);
	}

	/** Creates an ordered map with the specified capacity. */
	public ArrayMap (int capacity) {
		this(true, capacity);
	}

	/** @param ordered If false, methods that remove elements may change the order of other elements in the arrays, which avoids a
	 *           memory copy.
	 * @param capacity Any elements added beyond this will cause the backing arrays to be grown. */
	public ArrayMap (boolean ordered, int capacity) {
		this.ordered = ordered;
		keys = (K[])new Object[capacity];
		values = (V[])new Object[capacity];
	}

	/** Creates a new map with {@link #keys} and {@link #values} of the specified type.
	 * @param ordered If false, methods that remove elements may change the order of other elements in the arrays, which avoids a
	 *           memory copy.
	 * @param capacity Any elements added beyond this will cause the backing arrays to be grown. */
	public ArrayMap (boolean ordered, int capacity, Class<K> keyArrayType, Class<V> valueArrayType) {
		this.ordered = ordered;
		keys = (K[])java.lang.reflect.Array.newInstance(keyArrayType, capacity);
		values = (V[])java.lang.reflect.Array.newInstance(valueArrayType, capacity);
	}

	/** Creates an ordered map with {@link #keys} and {@link #values} of the specified type and a capacity of 16. */
	public ArrayMap (Class<K> keyArrayType, Class<V> valueArrayType) {
		this(false, 16, keyArrayType, valueArrayType);
	}

	/** Creates a new map containing the elements in the specified map. The new map will have the same type of backing arrays and
	 * will be ordered if the specified map is ordered. The capacity is set to the number of elements, so any subsequent elements
	 * added will cause the backing arrays to be grown. */
	public ArrayMap (ArrayMap array) {
		this(array.ordered, array.size, (Class<K>)array.keys.getClass().getComponentType(), (Class<V>)array.values.getClass()
			.getComponentType());
		size = array.size;
		System.arraycopy(array.keys, 0, keys, 0, size);
		System.arraycopy(array.values, 0, values, 0, size);
	}

	public void put (K key, V value) {
		if (size == keys.length) resize(Math.max(8, (int)(size * 1.75f)));
		int index = indexOfKey(key);
		if (index == -1) index = size++;
		keys[index] = key;
		values[index] = value;
	}

	public void put (K key, V value, int index) {
		if (size == keys.length) resize(Math.max(8, (int)(size * 1.75f)));
		int existingIndex = indexOfKey(key);
		if (existingIndex != -1) removeIndex(existingIndex);
		System.arraycopy(keys, index, keys, index + 1, size - index);
		System.arraycopy(values, index, values, index + 1, size - index);
		keys[index] = key;
		values[index] = value;
		size++;
	}

	public void addAll (ArrayMap map) {
		addAll(map, 0, map.size);
	}

	public void addAll (ArrayMap map, int offset, int length) {
		if (offset + length > map.size)
			throw new IllegalArgumentException("offset + length must be <= size: " + offset + " + " + length + " <= " + map.size);
		int sizeNeeded = size + length - offset;
		if (sizeNeeded >= keys.length) resize(Math.max(8, (int)(sizeNeeded * 1.75f)));
		System.arraycopy(map.keys, offset, keys, size, length);
		System.arraycopy(map.values, offset, values, size, length);
		size += length;
	}

	/** Returns the value for the specified key. Note this does a .equals() comparison of each key in reverse order until the
	 * specified key is found. */
	public V get (K key) {
		Object[] keys = this.keys;
		int i = size - 1;
		if (key == null) {
			for (; i >= 0; i--)
				if (keys[i] == key) return values[i];
		} else {
			for (; i >= 0; i--)
				if (key.equals(keys[i])) return values[i];
		}
		return null;
	}

	/** Returns the key for the specified value. Note this does a comparison of each value in reverse order until the specified
	 * value is found.
	 * @param identity If true, == comparison will be used. If false, .equals() comaparison will be used. */
	public K getKey (V value, boolean identity) {
		Object[] values = this.values;
		int i = size - 1;
		if (identity || values == null) {
			for (; i >= 0; i--)
				if (values[i] == values) return keys[i];
		} else {
			for (; i >= 0; i--)
				if (values.equals(values[i])) return keys[i];
		}
		return null;
	}

	public K getKeyAt (int index) {
		if (index >= size) throw new IndexOutOfBoundsException(String.valueOf(index));
		return keys[index];
	}

	public V getValueAt (int index) {
		if (index >= size) throw new IndexOutOfBoundsException(String.valueOf(index));
		return values[index];
	}

	public void setKey (int index, K key) {
		if (index >= size) throw new IndexOutOfBoundsException(String.valueOf(index));
		keys[index] = key;
	}

	public void setValue (int index, V value) {
		if (index >= size) throw new IndexOutOfBoundsException(String.valueOf(index));
		values[index] = value;
	}

	public void insert (int index, K key, V value) {
		if (size == keys.length) resize(Math.max(8, (int)(size * 1.75f)));
		if (ordered) {
			System.arraycopy(keys, index, keys, index + 1, size - index);
			System.arraycopy(values, index, values, index + 1, size - index);
		} else {
			keys[size] = keys[index];
			values[size] = values[index];
		}
		size++;
		keys[index] = key;
		values[index] = value;
	}

	public boolean containsKey (K key) {
		K[] keys = this.keys;
		int i = size - 1;
		if (key == null) {
			while (i >= 0)
				if (keys[i--] == key) return true;
		} else {
			while (i >= 0)
				if (key.equals(keys[i--])) return true;
		}
		return false;
	}

	/** @param identity If true, == comparison will be used. If false, .equals() comaparison will be used. */
	public boolean containsValue (V value, boolean identity) {
		V[] values = this.values;
		int i = size - 1;
		if (identity || value == null) {
			while (i >= 0)
				if (values[i--] == value) return true;
		} else {
			while (i >= 0)
				if (value.equals(values[i--])) return true;
		}
		return false;
	}

	public int indexOfKey (K key) {
		Object[] keys = this.keys;
		if (key == null) {
			for (int i = 0, n = size; i < n; i++)
				if (keys[i] == key) return i;
		} else {
			for (int i = 0, n = size; i < n; i++)
				if (key.equals(keys[i])) return i;
		}
		return -1;
	}

	public int indexOfValue (V value, boolean identity) {
		Object[] values = this.values;
		if (identity || value == null) {
			for (int i = 0, n = size; i < n; i++)
				if (values[i] == value) return i;
		} else {
			for (int i = 0, n = size; i < n; i++)
				if (value.equals(values[i])) return i;
		}
		return -1;
	}

	public V removeKey (K key) {
		Object[] keys = this.keys;
		if (key == null) {
			for (int i = 0, n = size; i < n; i++) {
				if (keys[i] == key) {
					V value = values[i];
					removeIndex(i);
					return value;
				}
			}
		} else {
			for (int i = 0, n = size; i < n; i++) {
				if (key.equals(keys[i])) {
					V value = values[i];
					removeIndex(i);
					return value;
				}
			}
		}
		return null;
	}

	public boolean removeValue (V value, boolean identity) {
		Object[] values = this.values;
		if (identity || value == null) {
			for (int i = 0, n = size; i < n; i++) {
				if (values[i] == value) {
					removeIndex(i);
					return true;
				}
			}
		} else {
			for (int i = 0, n = size; i < n; i++) {
				if (value.equals(values[i])) {
					removeIndex(i);
					return true;
				}
			}
		}
		return false;
	}

	/** Removes and returns the key/values pair at the specified index. */
	public void removeIndex (int index) {
		if (index >= size) throw new IndexOutOfBoundsException(String.valueOf(index));
		Object[] keys = this.keys;
		size--;
		if (ordered) {
			System.arraycopy(keys, index + 1, keys, index, size - index);
			System.arraycopy(values, index + 1, values, index, size - index);
		} else {
			keys[index] = keys[size];
			values[index] = values[size];
		}
		keys[size] = null;
		values[size] = null;
	}

	/** Returns the last key. */
	public K peekKey () {
		return keys[size - 1];
	}

	/** Returns the last value. */
	public V peekValue () {
		return values[size - 1];
	}

	public void clear () {
		K[] keys = this.keys;
		V[] values = this.values;
		for (int i = 0, n = size; i < n; i++) {
			keys[i] = null;
			values[i] = null;
		}
		size = 0;
	}

	/** Reduces the size of the backing arrays to the size of the actual number of entries. This is useful to release memory when
	 * many items have been removed, or if it is known that more entries will not be added. */
	public void shrink () {
		resize(size);
	}

	/** Increases the size of the backing arrays to acommodate the specified number of additional entries. Useful before adding many
	 * entries to avoid multiple backing array resizes. */
	public void ensureCapacity (int additionalCapacity) {
		int sizeNeeded = size + additionalCapacity;
		if (sizeNeeded >= keys.length) resize(Math.max(8, sizeNeeded));
	}

	protected void resize (int newSize) {
		K[] newKeys = (K[])java.lang.reflect.Array.newInstance(keys.getClass().getComponentType(), newSize);
		System.arraycopy(keys, 0, newKeys, 0, Math.min(keys.length, newKeys.length));
		this.keys = newKeys;

		V[] newValues = (V[])java.lang.reflect.Array.newInstance(values.getClass().getComponentType(), newSize);
		System.arraycopy(values, 0, newValues, 0, Math.min(values.length, newValues.length));
		this.values = newValues;
	}

	public void reverse () {
		for (int i = 0, lastIndex = size - 1, n = size / 2; i < n; i++) {
			int ii = lastIndex - i;
			K tempKey = keys[i];
			keys[i] = keys[ii];
			keys[ii] = tempKey;

			V tempValue = values[i];
			values[i] = values[ii];
			values[ii] = tempValue;
		}
	}

	public void shuffle () {
		for (int i = size - 1; i >= 0; i--) {
			int ii = MathUtils.random(i);
			K tempKey = keys[i];
			keys[i] = keys[ii];
			keys[ii] = tempKey;

			V tempValue = values[i];
			values[i] = values[ii];
			values[ii] = tempValue;
		}
	}

	/** Reduces the size of the arrays to the specified size. If the arrays are already smaller than the specified size, no action
	 * is taken. */
	public void truncate (int newSize) {
		if (size <= newSize) return;
		for (int i = newSize; i < size; i++) {
			keys[i] = null;
			values[i] = null;
		}
		size = newSize;
	}

	public String toString () {
		if (size == 0) return "{}";
		K[] keys = this.keys;
		V[] values = this.values;
		StringBuilder buffer = new StringBuilder(32);
		buffer.append('{');
		buffer.append(keys[0]);
		buffer.append('=');
		buffer.append(values[0]);
		for (int i = 1; i < size; i++) {
			buffer.append(", ");
			buffer.append(keys[i]);
			buffer.append('=');
			buffer.append(values[i]);
		}
		buffer.append('}');
		return buffer.toString();
	}

	/** Returns an iterator for the entries in the map. Remove is supported. Note that the same iterator instance is returned each
	 * time this method is called. Use the {@link Entries} constructor for nested or multithreaded iteration. */
	public Entries<K, V> entries () {
		if (entries == null)
			entries = new Entries(this);
		else
			entries.reset();
		return entries;
	}

	/** Returns an iterator for the values in the map. Remove is supported. Note that the same iterator instance is returned each
	 * time this method is called. Use the {@link Entries} constructor for nested or multithreaded iteration. */
	public Values<V> values () {
		if (valuesIter == null)
			valuesIter = new Values(this);
		else
			valuesIter.reset();
		return valuesIter;
	}

	/** Returns an iterator for the keys in the map. Remove is supported. Note that the same iterator instance is returned each time
	 * this method is called. Use the {@link Entries} constructor for nested or multithreaded iteration. */
	public Keys<K> keys () {
		if (keysIter == null)
			keysIter = new Keys(this);
		else
			keysIter.reset();
		return keysIter;
	}

	static public class Entries<K, V> implements Iterable<Entry<K, V>>, Iterator<Entry<K, V>> {
		private final ArrayMap<K, V> map;
		Entry<K, V> entry = new Entry();
		int index;

		public Entries (ArrayMap<K, V> map) {
			this.map = map;
		}

		public boolean hasNext () {
			return index < map.size;
		}

		public Iterator<Entry<K, V>> iterator () {
			return this;
		}

		public Entry<K, V> next () {
			if (index >= map.size) throw new NoSuchElementException(String.valueOf(index));
			entry.key = map.keys[index];
			entry.value = map.values[index++];
			return entry;
		}

		public void remove () {
			index--;
			map.removeIndex(index);
		}

		public void reset () {
			index = 0;
		}
	}

	static public class Values<V> implements Iterable<V>, Iterator<V> {
		private final ArrayMap<Object, V> map;
		int index;

		public Values (ArrayMap<Object, V> map) {
			this.map = map;
		}

		public boolean hasNext () {
			return index < map.size;
		}

		public Iterator<V> iterator () {
			return this;
		}

		public V next () {
			if (index >= map.size) throw new NoSuchElementException(String.valueOf(index));
			return map.values[index++];
		}

		public void remove () {
			index--;
			map.removeIndex(index);
		}

		public void reset () {
			index = 0;
		}
	}

	static public class Keys<K> implements Iterable<K>, Iterator<K> {
		private final ArrayMap<K, Object> map;
		int index;

		public Keys (ArrayMap<K, Object> map) {
			this.map = map;
		}

		public boolean hasNext () {
			return index < map.size;
		}

		public Iterator<K> iterator () {
			return this;
		}

		public K next () {
			if (index >= map.size) throw new NoSuchElementException(String.valueOf(index));
			return map.keys[index++];
		}

		public void remove () {
			index--;
			map.removeIndex(index);
		}

		public void reset () {
			index = 0;
		}
	}
}