package okj.easy.core;

import com.badlogic.gdx.assets.AssetLoaderParameters;

class Data<T> {
	Class<T>					clazz	= null;
	AssetLoaderParameters<T>	param	= null;

	Data (Class<T> clazz, AssetLoaderParameters<T> param) {
		this.clazz = clazz;
		this.param = param;
	}
}
