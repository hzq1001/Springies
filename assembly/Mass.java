package simulation.assembly;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.NoSuchElementException;
import java.util.Scanner;

import simulation.Model;
import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;


/**
 * XXX.
 * 
 * @author Robert C. Duvall
 */
public class Mass extends Sprite {
    // reasonable default values
    public static final Dimension DEFAULT_SIZE = new Dimension(16, 16);
    public static final Pixmap DEFUALT_IMAGE = new Pixmap("mass.gif");
    
	private static final double DEFAULT_MASS = 5;
	private static final double DEFAULT_Y_POSITION = 0;
	private static final double DEFAULT_X_POSITION = 0;

    private double myMass;
    private Vector myAcceleration;

    /**
     * XXX.
     */
    public Mass (double x, double y, double mass) {
        super(DEFUALT_IMAGE, new Location(x, y), DEFAULT_SIZE);
        myMass = mass;
        myAcceleration = new Vector();
    }

    public static Mass generator (Scanner line, Model model) {
    	
    	try {
    		int id = line.nextInt();
    		double x = line.hasNextDouble() ? line.nextDouble() : DEFAULT_X_POSITION;
    		double y = line.hasNextDouble() ? line.nextDouble() : DEFAULT_Y_POSITION;
    		double mass = line.hasNextDouble() ? line.nextDouble() : DEFAULT_MASS;
    		Mass result = new Mass(x, y, mass);
    		model.getMyMassesMap().put(id, result);
    		return result;
    		}
    	catch (NoSuchElementException e) {
    		System.out.println("File read error. Mass must specify id: " + e.getMessage());
    		System.exit(0);
    	}
    	return null;
    }

    /**
     * XXX.
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        // maybe we do not need FixedMass class
        if (myMass > 0) {
            applyForce(getBounce(bounds));
            // convert force back into Mover's velocity
            getVelocity().sum(myAcceleration);
            myAcceleration.reset();
            // move mass by velocity
            super.update(elapsedTime, bounds);
        }
    }

    /**
     * XXX.
     */
    @Override
    public void paint (Graphics2D pen) {
        pen.setColor(Color.BLACK);
        pen.fillOval((int) getLeft(), (int) getTop(), (int) getWidth(), (int) getHeight());
    }

    /**
     * Use the given force to change this mass's acceleration.
     */
    public void applyForce (Vector force) {
        myAcceleration.sum(force);
    }

    /**
     * Convenience method.
     */
    public double distance (Mass other) {
        // this is a little awkward, so hide it
        return new Location(getX(), getY()).distance(new Location(other.getX(), other.getY()));
    }

    // check for move out of bounds
    private Vector getBounce (Dimension bounds) {
        final double IMPULSE_MAGNITUDE = 2;
        Vector impulse = new Vector();
        if (getLeft() < 0) {
            impulse = new Vector(RIGHT_DIRECTION, IMPULSE_MAGNITUDE);
        }
        else if (getRight() > bounds.width) {
            impulse = new Vector(LEFT_DIRECTION, IMPULSE_MAGNITUDE);
        }
        if (getTop() < 0) {
            impulse = new Vector(DOWN_DIRECTION, IMPULSE_MAGNITUDE);
        }
        else if (getBottom() > bounds.height) {
            impulse = new Vector(UP_DIRECTION, IMPULSE_MAGNITUDE);
        }
        impulse.scale(getVelocity().getRelativeMagnitude(impulse));
        return impulse;
    }

    public double getMass () {
        // TODO Auto-generated method stub
        return myMass;
    }
}
