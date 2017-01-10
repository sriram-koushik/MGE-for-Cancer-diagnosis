
package MVGPC;

import java.io.Serializable;


// Referenced classes of package MVGPC:
//            Chromosome, Function, Population

public class Individual
    implements Serializable
{

    public Individual()
    {
        fitness = -1F;
        trainingaccuracy = -1F;
        testaccuracy = -1F;
        chromosome = new Chromosome();
    }

    public void CountGeneFreq()
    {
        chromosome.countGeneFreq();
    }

    public boolean execute_boolean()
    {
        Function.setIndividual(this);
        return chromosome.execute_boolean();
    }

    public double execute_double()
    {
        Function.setIndividual(this);
        return chromosome.execute_double();
    }

    public float execute_float()
    {
        Function.setIndividual(this);
        return chromosome.execute_float();
    }

    public int execute_int()
    {
        Function.setIndividual(this);
        return chromosome.execute_int();
    }

    public long execute_long()
    {
        Function.setIndividual(this);
        return chromosome.execute_long();
    }

    public Object execute_object()
    {
        Function.setIndividual(this);
        return chromosome.execute_object();
    }

    public void execute_void()
    {
        Function.setIndividual(this);
        chromosome.execute_void();
    }

    public void full(int depth, Class rtype, Function nodeSets[])
    {
        Function.setIndividual(this);
        chromosome.full(depth, rtype, nodeSets);
    }

    public Chromosome getChromosome()
    {
        return chromosome;
    }

    public float getFitness()
    {
        return fitness;
    }

    public int getSequence()
    {
        return sequence;
    }

    public void grow(int depth, Class rtype, Function nodeSets[])
    {
        Function.setIndividual(this);
        chromosome.grow(depth, rtype, nodeSets);
    }

    public int numElements()
    {
        return chromosome.numElements();
    }

    public void setFitness(float f)
    {
        fitness = f;
    }

    public String toString()
    {
        String str = new String();
        str = str + chromosome + "\n";
        return str;
    }

    Chromosome chromosome;
    float fitness;
    transient Population population;
    transient int sequence;
    float testaccuracy;
    float trainingaccuracy;
}
