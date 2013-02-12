package simulation.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import simulation.Model;


/**
 * XXX
 * 
 * @author Robert C. Duvall
 */
public abstract class Factory {

    public Factory() {
    	
    }
    
    public void load(Model model, File dataFile) {
    	try {
            Scanner input = new Scanner(dataFile);
            while (input.hasNext()) {
                evaluateLine(model, new Scanner(input.nextLine()));
            }
            input.close();
        }
        catch (FileNotFoundException e) {
            // should not happen because File came from user selection
            e.printStackTrace();
        }
    }
    
    public abstract void evaluateLine(Model model, Scanner line);
}
