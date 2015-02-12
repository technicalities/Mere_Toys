import java.awt.Frame;

public class launchClause 
{

/** The main method
 * @param args the arguments  
 * @throws InterruptedException */
	
	public static void main(String [] args) throws InterruptedException
	{
		firstGUI passwordGUI = new firstGUI();
		passwordGUI.setVisible(true);
		passwordGUI.popupButtons();	
	}

}
