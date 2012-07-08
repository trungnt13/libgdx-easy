package org.ege.utils;

import okj.easy.graphics.graphics2d.SpriteCA;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public interface SpriteCAFactory<T extends SpriteCA>{

	public   T newObject(OnRecycleListener<SpriteCA> listener,Vector2 position,Vector2 size,TextureRegion[]...keyFrames) ;

	public   T newObject(OnRecycleListener<SpriteCA> listener);
	
	public   T newObject(OnRecycleListener<SpriteCA> listener,TextureRegion[]...keyFrames);
	
	public T newObject(OnRecycleListener<SpriteCA> listener,Vector2 position,Vector2 size);
}
