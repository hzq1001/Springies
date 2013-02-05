package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * XXX
 * 
 * @author Robert C. Duvall
 */
public class Factory {
	// data file keywords
	private static final String MASS_KEYWORD = "mass";
	private static final String SPRING_KEYWORD = "spring";
	private static final String MUSCLE_KEYWORD = "muscle";

	//environment keywords
	private static final String GRAVITY_KEYWORD = "gravity";
	private static final String VISCOSITY_KEYWORD = "viscosity";
	private static final String CENTERMASS_KEYWORD = "centermass";
	private static final String WALLREPULSION_KEYWORD = "wall";
	


	public void loadModel (Model model, File dataFile) {
		loadObjects(model, dataFile);
	}

	public void loadModel(Model model, File dataFile, File envFile) {
		loadObjects(model, dataFile);
		loadEnvironment(model, envFile);
	}

	public void loadObjects(Model model, File dataFile) {
		try {
			Scanner input = new Scanner(dataFile);
			while (input.hasNext()) {
				Scanner line = new Scanner(input.nextLine());
				if (line.hasNext()) {
					String type = line.next();
					if (MASS_KEYWORD.equals(type)) {
						model.add(Mass.generator(line, model));
					}
					else if (SPRING_KEYWORD.equals(type)) {
						model.add(Spring.generator(line, model));
					}
					else if (MUSCLE_KEYWORD.equals(type)) {
						model.add(Muscle.generator(line, model));
					}
				}
			}
			input.close();
		}
		catch (FileNotFoundException e) {
			// should not happen because File came from user selection
			e.printStackTrace();
		}
	}

	private void loadEnvironment(Model model, File envFile) {
		try {
			//optional second file
			Scanner input = new Scanner(envFile);
			while (input.hasNext()) {
				Scanner line = new Scanner(input.nextLine());
				if (line.hasNext()) {
					String type = line.next();
					if (GRAVITY_KEYWORD.equals(type)) {
						model.add(Gravity.generator(line));
					}
					else if (VISCOSITY_KEYWORD.equals(type)) {
						model.add(Viscosity.generator(line));
					}
					
					else if (CENTERMASS_KEYWORD.equals(type)) {
						model.add(CenterOfMass.generator(line));
					}
					
					else if (WALLREPULSION_KEYWORD.equals(type)) {
						model.add(WallRepulsion.generator(line));
					}
				}
			}
			input.close();

		}
		catch (FileNotFoundException e) {
			// should not happen because File came from user selection
			e.printStackTrace();
		}
	}

}
