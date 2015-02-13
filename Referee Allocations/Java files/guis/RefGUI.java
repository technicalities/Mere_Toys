package guis;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import referees.Referee;
import app.JBController;

public class RefGUI extends JPanel
{
	/** GUI components */
	private JPanel refPanel;
	private JButton refCancelButton, newRefButton, searchRefButton, deleteButton;
	private JLabel fNameLabel, sNameLabel, willingToLabel, levelLabel, locLabel, idLabel, allocLabel;
	private JLabel fillerID, fillerButtons, fillerNames, fillerQual, fillerAlloc, fillerWilling, fillerHome;
	private JTextField fNameField, sNameField, idField, allocField;
	private JComboBox<String> locBox, levelBox;
	private JCheckBox northCheck, centerCheck, southCheck;
	private final String fillerString = String.format("%-200s", " ");
	
	// Button Graphics
	private JBGraphicButtons icon = new JBGraphicButtons();

	private final int NORTH = 0;
	private final int CENTRAL = 1;
	private final int SOUTH = 2;

	private final String[] LEVEL_STRINGS = { "NJB1", "IJB1", "NJB2", "IJB2", "NJB3","IJB3", "NJB4", "IJB4"};
	/**************************************************************************
	 *  Constructor.
	 * @param controller - the controller object
	 **************************************************************************/
	public RefGUI(JBController controller)
	{
		//  Initializes all Components for refGUI
		refPanel = new JPanel(new FlowLayout());
		idLabel = new JLabel("Ref ID:   "); 
		idField = new JTextField(4);
		fillerID = new JLabel(fillerString);
		fNameLabel = new JLabel("First Name: "); 
		fNameField = new JTextField(15);
		sNameLabel = new JLabel("Surname:   "); 
		sNameField = new JTextField(15);
		allocLabel = new JLabel("Allocations:   ");
		allocField = new JTextField(5);
		fillerButtons = new JLabel(fillerString);
		searchRefButton = new JButton("Search");
		refCancelButton = new JButton("Cancel");
		newRefButton = new JButton("Update");
		deleteButton = new JButton("Delete");

		//  Adds a Label to the Home Location drop-down menu.
		locLabel = new JLabel("Home location: ");
		// Create the Location drop-down menu.
		String[] locationStrings = { "North", "Central", "South" };
		locBox = new JComboBox<String>(locationStrings);
		// WillingToTravel Label and Areas checkBoxes
		willingToLabel = new JLabel("Willing to Ref: ");
		northCheck = new JCheckBox("North"); 
		centerCheck = new JCheckBox("Center"); 
		southCheck = new JCheckBox("South");
		//  Adds qualification Label drop-down list.
		levelLabel = new JLabel("Highest qualification: ");
		//Add the qualification level drop-down list
		levelBox = new JComboBox<String>(LEVEL_STRINGS);
		//Create some empty JLabel for better visual results
		fillerNames = new JLabel(fillerString);
		fillerHome = new JLabel(fillerString);
		fillerWilling = new JLabel(fillerString);
		fillerQual = new JLabel(fillerString);
		fillerAlloc = new JLabel(fillerString);

		// Add Action Listeners to the buttons
		searchRefButton.addActionListener(controller);
		refCancelButton.addActionListener(controller);
		newRefButton.addActionListener(controller);
		deleteButton.addActionListener(controller);
		locBox.addActionListener(controller);
		northCheck.addActionListener(controller);
		centerCheck.addActionListener(controller);
		southCheck.addActionListener(controller);
		levelBox.addActionListener(controller);

		//Set Foreground and Background colours for all components
		setRefSearchComponentColors();

		// Add an empty border to center the contents of the panel in the screen
		refPanel.setBorder(BorderFactory.createEmptyBorder(30,0,10,0));
		refPanel.setVisible(false);
	}

