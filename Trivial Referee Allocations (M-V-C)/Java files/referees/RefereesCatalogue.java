package referees;
import guis.JBGraphicButtons;

import java.util.Arrays;

import javax.swing.JOptionPane;

import matches.Match;
/**********************************************************************
 * Maintains a list of Referee objects
 * The list is initialised in lexicographical order of RefereeID
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of available refs
 * for a particular match
 **********************************************************************/
public class RefereesCatalogue {

	//A Referee array which will be used to store the referees
	private Referee [] refereeList;
	//A 2D Referee array which will be used to store the referees in the right order
	private Referee [][] newRefArray;
	//Instance variables to store integers
	private final int MAX_NO_REFS = 12;
	private final int THREE = 3;
	//Boolean to store the comparison decision/ If true Referees will be sorted
	//by MatchAllocations, if false it will sort them based on RefereeID 
	public static boolean compByMatchAlloc = false;
	//Variable to store the number of Available Refs
	private int actualNumberOfReferees = 0; 
	//The first level of a Referees qualification 
	private final char LEVEL_ONE = '1';
	//Position of the level number of qualification e.g. IJB1
	private final int QUALIF_POS = 3;
	
	// Button Graphics
		private JBGraphicButtons icon = new JBGraphicButtons();

	/****************************************************************** 
	 * The constructor generates a RefereesCatalogue (a Referee array)
	 ******************************************************************/
	public RefereesCatalogue() {
		refereeList = new Referee[MAX_NO_REFS];
	}

	/******************************************************************
	 * A method which generates the ID
	 * @param fName, lName
	 * @return the referee's ID
	 *****************************************************************/
	public String generateRefID( String fName, String lName)
	{	//Initialize the refereeID number to 1
		int refIDNum = 1;
		String refID = "";
		
		if (fName.length() > 0 && lName.length() > 0 )
			refID = fName.substring( 0, 1 ) + lName.substring( 0, 1 );

		//Loop through the refereeList 
		for( Referee one : refereeList )
		{	//If the ID initials already exist, increase the refIDNum
			if ( one != null && one.getRefereeID().substring( 0, 2 ).equals(refID ) )
				refIDNum += 1;		
		}

		//Add the ID number to the end of the refID String
		refID += refIDNum;

		return refID;
	}

	/******************************************************************
	 * A method to gather together all the details from all
	 * the Referees of the Referee catalog
	 * @return a String with all refDetails
	 *****************************************************************/
	public String getReport ()
	{	//Initialize the report variable
		String report = "";
		//Loop through the program and get the details for each match
		for(Referee oneRef : refereeList)
			// Check if the position in refereeList is not null
			if(oneRef != null)
				//Add the ref report line in the report
				report += oneRef.getRefereeReport() + String.format("%n");
		//Rerurn the report when fully completed
		return report;
	}

	/******************************************************************
	 * A method which finds all the available refs for a given match
	 * @param a new match
	 * @return an array of available refs
	 *****************************************************************/
	private Referee[] getAvailableRefs(Match newMatch)
	{	//Create an array to store the available Refs
		Referee [] availRefsTemp = new Referee [actualNumberOfReferees];
		//Instance variable to store the number of the available refs
		int availRefsCount = 0;
		//Find all the available refs in the List
		for( Referee oneRef : refereeList )
		{	//boolean to store if the qualification of a referee is appropriate for the match level
			// first check if the position in the Referee List is not empty
			boolean refQualAvail = (oneRef != null) && !( oneRef.getQualification().charAt( QUALIF_POS ) == LEVEL_ONE && newMatch.getMatchLevel() == Match.SENIOR ) ;
			//Check whether a Referee is suitable for the match
			if ( refQualAvail && oneRef.getAvailability(newMatch.getMatchArea()) )
			{	//Store the Ref in the available refs array
				availRefsTemp[availRefsCount] = oneRef;
				//Increase the count of available refs
				availRefsCount++;
			}
		}

		//Create a second temporary array to store the available Refs
		//with the correct array length this time
		Referee [] availRefsTempFinal = new Referee[availRefsCount];
		//Transfer the available refs from the first temporary array to the final
		System.arraycopy(availRefsTemp, 0, availRefsTempFinal, 0, availRefsCount);

		//Return the available Referees
		return availRefsTempFinal;
	}

	/**************************************************************************
	 * A method to find the referee from a given name
	 * @param fName, lName
	 * @return the referee, or null if not found
	 *************************************************************************/
	public Referee getReferee (String fName, String lName) 
	{	//Loop through the Referee Catalogue
		for (Referee thisRef : refereeList)
			if (thisRef != null && thisRef.getFName().equals(fName) && thisRef.getLName().equals(lName))
				return thisRef;
		
		return null;
	}
	
	
	
	/**************************************************************************
	 * A method to find the referee from a given index number
	 * @param refIndex - the referee's index
	 * @return the referee, or null if not found
	 *************************************************************************/
	public Referee getRefereeAtIndex (int refIndex) 
	{
		if(refereeList[refIndex] != null)
			return refereeList[refIndex];

		return null;
	}
	
	/**************************************************************************
	 * A method to get the actual number of Referees in the Catalogue
	 * @return int - the actual number of Referees
	 *************************************************************************/
	public int getActualRefsNumber()
	{
		return actualNumberOfReferees;
	}

	/**************************************************************************
	 * A method to update the details of a specific referee
	 * @param oneRef - a specific referee, qualification, homeArea, 
	 * travelAreas, matchAllocations
	 *************************************************************************/
	public void updateRefDetails(Referee oneRef, String qualification, String homeArea, String travelAreas)
	{	//Updating the referee details
		oneRef.setQualification(qualification); 
		oneRef.setHomeArea(homeArea);
		oneRef.setTravelAreas(travelAreas);
	}

