package simulation;

import java.awt.Dimension;
import java.util.List;

import util.Vector;

public abstract class Force extends Vector {

	public abstract void apply(List<Mass> masses, Dimension bounds);

	
}
