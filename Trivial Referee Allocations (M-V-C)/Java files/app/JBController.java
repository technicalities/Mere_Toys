package app;

import matches.*;
import referees.*;
import guis.JBGraphicButtons;
import guis.MainGUI;
import guis.MatchGUI;
import guis.RefGUI;

import javax.swing.*;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


/*************************************************************************************************
 * A Controller class. Handles all Events from the GUI, co-ordinates 
 * method calls, and passes data between the models and the view.
 *************************************************************************************************/
public class JBController implements ActionListener {

	// Names of input text files
	private final String refereesInFile = "RefereesIn.txt";
	private final String refereesOutFile = "RefereesOut.txt";
	private final String matchesOutFile = "MatchAllocs.txt";

	// Filereader and Scanner objects initialized 
	private FileReader fr = null;
	private Scanner in = null;
	
	// Button Graphics
	private JBGraphicButtons icon = new JBGraphicButtons();

	// Referees Catalogue 
	private RefereesCatalogue aRefCatalogue = new RefereesCatalogue();
	private final int MAX_REFS = 12;
	private final int THREE = 3;

	// Matches Program
	private MatchProgram aMatchProg = new MatchProgram();

	// Instances of the Objects to handle.
	private MainGUI aMainGUI;
	private RefGUI aRefGUI;
	private MatchGUI aMatchGUI;

	/**************************************************************
	 * Constructors.
	 ***************************************************************/
	public JBController() throws IOException
	{
		MainGUI display = new MainGUI(this,aRefCatalogue);
		display.setVisible(true);
		this.setView(display);
	}

