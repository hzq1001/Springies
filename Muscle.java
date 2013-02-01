package simulation;

import java.awt.Color;
import java.awt.Dimension;

import util.Vector;

public class Muscle extends Spring {
	
	private double myRestingLength;
	private double myAmplitude;
    private double myPeriod;
    private double myAngle;
    
    //override muscle colors
    private static Color COMPRESSED_COLOR = new Color(0,0,165,255); //dark blue
    private static Color TENSIONED_COLOR = new Color(178,34,34,255);  //dark red
    
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
	
	
	/**
	 * Override the color method so that muscles can be visually discerned from springs.
	 */
	@Override
    protected Color getColor (double diff) {
        if (Vector.fuzzyEquals(diff, 0)) return Color.BLACK;
        else if (diff < 0.0) return COMPRESSED_COLOR;
        else return TENSIONED_COLOR;
    }
	
}
