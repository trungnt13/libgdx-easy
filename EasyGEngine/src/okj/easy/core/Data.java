package okj.easy.core;

import com.badlogic.gdx.assets.AssetLoaderParameters;

/**
 * 
 * @FileName: Data.java
 * @CreateOn: Sep 15, 2012 - 11:06:23 AM
 * @Author: TrungNT
 */
class Data<T> {
	Class<T>					clazz	= null;
	AssetLoaderParameters<T>	param	= null;

	Data (Class<T> clazz, AssetLoaderParameters<T> param) {
		this.clazz = clazz;
		this.param = param;
	}
}
