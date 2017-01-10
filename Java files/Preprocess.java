
package MVGPC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.StringTokenizer;


public class Preprocess
{

    public Preprocess(boolean sid, boolean gid)
    {
        SAMPLE = 0;
        GENES = 0;
        IsFirstLabels = false;
        IsFirstGeneIds = false;
        IsFirstLabels = sid;
        IsFirstGeneIds = gid;
    }

    static void SyntaxMessage()
    {
        String syntax = "Usage: java [-Xmx<heapsize>] -jar EGPCpre.jar [arguments...]\nCommand line arguments and formats:\n\t-Xmx<HeapSize>: maximum heap size; some datasets may require higher heap size. Example: -Xmx512m  (m or M for mega byte).\n\t-f <input file>: input data file name (with path if not on the current working directory);<input file> must be provided.\n\t-o <output file>: output file name (with path if not on the current working directory); default: DataOut.txt.\n\t-p <l:h:d:f>: preprocessing parameters; l=lower threshold, h-higher threshold, d=difference, f=fold change.\n\t-n <normalziation info>: normalization info; for log normalization type G with the base like G10 or Ge while for linear normalization type La:b where a:b is the range.\n\t-h <header info>: header info; G: first column contains genes IDs; S: first row contains samples IDs; GS or SG for both.\nExample: java -jar EGPCpre.jar -f \"DataFile/BrainPre.txt\" -o BrainPro.txt -p 20:16000:100:3 -n Ge -h GS";
        System.out.println(syntax);
    }

    public boolean hasNumber(String s)
    {
        for(int j = 0; j < s.length(); j++)
        {
            if(Character.isDigit(s.charAt(j)))
                return true;
        }

        return false;
    }

    void linearNormalization(float a, float b)
    {
        for(int i = 0; i < GENES; i++)
        {
            for(int j = 0; j < SAMPLE; j++)
                data[i][j] = a + ((data[i][j] - MINVAL) / (MAXVAL - MINVAL)) * (b - a);

        }

    }

    boolean logNormalization(String base)
    {
        boolean OK = true;
        double lnbase = 1.0D;
        if(hasNumber(base))
            lnbase = Math.log(Integer.parseInt(base));
        if(MINVAL > 0.0F)
        {
            for(int i = 0; i < GENES; i++)
            {
                for(int j = 0; j < SAMPLE; j++)
                    data[i][j] = (float)(Math.log(data[i][j]) / lnbase);

            }

            return OK;
        } else
        {
            return !OK;
        }
    }

    public static void main(String args[])
        throws IOException
    {
        boolean firstlabels = false;
        boolean firstgeneid = false;
        String datafilename = null;
        String outfilename = "DataOut.txt";
        boolean DoPreprocess = false;
        float low = 10F;
        float high = 16000F;
        float diff = 50F;
        float fold = 5F;
        float a = 0.0F;
        float b = 0.0F;
        boolean LogNorm = false;
        boolean LinearNorm = false;
        String logbase = "e";
        StringTokenizer st;
        if(args.length == 0)
        {
            SyntaxMessage();
            System.exit(0);
        }
        for(int i = 0; i < args.length; i++)
        {
            if(args[i].charAt(0) != '-')
                break;
            i++;
            switch(args[i - 1].charAt(1))
            {
            case 103: // 'g'
            case 105: // 'i'
            case 106: // 'j'
            case 107: // 'k'
            case 108: // 'l'
            case 109: // 'm'
            default:
                break;

            case 102: // 'f'
                datafilename = args[i];
                break;

            case 111: // 'o'
                outfilename = args[i];
                break;

            case 112: // 'p'
                DoPreprocess = true;
                st = new StringTokenizer(args[i], ":|");
                low = Float.parseFloat(st.nextToken());
                high = Float.parseFloat(st.nextToken());
                diff = Float.parseFloat(st.nextToken());
                fold = Float.parseFloat(st.nextToken());
                break;

            case 110: // 'n'
                if(args[i].charAt(0) == 'G' || args[i].charAt(0) == 'g')
                {
                    LogNorm = true;
                    logbase = args[i].substring(1);
                    break;
                }
                if(args[i].charAt(0) == 'L' || args[i].charAt(0) == 'l')
                {
                    LinearNorm = true;
                    st = new StringTokenizer(args[i].substring(1), ":|");
                    a = Float.parseFloat(st.nextToken());
                    b = Float.parseFloat(st.nextToken());
                }
                break;

            case 104: // 'h'
                if(args[i].toUpperCase().indexOf("G") > -1)
                    firstgeneid = true;
                if(args[i].toUpperCase().indexOf("S") > -1)
                    firstlabels = true;
                break;
            }
        }

        Preprocess ppdata = new Preprocess(firstlabels, firstgeneid);
        ppdata.readData(datafilename);
        if(DoPreprocess)
            ppdata.preprocessData(low, high, diff, fold);
        if(LogNorm)
        {
            if(!ppdata.logNormalization(logbase))
                System.out.println("Log normalization has not been done as some values are <=0.");
        } else
        if(LinearNorm)
            ppdata.linearNormalization(a, b);
        String summary = ppdata.writeData(outfilename);
        System.out.println(summary);
    }

