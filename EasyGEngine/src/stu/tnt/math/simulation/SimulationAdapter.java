package stu.tnt.math.simulation;

import com.badlogic.gdx.math.Vector2;

public abstract class SimulationAdapter implements Simulation {

	@Override
	public Vector2 project(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector2 unproject(int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector2 unproject(float column, float row) {
		return null;
		
	}

	
	@Override
	public int toMappingId(float xPosition, float yPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector2 toGridPos(int ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int toMappingId(int row, int column) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
