* 	ASSESSED EXERCISE 2, part (d). 

* d)	"Using an interrupt routine, adapt the program in (c) so that when the STRA switch is
*	thrown from up to down (1 to 0), the count goes into reverse and a message is printed
*	out on the I/O box screen, saying 'Count reversed by user'. This message should
*	display for a few seconds and then clear. Every time STRA is cycled (switched up,
*	then down), the count should reverse again with the same message. "

* A program that counts in binary lights, reversible with a switch and showing a timed help message.


* Algorithm:
*  1. Start increasing lights loop from (c).
*  2. Build interrupt routine that reverses the count by
*  3. choosing between two futher subroutines
* 	a. One that switches from incr to decr
*	b. One that switches from decr to incr
*  4. The IRQ then calls a further subroutine to print and hold an ASCII message.

* Register use.
* A: Stores current count to display on lights.
* B: Stores count direction (increasing or decreasing).
* X: Times pause lengths or message printing.
* Y: Times pause iterations.

* 	DATA DEFINITION
	
	ORG	$0000		; For reserving Message string space, start at RAM bottom.
Stack	EQU	$00ff		; Bottom of Stack
Irq	EQU	$fff2		; IRQ vector.
Lights 	EQU 	$1004 		; Label for the Port B data register.
Switch	EQU	$1005		; Label for the Port C-Latched data register. Up = 1.
Screen	EQU	$1040		; Data register for LCD display.
PIOC	EQU	$1002		; Parallel I/O Control Register

Message FCC	"Count reversed by user" 	; The array to be printed.
Sign 	RMB	1		; Variable to hold direction of counting.
Length	EQU	22		; Message length, plus one.

StrtNOP	EQU	$0080		; 1st address for controlling interrupt delay.
LastNOP	EQU	$0700		; Last address for controlling interrupt delay (c. 1,700 cycles later).


* 	PROGRAM PROPER
 	
* step (0) - initialise 
	ORG	$c000		; Start machine code at EPROM bottom.
 	LDAB	#$01		; Initialise count direction (in register B) to 1: increasing.	
	STAB	Sign		; Continuity across interrupts.

Start	LDS	#Stack		; initialise stack at $00ff (lots of room to grow down).
	LDAA	#%01000000	; This config string enables a Strobe A interrupt on falling edge.
	STAA	PIOC		; This stores the config to the control register.
	CLI			; enable IRQ
	LDAA	#$00		; Then initialise register A to 0.	


* step (1) - Loop with constant comparisons and inner Pause loop
Loop	STAA 	Lights 		; "Print" current number in A onto LEDs.
	LDAB	Sign		; Load B with Sign
 	CMPB	#1		; Compare B to 1.
 	BNE	Decre		; If B(1), branch to increase.
 	JMP	Incre		; Otherwise branch to decrease
	
Incre	INCA			; Increment A.
 	LDX 	#StrtNOP 	; Set index register (X) to start of address block.	
 	JMP	Pause		; Jump to wait loop.

Decre	DECA			; Decrement A.
 	LDX 	#StrtNOP 	; Set index register (X) to start of address block.	
 	JMP	Pause		; Jump to wait loop.
	
Pause	NOP			; Pause for one cycle.
 	INX			; Increment the X register.
	CPX 	#LastNOP 	; Have we reached the last address yet?
 	BNE 	Pause 		; If no, branch back to Pause.
	BRA	Loop		; If yes, return to main Loop start.



* 	INTERRUPT SERVICE ROUTINE (IRQ)
				; When Switch ($1005) = 0, Interrupt.
StIRQ	LDAA	PIOC		; These two lines reset STAF.
	LDAA	Switch		
	CMPB	#0		; Is the light-count currently increasing?
	BNE	NowDec		; If so, branch to decreasing (B(0)).
NowInc	LDAB	#1		; Count will now increase.
	JMP	Return		; Skip next line if B should be 1.
NowDec	LDAB	#0		; Count will now decrease.

Return	STAB	Sign		; Store B in the variable Sign ($0016)


; Now for the message printer.

Print	LDAA	#Length		; Initialise A to message length.
	LDY	#Message	; Load whole mesaage into Y.
BeginP	LDAB	0,Y		; Load next char of message into A.
	STAB	Screen		; Print that char.
	INY			; Increment Y.
	DECA			; Decrement A.
				; Has the message finished printing?
	BNE	BeginP		; If no, print next.

; A Delayer
	LDY	#0		; Otherwise, initialise Y to 0.
Leave	INY			; Wait for "a few" seconds. 
	LDX 	#StrtNOP 	; Set index register (X) to start of address block.	
HoldIt	INX			; Pause loop begins. Increment the X register.
	CPX 	#LastNOP 	; Have we reached the last address yet?
 	BNE 	HoldIt 		; If no, branch back to Pause.
	
	CPY	#4		; If Y is 4, break Leave loop.
	BNE	Leave		; Otherwise another pause loop:

	CLRA			; Clear display.
	STAA	Screen

	RTI			; Return to main loop.

* Vector
 	ORG	Irq		; Set IRQ vector.
 	FDB	StIRQ		; initialise to first line, labelled StTRQ.

	END

