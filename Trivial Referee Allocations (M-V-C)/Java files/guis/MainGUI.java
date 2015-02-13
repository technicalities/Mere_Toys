package guis;

import app.JBController;
import guis.RectangleBar;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import matches.Match;
import referees.Referee;
import referees.RefereesCatalogue;
import java.io.*;

/*************************************************************************************
 *  Class is a View for the JavaBall program.
 * 	Can switch between three pages showing the Homepage list, a Match creator 
 * 	and a ref search/edit function..
 * 	NB: JMenuBar is not properly rendered on Mac versions of Eclipse.
 **************************************************************************************/
public class MainGUI extends JFrame 
{	
	/** GUI components. 
	 * The Public instance variables are used by the controller's ActionPerformed method. */
	public JPanel middlePanel;
	public JButton barButton, logoButton, quitButton, headerImage;
	
	// Dimension
	private final Dimension NORTHDimension = new Dimension(180,90);

	// Button Graphics
	private JBGraphicButtons icons = new JBGraphicButtons();

	// MenuBar variables.
	private	JMenuBar menuBar;
	private	JMenu menuMatch, menuRef;
	public JMenuItem menuMatchCreate, menuRefSearch, menuRefAdd, menuRefUpdate;

	//private JTable IDList
	private TableObject IDList, mainRefList, availableRefList;
	public JLabel bigLabel, logoLabel;
	public JTextField fnField, snField, idField;
	private JPanel northPanel, navPanel, westPanel, eastPanel, helperPanel, southPanel, barPanel;

	//  The objects that display search and edit functions for refs and matches.
	private RefGUI aRefGUI;
	private MatchGUI aMatchGUI;

	// Constants for Rows and Columns
	private final int FIVE_COLUMNS = 5;
	private final int SINGLE_COL = 1;
	private final int TWO_COLUMNS = 2;
	private final int ROWS = 13;
	// Constants for GUI size
	private final int GUI_WIDTH = 800;
	private final int GUI_HEIGHT = 600;
	// Constants for fonts
	private final Font BIG_FONT = new Font("Courier", Font.BOLD, 30);
	private final Font MAIN_FONT = new Font("Courier", Font.PLAIN, 12);
	// Status boolean showing which GUI is showing
	private boolean isBarShowing = false;									


	// Model - A catalogue with Referees
	private RefereesCatalogue newRefCat;

	/**************************************************************************************  
	 * Constructor for the frame in which all pages are displayed.		 
	 * @throws IOException 
	 *************************************************************************************/
	public MainGUI(JBController controller, RefereesCatalogue newRefCat)  
	{	
		//Initialize the referees Catalogue
		this.newRefCat = newRefCat;
		// Set the Title of the GUI
		setTitle("Hibernian JavaBall Referee System");
		// Set the size of the GUI
		setSize(GUI_WIDTH, GUI_HEIGHT);
		//  Centers GUI perfectly using two methods. 		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();		
		this.setLocation(getCentralWidth(dim), getCentralHeight(dim));		
		// Removes standard JFrame controls and adds our own.
		this.setUndecorated(true);						
		Border undecorBorder = new BevelBorder(0, JBColour.jBlueMid(), JBColour.jBlueMid());
		((JComponent) this.getContentPane()).setBorder(undecorBorder);
		// Constructs and adds the MenuBar.
		buildMenuBar(this);
		setJMenuBar(menuBar);
		// Constructs but doesn't add the RefGUI object.
		aRefGUI = new RefGUI(controller);
		controller.setControlledRef(aRefGUI);
		// Constructs, but doesn't add the MatchGUI object.
		aMatchGUI = new MatchGUI(controller);
		controller.setControlledMatch(aMatchGUI);
		// Initialize all the panels of the GUI
		initializePanels();
		// Lays out initial Ref Table page and its formats.
		buildMainView();
		// Add the Listiners 
		addMenuBarListeners(controller);
	}

