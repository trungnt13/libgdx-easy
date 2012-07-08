package group.easy.tests;

import org.ege.utils.D;

import okj.easy.core.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class MultiThreadGdx extends GameScreen{
	public static class Counter{
		private int i ;
		
		public Counter(){
			
		}
		
		public Counter(int i){
			this.i = i;
		}
		
		public void increase(){
			i++;
		}
		
		public void decrease(){
			i--;
		}
		
		public int value(){
			return i;
		}
	}
	
	Counter c = new Counter();
	@Override
	public Layout onCreateLayout() {
		return null;
		// TODO Auto-generated method stub
	}

	@Override
	public void onLoadResource() {
		Thread tmp = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(c.value() < 50){
					try {
						Thread.sleep(100);
						c.increase();
						if(c.value() == 50)
						Gdx.app.postRunnable(new Runnable() {
							
							@Override
							public void run() {
								c = new Counter(113);
							}
						});
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		tmp.start();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRender(float delta) {
		glClear(GL10.GL_COLOR_BUFFER_BIT);
		glClearColor(1, 0, 0, 1);
		synchronized (c) {
			D.out(c.value());
		}
	}

	@Override
	public void onResize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
	}

}