	/**************************************************************************
	 * A method to add a new Referee to the catalogue
	 * @param newRef
	 * @return boolean - if the addition was successful or not
	 *************************************************************************/
	public boolean addReferee(Referee newRef) 
	{	//Check that there is enough space
		if ( actualNumberOfReferees < MAX_NO_REFS ) 
		{	//Add the new referee at the end of the array
			refereeList[actualNumberOfReferees] = newRef;
			//Decrease the count of the actual number of referees
			actualNumberOfReferees += 1;
			//Sort the array after the changes
			Arrays.sort(refereeList,0,actualNumberOfReferees);
			
			return true;
		}

		return false;
	}

	/****************************************************************************
	 * A method to delete a referee from the referee catalogue
	 * @param oneRef - the ref to be deleted
	 ***************************************************************************/
	public void deleteReferee( Referee oneRef )
	{	//variable to store the position in the ref array
		int positionOfTheReferee = 0;
		//while the refereeID cannot be matched keep searching
		for( int i = 0; i < MAX_NO_REFS; i++) 
			if ( refereeList[i] != null && oneRef.getRefereeID().equals(refereeList[i].getRefereeID()) )
			{	//if a referee is found delete the class
				refereeList[i] = null;
				positionOfTheReferee = i;
				//Exit the loop
				i = MAX_NO_REFS;
			}
		// If its not the last referee that was deleted, proceed with the following
		if( positionOfTheReferee != actualNumberOfReferees-1 ) 
		{	//Store the last referee of the array in the position of the deleted ref
			refereeList[ positionOfTheReferee ] = refereeList[ actualNumberOfReferees - 1 ];
			//Empty the last slot of the array
			refereeList[ actualNumberOfReferees - 1 ] = null;
		}

		//Decrease the count of the actual number of referees
		actualNumberOfReferees --;
		//Sort the array after the changes (avoiding the empty slots)
		Arrays.sort(refereeList,0,actualNumberOfReferees);
		
		//Show confirmation pane
		JOptionPane.showMessageDialog(null, "Referee "+ oneRef.getFName() + " "+ oneRef.getLName()+" has been successfully deleted.", "Referee Deleted", JOptionPane.INFORMATION_MESSAGE, icon.addJPaneIcon(3));
	}

	/********************************************************************************
	 * A method to find the available referees for a specific match.
	 * @param newMatch
	 * @return a Referee array with all available refs
	 ********************************************************************************/
	public Referee[] getRefsForMatch(Match newMatch)
	{	
		Referee [] availableRefs = getAvailableRefs(newMatch);
		//An array to store the available refs ordered
		Referee [] orderedAvailRefs = new Referee[ availableRefs.length ];
		//Initialize a 2D array to store the available refs in the appropriate order
		newRefArray = new Referee[ THREE ][ availableRefs.length ];
		//Variables used to store the selection order of refs based on matchAreas
		String first = "";
		String second = "";
		String third = "";
		//This extra optional variable is used in case the match area is in the
		//Center in which case South and North are just as far away
		String second_optional = "";
		//Variable to store the match area
		String matchArea = newMatch.getMatchArea();

		//Check in which area the match is played and set the right sequence
		//for sorting the referees
		if ( matchArea.equals( "Central" ) ) {	
			first = "Central";
			second = "South";
			second_optional = "North";
		} 
		else if ( matchArea.equals( "South" ) ) {
			first = "South";
			second = "Central";
			third = "North";
		} 
		else { //if ( matchArea.equals( "North" ) )
			first = "North";
			second = "Central";
			third = "South";
		}

		//Populate the 2D array with the available refs in the right order
		populateMatchArray( 0, newMatch, availableRefs, first );
		populateMatchArray( 1, newMatch, availableRefs, second, second_optional );
		populateMatchArray( 2, newMatch, availableRefs, third );
		
		//Variable to store the position number
		int posNo = 0;
		//Loop through all the 3 slots of the 2D array
		for( int i = 0; i < THREE; i++ )
			for( Referee oneRef : newRefArray[i] ) 
				//Check if the slot is empty
				if(oneRef != null)
				{	// Store the available referees
					orderedAvailRefs[posNo] = oneRef;
					posNo++;
				}

		//Return the array with available referees (sorted appropriately)
		return orderedAvailRefs;
	}

	/********************************************************************************
	 * A method that populates the 2D array of available referees
	 * in the appropriate sequence based on refs home areas
	 * @param arrayPos - the 2D's array first position parameter
	 * @param newMatch
	 * @param searchArea - the area to compare with refs homeAreas
	 * @param searchArea2 - optional parameter in case the home area is Center
	 *******************************************************************************/
	private void populateMatchArray( int arrayPos, Match newMatch, Referee[] availableRefs, String searchArea, String...searchArea2 )
	{	//Variable to store the number of available refs, and arrayPos
		int refCount = 0;

		//Loop through all the available Refs list
		for( Referee thisRef : availableRefs )
			//if the ref is suitable, proceed with the following
			if( thisRef.getHomeArea().equals( searchArea ) || ( searchArea2.length > 0 && thisRef.getHomeArea().equals( searchArea2[0] ) ) )
			{	//Add the ref to the appropriate location and increment the count
				newRefArray[ arrayPos ][ refCount ] = thisRef;
				refCount++;
			} 

		//Change the boolean compByMatchAllocotion to true so
		//sorting Referees will be based on match allocations
		compByMatchAlloc = true;
		//Sort the Referees based on Match allocations
		Arrays.sort( newRefArray[ arrayPos ], 0, refCount );
		//Change the boolean compByMatchAllocation to the default value
		compByMatchAlloc = false;
	}
}