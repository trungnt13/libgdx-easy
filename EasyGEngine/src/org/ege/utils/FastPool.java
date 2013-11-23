package org.ege.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool.Poolable;

public class FastPool<T> {
	public final int max;

	private final Array<T> freeObjects;

	private final Array<T> usingObjects;

	private final Factory<T> mFactory;

	/** Creates a pool with an initial capacity of 16 and no maximum. */
	public FastPool(Factory<T> factoy) {
		this(16, Integer.MAX_VALUE, factoy);
	}

	/** Creates a pool with the specified initial capacity and no maximum. */
	public FastPool(int initialCapacity, Factory<T> factoy) {
		this(initialCapacity, Integer.MAX_VALUE, factoy);
	}

	/**
	 * @param max
	 *            The maximum number of free objects to store in this pool.
	 */
	public FastPool(int initialCapacity, int max, Factory<T> factoy) {
		freeObjects = new Array(false, initialCapacity);
		usingObjects = new Array(false, initialCapacity);

		this.max = max;
		this.mFactory = factoy;
	}

	/**
	 * Returns an object from this pool. The object may be new (from
	 * {@link #newObject()}) or reused (previously {@link #free(Object) freed}).
	 */
	public T obtain() {
		T t = freeObjects.size == 0 ? mFactory.newObject() : freeObjects.pop();
		usingObjects.add(t);
		return t;
	}

	/**
	 * Puts the specified object in the pool, making it eligible to be returned
	 * by {@link #obtain()}. If the pool already contains {@link #max} free
	 * objects, the specified object is ignored.
	 */
	public boolean free(T object) {
		if (object == null)
			throw new IllegalArgumentException("object cannot be null.");
		if (freeObjects.contains(object, true))
			return false;
		// remove from using
		usingObjects.removeValue(object, true);
		if (freeObjects.size < max) {
			freeObjects.add(object);
			if (object instanceof Poolable)
				((Poolable) object).reset();
			return true;
		}
		return false;
	}

	/**
	 * Puts the specified object in the pool, making it eligible to be returned
	 * by {@link #obtain()}. If the pool already contains {@link #max} free
	 * objects, the specified object is ignored. <b>This method won't reset the
	 * object</b>
	 * 
	 * @return true if pool success, otherwise false
	 */
	public boolean freeNoReset(T object) {
		if (object == null)
			throw new IllegalArgumentException("object cannot be null.");

		// remove from using
		usingObjects.removeValue(object, true);
		if (freeObjects.size < max) {
			if (!freeObjects.contains(object, true))
				freeObjects.add(object);
			return true;
		}
		return false;
	}

	/**
	 * Puts the specified objects in the pool.
	 * 
	 * @see #free(Object)
	 */
	public void free(Array<T> objects) {
		for (int i = 0, n = Math.min(objects.size, max - freeObjects.size); i < n; i++) {
			T object = objects.get(i);
			// remove from using
			usingObjects.removeValue(object, true);

			if (freeObjects.contains(object, true))
				continue;

			freeObjects.add(object);
			if (object instanceof Poolable)
				((Poolable) object).reset();
		}
	}

	/**
	 * Puts the specified objects in the pool. <b>This method won't reset the
	 * object</b>
	 * 
	 * @see #free(Object)
	 */
	public void freeNoReset(Array<T> objects) {
		for (int i = 0, n = Math.min(objects.size, max - freeObjects.size); i < n; i++) {
			T object = objects.get(i);
			// remove from using
			usingObjects.removeValue(object, true);

			if (freeObjects.contains(object, true))
				continue;

			freeObjects.add(object);
		}
	}

	public void delete(T... object) {
		for (T t : object) {
			freeObjects.removeValue(t, true);
			// remove from using
			usingObjects.removeValue(t, true);
		}
	}

	public int sizeFree() {
		return freeObjects.size;
	}

	public int sizeUsing() {
		return freeObjects.size;
	}

	public Array<T> getFreeObj() {
		return freeObjects;
	}

	public Array<T> getUsingObj() {
		return usingObjects;
	}

	/** Removes all free objects from this pool. */
	public void clear() {
		final int size = freeObjects.size;
		for (int i = 0; i < size; i++) {
			// ------ free ------
			T t = freeObjects.get(i);
			if (t instanceof Disposable)
				((Disposable) t).dispose();
			// ------ using ------
			t = usingObjects.get(i);
			if (t instanceof Disposable)
				((Disposable) t).dispose();
		}
		usingObjects.clear();
		freeObjects.clear();
	}
}
