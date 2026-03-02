/*****************************************************
   CS 326 - Spring 2026 - Assignment #2

   Student's full name: _____
   Student's full name: _____
   Student's full name: _____
*****************************************************/

abstract class FeistelFunction
{
    /* given a 2w-bit block and a k-bit key, return the 2w-bit block 
       resulting from applying the round function to the two inputs
    */
    abstract int[] round(int[] block, int[] key);
    
}// class FeistelFunction

/* implements the Feistel round function that 
   ignores its inputs (data block and key) and 
   always returns all 0 bits
*/
class FeistelAllZeros  extends FeistelFunction
{
    int[] round(int[] block, int[] key)
    {
        int[] arr = new int[block.length];

        for(int i = 0; i < block.length; i++){
            arr[i] = 0;
        }

        return arr;
    }
}// class FeistelAllZeros

/* implements the Feistel round function that 
   ignores its inputs (data block and key) and 
   always returns all 1 bits
*/
class FeistelAllOnes  extends FeistelFunction
{
    int[] round(int[] block, int[] key)
    {
        int[] arr = new int[block.length];

        for(int i = 0; i < block.length; i++){
            arr[i] = 1;
        }

        return arr;
    }
}// class FeistelAllOnes


/* implements the Feistel round function that 
   returns the conjunction (bitwise AND) of its
   two inputs.
*/
class FeistelAnd  extends FeistelFunction
{
    int[] round(int[] block, int[] key)
    {
        int[] arr = new int[block.length];

        for(int i = 0; i < block.length; i++){
            
            if(block[i] == 1 && key[i] == 1){
                arr[i] = 1;
            } else{
                arr[i] = 0;
            }
        }

        return arr;
    }
}// class FeistelAnd
