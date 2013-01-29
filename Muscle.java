package simulation;

import java.awt.Dimension;

public class Muscle extends Spring {
	
	private double myRestingLength;
	private double myAmplitude;
    private double myPeriod;
    private double myAngle;
    
	public Muscle(Mass start, Mass end, double length, double kVal, double amplitude, double period) {
		super(start, end, length, kVal);
		myRestingLength = length;
		myAmplitude = amplitude;
		myPeriod = period;
		myAngle = 0;
	}

	@Override
    public void update (double elapsedTime, Dimension bounds) {
        super.update(elapsedTime, bounds);
        updateLength(elapsedTime);
    }
	
	private void updateLength(double elapsedTime) {
		myAngle += elapsedTime/myPeriod * 2*Math.PI;
		
		//after every cycle, reset angle to 0
		if(myAngle > 2*Math.PI)
			myAngle -= 2*Math.PI;
		
		super.setLength(myRestingLength + myAmplitude*Math.sin(myAngle));
	}
	
}
