package simulation.assembly;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Scanner;

import simulation.Model;
import util.Vector;


public class Muscle extends Spring {

    private final double myRestingLength;
    private final double myAmplitude;
    private final double myPeriod;
    private double myAngle;

    // override muscle colors
    private static Color COMPRESSED_COLOR = new Color(0, 0, 165, 255); // dark
                                                                       // blue
    private static Color TENSIONED_COLOR = new Color(178, 34, 34, 255);  // dark
                                                                        // red
    
    private static final double DEFAULT_AMPLITUDE = 100;
    private static final double DEFAULT_PERIOD = 2;

    public Muscle(final Mass start, final Mass end, final double length, final double kVal, final double amplitude, final double period) {
        super(start, end, length, kVal);
        myRestingLength = length;
        myAmplitude = amplitude;
        myPeriod = period;
        myAngle = 0;
    }

    public static Muscle generator(final Scanner line, final Model model) {
        Spring s = Spring.generator(line, model);
        
        final double amplitude = line.hasNextDouble() ? line.nextDouble() : DEFAULT_AMPLITUDE;
        final double period    = line.hasNextDouble() ? line.nextDouble() : DEFAULT_PERIOD;
        
        return new Muscle(s.getMyStart(), s.getMyEnd(), s.getMyLength(), s.getMyK(), amplitude, period);
    }

    @Override
    public void update(final double elapsedTime, final Dimension bounds) {
        super.update(elapsedTime, bounds);
        updateLength(elapsedTime);
    }

    private void updateLength(final double elapsedTime) {
        myAngle += elapsedTime / myPeriod * 2 * Math.PI;

        // after every cycle, reset angle to 0
        if (myAngle > 2 * Math.PI) {
            myAngle -= 2 * Math.PI;
        }

        super.setMyLength(myRestingLength + myAmplitude * Math.sin(myAngle));
    }

    /**
     * Override the color method so that muscles can be visually discerned from
     * springs.
     */
    @Override
    protected Color getColor(final double diff) {
        if (Vector.fuzzyEquals(diff, 0)) {
            return Color.BLACK;
        } else if (diff < 0.0) {
            return COMPRESSED_COLOR;
        } else {
            return TENSIONED_COLOR;
        }
    }

}
