package stu.tnt.gdx.core;

import stu.tnt.gdx.utils.E;
import stu.tnt.gdx.utils.exception.EasyGEngineRuntimeException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * 
 * @FileName: eGraphics.java
 * @CreateOn: Sep 15, 2012 - 11:11:48 AM
 * @Author: TrungNT
 */
public class eGraphics {
	public static int ORIENTATION = E.orientation.LANDSCAPE;

	public eGraphics() {
	}

	/***************************************************************
	 * 
	 ***************************************************************/

	static final class ui {
		static int UI_HEIGHT = 320;
		static int UI_WIDTH = 480;

		public static float TOAST_WIDTH = 220;
		private static byte mCurrentMode = E.resolutions.FIXED_RESOLUTION_MODE;

		private ui() {
			
		}

		static void resolve(int screenWIdth, int screenHEight) {
			switch (mCurrentMode) {
			case E.resolutions.FIXED_RESOLUTION_MODE:
				if (screenWIdth > screenHEight) {
					ORIENTATION = E.orientation.LANDSCAPE;
					UI_WIDTH = 800;
					UI_HEIGHT = 500;
				} else {
					ORIENTATION = E.orientation.PORTRAIT;
					UI_WIDTH = 500;
					UI_HEIGHT = 800;
				}
				break;
			case E.resolutions.MULTI_RESOLUTION_MODE:
				UI_WIDTH = screenWIdth;
				UI_HEIGHT = screenHEight;
				if (screenWIdth > screenHEight) {
					ORIENTATION = E.orientation.LANDSCAPE;
				} else {
					ORIENTATION = E.orientation.PORTRAIT;
				}
				break;
			case E.resolutions.FIXED_MULTI_RESOLUTION_MODE:
				float ratio = (float) screenWIdth / (float) screenHEight;
				UI_WIDTH = (int) (ratio * 320);
				UI_HEIGHT = 800;
				if (screenWIdth > screenHEight) {
					ORIENTATION = E.orientation.LANDSCAPE;
				} else {
					ORIENTATION = E.orientation.PORTRAIT;
				}
				break;
			case E.resolutions.MANUAL_RESOLUTION_MODE:
				if (screenWIdth > screenHEight) {
					ORIENTATION = E.orientation.LANDSCAPE;
				} else {
					ORIENTATION = E.orientation.PORTRAIT;
				}
				break;
			}
			TOAST_WIDTH = 2f / 3f * (float) UI_WIDTH;
			game.resolve(screenWIdth, screenHEight);
		}
	}

	public final void setUiResolution(byte eResolution, int... resolutions) {
		ui.mCurrentMode = eResolution;
		if (eResolution == E.resolutions.MANUAL_RESOLUTION_MODE) {
			ui.UI_WIDTH = resolutions[0];
			ui.UI_HEIGHT = resolutions[1];
		}
		ui.resolve(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	/***************************************************************
	 * 
	 ***************************************************************/

	static final class game {
		static int GAME_HEIGHT = 800;
		static int GAME_WIDTH = 500;

		private static int[] mGameResolution;

		private static void resolve(int screenWidth, int screenHeight) {
			if (mGameResolution != null) {
				int closest = Math.abs(screenWidth - mGameResolution[0]);
				int id = 0;
				for (int i = 0; i < mGameResolution.length; i += 2) {
					if (Math.abs(screenWidth - mGameResolution[i]) < closest) {
						id = i;
						closest = Math.abs(screenWidth - mGameResolution[i]);
						if (closest == 0
								|| screenHeight == mGameResolution[i + 1]) {
							GAME_WIDTH = mGameResolution[i];
							GAME_HEIGHT = mGameResolution[i + 1];
							return;
						}
					}
				}
				GAME_WIDTH = mGameResolution[id];
				GAME_HEIGHT = mGameResolution[id + 1];
			}
		}
	}

	public void setGameResolution(int... resolutions) {
		if (resolutions.length == 2) {
			game.GAME_WIDTH = resolutions[0];
			game.GAME_HEIGHT = resolutions[1];
			game.mGameResolution = null;
		} else if (resolutions.length > 2 && resolutions.length % 2 == 0) {
			game.mGameResolution = resolutions;
		} else
			throw new EasyGEngineRuntimeException(
					"Please fix you Game resolutions");
	}

	public void resolve(int screenWidth, int screenHeight) {
		ui.resolve(screenWidth, screenHeight);
		game.resolve(screenWidth, screenHeight);
	}

	/***************************************************************
	 * 
	 ***************************************************************/

	public static TextureRegion[] split(Texture texture, int regionNumber,
			int tileWidth, int tileHeight, int padding) {
		TextureRegion tmp = new TextureRegion(texture);
		int x = tmp.getRegionX();
		int y = tmp.getRegionY();
		int width = tmp.getRegionWidth();
		int height = tmp.getRegionHeight();

		int pad = 0;

		if (padding > 0)
			pad = padding;

		if (width < 0) {
			x = x - width;
			width = -width;
		}

		if (height < 0) {
			y = y - height;
			height = -height;
		}
		/**
		 * Row is a int so when i devide like this the excessive area will be
		 * dismiss . But remember the excessive area must be smaller than tile
		 * width and tile height
		 */
		int rows = (height + pad) / (tileHeight + pad);
		int cols = (width + pad) / (tileWidth + pad);

		TextureRegion[] tiles = new TextureRegion[regionNumber];

		int count = 0;
		int startX = x;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (count < regionNumber) {
					tiles[count] = new TextureRegion(texture, x, y, tileWidth,
							tileHeight);
					count++;
				}
				x += tileWidth + pad;
			}
			x = startX;
			y += tileHeight + pad;
		}

		return tiles;
	}

	public static TextureRegion[] split(TextureRegion region,
			int totalRegionNumber, int numberOfRegionInARow,
			int numberOfRegionInAColumn, int padding) {
		Texture texture = region.getTexture();

		TextureRegion[] tmp = new TextureRegion[totalRegionNumber];
		int count = 0;

		final int firstX = region.getRegionX();
		final int firstY = region.getRegionY();

		int tileWidth = (region.getRegionWidth() - (padding * (numberOfRegionInARow - 1)))
				/ numberOfRegionInARow;
		int tileHeight = (region.getRegionHeight() - (padding * (numberOfRegionInAColumn - 1)))
				/ numberOfRegionInAColumn;

		for (int i = 0; i < numberOfRegionInAColumn; i++) {
			for (int j = 0; j < numberOfRegionInARow; j++) {
				tmp[count] = new TextureRegion(texture, firstX
						+ (j * (tileWidth + padding)), firstY
						+ (i * (tileHeight + padding)), tileWidth, tileHeight);
				count++;
				if (count >= totalRegionNumber)
					return tmp;
			}
		}
		return tmp;
	}

	public static TextureRegion[] regionConvert(Array<AtlasRegion> regions) {
		if (!(regions.get(0) instanceof TextureRegion))
			throw new EasyGEngineRuntimeException(
					"Your List not instance of TextureRegion");
		TextureRegion[] tmp = new TextureRegion[regions.size];
		for (int i = 0, n = regions.size; i < n; i++) {
			tmp[i] = (TextureRegion) regions.get(i);
		}
		return tmp;
	}

}
