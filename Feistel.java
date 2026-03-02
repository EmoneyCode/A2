/*****************************************************
   CS 326 - Spring 2026 - Assignment #2

   Student's full name: _____
   Student's full name: _____
   Student's full name: _____
*****************************************************/

import java.util.Arrays;

class Feistel
{
    int w;             // the half-width of a block (in bits)
    int n;             // the number of rounds
    FeistelFunction F; // the round function
    int[][] K;         // the set of sub-keys

    /* do not modify this constructor */
    Feistel(int w, int n, FeistelFunction F, int[][] K)
    {
        this.w = w;
        this.n = n;
        this.F = F;
        this.K = K;
    }// constructor

    /* given a 2w-bit vector of plaintext, return the 2w-bit encrypted 
       input data block.
     */
    int[] encrypt(int[] block)
    {
        int[] left = Arrays.copyOfRange(block, 0, w);
        int[] right = Arrays.copyOfRange(block, w, w*2);
        int[] temp; //assists with swaps

        for(int i = 0; i < n; i++){
            temp = right;
            right = Utils.XOR(left, F.round(right, K[i + 1]));
            left = temp;
        }

        int[] ciphertext = new int[w*2];

        System.arraycopy(right, 0, ciphertext, 0, w);
        System.arraycopy(left, 0, ciphertext, w, w);

        return ciphertext; // here to please the compiler
    }// encrypt method

    /* given a 2w-bit vector of encrypted ciphertext, return the 2w-bit 
       plaintext block.
     */
    int[] decrypt(int[] block)
    {
        int[][] reversedKeys = new int[K.length][K[1].length];

        for(int i = 1; i < K.length; i++){
            reversedKeys[i] = K[K.length - i];
        }
        
        int[][] tempK = K; //saves K

        K = reversedKeys;

        int[] plaintext = encrypt(block);

        K = tempK; //Resets K
        
        return plaintext;    
    }// decrypt method

    /* I will use this driver code to test your program. Do not modify it.
    */
    public static void main(String[] args)
    {
        if (args.length != 5)
        {
            System.out.println("This program should be invoked with the " +
                               "following arguments:");
            System.out.println("  java Feistel <e or d> <w> <#rounds> " +
                               "<allzeros or allones or and> <hex block>");
            System.exit(1);
        }

        boolean encrypt = args[0].equals("e");
        int numBits = Integer.parseInt(args[1]);
        int numRounds = Integer.parseInt(args[2]);
        String roundFn = args[3];
        int[] block = Utils.binStringToIntArray(
                        Utils.hexToBinString(args[4],4*args[4].length()));      
        int[][] subkeys = new int[1+numRounds][numBits];
        Feistel cipher = null;;
        if (roundFn.equals("allzeros"))
        {
            cipher = new Feistel(numBits,numRounds, 
                                         new FeistelAllZeros(),subkeys);
        } else if (roundFn.equals("allones"))
        {
            cipher = new Feistel(numBits,numRounds, 
                                         new FeistelAllOnes(),subkeys);
        } else if (roundFn.equals("and"))
        {
            for(int round=1; round<=numRounds; round +=2)
            {
                for(int bit=0; bit<numBits; bit++)
                {
                    subkeys[round][bit] = 0;
                    if (round < numRounds)
                        subkeys[round+1][bit] = 1;                  
                }
            }
            cipher = new Feistel(numBits,numRounds,new FeistelAnd(),subkeys);
        }

        if (encrypt)
        {
            System.out.println( 
                   Utils.binStringToHex(
                         Utils.intArrayToBinString(cipher.encrypt(block)))
                               );
        } else
        {
            System.out.println( 
                   Utils.binStringToHex(
                         Utils.intArrayToBinString(cipher.decrypt(block)))
                               );
        }
    }// main method

}// Feistel class
