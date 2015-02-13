GUI for calculating primes in the least efficient way: 'trial division'. 
(Time complexity is n^n.)


****************************************************************************

In pseudocode:

+ candidate = number that might be prime
+ i = index. Initial value 2.


1. If candidate is less than 2, terminate: not prime.
2. Else while i is less than (candidate / 2):
   2.1. If the modulus of candidate % i is 0 
   2.2. Terminate; candidate is not prime.
3. Else terminate; candidate is prime.


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