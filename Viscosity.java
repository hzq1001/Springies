package simulation;

import java.awt.Dimension;
import java.util.List;
import java.util.Scanner;
import util.Vector;


public class Viscosity extends Force {

<<<<<<< HEAD
    double scale;

=======
	private static final double DEFAULT_SCALE = .01;
	
    double scale;

    public Viscosity ()
    {
        this.scale = DEFAULT_SCALE;
    }
    
>>>>>>> Broke factory into heirarchy
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
<<<<<<< HEAD
            // value
            Vector force = m.getVelocity();
=======
            Vector force = new Vector(m.getVelocity().getDirection(), m.getVelocity().getMagnitude());
>>>>>>> Broke factory into heirarchy
            force.negate();
            force.scale(scale);
            m.applyForce(force);
        }

    }

}
