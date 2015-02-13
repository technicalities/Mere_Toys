package guis;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import app.JBController;

public class MatchGUI extends JPanel 
{
	/** GUI components */
	private JPanel matchPanel;
	private JButton newMatchButton, matchCancelButton;
	private JLabel weekLabel, locLabel, levelLabel, confirmationLabel;
	private JTextField weekField;
	private JComboBox<String> locationBox, levelBox;
	
	// Button Graphics
	private JBGraphicButtons icon = new JBGraphicButtons();

	/**************************************************************************
	 *  The Match GUI Constructor
	 * @param controller - the JBController
	 **************************************************************************/
	public MatchGUI(JBController controller)
	{
		//Instance Variables to store info for the Match GUI
		String matchFiller = String.format("%-200s", " ");
		String[] locationStrings = { "North", "Central", "South" };
		String[] levelStrings = { "Senior", "Junior" };

		//Initializes the Match Panel
		matchPanel = new JPanel(new FlowLayout());
		confirmationLabel = new JLabel(matchFiller);
		//  Initializes week number Label and field.
		weekLabel = new JLabel("Week Number: "); 
		weekField = new JTextField(2);		
		// Initializes match level and location drop-down menus and labels
		locLabel = new JLabel("Match location: ");
		locationBox = new JComboBox<String>(locationStrings);
		levelLabel = new JLabel("Match level: ");
		levelBox = new JComboBox<String>(levelStrings);
		// Creates four filler labels
		JLabel fillerWeek = new JLabel(matchFiller);
		JLabel fillerLocation = new JLabel(matchFiller);
		//JLabel fillerLevel = new JLabel(matchFiller);
		JLabel fillerLevel2 = new JLabel(matchFiller);		
		//  Initializes buttons
		newMatchButton = new JButton("Create Match");
		matchCancelButton = new JButton("Cancel");

		Border border = BorderFactory.createMatteBorder(105,0,0,0, Color.WHITE);//---------------------Check what this stuff does
		Border margin = BorderFactory.createEmptyBorder(30,0,0,0);
		matchPanel.setBorder(BorderFactory.createCompoundBorder(border, margin));

		// Set Colour
		matchPanel.setBackground(JBColour.jGreenMid());
		weekLabel.setForeground(Color.white);		
		locLabel.setForeground(Color.white);
		locationBox.setBackground(JBColour.jGreenMid());
		locationBox.setForeground(JBColour.jGreenLight());
		levelLabel.setForeground(Color.white);
		levelBox.setBackground(JBColour.jGreenMid());
		levelBox.setForeground(JBColour.jGreenLight());
		newMatchButton.setBackground(JBColour.jGreenLight());
		newMatchButton.setForeground(JBColour.jGrayMid());
		matchCancelButton.setBackground(JBColour.jGreenLight());
		matchCancelButton.setForeground(JBColour.jGrayMid());

		//Set Borders
		locationBox.setBorder(new LineBorder(JBColour.jGreenLight()));
		levelBox.setBorder(new LineBorder(JBColour.jGreenLight()));

		locationBox.setBounds(0, 0, 100, 20);
		locationBox.doLayout();

		levelBox.setVisible(true);

		//Add action Listeners
		locationBox.addActionListener(controller);
		newMatchButton.addActionListener(controller);
		matchCancelButton.addActionListener(controller);
		levelBox.addActionListener(controller);

		//Add all components to the MatchPanel
		matchPanel.add(weekLabel);
		matchPanel.add(weekField);
		matchPanel.add(fillerWeek);
		matchPanel.add(locLabel);
		matchPanel.add(locationBox);
		matchPanel.add(fillerLocation);
		matchPanel.add(levelLabel);
		matchPanel.add(levelBox);
		//matchPanel.add(fillerLevel);
		matchPanel.add(fillerLevel2);
		matchPanel.add(confirmationLabel);
		matchPanel.add(newMatchButton);
		matchPanel.add(matchCancelButton);
	}

	/***********************************************************************************
	 * A method to check whether input for week number is 
	 * a number and a number between 1 - 52
	 * @return boolean - the result of the check
	 **********************************************************************************/
	public boolean checkInput() 
	{	try
		{	// Try assign a number
			int x = Integer.parseInt(weekField.getText());
			// Check whether the number is NOT between 1 - 52
			if(x < 1 || x > 52) 
			{	// If it is not, throw a Warning JOptionPane with custom icon
				JOptionPane.showMessageDialog(this, "Please enter a number between 1 - 52", "Warning", JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(1));
				return false;
			}
		}catch(NumberFormatException e)
		{	// If assigning a number fails, throw a Warning JOptionPane with custom icon
			JOptionPane.showMessageDialog(this, "Please enter a number between 1 - 52", "Warning", JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(1));
			return false;
		}
		// If the input was correct
		return true;
	}
	
	/***********************************************************************************
	 * A method to reset all the gui elements into their default state
	 **********************************************************************************/
	public void resetMatchGUI()
	{
		weekField.setText("");
		locationBox.setSelectedIndex(0);
		levelBox.setSelectedIndex(0);
		confirmationLabel.setText(String.format("%-200s", " "));
	}

	/***********************************************************************************
	 * Accessor methods
	 **********************************************************************************/
	public int getWeekNumber() 
	{	//return int the week number
		return Integer.parseInt(weekField.getText());
	}

	public String getMatchLocation() 
	{	//return String the match location
		return (String)locationBox.getSelectedItem();
	}

	public int getMatchLevel() 
	{	//return int the match level
		return levelBox.getSelectedIndex();
	}

	public String getMatchLevelText() 
	{	//return String the match level
		return (String)levelBox.getSelectedItem();
	}
	
	public JButton getMatchCancelButton()
	{	//return JButton - the cancel button
		return matchCancelButton;
	}

	public JTextField getWeekField() 
	{	//return the JTextField for week number.
		return weekField;
	}
	
	public JButton getNewMatchButton()
	{	//return JButton - the cancel button
		return newMatchButton;
	}

	public JPanel getThisPanel()
	{	//return JPanel - the whole Match panel
		return matchPanel;
	}

	
	/***********************************************************************************
	 * Mutator methods
	 **********************************************************************************/
	public void setConfirmationLabel()
	{
		confirmationLabel.setText("You have added a " + getMatchLevelText() + " match in " + getMatchLocation() + " for week " + getWeekNumber() + ".");
	}



	
	
}