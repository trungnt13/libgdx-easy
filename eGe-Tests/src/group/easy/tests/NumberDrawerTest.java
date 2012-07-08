package group.easy.tests;

import org.ege.utils.GraphicsHelper;

import group.easy.I;
import okj.easy.core.GameScreen;
import okj.easy.graphics.graphics2d.NumberDrawer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NumberDrawerTest extends GameScreen{
	TextureRegion[] region;
	TextureRegion[] region1;

	NumberDrawer drawer;
	float count =0;
	@Override
	public Layout onCreateLayout () {
		return null;
		
	}

	@Override
	public void onLoadResource () {
		Texture texture = new Texture(Gdx.files.internal(I.texture.sodiem));
		TextureRegion test = new TextureRegion(texture, 200, 32);
		region = GraphicsHelper.split(texture, 10, 20, 32, 0);
		region1 = GraphicsHelper.split(test, 10, 10, 1, 0);
		
		drawer = new NumberDrawer(test);
		drawer.setPosition(500, 500);
		drawer.setDrawingNumber(113);
		drawer.setOrientation(Orientation.VERTICAL);
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
		glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		for(int i = 0; i < region.length;i++){
			batch.draw(region[i],i*50,100);
			batch.draw(region1[i],i*50,300);
		}
		drawer.draw(batch,1999999999);
		batch.end();
		count += 5*delta;
	}

	@Override
	public void onResize (int width, int height) {
	}

}