	/**************************************************************************
	 * Set Foreground and Background colours for search components
	 *************************************************************************/
	private void setRefSearchComponentColors() 
	{	// Set 
		fNameField.setBackground(Color.white);
		sNameField.setBackground(Color.white);
		refPanel.setBackground(JBColour.jGrayMid());
		idField.setBackground(Color.white);
		setBackAndForeground(idLabel,JBColour.jGrayMid(), Color.white);
		setBackAndForeground(fNameLabel,JBColour.jGrayMid(), Color.white);
		setBackAndForeground(sNameLabel,JBColour.jGrayMid(), Color.white);
		setBackAndForeground(searchRefButton,JBColour.jGrayDark(), Color.white);
		setBackAndForeground(refCancelButton,JBColour.jGrayDark(), Color.white);
		setBackAndForeground(allocLabel,JBColour.jGrayMid(), Color.white);
	}

	/**************************************************************************
	 * Set Foreground and Background colours for update/delete ref components
	 *************************************************************************/
	private void setRefUpdateComponentColors() 
	{	// Set Background and Foreground Colours
		locLabel.setForeground(Color.white);
		willingToLabel.setForeground(Color.white);
		levelLabel.setForeground(Color.white);
		setBackAndForeground(locBox,JBColour.jGrayDark(), Color.white);
		setBackAndForeground(northCheck,JBColour.jGrayMid(), Color.white);
		setBackAndForeground(centerCheck,JBColour.jGrayMid(), Color.white);
		setBackAndForeground(southCheck,JBColour.jGrayMid(), Color.white);
		setBackAndForeground(levelBox,JBColour.jGrayDark(), Color.white);
		setBackAndForeground(deleteButton,JBColour.jGrayDark(), Color.white);
		setBackAndForeground(newRefButton,JBColour.jGrayDark(), Color.white);

		locBox.setBorder(new LineBorder(JBColour.jGrayDark()));
		levelBox.setBorder(new LineBorder(JBColour.jGrayDark()));
	}

	/*******************************************************************************
	 * A method to set the background and foreground colors of a component
	 * @param aComponent - the JComponent
	 * @param backColor,foreColor
	 ******************************************************************************/
	private void setBackAndForeground(JComponent aComponent, Color backColor, Color foreColor){
		aComponent.setBackground(backColor);
		aComponent.setForeground(foreColor);
	}

	/**************************************************************************
	 *  Method adds the ref components which don't vary from screen to screen.
	 **************************************************************************/
	public void displayRefNameID()
	{	//Make components visible
		refPanel.setVisible(true);
		idLabel.setVisible(false);
		idField.setVisible(false);
		//Disable the idField
		idField.setEditable(false);

		//Add all components to the refGUI
		refPanel.add(idLabel);
		refPanel.add(idField);
		refPanel.add(fillerID);
		refPanel.add(fNameLabel);
		refPanel.add(fNameField);
		refPanel.add(fillerNames);
		refPanel.add(sNameLabel);
		refPanel.add(sNameField);
		refPanel.add(fillerButtons);

		//Refresh Components
		validate();
		refPanel.updateUI();
		//Enable textfields
		setEdit(true);
	}


	/**********************************************************************************
	 * Method adds the buttons and layouts for the "Search a Ref" page. 
	 *********************************************************************************/
	public void displayRefSearchButtons()
	{	//Make the refPanel visible
		refPanel.setVisible(true);
		// Add Buttons to the panel
		refPanel.add(searchRefButton);
		refPanel.add(refCancelButton);
	}

	/*****************************************************************************************
	 * A method to translate a String location to an integer
	 ****************************************************************************************/
	private int translateLocation(String location)
	{	//Depending on the location, return the appropriate index
		if (location.equals("North"))
			return NORTH;
		else if (location.equals("Central"))
			return CENTRAL;
		else //if (location.equals("South")
			return SOUTH;
	}

	/*****************************************************************************************
	 * A method to translate a String qualification to an integer
	 ****************************************************************************************/
	private int translateQualification(String qualification, String[] levelStrings)
	{	//initialize a variable to store the index
		int index = 0;
		//While the qualification cannot be found keep increasing the index
		while ( !qualification.equals( levelStrings[index] ) ) { index++; }
		return index;
	}			

