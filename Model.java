package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import util.Location;
import view.Canvas;


/**
 * XXX.
 * 
 * @author Robert C. Duvall
 */
public class Model {
    // bounds and input for game
    private Canvas myView;
    // simulation state
    private List<Mass> myMasses;
    private List<Spring> mySprings;
 	private Map<Integer, Mass> myMassesMap;
 	private Map<String, Force> myForceMap;
 	private Dimension myWallArea;
 	
 	/**
     * Create a game of the given size with the given display for its shapes.
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        setMassesMap(new HashMap<Integer, Mass>());
        setForceMap(new HashMap<String, Force>());
        myWallArea = myView.getSize();
    }

    /**
     * Draw all elements of the simulation.
     */
    public void paint (Graphics2D pen) {
        for (Spring s : mySprings) {
            s.paint(pen);
        }
        for (Mass m : myMasses) {
            m.paint(pen);
        }
    }

    
    /**
     * Update simulation for this moment, given the time since the last moment.
     */
    public void update (double elapsedTime) {
    	
    	//handles user input logic
    	UserInput.getInstance(myView, this).update(elapsedTime);
    	
    	//Dimension bounds = myView.getSize();
    	
    	for (Force f : myForceMap.values()) {
    		f.apply(myMasses, myWallArea);
    	}
    	
        for (Spring s : mySprings) {
            s.update(elapsedTime, myWallArea);
        }
        for (Mass m : myMasses) {
            m.update(elapsedTime, myWallArea);
        }
    }

    /**
     * Add given mass to this simulation.
     */
    public void add (Mass mass) {
        myMasses.add(mass);
    }
    
    /**
     * Remove given mass from this simulation.
     */
    public void remove (Mass mass) {
        myMasses.remove(mass);
    }

    /**
     * Add given spring to this simulation.
     */
    public void add (Spring spring) {
        mySprings.add(spring);
    }
    
    /**
     * Remove given spring from this simulation.
     */
    public void remove (Spring spring) {
        mySprings.remove(spring);
    }
    
    /**
     * Add given force to this simulation.
     */
    public void add (String name, Force force) {
        myForceMap.put(name, force);
    }
    
    /**
     * Remove given force from this simulation.
     */
    public void remove (String name) {
        myForceMap.remove(name);
    }
    
    public void clear() {
    	myMasses.clear();
    	mySprings.clear();
    	myMassesMap.clear();
    }

	public Map<Integer, Mass> getMassesMap() {
		return myMassesMap;
	}

	public List<Mass> getMasses() {
		return myMasses;
	}

	public void setMasses(List<Mass> myMasses) {
		this.myMasses = myMasses;
	}

	public void setMassesMap(Map<Integer, Mass> myMassesMap) {
		this.myMassesMap = myMassesMap;
	}

	public void setForceMap(Map<String, Force> myForceMap) {
		this.myForceMap = myForceMap;
	}
	public Map<String, Force> getForceMap() {
		return myForceMap;
	}

	public Dimension getWallArea() {
		return myWallArea;
	}

	public void setWallArea(Dimension myWallArea) {
		this.myWallArea = myWallArea;
	}

	
	/**
	 * Returns the mass nearest to a given location
	 */
	public Mass getNearestMass(double x, double y) {
		Mass nearestMass = null;
		double shortestDistance = myView.getWidth() + myView.getHeight(); //will always be larger than any other distance
		double distance;
		for (Mass mass : myMasses) {
			if (nearestMass == null)
				nearestMass = mass;
			
			distance = distance(x,y, mass.getX(), mass.getY());
			if (distance < shortestDistance) {
				shortestDistance = distance;
				nearestMass = mass;
			}
		}
		return nearestMass;
	}
	
	/**
	 *  Helper function that measures the distance between two points
	 *  TODO: replace this with a default method from somewhere.
	 */
	private double distance(double x1, double y1, double x2, double y2) {
		//pythagorean theorem
		return Math.pow(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2), 0.5);
	}
	
	

	
}
