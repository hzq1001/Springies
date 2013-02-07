package simulation;

import java.awt.event.KeyEvent;
import java.util.Collection;

import view.Canvas;

public class UserInput {
	
	private Canvas myView;
	private Model myModel;
	
	//UserInput is a Singleton
	private static UserInput instance;
	
	private UserInput (Canvas view, Model model) {
		this.myView = view;
		this.myModel = model;
	}
	
	public static UserInput getInstance(Canvas view, Model model)
	{
		if (instance == null)
		{
			synchronized(UserInput.class)
			{
				if (instance == null)
					instance = new UserInput(view, model);
			}            
		}

		return instance;
	}
	
	public void update(double elapsedTime) {
		handleKeys();
		handleMouse();
	}
	
	private void handleKeys() {
		Collection<Integer> keys = myView.getKeysPressed();
		
		if(myView.getLastKeyPressed() == KeyEvent.VK_N) {
			myView.loadAssembly();
		}
		
	}
	private void handleMouse() {
		
	}
	
	
	
}