	/*****************************************************************************************
	 * Method adds buttons and layouts for a successful search, displaying
	 * all Referee Details 
	 * @param oneRef - A Referee
	 ****************************************************************************************/
	public String displayAllRefDetails(Referee oneRef)
	{
		String headerString = "";

		//Set the Background and Foreground colors of the components
		setRefUpdateComponentColors();

		//If a Referee is not passed as a parameter
		if (oneRef != null)
		{	// Referee ID Label and Text Field
			idLabel.setVisible(true);
			idField.setVisible(true);
			idField.setEditable(false);
			//Display the ID of the Referee
			idField.setText(oneRef.getRefereeID());
			//Display the current home Location of the Referee
			locBox.setSelectedIndex(translateLocation(oneRef.getHomeArea()));
			//Display number of allocation of the Referee
			allocField.setText("" + oneRef.getMatchAllocations());
			//Set the current state of the checkBoxes
			setTravelCheckBoxes(oneRef);
			//Set the current level of a referee's qualification
			levelBox.setSelectedIndex(translateQualification(oneRef.getQualification(), LEVEL_STRINGS));
			//Set the header
			headerString = "Edit "+ oneRef.getFName() +" "+ oneRef.getLName();
			//Set the text of the button
			newRefButton.setText("Update");
		} 
		//Load the page for Adding a new Referee
		else {
			//Set the header
			headerString = "Add a New Referee";
			newRefButton.setText("Add Referee");
		}

		//Add all label and boxes to the Referee Panel
		refPanel.add(locLabel);
		refPanel.add(locBox);
		refPanel.add(fillerHome);
		refPanel.add(willingToLabel);
		refPanel.add(northCheck);
		refPanel.add(centerCheck);
		refPanel.add(southCheck);
		refPanel.add(fillerWilling);
		refPanel.add(levelLabel);
		refPanel.add(levelBox);
		refPanel.add(fillerQual);
		refPanel.add(allocLabel);
		refPanel.add(allocField);
		refPanel.add(fillerAlloc);

		//Add a MouseListener to delete button
		deleteButton.addMouseListener(new MouseListener()
		{	
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) { 
				deleteButton.setBackground(Color.BLACK);  deleteButton.setForeground(Color.WHITE);}
			public void mouseEntered(MouseEvent e) { 
				deleteButton.setBackground(JBColour.jRedLight());  deleteButton.setForeground(Color.white); }
			public void mouseClicked(MouseEvent e) {}
		});

		//Add the buttons to the Ref panel
		refPanel.add(newRefButton);
		refPanel.add(deleteButton);
		refPanel.add(refCancelButton);

