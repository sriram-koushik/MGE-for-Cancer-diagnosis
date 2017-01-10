
package MVGPC;

import java.util.Comparator;


// Referenced classes of package MVGPC:
//            GeneFreq

class GeneFreqComparator
    implements Comparator
{

    GeneFreqComparator()
    {
    }

    public int compare(Object o1, Object o2)
    {
        if(!(o1 instanceof GeneFreq) || !(o2 instanceof GeneFreq))
            throw new ClassCastException("GeneFreqComparator must operate on gene frequency!");
        GeneFreq a = (GeneFreq)o1;
        GeneFreq b = (GeneFreq)o2;
        int f1 = a.getFreq();
        int f2 = b.getFreq();
        if(f1 < f2)
            return 1;
        return f1 <= f2 ? 0 : -1;
    }
}
