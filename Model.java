package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulation.assembly.Mass;
import simulation.assembly.Spring;
import simulation.forces.Force;
import view.Canvas;


/**
 * XXX.
 * 
 * @author Robert C. Duvall
 */
public class Model {
    // bounds and input for game
    private Canvas myView;
    private UserInput myUserInput;
    // simulation state
    private List<Mass> myMasses;
    private List<Spring> mySprings;
    private Map<Integer, Mass> myMassesMap;
    private Map<String, Force> myForceMap;
    private Dimension myWallArea;

    private double shortestDistance;

    /**
     * Create a game of the given size with the given display for its shapes.
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        setMyMassesMap(new HashMap<Integer, Mass>());
        setMyForceMap(new HashMap<String, Force>());
        myWallArea = myView.getSize();
        myUserInput = new UserInput(canvas, this);
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

        // handles user input logic
        myUserInput.update(elapsedTime);

        // Dimension bounds = myView.getSize();

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

    public void clear () {
        myMasses.clear();
        mySprings.clear();
        myMassesMap.clear();
    }

    public Map<Integer, Mass> getMyMassesMap () {
        return myMassesMap;
    }

    public void setMyMassesMap (Map<Integer, Mass> myMassesMap) {
        this.myMassesMap = myMassesMap;
    }

    public void setMyForceMap (Map<String, Force> myForceMap) {
        this.myForceMap = myForceMap;
    }

    public Map<String, Force> getMyForceMap () {
        return myForceMap;
    }

    public Dimension getMyWallArea () {
        return myWallArea;
    }

    public void setMyWallArea (Dimension myWallArea) {
        this.myWallArea = myWallArea;
    }

    /**
     * Returns the mass nearest to a given location
     */
    public Mass getNearestMass (Mass myMass) {
        Mass nearestMass = null;
        shortestDistance = myView.getWidth() + myView.getHeight();
        // //will always be larger than any other distance
        double distance;
        for (Mass mass : myMasses) {
            if (nearestMass == null) {
                nearestMass = mass;
            }

            distance = myMass.distance(mass);
            if (distance < shortestDistance) {
                shortestDistance = distance;
                nearestMass = mass;
            }
        }
        return nearestMass;
    }

    public double getShortestDistance () {
        return shortestDistance;
    }

    public void setShortestDistance (double shortestDistance) {
        this.shortestDistance = shortestDistance;
    }


}
