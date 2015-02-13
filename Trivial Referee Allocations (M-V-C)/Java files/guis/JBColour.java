package guis;

import java.awt.Color;

/****************************************************************
 * 					The Colour Object 
 * Set the colour pallet for the JavaBall application.
 *****************************************************************/
public abstract class JBColour {

	private static Color colour;

	/****************************************************************
	 * GREEN Methods to return Java Ball Colour
	 * @return colour - RGB Color
	 ****************************************************************/

	// Green Light RGB = R143,G229,B92
	public static Color jGreenLight ()
	{	//Create new Color
		colour = new Color(143,229,92);
		return colour;
	}

	// Green Mid RGB =  R113, G150, B90
	public static Color jGreenMid ()
	{	//Create new Color
		colour = new Color(113,150,90);
		return colour;
	}

	/****************************************************************
	 * RED Methods to return Java Ball Colour 
	 * @return colour - RGB Color
	 ****************************************************************/
	// Red Light RGB =  R229,G115,B115
	public static Color jRedLight ()
	{	//Create new Color
		colour = new Color(229,115,115);
		return colour;
	}
	// Red Mid RGB =  R153,G99,B77
	public static Color jRedMid ()
	{	//Create new Color
		colour = new Color(153,99,77);
		return colour;
	}


	/*****************************************************************
	 * BLUE Methods to return Java Ball Colour
	 * @return colour - RGB Color
	 *****************************************************************/
	// Blue Light RGB =  R92,G126,B229
	public static Color jBlueLight ()
	{	// Create new Color
		colour = new Color(92,126,229);
		return colour;
	}
	// Blue Mid RGB =  R92,G107,B153
	public static Color jBlueMid ()
	{	// Create new Color
		colour = new Color(92,107,153);
		return colour;
	}
	// Blue Dark RGB =  R56,G41,B102
	public static Color jBlueDark ()
	{	// Create new Color
		colour = new Color(56,41,102);
		return colour;
	}

	/*****************************************************************
	 * Gray Methods to return Java Ball Colour
	 * @return colour - RGB Color
	 ****************************************************************/
	// Gray Dark RGB = R26,G26,B26 
	public static Color jGrayDark()
	{	// Create new Color
		colour = new Color( 26,26,26);
		return colour;
	}
	// Gray Mid RGB = R51,G51,B51
	public static Color jGrayMid()
	{	// Create new Color
		colour = new Color(51,51,51);
		return colour;
	}	
}