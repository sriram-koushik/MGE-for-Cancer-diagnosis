
package MVGPC;

import java.util.Comparator;


// Referenced classes of package MVGPC:
//            Individual

class FitnessComparator
    implements Comparator
{

    FitnessComparator()
    {
    }

    public int compare(Object o1, Object o2)
    {
        if(!(o1 instanceof Individual) || !(o2 instanceof Individual))
            throw new ClassCastException("FitnessComparator must operate on Individuals");
        Individual a = (Individual)o1;
        Individual b = (Individual)o2;
        float f1 = a.getFitness();
        float f2 = b.getFitness();
        if(f1 > f2)
            return 1;
        return f1 >= f2 ? 0 : -1;
    }
}
