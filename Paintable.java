package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;

import util.Vector;

public interface Paintable {
	public void paint(Graphics2D pen);
	public void update(double elapsedTime, Dimension bounds);
	public void applyForce(Vector forceVector);

}
