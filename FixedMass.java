package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import util.Location;
import util.Pixmap;
import util.Sprite;

public class FixedMass extends Sprite {
	// reasonable default values
    public static final Dimension DEFAULT_SIZE = new Dimension(16, 16);
    public static final Pixmap DEFUALT_IMAGE = new Pixmap("mass.gif");

    public FixedMass (double x, double y, double mass) {
    super(DEFUALT_IMAGE, new Location(x, y), DEFAULT_SIZE);
}

@Override
public void paint (Graphics2D pen) {
    pen.setColor(Color.BLACK);
    pen.fillOval((int)getLeft(), (int)getTop(), (int)getWidth(), (int)getHeight());
}

public double distance (Mass other) {
    // this is a little awkward, so hide it
    return new Location(getX(), getY()).distance(new Location(other.getX(), other.getY()));
}



}