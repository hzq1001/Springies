package simulation;

import java.awt.Dimension;
import java.util.List;
import java.util.Scanner;
import util.Vector;


public class Gravity extends Force {

<<<<<<< HEAD
    Vector forceVector;

=======
	private static final double DEFAULT_ANGLE = 90;
	private static final double DEFAULT_MAGNITUDE = 100;
	
    Vector forceVector;

    public Gravity ()
    {
        forceVector = new Vector(DEFAULT_ANGLE, DEFAULT_MAGNITUDE);
    }
    
>>>>>>> Broke factory into heirarchy
    public Gravity (double angle, double magnitude)
    {
        forceVector = new Vector(angle, magnitude);
    }

    public static Gravity generator (Scanner line) {
        double angle = line.nextDouble();
        double magnitude = line.nextDouble();
        return new Gravity(angle, magnitude);
    }

    @Override
    public void apply (List<Mass> masses, Dimension bounds) {
        for (Mass m : masses) {
            m.applyForce(forceVector);
        }
    }

}
