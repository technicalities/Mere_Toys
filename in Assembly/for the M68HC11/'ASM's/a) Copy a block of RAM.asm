*  "Copy a block of 16 bytes of RAM from $0080 to $B680."
*  A program that duplicates an area of RAM from a hard-coded index. It does this by 
*  the following:

*  1. Starting at $0080, fill 16 contiguous locations with arbitrary values (say the integers 1 - 16). Call this the input block.
*  2. Starting at $B680, load and copy the values above to 16 new contiguous locations. Call this the output block.
*  3. Test.

* 	DATA DEFINITION
* The values written into RAM are hardcoded below as 1-16..

SrtIn	EQU	$0080		; 1st address of input block.
EndIn	EQU	$0090		; Last address of input block, plus one (break condition is 'when less than this').

SrtOut	EQU	$B680		; 1st address of copied output block.
EndOut	EQU	$B690		; Last address of copied output block.


* 	PROGRAM PROPER
 	ORG	$c000		; Sets machine code start at first EPROM address.

* step (1) - initialise a block
  	LDX 	#SrtIn	 	; Set index register (X) to start of input block.
 	LDAA	#$01		; Initialise A with value 1.

Loop 	STAA 	0,X		; Store current value of A register into address X.
  	INX 			; Increment the index register.	
 	INCA			; Increment the A register (value to write next).	
 	CPX 	#EndIn	 	; Have we reached the last address yet?
 	BNE 	Loop 		; If no, branch back to Loop.
				; If yes, move on to copy loop.

* step (2) - copy the block
	LDX 	#SrtIn	 	; Set index register (X) to start of input block.
 	LDY 	#SrtOut	 	; Set index register (Y) to start of output block.

LoopC 	LDAA 	0,X 		; Load value at address X into register A.
	STAA	0,Y		; Store value in A into address Y.
 	INX 			; Increment index register X.
 	INY 			; Increment index register Y.
 	CPY 	#EndOut 	; Have we reached the last address yet?
 	BNE 	LoopC 		; If no, branch to start
				; If yes, break and end.

 	WAI			; Pause until a hardware interrupt.

	END
