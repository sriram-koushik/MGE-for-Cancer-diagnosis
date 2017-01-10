
package MVGPC;

import java.io.Serializable;


// Referenced classes of package MVGPC:
//            Parameters, Individual, Chromosome

public abstract class Operator
    implements Serializable
{

    protected Operator(int arity)
    {
        this.arity = arity;
    }

    public int getArity()
    {
        return arity;
    }

    public abstract int getOperatorIndex();

    public abstract String getName();

    protected int arity;
    
}
