GUI for calculating primes in the least efficient way: 'trial division'. 
(Time complexity is n^n.)


In pseudocode:

1. 
2. 
3. 
4. 
5. 

****************************************************************************

In Java:

if (numToCheck<=1) 
  return false; 
else 
     for (int i = 2; i <= numToCheck/2; i++) 
     {
       if (numToCheck % i == 0) 
          return false;
     }
return true;

****************************************************************************