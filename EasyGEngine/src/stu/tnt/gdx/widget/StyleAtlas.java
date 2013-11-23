package stu.tnt.gdx.widget;

/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

/**
 * @author Nathan Sweet
 */
public class StyleAtlas extends Skin {
	public StyleAtlas() {
	}

	public StyleAtlas(FileHandle skinFile) {
		super(skinFile);
	}

	public StyleAtlas(FileHandle skinFile, TextureAtlas texture) {
		super(skinFile, texture);
	}

	protected Json getJsonLoader(final FileHandle skinFile) {
		final Skin skin = this;

		final Json json = new Json() {
			public <T> T readValue(Class<T> type, Class elementType,
					JsonValue jsonData) {
				// If the JSON is a string but the type is not, look up the
				// actual value by name.
				if (jsonData.isString()
						&& !ClassReflection.isAssignableFrom(
								CharSequence.class, type))
					return get(jsonData.asString(), type);
				return super.readValue(type, elementType, jsonData);
			}
		};
		json.setTypeName(null);
		json.setUsePrototypes(false);

		/* ------------------------------------------------ */

		json.setSerializer(Skin.class, new ReadOnlySerializer<Skin>() {
			public Skin read(Json json, JsonValue typeToValueMap, Class ignored) {
				for (JsonValue valueMap = typeToValueMap.child(); valueMap != null; valueMap = valueMap
						.next()) {
					try {
						readNamedObjects(json,
								ClassReflection.forName(valueMap.name()),
								valueMap);
					} catch (ReflectionException ex) {
						throw new SerializationException(ex);
					}
				}
				return skin;
			}

			private void readNamedObjects(Json json, Class type,
					JsonValue valueMap) {
				Class addType = type == TintedDrawable.class ? Drawable.class
						: type;
				for (JsonValue valueEntry = valueMap.child(); valueEntry != null; valueEntry = valueEntry
						.next()) {
					Object object = json.readValue(type, valueEntry);
					if (object == null)
						continue;
					try {
						add(valueEntry.name(), object, addType);
					} catch (Exception ex) {
						throw new SerializationException("Error reading "
								+ ClassReflection.getSimpleName(type) + ": "
								+ valueEntry.name(), ex);
					}
				}
			}
		});

		json.setSerializer(BitmapFont.class,
				new ReadOnlySerializer<BitmapFont>() {
					public BitmapFont read(Json json, JsonValue jsonData,
							Class type) {
						String path = json.readValue("file", String.class,
								jsonData);

						FileHandle fontFile = skinFile.parent().child(path);
						if (!fontFile.exists())
							fontFile = Gdx.files.internal(path);
						if (!fontFile.exists())
							throw new SerializationException(
									"Font file not found: " + fontFile);

						// Use a region with the same name as the font, else use
						// a PNG file in the same directory as the FNT file.
						String regionName = fontFile.nameWithoutExtension();
						try {
							TextureRegion region = skin.optional(regionName,
									TextureRegion.class);
							if (region != null)
								return new BitmapFont(fontFile, region, false);
							else {
								FileHandle imageFile = fontFile.parent().child(
										regionName + ".png");
								if (imageFile.exists())
									return new BitmapFont(fontFile, imageFile,
											false);
								else
									return new BitmapFont(fontFile, false);
							}
						} catch (RuntimeException ex) {
							throw new SerializationException(
									"Error loading bitmap font: " + fontFile,
									ex);
						}
					}
				});

		json.setSerializer(Color.class, new ReadOnlySerializer<Color>() {
			public Color read(Json json, JsonValue jsonData, Class type) {
				if (jsonData.isString())
					return get(jsonData.asString(), Color.class);
				String hex = json.readValue("hex", String.class, (String) null,
						jsonData);
				if (hex != null)
					return Color.valueOf(hex);
				float r = json.readValue("r", float.class, 0f, jsonData);
				float g = json.readValue("g", float.class, 0f, jsonData);
				float b = json.readValue("b", float.class, 0f, jsonData);
				float a = json.readValue("a", float.class, 1f, jsonData);
				return new Color(r, g, b, a);
			}
		});

		json.setSerializer(TintedDrawable.class, new ReadOnlySerializer() {
			public Object read(Json json, JsonValue jsonData, Class type) {
				String name = json.readValue("name", String.class, jsonData);
				Color color = json.readValue("color", Color.class, jsonData);
				return newDrawable(name, color);
			}
		});

		/* ------------------------------------------------ */

		json.setSerializer(Attributes.class,
				new ReadOnlySerializer<Attributes>() {
					@Override
					public Attributes read(Json json, JsonValue jsonData,
							Class type) {
						float startX = json.readValue("startX", float.class,
								(float) 0, jsonData);
						float startY = json.readValue("startY", float.class,
								(float) 0, jsonData);
						float dstX = json.readValue("x", float.class, jsonData);
						float dstY = json.readValue("y", float.class, jsonData);
						float width = json.readValue("width", float.class,
								jsonData);
						float height = json.readValue("height", float.class,
								jsonData);
						Attributes attr = new Attributes();
						attr.startX = startX;
						attr.startY = startY;
						attr.x = dstX;
						attr.y = dstY;
						attr.width = width;
						attr.height = height;
						return attr;
					}
				});

		/* ------------------------------------------------ */

		json.setSerializer(ScalingSet.class,
				new ReadOnlySerializer<ScalingSet>() {
					@Override
					public ScalingSet read(Json json, JsonValue jsonData,
							Class type) {
						float layoutx = json.readValue("layoutx", float.class,
								jsonData);
						float layouty = json.readValue("layouty", float.class,
								jsonData);
						float layoutwidth = json.readValue("layoutwidth",
								float.class, jsonData);
						float layoutheight = json.readValue("layoutheight",
								float.class, jsonData);
						float whratio = json.readValue("whratio", float.class,
								jsonData);
						float hwratio = json.readValue("hwratio", float.class,
								jsonData);
						return new ScalingSet().set(layoutx, layouty,
								layoutwidth, layoutheight, whratio, hwratio);
					}
				});

		/* ------------------------------------------------ */

		json.setSerializer(Vector2.class, new ReadOnlySerializer<Vector2>() {
			@Override
			public Vector2 read(Json json, JsonValue jsonData, Class type) {
				return new Vector2(json.readValue("x", float.class, jsonData),
						json.readValue("y", float.class, jsonData));
			}
		});
		return json;
	}
}
