package org.ege.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.ege.widget.Attributes;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.SerializationException;

public class DataSSD implements Disposable{
	protected ObjectMap<Class, ObjectMap<String, Object>> resources = new ObjectMap<Class, ObjectMap<String,Object>>();
	private static ObjectMap<Class,Serializer> mDataSerializer = new ObjectMap<Class, Json.Serializer>();
	
	public DataSSD(){
	}
	
	public DataSSD(FileHandle dataFile) {
		load(dataFile);
	}

	public static void putSerializer(Class type,Serializer serializer){
		mDataSerializer.put(type, serializer);
	}
	
	private void load (FileHandle dataFile) {
		try {
			getJsonLoader(dataFile).fromJson(DataSSD.class, dataFile);
		} catch (SerializationException ex) {
			throw new SerializationException("Error reading file: " + dataFile, ex);
		}
	}

	public void addData (String name, Object resource) {
		if (name == null) throw new IllegalArgumentException("name cannot be null.");
		if (resource == null) throw new IllegalArgumentException("resource cannot be null.");
		ObjectMap<String, Object> typeResources = resources.get(resource.getClass());
		if (typeResources == null) {
			typeResources = new ObjectMap();
			resources.put(resource.getClass(), typeResources);
		}
		typeResources.put(name, resource);
	}

	public <T> T getData (String name, Class<T> type) {
		if (name == null) throw new IllegalArgumentException("name cannot be null.");
		ObjectMap<String, Object> typeResources = resources.get(type);
		if (typeResources == null)
			throw new GdxRuntimeException("No " + type.getName() + " resource registered with name: " + name);
		Object resource = typeResources.get(name);
		if (resource == null) throw new GdxRuntimeException("No " + type.getName() + " resource registered with name: " + name);
		return (T)resource;
	}
	
	public boolean hasResource (String name, Class type) {
		ObjectMap<String, Object> typeResources = resources.get(type);
		if (typeResources == null) return false;
		Object resource = typeResources.get(name);
		if (resource == null) return false;
		return true;
	}
	
	
	public void save (FileHandle skinFile) {
		String text = getJsonLoader(null).prettyPrint(this, 130);
		Writer writer = skinFile.writer(false);
		try {
			writer.write(text);
			writer.close();
		} catch (IOException ex) {
			throw new GdxRuntimeException(ex);
		}
	}
	
