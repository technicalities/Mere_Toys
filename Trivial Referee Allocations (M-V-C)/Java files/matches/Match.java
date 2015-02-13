package matches;
import referees.*;

public class Match {
	
	//Instance variables to store information about a match
	private int weekNumber;
	private String matchArea;
	private int matchLevel;
	private Referee refereeOne;
	private Referee refereeTwo;
	//Instance variables to store match levels as integers
	public static final int SENIOR = 0;
	public static final int JUNIOR = 1;
	//Instance variables to store match areas as strings
	public static final String NORTH = "North";
	public static final String CENTRAL = "Central";
	public static final String SOUTH = "South";
	
	/**********************************************************************
	 * Default constructor that sets default values
	 **********************************************************************/
	public Match() 
	{
		weekNumber = 0;
		matchArea = "";
		matchLevel = SENIOR;
		refereeOne = null;
		refereeTwo = null;
	}
	
	/***************************************************************************************************
	 * Constructor that constructs Match from following instance variables
	 * @param weekNumber
	 * @param matchArea
	 * @param matchLevel
	 * @param newCatalogue
	 ***************************************************************************************************/
	public Match(int weekNumber, String matchArea, int matchLevel, RefereesCatalogue newCatalogue) 
	{
		this.weekNumber = weekNumber;
		this.matchArea = matchArea;
		this.matchLevel = matchLevel;
	}
	
	/********************************************************
	 * Accessor methods
	 ********************************************************/
	public int getWeekNumber() {
		return weekNumber;
	}
	
	public String getMatchArea() {
		return matchArea;
	}
	
	public int getMatchLevel() {
		return matchLevel;
	}
	
	public Referee getRefereeOne() {
		return refereeOne;
	}
	
	public Referee getRefereeTwo() {
		return refereeTwo;
	}
	
	public String getMatchLevelReport() 
	{
		if( matchLevel == SENIOR )
			return "Senior";
		else 
			return "Junior";
	}
	
	/*********************************************************
	 * Mutator methods
	 ********************************************************/
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	
	public void setMatchArea(String matchArea) {
		this.matchArea = matchArea;
	}
	
	public void setMatchLevel(int matchLevel) {
		this.matchLevel = matchLevel;
	}
	
	public void setRefereeOne(Referee refereeOne) {
		this.refereeOne = refereeOne;
	}
	
	public void setRefereeTwo(Referee refereeTwo) {
		this.refereeTwo = refereeTwo;
	}
	
	/*********************************************************
	 * Instantiates the refereeOne and refereeTwo variables
	 * given a referee catalogue
	 * @param a newCatalogue
	 ********************************************************/
	public Referee [] setReferees(RefereesCatalogue newCatalogue)
	{
		// Get available referees according to match location and level
		Referee [] availableRefs = newCatalogue.getRefsForMatch(this);
		
		// Try to set referees, if there is less than 2 available
		// we catch the IndexOutOfBoundsException
		try {
		
			// Set referees to instance variables
			this.refereeOne = availableRefs[0];
			this.refereeTwo = availableRefs[1];
			// Increase match allocations for both referees
			availableRefs[0].increaseMatchAllocations();
			availableRefs[1].increaseMatchAllocations();
		
		} catch(IndexOutOfBoundsException e) 
		{
			// Set referees to empty values
			this.refereeOne = new Referee();
			this.refereeTwo = new Referee();
			// Not enough available referees to assign to a match
			return null;
		}
		
		// If everything went correctly, return availableReferees
		return availableRefs;
	}
	
	/**************************************************************
	 * A method to decrease match allocations of the match
	 * referees by calling the synonym method in the Referee class
	 *************************************************************/
	public void decreaseMatchAllocations()
	{
		refereeOne.decreaseMatchAllocations();
		refereeTwo.decreaseMatchAllocations();
	}
	
	/****************************************************
	 * Creates a printable String from instance variables
	 * @return a String with info about the match
	 ****************************************************/
	public String getMatchReport() 
	{
		String printFormat = "%d %s %s %s %s %s %s";
		return String.format(printFormat,weekNumber,getMatchLevelReport(),getMatchArea(),refereeOne.getFName(),refereeOne.getLName(),refereeTwo.getFName(),refereeTwo.getLName());	
	}	
}