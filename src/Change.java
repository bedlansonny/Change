import java.io.*;
import java.util.*;

public class Change
{
    static int[] v;     //coin values
    static int[][] s;   //smallest amount of coins given [coin index of v][total desired amount]
    public static void main(String args[]) throws IOException
    {
        Scanner in = new Scanner(new File("change.dat"));

        int cases = in.nextInt();
        for(int casesI = 0; casesI < cases; casesI++)
        {
            int t = in.nextInt();
            int coins = in.nextInt();

            v = new int[coins];
            for(int coinsI = 0; coinsI < coins; coinsI++)
            {
                v[coinsI] = in.nextInt();
            }

            s = new int[coins][t+1];

            System.out.println(smallest());
        }
    }

    //DP, returns smallest amount of coins to reach the target amount, returns -1 if impossible
    static int smallest()
    {
        //base case, first row
        for (int t = 0; t < s[0].length; t++)
        {
            if(t % v[0] != 0)
                s[0][t] = Integer.MAX_VALUE;
            else
                s[0][t] = t / v[0];
        }

        //normal case
        for(int t = 0; t < s[0].length; t++)
        {
            for(int c = 1; c < s.length; c++)
            {
                if(t < v[c])
                    s[c][t] = s[c-1][t];
                else
                    s[c][t] = Math.min(s[c][t - v[c]] + 1, s[c-1][t]);
            }
        }

        //output
        if(s[s.length-1][s[0].length-1] == Integer.MAX_VALUE)
            return -1;
        else
            return s[s.length-1][s[0].length-1];
    }
}
