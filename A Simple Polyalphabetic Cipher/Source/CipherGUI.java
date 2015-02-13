import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.*;
import java.io.*;

/** Programming AE2. View/Controller class.
 * Class to display cipher GUI and listen for events  */

public class CipherGUI extends JFrame implements ActionListener
{
	// JComponent instance variables
	private JPanel topPanel, top2, bottom, middlePanel;
	private JButton encodeButton, decodeButton, quitButton, chooserButton, questButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel, nietzscheFiller, filler, filler2, filler3;
	private JRadioButton monoRadio, vigenRadio;

	// Application instance variables		 		
	private String inputFileName;													//  Stores "core" file name from field.
	private String key = "";														//  Stores keyword
	private char [] fileAsArray = new char[0];
	private String last = "";
	

/******************************************************************************************************
 * 		Constructor. Also adds layout manager to the frame		 		 
 ******************************************************************************************************/
	public CipherGUI()
	{
		this.setSize(420,320);
		this.setTitle("Seek Ciphers!");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation( (dim.width/2 - this.getSize().width/2), (dim.height/2 - this.getSize().height/2));
		this.layoutComponents();
		
		//  Gets rid of ugly Java JFrame.
	    this.setUndecorated(true);
	}

	
/******************************************************************************************************
 * Helper method to add components to the frame *		 
 ******************************************************************************************************/
	public void layoutComponents()
	{	 
		// top panel for buttons
		topPanel = new JPanel();					
		topPanel.setBackground(Color.black);
		this.add(topPanel,BorderLayout.NORTH);

		//  Help button.
		questButton = formatButton("?", topPanel);	
		//  Quit button.
		quitButton = formatButton(" X ", topPanel);	
		
		
		//  middle does everything except buttons
		middlePanel = new JPanel();							
		middlePanel.setBackground(Color.black);
		this.add(middlePanel,BorderLayout.CENTER);		
		
		filler = new JLabel("He said 'The limits of my language are the limits of my world.'");
		filler.setForeground(Color.black);
		middlePanel.add(filler);
		
		//  The title label.
		nietzscheFiller = new JLabel(String.format("%s", "SEEK CIPHERS!"));
		Font mainFont = new Font(Font.SERIF, Font.BOLD, 24);
		nietzscheFiller.setForeground(Color.black);
		nietzscheFiller.setFont(mainFont);
		nietzscheFiller.setForeground(Color.green);
		middlePanel.add(nietzscheFiller);
		
		filler3 = new JLabel("He said 'The limits of my language are the limits of my world.'");
		filler3.setForeground(Color.black);
		middlePanel.add(filler3);
		
		//  Label and field for entering keyword.
		keyLabel = new JLabel("Keyword:      ");	
		keyLabel.setForeground(Color.green);
		middlePanel.add(keyLabel);	
		keyField = formatField(26, middlePanel);	
		
		//  Label and field for entering input file name.
		messageLabel = new JLabel("Message file : ");	
		messageLabel.setForeground(Color.green);
		middlePanel.add(messageLabel);
		messageField = formatField(18, middlePanel);				
		
		// Create file chooser button.
		chooserButton = formatButton("Select...", middlePanel);	
		
		filler2 = new JLabel("Whereof we cannot speak, thereof we must pass in silence.");
		filler2.setForeground(Color.black);
		middlePanel.add(filler2);
		
		//  Radio buttons to select cipher now.
		monoRadio = formatRadio("Mono Shift        ", middlePanel);
		vigenRadio = formatRadio("Vigenére", middlePanel);	
		ButtonGroup group = new ButtonGroup();
	    group.add(monoRadio);
	    group.add(vigenRadio);
		
		filler = new JLabel("Whereof we cannot speak, thereof we must pass in silence.");
		filler.setForeground(Color.black);
		middlePanel.add(filler);
			
		// create mono button and add it to the middle panel
		encodeButton = formatButton("Encode now!", middlePanel);	

		// create vigenere button and add it to the middle panel
		decodeButton = formatButton("Decode now!", middlePanel);	
		
		// bottom panel is for spacing
		bottom = new JPanel();					
		bottom.setBackground(Color.black);
		this.add(bottom,BorderLayout.SOUTH);		
	}


