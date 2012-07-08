package group.easy.tests;

import org.ege.utils.D;

import okj.easy.core.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.OrderedMap;

public class JsonReaderTest extends  GameScreen{
	
	@Override
	public Layout onCreateLayout () {
		return null;
	}

	@Override
	public void onLoadResource () {
		JsonReader reader = new JsonReader();
		OrderedMap<Integer, String> test= (OrderedMap<Integer, String>)reader.parse(Gdx.files.internal("data/jsonTest.json"));
		D.out(test.size);
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
		glClearColor(0, 0, 1, 1);
	}

	@Override
	public void onResize (int width, int height) {
	}

}
