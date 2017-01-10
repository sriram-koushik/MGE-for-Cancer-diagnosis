

package MVGPC;

import java.io.Serializable;


// Referenced classes of package MVGPC:
//            Function, Parameters, Chromosome

public class FuncSet extends Function
    implements Serializable
{
    public static interface Compatible
    {

        public abstract Object execute_add(Object obj);
    }


    public FuncSet(int findex, int arity, Class rtype, Class ctype, String fname)
    {
        super(arity, rtype);
        setName(fname);
        setFunctionIndex(findex);
        setChildType(ctype);
    }

    public boolean execute_boolean(Chromosome c, int n)
    {
        switch(getFunctionIndex())
        {
        case 17: // '\021'
            return c.execute_boolean(n, 0) && c.execute_boolean(n, 1);

        case 18: // '\022'
            return c.execute_boolean(n, 0) || c.execute_boolean(n, 1);

        case 19: // '\023'
            return !c.execute_boolean(n, 0);

        case 10: // '\n'
            return c.execute_boolean(n, 0) ? c.execute_boolean(n, 1) : c.execute_boolean(n, 2);

        case 11: // '\013'
            if(childType.equals(Parameters.integerClass))
                return c.execute_int(n, 0) == c.execute_int(n, 1);
            if(childType.equals(Parameters.longClass))
                return c.execute_long(n, 0) == c.execute_long(n, 1);
            if(childType.equals(Parameters.floatClass))
                return c.execute_float(n, 0) == c.execute_float(n, 1);
            if(childType.equals(Parameters.doubleClass))
                return c.execute_double(n, 0) == c.execute_double(n, 1);
            // fall through

        case 12: // '\f'
            if(childType.equals(Parameters.integerClass))
                return c.execute_int(n, 0) != c.execute_int(n, 1);
            if(childType.equals(Parameters.longClass))
                return c.execute_long(n, 0) != c.execute_long(n, 1);
            if(childType.equals(Parameters.floatClass))
                return c.execute_float(n, 0) != c.execute_float(n, 1);
            if(childType.equals(Parameters.doubleClass))
                return c.execute_double(n, 0) != c.execute_double(n, 1);
            // fall through

        case 13: // '\r'
            if(childType.equals(Parameters.integerClass))
                return c.execute_int(n, 0) < c.execute_int(n, 1);
            if(childType.equals(Parameters.longClass))
                return c.execute_long(n, 0) < c.execute_long(n, 1);
            if(childType.equals(Parameters.floatClass))
                return c.execute_float(n, 0) < c.execute_float(n, 1);
            if(childType.equals(Parameters.doubleClass))
                return c.execute_double(n, 0) < c.execute_double(n, 1);
            // fall through

        case 15: // '\017'
            if(childType.equals(Parameters.integerClass))
                return c.execute_int(n, 0) > c.execute_int(n, 1);
            if(childType.equals(Parameters.longClass))
                return c.execute_long(n, 0) > c.execute_long(n, 1);
            if(childType.equals(Parameters.floatClass))
                return c.execute_float(n, 0) > c.execute_float(n, 1);
            if(childType.equals(Parameters.doubleClass))
                return c.execute_double(n, 0) > c.execute_double(n, 1);
            // fall through

        case 14: // '\016'
            if(childType.equals(Parameters.integerClass))
                return c.execute_int(n, 0) <= c.execute_int(n, 1);
            if(childType.equals(Parameters.longClass))
                return c.execute_long(n, 0) <= c.execute_long(n, 1);
            if(childType.equals(Parameters.floatClass))
                return c.execute_float(n, 0) <= c.execute_float(n, 1);
            if(childType.equals(Parameters.doubleClass))
                return c.execute_double(n, 0) <= c.execute_double(n, 1);
            // fall through

        case 16: // '\020'
            if(childType.equals(Parameters.integerClass))
                return c.execute_int(n, 0) >= c.execute_int(n, 1);
            if(childType.equals(Parameters.longClass))
                return c.execute_long(n, 0) >= c.execute_long(n, 1);
            if(childType.equals(Parameters.floatClass))
                return c.execute_float(n, 0) >= c.execute_float(n, 1);
            if(childType.equals(Parameters.doubleClass))
                return c.execute_double(n, 0) >= c.execute_double(n, 1);
            // fall through

        default:
            return false;
        }
    }

    public double execute_double(Chromosome c, int n)
    {
        switch(getFunctionIndex())
        {
        case 0: // '\0'
        {
            return c.execute_double(n, 0) + c.execute_double(n, 1);
        }

        case 1: // '\001'
        {
            return c.execute_double(n, 0) - c.execute_double(n, 1);
        }

        case 2: // '\002'
        {
            return c.execute_double(n, 0) * c.execute_double(n, 1);
        }

        case 3: // '\003'
        {
            double d = c.execute_double(n, 1);
            return d != 0.0D ? c.execute_double(n, 0) / d : 1.0D;
        }

        case 6: // '\006'
        {
            double d = c.execute_double(n, 0);
            if(d == 0.0D)
                return 0.0D;
            d = Math.log(Math.abs(d));
            if(d > 1E+100D)
                d = 1E+100D;
            else
            if(d < -1E+100D)
                d = -1E+100D;
            return d;
        }

        case 7: // '\007'
        {
            double d = c.execute_double(n, 0);
            return Math.exp(Math.max(-10000D, Math.min(d, 20D)));
        }

        case 8: // '\b'
        {
            double d = c.execute_double(n, 0);
            return Math.sin(Math.max(-10000D, Math.min(d, 10000D)));
        }

        case 9: // '\t'
        {
            double d = c.execute_double(n, 0);
            return Math.cos(Math.max(-10000D, Math.min(d, 10000D)));
        }

        case 4: // '\004'
        {
            return c.execute_double(n, 0) * c.execute_double(n, 0);
        }

        case 5: // '\005'
        {
            double d = c.execute_double(n, 0);
            return Math.sqrt(Math.max(0.0D, d));
        }

        case 10: // '\n'
        {
            return c.execute_boolean(n, 0) ? c.execute_double(n, 1) : c.execute_double(n, 2);
        }
        }
        return 0.0D;
    }

    public float execute_float(Chromosome c, int n)
    {
        switch(getFunctionIndex())
        {
        case 0: // '\0'
        {
            return c.execute_float(n, 0) + c.execute_float(n, 1);
        }

        case 1: // '\001'
        {
            return c.execute_float(n, 0) - c.execute_float(n, 1);
        }

        case 2: // '\002'
        {
            return c.execute_float(n, 0) * c.execute_float(n, 1);
        }

        case 3: // '\003'
        {
            float d = c.execute_float(n, 1);
            return d != 0.0F ? c.execute_float(n, 0) / d : 1.0F;
        }

        case 6: // '\006'
        {
            float d = c.execute_float(n, 0);
            if((double)d == 0.0D)
                return 0.0F;
            d = (float)Math.log(Math.abs(d));
            if((double)d > 1E+100D)
                d = (1.0F / 0.0F);
            else
            if((double)d < -1E+100D)
                d = (-1.0F / 0.0F);
            return d;
        }

        case 7: // '\007'
        {
            float d = c.execute_float(n, 0);
            return (float)Math.exp(Math.max(-10000D, Math.min(d, 20D)));
        }

        case 8: // '\b'
        {
            float d = c.execute_float(n, 0);
            return (float)Math.sin(Math.max(-10000D, Math.min(d, 10000D)));
        }

        case 9: // '\t'
        {
            float d = c.execute_float(n, 0);
            return (float)Math.cos(Math.max(-10000D, Math.min(d, 10000D)));
        }

        case 4: // '\004'
        {
            return c.execute_float(n, 0) * c.execute_float(n, 0);
        }

        case 5: // '\005'
        {
            float d = c.execute_float(n, 0);
            return (float)Math.sqrt(Math.max(0.0F, d));
        }

        case 10: // '\n'
        {
            return c.execute_boolean(n, 0) ? c.execute_float(n, 1) : c.execute_float(n, 2);
        }
        }
        return 0.0F;
    }

    public int execute_int(Chromosome c, int n)
    {
        switch(getFunctionIndex())
        {
        case 0: // '\0'
            return c.execute_int(n, 0) + c.execute_int(n, 1);

        case 1: // '\001'
            return c.execute_int(n, 0) - c.execute_int(n, 1);

        case 2: // '\002'
            return c.execute_int(n, 0) * c.execute_int(n, 1);

        case 3: // '\003'
            int d = c.execute_int(n, 1);
            return d != 0 ? c.execute_int(n, 0) / d : 1;

        case 10: // '\n'
            return c.execute_boolean(n, 0) ? c.execute_int(n, 1) : c.execute_int(n, 2);

        case 4: // '\004'
        case 5: // '\005'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
        case 9: // '\t'
        default:
            return 0;
        }
    }

    public long execute_long(Chromosome c, int n)
    {
        switch(getFunctionIndex())
        {
        case 0: // '\0'
            return c.execute_long(n, 0) + c.execute_long(n, 1);

        case 1: // '\001'
            return c.execute_long(n, 0) - c.execute_long(n, 1);

        case 2: // '\002'
            return c.execute_long(n, 0) * c.execute_long(n, 1);

        case 3: // '\003'
            long d = c.execute_long(n, 1);
            return d != 0L ? c.execute_long(n, 0) / d : 1L;

        case 10: // '\n'
            return c.execute_boolean(n, 0) ? c.execute_long(n, 1) : c.execute_long(n, 2);

        case 4: // '\004'
        case 5: // '\005'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
        case 9: // '\t'
        default:
            return 0L;
        }
    }

    public Class getChildType(int i)
    {
        if(i == 0 && getFunctionIndex() == 10)
            return Parameters.booleanClass;
        else
            return childType;
    }

    public int getFunctionIndex()
    {
        return fnIndex;
    }

    public String getName()
    {
        return functionname;
    }

    public void setChildType(Class ctype)
    {
        childType = ctype;
    }

    public void setFunctionIndex(int index)
    {
        fnIndex = index;
    }

    public void setName(String fname)
    {
        functionname = fname;
    }

    protected Class childType;
    protected int fnIndex;
    protected String functionname;
}
