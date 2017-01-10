

package MVGPC;

import java.io.Serializable;


// Referenced classes of package MVGPC:
//            Function, Parameters, Chromosome

public class OpSet extends Operator
    implements Serializable
{
    

    public OpSet(int OpIndex, int arity, String OpName)
    {
        super(arity);
        setName(OpName);
        setOperatorIndex(OpIndex);
    }

    public int getOperatorIndex()
    {
        return OpIndex;
    }

    public String getName()
    {
        return OpName;
    }

    public void setOperatorIndex(int index)
    {
        OpIndex = index;
    }

    public void setName(String fname)
    {
        OpName = fname;
    }

    protected int OpIndex;
    protected String OpName;
}