	/***************************************************************************  
	 *  Methods to calculate center points of each Screen
	 *  @param dim - the dimensions
	 *  @return new dimensions, centred
	 **************************************************************************/
	private int getCentralWidth(Dimension dim) 
	{ return (dim.width/2 - this.getSize().width/2); }

	private int getCentralHeight(Dimension dim) 
	{ return (dim.height/2 - this.getSize().height/2); }


	/********************************************************************************************  
	 *    Method initialises centre of GUI: the Home page (this is the first page shown, and 
	 *    the one returned to after the logo is clicked.)
	 *******************************************************************************************/
	private void layoutMiddle() 
	{	//Make sure middlePanel is clean/empty
		middlePanel.removeAll();
		// Set Colours of Panels
		middlePanel.setBackground(Color.WHITE);
		navPanel.setBackground(Color.WHITE);
		helperPanel.setBackground(Color.WHITE);
		barButton.setBackground(Color.WHITE);
		// Set Dimensions
		helperPanel.setPreferredSize(new Dimension(375,260));
		navPanel.setBorder(BorderFactory.createEmptyBorder(40,0,0,0));
		// Set preferred size and icon of button
		barButton.setPreferredSize(new Dimension(115,40));
		barButton.setIcon(icons.addBarChartButton());
		// Add Panels
		middlePanel.add(navPanel);
		middlePanel.add(helperPanel);
		helperPanel.add(mainRefList.getThisTable());
		navPanel.add(barButton);
		//Add the middlePanel to the Frame
		add(middlePanel, BorderLayout.CENTER);
	}

	/*******************************************************************************
	 * A method to set the background and foreground colors of a component
	 * @param aComponent - the JComponent
	 * @param backColor,foreColor
	 ******************************************************************************/
	private void setBackAndForeground(JComponent aComponent, Color backColor, Color foreColor)
	{
		aComponent.setBackground(backColor);
		aComponent.setForeground(foreColor);
	}

	/*******************************************************************************
	 * A method to populate the Referees Table
	 * @param refCat - the referee catalogue
	 ******************************************************************************/
	public void populateTable()
	{	// Empty the IDList table and the main table
		mainRefList.emptyTable();
		IDList.emptyTable();
		// Populate the IDList table and the main table with new data
		mainRefList.populateCells(newRefCat);
		IDList.populateCells(newRefCat);
	}

	/*********************************************************************************  
	 * Initializes west side of Layout.				 
	 ********************************************************************************/
	public void layoutWest() 
	{ 	// Set Colour of Panels
		westPanel.setBackground(Color.WHITE);
		// Set to absolute positioning
		westPanel.setLayout(null);
		// Set the preferred size for the west panel
		westPanel.setPreferredSize(new Dimension(240,200));
		// Set the border of the panel
		westPanel.setBorder(BorderFactory.createEmptyBorder(96,160,150,0));
		// Set absolute position for the table (x,y,width,height)
		IDList.getThisTable().setBounds(175, 105,60,251);
		// Set absolute position for the table (x,y,width.height)
		availableRefList.getThisTable().setBounds(40, 105, 190, 251);
		//Add the West Panel to the Frame
		add(westPanel, BorderLayout.WEST);
		//Add the IDList Table object to the west panel
		westPanel.add(IDList.getThisTable());	
		// Add the availableRefList Table object to the west panel
		westPanel.add(availableRefList.getThisTable());
		// Hide the availableRefList, we want to see it 
		// only in the Create new match screen
		availableRefList.getThisTable().setVisible(false);
		// Show IDList table, because we hide it in the Create new match screen
		IDList.getThisTable().setVisible(true);
		
	}

