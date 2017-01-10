package MVGPC;

// Referenced classes of package MVGPC:
//            Function, Parameters, Chromosome

public class OperatorTree
{

    public OperatorTree(int size)
    {
    
        opSet = new OpSet[size];
        opSize = size;
    }
    
    public int getOperatorSize() {
        return opSize;
    }
    
    protected OpSet opSet[];
    protected int opSize;
    
}
