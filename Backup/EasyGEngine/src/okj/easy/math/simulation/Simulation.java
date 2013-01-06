package okj.easy.math.simulation;

import com.badlogic.gdx.math.Vector2;

public interface Simulation {
	public  Vector2 project(float x,float y);
	public  Vector2 unproject(int column,int row);
	public Vector2 unproject(float column,float row);
	public int  toMappingId(float xPosition,float yPosition);
	public Vector2 toGridPos(int ID);
	public int toMappingId(int column,int row);
}
