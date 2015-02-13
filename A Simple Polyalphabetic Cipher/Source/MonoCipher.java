/**
 * Programming: AE2
 * 
 * This is a model class that contains the monoalphabetic cipher and methods to encode and decode a character.
 * 
 * The plaintext file (P) is the original English file before encryption, 
 * the cipher text file (C) is the encrypted file and 
 * the deciphered file (D) is the result of decrypting the cipher text file.   */


public class MonoCipher
{
	private final int SIZE = 26;						// Constant: size of alphabet. 
	private char [] alphabet = new char [SIZE];			// The alphabet array. 
	private char [] cipher = new char [SIZE];			// The cipher array. 

	
/**	Constructor using the keyword. Instantiates a new mono cipher.
	 * 	@param keyword the cipher keyword 	
**/
	public MonoCipher(String key)
	{					
		for (int i = 0; i < SIZE; i++)				//  Loop creates alphabet as array of 26 distinct elements
			alphabet[i] = (char) ('A'+ i);
		
		char [] backabet = new char [SIZE];				//  Loop creates backwards alphabet as array.
			for (int i = 0; i < SIZE; i++)
				backabet[i] = (char) ('Z'- i);
		
//  Create first part of cipher from the keyword.
		System.out.println();
		for (int i = 0; i < key.length(); i++)
		{
			cipher[i] = key.charAt(i);					//  Fills first i elements with keyword's characters.
		} 			

//  Record keyword characters in boolean array.	
		boolean [] contains = new boolean [SIZE];						
		for (int j = 0; j < SIZE; j++)					//  for each of the whole backabet, do the following once
		{
			for (int k = 0; k < key.length(); k++) 		//  for each character of the key, check if backabet[j] is there.
				{
					if (cipher[k] == backabet[j]) 					
						contains[j] = true;
				}		    	
		}	
		
//  Create remainder of cipher from the remaining characters of the (backwards) alphabet.
	int j = key.length();	
	for (int i = 0; i < SIZE; i++)
	{
		if (contains[i] == false)
		{
			cipher[j] = backabet[i];
			j++;
		}	
	}
		
// Prints whole cipher.
		System.out.println("Mono cipher array check ([cipher] is [alphabet]:");
		for (int m = 0; m < SIZE; m++)	
			System.out.println(cipher[m] + " is " + alphabet[m] + ".   ");		
		System.out.println();
	}
//  Constructor end.	

	
/**	The method to return an encoded letter from a given character parameter.  
	 * 	@param ch the character to be encoded; @return the encoded character 		 */
	public char encode(char ch)
	{
		for (int i = 0; i < SIZE; i++)
		{
			if (ch == alphabet[i])
				{
				ch = cipher[i];
				return ch;
				}
		}  
		return ch;
	}

/** Decode a character
	 * 	@param ch the character to be encoded; @return the decoded character 		*/
	public char decode(char ch)
	{
		
		for (int i = 0; i < SIZE; i++)
		{
			if (ch == cipher[i])
				return alphabet[i];
		}    
		return ch;
	}

	

	
}	