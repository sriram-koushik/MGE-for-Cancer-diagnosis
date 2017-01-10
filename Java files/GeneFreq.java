
package MVGPC;


class GeneFreq
{

    GeneFreq(int id)
    {
        Id = id;
        freq = 0;
    }

    public int getFreq()
    {
        return freq;
    }

    public int getId()
    {
        return Id;
    }

    public void updateFreq()
    {
        freq++;
    }

    private int Id;
    private int freq;
}
