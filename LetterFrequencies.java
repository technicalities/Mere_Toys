/**  Programming AE2
 * 
 *   Model class that processes summary statistics and a report on letter frequencies.
 *   
 *   The LetterFrequencies object should do its setting up in the constructor and then be given letters as 
 *   the encryption or decryption takes place.  You should then work out when the LetterFrequencies object 
 *   should calculate the frequencies and how it should report the totals and frequencies for each letter, 
 *   as well as the most frequent letter, to the GUI class.
 *   
 */
public class LetterFrequencies
{
	private final int SIZE = 26; 							//  Size of the alphabet 
	private int [] alphaCounts = new int[SIZE];				//  Count for each letter
	private char [] alphabet = new char[SIZE];					//  The alphabet 
												 	
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,	// Average frequency counts 
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	private char maxCh;										//  Character that occurs most frequently
	private int totChars = 0;								//  Total number of characters encrypted / decrypted
	
	
	/** Instantiates a new letterFrequencies object.	*/
	public LetterFrequencies()
	{		
		for (int i = 0; i < SIZE; i++)						//  Loop creates alphabet as array of 26 distinct elements
			{
			alphabet[i] = (char) ('A'+ i);
			}
	}
	
	/** Increases frequency details for given character
	 * @param ch the character just read	 */
	public void addChar(char ch)
	{
		for (int i = 0; i < SIZE; i++)
		{
			if (ch == alphabet[i])
			{
			alphaCounts[i]++;
			totChars++;
			}
		}
  	}
	
	/** Gets the index of the character with maximum frequency.
	 * @return the maximum frequency	*/
	private int getMaxPC()
        {
	    int maxIndexSoFar = 0;

		for (int index = 0; index < SIZE; index++)
		   {
		   	if (alphaCounts[index] > alphaCounts[maxIndexSoFar])      
       		maxIndexSoFar = index;
       		}
		this.maxCh = alphabet[maxIndexSoFar];
		return maxIndexSoFar;
		}
	
	
/** Returns a String consisting of the full frequency report.  
		1.	letter; 	2.	letter frequency; 	 3.	 frequency %;
		4.	average English frequency %;	5.	(3-4)
 	  * @return the report		 */
	
	public String getReport()
	{
		System.out.println();		
		System.out.println("\nCheck contents of alphaCounts array: ");			//  Trace statements for alphaCounts array.			
		for (int i = 0; i < SIZE; i++)
			System.out.print(alphabet[i] + ": " + alphaCounts[i] + "  "); 
		System.out.println("\n\n");	
		
		double[] freqPercent = new double[SIZE];								//  Array will store each letter's proportion of the file.
		double dTotChars = (double) totChars; 
		
		for (int i = 0; i < SIZE; i++)											//  Loop calculates % for each alphabetic char and stores.
			freqPercent[i] = (alphaCounts[i] / dTotChars) * 100;
		
		double maxFreqPercent = freqPercent[getMaxPC()];						//  Call to find highest freq (and set maxCh).
		
		double[] freqMinusAvg = new double[SIZE];								//  Array will store each letter's proportion of the file.
		for (int i = 0; i < SIZE; i++)											//  Loop calculates % for each alphabetic char and stores.
			freqMinusAvg[i] = (freqPercent[i] - avgCounts[i]);
		
		
		String reportFinal = String.format("LETTER ANALYSIS%n%n");				//  Complex statement, building the output string in pieces.
		reportFinal += String.format("Letter   Freq   Freq%%   AvgFreq%%   Diff%n");
		for (int i = 0; i < SIZE; i++)											//  Loop outputs all relevant values for each letter. 
		{
			reportFinal += String.format("%3c %8d %7.1f %8.1f %9.1f %n", alphabet[i], alphaCounts[i], freqPercent[i], avgCounts[i], freqMinusAvg[i]);
		}						
		reportFinal += String.format("%n%s %c %s %.1f %%", "The most frequent letter is", maxCh, "at", maxFreqPercent);
		
		return reportFinal;	
	}
}