    void preprocessData(float l, float h, float diff, float fold)
    {
        MAXVAL = l;
        MINVAL = h;
        for(int i = 0; i < GENES; i++)
        {
            for(int j = 0; j < SAMPLE; j++)
            {
                data[i][j] = Math.min(data[i][j], h);
                data[i][j] = Math.max(data[i][j], l);
            }

        }

        for(int i = 0; i < GENES; i++)
        {
            float maxg = l;
            float ming = h;
            for(int j = 0; j < SAMPLE; j++)
            {
                maxg = Math.max(data[i][j], maxg);
                ming = Math.min(data[i][j], ming);
            }

            if(maxg - ming > diff && maxg / ming > fold)
            {
                pgenelist[i] = true;
                MAXVAL = Math.max(MAXVAL, maxg);
                MINVAL = Math.min(MINVAL, ming);
            } else
            {
                pgenelist[i] = false;
            }
        }

    }

    void readData(String filename, int rows, int cols)
        throws IOException
    {
        BufferedReader fp = new BufferedReader(new FileReader(filename));
        SAMPLE = cols;
        GENES = rows;
        data = new float[GENES][SAMPLE];
        pgenelist = new boolean[GENES];
        if(IsFirstGeneIds)
            GeneIds = new String[GENES];
        for(int i = 0; i < GENES; i++)
            pgenelist[i] = true;

        if(IsFirstLabels)
        {
            String line = fp.readLine();
            StringTokenizer st = new StringTokenizer(line, "|;:\t\n");
            int k = st.countTokens();
            labels = new int[SAMPLE];
            int j = 0;
            for(; k > 0; k--)
            {
                String s = st.nextToken();
                if(hasNumber(s))
                {
                    labels[j] = Integer.parseInt(s);
                    j++;
                }
            }

        }
        String line;
        for(int i = 0; (line = fp.readLine()) != null; i++)
        {
            StringTokenizer st = new StringTokenizer(line, "|;:\t\n");
            int k = st.countTokens();
            if(IsFirstGeneIds)
            {
                GeneIds[i] = st.nextToken();
                k--;
            }
            int j = 0;
            for(; k > 0; k--)
            {
                String s = st.nextToken();
                if(hasNumber(s))
                {
                    data[i][j] = Float.parseFloat(s);
                    MAXVAL = Math.max(MAXVAL, data[i][j]);
                    MINVAL = Math.min(MINVAL, data[i][j]);
                    j++;
                }
            }

        }

        fp.close();
    }

    void readData(String filename)
        throws IOException
    {
        BufferedReader fp = new BufferedReader(new FileReader(filename));
        SAMPLE = 0;
        String line = fp.readLine();
        StringTokenizer st = new StringTokenizer(line, "|;:\t\n");
        for(int n = st.countTokens(); n > 0; n--)
        {
            String s = st.nextToken();
            if(hasNumber(s))
                SAMPLE++;
        }

        if(IsFirstLabels)
            GENES = 0;
        else
            GENES = 1;
        while((line = fp.readLine()) != null) 
        {
            if(line.length() > 0 && line.trim().length() > 0)
                GENES++;
        }
        fp.close();
        readData(filename, GENES, SAMPLE);
    }

    void standardNormalization()
    {
        for(int i = 0; i < GENES; i++)
        {
            for(int j = 0; j < SAMPLE; j++)
                data[i][j] = (data[i][j] - MEAN) / STDEV;

        }

    }

    String writeData(String filename)
        throws IOException
    {
        String summary = "#Samples=" + SAMPLE + "; #Genes=" + GENES;
        PrintWriter outfile = new PrintWriter(new FileWriter(filename));
        int i;
        if(IsFirstLabels)
        {
            outfile.print(labels[0]);
            for(i = 1; i < SAMPLE; i++)
                outfile.print("\t" + labels[i]);

            outfile.println();
        }
        int j;
        for(i = 0; i < GENES; i++)
            if(pgenelist[i])
            {
                outfile.print(data[i][0]);
                for(j = 1; j < SAMPLE; j++)
                    outfile.print("\t" + data[i][j]);

                outfile.println();
            }

        outfile.flush();
        outfile.close();
        PrintWriter gindexfile = new PrintWriter(new FileWriter("CrossRefIdx.txt"));
        gindexfile.println("Index numbers of genes for cross referencing.");
        if(IsFirstGeneIds)
            gindexfile.println("CurIdx\tPrevIdx\tGeneId");
        else
            gindexfile.println("CurIdx\tPrevIdx");
        i = 0;
        j = 0;
        for(; i < GENES; i++)
            if(pgenelist[i])
            {
                j++;
                if(IsFirstGeneIds)
                    gindexfile.println("X" + Integer.toString(j) + "\t" + Integer.toString(i + 1) + "\t" + GeneIds[i]);
                else
                    gindexfile.println("X" + Integer.toString(j) + "\t" + Integer.toString(i + 1));
            }

        gindexfile.close();
        if(j < GENES)
            summary = summary + "; #Preprocessed Genes=" + j;
        return summary;
    }

    int GENES;
    String GeneIds[];
    boolean IsFirstGeneIds;
    boolean IsFirstLabels;
    float MAXVAL;
    float MEAN;
    float MINVAL;
    int SAMPLE;
    float STDEV;
    float data[][];
    int labels[];
    boolean pgenelist[];
}
