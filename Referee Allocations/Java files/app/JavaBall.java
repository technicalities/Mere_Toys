package app;

import java.io.IOException;

import guis.MainGUI;

/**************************************************************
 * The main class
 *************************************************************/
public class JavaBall {
	
	/**************************************************************
	 * The main method. Creates models, a controller, and a view, and links them.
	 * @param args the arguments
	 * @throws IOException 
	 *************************************************************/
	public static void main(String[] args) throws IOException 
	{
		JBController control = new JBController();		
		
		control.initReferees();
				
	}
}
