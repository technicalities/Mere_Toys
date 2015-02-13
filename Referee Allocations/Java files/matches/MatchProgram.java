package matches;
import guis.JBGraphicButtons;

import javax.swing.JOptionPane;

import referees.*;

public class MatchProgram {

	//Instance variables to store a match program info
	private Match [] matchList;
	private int actualNumberOfMatches = 0;
	// Instance variable to store the number of weeks
	private final int NO_WEEKS = 52;

	// Button Graphics
	private JBGraphicButtons icon = new JBGraphicButtons();

	/*****************************************************************
	 * Default constructor that sets default values
	 ****************************************************************/
	public MatchProgram() 
	{	//initiliaze the matchlist array
		matchList = new Match[ NO_WEEKS ];
		for(int i = 0; i < NO_WEEKS; i++)
			matchList[i] = null;
	}

	/****************************************************************
	 * Adds a match to the matchList array
	 * @param newMatch the Match
	 ***************************************************************/
	public boolean addMatch( Match newMatch ) 
	{	// Check whether match at a certain week already exist
		if(matchAtWeek(newMatch.getWeekNumber()) == null) {
			//Add the match at the appropriate position
			matchList[actualNumberOfMatches] = newMatch;
			//Increment the number of matches
			actualNumberOfMatches++;
		} else {
			// If match already exist in the specified week, throw an error and return false
			JOptionPane.showMessageDialog(null,"Match already exists in this week", "Error", JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(2));
			return false;
		}

		// If match was succesfully added, return true
		return true;

	}

	private Match matchAtWeek(int x)
	{	//Loop through the whole list of matches
		for(Match oneMatch : matchList)
			//Check that the slot is not empty
			if(oneMatch != null)
				if(oneMatch.getWeekNumber() == x) {
					return oneMatch;
				}
		// Return null if no match at week x is found
		return null;
	}

	/****************************************************************
	 * A method to gather together all the details from all
	 * the Matches of the Matches program
	 * @return a String with all matches
	 ***************************************************************/
	public String getReport ()
	{	//Initialise the report variable
		String report = "";
		//Loop through the whole list of matches and get the details for each match
		for(Match oneMatch : matchList)
			//Check that the slot is not empty
			if(oneMatch != null)
				//Add the report for each match to the overall match report
				report += oneMatch.getMatchReport() + String.format("%n");
		//return the completed report
		return report;
	}


	/******************************************************************************
	 * A method to update all match allocations after 
	 * deleting a referee
	 *****************************************************************************/
	public void updateRefereeAllocations(RefereesCatalogue theCatalogue )
	{	//Loop through the whole list of matches
		for (Match oneMatch : matchList)
			//Check that the slot is not empty
			if (oneMatch != null) 
			{	//initially decrease match allocations to avoid double counting
				oneMatch.decreaseMatchAllocations();
				//subsequently reset the referees along with the right allocations
				oneMatch.setReferees(theCatalogue);  
			}
	}
}
