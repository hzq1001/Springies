package simulation;

import java.util.Scanner;

public class EnvironmentFactory extends Factory {

	private static final String GRAVITY_KEYWORD = "gravity";
    private static final String VISCOSITY_KEYWORD = "viscosity";
    private static final String CENTERMASS_KEYWORD = "centermass";
    private static final String WALLREPULSION_KEYWORD = "wall";
	
	@Override
	public void evaluateLine(Model model, Scanner line) {
		if (line.hasNext()) {
            String type = line.next();
            if (GRAVITY_KEYWORD.equals(type)) {
                model.add(GRAVITY_KEYWORD, Gravity.generator(line));
            }
            else if (VISCOSITY_KEYWORD.equals(type)) {
                model.add(VISCOSITY_KEYWORD, Viscosity.generator(line));
            }

            else if (CENTERMASS_KEYWORD.equals(type)) {
                model.add(CENTERMASS_KEYWORD, CenterOfMass.generator(line));
            }

            else if (WALLREPULSION_KEYWORD.equals(type)) {
                WallRepulsion w = WallRepulsion.generator(line);
                model.add(WALLREPULSION_KEYWORD + Integer.toString(w.getId()), w);
            }
        }
	}

}
