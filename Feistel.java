
/*****************************************************
   CS 326 - Spring 2026 - Assignment #2

   Student's full name: _____
   Student's full name: _____
   Student's full name: _____
*****************************************************/

import java.util.Arrays;

class Feistel {
    int w; // the half-width of a block (in bits)
    int n; // the number of rounds
    FeistelFunction F; // the round function
    int[][] K; // the set of sub-keys

    /* do not modify this constructor */
    Feistel(int w, int n, FeistelFunction F, int[][] K) {
        this.w = w;
        this.n = n;
        this.F = F;
        this.K = K;
    }// constructor

    /*
     * given a 2w-bit vector of plaintext, return the 2w-bit encrypted
     * input data block.
     */
    int[] encrypt(int[] block)
    {
        for(int i = 0; i<n; i++){
            int[] l = Arrays.copyOfRange(block, 0, w);
            int[] r = Arrays.copyOfRange(block, w, 2*w);
            int[] fOut = F.round(r,K[i]);
            int[] newR = Utils.XOR(l, fOut);
            block = combine(r, newR);
        }

        return block; // here to please the compiler
    }// encrypt method

    private int[] combine(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];

        System.arraycopy(left, 0, result, 0, left.length);
        System.arraycopy(right, 0, result, left.length, right.length);

        return result;
    }

    /*
     * given a 2w-bit vector of encrypted ciphertext, return the 2w-bit
     * plaintext block.
     */
    int[] decrypt(int[] block) {
        for(int i = n-1; i>=0; i--){
            int[] l = Arrays.copyOfRange(block, 0, w);
            int[] r = Arrays.copyOfRange(block, w, 2*w);
            int[] fOut = F.round(r,K[i]);
            int[] newR = Utils.XOR(l, fOut);
            block = combine(r, newR);
        }

        return block; // here to please the compiler
    }// decrypt method

    /*
     * I will use this driver code to test your program. Do not modify it.
     */
    public static void main(String[] args) {
        if (args.length != 5) {
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
                Utils.hexToBinString(args[4], 4 * args[4].length()));
        int[][] subkeys = new int[1 + numRounds][numBits];
        Feistel cipher = null;
        ;
        if (roundFn.equals("allzeros")) {
            cipher = new Feistel(numBits, numRounds,
                    new FeistelAllZeros(), subkeys);
        } else if (roundFn.equals("allones")) {
            cipher = new Feistel(numBits, numRounds,
                    new FeistelAllOnes(), subkeys);
        } else if (roundFn.equals("and")) {
            for (int round = 1; round <= numRounds; round += 2) {
                for (int bit = 0; bit < numBits; bit++) {
                    subkeys[round][bit] = 0;
                    if (round < numRounds)
                        subkeys[round + 1][bit] = 1;
                }
            }
            cipher = new Feistel(numBits, numRounds, new FeistelAnd(), subkeys);
        }

        if (encrypt) {
            System.out.println(
                    Utils.binStringToHex(
                            Utils.intArrayToBinString(cipher.encrypt(block))));
        } else {
            System.out.println(
                    Utils.binStringToHex(
                            Utils.intArrayToBinString(cipher.decrypt(block))));
        }
    }// main method

}// Feistel class
