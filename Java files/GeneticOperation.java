
package MVGPC;


// Referenced classes of package MVGPC:
//            Individual

abstract class GeneticOperation
{

    GeneticOperation()
    {
    }

    abstract Individual[] cross(Individual individual, Individual individual1);

    abstract Individual mutate(Individual individual)
        throws CloneNotSupportedException;
        
    abstract Individual[] applyGenOperator(Individual individual, Individual individual1,OperatorTree op);

}
