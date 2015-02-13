package guis;
import java.awt.Dimension;
import javax.swing.*;

public class RectangleBar extends JProgressBar
{
	private JProgressBar rectangleBar;
	
	//The maximum number of matches
	private final int MAX_MATCHES = 52;
	//The width and height of the bar
	private final int BAR_WIDTH = 350;
	private final int BAR_HEIGHT = 18;
	
	/**************************************************************************
	 * Default Constructor
	 *************************************************************************/
	public RectangleBar(int numMatches) 
	{	//Initialize the ProgressBar
		rectangleBar = new JProgressBar();
		//Set the progress bar maximum value
		rectangleBar.setMaximum(MAX_MATCHES);
		//Delete the border of the bar
		rectangleBar.setBorderPainted(false);
		//Set the preferred size of the bar
		rectangleBar.setPreferredSize(new Dimension(BAR_WIDTH,BAR_HEIGHT));
		//Set the bars current values
		rectangleBar.setValue(numMatches);	
		//Set the color of the bar
		rectangleBar.setForeground(JBColour.jGreenMid());
		//Makes the bar String visible and adds the number of Matches as a title
		rectangleBar.setStringPainted(true);
		rectangleBar.setString(numMatches + " Matches");
	}

	/**************************************************************************
	 * Accessor method
	 * @return rectangleBar - a JProgressBar
	 *************************************************************************/
	public JProgressBar getRectangle()
	{	
		return rectangleBar;
	}	
}