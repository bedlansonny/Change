import java.io.*;
import java.util.*;

public class Change
{
    static int[] v;     //coin values
    static int[][] s;   //smallest amount of coins given [coin index of v][total desired amount]
    static TreeMap<Integer, Integer> amounts;

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

            for(int y = 0; y < s.length; y++)
            {
                for (int x = 0; x < s[0].length; x++)
                {
                    System.out.printf("%2s, ", "" + s[y][x]);
                }
                System.out.println();
            }


            amounts = new TreeMap<>();
            trace(s.length-1, s[0].length-1);
            System.out.println(amounts.toString());
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
                if(!amounts.containsKey(v[c]))
                    amounts.put(v[c], s[c][t]);
                else
                    amounts.put(v[c], amounts.get(v[c]) + s[c][t]);
            }

        }
        else if(s[c-1][t] == s[c][t])
        {
            trace(c-1, t);
        }
        else
        {
            if(!amounts.containsKey(v[c]))
                amounts.put(v[c], 1);
            else
                amounts.put(v[c], amounts.get(v[c]) + 1);
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
}
