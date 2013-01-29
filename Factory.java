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
	// mass IDs
	Map<Integer, Mass> myMasses = new HashMap<Integer, Mass>();


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
						model.add(massCommand(line));
					}
					else if (SPRING_KEYWORD.equals(type)) {
						model.add(springCommand(line));
					}
					else if (MUSCLE_KEYWORD.equals(type)) {
						model.add(muscleCommand(line));
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
						model.add(gravityCommand(line));
					}
					else if (VISCOSITY_KEYWORD.equals(type)) {
						model.add(viscosityCommand(line));
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

	// create mass from formatted data
	private Mass massCommand (Scanner line) {
		int id = line.nextInt();
		double x = line.nextDouble();
		double y = line.nextDouble();
		double mass = line.nextDouble();
		Mass result = new Mass(x, y, mass);
		myMasses.put(id,  result);
		return result;
	}

	// create spring from formatted data
	private Spring springCommand (Scanner line) {
		Mass m1 = myMasses.get(line.nextInt());
		Mass m2 = myMasses.get(line.nextInt());
		double restLength = line.nextDouble();
		double ks = line.nextDouble();
		return new Spring(m1, m2, restLength, ks);
	}

	// create muscle from formatted data
	private Spring muscleCommand (Scanner line) {
		Mass m1 = myMasses.get(line.nextInt());
		Mass m2 = myMasses.get(line.nextInt());
		double restLength = line.nextDouble();
		double ks = line.nextDouble();
		double amplitude = line.nextDouble();
		double period = line.nextDouble();
		return new Muscle(m1, m2, restLength, ks, amplitude, period);
	}

	private Force gravityCommand (Scanner line) {
		double angle = line.nextDouble();
		double magnitude = line.nextDouble();
		return new Gravity(angle, magnitude);
	}
	
	private Force viscosityCommand (Scanner line) {
		double scale = line.nextDouble();
		return new Viscosity(scale);
	}
}
