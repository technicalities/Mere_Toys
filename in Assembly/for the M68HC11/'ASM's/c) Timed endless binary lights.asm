*  "Use a delay loop implemented as a subroutine to pace the count sequence from (b) so that the numbers change 
*  approximately once per second for the RUN command. The accuracy here does not have to be 
*  great but each individual light pattern should remain long enough to be clearly visible to an observer."

*  A program that slowly cycles 8 LEDs through binary integers, about one increment per second. 
*  It does this by the following algorithm:

*  1. In Loop, jump after each new number display.
*  2. In another loop ('Pause'), idle for 1 second of cycles.
*  3. Jump back to Loop.
*  4. Repeat indefinitely.
	

* 	DATA DEFINITION
	
	ORG	$0000		; If memory is to be reserved, start at RAM bottom.
PROG	EQU	$c000		; Start machine code at EPROM bottom.
LIGHTS 	EQU 	$1004 		; Label for the Port B data register.

StrtNOP	EQU	$0080		; 1st address for controlling interrupt delay.
LastNOP	EQU	$0800		; Last address for controlling interrupt delay (c.2,000 cycles later).


* 	PROGRAM PROPER

* step (0) - initialise 
	LDAA	#$00		; Initialise register A to 0.	

* step (1) - Loop
Loop	STAA 	LIGHTS 		; "Print" current number in A onto LEDs.
	INCA			; Increment A.
	JMP	SubStrt
 	

* step (2) - Pause loop
SubStrt	LDX 	#StrtNOP 	; Set index register (X) to start of address block.
Pause	NOP			; Pause for one cycle.
 	INX			; Increment the X register.
	CPX 	#LastNOP 	; Have we reached the last address yet?
 	BNE 	Pause 		; If no, branch back to Pause.
	JMP	Loop		; If yes, return to main loop.

* step (3) - return


