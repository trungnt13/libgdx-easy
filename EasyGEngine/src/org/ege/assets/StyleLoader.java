package org.ege.assets;

import org.ege.widget.StyleAtlas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class StyleLoader extends AsynchronousAssetLoader<StyleAtlas, StyleLoader.StyleParameter>
{
    public StyleLoader(FileHandleResolver resolver)
    {
	super(resolver);
    }

    @Override
    public void loadAsync (AssetManager manager, String fileName, StyleParameter parameter)
    {
    }

    @Override
    public StyleAtlas loadSync (AssetManager manager, String fileName, StyleParameter parameter)
    {
	String textureAtlasPath;
	if (parameter == null)
	    textureAtlasPath = Gdx.files.internal(fileName).pathWithoutExtension() + ".atlas";
	else
	    textureAtlasPath = parameter.textureAtlasPath;
	TextureAtlas atlas = manager.get(textureAtlasPath, TextureAtlas.class);
	return new StyleAtlas(resolve(fileName), atlas);
    }

    @Override
    public Array<AssetDescriptor> getDependencies (String fileName, StyleParameter parameter)
    {
	Array<AssetDescriptor> deps = new Array();
	if (parameter == null)
	    deps.add(new AssetDescriptor(resolve(fileName).pathWithoutExtension() + ".atlas",
		    TextureAtlas.class));
	else
	    deps.add(new AssetDescriptor(parameter.textureAtlasPath, TextureAtlas.class));
	return deps;
    }

    public static class StyleParameter extends AssetLoaderParameters<StyleAtlas>
    {
	public final String textureAtlasPath;

	public StyleParameter(String textureAtlasPath)
	{
	    this.textureAtlasPath = textureAtlasPath;
	}
    }
}
