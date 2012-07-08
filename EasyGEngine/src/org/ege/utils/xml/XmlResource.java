package org.ege.utils.xml;

import java.util.ArrayList;

import org.ege.utils.helper.StringHelper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;

final class XmlResource {
	static final ObjectMap<Integer, String> $ELEMENT = new ObjectMap<Integer, String>();

	final String $FILE = "res/x";
	
	XmlResource(){
		load();
	}

	private void load() {
		FileHandle file = Gdx.files.internal($FILE);
		String textRaw = file.readString();
		
		String element = textRaw.substring(textRaw.indexOf("{")+1, textRaw.indexOf("}"));
		
		readELEMENT(element);
	}

	private void readELEMENT(String element) {
		ArrayList<String> tmp = StringHelper.readLine(element);
		StringBuilder builder = new StringBuilder(); 

		for(int i =0 ; i< tmp.size();i++){
			String line = tmp.get(i);
			int id = 0;
			String ele = null;
			builder.setLength(0);
			for(int j =0; j < line.length();j++){
				
				if(line.charAt(j) == ' '){
					id = Integer.parseInt(builder.toString());
					builder.setLength(0);
					j++;
				}
				builder.append(line.charAt(j));
				if(j == line.length()-1){
					ele = builder.toString();
				}
			}
			$ELEMENT.put(id, ele);
		}
	}
		
//		Iterator<Integer> keys = $ELEMENT.keys();
//		Iterator<String> values = $ELEMENT.values();
//		while (keys.hasNext()){
//			D.out(keys.next() + " " + values.next());
//		}

//		Iterator<Integer> keys = $TAG.keys();
//		Iterator<String> values = $TAG.values();
//		while (keys.hasNext()){
//			D.out(keys.next() + " " + values.next());
//		}
	
	String getElement(int id){
		return $ELEMENT.get(id);
	}
}
