package simulation;

import java.awt.Dimension;

import util.Vector;

public class FixedMass extends Mass {

	public FixedMass(double x, double y, double mass) {
		super(x, y, mass);
	}
	
	 @Override
	    public void update (double elapsedTime, Dimension bounds) {
		 super.setVelocity(0, 0);
		 super.update(elapsedTime, bounds);
	 }
	 
	 @Override
	 public void applyForce (Vector force) {
	        //do nothing.  no force can move my immovable object!
	    }

}
