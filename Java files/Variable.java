
package MVGPC;

import java.io.Serializable;

import java.util.Hashtable;


// Referenced classes of package MVGPC:
//            Function, Chromosome

public class Variable extends Function
    implements Serializable
{

    private Variable(int index, String name, Class type)
    {
        super(0, type);
        this.name = name;
        this.index = index;
        vars.put(name, this);
    }

    public static Variable create(int i, String name, Class type)
    {
        Variable var;
        if((var = getVariable(name)) != null)
            return var;
        else
            return new Variable(i, name, type);
    }

    public boolean execute_boolean(Chromosome c, int n)
    {
        return ((Boolean)value).booleanValue();
    }

    public double execute_double(Chromosome c, int n)
    {
        return ((Double)value).doubleValue();
    }

    public float execute_float(Chromosome c, int n)
    {
        return ((Float)value).floatValue();
    }

    public int execute_int(Chromosome c, int n)
    {
        return ((Integer)value).intValue();
    }

    public long execute_long(Chromosome c, int n)
    {
        return ((Long)value).longValue();
    }

    public Object execute_object(Chromosome c, int n)
    {
        return value;
    }

    public Object get()
    {
        return value;
    }

    public static Object get(String name)
    {
        return ((Variable)vars.get(name)).value;
    }

    public Class getChildType(int i)
    {
        return returnType;
    }

    public int getFunctionIndex()
    {
        return index;
    }

    public String getName()
    {
        return name;
    }

    public static Variable getVariable(String name)
    {
        return (Variable)vars.get(name);
    }

    public boolean isConstant()
    {
        return false;
    }

    public void set(Object value)
    {
        this.value = value;
    }

    public static void set(String name, Object value)
    {
        ((Variable)vars.get(name)).value = value;
    }

    protected int index;
    String name;
    public Object value;
    static Hashtable vars = new Hashtable();

}
