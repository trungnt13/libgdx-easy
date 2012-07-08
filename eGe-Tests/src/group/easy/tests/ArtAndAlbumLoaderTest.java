package group.easy.tests;

import group.easy.I;
import okj.easy.admin.Album;
import okj.easy.admin.Art;
import okj.easy.admin.eAdmin;
import okj.easy.admin.eAudio.AudioType;
import okj.easy.admin.eInput.KeyCallBack;
import okj.easy.core.GameScreen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class ArtAndAlbumLoaderTest extends GameScreen implements KeyCallBack{

	Texture t1 = null;
	Texture t2 = null;
	Texture t3 = null;
	
	boolean swi = true;
	boolean swi1 = true;

	
	@Override
	public void onLoadResource () {
		
		eAdmin.einput.addKeyCallback(this);
		
		Art art = new Art(I.art.art);
		TextureParameter parma = new TextureParameter();
		parma.minFilter = TextureFilter.Linear;
		parma.magFilter = TextureFilter.Linear;
		
		art.load(I.texture.addplaneblock, Texture.class);
		art.load(I.texture.exitup, Texture.class,parma);
		art.load(I.texture.thumbailmap1, Texture.class);
		art.load(I.texture.thumbailmap2, Texture.class,parma);
		art.load(I.texture.thumbailmap3, Texture.class);
		
		Album album = new Album("album");
		album.load(I.sound.soundTest, AudioType.SOUND);
		album.load(I.music.musicTest, AudioType.MUSIC);
		
	}

	@Override
	public void onResume () {
	}

	@Override
	public void onPause () {
	}

	@Override
	public void onDestroy () {
	}

	@Override
	public void onUpdate (float delta) {
		
	}

	@Override
	public void onRender (float delta) {
		if(eAdmin.egraphics.update() & swi){
			t1 = findGDataByName(I.texture.thumbailmap1, Texture.class);
			t2 = findGDataByName(I.texture.thumbailmap2, Texture.class);
			t3 = findGDataByName(I.texture.thumbailmap3, Texture.class);
			swi = false;
		}
		
		if(eAdmin.eaudio.update() & swi1){
			swi1 = false;
		}
		
		if(eAdmin.eaudio.isLoaded(I.music.musicTest, AudioType.MUSIC) &&
		   !eAdmin.eaudio.isPlaying(I.music.musicTest))
			eAdmin.eaudio.playMusic(I.music.musicTest);
		if(t1 != null){
			batch.begin();
			batch.draw(t1,0,0);
			batch.draw(t2,300,-100);
			batch.draw(t3,100,100);
			batch.end();
		}
	}

	@Override
	public void onResize (int width, int height) {
	}

	@Override
	public boolean onKeyDown (int keycode) {
		return false;
	}

	@Override
	public boolean onKeyUp (int keycode) {
		if(keycode == Keys.SHIFT_LEFT){
			findArtByName(I.art.art).unload();
		}else if(keycode == Keys.SHIFT_RIGHT){
			findArtByName(I.art.art).reload();
			swi = true;
		}else if(keycode == Keys.ALT_LEFT){
			findArtByName(I.art.art).unload(I.texture.thumbailmap1);
		}else if(keycode == Keys.ALT_RIGHT){
			findArtByName(I.art.art).remove(I.texture.thumbailmap2);
		}else if(keycode == Keys.CONTROL_LEFT){
			findAlbumByName("album").unload(I.music.musicTest);
		}else if(keycode == Keys.CONTROL_RIGHT){
			findAlbumByName("album").reload();
		}
		return false;
	}

	@Override
	public boolean onKeyTyped (char character) {
		
		return false;
		
	}

	@Override
	public org.ege.widget.Layout onCreateLayout () {
		return null;
		
	}

}
