package simulation;

import java.awt.Dimension;
import java.util.List;
import java.util.Scanner;

import util.Vector;

public class Gravity extends Force {
	
	Vector forceVector;
	
	public Gravity(double angle, double magnitude)
	{
		forceVector = new Vector(angle,magnitude);
	}
	
	public static Gravity generator(Scanner line) {
		double angle = line.nextDouble();
		double magnitude = line.nextDouble();
		return new Gravity(angle, magnitude);		
	}
	
	@Override
	public void apply(List<Mass> masses, Dimension bounds) {
		for (Mass m : masses)
			m.applyForce(forceVector);
	}
	
}
