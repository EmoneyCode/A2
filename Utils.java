import java.util.Arrays;

/*****************************************************
   CS 326 - Spring 2026 - Assignment #2

   Student's full name: _____
   Student's full name: _____
   Student's full name: _____
*****************************************************/

class Utils
{

    /* given a character string, return the sequence of ASCII codes (in 
       hexadecimal) for the characters in the string. Sample input/output:
          input: "ABC"       output: "414243"
          input: "\nA\nB\n"  output: "0A410A420A"
       Note that each input character always yields exactly two hex digits.
     */
    static String textToHex(String s)
    {
        String hexText = "";
        for(int i = 0; i < s.length(); i++){
            int intVal = (int) s.charAt(i);
            hexText += Integer.toHexString(intVal);
        }
        return hexText;
        
    }// textToHex method

    /* given a string of ascii codes (in hexadecimal), return the string of
       the corresponding characters.
          input: "414243"      output: "ABC" 
          input: "0A410A420A"  output: "\nA\nB\n"
       Note that all input strings have an even length.
     */
    static String hexToText(String s)
    {
        String text = "";
        int count = 0;
        while(count < s.length()){
            String hex = "";
            hex += s.charAt(count);
            hex += s.charAt(count + 1);
            text += (char) Integer.parseInt(hex, 16);
            count +=2;
        }

        return text;
    }// hexTotext method

    /* given a binary string, return the integer array of the same length as
       the bit string and in which each element is the integer value of the
       bit in the corresponding position in the string. Sample input/output:
           input: "01101"           output: [0, 1, 1, 0, 1]
    */
    static int[] binStringToIntArray(String bits)
    {
        int[] arr = new int[bits.length()];

        for(int i = 0; i < bits.length(); i++){

            arr[i] = Character.getNumericValue(bits.charAt(i));

        }
        
        return arr;
    }// bitStringToIntArray method

    /* given an integer array containing 0s and 1s exclusively, return
       the binary string of the same length in which each element is the 
       character ('0' or '1') of the corresponding element in the input array.
           input: [0, 1, 1, 0, 1]      output: "01101"
    */
    static String intArrayToBinString(int[] data)
    {
        String bits = "";

        for(int i = 0; i < data.length; i++){

            bits += data[i];

        }

        return bits; // only here to please the compiler
    }//intArrayToBinString method
    
    /* given an arbitrary long string of hexadecimal digits and a number 
       of bits, return the binary string of the given length corresponding
       to the first input. Sample input/output:
           input: "ABC" 16     output: "0000101010111100"
           input: "01F3" 16    output: "0000000111110011"
       Note: You must assume that numBits is always larger than or equal to
       4 times the number of hexadecimal digits in the first argument.
     */
    static String hexToBinString(String s, int numBits)
    {
        int integer = Integer.parseInt(s, 16);
        String bits = Integer.toBinaryString(integer);
        String leadingZeros = "";

        for(int i = 0; i < numBits - bits.length(); i++){

            leadingZeros += '0';

        }
        

        return leadingZeros + bits;
    }// hexToBinString method

    /* given a binary string, return the hexadecimal representation of the
       input as a String. Sample input/output:
           input: "01101110"           output: "6E"
       Note: you must assume that the length of the input is a multiple of 4.
    */
    static String binStringToHex(String bits)
    {
        String hex = "";

        for(int i = 0; i < bits.length(); i += 4){

            int interger = Integer.parseInt(bits.substring(i, i + 4), 2);

            hex += Integer.toHexString(interger);

        }
        

        return hex; // only here to please the compiler
    }// binStringToHex method

    
    /* given two arrays of the same size each containing n integer values
       equal to 0 or 1 exclusively, return an n-element array containing the
       bitwise XOR of the pairs of input bits. Sample input/output:
          input: [0, 0, 1, 1] and [0, 1, 0, 1]   output: [0, 1, 1, 0]
     */
    static int[] XOR(int[] a, int[] b)
    {
        int[] c = new int[a.length];
        for(int i = 0; i < a.length; i++){
            if(a[i] + b[i] == 1){
                c[i] = 1;
            } else {
                c[i] = 0;
            }
        }
        
        return c;
    }// XOR method

    /* given an n-long permutation of bit positions ranging from 1 to m  and 
       an m-bit vector, return the n-bit vector resulting from applying the 
       permutation to the second vector. Sample input/output:
         input: [1, 1, 2, 1, 1, 2, 2] and [0, 1]
         output:  [0, 0, 1, 0, 0, 1, 1]
      Note that the values in the permutation are position indexes
      starting at 1, not 0. Therefore, the value of the bit at position 1 in 
      the second argument is 0, not 1.
     */
    static int[] applyPermut(int[] perm, int[] data)
    {
        for(int i = 0; i < perm.length; i++){
            perm[i] = data[perm[i] - 1];
        }
        return perm;
    }// applyPermut method

}// class Utils
