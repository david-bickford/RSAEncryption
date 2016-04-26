# RSAEncryption
Fifth project for CS1501.


#CS/COE 1501 Assignment 5

Posted:  Apr 14, 2016

***Due:  Apr 24, 2015 @ 11:59PM***

##Goal:
To get hands on experience with the use of digital signatures.

Note that the result of this project should NEVER be used for any security applications.  It is purely instructive.  Always use trusted and tested crypto libraries.

##High-level description:
You will be writing two programs.  The first will generate a 1024 bit RSA keypair and store the public and private keys in files named pubkey.rsa and privkey.rsa respectively.
The second will generate and verify digital signatures using a SHA-256 hash.  You will need to use Java's BigInteger (http://docs.oracle.com/javase/7/docs/api/java/math/BigInteger.html) and MessageDigest (https://docs.oracle.com/javase/7/docs/api/java/security/MessageDigest.html) classes to complete this project.

##Specifications:
1.  Write a program named MyKeyGen to generate a new keypair
	1.  To generate a keypair, you will need to follow these steps as described in lecture:
		1.  Pick P and Q to be random primes of an appropriate size to generate a 1024 bit key
		1.  Generate N as P * Q
		1.  Generate PHI(N) as (P-1) * (Q-1)
		1.  Pick E such that 1 < E < PHI(N) and GCD(E, PHI(N))=1 (E must not divide PHI(N) evenly)
		1.  Pick D such that D = E<sup>-1</sup> mod PHI(N)
	1.  After generating E, D, and N, save E and N to pubkey.rsa and D and N to privkey.rsa
1.  Write a second program named MySign to sign files and verify signatures.  This program should take in two command line arguments, a flag to specify whether it should be signing or verifying ("s" or "v"), and the file that should be signed or verified.
	1.  If called to sign (e.g., "java MySign s myfile.txt") your program should:
		1.  Generate a SHA-256 hash of the contents of the provided file (e.g., "myfile.txt")
		1.  "decrypt" this hash value using the private key stored in privkey.rsa (i.e., raise it to the D<sup>th</sup> power mod N)
			*  Your program should exit and display an error if privkey.rsa is not found in the current directory
		1.  Write out a signed version of the file (e.g., "myfile.txt.signed") that contains:
			*  The contents of the original file
			*  The "decrypted" hash of the original file
	1.  If called to verify (e.g., "java MySign v myfile.txt.signed") your program should:
		1.  Read the contents of the original file
		1.  Generate a SHA-256 hash of the contents of the orignal file
		1.  Read the "decrypted" hash of the original file
		1.  "encrypt" this value with the contents of pubkey.rsa (i.e., raise it to the E<sup>th</sup> power mod N)
			*  Your program should exit and display an error if pubkey.rsa is not found in the current directory
		1.  Compare these two hash values (the on newly generated and the one that you just "encrypted") and print out to the console whether or not the signature is valid (i.e., whether or not the values are the same).
