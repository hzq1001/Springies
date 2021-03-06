package simulation.forces;

import java.awt.Dimension;
import java.util.List;
import java.util.Scanner;

import simulation.assembly.Mass;
import util.Location;
import util.Vector;


public class CenterOfMass extends Force {

	private static final double DEFAULT_MAGNITUDE = 100;
	private static final double DEFAULT_EXPONENT = 2;
	
    private Location com;
    private double magnitude;
    private double exponent;

    public CenterOfMass () {
        this.magnitude = DEFAULT_MAGNITUDE;
        this.exponent = DEFAULT_EXPONENT;
    }
    
    public CenterOfMass (double magnitude, double exponent) {
        this.magnitude = magnitude;
        this.exponent = exponent;
    }

    public static CenterOfMass generator (Scanner line) {
        double magnitude = line.hasNextDouble() ? line.nextDouble() : DEFAULT_MAGNITUDE;
        double exponent  = line.hasNextDouble() ? line.nextDouble() : DEFAULT_EXPONENT;
        return new CenterOfMass(magnitude, exponent);
    }

    @Override
    public void apply (List<Mass> masses, Dimension bounds) {
        // apply a force to each mass according to its distance from the center of the masses
        com = calculateCOM(masses);
        for (Mass m : masses) {
            double dx = com.getX() - m.getX();
            double dy = com.getY() - m.getY();
            double r = Vector.distanceBetween(dx, dy);
            Vector force =
                    new Vector(Vector.angleBetween(dx, dy), magnitude / Math.pow(r, exponent));
            m.applyForce(force);
        }

    }

    /**
     * Helper function to calculate the center of mass of a list of masses
     */
    public Location calculateCOM (List<Mass> masses) {
        double x = 0;
        double y = 0;
        double totalMass = 0;

        for (Mass m : masses) {
            totalMass += m.getMass();
            x += m.getMass() * m.getX();
            y += m.getMass() * m.getY();
        }

        x /= totalMass;
        y /= totalMass;

        return new Location(x, y);
    }

}
