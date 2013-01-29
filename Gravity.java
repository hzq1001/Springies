package simulation;

import java.awt.Dimension;
import java.util.List;

import util.Vector;

public class Gravity extends Force {
	
	Vector forceVector;
	
	public Gravity(double angle, double magnitude)
	{
		forceVector = new Vector(angle,magnitude);
	}
	
	@Override
	public void apply(List<Mass> masses, Dimension bounds) {
		for (Mass m : masses)
			m.applyForce(forceVector);
	}
	
}
