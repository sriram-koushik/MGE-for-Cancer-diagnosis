
package MVGPC;


// Referenced classes of package MVGPC:
//            GeneticOperation, Individual, Chromosome, Parameters, 
//            Function

public class GeneticEvolution extends GeneticOperation
{

    public GeneticEvolution()
    {
    }

    public Individual[] cross(Individual i1, Individual i2)
    {
        Individual newIndividuals[] = {
            new Individual(), new Individual()
        };
        Chromosome newChromosomes[] = doCross(i1.chromosome, i2.chromosome);
        newIndividuals[0].chromosome = newChromosomes[0];
        newIndividuals[1].chromosome = newChromosomes[1];
        return newIndividuals;
    }
    
    public Individual[] applyGenOperator(Individual I1,Individual I2,OperatorTree op)    {
      int opSize;
      int opIndex;
        Individual newIndividuals[] = {new Individual(), new Individual() };
        Chromosome newChromosomes[]={I1.chromosome,I2.chromosome};
        
       for(int i=0;i<op.getOperatorSize();i++) {
          opIndex = op.opSet[i].getOperatorIndex();
          newChromosomes = doGenOperation(newChromosomes[0],newChromosomes[1],opIndex);
          
      }
      
      newIndividuals[0].chromosome = newChromosomes[0];
      newIndividuals[1].chromosome = newChromosomes[1];
      return newIndividuals;
             
    }
    protected static Chromosome[] doGenOperation(Chromosome c0, Chromosome c1, int opIndex)    {
       Chromosome c[] = {c0, c1 };
       int pos1,pos2,size1,size2;

       switch(opIndex)
       {
       case 4:
       case 8:
               {
               c[0] = modifyChromosome(c0,opIndex);
               c[0].redepth();              
               break;
               }
       case 5:         
       case 9: 
               {
               c[1] = modifyChromosome(c1,opIndex);
               c[1].redepth();
               break;
               }
       case 10:
               {
                 c = interchangeChromosome(c);
                 c[0].redepth();
                 c[1].redepth();
                 break;
                }
       }
       return c;

    }
    protected static Chromosome modifyChromosome(Chromosome c0,int opIndex)
    {
        int numOfFunctions,currentFunction,pos,currentPos,loopIndex,size;

        int i=0;
       // System.out.println("Inside Modify");
        Chromosome temp= new Chromosome();
        numOfFunctions = c0.numFunctions();
        if(numOfFunctions <= 1)
            return c0;

        //System.out.println(" opIndex = " + opIndex + " numOfFunctions = " + numOfFunctions);
        if(opIndex == 4 || opIndex ==5) {
            for(i=0;i<3;i++){
        
                currentFunction = Parameters.random.nextInt(numOfFunctions);
          //      System.out.println(" Current Function = " + currentFunction);
                currentPos = c0.getFunction(currentFunction);
            //    System.out.println(" Num of Functions = " + numOfFunctions + " current pos = " + currentPos + " c0.getSize = " + c0.getSize(0));
                if(currentPos == 0){
                    temp = c0;
                    break;
                }
                loopIndex = 1;
                while(loopIndex < currentFunction){
                    pos = c0.getFunction(loopIndex);
                    size = c0.getSize(pos);
                    if(size+pos < currentPos){
                        temp = new Chromosome(size,c0.functionSet);
              //          System.out.println("Size = " + size + " Num of Functions = " + numOfFunctions + " current pos = " + currentPos);
                        System.arraycopy(c0.functions,pos,temp.functions,0,size);
                        return temp;
                    }
                    loopIndex++;
                }
                
                temp=c0;
            }
        }
        if(opIndex == 8 || opIndex ==9){        
                currentFunction = Parameters.random.nextInt(numOfFunctions);
                //System.out.println(" Current Function = " + currentFunction);
                currentPos = c0.getFunction(currentFunction);
                //System.out.println(" Current Position = " + currentPos);
                if(currentPos == 0)
                        temp=c0;
                else{
                size = c0.getSize(currentPos);
                //System.out.println("Size = " + size + " Num of Functions = " + numOfFunctions + " current pos = " + currentPos);
                temp = new Chromosome(size,c0.functionSet);
                System.arraycopy(c0.functions,currentPos,temp.functions,0,size);
                }
        }

        return temp;

    }

