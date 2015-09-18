*  "Use the I/O Box LEDs to count forever through the binary sequence
*  0000 0000 --> 0000 0001 --> ... --> 1111 1111 --> 0000 0000 --> 0000 0001 ..."

*  A program that counts with 8 binary LEDs, overflowing back to '0' from '255'. It does this by 
*  the following extremely simple: increment a register, then translate the new count to LED binary in a loop.

* PROGRAM PROPER

	ORG 	$c000		; Begin machine code at EPROM start.
	LDAA	#$00		; Initialise register A to 0.

Loop	STAA 	LIGHTS 		; "Print" current number in A onto LEDs
	INCA			; Increment A.
	BRA 	Loop 		; Loop Forever


* DATA DEFINITION. No ORG needed as Assembler is not reserving data memory.

LIGHTS 	 EQU 	$1004 		; Label for the Port B data register.
	 
	 END
