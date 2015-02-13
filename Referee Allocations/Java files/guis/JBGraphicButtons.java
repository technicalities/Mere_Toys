package guis;

import javax.swing.ImageIcon;


/*****************************************************************************************
 * 	Central location for uploading all custom icons from buttons folder.
 * 	Uploaded icons grouped in methods to called with int.
 * 
 *  - Constructor Method
 *  - Menu Button Methods
 *  - Bar Chart and Table Button Methods
 *  - Graphics Methods
 *  - JOptionPane Custom Icons Methods
 * 
 * *****************************************************************************************/


public class JBGraphicButtons {
	private ImageIcon buttonIcon;

	/*****************************************************************************************
	 * 	Constructor Method
	 * *****************************************************************************************/
	public JBGraphicButtons()
	{
		this.buttonIcon = null;
	}

	/*****************************************************************************************
	 * 	Main Menu Bar Button Methods
	 *  - Add Methods
	 * *****************************************************************************************/
	// Logo Home Button Icons
	public ImageIcon addLogo(int num)
	{
		// Numbers passed correspond to these values
		int logoButtonUpstate = 1;
		// Load upstate if 1 number entered
		if (num == logoButtonUpstate)
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_logo_home.png");
		// Load downstate if 0 number entered
		else 
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_logo_home_down.png");
		return buttonIcon;
	}

	// Matches Button Icons
	public ImageIcon addMatchesButton(int num)
	{
		// Numbers passed correspond to these values
		int matchesButtonUpstate = 1;
		// Load upstate if 1 number entered
		if (num == matchesButtonUpstate)
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_menu_button_matches.png");
		// Load downstate if 0 number entered
		else 
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_menu_button_matches_over.png");

		return buttonIcon;
	}

	// Referee Button Icons
	public ImageIcon addRefereeButton(int num)
	{
		// Numbers passed correspond to these values
		int refereeButtonUpstate = 1;
		// Load upstate if 1 number entered
		if (num == refereeButtonUpstate)
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_menu_button_ref.png");
		// Load downstate if 0 number entered
		else 
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_menu_button_ref_over.png");
		return buttonIcon;
	}

	// Save And Close Button Icons
	public ImageIcon addSaveAndClose(int num)
	{
		// Numbers passed correspond to these values
		int closeButtonUpstate = 1;
		// Load upstate if 1 number entered
		if (num == closeButtonUpstate)
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_close.png");
		// Load downstate if 0 number entered
		else 
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_close_down.png");
		return buttonIcon;
	}

	/*****************************************************************************************
	 * 	Bar Chart and Table Button Methods
	 *  - Add Methods
	 * *****************************************************************************************/

	// Bar Chart Button Icon
	public ImageIcon addBarChartButton()
	{
		// Load bar chart button icon
		return buttonIcon = new ImageIcon("buttons/BarIcon.png");
	}

	// Table Button
	public ImageIcon addTableButton()
	{
		// Load table button icon
		return buttonIcon = new ImageIcon("buttons/Table.png");
	}

	/*****************************************************************************************
	 * 	Graphics Methods
	 *  - Add Methods
	 ******************************************************************************************/

	// Graphic for title
	public ImageIcon addTitleLogo(int num)
	{
		// Numbers passed correspond to these values
		int title = 1;
		int match = 2;
		int ref = 3;
		// Load Main Logo
		if (num == title)
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_Header_Logo.png");
		// Load Matches Header Graphic
		if (num == match)
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_Header_Matches.png");
		// Load Referee Header Graphic
		if (num == ref)
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_Header_Referee.png");
		return buttonIcon;
	}

	/*****************************************************************************************
	 * 	JOptionPane Custom Icons Methods
	 *  - Add Methods
	 ******************************************************************************************/

	// JOptionPane Custom Icon Button
	public ImageIcon addJPaneIcon(int num)
	{		
		// Numbers passed correspond to these values
		int warning = 1;
		int errors = 2;
		int confirmation = 3;
		int save = 4;
		// Load Warning if 1 number entered
		if (num == warning)
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_warning_jpane.png");
		// Load Error if 2 number entered
		if (num == errors)
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_error_jpane.png");
		// Load Confirmation if 3 number entered
		if (num == confirmation)
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_confirmation_jpane.png");
		// Load Save Icon if 4 number entered
		if (num == save)
			this.buttonIcon = new ImageIcon("buttons/JBbuttons_save_jpane.png");
		// Return Custom JOptionPane Icon
		return buttonIcon;
	}

}