	protected Json getJsonLoader (FileHandle dataFile) {
		final DataSSD data = this;
		
		final  Json json = new Json();
		json.setTypeName(null);
		json.setUsePrototypes(false);
		
		class AliasWriter implements Serializer {
			final ObjectMap<String, ?> map;

			public AliasWriter (Class type) {
				map = resources.get(type);
			}

			public void write (Json json, Object object, Class valueType) {
				for (Entry<String, ?> entry : map.entries()) {
					if (entry.value.equals(object)) {
						json.writeValue(entry.key);
						return;
					}
				}
				throw new SerializationException(object.getClass().getSimpleName() + " not found: " + object);
			}

			public Object read (Json json, Object jsonData, Class type) {
				throw new UnsupportedOperationException();
			}
		}
		
		/*	-------------------------------------------	*/
		
		json.setSerializer(DataSSD.class, new Serializer<DataSSD>() {

			@Override
			public void write (Json json, DataSSD object, Class knownType) {
				json.writeObjectStart();
				json.writeValue("resources", data.resources);
				for (Entry<Class, ObjectMap<String, Object>> entry : resources.entries())
					json.setSerializer(entry.key, new AliasWriter(entry.key));
				json.writeObjectEnd();
			}

			@Override
			public DataSSD read (Json json, Object jsonData, Class type) {
				ObjectMap map = (ObjectMap)jsonData;
				readTypeMap(json, (ObjectMap)map.get("data"), true);
				return data;
			}
			
			
			private void readTypeMap (Json json, ObjectMap<String, ObjectMap> typeToValueMap, boolean isData) {
				if (typeToValueMap == null)
					throw new SerializationException("Data file is missing a \"" + (isData ? "resources" : "styles")
						+ "\" section.");
				for (Entry<String, ObjectMap> typeEntry : typeToValueMap.entries()) {
					String className = typeEntry.key;
					ObjectMap<String, ObjectMap> valueMap = (ObjectMap)typeEntry.value;
					try {
						readNamedObjects(json, Class.forName(className), valueMap, isData);
					} catch (ClassNotFoundException ex) {
						throw new SerializationException(ex);
					}
				}
			}
			
			private void readNamedObjects (Json json, Class type, ObjectMap<String, ObjectMap> valueMap, boolean isData) {
				for (Entry<String, ObjectMap> valueEntry : valueMap.entries()) {
					String name = valueEntry.key;
					Object object = json.readValue(type, valueEntry.value);
					if (object == null) continue;
					try {
						if (isData)
							addData(name, object);
					} catch (Exception ex) {
						throw new SerializationException("Error reading " + type.getSimpleName() + ": " + valueEntry.key, ex);
					}
				}
			}
		});
		
		/*	------------------------------------------------	*/

		json.setSerializer(Color.class, new Serializer<Color>() {
			public void write (Json json, Color color, Class valueType) {
				json.writeObjectStart();
				json.writeFields(color);
				json.writeObjectEnd();
			}

			
			public Color read (Json json, Object jsonData, Class type) {
				if (jsonData instanceof String) return getData((String)jsonData, Color.class);
				ObjectMap map = (ObjectMap)jsonData;
				float r = json.readValue("r", float.class, 0f, jsonData);
				float g = json.readValue("g", float.class, 0f, jsonData);
				float b = json.readValue("b", float.class, 0f, jsonData);
				float a = json.readValue("a", float.class, 1f, jsonData);
				return new Color(r, g, b, a);
			}
		});
		
		/*	------------------------------------------------	*/

		json.setSerializer(Orientation.class, new Serializer<Orientation>() {

			@Override
			public void write (Json json, Orientation object, Class knownType) {
				json.writeObjectStart();
				switch (object) {
					case HORIZONTAL:
						json.writeValue("orientation", "HORIZONTAL");
						break;
					case VERTICAL:
						json.writeValue("orientation", "VERTICAL");
						break;
					case LANDSCAPE:
						json.writeValue("orientation", "LANDSCAPE");
						break;
					case PORTRAIT:
						json.writeValue("orientation", "PORTRAIT");
						break;
				}
				json.writeObjectEnd();
			}

			@Override
			public Orientation read (Json json, Object jsonData, Class type) {
				String ori = (String)jsonData;
				ori = ori.toLowerCase();
				if(ori.equals("landscape"))
					return Orientation.LANDSCAPE;
				else if(ori.equals("portrait"))
					return Orientation.PORTRAIT;
				else if(ori.equals("vertical"))
					return Orientation.VERTICAL;
				return Orientation.HORIZONTAL;
			}
			
		});
		
		/*	------------------------------------------------	*/

		json.setSerializer(Info.class, new Serializer<Info>() {

			@Override
			public void write (Json json, Info object, Class knownType) {
				json.writeObjectStart();
				if(object.value.size() > 0)
					json.writeValue("type",object.value.get(0).getClass().getName());
				json.writeValue("value",object.value);
				json.writeObjectEnd();
			}

			@Override
			public Info read (Json json, Object jsonData, Class type) {
				if(jsonData instanceof String) return getData((String)jsonData, Info.class);
				String name = json.readValue("type", String.class,jsonData);
				if(name == null){
					Info info = new Info();
					info.value = (ArrayList) json.readValue("value", List.class,jsonData);
					return info;
				}
			
				Class clazz = null;
				try {
					clazz = Class.forName(name);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				Info info = new Info();
				List test= json.readValue("value", List.class, jsonData);
				for(int i =0 ; i < test.size();i++)
					info.value.add(json.readValue(clazz, test.get(i)));
				return info;
			}
		});
		
		/*	------------------------------------------------	*/

		json.setSerializer(Attributes.class, new Serializer<Attributes>() {

			@Override
			public void write (Json json, Attributes object, Class knownType) {
				json.writeObjectStart();
				json.writeValue("startX", object.startX);
				json.writeValue("startY", object.startY);
				json.writeValue("x", object.x);
				json.writeValue("y", object.y);
				json.writeValue("width", object.width);
				json.writeValue("height", object.height);
				json.writeObjectEnd();
			}

			@Override
			public Attributes read (Json json, Object jsonData, Class type) {
				if(jsonData instanceof String) return getData((String)jsonData, Attributes.class);
				float startX = json.readValue("startX", float.class, jsonData);
				float startY = json.readValue("startY", float.class, jsonData);
				float dstX = json.readValue("dstX", float.class, jsonData);
				float dstY = json.readValue("dstY", float.class, jsonData);
				float width = json.readValue("width", float.class, jsonData);
				float height = json.readValue("height", float.class, jsonData);
				Attributes attr = new Attributes();
				attr.startX = startX;
				attr.startY = startY;
				attr.x = dstX;
				attr.y  = dstY;
				attr.width  = width;
				attr.height = height;
				return attr;
			}
		});

		for (Entry<Class, Serializer> entry : mDataSerializer.entries()) {
			json.setSerializer(entry.key, entry.value);
		}
		
		return json;
	}
	
	@Override
	public void dispose () {
		for (Entry<Class, ObjectMap<String, Object>> entry : resources.entries()) {
			if (!Disposable.class.isAssignableFrom(entry.key)) continue;
			for (Object resource : entry.value.values())
				((Disposable)resource).dispose();
		}
	}
}
