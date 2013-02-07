package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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
 	
 	/**
     * Create a game of the given size with the given display for its shapes.
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        setMyMassesMap(new HashMap<Integer, Mass>());
        setMyForceMap(new HashMap<String, Force>());
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
    	
    	Dimension bounds = myView.getSize();
    	
    	for (Force f : myForceMap.values()) {
    		f.apply(myMasses, bounds);
    	}
    	
        for (Spring s : mySprings) {
            s.update(elapsedTime, bounds);
        }
        for (Mass m : myMasses) {
            m.update(elapsedTime, bounds);
        }
    }

    /**
     * Add given mass to this simulation.
     */
    public void add (Mass mass) {
        myMasses.add(mass);
    }

    /**
     * Add given spring to this simulation.
     */
    public void add (Spring spring) {
        mySprings.add(spring);
    }
    
    /**
     * Add given force to this simulation.
     */
    public void add (String name, Force force) {
        myForceMap.put(name, force);
    }
    
    public void clear() {
    	myMasses.clear();
    	mySprings.clear();
    	myMassesMap.clear();
    }

	public Map<Integer, Mass> getMyMassesMap() {
		return myMassesMap;
	}

	public void setMyMassesMap(Map<Integer, Mass> myMassesMap) {
		this.myMassesMap = myMassesMap;
	}

	public void setMyForceMap(Map<String, Force> myForceMap) {
		this.myForceMap = myForceMap;
	}
	public Map<String, Force> getMyForceMap() {
		return myForceMap;
	}

	
}
