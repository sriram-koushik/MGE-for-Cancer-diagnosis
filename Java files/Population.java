
package MVGPC;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Comparator;


// Referenced classes of package MVGPC:
//            Individual, Parameters, Function

public class Population
    implements Serializable
{

    public Population(int size)
    {
        individuals = new Individual[size];
        fitnessRank = new float[size];
    }

    public void create(Class rtype, Function nodeSets[])
    {
        for(int i = 0; i < individuals.length; i++)
        {
            int depth = 2 + ((Parameters.maxInitDepth - 1) * i) / individuals.length;
            individuals[i] = new Individual();
            if(i % 2 == 0)
                individuals[i].grow(depth, rtype, nodeSets);
            else
                individuals[i].full(depth, rtype, nodeSets);
        }

    }

    public Individual getIndividual(int i)
    {
        return individuals[i];
    }

    public int getSize()
    {
        return individuals.length;
    }

    public void setIndividual(int i, Individual ind)
    {
        individuals[i] = ind;
    }

    public void sort(Comparator c)
    {
        Arrays.sort(individuals, c);
        float f = 0.0F;
        for(int i = 0; i < individuals.length; i++)
        {
            fitnessRank[i] = f;
            f += individuals[i].getFitness();
        }

    }

    transient float fitnessRank[];
    Individual individuals[];
}