	/**************************************************************************************
	 * Initializes all Main GUI panels and the Table Objects
	 * containing Referee info
	 *************************************************************************************/
	public void initializePanels()
	{	//Initialize all Panels
		eastPanel = new JPanel();
		westPanel = new JPanel();
		northPanel = new JPanel();
		southPanel = new JPanel();
		middlePanel = new JPanel();
		navPanel = new JPanel();
		// Helper panel helps us change page tersely.
		helperPanel = new JPanel();		
		barPanel = new JPanel(new FlowLayout(0,0,0));
		// Creating ID List and Ref Details table objects
		IDList = new TableObject(ROWS, SINGLE_COL);
		mainRefList = new TableObject(ROWS, FIVE_COLUMNS);
		// Creating Available Refs List table object
		availableRefList = new TableObject(ROWS, TWO_COLUMNS);
		// Initialize the bar button
		barButton = new JButton();
		//Add the title to a String and then store to a label		
		String fillerString = String.format("%-60s", " All Hibernian referees: ");
		JLabel fillerForTable = new JLabel(fillerString);
		// Create the header/title for the main panel
		bigLabel = new JLabel("THE OFFICIAL JAVABALL REFEREE LIST");
		logoLabel = new JLabel(icons.addTitleLogo(1));
		// Add nav and helper panels to the middle panel
		middlePanel.add(navPanel);
		middlePanel.add(helperPanel);
		//Add the sub heading label to the nav panel
		navPanel.add(fillerForTable);
	}
	/**************************************************************************************  
	 * Initialises other sides of the Layout.				 
	 *************************************************************************************/
	public void layoutMargins() 
	{	//Set the preferred size dimensions of the panels
		eastPanel.setPreferredSize(new Dimension(180,120));
		northPanel.setPreferredSize(NORTHDimension);
		southPanel.setPreferredSize(new Dimension(160,30));
		// Set Background-Colour of Panels
		eastPanel.setBackground(Color.WHITE);
		northPanel.setBackground(Color.WHITE);
		southPanel.setBackground(Color.WHITE);
		// Add the Panels to the frame
		add(northPanel, BorderLayout.NORTH);
		add(eastPanel, BorderLayout.EAST);
		add(southPanel, BorderLayout.SOUTH);
		// Back Up for Title 
		bigLabel.setText("THE OFFICIAL JAVABALL REFEREE LIST");
		bigLabel.setFont(BIG_FONT);
		//Add the header bigLabel and logoLabel to the panel
		logoLabel.setIcon(icons.addTitleLogo(1));
		northPanel.add(logoLabel);
		northPanel.add(bigLabel);
		
	}		

	/**************************************************************************************  
	 *  Method keeps a set of panel formats to switch to bar chart view.
	 *************************************************************************************/
	public void setBarFormats()
	{
		barPanel.setPreferredSize(new Dimension(375,256));
		barPanel.setBorder(BorderFactory.createEmptyBorder(39,0,0,0));
		barPanel.setBackground(Color.WHITE);
	}

	/**************************************************************************************
	 * A method to remove the helper and bar panel from the middle panel
	 *************************************************************************************/
	private void clearMiddleAndBarP(){
		middlePanel.remove(helperPanel);
		middlePanel.remove(barPanel);
		// Remove bars from barPanel every time it reappears
		barPanel.removeAll();
	}
	/**************************************************************************************  
	 *  Method that manages the shift between full ref table view and bar chart view.
	 *************************************************************************************/
	public void loadBarChart() 
	{	//remove the helper and bar panel
		clearMiddleAndBarP();
		//Switches from table view to bar view
		helperPanel.remove(mainRefList.getThisTable());
		// Set the bar formats
		setBarFormats();
		//Add the icon to the bar button
		barButton.setIcon(icons.addTableButton());
		// Prints bars onto helper panel.
		rectangleBuilder();
		//Add the bar Panel to the middle panel
		middlePanel.add(barPanel);
		//Refresh
		revalidate();
		//Set the boolean to true / Currently in Bar View
		isBarShowing = true;
	}

