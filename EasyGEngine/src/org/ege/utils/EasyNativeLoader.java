package org.ege.utils;



public class EasyNativeLoader {
	static public boolean isWindows = System.getProperty("os.name").contains("Windows");
	static public boolean isLinux = System.getProperty("os.name").contains("Linux");

	public static void load(){
		if(isLinux){
			try{
				System.loadLibrary("enative");
			}catch (UnsatisfiedLinkError e) {
				System.load("/home/trung/EngineResearchTeam/libgdx-easy/EasyGEngine/libs/libenative.so");
			}
		}else if(isWindows)
			throw new UnsupportedOperationException("I haven't support native for window");
	}

	// build script in linux 64 bit
	// g++ -I"/usr/lib/jvm/java-7-orcale/include" -I"/usr/lib/jvm/java-7-orcale/include/linux" -fPIC -o libenative.so -shared  utils.cpp utils/Array.cpp utils/GridSimulation.cpp utils/math/eMath.cpp utils/math/Vector2.cpp CollisionProcessor.cpp SpriteProcessor.cpp org_ege_utils_CollisionChecker.cpp org_ege_utils_SpriteUtils.cpp 
	// jni linux 64 bit
	// /usr/lib/jvm/java-7-oracle/include/jni.h
}