		refPanel.setVisible(true);
		levelBox.setVisible(true);
		locBox.setBounds(0, 0, 100, 20);//--------------------------------------------------------------------MAGIC NUMBERS
		locBox.doLayout();
		//Return the String containing the header
		return headerString;
	}



	/**********************************************************************************
	 * Method removes all search page components from the refPanel. 
	 *********************************************************************************/
	public void removeSearchFields()
	{
		refPanel.remove(refCancelButton);
		refPanel.remove(searchRefButton);
	}
	


	/**********************************************************************************
	 * Method sets the editability of the name fields and the appearance
	 * of the delete button
	 * @param addOrEdit - boolean to signal switch between add page and edit page
	 *********************************************************************************/
	public void setEdit(boolean addOrEdit)
	{
		fNameField.setEditable(addOrEdit);
		sNameField.setEditable(addOrEdit);
		allocField.setEditable(addOrEdit);
		//Remove the button
		if (addOrEdit)
			refPanel.remove(deleteButton);
	}

	/***********************************************************************************99999999999
	 * A method that sets current TravelCheckBoxes to true 
	 * if the referee is currently available to travel there
	 * @param oneRef - a Referee
	 ***********************************************************************************/
	private void setTravelCheckBoxes(Referee oneRef)
	{	// Tick the CheckBox if the Ref is available
		if (oneRef.getAvailability(("North")))
			northCheck.setSelected(true);
		if (oneRef.getAvailability(("Central")))
			centerCheck.setSelected(true);
		if (oneRef.getAvailability(("South")))
			southCheck.setSelected(true);
		//Prevents checkBox from being highlighted
		northCheck.setFocusable(false);
		centerCheck.setFocusable(false);
		southCheck.setFocusable(false);
	}

	/***********************************************************************************
	 * Accessor methods fetch components for the controller object's ActionPerformed method.
	 ***********************************************************************************/
	//Return the FirstName Input
	public String getRefFNameInputText() 
	{ 	return fNameField.getText(); }

	//Return the LastName Input
	public String getRefSNameInputText()
	{ 	return sNameField.getText(); }
	
	//Return the number of allocations
	public String getNumberOfAllocations()
	{	return allocField.getText(); }

	public JButton getSearchButton()
	{ return searchRefButton; }

	public JButton getNewRefButton()
	{ return newRefButton; }

	public JButton getRefCancelButton()
	{ return refCancelButton; }

	public JButton getDeleteButton()
	{ return deleteButton; 	}

	public JPanel getThisPanel()
	{	clearTextFields();
		return refPanel; }

	//Return the selected HomeLocation of Ref
	public String getSelectedHomeLoc()
	{	return (String) locBox.getSelectedItem(); }

	//Return the selected Qualification of Ref
	public String getSelectedQual()
	{	return (String) levelBox.getSelectedItem();	}

	/*********************************************************************************
	 * A method to check the WillingToRef boxes and return a string
	 * @return travelAreas in YYY/NNN format
	 ********************************************************************************/
	public String getSelectedTravelAreas(){
		//Variable to store the Ref availability to travel
		String travelAreas = "";
		//Check if the North box is selected
		if (northCheck.isSelected() || locBox.getSelectedItem().equals("North"))
			travelAreas += "Y";	
		else
			travelAreas += "N";
		//Check if the Central box is selected			
		if (centerCheck.isSelected()|| locBox.getSelectedItem().equals("Central"))
			travelAreas += "Y";	
		else
			travelAreas += "N";
		//Check if the South box is selected
		if (southCheck.isSelected()|| locBox.getSelectedItem().equals("South"))
			travelAreas += "Y";	
		else	
			travelAreas += "N";

		return travelAreas;
	}
	
	/*********************************************************************************
	 * A method to clear all text Fields and set
	 * JComponents to their default state
	 ********************************************************************************/
	public void clearTextFields()
	{	// Clear the Text Fields
		sNameField.setText("");
		allocField.setText("");
		fNameField.setText("");
		// Set checkboxes to default state
		northCheck.setSelected(false);
		centerCheck.setSelected(false);
		southCheck.setSelected(false);
		// Set the drop-down menus to default index
		locBox.setSelectedIndex(0);
		levelBox.setSelectedIndex(0);
	}
	
	/*********************************************************************************
	 * A method to check if any errors where found in the user input
	 * @return boolean errors - whether or nor errors where found
	 ********************************************************************************/
	public boolean checkInput(){
		// Variables to store error information
		boolean nowarning = true;
		String warning = "";
		
		// Check all text input fields
		if ( getRefFNameInputText().trim().equals("")) 
		{	nowarning = false;
			warning += "Please enter the Referee's first name\n";
		}	
		
		if ( getRefSNameInputText().trim().equals("")) 
		{	nowarning = false;
			warning += "Please enter the Referee's surname\n";
		}
		
		if ( getNumberOfAllocations().trim().equals("") || ! getNumberOfAllocations().matches("[0-9]+"))
		{	nowarning = false;
			warning += "Please enter a positive number of match allocations\n";
		}	
		
		if (!nowarning)
		{	// Show an error message in case of error JOptionPane with custom icon
			JOptionPane.showMessageDialog(null, warning, "Input Warning", JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(1));
		}
		return nowarning;
	}
}