    protected static Chromosome[] interchangeChromosome(Chromosome[] c)
    {

    Chromosome temp[] = new Chromosome[2];

    int p0,p1,s0,s1,curFunc1,curFunc2,totFunc1,totFunc2,loopIndex,totSize1,totSize2,tS0,tS1;

    totFunc1 = c[0].numFunctions();
    totFunc2 = c[1].numFunctions();
    
    tS0 = c[0].getSize(0);
    tS1 = c[1].getSize(0);
    //System.out.println("Inside interchange");
    if(totFunc1 <=0 || totFunc2 <=0)
        return c;

    //System.out.println(" totFunc1 = " + totFunc1 + " totFunc2 =" + totFunc2);
    for(loopIndex=0;loopIndex<3;loopIndex++){
        curFunc1 = Parameters.random.nextInt(totFunc1);
        curFunc2 = Parameters.random.nextInt(totFunc2);
        
        p0 = c[0].getFunction(curFunc1);
        p1 = c[1].getFunction(curFunc2);

        if(c[0].getNode(p0).getArity() == c[1].getNode(p1).getArity()){
                
                s0 = c[0].getSize(p0);
                s1 = c[1].getSize(p1);

                totSize1 = p0+s1+c[0].getSize(0)-(p0+s0);
                totSize2 = p1+s0+c[1].getSize(0) -(p1+s1);
    
      //          System.out.println(" tS0 = " + tS0 + "  tS1 = " + tS1 + " totSize1 = " + totSize1 + " TotSize2 = " + totSize2 + " p0= " + p0 + " P1 = " + p1 + " s0 =" + s0 + " s1 = " + s1);
                temp[0] = new Chromosome(totSize1,c[0].functionSet);
                temp[1] = new Chromosome(totSize2,c[1].functionSet);
                                       

                System.arraycopy(c[1].functions,p1,temp[0].functions,p0,s1);
                System.arraycopy(c[0].functions,p0,temp[1].functions,p1,s0);
                
                    if(s0 != tS0 ){
        //            System.out.println(" p0-1 = " + (p0-1) + " p0+s0+1 = " + (p0+s0+1) + " p0+s1+1 = " + (p0+s1+1) + " c[0].getSize(0)-(p0+s0) = " + (c[0].getSize(0)-(p0+s0)));
                    System.arraycopy(c[0].functions,0,temp[0].functions,0,p0);
                    
                        if((p0+s1) != totSize1){
          //                  System.out.println("inside 3 p0+s0 = " + (p0+s0) + " p0+s1 = " + (p0+s1) + " tS0-(p0+s0) = " + (tS0-(p0+s0)));
                            System.arraycopy(c[0].functions,p0+s0,temp[0].functions,p0+s1,tS0-(p0+s0));
                        }  
                    
                    }
                    
                    if(s1 != tS1 ) {
            //            System.out.println(" p1-1 = " + (p1-1) + " p1+s1+1 = " + p1+s1+1 + " p1+s0+1 = " + p1+s0+1 + " c[1].getSize(0)-(p1+s1) = " + (c[1].getSize(0)-(p1+s1)));
                        System.arraycopy(c[1].functions,0,temp[1].functions,0,p1);
                        
                        if((p1+s0) != totSize2) {
              //              System.out.println("inside 3-2 p1+s1 = " + (p1+s1) + " p1+s0 = " + (p1+s0) + " tS1-(p1+s1) = " + (tS1-(p1+s1)));
                            System.arraycopy(c[1].functions,p1+s1,temp[1].functions,p1+s0,tS1-(p1+s1));
                        }
                        
                    }
                    
                   
                return temp;

    }
    }
        return c; 
    }
        

   protected static Chromosome[] doCross(Chromosome c0, Chromosome c1)
    {
        Chromosome c[] = {
            c0, c1
        };
        int p0;
        if(Parameters.random.nextFloat() < 0.9F)
        {
            int nf = c0.numFunctions();
            if(nf == 0)
                return c;
            p0 = c0.getFunction(Parameters.random.nextInt(nf));
        } else
        {
            p0 = c0.getTerminal(Parameters.random.nextInt(c0.numTerminals()));
        }
        Class t = c0.getNode(p0).getReturnType();
        int p1;
        if(Parameters.random.nextFloat() < 0.9F)
        {
            int nf = c1.numFunctions(t);
            if(nf == 0)
                return c;
            p1 = c1.getFunction(Parameters.random.nextInt(nf), t);
        } else
        {
            int nt = c1.numTerminals(t);
            if(nt == 0)
                return c;
            p1 = c1.getTerminal(Parameters.random.nextInt(c1.numTerminals(t)), t);
        }
        int s0 = c0.getSize(p0);
        int s1 = c1.getSize(p1);
        int d0 = c0.getDepth(p0);
        int d1 = c1.getDepth(p1);
        int c0s = c0.getSize(0);
        int c1s = c1.getSize(0);
        if((d0 - 1) + s1 > Parameters.maxCrossoverDepth)
        {
            c[0] = c1;
        } else
        {
            c[0] = new Chromosome((c0s - s0) + s1, c[0].functionSet);
            System.arraycopy(c0.functions, 0, c[0].functions, 0, p0);
            System.arraycopy(c1.functions, p1, c[0].functions, p0, s1);
            System.arraycopy(c0.functions, p0 + s0, c[0].functions, p0 + s1, c0s - p0 - s0);
            c[0].redepth();
        }
        if((d1 - 1) + s0 > Parameters.maxCrossoverDepth)
        {
            c[1] = c0;
        } else
        {
            c[1] = new Chromosome((c1s - s1) + s0, c[1].functionSet);
            System.arraycopy(c1.functions, 0, c[1].functions, 0, p1);
            System.arraycopy(c0.functions, p0, c[1].functions, p1, s0);
            System.arraycopy(c1.functions, p1 + s1, c[1].functions, p1 + s0, c1s - p1 - s1);
            c[1].redepth();
        }
        return c;
    }

    protected static Chromosome doMutate(Chromosome c)
        throws CloneNotSupportedException
    {
        Chromosome newc = (Chromosome)c.clone();
        int p = -1;
        if(Parameters.random.nextFloat() < 0.9F)
        {
            int nf = c.numFunctions();
            if(nf == 0)
                return c;
            p = c.getFunction(Parameters.random.nextInt(nf));
        } else
        {
            p = c.getTerminal(Parameters.random.nextInt(c.numTerminals()));
        }
        Function snode = c.getNode(p);
        int nodetype = Parameters.GetNodeType(snode.getFunctionIndex());
        if(Parameters.CountNodeType[nodetype] > 1)
        {
            Chromosome _tmp = c;
            Function randNode = Chromosome.selectNode(nodetype, c.functionSet);
            newc.setNode(p, randNode);
            newc.redepth();
            return newc;
        } else
        {
            return c;
        }
    }

    public Individual mutate(Individual ind)
        throws CloneNotSupportedException
    {
        Chromosome newChromosome = doMutate(ind.chromosome);
        Individual newIndividual = new Individual();
        newIndividual.chromosome = newChromosome;
        return newIndividual;
    }
}