	/**************************************************************************************  
	 *  Method that manages the shift between full bar chart view and ref table view
	 *************************************************************************************/
	public void loadMainTable(){

		clearMiddleAndBarP();
		//  Lays out initial Ref Table page and its formats.
		buildMainView();
		// Rebuild Ref Table
		mainRefList.populateCells(newRefCat);
		revalidate();
		//Set the boolean to false / Currently in Table View
		isBarShowing = false;
	}

	/********************************************************************************  
	 *   Method creates and adds a bar for each referee in the catalogue.
	 *******************************************************************************/
	public void rectangleBuilder()
	{	//For each Referee in the Catalogue
		for (int i = 0; i < newRefCat.getActualRefsNumber(); i++)
		{	//Create a new Rectangle Bar, with the Refs number of matches	
			RectangleBar Rec = new RectangleBar(newRefCat.getRefereeAtIndex(i).getMatchAllocations());
			//Add the rectangle bar to the panel
			barPanel.add(Rec.getRectangle());
		}
	}

	/*****************************************************************************  
	 * Method adds Match creator screen. 
	 ****************************************************************************/
	public void switchToAddMatch() 
	{	// Add the panel to the frame
		add(aMatchGUI.getThisPanel(), BorderLayout.CENTER);
		//Set the Title and Icon for the panel and add it
		bigLabel.setText("Create New Match");
		logoLabel.setIcon(icons.addTitleLogo(2));
		northPanel.add(logoLabel);
		northPanel.add(bigLabel);
		//Set preferred size of panels
		northPanel.setPreferredSize(NORTHDimension);
		southPanel.setPreferredSize(new Dimension(180,112));
		// Empty availableRefList table
		availableRefList.emptyTable();
		// Reset Match GUI values to defaults
		aMatchGUI.resetMatchGUI();
		//Layout the West panel
		layoutWest();
		// Hide ID table
		IDList.getThisTable().setVisible(false);
		// Show availableRefList table
		availableRefList.getThisTable().setVisible(true);
		//Refresh screen elements
		aMatchGUI.getThisPanel().updateUI();
		validate();
	}	

	/***************************************************************************
	 * Delegate method to populate ID table with available refs
	 * @param newMatch the match according to which will refs be generated
	 **************************************************************************/
	public void populateCellsWithAvailRefs(Match newMatch, Referee [] availableRefs)
	{	// Empty availableRefList table
		availableRefList.emptyTable();
		// Set confirmation label
		aMatchGUI.setConfirmationLabel();
		//Load the Data
		availableRefList.populateCellsWithAvailRefs(newRefCat, newMatch, availableRefs);
	}

	/************************************************************************  
	 * Adds buttons and title for the search screen.  
	 ***********************************************************************/
	public void toSearchRef()
	{ 	//Display appropriate buttons
		aRefGUI.displayRefSearchButtons();
		//Set the Header and add it to the north panel
		bigLabel.setText("Search Referees: ");
		logoLabel.setIcon(icons.addTitleLogo(3));
		northPanel.add(logoLabel);
		northPanel.add(bigLabel);
//		northPanel.setBorder(BorderFactory.createEmptyBorder(10,0,40,0));
		//Refresh the refGUI object
		aRefGUI.updateUI();
	}	

	/**************************************************************************************  
	 *  Method to switch view to the Add Ref or Edit Ref screens. Tests for Search components,
	 *  and calls a RefGUI method to create and display the full ref edit screen.
	 *  @param oneRef - the referee 
	 *************************************************************************************/
	public void toFullRefDetails(Referee oneRef)
	{	//Remove the Search button
		aRefGUI.removeSearchFields();
		//Display full ref Details components and add the title
		bigLabel.setText(aRefGUI.displayAllRefDetails(oneRef));
		logoLabel.setIcon(icons.addTitleLogo(3));
		northPanel.add(logoLabel);
		northPanel.add(bigLabel);
		//Refresh the panels
		northPanel.updateUI();
		westPanel.updateUI();
		aRefGUI.updateUI();

		// Disable/Enable text fields based on the current page
		if (oneRef != null)
			//Disable editing
			aRefGUI.setEdit(false);
		else 		
			aRefGUI.setEdit(true);
	}