	/**************************************************************
	 * Helper method for reading the contents of a file
	 * @param String file location
	 * @return boolean if the the process was successful
	 *************************************************************/
	public boolean readFile(String file)
	{
		try 
		{
			fr = new FileReader(file);
			in = new Scanner(fr);
		} 
		catch (IOException e) 
		{
			// Custom file name Error JOptionPane with custom icon
			JOptionPane.showMessageDialog(null,"The file name was not found", "Error", JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(2));
//			JOptionPane.showMessageDialog(null,"The filename was not found.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/**************************************************************
	 * Helper method for closing the file
	 * @param String file location
	 *************************************************************/
	public void closeFile()
	{
		try 
		{
			if (fr != null) { fr.close(); }
			if (in != null) { in.close(); }
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null,"The file name was not found", "Error", JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(2));
			//JOptionPane.showMessageDialog(null,"The filename was not found.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**************************************************************
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 *************************************************************/
	public void initReferees() 
	{	//Read all the lines of the file
		if(readFile(refereesInFile))
		{	
			while ( in.hasNextLine() )
			{	//Store the information of its line in a string
				String refereeInfo = in.nextLine();
				//Create a new Class from the String
				Referee newReferee = new Referee(refereeInfo);
				//Add the class to the the Program
				aRefCatalogue.addReferee(newReferee);
			}
			//Close the file
			closeFile();
			//Populate the GUI Table with the Refs
			aMainGUI.populateTable();
		}
	}


	/***************************************************************************************
	 *  Method to write given parameters to a txt file.
	 ***************************************************************************************/  
	public void writeFile(String filename, String text) {
		//Create a Print Writer
		PrintWriter pw = null;

		try
		{
			try
			{	//create output file
				pw = new PrintWriter( filename );
				//Print the report in the file
				pw.print( text );
			} 
			finally 
			{	// close the second output file assuming it was opened successfully
				if (pw != null) pw.close();
			}
		}catch(IOException e){
			//An exception that cannot be triggered by the user
			JOptionPane.showMessageDialog(null,"The file name was not found", "Error", JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(2));
//			JOptionPane.showMessageDialog(null,"The filename was not found.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/***************************************************************************************
	 *  Methods to pass the GUI objects into the controller for appropriate Event handling.
	 ***************************************************************************************/  
	public void setView(MainGUI display) 
	{
		aMainGUI = display;
	}

	public void setControlledRef(RefGUI aRefGUIObject)
	{
		aRefGUI = aRefGUIObject;
	}

	public void setControlledMatch(MatchGUI aMatchGUIObject)
	{
		aMatchGUI = aMatchGUIObject;
	}

	/*********************************************************************
	 *  To process button clicks from many different components of 
	 *  several different objects.
	 *  @param ae the ActionEvent			 
	 **************************************************************************************/
	public void actionPerformed(ActionEvent ae) 
	{	
		//If BAR or BACK button is pressed (in the main page)
		if (ae.getSource() == aMainGUI.barButton)						
		{ 	//Switches between the ref list and bar chart view.
			if (aMainGUI.getIfBarShowing())
				aMainGUI.loadMainTable();
			else{
				aMainGUI.loadBarChart();
			}
			aMainGUI.middlePanel.updateUI();
		}

		//-----------HOME-LOGO button-----------
		if (ae.getSource() == aMainGUI.logoButton)												
		{ 	//Remove all from the panels
			aMainGUI.clearMainView();
			//Reset to main table View
			aMainGUI.buildMainView();
			// Rebuild the bar chart
			//aMainGUI.rectangleBuilder();
			// Repopulate the table with new data
			aMainGUI.populateTable();
			// Update UI
			aMainGUI.middlePanel.updateUI();
		}

		//-----------CREATE NEW MATCH-----------
		if (ae.getSource() == aMainGUI.menuMatchCreate)									
		{ 
			//Remove all from the panels
			aMainGUI.clearMainView();

			//aMainGUI.setHeader("Add a New Match");
			aMainGUI.switchToAddMatch();

		}

		//-----------MENUBAR SEARCH or UPDATE/DELETE----------------
		if (ae.getSource() == aMainGUI.menuRefSearch || ae.getSource() == aMainGUI.menuRefUpdate)									
		{ 
			//Remove all from the panels
			aMainGUI.clearMainView();

			//aMainGUI.setHeader("Search for a Referee");
			aMainGUI.add(aRefGUI.getThisPanel(), BorderLayout.CENTER);
			aRefGUI.displayRefNameID();
			aMainGUI.toSearchRef();
		}

		//-----------ADD NEW REFEREE (menu bar option)-----------
		if (ae.getSource() == aMainGUI.menuRefAdd)									
		{ 	
			//Remove all from the panels
			aMainGUI.clearMainView();

			aMainGUI.add(aRefGUI.getThisPanel(), BorderLayout.CENTER);
			aRefGUI.displayRefNameID();
			aMainGUI.toFullRefDetails(null);
			aMainGUI.getRefGUI().setEdit(true);
		}

		//----------CREATE MATCH button------------------
		if (ae.getSource() == aMatchGUI.getNewMatchButton())									
		{ 	// Check whether the week number is in correct format
			if(aMatchGUI.checkInput()) 
			{	// Initialize variables
				int weekNumber = aMatchGUI.getWeekNumber();
				String matchLocation = aMatchGUI.getMatchLocation();
				int matchLevel = aMatchGUI.getMatchLevel();

				// Create the match
				Match newMatch = new Match(weekNumber, matchLocation,matchLevel,aRefCatalogue);
				
				// Assign referees to the match and return them so we can use them in populateCellsWithAvailRefs
				Referee [] availableRefs = newMatch.setReferees(aRefCatalogue);
				
				// If it successfully instantiates refereeOne and refereeTwo to the match, continue
				if(availableRefs != null) 
				{
					// Add the match to a Match Program
					// If the match was successfully added to a Match Program, continue with updating the GUI
					if(aMatchProg.addMatch(newMatch)) 
						// Update the ID table with available refs
						aMainGUI.populateCellsWithAvailRefs(newMatch, availableRefs);
				}
				// If the assigning procedure failed, throw an error
				else
					JOptionPane.showMessageDialog(null, "Not enough suitable referees for the match", "Not enough referees", JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(2));
						
			// If the week number is not in the correct format, set week number field to default
			}
			else
				aMatchGUI.getWeekField().setText("");
		}

		//----------CANCEL button-------------return to main table view.
		if (ae.getSource() == aRefGUI.getRefCancelButton() || ae.getSource() == aMatchGUI.getMatchCancelButton())									
			// Call the ActionPerformed already defined for the Logo button.
			aMainGUI.logoButton.doClick();									

		//----------NEW REFEREE or UPDATE button------------------
		if (ae.getSource() == aMainGUI.getRefGUI().getNewRefButton())
		{	//Try to find the ref in the catalogue								
			if (aRefCatalogue.getReferee(aRefGUI.getRefFNameInputText(), aRefGUI.getRefSNameInputText()) != null)
			{	//If found update his details
				updateRefDetails();
				//Go back to the main Screen
				aMainGUI.logoButton.doClick();
			}
			else
			{	if ( aRefGUI.checkInput() == true)
				{	//If not found add the new Referee in the catalogue
					addNewRefToCatalogue();
					//Go back to the main Screen
					aMainGUI.logoButton.doClick();
				}
				else
					aRefGUI.clearTextFields();
			}
		}

		//----------SEARCH button------------------
		if (ae.getSource() == aMainGUI.getRefGUI().getSearchButton() )									
		{	//Search for the Referee in the Catalogue
			Referee oneRef = getTheReferee();
			//If search is successful populate GUI with their data.
			if (oneRef != null)
				aMainGUI.toFullRefDetails(oneRef);
			else
			{	//Throw an error and clear the text fields
				JOptionPane.showMessageDialog(null, "Your search was unsuccesfull. Please try again.", "Referee not found", JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(2));
				aRefGUI.clearTextFields();
			}
		}

		//----------DELETE button------------------
		if (ae.getSource() == aMainGUI.getRefGUI().getDeleteButton())									
			deleteRefFromCatalogue();

		//----------EXIT/QUIT button------------------
		if (ae.getSource() == aMainGUI.quitButton)						
			saveAndExit();

	}

	/*************************************************************************************
	 * Helper method to create an ID for a new Referee
	 * @return String - the newly created ID
	 ************************************************************************************/
	private String createRefID()
	{
		return aRefCatalogue.generateRefID(aRefGUI.getRefFNameInputText(), aRefGUI.getRefSNameInputText());
	}

	/*************************************************************************************
	 * Add a new Referee to the Catalogue
	 ************************************************************************************/
	private void addNewRefToCatalogue()
	{	//Variables to store the new Refs name
		String newRefName = aRefGUI.getRefFNameInputText() +" "+aRefGUI.getRefSNameInputText();
		//Initialize the String with the new RefID
		String newRefDetails = createRefID() + " ";
		//Add the newRefs first and surname
		newRefDetails += newRefName + " ";
		//Add the newRefs qualification followed by 0 match allocations
		newRefDetails += aRefGUI.getSelectedQual()+ " ";
		//Add the newRefs allocations
		newRefDetails += aRefGUI.getNumberOfAllocations() + " ";
		//Add the newRefs HomeLocation
		newRefDetails += aRefGUI.getSelectedHomeLoc() + " ";
		//Add the newRefs travel availabilities
		newRefDetails += aRefGUI.getSelectedTravelAreas();

		//Create and Add the new Referee
		Referee newReferee = new Referee(newRefDetails);
		aRefCatalogue.addReferee(newReferee);
		
		// Update referee allocations when a match has no allocated refs
		if(aRefCatalogue.getActualRefsNumber() < THREE)
			aMatchProg.updateRefereeAllocations(aRefCatalogue);
		
		//Check to see if the ref list is already full.
		if (aRefCatalogue.getActualRefsNumber() > MAX_REFS)
			JOptionPane.showMessageDialog(null,"Ref list is full. This ref not added.", "List full", JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(2));			
		else	//If not show confirmation pane
			JOptionPane.showMessageDialog(null, "You have succesfully added " + newRefName + ".", " Ref.", JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(3));
	
		//Return to home page
		aMainGUI.logoButton.doClick();
	}
	/*************************************************************************************
	 * Finds and return the Referee from the Ref GUI textfields
	 * @return theRef - a Referee object
	 ************************************************************************************/
	private Referee getTheReferee()
	{	//Find and return the Referee from the Ref GUI textfields
		Referee theRef = aRefCatalogue.getReferee(aRefGUI.getRefFNameInputText(), aRefGUI.getRefSNameInputText());

		return theRef;
	}
	/*************************************************************************************
	 * Deletes a Referee from the Catalogue
	 ************************************************************************************/
	private void deleteRefFromCatalogue()
	{	
			//Delete the Referee
			aRefCatalogue.deleteReferee(getTheReferee());
			//Update the Match Referees Allocations
			aMatchProg.updateRefereeAllocations(aRefCatalogue);
			//Go back to the main Screen
			aMainGUI.logoButton.doClick();
	}
	/*************************************************************************************
	 * Update a Referees Details in the Catalogue
	 ************************************************************************************/
	private void updateRefDetails(){
		aRefCatalogue.updateRefDetails(getTheReferee(), aRefGUI.getSelectedQual(), aRefGUI.getSelectedHomeLoc(), aRefGUI.getSelectedTravelAreas());
		// Reassign the referees in the Matches since changing the qualification
		// could possibly make the referee no longer suitable for some match
		aMatchProg.updateRefereeAllocations(aRefCatalogue);
	}

	/*************************************************************************************
	 * Method presents a popup option, calls methods to save MatchProgram and RefCatalogue state
	 ************************************************************************************/
	private void saveAndExit()
	{	// Show confirmation panel with two options
		Object [] showOptions = {"Save and close", "Return to JavaBall"};
		int quitChoice = JOptionPane.showOptionDialog(null, "Save ref list and match allocations and close program.", "Quit JavaBall?", 
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(4), showOptions, showOptions[1]);
		// if the user chooses to quit
		if (quitChoice == 0)
		{	// Save referees report into a file
			writeFile(refereesOutFile,aRefCatalogue.getReport());
			// Save matches report into a file
			writeFile(matchesOutFile,aMatchProg.getReport());
			//Exit the system
			System.exit(1);
		}
	}
}