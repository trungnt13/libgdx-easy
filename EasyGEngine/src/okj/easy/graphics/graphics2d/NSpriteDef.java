package okj.easy.graphics.graphics2d;

import org.ege.utils.exception.EasyGEngineRuntimeException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Disposable;

/**
 * 
 * NSpriteDef.java
 * 
 * Created on: Oct 6, 2012
 * Author: Trung
 */
public class NSpriteDef implements Disposable {
	public final String name;
	final long address;

	int mSpriteCount = 0;

	private boolean isDisposed = false;
	private final NWorld world;

	// ========================================

	NSpriteDef(String name, long address, NWorld world) {
		this.name = name;
		this.address = address;
		this.world = world;
	}

	public int getSpriteCount () {
		return mSpriteCount;
	}

	/******************************************************
	 * 
	 ******************************************************/

	/**
	 * add new polygon to sprite def, this polygon won't added to list if it
	 * local vertices are the same with existed polygon in list
	 */
	public void addPolygon (Polygon polygon) {
		if (isDisposed)
			return;
		final float[] vertices = polygon.getVertices();
		final int[] noIndex = polygon.getNoIndex();
		addBounding(address, vertices, vertices.length, noIndex, noIndex.length);
	}

	/**
	 * add new polygon to sprite def, this polygon won't added to list if it
	 * local vertices are the same with existed polygon in list
	 */
	public void addPolygon (float[] vertices) {
		if (isDisposed)
			return;

		if (vertices.length < 6 || vertices.length % 2 != 0)
			throw new EasyGEngineRuntimeException("Vertices of your polygon have wrong length");

		addBounding(address, vertices, vertices.length, null, 0);
	}

	/**
	 * add new polygon to sprite def, this polygon won't added to list if it
	 * local vertices are the same with existed polygon in list
	 */
	public void addPolygon (float[] vertices, int noIndex[]) {
		if (vertices.length < 6 || vertices.length % 2 != 0)
			throw new EasyGEngineRuntimeException("Vertices of your polygon have wrong length");

		addBounding(address, vertices, vertices.length, noIndex, noIndex.length);
	}

	/** remove the given polygon with specify index position in polygon list */
	public void removePolygon (int index) {
		removeBounding(address, index);
	}

	/**
	 * Clear all polygon in this sprite def ,for reuse sprite def with given
	 * name
	 */
	public void clearBounding () {
		clearBounding(address);
	}

	/** Returns the number of polygon in this sprite def */
	public int size () {
		return size(address);
	}

	/** Compare two spritedef */
	public boolean equal (NSpriteDef def) {
		return equal(address, def.address);
	}

	public void dispose () {
		if (mSpriteCount == 0) {
			world.deleteSpriteDef(name);
			isDisposed = true;
		} else
			Gdx.app.log("EasyGameEngine  ", "You can't delete NSpriteDef with name : " + name
					+ "  because still have NSprite associate with it");
	}

	// =====================================
	// native method

	private final native boolean equal (long thisAddress, long spriteDefAddress);

	private final native void addBounding (long address, float vertices[], int verticesSize,
			int noIndex[], int noIndexSize);

	private final native void removeBounding (long address, int indexOfBounding);

	private final native int size (long address);

	private final native void clearBounding (long address);

}
