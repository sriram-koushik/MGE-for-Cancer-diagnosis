

package MVGPC;


public class Statistics
{

    public Statistics()
    {
    }

    static double[] getMVGPCTestStat()
    {
        double stat[] = new double[2];
        stat[0] = getMean(MVGPCaccuracy[1], curMVGPC);
        stat[1] = getStdev(MVGPCaccuracy[1], curMVGPC);
        return stat;
    }

    static double[] getMVGPCTrainStat()
    {
        double stat[] = new double[2];
        stat[0] = getMean(MVGPCaccuracy[0], curMVGPC);
        stat[1] = getStdev(MVGPCaccuracy[0], curMVGPC);
        return stat;
    }

    private static double getMean(double x[], int n)
    {
        double m = 0.0D;
        for(int i = 0; i < n; i++)
            m += x[i];

        return m / (double)n;
    }

    static double[] getPTRTestStat()
    {
        double stat[] = new double[2];
        stat[0] = getMean(PTRaccuracy[1], curPTR);
        stat[1] = getStdev(PTRaccuracy[1], curPTR);
        return stat;
    }

    static double[] getPTRTrainStat()
    {
        double stat[] = new double[2];
        stat[0] = getMean(PTRaccuracy[0], curPTR);
        stat[1] = getStdev(PTRaccuracy[0], curPTR);
        return stat;
    }

    private static double getStdev(double x[], int n)
    {
        double s = 0.0D;
        double m = getMean(x, n);
        for(int i = 0; i < n; i++)
            s += (x[i] - m) * (x[i] - m);

        return Math.sqrt(s / (double)(n - 1));
    }

    static void initStat(int nPTR, int nMVGPC)
    {
        PTRaccuracy = new double[2][nPTR];
        MVGPCaccuracy = new double[2][nMVGPC];
        curPTR = 0;
        curMVGPC = 0;
    }

    static void setMVGPCAccuracy(double trainaccuracy, double testaccuracy)
    {
        MVGPCaccuracy[0][curMVGPC] = trainaccuracy;
        MVGPCaccuracy[1][curMVGPC] = testaccuracy;
        curMVGPC++;
    }

    static void setPTRAccuracy(double trainaccuracy, double testaccuracy)
    {
        PTRaccuracy[0][curPTR] = trainaccuracy;
        PTRaccuracy[1][curPTR] = testaccuracy;
        curPTR++;
    }

    private static double MVGPCaccuracy[][];
    private static double PTRaccuracy[][];
    private static int curMVGPC;
    private static int curPTR;
}
