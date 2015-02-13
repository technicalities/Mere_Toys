package referees;


/**************************************************************************** 
 * Defines an object representing a single Referee
 ***************************************************************************/
public class Referee implements Comparable<Referee> {

	//Instance variables to store information about a Referee
	private String refereeID;
	private String fName;
	private String lName;
	private String qualification;
	private String travelAreas;
	private String homeArea;
	private int matchAllocations;
	//Instance variable to store the char Y
	private final char YES = 'Y';

	/************************************************************************ 
	 * The default constructor generates a Referee
	 ***********************************************************************/
	public Referee()
	{
		this.refereeID = "";
		this.fName = "";
		this.lName = "";
		this.qualification = "";
		this.travelAreas = "";
		this.homeArea = "";
		this.matchAllocations = 0;
	}

	/************************************************************************
	 * Third constructor, generates a Referee
	 * @param refereeDetails
	 ***********************************************************************/
	public Referee(String refereeDetails)
	{	//split up ref details into the refData array using blank space(s) delimiters
		String [] refData = refereeDetails.split(" +");
		//Access the particular referee details from the refData array
		this.refereeID = refData[0];
		this.fName = refData[1];
		this.lName = refData[2];
		this.qualification = refData[3];
		this.matchAllocations = Integer.parseInt(refData[4]);
		this.homeArea = refData[5];
		this.travelAreas = refData[6];		
	}

	/************************************************************************
	 * Increment a referee's match allocations count
	 ***********************************************************************/
	public void increaseMatchAllocations() {
		this.matchAllocations ++;
	}
	
	/************************************************************************
	 * Decrement a referee's match allocations count
	 ***********************************************************************/
	public void decreaseMatchAllocations() {
		this.matchAllocations --;
	}

	/************************************************************************
	 * A method which formats the referee info appropriately for
	 * the attendance report.
	 * @return String - a line containing the Referee Details
	 ***********************************************************************/
	public String getRefereeReport () 
	{	//the appropriate format for the report line
		String printFormat = "%5s%15s%15s%10s%5d%10s%5s";
		//Initializing the report with the appropriate values
		String report = String.format(printFormat, refereeID, fName, lName, qualification, matchAllocations, homeArea, travelAreas);
		//Return the report line
		return report;
	}

	/************************************************************************
	 * A method to check if a referee is available to play in an area
	 * @param matchArea
	 * @return boolean
	 ***********************************************************************/
	public boolean getAvailability(String matchArea) 
	{	//check if the referee is able to travel at the particular matchArea
		if (matchArea.equals("North") && travelAreas.charAt(0) == YES)
			return true;
		else if (matchArea.equals("Central") && travelAreas.charAt(1) == YES)
			return true;
		else if (matchArea.equals("South") && travelAreas.charAt(2) == YES)
			return true;
		//return false if the ref is not available
		return false;
	}

	/************************************************************************ 
	 * A method to compare two referee objects on matchAllocations or
	 * based on the referee IDs (lexicographically) 
	 * @param the Referee class to be compared
	 * @return  a negative integer, zero, or a positive integer as this 
	 * object is greater than, equal to, or less than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException if the specified object's type prevents it
	 * from being compared to this object.
	 **********************************************************************/
	public int compareTo(Referee other) 
	{
		//Compare according to match allocations if the compByMatchAlloc is true
		if (RefereesCatalogue.compByMatchAlloc) 
		{
			if ( this.getMatchAllocations() < other.getMatchAllocations() )
				return -1;
			else if ( this.getMatchAllocations() == other.getMatchAllocations() )
				return 0;
			else //if ( this.getMatchAllocations() > other.getMatchAllocations() )
				return 1;
		}	
		else //if false compare based on RefereeID
		{
			// Get the IDs of the Referees to be compared
			String idA = this.getRefereeID();
			String idB = other.getRefereeID();
			//Return the result of the comparison
			return idA.compareTo(idB);
		}
	}

	/*********************************************************** 
	 * Mutator methods for qualification, homeArea,
	 * travelAreas and matchAllocations
	 **********************************************************/
	public void setQualification( String qualification ) {
		this.qualification = qualification;
	};

	public void setHomeArea( String homeArea ) {
		this.homeArea = homeArea;
	};

	public void setTravelAreas( String travelAreas ) {
		this.travelAreas = travelAreas;
	};

	public void setMatchAllocations( int matchAllocations ) {
		this.matchAllocations = matchAllocations;
	};


	/************************************************************ 
	 * Accessor methods for all instance variables
	 ***********************************************************/
	public String getRefereeID() {
		return refereeID;
	};

	public String getFName() {
		return fName;
	};

	public String getLName() {
		return lName;
	};

	public String getQualification() {
		return qualification;
	};

	public String getHomeArea()	{
		return homeArea;
	};

	public String getTravelAreas() {
		return travelAreas;
	};

	public int getMatchAllocations() {
		return matchAllocations;
	}
}