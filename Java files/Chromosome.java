
package MVGPC;

import java.io.Serializable;


// Referenced classes of package MVGPC:
//            Function, Parameters, GeneStat

public class Chromosome
    implements Serializable, Cloneable
{

    public Chromosome(boolean empty)
    {
        functions = null;
        functionSet = null;
        depth = null;
        fitness = 0.0D;
    }

    public Chromosome(int size, Function functionSet[])
    {
        functions = null;
        this.functionSet = null;
        depth = null;
        fitness = 0.0D;
        this.functionSet = functionSet;
        functions = new Function[size];
        depth = new int[size];
    }

    public Chromosome()
    {
        functions = null;
        functionSet = null;
        depth = null;
        fitness = 0.0D;
        functions = new Function[Parameters.maxSize];
        depth = new int[Parameters.maxSize];
        fitness = 0.0D;
    }

    public double GetFitness()
    {
        return fitness;
    }

    public void SetFitness(int tp, int tn, int fp, int fn)
    {
        TP = tp;
        TN = tn;
        FP = fp;
        FN = fn;
        float C = 0.0F;
        C = (float)(TP * TN - FP * FN) / Math.max(1.0F, (float)Math.sqrt((TN + FN) * (TN + FP) * (TP + FN) * (TP + FP)));
        fitness = (double)(1.0F - C) / 2D;
    }

    public void SetTestResults(int testtp, int testtn, int testfp, int testfn)
    {
        testTP = testtp;
        testTN = testtn;
        testFP = testfp;
        testFN = testfn;
    }

    public Object clone()
        throws CloneNotSupportedException
    {
        Chromosome c = new Chromosome(true);
        c.functions = (Function[])functions.clone();
        c.functionSet = (Function[])functionSet.clone();
        c.depth = (int[])depth.clone();
        return c;
    }

    public void countGeneFreq()
    {
        for(int i = 0; i < functions.length && functions[i] != null; i++)
            if(functions[i].getArity() == 0 && functions[i].getName().charAt(0) == 'X')
            {
                int geneId = Integer.parseInt(functions[i].getName().substring(1)) - 1;
                GeneStat.updateFreq(geneId);
            }

    }

    public Object execute(int n, int child)
    {
        return execute_object(n, child);
    }

    public Object execute()
    {
        return functions[0].execute_object(this, 0);
    }

    public boolean execute_boolean(int n, int child)
    {
        if(child == 0)
        {
            return functions[n + 1].execute_boolean(this, n + 1);
        } else
        {
            int other = getChild(n, child);
            return functions[other].execute_boolean(this, other);
        }
    }

    public boolean execute_boolean()
    {
        return functions[0].execute_boolean(this, 0);
    }

    public double execute_double(int n, int child)
    {
        if(child == 0)
        {
            return functions[n + 1].execute_double(this, n + 1);
        } else
        {
            int other = getChild(n, child);
            return functions[other].execute_double(this, other);
        }
    }

    public double execute_double()
    {
        return functions[0].execute_double(this, 0);
    }

    public float execute_float(int n, int child)
    {
        if(child == 0)
        {
            return functions[n + 1].execute_float(this, n + 1);
        } else
        {
            int other = getChild(n, child);
            return functions[other].execute_float(this, other);
        }
    }

    public float execute_float()
    {
        return functions[0].execute_float(this, 0);
    }

    public int execute_int(int n, int child)
    {
        if(child == 0)
        {
            return functions[n + 1].execute_int(this, n + 1);
        } else
        {
            int other = getChild(n, child);
            return functions[other].execute_int(this, other);
        }
    }

    public int execute_int()
    {
        return functions[0].execute_int(this, 0);
    }

    public long execute_long(int n, int child)
    {
        if(child == 0)
        {
            return functions[n + 1].execute_long(this, n + 1);
        } else
        {
            int other = getChild(n, child);
            return functions[other].execute_long(this, other);
        }
    }

    public long execute_long()
    {
        return functions[0].execute_long(this, 0);
    }

    public Object execute_object(int n, int child)
    {
        if(child == 0)
        {
            return functions[n + 1].execute_object(this, n + 1);
        } else
        {
            int other = getChild(n, child);
            return functions[other].execute_object(this, other);
        }
    }

    public Object execute_object()
    {
        return functions[0].execute_object(this, 0);
    }

    public void execute_void(int n, int child)
    {
        if(child == 0)
            functions[n + 1].execute_void(this, n + 1);
        int other = getChild(n, child);
        functions[other].execute_void(this, other);
    }

    public void execute_void()
    {
        functions[0].execute_void(this, 0);
    }

    public void full(int depth, Class type, Function functionSet[])
    {
        this.functionSet = new Function[functionSet.length];
        System.arraycopy(functionSet, 0, this.functionSet, 0, functionSet.length);
        index = 0;
        maxDepth = depth;
        fullNode(depth, type, this.functionSet);
        redepth();
    }

    void fullNode(int depth, Class type, Function functionSet[])
    {
        Function n = selectNode(type, functionSet, depth > 1, false);
        if(Parameters.CountNodeType[0] + Parameters.CountNodeType[1] == 0)
        {
            if(n.getName().compareTo(">") == 0 || n.getName().compareTo("<") == 0 || n.getName().compareTo(">=") == 0 || n.getName().compareTo("<=") == 0 || n.getName().compareTo("=") == 0 || n.getName().compareTo("<>") == 0)
            {
                this.depth[index] = 2;
                depth = 2;
            } else
            if(n.getArity() == 0)
            {
                this.depth[index] = 1;
                depth = 1;
            } else
            {
                this.depth[index] = maxDepth - depth;
            }
        }
        this.depth[index] = maxDepth - depth;
        functions[index++] = n;
        if(depth > 1)
        {
            for(int i = 0; i < n.getArity(); i++)
                fullNode(depth - 1, n.getChildType(i), functionSet);

        }
    }

    public Class getArgType(int i)
    {
        return null;
    }

    public int getArity()
    {
        return 0;
    }

    public int getChild(int n, int child)
    {
        for(int i = n + 1; i < functions.length; i++)
        {
            if(depth[i] <= depth[n])
                return -1;
            if(depth[i] == depth[n] + 1 && --child < 0)
                return i;
        }

        return -1;
    }

    public int getDepth(int n)
    {
        int maxdepth = depth[n];
        for(int i = n + 1; i < functions.length && functions[i] != null; i++)
        {
            if(depth[i] <= depth[n])
                break;
            if(depth[i] > maxdepth)
                maxdepth = depth[i];
        }

        return maxdepth - depth[n];
    }

    public int getFunction(int i, Class type)
    {
        for(int j = 0; j < functions.length && functions[j] != null; j++)
            if(functions[j].getReturnType() == type && functions[j].getArity() != 0 && --i < 0)
                return j;

        return -1;
    }

    public int getFunction(int i)
    {
        for(int j = 0; j < functions.length && functions[j] != null; j++)
            if(functions[j].getArity() != 0 && --i < 0)
                return j;

        return -1;
    }

    public int getNode(int i, Class type)
    {
        for(int j = 0; j < functions.length && functions[j] != null; j++)
            if(functions[j].getReturnType() == type && --i < 0)
                return j;

        return -1;
    }

    public Function getNode(int i)
    {
        if(i >= functions.length || functions[i] == null)
            return null;
        else
            return functions[i];
    }

    public int getParentNode(int child)
    {
        if(child >= functions.length || functions[child] == null)
            return -1;
        for(int i = child - 1; i >= 0; i--)
            if(depth[i] == depth[child] - 1)
                return i;

        return -1;
    }

    public int getSize(int n)
    {
        int i;
        for(i = n + 1; i < functions.length && functions[i] != null; i++)
            if(depth[i] <= depth[n])
                break;

        return i - n;
    }

    public int getTerminal(int i, Class type)
    {
        for(int j = 0; j < functions.length && functions[j] != null; j++)
            if(functions[j].getReturnType() == type && functions[j].getArity() == 0 && --i < 0)
                return j;

        return -1;
    }

    public int getTerminal(int i)
    {
        for(int j = 0; j < functions.length && functions[j] != null; j++)
            if(functions[j].getArity() == 0 && --i < 0)
                return j;

        return -1;
    }

    public void grow(int depth, Class type, Function functionSet[])
    {
        this.functionSet = new Function[functionSet.length];
        System.arraycopy(functionSet, 0, this.functionSet, 0, functionSet.length);
        index = 0;
        maxDepth = depth;
        growNode(depth, type, this.functionSet);
        redepth();
    }

    void growNode(int depth, Class type, Function functionSet[])
    {
        Function n = selectNode(type, functionSet, depth > 1, true);
        if(Parameters.CountNodeType[0] + Parameters.CountNodeType[1] == 0)
        {
            if(n.getName().compareTo(">") == 0 || n.getName().compareTo("<") == 0 || n.getName().compareTo(">=") == 0 || n.getName().compareTo("<=") == 0 || n.getName().compareTo("=") == 0 || n.getName().compareTo("<>") == 0)
            {
                this.depth[index] = 2;
                depth = 2;
            } else
            if(n.getArity() == 0)
            {
                this.depth[index] = 1;
                depth = 1;
            } else
            {
                this.depth[index] = maxDepth - depth;
            }
        } else
        {
            this.depth[index] = maxDepth - depth;
        }
        functions[index++] = n;
        if(depth > 1)
        {
            for(int i = 0; i < n.getArity(); i++)
                growNode(depth - 1, n.getChildType(i), functionSet);

        }
    }

    public boolean isPossible(Function f)
    {
        for(int i = 0; i < functionSet.length; i++)
            if(functionSet[i] == f)
                return true;

        return false;
    }

    public static boolean isPossible(Class type, Function nodeSet[], boolean function)
    {
        for(int i = 0; i < nodeSet.length; i++)
            if(nodeSet[i].getReturnType() == type && (nodeSet[i].getArity() != 0) == function)
                return true;

        return false;
    }

    public int numElements()
    {
        int e = 0;
        e = numTerminals() + numFunctions();
        return e;
    }

    public int numFunctions(Class type)
    {
        int count = 0;
        for(int i = 0; i < functions.length && functions[i] != null; i++)
            if(functions[i].getArity() != 0 && functions[i].getReturnType() == type)
                count++;

        return count;
    }

    public int numFunctions()
    {
        int count = 0;
        for(int i = 0; i < functions.length && functions[i] != null; i++)
            if(functions[i].getArity() != 0)
                count++;

        return count;
    }

    public int numTerminals(Class type)
    {
        int count = 0;
        for(int i = 0; i < functions.length && functions[i] != null; i++)
            if(functions[i].getArity() == 0 && functions[i].getReturnType() == type)
                count++;

        return count;
    }

    public int numTerminals()
    {
        int count = 0;
        for(int i = 0; i < functions.length && functions[i] != null; i++)
            if(functions[i].getArity() == 0)
                count++;

        return count;
    }

    protected int redepth(int n)
    {
        int num = n + 1;
        int arity = getNode(n).getArity();
        for(int i = 0; i < arity; i++)
        {
            depth[num] = depth[n] + 1;
            num = redepth(num);
        }

        return num;
    }

    public void redepth()
    {
        depth[0] = 0;
        redepth(0);
    }

    public static Function selectNode(int nodetype, Function functionSet[])
    {
        int n = Parameters.CountNodeType[nodetype];
        int index = Parameters.random.nextInt(n);
        for(int i = 0; i < functionSet.length; i++)
            if(Parameters.GetNodeType(functionSet[i].getFunctionIndex()) == nodetype && --index < 0)
                return functionSet[i];

        return null;
    }

    public static Function selectNode(Class type, Function functionSet[], boolean function, boolean growing)
    {
        if(!isPossible(type, functionSet, function))
            throw new IllegalArgumentException("Chromosome requires a " + (function ? "function" + (growing ? " or terminal" : "") : "terminal") + " of type " + type + " but there is no such node available");
        Function n;
        for(n = null; n == null;)
        {
            int index = Parameters.random.nextInt(functionSet.length);
            if(functionSet[index].getReturnType() == type)
            {
                if(functionSet[index].getArity() == 0 && (!function || growing))
                    n = functionSet[index];
                if(functionSet[index].getArity() != 0 && function)
                    n = functionSet[index];
            }
        }

        return n;
    }

    public void setNode(int i, Function nd)
    {
        if(i < functions.length)
            functions[i] = nd;
    }

    public String toString()
    {
        return toString(0);
    }

    protected String toString(int n)
    {
        if(functions[n].getArity() == 0)
            if(functions[n].getName().charAt(0) == 'C')
                return functions[n].getName().substring(1);
            else
                return functions[n].getName();
        if(functions[n].getArity() == 1)
            return functions[n].getName() + "(" + toString(getChild(n, 0)) + ")";
        else
            return "(" + toString(getChild(n, 0)) + " " + functions[n].getName() + " " + toString(getChild(n, 1)) + ")";
    }

    int FN;
    int FP;
    int TN;
    int TP;
    int depth[];
    double fitness;
    Function functionSet[];
    Function functions[];
    transient int index;
    transient int maxDepth;
    int testFN;
    int testFP;
    int testTN;
    int testTP;
}
