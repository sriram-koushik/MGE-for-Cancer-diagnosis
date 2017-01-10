
package MVGPC;

import java.util.Arrays;


// Referenced classes of package MVGPC:
//            GeneFreq, GeneFreqComparator, Chromosome

public class GeneStat
{

    public GeneStat()
    {
    }

    public static void countGeneFreq(Chromosome rule)
    {
        rule.countGeneFreq();
    }

    public static int getFreq(int i)
    {
        return genefreq[i].getFreq();
    }

    public static int getId(int i)
    {
        return genefreq[i].getId();
    }

    public static void initGeneFreq(int totalgenes)
    {
        genefreq = new GeneFreq[totalgenes];
        for(int i = 0; i < totalgenes; i++)
            genefreq[i] = new GeneFreq(i + 1);

    }

    public static void sortGeneFreq()
    {
        Arrays.sort(genefreq, new GeneFreqComparator());
    }

    public static void updateFreq(int i)
    {
        genefreq[i].updateFreq();
    }

    public static GeneFreq genefreq[];
}
