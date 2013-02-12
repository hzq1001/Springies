package simulation.factory;

import java.util.Scanner;

import simulation.Model;
import simulation.assembly.Mass;
import simulation.assembly.Muscle;
import simulation.assembly.Spring;

public class AssemblyFactory extends Factory {

	private static final String MASS_KEYWORD = "mass";
    private static final String SPRING_KEYWORD = "spring";
    private static final String MUSCLE_KEYWORD = "muscle";

	
	@Override
	public void evaluateLine(Model model, Scanner line) {
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

}
