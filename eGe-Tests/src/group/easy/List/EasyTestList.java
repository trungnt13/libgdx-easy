package group.easy.List;

import group.easy.tests.ArtAndAlbumLoaderTest;
import group.easy.tests.BridgeTest1;
import group.easy.tests.JsonReaderTest;
import group.easy.tests.MultiThreadGdx;
import group.easy.tests.NumberDrawerTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okj.easy.core.Screen;

public class EasyTestList {
	public static final Class[] tests = {/*Class List Here*/
		MultiThreadGdx.class,BridgeTest1.class,
		JsonReaderTest.class,NumberDrawerTest.class,
		ArtAndAlbumLoaderTest.class
		
	};
	
	public static String[] getNames(){
		List<String> names = new ArrayList<String>();
		
		for(Class clazz: tests)
			names.add(clazz.getSimpleName());
		Collections.sort(names);
		return names.toArray(new String[names.size()]);
	}
	
	public static Screen newTest(String testName){
		try{
			Class clazz = Class.forName("group.easy.tests." + testName);
			return (Screen) clazz.newInstance();
		}catch (Exception _e) {
			_e.printStackTrace();
			return null;
		}
	}
}
