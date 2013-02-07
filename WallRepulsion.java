package simulation;

import java.awt.Dimension;
import java.util.List;
import java.util.Scanner;

import util.Vector;


public class WallRepulsion extends Force {
	
	private int id;
	private double magnitude;
	private double exponent;
	private Vector force;
	
	
	private static final int TOP = 1;
	private static final int RIGHT = 2;
	private static final int DOWN = 3;
	private static final int LEFT = 4;
	
	
	
	
	public WallRepulsion(int id, double magnitude, double exponent) {
		this.id = id;
		this.magnitude = magnitude;
		this.exponent = exponent;
	}
	
	public static WallRepulsion generator(Scanner line){
		int id = line.nextInt();
		double magnitude = line.nextDouble();
		double exponent = line.nextDouble();
		return new WallRepulsion(id, magnitude, exponent);
	}
	
	@Override
	public void apply(List<Mass> masses, Dimension bounds) {
		
		//apply a force opposite from a given wall

		for(Mass m : masses) {
			if(id == TOP) {
				force = new Vector(Mass.DOWN_DIRECTION, magnitude/Math.pow(m.getY(), exponent));
			}
			
			else if (id == RIGHT) {
				force = new Vector(Mass.LEFT_DIRECTION, magnitude/Math.pow(bounds.getWidth() - m.getX(), exponent));
			}
			
			else if (id == DOWN) {
				force = new Vector(Mass.UP_DIRECTION, magnitude/Math.pow(bounds.getHeight() - m.getY(), exponent));
			}
			
			else if (id == LEFT) {
				force = new Vector(Mass.RIGHT_DIRECTION, magnitude/Math.pow(m.getX(), exponent));
			}
			
			
			m.applyForce(force);
		}
		

	}

	public int getId() {
		return id;
	}
	
	

}
