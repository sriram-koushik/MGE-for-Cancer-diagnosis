
package MVGPC;

import java.util.Random;
import java.util.StringTokenizer;


// Referenced classes of package MVGPC:
//            GeneticEvolution, GeneticOperation

public class Parameters
{

    public Parameters()
    {
    }

    public static int GetFunctionIndex(String f)
    {
        if(f.compareTo("+") == 0)
            return 0;
        if(f.compareTo("-") == 0)
            return 1;
        if(f.compareTo("*") == 0)
            return 2;
        if(f.compareTo("/") == 0)
            return 3;
        if(f.compareTo("SQR") == 0 || f.compareTo("sqr") == 0)
            return 4;
        if(f.compareTo("SQRT") == 0 || f.compareTo("sqrt") == 0)
            return 5;
        if(f.compareTo("LN") == 0 || f.compareTo("ln") == 0)
            return 6;
        if(f.compareTo("EXP") == 0 || f.compareTo("exp") == 0)
            return 7;
        if(f.compareTo("SIN") == 0 || f.compareTo("sin") == 0)
            return 8;
        if(f.compareTo("COS") == 0 || f.compareTo("COS") == 0)
            return 9;
        if(f.compareTo("AND") == 0 || f.compareTo("and") == 0)
            return 17;
        if(f.compareTo("OR") == 0 || f.compareTo("or") == 0)
            return 18;
        if(f.compareTo("NOT") == 0 || f.compareTo("not") == 0)
            return 19;
        if(f.compareTo("=") == 0)
            return 11;
        if(f.compareTo("<>") == 0 || f.compareTo("!=") == 0)
            return 12;
        if(f.compareTo("<") == 0)
            return 13;
        if(f.compareTo(">") == 0)
            return 15;
        if(f.compareTo("<=") == 0)
            return 14;
        return f.compareTo(">=") != 0 ? -1 : 16;
    }

    public static int GetNodeType(int findex)
    {
        switch(findex)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
            return 0;

        case 4: // '\004'
        case 5: // '\005'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
        case 9: // '\t'
            return 1;

        case 11: // '\013'
        case 12: // '\f'
        case 13: // '\r'
        case 14: // '\016'
        case 15: // '\017'
        case 16: // '\020'
            return 2;

        case 17: // '\021'
        case 18: // '\022'
            return 3;

        case 19: // '\023'
            return 4;

        case 20: // '\024'
        case 21: // '\025'
            return 5;

        case 22: // '\026'
            return 6;

        case 10: // '\n'
        default:
            return -1;
        }
    }

// Start Operator Implementation

    public static int GetOperatorIndex(String Ops)
    {
        if(Ops.compareTo("RAND1") == 0 || Ops.compareTo("rand1") == 0)
            return 0;
        if(Ops.compareTo("RAND2") == 0 || Ops.compareTo("rand2") == 0)
            return 1;
        if(Ops.compareTo("UP1") == 0 || Ops.compareTo("up1") == 0)
            return 2;
        if(Ops.compareTo("UP2") == 0 || Ops.compareTo("up2") == 0)
            return 3;
        if(Ops.compareTo("BOTTOM1") == 0 || Ops.compareTo("bottom1") == 0)
            return 4;
        if(Ops.compareTo("BOTTOM2") == 0 || Ops.compareTo("bottom2") == 0)
            return 5;
        if(Ops.compareTo("DOWN1") == 0 || Ops.compareTo("down1") == 0)
            return 6;
        if(Ops.compareTo("DOWN2") == 0 || Ops.compareTo("down2") == 0)
            return 7;
        if(Ops.compareTo("TOP1") == 0 || Ops.compareTo("top1") == 0)
            return 8;
        if(Ops.compareTo("TOP2") == 0 || Ops.compareTo("top2") == 0)
            return 9;
        if(Ops.compareTo("SUBS") == 0 || Ops.compareTo("subs") == 0)
            return 10;
        else
            return -1;
    }

    public static int GetOperatorType(int OpsIndex)
    {
        switch(OpsIndex)
        {
        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
        case 3: // '\003'
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
            return 1;

        case 10: 
            return 2;

        default:
            return -1;
        }
    }
//End Operator Implementation

    static Class _mthclass$(String x0)
    {
        try
        {
        return Class.forName(x0);
        }
        catch(Exception x1)
        {
        throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    public static String getFileName(String filename)
    {
        StringTokenizer path = new StringTokenizer(filename, "\\");
        for(int n = path.countTokens(); n > 1; n--)
            path.nextToken();

        String s = path.nextToken();
        return s;
    }

    public static final int ADD = 0;
    public static final int AND = 17;
    public static final int BINARY_ARITHMETIC = 0;
    public static final int BINARY_BOOLEAN = 3;
    public static final int BOOLEAN_TERMINAL = 6;
    public static final int BOOLEAN_VAR = 22;
    public static final int COMPARISON = 2;
    public static final int COS = 9;
    public static int CountNodeType[] = new int[7];
    public static final int DIVIDE = 3;
    public static final int EQ = 11;
    public static final int EXP = 7;
    public static final String FuncName[] = {
        "+", "-", "*", "/", "SQR", "SQRT", "LN", "EXP", "SIN", "COS", 
        "IF", "=", "<>", "<", "<=", ">", ">=", "AND", "OR", "NOT"
    };
    public static final int GE = 16;
    public static final int GREATERTHAN = 15;
    public static final int IF = 10;
    public static final int LE = 14;
    public static final int LESSTHAN = 13;
    public static final int LN = 6;
    public static final int MULTIPLY = 2;
    public static final int NE = 12;
    public static final int NOT = 19;
    public static final int NUMERIC_CONST = 21;
    public static final int NUMERIC_TERMINAL = 5;
    public static final int NUMERIC_VAR = 20;
    public static final int OR = 18;
    public static boolean ProcessMode = false;
    public static boolean RunningMode = false;
    public static final int SIN = 8;
    public static final int SQR = 4;
    public static final int SQRT = 5;
    public static final int SUBTRACT = 1;
    public static final int UNARY_ARITHMETIC = 1;
    public static final int UNARY_BOOLEAN = 4;
    public static final Class booleanClass;
    protected static float crossoverProb = 0.9F;
    public static final Class doubleClass;
    public static final Class floatClass;
    protected static GeneticOperation geneticoperation = new GeneticEvolution();
    public static final Class integerClass;
    public static final Class longClass;
    protected static int maxChromosomes = 4;
    protected static int maxCrossoverDepth = 7;
    protected static int maxInitDepth = 6;
    protected static int maxSize = 100;
    protected static int minChromosomes = 1;
    protected static float mutationProb = 0.1F;
    public static Random random = new Random();
    protected static float reproductionProb = 0.1F;
    public static final Class voidClass;

    static 
    {
        booleanClass = java.lang.Boolean.class;
        integerClass = java.lang.Integer.class;
        longClass = java.lang.Long.class;
        floatClass = java.lang.Float.class;
        doubleClass = java.lang.Double.class;
        voidClass = java.lang.Void.class;
    }
}
