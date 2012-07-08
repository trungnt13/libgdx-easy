package org.ege.assets;

import org.ege.utils.DataSSD;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.utils.Array;

public class DataLoader extends SynchronousAssetLoader<DataSSD, DataLoader.DataParameters>{

	public DataLoader (FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public DataSSD load (AssetManager assetManager, String fileName,DataParameters parameter) {
		return new DataSSD(resolve(fileName));
	}

	@Override
	public Array<AssetDescriptor> getDependencies (String fileName,
			DataParameters parameter) {
		return null;
		
	}
	
	public static class DataParameters extends AssetLoaderParameters<DataSSD>{
		
	}
}
