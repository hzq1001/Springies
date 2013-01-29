package simulation;

import java.util.List;

import util.Location;
import util.Vector;

public class CenterOfMass extends Force {

	private Location com;
	private double magnitude;
	private double exponent;
	
	public CenterOfMass(double magnitude, double exponent) {
		this.magnitude = magnitude;
		this.exponent = exponent;
	}
	
	@Override
	public void apply(List<Mass> masses) {
		com = calculateCOM(masses);
		for(Mass m : masses) {
			//Vector force = new Vector(Vector.angleBetween(m., dy))
		}
		

	}
	
	/**
	 * Helper function to calculate the center of mass of a list of masses
	 */
	public Location calculateCOM(List<Mass> masses) {
		double x = 0;
		double y = 0;
		double totalMass = 0;
		
		for(Mass m : masses) {
			totalMass += m.getMass();
			x += m.getMass()*m.getX();
			y += m.getMass()*m.getY();
		}
		
		x /= totalMass;
		y /= totalMass;

		return new Location(x,y);
	}

}
