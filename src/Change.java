import java.io.*;
import java.util.*;

public class Change
{
    static int[] v;     //coin values
    static int[][] s;   //smallest amount of coins given [coin index of v][total desired amount]
    static int[] amounts;

    public static void main(String args[]) throws IOException
    {
        Scanner in = new Scanner(new File("change.dat"));

        int cases = in.nextInt();
        for(int casesI = 0; casesI < cases; casesI++)
        {
            int coins = in.nextInt();

            v = new int[coins];
            for(int coinsI = 0; coinsI < coins; coinsI++)
            {
                v[coinsI] = in.nextInt();
            }

            int t = in.nextInt();

            s = new int[coins][t+1];

            //DP
//            System.out.print(smallest() + " ");

            //Recursive Memoization (top-down)
            for (int y = 0; y < s.length; y++) {
                for (int x = 0; x < s[0].length; x++) {
                    s[y][x] = -1;
                }
            }
            System.out.print(smallestR(s.length-1, s[0].length-1) + " ");

            amounts = new int[v.length];
            trace(s.length-1, s[0].length-1);
            for (int i = 0; i < amounts.length; i++) {
                System.out.print(amounts[i] + " ");
            }
            System.out.println();
        }
    }

    //recursive, traces which coins produced the smallest amount of coins to reach the target amount
    static void trace(int c, int t)
    {
        if(c == 0)
        {
            if(s[c][t] != 0)
            {
                amounts[c] += s[c][t];
            }

        }
        else if(s[c-1][t] == s[c][t])
        {
            trace(c-1, t);
        }
        else
        {
            amounts[c]++;
            trace(c, t - v[c]);
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

    //same as DP method but uses top-down memoized recursion
    static int smallestR(int c, int t)
    {
        if(s[c][t] == -1)
        {
            if(c == 0)
            {
                if(t % v[0] != 0)
                    s[c][t] = Integer.MAX_VALUE;
                else
                    s[c][t] = t / v[0];
            }
            else
            {
                if(t < v[c])
                    s[c][t] = smallestR(c-1, t);
                else
                    s[c][t] = Math.min(smallestR(c, t-v[c]) + 1, smallestR(c-1, t));
            }
        }
        return s[c][t];
    }
}