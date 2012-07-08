package org.ege.utils;

import java.util.Random;

public interface Identity {
	public final int ID = new Random(System.nanoTime()).nextInt();
}
