package simulation;

import java.awt.Dimension;
import java.util.List;
import java.util.Scanner;
import util.Vector;


public class Viscosity extends Force {

    double scale;

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
            // value
            Vector force = m.getVelocity();
            force.negate();
            force.scale(scale);
            m.applyForce(force);
        }

    }

}