	/******************************************************************************
	 * A method to clear the Main View
	 *****************************************************************************/
	public void clearMainView()
	{	//Remove all Panels
		westPanel.removeAll();
		northPanel.removeAll();
		middlePanel.removeAll();
		aRefGUI.getThisPanel().removeAll();
		remove(aRefGUI.getThisPanel());
		remove(aMatchGUI.getThisPanel());
		remove(middlePanel);

		// Set default size for southPanel
		southPanel.setPreferredSize(new Dimension(160,30));
	}
	/******************************************************************************
	 * A method to build the Main View
	 *****************************************************************************/
	public void buildMainView()
	{	//Make sure layouts know that table should be built
		isBarShowing = false;
		// Lays out initial Ref Table page and its formats.
		layoutMiddle();
		layoutWest();
		layoutMargins();
	}

	/*******************************************************************************  
	 * Method removes search result ref table from the West panel
	 * and gets the center ready for the Home page. 
	 ******************************************************************************/
	public void resetFromMatchView()
	{	//Remove the old panel
		remove(aMatchGUI.getThisPanel());
		// Lays out initial Ref Table page and its formats.
		buildMainView();
		// Add the middle panel to the frame
		add(middlePanel, BorderLayout.CENTER);
		//Refresh Screen
		middlePanel.updateUI();
	}

	/***********************************************************************************  
	 *  Accessor method
	 *  @return boolean - if the bar view or table view is on
	 **********************************************************************************/
	public boolean getIfBarShowing()
	{ return isBarShowing; }

	/***********************************************************************************  
	 * Methods return whole RefGUI or MatchGUI object 
	 **********************************************************************************/
	public RefGUI getRefGUI()
	{ return aRefGUI; }

	public MatchGUI getMatchGUI()
	{ return aMatchGUI;	}

	/**************************************************************************************
	 *  Method builds the Menu bar for the frame. 
	 *  (JFrame doesn't support separate JMenuBar objects.)
	 *************************************************************************************/
	public void buildMenuBar(JFrame forFrame)
	{	// Initialize the Menu Bar
		menuBar = new JMenuBar();
		// Initializes Matches and Referees Menu categories 
		menuMatch = new JMenu("Matches");
		menuRef = new JMenu("Referees");
		// Match and Ref drop-down menu items
		menuMatchCreate = new JMenuItem("Create New Match");
		menuRefSearch = new JMenuItem("Search Refs");
		menuRefAdd = new JMenuItem("Add New Ref");
		menuRefUpdate = new JMenuItem("Update / Delete Ref");
		//  Blank string to space elements.
		String menuFillerString = String.format("%130s", " ");
		JLabel fillerForQuit = new JLabel(menuFillerString);
		// Initializes logo and quit button
		logoButton = new JButton("");
		quitButton = new JButton();

		// Add icons to Buttons
		logoButton.setIcon(icons.addLogo(1));
		menuMatch.setIcon(icons.addMatchesButton(1));
		menuRef.setIcon(icons.addRefereeButton(1));
		quitButton.setIcon(icons.addSaveAndClose(1));
		// Set Preferred Sizes
		logoButton.setPreferredSize(new Dimension(36,36));
		menuMatch.setPreferredSize(new Dimension(115,36));
		menuRef.setPreferredSize(new Dimension(115,36));
		quitButton.setPreferredSize(new Dimension(36,36));

		// Set the Font for the Menus
		menuMatch.setFont(MAIN_FONT);
		menuRef.setFont(MAIN_FONT);
		// Set Background and Foreground colours
		menuBar.setBackground(JBColour.jBlueMid());
		menuMatchCreate.setBackground(JBColour.jGreenLight());
		setBackAndForeground(logoButton,Color.white, JBColour.jBlueMid());
		setBackAndForeground(menuMatch,JBColour.jGreenMid(), JBColour.jGreenLight());
		setBackAndForeground(menuRef,JBColour.jGrayMid(), JBColour.jGrayMid());
		setBackAndForeground(menuRefSearch,JBColour.jGrayMid(), Color.white);
		setBackAndForeground(menuRefAdd,JBColour.jGrayMid(), Color.white);
		setBackAndForeground(menuRefUpdate,JBColour.jGrayMid(), Color.white);
		setBackAndForeground(quitButton,JBColour.jRedLight(), JBColour.jRedMid());

		//Add components to JMenu
		menuBar.add(logoButton);
		menuBar.add(menuMatch);
		menuMatch.add(menuMatchCreate);
		menuBar.add(menuRef);
		menuRef.add(menuRefSearch);
		menuRef.add(menuRefAdd);
		menuRef.add(menuRefUpdate);
		menuBar.add(fillerForQuit);
		menuBar.add(quitButton);	
	}


