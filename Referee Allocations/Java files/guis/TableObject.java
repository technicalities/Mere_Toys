package guis;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import matches.Match;
import referees.Referee;
import referees.RefereesCatalogue;

/*********************************************************************************
 *  Class for creating tables of various width and contents.
 ********************************************************************************/
public class TableObject extends JTable
{
    private JTable thisTable;
	private int numRows;
	private int numCols;
	private final int MAIN_NUM_COLS = 5;
	private final int DOUBLE_COL = 2;
	private final int MAX_REFS = 12;
	private final int BORDER_SIZE = 2;
	private final int ROW_HEIGHT = 18;
	private final int HEADER_ROW_HEIGHT = 35;
	private final int HEADER_ROW = 0;
	
	private final int MAIN_COL_WIDTH = 60;
	private final int NAME_COL_WIDTH = 120;

	// Arrays to hold column headers
	private String[] mainRefArray = new String[MAIN_NUM_COLS];

	/***************************************************************************************
	 *  Table Object Constructor
	 *  @param numRows - the number of Rows
	 *  @param numCols - the number of Columns
	 **************************************************************************************/
	public TableObject(int numRows, int numCols)
	{	//Set the number of Rows and Columns
		this.numRows = numRows;
		this.numCols = numCols;
		//Instantiate the Table object
		thisTable = new JTable(this.numRows, this.numCols);
		//Set the height for each row
		thisTable.setRowHeight( ROW_HEIGHT ); 
		//Make the header-row height bigger than the rest
		thisTable.setRowHeight( HEADER_ROW, HEADER_ROW_HEIGHT ); 
		//Set a black border for the table
		thisTable.setBorder(new LineBorder(Color.BLACK, BORDER_SIZE));

		// If the number of columns is bigger than 2, we're working with Referees table
		if(this.numCols > DOUBLE_COL) 
		{	// Turn off autoresizing for table columns
			thisTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			// Set width for name column
			thisTable.getColumnModel().getColumn(0).setPreferredWidth(NAME_COL_WIDTH); 
			// Set width for the rest of the columns
			for(int i = 1; i < MAIN_NUM_COLS; i++)
				thisTable.getColumnModel().getColumn(i).setPreferredWidth(MAIN_COL_WIDTH);
			
			//The format of the headers of the table
			String formatStyle[] = { "%10s", "%12s","%10s","%12s","%11s" };
			//The content of the headers of the table
			String headerTitle[] = {"Name", "Qualif", "Home", "Willing", "# Matches"};
			//Fill up an array with the formatted strings
			for( int i = 0; i < MAIN_NUM_COLS; i++ )
				mainRefArray[i] = String.format(formatStyle[i], headerTitle[i]);
		}
		// If the number of columns is two, we're working with 
		// a Table that displays available referees
		else if(this.numCols == DOUBLE_COL) 
		{
			// Turn off autoresizing for table columns
			thisTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			// Set width for name column
			thisTable.getColumnModel().getColumn(0).setPreferredWidth(NAME_COL_WIDTH);
			// The content of the headers of the table
			mainRefArray[0] = "  Name";
			mainRefArray[1] = "  # Matches";
		}
		// If the number of columns is 1, we're working with ID table
		else 
			// THe contenet of the header of the table
			mainRefArray[0] = "  ID";
		
		// For each column
		for ( int colIndex = 0; colIndex < numCols; colIndex++ )
			// Set the Header cells
			thisTable.setValueAt( mainRefArray[colIndex], 0, colIndex );	
		
		thisTable.setEnabled(false);
		thisTable.setVisible(true);
	}

	/*************************************************************
	 * Accessor method 
	 * @return thisTable
	 ************************************************************/
	public JTable getThisTable()											
	{	
		return thisTable;
	}
	
	/************************************************************
	 * A method that empties all the rows in the table
	 ***********************************************************/
	public void emptyTable() 
	{	for ( int column = 0; column < numCols; column++)
			// Go over all rows
			for ( int row = 1; row < MAX_REFS; row++ )
				thisTable.setValueAt( "", row, column );		
	}

	/*******************************************************************************************
	 * A method to populate the ID tables with available Refs for a match
	 ******************************************************************************************/
	public void populateCellsWithAvailRefs(RefereesCatalogue refCat, Match newMatch, Referee [] availableRefs)
	{	// Print each ref ID to the table
		for ( int i = 0, row = 1; i < availableRefs.length; i++, row++)
			// Check whether the slot in availableRefs is empty
			if(availableRefs[i] != null)
			{	// Populate the name column with referee names
				String fullName = availableRefs[i].getFName() + " " + availableRefs[i].getLName(); 
				thisTable.setValueAt("  " + fullName, row, 0);
				// First two rows will have arrows to indicate the allocated referees
				if(row < 3) 
					thisTable.setValueAt(" " + availableRefs[i].getMatchAllocations() + "  <<", row, 1);
				// Other columns will not have the arrows
				else  
					thisTable.setValueAt(" " + availableRefs[i].getMatchAllocations(), row, 1);
			}
	}

	/********************************************************************************************
	 * Method to populate the Ref table with Referees
	 *******************************************************************************************/
	public void populateCells(RefereesCatalogue refCat)
	{	// For each Row
		for ( int i = 0, row = 1; i < MAX_REFS; i++, row++)
		{	//Check whether a Referee exists at this index(row)
			if (refCat.getRefereeAtIndex(i) != null)
			{	// If the number of columns is bigger than 1, we're working with Referees table
				if(this.numCols > 1) 
				{	// Populate the columns with appropriate referee info
					String fullName = refCat.getRefereeAtIndex(i).getFName() + " " + refCat.getRefereeAtIndex(i).getLName(); 
					thisTable.setValueAt("  " + fullName, row, 0);
					thisTable.setValueAt(" " + refCat.getRefereeAtIndex(i).getQualification(), row, 1);
					thisTable.setValueAt(" " + refCat.getRefereeAtIndex(i).getHomeArea(), row, 2);
					thisTable.setValueAt(" " + refCat.getRefereeAtIndex(i).getTravelAreas(), row, 3);
					thisTable.setValueAt(" " + refCat.getRefereeAtIndex(i).getMatchAllocations(), row, 4);
				} else 
				 	// If #columns is 1,populate the table with Referee IDs
					thisTable.setValueAt("  " + refCat.getRefereeAtIndex(i).getRefereeID(), row, 0);
			} // if
		} // for
	} // end of the method
	
}