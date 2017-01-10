
package MVGPC;

import java.io.Serializable;


// Referenced classes of package MVGPC:
//            Parameters, Individual, Chromosome

public abstract class Function
    implements Serializable
{

    protected Function(int arity, Class type)
    {
        returnType = type;
        this.arity = arity;
    }

    public Object execute(Chromosome c, int n)
    {
        if(returnType == Parameters.booleanClass)
            return new Boolean(execute_boolean(c, n));
        if(returnType == Parameters.integerClass)
            return new Integer(execute_int(c, n));
        if(returnType == Parameters.longClass)
            return new Long(execute_long(c, n));
        if(returnType == Parameters.floatClass)
            return new Float(execute_float(c, n));
        if(returnType == Parameters.doubleClass)
            return new Double(execute_double(c, n));
        if(returnType == Parameters.voidClass)
            execute_void(c, n);
        else
            return execute_object(c, n);
        return null;
    }

    public boolean execute_boolean(Chromosome c, int n)
    {
        throw new UnsupportedOperationException(getName() + " cannot return boolean");
    }

    public double execute_double(Chromosome c, int n)
    {
        throw new UnsupportedOperationException(getName() + " cannot return double");
    }

    public float execute_float(Chromosome c, int n)
    {
        throw new UnsupportedOperationException(getName() + " cannot return float");
    }

    public int execute_int(Chromosome c, int n)
    {
        throw new UnsupportedOperationException(getName() + " cannot return int");
    }

    public long execute_long(Chromosome c, int n)
    {
        throw new UnsupportedOperationException(getName() + " cannot return long");
    }

    public Object execute_object(Chromosome c, int n)
    {
        throw new UnsupportedOperationException(getName() + " cannot return Object");
    }

    public void execute_void(Chromosome c, int n)
    {
        throw new UnsupportedOperationException(getName() + " cannot return void");
    }

    public int getArity()
    {
        return arity;
    }

    public abstract Class getChildType(int i);

    public abstract int getFunctionIndex();

    public abstract String getName();

    public Class getReturnType()
    {
        return returnType;
    }

    public static void setIndividual(Individual individual)
    {
        individual = individual;
    }

    public void setReturnType(Class type)
    {
        returnType = type;
    }

    protected int arity;
    protected static transient Individual individual = null;
    protected Class returnType;

}