	//  Method to define and format a JButton, 
	//  including a caption for actionPerformed trigger.
	private JButton formatButton(String caption, JPanel panel) 
	{
		JButton b = new JButton(caption);
		b.setActionCommand(b.getText());
		b.addActionListener(this);
		b.setBackground(Color.black);
		b.setForeground(Color.green);
		panel.add(b);
		return b;
	}
	
	//  Method to define and format a JRadioButton. 
	private JRadioButton formatRadio(String caption, JPanel panel) 
	{
		JRadioButton r = new JRadioButton(caption);
		r.addActionListener(this);
		r.setBackground(Color.black);
		r.setForeground(Color.green);
		panel.add(r);
		mouseDetail(r);
		return r;
	}
	
	//  Method to define and format a JRadioButton. 
	private JTextField formatField(int size, JPanel panel) 
	{
		JTextField t = new JTextField(size);
		t.addActionListener(this);
		t.setBackground(Color.black);
		t.setForeground(Color.green);
		t.setCaretColor(Color.white);
		panel.add(t);
		mouseDetail(t);
		return t;
	}
	
	//  Method to add the same mouse listener decal to whatever.
	private JComponent mouseDetail(final JComponent b)
	{
		MouseListener listenForB = new MouseListener() 
		{
		    public void mouseReleased(MouseEvent e) {}
		    public void mousePressed(MouseEvent e) {}
		    public void mouseExited(MouseEvent e) {	b.setBackground(Color.black);  b.setForeground(Color.green);     }
		    public void mouseEntered(MouseEvent e) { b.setBackground(Color.white);  b.setForeground(Color.black); }
		    public void mouseClicked(MouseEvent e) {}
		    };			
		b.addMouseListener(listenForB);
		return b;
	}
	
	
	
/******************************************************************************************************
 * Listener and reactor to buttonpress events (conditionally uses helper methods 3&4&5 below)
 * @param e the event		 
 ******************************************************************************************************/
	public void actionPerformed(ActionEvent e)
	{			
		if (e.getSource() == chooserButton)									//  user has pressed "Select file"
		{
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
			chooser.setFileFilter(filter);	
			int returnVal = chooser.showOpenDialog(this);
		
			if(returnVal == JFileChooser.APPROVE_OPTION) 
				messageField.setText(chooser.getSelectedFile().getName());
		}
		
		if (e.getSource() == encodeButton)									//  user has pressed "Process: Mono."
		{
			if (getKeyword() && processFileName(last) && processFile(inputFileName, fileAsArray))	//  Tests keyword validity, filename validity and file contents.					
					if (monoRadio.isSelected())
					{
						try {	encodeMono(last, fileAsArray);	} 			//  creates Cipher object, processes file, writes.
							catch (FileNotFoundException e1) 
								{	e1.printStackTrace();	}
					}
					else if (vigenRadio.isSelected())
					{
						try {	encodeVigen(last, fileAsArray);	} 			//  creates Cipher object, processes file, writes.
							catch (FileNotFoundException e1) 
								{	e1.printStackTrace();	}
					}
		}			
		
		if (e.getSource() == decodeButton)							//  user has pressed "Process: Vigenere."
		{
			if (getKeyword() && processFileName(last) && processFile(inputFileName, fileAsArray))	//  Tests keyword validity & file validity				
				if (monoRadio.isSelected())
				{
					try {	decodeMono(last, fileAsArray);	} 			//  creates Cipher object, processes file, writes.
						catch (FileNotFoundException e1) 
							{	e1.printStackTrace();	}
				}
				else if (vigenRadio.isSelected())
				{
					try {	decodeVigen(last, fileAsArray);	} 			//  creates VCipher object, processes file, writes.
						catch (FileNotFoundException e1) 
							{	e1.printStackTrace();	}
				}
		}			
		
		if (e.getSource() == quitButton)									
		{
			Object [] options = {"I'M OUTTA HERE", "NAH, MORE COOLNESS"};
			int choice = JOptionPane.showOptionDialog(null, "Sure?", "Stop being cool", JOptionPane.YES_NO_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
						if (choice == 0 )
						{
							System.exit(0);
						}
		}
						
		if (e.getSource() == questButton)									
		{
			JOptionPane.showMessageDialog(null, "Cipher offers a basic shift cipher and a polyalphabetic 'Vigenére' cipher.\nDeveloped by https://github.com/technicalities, December 2014.\n\nThe sample file I've given you is called 'importunate'.\n\nLook in the root folder for your encrypted and decrypted files.\n\nFile selection isn't so hot, beg your pardon.\n");
		}
}
	
	
	
/** 3. Helper to obtains cipher keyword. If the keyword is invalid, a message is produced
 * 	@return whether a valid keyword was entered 		 */
	private boolean getKeyword()
	{	
		this.key = keyField.getText();					//  Test for: empty string, non-upper case character, non-unique character.

		if (key.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Keyword can't be blank.", " ", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
			return false;
		}
	
		else if (!testUnique(key))
		{
			JOptionPane.showMessageDialog(null, "All keyword characters must be unique.", " ", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");
			return false;
		}	
		if (testAlph(key))
		{
				key = key.toUpperCase();
				System.out.println("The key: " + key);
				return true;
		}
		else return false;
	}

	
//  Test if whole keyword is 1) alphabetical, AND 2) upper-case
		private boolean testAlph(String key)	
		{				
			for (int i = 0; i < key.length(); i++)							//  1) Is every character alphabetical? 
			{
				if (!(Character.isLetter(key.charAt(i))))
				{
					JOptionPane.showMessageDialog(null, "Alphabet only.", "", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
			return true;
		}
		
//  Test if all characters are unique
		private boolean testUnique(String key)								  
		{
			char [] keyChar = key.toCharArray();
			
			for (int i = 0; i < key.length(); i++)							//  Loop over whole word
			{												 
			for (int j = 0; j < key.length(); j++)							//  Does the ith character appear anywhere else (j) in Key?
				{   if (i != j)													//  Conditional at line 140 prevents mistaking the same character at i for a duplicate at j.
						if (keyChar[i] == keyChar[j])					
							return false;	}     								
			}
		return true;								
		}											 
	
		
/**  4. Helper. Obtains filename from GUI
 * The details of the filename and the type of coding are extracted. If the filename is invalid, a message is produced.
 * @return whether a valid filename was entered				 */
	
	private boolean processFileName(String last)
	{ 	
		try																	//  Try block catches empty filename.
		{
		inputFileName = messageField.getText();
		last = inputFileName.substring(inputFileName.length() - 1);
		
			String inputFilePath = inputFileName + ".txt";
			try																//  Try block catches missing files / file name typos.
				{	FileReader reader = new FileReader(inputFilePath);	}
			catch (IOException e)										
				{
				JOptionPane.showMessageDialog(null, "File not found (by that name).", "I/O Exception", JOptionPane.ERROR_MESSAGE);
				messageField.setText("");
				return false;	
				}
		}
		catch (StringIndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(null, "File name cannot be empty.", "Empty file string", JOptionPane.ERROR_MESSAGE, null);
			messageField.setText("");
			return false;
		}
		return true;
	}

	
/** 5. Helper reads input file, validates, and converts to array. @Returns input as array. */
	private boolean processFile(String inputFileName, char[] fileAsArray_i)
	{	
	try																		//  Whole method inside a IOException try block.
	{
			String inputFilePath = inputFileName + ".txt";
			File inputFile = new File(inputFilePath);						//  Create File, take in whole at once 
			FileInputStream wholeStream = new FileInputStream(inputFile);	//  to pass to upper-case tester method.
			byte[] wholeFile = new byte[(int) inputFile.length()];
			wholeStream.read(wholeFile);
			wholeStream.close();
			
			String fileAsString = new String(wholeFile);					//  Convert to string for helper.
			this.fileAsArray = fileAsString.toCharArray();					//  Convert to array for coding. 
			
			System.out.println();
			System.out.println("Check input array before cipher operation: ");
				for (int i = 0; i < fileAsArray.length; i++)				//  Trace statement prints whole file array.
					{	System.out.print(fileAsArray[i]);	}					
					System.out.println();
					
		if (!allcaps(fileAsString))											//  Test for all caps in file. (Helper just below.)
		{
			fileAsString = fileAsString.toUpperCase();
			
		}
		return true;
	}
	catch (IOException x)
		{
		JOptionPane.showMessageDialog(null, "File not found (by that name).", "I/O Exception", JOptionPane.ERROR_MESSAGE);
		messageField.setText("");
		return false;
		}
	}

//  Helper to test for all capitals in file. (Used in processFile.)
		private boolean allcaps(String fileAsString)
		{
			if (fileAsString.toUpperCase().equals(fileAsString)) 			//  Is an upper-case copy of the string the same as the string?		
				return true;												//  If so, the whole string is upper case.
			else	
				return false;
		}


/** 5a. Method for coordinating Mono encoding or decoding and writing output file. (Taken out of processFile to obey style guide line-limit.)
 *  Each character is encoded or decoded as appropriate and written to output. 		*/
	private char[] encodeMono(String last_i, char[] fileAsArray_i) throws FileNotFoundException 
	{	
		this.last = inputFileName.substring(inputFileName.length() - 1);
		char[] codedArray = new char[this.fileAsArray.length];					//  Define final array here, to pass as parameters just below.
		
		MonoCipher mono1 = new MonoCipher(key);									//  Create MonoCipher object.	
		LetterFrequencies letterfreq = new LetterFrequencies ();				//  Create LetterFrequencies object.

		mEncodeFile(mono1, fileAsArray, codedArray, letterfreq);
		writeEncToFile(codedArray, letterfreq);
		return codedArray;
	}


	private char[] decodeMono(String last_i, char[] fileAsArray_i) throws FileNotFoundException 
	{	
		this.last = inputFileName.substring(inputFileName.length() - 1);
		char[] codedArray = new char[this.fileAsArray.length];					//  Define final array here, to pass as parameters just below.
		
		MonoCipher mono1 = new MonoCipher(key);									//  Create MonoCipher object.	
		LetterFrequencies letterfreq = new LetterFrequencies ();				//  Create LetterFrequencies object.

		mDecodeFile(mono1, fileAsArray, codedArray, letterfreq);
		writeDecToFile(codedArray, letterfreq);
		return codedArray;
	}

	
/** 5b. Helper for coordinating the Vigenere cipher. 			 */
	private char[] encodeVigen(String last_i, char[] fileAsArray_i) throws FileNotFoundException
	{	
		this.last = inputFileName.substring(inputFileName.length() - 1);
		char[] codedArray = new char[fileAsArray.length];					//  Define final array here, to pass as parameters just below.
		
		VCipher vigen1 = new VCipher(key);									//  Create VCipher object.
		LetterFrequencies letterfreq = new LetterFrequencies ();			//  Create LetterFrequencies object.
		
		vEncodeFile(vigen1, fileAsArray, codedArray, letterfreq);
		writeEncToFile(codedArray, letterfreq);
		return codedArray;
	}	

	
	private char[] decodeVigen(String last_i, char[] fileAsArray_i) throws FileNotFoundException 
	{	
		this.last = inputFileName.substring(inputFileName.length() - 1);
		char[] codedArray = new char[this.fileAsArray.length];					//  Define final array here, to pass as parameters just below.
		
		VCipher vigen1 = new VCipher(key);										//  Create MonoCipher object.	
		LetterFrequencies letterfreq = new LetterFrequencies ();				//  Create LetterFrequencies object.

		vDecodeFile(vigen1, fileAsArray, codedArray, letterfreq);
		writeDecToFile(codedArray, letterfreq);
		return codedArray;
	}

	
//  Two helpers to encode characters for Mono or Vigen. Loop also feeds into letter frequencies. 
	private char[] mEncodeFile(MonoCipher mono1, char[] fileAsArray_i, char[] codedArray, LetterFrequencies letterfreq)
	{
	System.out.println("\nCheck output array after Mono encoding: ");	
	for (int i = 0; i < (fileAsArray.length); i++)								//  'For' loop calls Mono encode, [length] times.
		{		
			letterfreq.addChar(fileAsArray[i]);
			codedArray[i] = mono1.encode(fileAsArray[i]);
			System.out.print(codedArray[i] + " ");
		}
	System.out.println();
	return codedArray;
	}
	
	
	private char[] vEncodeFile(VCipher vigen1, char[] fileAsArray_i, char[] codedArray, LetterFrequencies letterfreq)
	{
	int whichRow = 0; 
	System.out.println();
	System.out.println("Check output array after Vigen encoding: ");	
		for (int i = 0; i < (fileAsArray.length); i++)						//  'For' loop calls Vigen encode, [length] times.
			{	
				if (whichRow == (key.length() - 1))							//  Increments the encryption array to be used, up to key-length index.
					whichRow = 0;
				letterfreq.addChar(fileAsArray[i]);
				codedArray[i] = vigen1.encode(fileAsArray[i], whichRow);	//  Calls the VCipher encode method.
				whichRow++;
				System.out.println("Array["+i+ "] gets: " + codedArray[i] + " ");
				}
		return codedArray;
	}
	
	
	
//  Helpers to call Decode method for whole file. Also feeds into letter frequencies. Mono and Vigen versions. 
	
	private char[] mDecodeFile(MonoCipher mono1, char[] fileAsArray_i, char[] codedArray, LetterFrequencies letterfreq)
	{
		System.out.println("Check output array after Mono decoding: ");
		for (int i = 0; i < fileAsArray.length; i++)						//  'For' loop calls Mono decode, [length] times.
			{
				letterfreq.addChar(fileAsArray[i]);	
				codedArray[i] = mono1.decode(fileAsArray[i]);
				System.out.print(codedArray[i]);
			}
		return codedArray;	
	}
			
	private char[] vDecodeFile(VCipher vigen1, char[] fileAsArray_i, char[] codedArray, LetterFrequencies letterfreq)
	{
		int whichRow = 0;
		for (int i = 0; i < fileAsArray.length; i++)						//  'For' loop calls Vigen decode, [length] times.
			{
				if (whichRow == (key.length() - 1))
					whichRow = 0;
				
				codedArray[i] = vigen1.decode(fileAsArray[i], whichRow);
//				letterfreq.addChar(fileAsArray[i]);
				whichRow++;;
			}
		System.out.println();
		System.out.println("Check output array after vigen decoding: ");
		for (int i = 0; i < fileAsArray.length; i++)
		{
		System.out.println("Array["+i+ "] gets: " + codedArray[i] + " ");
		}
		return codedArray;	
	}
	
	
//  Helper to 1) create appropriate filename and 2) write final string out.
	private void writeEncToFile(char[] codedArray, LetterFrequencies letterfreq)	//  Writes to output after encoding. 
	{
		try
		{		
			String outputFilePath = inputFileName + "C" + ".txt";
			String freqFilePath = inputFileName + "F" + ".txt";
			PrintWriter encodeFileWriter = new PrintWriter(outputFilePath);
			PrintWriter freqReportWriter = new PrintWriter(freqFilePath);
			
			encodeFileWriter.write(codedArray);
			freqReportWriter.write(letterfreq.getReport());
			
			encodeFileWriter.close();
			freqReportWriter.close();
			offerLoop(codedArray, outputFilePath);
		}
		catch (FileNotFoundException x)
			{	JOptionPane.showMessageDialog(null, "File not found (by that name). Creating file.", "I/O Exception", JOptionPane.ERROR_MESSAGE);	}
	}
	
		
//  Writes to output after decoding.
	private void writeDecToFile(char[] codedArray, LetterFrequencies letterfreq) 
	{
		try
		{
		String outputFilePath = inputFileName + "D" + ".txt";
		String freqFilePath = inputFileName + "F" + ".txt";
		PrintWriter decodeFileWriter = new PrintWriter(outputFilePath);
//		PrintWriter freqReportWriter = new PrintWriter(freqFilePath);
		
		decodeFileWriter.write(codedArray);
//		freqReportWriter.write(letterfreq.getReport());
			
		decodeFileWriter.close();
//		freqReportWriter.close();
		offerLoop(codedArray, outputFilePath);
		}
		catch (FileNotFoundException x)
		{	JOptionPane.showMessageDialog(null, "File not found (by that name). Creating file.", "I/O Exception", JOptionPane.ERROR_MESSAGE);	}
	}
	
	
	private void offerLoop(char[] codedArray, String outputFilePath)
	{
	String mess = Arrays.toString(codedArray);
	JOptionPane.showMessageDialog(null, "The resulting message is in the file '"+outputFilePath, "'", JOptionPane.INFORMATION_MESSAGE);
	
	}
	

}
