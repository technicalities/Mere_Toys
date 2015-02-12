/**  Programming AE2
 *   This is a model class that contains the Vigenere cipher and methods to encode and decode a character. 
 *    */

public class VCipher
{
	private final int SIZE = 26;						//  Constant: size of alphabet. 
	private char [] alphabet = new char [SIZE];			//  The alphabet array. 
	private char[][] vCipher;
	
/** The constructor generates the cipher
	 * @param keyword the cipher keyword	*/
	
	public VCipher(String key)
	{		
			vCipher = new char [key.length()][SIZE];	//  The 2D array of cipher arrays. 
		
			for (int i = 0; i < SIZE; i++)					//  Loop creates alphabet as array of 26 distinct elements
				alphabet[i] = (char) ('A'+ i);	
		
//  Create first column of cipher from the keyword:
			for (int i = 0; i < key.length(); i++)
			{
				vCipher[i][0] = key.charAt(i);
				System.out.println("@i: " + vCipher[i][0]);
			}
				System.out.println();
				
//  Create rest of array by taking the character at col 0, traversing on from there.	
			for (int rowIndex = 0; rowIndex < key.length(); rowIndex++)
			{
				int indexOfLast = 0;
				
				for (int colIndex = 1; colIndex < SIZE; colIndex++)	
				{ 
					if (vCipher[rowIndex][colIndex - 1] == 'Z')
						vCipher[rowIndex][colIndex] = alphabet[0];
					
					else
					{
 					for (int i = 0; i < SIZE; i++)
						{	
						if (vCipher[rowIndex][colIndex - 1] == alphabet[i])
							indexOfLast = i;
						}
 					vCipher[rowIndex][colIndex] = alphabet[(indexOfLast) + 1];
					}
				}
			}

			System.out.println("Check result of Vigen cipher: ");			//  Trace statement printing the final cipher.
			for (int i = 0; i < 10; i++)	
				System.out.print(i + "  ");
			for (int i = 10; i < SIZE; i++)	
				System.out.print(i + " ");
				System.out.println();
			
			for (int i = 0; i < key.length(); i++)
				{
				for (int j = 0; j < SIZE; j++)
					{	System.out.print(vCipher[i][j] + "  ");	}
				System.out.println();
				}
		} //  End of constructor.	

	
/**	The method to return an encoded letter from a given character parameter.  
 * 	The first character to be encrypted is coded with reference to the first encryption array, 
	the second using the second array, etc.  
 * 
	 * 	@param ch the character to be encoded; @return the encoded character 		 */
	public char encode(char ch, int whichRow)
	{
		for (int j = 0; j < SIZE; j++)
		{	
			if (ch == alphabet[j])
				{
				ch = (vCipher[whichRow][j]);
				return ch;
				}
		}
		return ch;
	}
	

/** Decode a character.	
 	Decryption uses a similar idea: the first character is decoded with reference to the first encryption array, 
 	and so on, cycling back to the first array when the (n+1)th character is reached.
 	
	* 	@param ch the character to be encoded; @return the decoded character 		*/
	public char decode(char ch, int whichRow)
	{
		for (int i = 0; i < SIZE; i++)
		{
			if (ch == vCipher[whichRow][i])
				return alphabet[i];
		}   
		return ch;
	}
}