	/**************************************************************************************  
	 * Method to set menuBar events to pass to the controller object.
	 * @param controller - the JBController
	 *************************************************************************************/
	private void addMenuBarListeners(JBController controller)
	{	//Array to store button and menu items
		AbstractButton[] allComponents = {logoButton, barButton, menuMatchCreate, menuRefSearch, menuRefAdd, menuRefUpdate, quitButton};
		//Add Action listeners to buttons and menu items
		for(AbstractButton oneComponent : allComponents)
			oneComponent.addActionListener(controller);
		//Add mouse listeners to buttons
		mouseListenerCreator(logoButton);
		mouseListenerCreator(menuMatch);
		mouseListenerCreator(menuRef);
		mouseListenerCreator(quitButton);
		mouseListenerCreator(barButton);
	}

	/**************************************************************************************  
	 * Method adds dynamic mouse listeners to components.  
	 * @param aComponent - the component to be listened to
	 *************************************************************************************/
	private void mouseListenerCreator(final AbstractButton aComponent)
	{	//  Listener to track mouse movement over bar elements.
		aComponent.addMouseListener(new MouseListener() 					
		{
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}			
			public void mouseExited(MouseEvent e) {	
				if (aComponent == logoButton)
				{	aComponent.setBackground(Color.white);
				logoButton.setIcon(icons.addLogo(1));
				} else if (aComponent == menuRef)
					menuRef.setIcon(icons.addRefereeButton(1));
				else if(aComponent == menuMatch)
					menuMatch.setIcon(icons.addMatchesButton(1));
				else if(aComponent == quitButton)
				{	aComponent.setBackground(JBColour.jRedLight()); 
				quitButton.setIcon(icons.addSaveAndClose(1));
				} else 
				{	aComponent.setBackground(JBColour.jRedLight());
				aComponent.setForeground(JBColour.jRedLight());
				}
			}//mouseExited
			public void mouseEntered(MouseEvent e) { 
				if (aComponent == logoButton)
				{	aComponent.setBackground(Color.white);
				logoButton.setIcon(icons.addLogo(0));						
				} else if (aComponent == menuRef)
					menuRef.setIcon(icons.addRefereeButton(0));
				else if(aComponent == menuMatch)
					menuMatch.setIcon(icons.addMatchesButton(0));
				else if(aComponent == quitButton)
				{	aComponent.setBackground(JBColour.jRedMid());  
				quitButton.setIcon(icons.addSaveAndClose(0));
				} else 
				{	aComponent.setBackground(JBColour.jRedMid());  
				aComponent.setForeground(JBColour.jRedMid());
				}
			}//mouseEntered
			public void mouseClicked(MouseEvent e) {}
		});
	}
}