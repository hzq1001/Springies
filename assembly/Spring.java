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
public class Spring extends Sprite {
    // reasonable default values
    public static final Pixmap DEFUALT_IMAGE = new Pixmap("spring.gif");
    public static final int IMAGE_HEIGHT = 20;
	private static final double DEFAULT_REST_LENGTH = 100;
	private static final double DEFAULT_KS = 0.1;

    private Mass myStart;
    private Mass myEnd;
    private double myLength;
    private double myK;

    /**
     * XXX.
     */
    public Spring (Mass start, Mass end, double length, double kVal) {
        super(DEFUALT_IMAGE, getCenter(start, end), getSize(start, end));
        myStart = start;
        myEnd = end;
        myLength = length;
        myK = kVal;
    }

    /**
     * XXX.
     */
    @Override
    public void paint (Graphics2D pen) {
        pen.setColor(getColor(myStart.distance(myEnd) - myLength));
        pen.drawLine((int) myStart.getX(), (int) myStart.getY(), (int) myEnd.getX(),
                     (int) myEnd.getY());
    }

    /**
     * XXX.
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        double dx = myStart.getX() - myEnd.getX();
        double dy = myStart.getY() - myEnd.getY();
        // apply hooke's law to each attached mass
        Vector force = new Vector(Vector.angleBetween(dx, dy),
                                  myK * (myLength - Vector.distanceBetween(dx, dy)));
        myStart.applyForce(force);
        force.negate();
        myEnd.applyForce(force);
        // update sprite values based on attached masses
        setCenter(getCenter(myStart, myEnd));
        setSize(getSize(myStart, myEnd));
        setVelocity(Vector.angleBetween(dx, dy), 0);
    }

    public static Spring generator (Scanner line, Model model) {
    	
    	try {
	        Mass m1 = model.getMyMassesMap().get(line.nextInt());
	        Mass m2 = model.getMyMassesMap().get(line.nextInt());
	        double restLength = line.hasNextDouble() ? line.nextDouble() : DEFAULT_REST_LENGTH;
	        double ks         = line.hasNextDouble() ? line.nextDouble() : DEFAULT_KS;
	        return new Spring(m1, m2, restLength, ks);
    	}
    	catch (NoSuchElementException e) {
    		System.out.println("File read error. Spring must specify masses: " + e.getMessage());
    		System.exit(0);
    	}
    	return null;
        
    }

    /**
     * Convenience method.
     */
    protected Color getColor (double diff) {
        if (Vector.fuzzyEquals(diff, 0))
            return Color.BLACK;
        else if (diff < 0.0)
            return Color.BLUE;
        else return Color.RED;
    }

    // compute center of this spring
    private static Location getCenter (Mass start, Mass end) {
        return new Location((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    // compute size of this spring
    private static Dimension getSize (Mass start, Mass end) {
        return new Dimension((int) start.distance(end), IMAGE_HEIGHT);
    }

//    protected double getLength () {
//        return myLength;
//    }
//
//    protected void setLength (double myLength) {
//        this.myLength = myLength;
//    }

    public Mass getMyStart() {
        return myStart;
    }

    public void setMyStart(Mass myStart) {
        this.myStart = myStart;
    }

    public Mass getMyEnd() {
        return myEnd;
    }

    public void setMyEnd(Mass myEnd) {
        this.myEnd = myEnd;
    }

    public double getMyLength() {
        return myLength;
    }

    public void setMyLength(double myLength) {
        this.myLength = myLength;
    }

    public double getMyK() {
        return myK;
    }

    public void setMyK(double myK) {
        this.myK = myK;
    }
    
    

}
