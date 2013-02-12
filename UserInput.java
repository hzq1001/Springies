package simulation;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Map;

import simulation.assembly.Mass;
import simulation.assembly.Spring;
import simulation.forces.CenterOfMass;
import simulation.forces.Force;
import simulation.forces.Gravity;
import simulation.forces.Viscosity;
import simulation.forces.WallRepulsion;
import view.Canvas;


public class UserInput {

    private final Canvas myView;
    private final Model myModel;

    private final Force myGravity;
    private final Force myViscosity;
    private final Force myCenterOfMass;
    private final Force myWallRepulsionTop;
    private final Force myWallRepulsionRight;
    private final Force myWallRepulsionDown;
    private final Force myWallRepulsionLeft;

    // defaults for mouse functionality
    private static final double DEFAULT_MOUSE_SPRING_CONSTANT = 0.2;

    // dummy mass and spring for the mouse functionality
    private static Mass mouseMass = new Mass(0, 0, -1);
    private static Spring mouseSpring = null;

    // UserInput is a Singleton
    private static UserInput instance;

    // Default force value
    private static final Force DEFAULT_GRAVITY = new Gravity();
    private static final Force DEFAULT_VISCOSITY = new Viscosity();
    private static final Force DEFAULT_CENTEROFMASS = new CenterOfMass();
    private static final Force DEFAULT_WALLREPULSION_TOP =    new WallRepulsion(WallRepulsion.TOP);
    private static final Force DEFAULT_WALLREPULSION_RIGHT =  new WallRepulsion(WallRepulsion.RIGHT);
    private static final Force DEFAULT_WALLREPULSION_DOWN =   new WallRepulsion(WallRepulsion.DOWN);
    private static final Force DEFAULT_WALLREPULSION_LEFT =   new WallRepulsion(WallRepulsion.LEFT);

    // Default wall size change value
    private static final int DEFAULT_CHANGE_VALUE = 10;

    private UserInput(final Canvas view, final Model model) {
        myView = view;
        myModel = model;
        myGravity = DEFAULT_GRAVITY;
        myViscosity = DEFAULT_VISCOSITY;
        myCenterOfMass = DEFAULT_CENTEROFMASS;
        myWallRepulsionTop = DEFAULT_WALLREPULSION_TOP;
        myWallRepulsionRight = DEFAULT_WALLREPULSION_RIGHT;
        myWallRepulsionDown = DEFAULT_WALLREPULSION_DOWN;
        myWallRepulsionLeft = DEFAULT_WALLREPULSION_LEFT;
    }

    public static UserInput getInstance(final Canvas view, final Model model) {
        if (instance == null) {
            synchronized (UserInput.class) {
                if (instance == null) {
                    instance = new UserInput(view, model);
                }
            }
        }

        return instance;
    }

    public void update(final double elapsedTime) {
        handleKeys();
        handleMouse();
    }

    private void handleKeys() {
        final int key = myView.getLastKeyPressed();

        if (key == KeyEvent.VK_N) {
            myView.loadAssembly();
            myView.resetLastKeyPressed();
        }
        else if (key == KeyEvent.VK_C) {
            myModel.clear();
        }

        else if (key == KeyEvent.VK_G) {
            toggleForce("gravity", myGravity);
        }

        else if (key == KeyEvent.VK_V) {
            toggleForce("viscosity", myViscosity);
        }

        else if (key == KeyEvent.VK_M) {
            toggleForce("centermass", myCenterOfMass);
        }

        else if (key == KeyEvent.VK_1) {
            toggleForce("wall1", myWallRepulsionTop);
        }

        else if (key == KeyEvent.VK_2) {
            toggleForce("wall2", myWallRepulsionRight);
        }

        else if (key == KeyEvent.VK_3) {
            toggleForce("wall3", myWallRepulsionDown);
        }

        else if (key == KeyEvent.VK_4) {
            toggleForce("wall4", myWallRepulsionLeft);
        }

        else if (key == KeyEvent.VK_UP) {
            increaseWallArea(DEFAULT_CHANGE_VALUE);
        }

        else if (key == KeyEvent.VK_DOWN) {
            decreaseWallArea(DEFAULT_CHANGE_VALUE);
        }
    }

    private void increaseWallArea(final int pixel) {
        final Dimension wall = myModel.getMyWallArea();
        final int newHight = (int) wall.getHeight() + pixel;
        final int newWidth = (int) wall.getWidth() + pixel;
        final Dimension newWall = new Dimension(newWidth, newHight);
        myModel.setMyWallArea(newWall);
        myView.resetLastKeyPressed();
    }

    private void decreaseWallArea(final int pixel) {
        increaseWallArea(-pixel);

    }

    private void toggleForce(final String name, Force f) {
        final Map<String, Force> m = myModel.getMyForceMap();
        if (m.containsKey(name)) {
            f = m.get(name);
            m.remove(name);
        }
        else {
            m.put(name, f);
        }
        myView.resetLastKeyPressed();
    }

    private void handleMouse() {

        // mouse not pressed
        if (!myView.getMousePressed()) {
            myModel.remove(mouseMass);
            myModel.remove(mouseSpring);
            mouseSpring = null;
        }
        else {// mouse pressed or dragged

            // set mouse spring and mass position
            if (myView.withinCanvas(myView.getMousePosition())) {
                mouseMass.setCenter(myView.getMousePosition().getX(), myView
                        .getMousePosition().getY());
            }

            // mouse pressed for first time
            if (mouseSpring == null) {
                mouseSpring =
                        new Spring(
                                   mouseMass,
                                   myModel.getNearestMass(mouseMass),
                                   myModel.getShortestDistance(),
                                   DEFAULT_MOUSE_SPRING_CONSTANT);

                // add the dummy mass and spring to the simulation
                myModel.add(mouseMass);
                myModel.add(mouseSpring);
            }
        }
    }

}
