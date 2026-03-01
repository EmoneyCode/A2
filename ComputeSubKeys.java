/*****************************************************
 * CS 326 - Spring 2026 - Assignment #2
 * 
 * Student's full name: _____
 * Student's full name: _____
 * Student's full name: _____
 *****************************************************/

class ComputeSubKeys {
    // the "Permuted Choice 1" permutation for the left (C) half-block
    static int[] PC1c = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36
    };

    // the "Permuted Choice 1" permutation for the right (D) half-block
    static int[] PC1d = {
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };

    // the "Permuted Choice 2" permutation
    static int[] PC2 = {
            14, 17, 11, 24, 1, 5, 3, 28,
            15, 6, 21, 10, 23, 19, 12, 4,
            26, 8, 16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56,
            34, 53, 46, 42, 50, 36, 29, 32
    };

    /*
     * return the bit vector obtained by applying a cyclic rotation
     * to the left by one bit to the bit vector given as input. Sample
     * input/output:
     * input: [0, 0, 0, 1, 1] output: [0, 0, 1, 1, 0]
     * You must assume that the length of the input vector is >= 1.
     */
    static int[] shiftLeftBy1(int[] data) {
        int left = data[0];
        for (int i = 0; i < data.length - 1; i++) {
            data[i] = data[i + 1];
        }
        data[data.length - 1] = left;
        return data;
    }// shiftLeftBy1 method

    /*
     * return the bit vector obtained by applying a cyclic rotation
     * to the left by two bits to the bit vector given as input. Sample
     * input/output:
     * input: [0, 0, 0, 1, 1] output: [0, 1, 1, 0, 0]
     * You can assume that the length of the input vector is >= 2.
     */
    static int[] shiftLeftBy2(int[] data) {
        int left1 = data[0];
        int left2 = data[1];
        for (int i = 0; i < data.length - 2; i++) {
            data[i] = data[i + 2];
        }
        data[data.length - 1] = left2;
        data[data.length - 2] = left1;
        return data;
    }// shiftLeftBy2 method

    /*
     * sends to the output stream a Java declaration for the 17x48 2D array
     * in which the first row is empty and each subsequent row contains a round
     * subkey represented by the sequence of 48 bit positions in the DES key
     * where the bits of this subkey are to be found.
     * 
     * This method implements the key schedule algorithm that takes in a
     * 64-bit key and outputs an array of 16 48-bit subkeys in which each bit
     * is one of the bits in the DES key. Recall that bit b in sub-key k
     * changes with the DES key but is ALWAYS obtained by copying a
     * bit at a fixed position in the DES key. Therefore, instead
     * of taking in a DES key and outputting a set of specific sub-keys for
     * that DES key, this method returns the array of *positions* at which
     * each bit in each sub-key will be found once (later) a DES key is
     * provided. This is why this method does not take the DES key
     * as input. Note that, in the DES standard and thus also in your
     * implementation, the bit positions in the key schedule are numbered
     * starting at 1, not 0. In other words, the leftmost (most significant)
     * bit of the key is at position 1.
     * 
     * The output of this method (in the console window, not as a returned
     * value) is thus an array of 16 position vectors, each of which is
     * 48 long. Actually, since we want to refer to each round through an
     * integer ranging from 1 to 16, the output is an array of 17 position
     * vectors, the first one of which (index 0) is left empty.
     * 
     * Finally, since the output of this method will later be used by the DES
     * algorithm to compute the actual sub-keys, the format of the output must
     * be a valid 2D Java array declaration/initialization that you will
     * manually copy into another Java class. Here is the required format of
     * the output of this method:
     * 
     * int[][] ks = {
     * {}, // unused
     * {10,51, ... }, // round 1
     * {2,43, ... }, // round 2
     * { ... },
     * { ... ,63,39} // round 16
     * };
     */
    static void outputHardwiredSchedule() {

        int[] shifts = { 0, 1, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1, 2 };

        // initial C and D from PC1
        int[] C = PC1c.clone();
        int[] D = PC1d.clone();

        // initialize 17x48 array (index 0 unused)
        int[][] ks = new int[17][48];

        for (int round = 1; round <= 16; round++) {
            int shift = shifts[round];

            if (shift == 1) {
                C = shiftLeftBy1(C);
                D = shiftLeftBy1(D);
            } else if (shift == 2) {
                C = shiftLeftBy2(C);
                D = shiftLeftBy2(D);
            }

            // combine C and D and apply PC2
            int[] CD = new int[56];
            System.arraycopy(C, 0, CD, 0, 28);
            System.arraycopy(D, 0, CD, 28, 28);

            int[] subkey = new int[48];
            for (int i = 0; i < 48; i++) {
                subkey[i] = CD[PC2[i] - 1]; // DES positions start at 1
            }

            ks[round] = subkey;
        }

        // print array in Java declaration format
        System.out.println("int[][] ks = {");
        System.out.println("    {}, // unused");
        for (int round = 1; round <= 16; round++) {
            System.out.print("    {");
            for (int i = 0; i < 48; i++) {
                System.out.print(ks[round][i]);
                if (i < 47)
                    System.out.print(",");
            }
            System.out.println("}" + (round < 16 ? "," : ""));
        }
        System.out.println("};");

    }// outputHardwiredSchedule method

    public static void main(String[] args) {
        // Run the method and visually inspect output format
        outputHardwiredSchedule();

        // --- Structural correctness tests ---

        // Rebuild ks internally to test (mirrors the logic above)
        int[] shifts = { 0, 1, 1, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1, 2 };
        int[] C = PC1c.clone();
        int[] D = PC1d.clone();
        int[][] ks = new int[17][48];

        for (int round = 1; round <= 16; round++) {
            int shift = shifts[round];
            if (shift == 1) {
                C = shiftLeftBy1(C);
                D = shiftLeftBy1(D);
            } else {
                C = shiftLeftBy2(C);
                D = shiftLeftBy2(D);
            }

            int[] CD = new int[56];
            System.arraycopy(C, 0, CD, 0, 28);
            System.arraycopy(D, 0, CD, 28, 28);

            for (int i = 0; i < 48; i++)
                ks[round][i] = CD[PC2[i] - 1];
        }

        // Test 1: every subkey has exactly 48 entries
        for (int r = 1; r <= 16; r++) {
            assert ks[r].length == 48 : "Round " + r + " subkey length != 48";
        }
        System.out.println("Test 1 passed: all subkeys have length 48");

        // Test 2: all bit positions are in range [1, 64]
        for (int r = 1; r <= 16; r++) {
            for (int i = 0; i < 48; i++) {
                assert ks[r][i] >= 1 && ks[r][i] <= 64
                        : "Out-of-range position at round " + r + " index " + i + ": " + ks[r][i];
            }
        }
        System.out.println("Test 2 passed: all bit positions are in range [1, 64]");

        // Test 3: no two entries within the same subkey are identical
        // (each subkey selects 48 *distinct* bits from the 56-bit CD)
        for (int r = 1; r <= 16; r++) {
            java.util.Set<Integer> seen = new java.util.HashSet<>();
            for (int i = 0; i < 48; i++) {
                assert seen.add(ks[r][i])
                        : "Duplicate position " + ks[r][i] + " in round " + r;
            }
        }
        System.out.println("Test 3 passed: no duplicate positions within any subkey");

        // Test 4: known DES standard values for round 1
        // Per the DES standard, the first few entries of subkey 1 should be:
        // positions 10, 51, 34, 60, 49, 17, 33, 57 (first 8 of 48)
        int[] expectedRound1Start = { 10, 51, 34, 60, 49, 17, 33, 57 };
        for (int i = 0; i < expectedRound1Start.length; i++) {
            assert ks[1][i] == expectedRound1Start[i]
                    : "Round 1 mismatch at index " + i
                            + ": expected " + expectedRound1Start[i] + " got " + ks[1][i];
        }
        System.out.println("Test 4 passed: round 1 matches known DES standard values");

        // Test 5: total left shifts over all 16 rounds = 28
        // (C and D are 28-bit registers that complete exactly one full rotation)
        int totalShifts = 0;
        for (int r = 1; r <= 16; r++)
            totalShifts += shifts[r];
        assert totalShifts == 28 : "Total shifts != 28, got " + totalShifts;
        System.out.println("Test 5 passed: total shifts across all rounds = 28");

        System.out.println("\nAll tests passed.");
    }

}// class ComputeSubKeys
