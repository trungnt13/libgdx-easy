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
}
