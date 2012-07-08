package org.ege.utils;

public enum Orientation {
	HORIZONTAL(1),
	VERTICAL(2),
	LANDSCAPE(1),
	PORTRAIT(2);
	
	public final int ID;
	
	private Orientation (int id) {
		ID = id;
	}
}
