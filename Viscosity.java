package simulation;

import java.util.List;

import util.Vector;

public class Viscosity extends Force {

	double scale;
	
	public Viscosity(double scale)
	{
		this.scale = scale;
	}
	
	@Override
	public void apply(List<Mass> masses) {
		
		for (Mass m : masses) {
			//apply a force opposite to the velocity of the mass scaled by the viscosity's scale value
			Vector force = m.getVelocity();
			force.negate();
			force.scale(scale);
			m.applyForce(force);
		}

	}

}
