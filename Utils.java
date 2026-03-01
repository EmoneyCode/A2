/*****************************************************
 * CS 326 - Spring 2026 - Assignment #2
 * 
 * Student's full name: _____
 * Student's full name: _____
 * Student's full name: _____
 *****************************************************/

class Utils {

    /*
     * given a character string, return the sequence of ASCII codes (in
     * hexadecimal) for the characters in the string. Sample input/output:
     * input: "ABC" output: "414243"
     * input: "\nA\nB\n" output: "0A410A420A"
     * Note that each input character always yields exactly two hex digits.
     */
    static String textToHex(String s) {
        byte[] bytes = s.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        String hex = "";
        for (int i = 0; i < bytes.length; i++) {
            hex = hex + String.format("%02x", bytes[i]);
        }
        return hex; // only here to please the compiler

    }// textToHex method

    /*
     * given a string of ascii codes (in hexadecimal), return the string of
     * the corresponding characters.
     * input: "414243" output: "ABC"
     * input: "0A410A420A" output: "\nA\nB\n"
     * Note that all input strings have an even length.
     */
    static String hexToText(String s) {
        int length = s.length();
        byte[] bytes = new byte[length / 2]; // each pair of hex chars = 1 byte

        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }

        return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
    }// hexTotext method

    /*
     * given a binary string, return the integer array of the same length as
     * the bit string and in which each element is the integer value of the
     * bit in the corresponding position in the string. Sample input/output:
     * input: "01101" output: [0, 1, 1, 0, 1]
     */
    static int[] binStringToIntArray(String bits) {
        int[] array = new int[bits.length()];
        for (int i = 0; i < bits.length(); i++) {
            array[i] = bits.charAt(i) - '0';
        }

        return array; // only here to please the compiler
    }// bitStringToIntArray method

    /*
     * given an integer array containing 0s and 1s exclusively, return
     * the binary string of the same length in which each element is the
     * character ('0' or '1') of the corresponding element in the input array.
     * input: [0, 1, 1, 0, 1] output: "01101"
     */
    static String intArrayToBinString(int[] data) {
        String string = "";
        for (int i = 0; i < data.length; i++) {
            string = string + data[i];
        }

        return string; // only here to please the compiler
    }// intArrayToBinString method

    /*
     * given an arbitrary long string of hexadecimal digits and a number
     * of bits, return the binary string of the given length corresponding
     * to the first input. Sample input/output:
     * input: "ABC" 16 output: "0000101010111100"
     * input: "01F3" 16 output: "0000000111110011"
     * Note: You must assume that numBits is always larger than or equal to
     * 4 times the number of hexadecimal digits in the first argument.
     */
    static String hexToBinString(String s, int numBits) {
        StringBuilder binary = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            int value = Character.digit(s.charAt(i), 16); // convert hex char to int
            String binChunk = String.format("%4s", Integer.toBinaryString(value))
                    .replace(' ', '0'); // pad to 4 bits
            binary.append(binChunk);
        }

        // Left pad to reach numBits
        while (binary.length() < numBits) {
            binary.insert(0, "0");
        }

        return binary.toString();
    }// hexToBinString method

    /*
     * given a binary string, return the hexadecimal representation of the
     * input as a String. Sample input/output:
     * input: "01101110" output: "6E"
     * Note: you must assume that the length of the input is a multiple of 4.
     */
    static String binStringToHex(String bits) {
        StringBuilder hex = new StringBuilder();

        for (int i = 0; i < bits.length(); i += 4) {
            String chunk = bits.substring(i, i + 4);
            int value = Integer.parseInt(chunk, 2); // binary → int
            hex.append(Integer.toHexString(value).toUpperCase());
        }

        return hex.toString();
    }// binStringToHex method

    /*
     * given two arrays of the same size each containing n integer values
     * equal to 0 or 1 exclusively, return an n-element array containing the
     * bitwise XOR of the pairs of input bits. Sample input/output:
     * input: [0, 0, 1, 1] and [0, 1, 0, 1] output: [0, 1, 1, 0]
     */
    static int[] XOR(int[] a, int[] b) {
        int[] output = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            output[i] = a[i] ^ b[i];
        }

        return output; // only here to please the compiler
    }// XOR method

    /*
     * given an n-long permutation of bit positions ranging from 1 to m and
     * an m-bit vector, return the n-bit vector resulting from applying the
     * permutation to the second vector. Sample input/output:
     * input: [1, 1, 2, 1, 1, 2, 2] and [0, 1]
     * output: [0, 0, 1, 0, 0, 1, 1]
     * Note that the values in the permutation are position indexes
     * starting at 1, not 0. Therefore, the value of the bit at position 1 in
     * the second argument is 0, not 1.
     */
    static int[] applyPermut(int[] perm, int[] data) {
        int[] result = new int[perm.length];

        for (int i = 0; i < perm.length; i++) {
            result[i] = data[perm[i] - 1]; // subtract 1 for 0-based indexing
        }

        return result;
    }// applyPermut method

    public static void main(String[] args) {
        int[] output = applyPermut(new int[] {1, 1, 2, 1, 1, 2, 2}, new int[] {0, 1});
        for(int i = 0; i<output.length; i++){
            System.out.print(output[i]);
        }
    }

}// class Utils
