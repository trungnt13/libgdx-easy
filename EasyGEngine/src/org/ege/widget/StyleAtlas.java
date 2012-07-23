package org.ege.widget;

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
import java.util.ArrayList;
import java.util.List;

import org.ege.utils.Info;
import org.ege.utils.Orientation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.SerializationException;

/** 
 * @author Nathan Sweet */
public class StyleAtlas extends Skin {

	public StyleAtlas(){
	}
	
	public StyleAtlas (FileHandle skinFile) {
		super(skinFile);
	}


	public StyleAtlas (FileHandle skinFile, TextureAtlas texture) {
		super(skinFile, texture);
	}
	
	
	protected Json getJsonLoader (final FileHandle skinFile) {
		final Skin skin = this;

		final Json json = new Json() {
			public <T> T readValue (Class<T> type, Class elementType, Object jsonData) {
				// If the JSON is a string but the type is not, look up the actual value by name.
				if (jsonData instanceof String && !CharSequence.class.isAssignableFrom(type)) return get((String)jsonData, type);
				return super.readValue(type, elementType, jsonData);
			}
		};
		
		json.setTypeName(null);
		json.setUsePrototypes(false);

		

		/*	------------------------------------------------	*/

		json.setSerializer(Skin.class, new ReadOnlySerializer<Skin>() {
			public Skin read (Json json, Object jsonData, Class ignored) {
				ObjectMap<String, ObjectMap> typeToValueMap = (ObjectMap)jsonData;
				for (Entry<String, ObjectMap> typeEntry : typeToValueMap.entries()) {
					String className = typeEntry.key;
					ObjectMap<String, ObjectMap> valueMap = (ObjectMap)typeEntry.value;
					try {
						readNamedObjects(json, Class.forName(className), valueMap);
					} catch (ClassNotFoundException ex) {
						throw new SerializationException(ex);
					}
				}
				return skin;
			}

			private void readNamedObjects (Json json, Class type, ObjectMap<String, ObjectMap> valueMap) {
				Class addType = type == TintedDrawable.class ? Drawable.class : type;
				for (Entry<String, ObjectMap> valueEntry : valueMap.entries()) {
					String name = valueEntry.key;
					Object object = json.readValue(type, valueEntry.value);
					if (object == null) continue;
					try {
						add(name, object, addType);
					} catch (Exception ex) {
						throw new SerializationException("Error reading " + type.getSimpleName() + ": " + valueEntry.key, ex);
					}
				}
			}
		});

		/*	------------------------------------------------	*/

		json.setSerializer(BitmapFont.class, new ReadOnlySerializer<BitmapFont>() {
			public BitmapFont read (Json json, Object jsonData, Class type) {
				String path = json.readValue("file", String.class, jsonData);

				FileHandle fontFile = skinFile.parent().child(path);
				if (!fontFile.exists()) fontFile = Gdx.files.internal(path);
				if (!fontFile.exists()) throw new SerializationException("Font file not found: " + fontFile);

				// Use a region with the same name as the font, else use a PNG file in the same directory as the FNT file.
				String regionName = fontFile.nameWithoutExtension();
				try {
					TextureRegion region = skin.optional(regionName, TextureRegion.class);
					if (region != null)
						return new BitmapFont(fontFile, region, false);
					else {
						FileHandle imageFile = fontFile.parent().child(regionName + ".png");
						if (imageFile.exists())
							return new BitmapFont(fontFile, imageFile, false);
						else
							return new BitmapFont(fontFile, false);
					}
				} catch (RuntimeException ex) {
					throw new SerializationException("Error loading bitmap font: " + fontFile, ex);
				}
			}
		});


		/*	------------------------------------------------	*/

		json.setSerializer(Color.class, new ReadOnlySerializer<Color>() {
			public Color read (Json json, Object jsonData, Class type) {
				if (jsonData instanceof String) return get((String)jsonData, Color.class);
				ObjectMap map = (ObjectMap)jsonData;
				float r = json.readValue("r", float.class, 0f, jsonData);
				float g = json.readValue("g", float.class, 0f, jsonData);
				float b = json.readValue("b", float.class, 0f, jsonData);
				float a = json.readValue("a", float.class, 1f, jsonData);
				return new Color(r, g, b, a);
			}
		});

		/*	------------------------------------------------	*/

		json.setSerializer(TintedDrawable.class, new ReadOnlySerializer() {
			public Object read (Json json, Object jsonData, Class type) {
				String name = json.readValue("name", String.class, jsonData);
				Color color = json.readValue("color", Color.class, jsonData);
				return newDrawable(name, color);
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

		json.setSerializer(Info.class, new ReadOnlySerializer<Info>() {

			@Override
			public Info read (Json json, Object jsonData, Class type) {
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

		json.setSerializer(Attributes.class, new ReadOnlySerializer<Attributes>() {
			@Override
			public Attributes read (Json json, Object jsonData, Class type) {
				float startX = json.readValue("startX", float.class,(float)0, jsonData);
				float startY = json.readValue("startY", float.class,(float)0, jsonData);
				float dstX = json.readValue("x", float.class, jsonData);
				float dstY = json.readValue("y", float.class, jsonData);
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
		
		/*	------------------------------------------------	*/
		
		json.setSerializer(ScalingSet.class, new ReadOnlySerializer<ScalingSet>() {
			@Override
			public ScalingSet read (Json json, Object jsonData, Class type) {
				float layoutx = json.readValue("layoutx", float.class,jsonData);
				float layouty = json.readValue("layouty", float.class,jsonData);
				float layoutwidth = json.readValue("layoutwidth", float.class,jsonData);
				float layoutheight = json.readValue("layoutheight", float.class,jsonData);
				float whratio = json.readValue("whratio", float.class,jsonData);
				float hwratio = json.readValue("hwratio", float.class,jsonData);
				return new ScalingSet().set(layoutx, layouty, layoutwidth, layoutheight, whratio, hwratio);
			}
		});
		
		/*	------------------------------------------------	*/
		
		json.setSerializer(Vector2.class, new ReadOnlySerializer<Vector2>() {
			@Override
			public Vector2 read (Json json, Object jsonData, Class type) {
				return new Vector2(json.readValue("x", float.class,jsonData), 
									json.readValue("y", float.class,jsonData))	;
			}
		});
		return json;
	}
}
