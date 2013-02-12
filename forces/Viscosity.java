package simulation.forces;

import java.awt.Dimension;
import java.util.List;
import java.util.Scanner;

import simulation.assembly.Mass;
import util.Vector;


public class Viscosity extends Force {

	private static final double DEFAULT_SCALE = .01;
	
    double scale;

    public Viscosity ()
    {
        this.scale = DEFAULT_SCALE;
    }
    
    public Viscosity (double scale)
    {
        this.scale = scale;
    }

    public static Viscosity generator (Scanner line)
    {
        double scale = line.nextDouble();
        return new Viscosity(scale);
    }

    @Override
    public void apply (List<Mass> masses, Dimension bounds) {

        for (Mass m : masses) {
            // apply a force opposite to the velocity of the mass scaled by the viscosity's scale
            Vector force = new Vector(m.getVelocity().getDirection(), m.getVelocity().getMagnitude());
            force.negate();
            force.scale(scale);
            m.applyForce(force);
        }

    }

}
