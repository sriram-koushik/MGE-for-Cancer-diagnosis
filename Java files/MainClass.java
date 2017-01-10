
package MVGPC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.text.DecimalFormat;

import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextArea;


// Referenced classes of package MVGPC:
//            FuncSet, Variable, Function, Population, 
//            FitnessComparator, Individual, Parameters, GeneStat, 
//            Chromosome, GeneticOperation, Statistics

public class MainClass
{

    public MainClass(int n, int g, int attrSize, String attributeinfo, int sampleSize, String funcset,int oSize,String operationSet)
        throws IOException
    {
        decimalformat = new DecimalFormat("0.00");
        GUIDisplay = false;
        RuleReturnType = Parameters.doubleClass;
        PopSize = n;
        MaxGen = g;
        ATTRIBUTE = attrSize;
        SAMPLE = sampleSize;
        random = new Random();
        GeneStat.initGeneFreq(ATTRIBUTE);
        CreateGPNodes(attributeinfo, funcset);
        opSize = GenerateOpSet(operationSet);
        opDepth = oSize;
    }

    int AddBoolTerm(String termset, Class rtype)
    {
        return rtype != Parameters.booleanClass || termset.indexOf("B") >= 0 ? 0 : 1;
    }

    int AddConstTerms(String termset, Class rtype)
    {
        if(rtype != Parameters.booleanClass)
            return 0;
        StringTokenizer terminfo = new StringTokenizer(termset, ":");
        int maxval = -1;
        while(terminfo.hasMoreTokens()) 
        {
            String tokenval = terminfo.nextToken();
            if(tokenval.charAt(0) == 'L')
            {
                terminfo.nextToken();
                maxval = Math.max(maxval, Integer.parseInt(terminfo.nextToken()));
            }
        }
        return maxval + 1;
    }

    void AssignFitness()
/*      */   {
/*  806 */     this.totalFitness = 0.0F;
/*  807 */     for (int i = 0; i < this.population.getSize(); i++) {
/*  808 */       Individual ind = this.population.getIndividual(i);
/*  809 */       ind.sequence = i;
/*  810 */       ind.population = this.population;
/*  811 */       if (ind.getFitness() == -1.0F) {
/*      */         try {
/*  813 */           ind.setFitness(RuleFitness(ind));
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  818 */           System.out.println("Exception in computing this individual:");
/*  819 */           System.out.println(ind.toString());
/*  820 */           System.out.println(ex);
/*  821 */           ex.printStackTrace();
/*  822 */           System.exit(1);
/*      */         }
/*      */       }
/*  825 */       this.totalFitness += ind.getFitness();
/*      */     }
/*      */   }
    double ConvertToNumeric(String v)
    {
        String upcasev = v.toUpperCase();
        if(upcasev.compareTo("TRUE") == 0 || upcasev.compareTo("T") == 0)
            return 1.0D;
        if(upcasev.compareTo("FALSE") == 0 || upcasev.compareTo("F") == 0)
            return 0.0D;
        else
            return Double.parseDouble(upcasev);
    }

    public float Correlation(int TP, int TN, int FP, int FN)
    {
        float C = 0.0F;
        double denom = (double)(TN + FN) * (double)(TN + FP) * (double)(TP + FN) * (double)(TP + FP);
        C = (float)(TP * TN - FP * FN) / Math.max(1.0F, (float)Math.sqrt(denom));
        return C;
    }

    int CreateFunctions(int s, String funcset)
    {
        StringTokenizer functions = new StringTokenizer(funcset, ":");
        int NumOfFunctions = functions.countTokens();
        int i = s;
        for(int j = 0; j < NumOfFunctions; j++)
        {
            String fname = functions.nextToken();
            int findex = Parameters.GetFunctionIndex(fname);
            switch(findex)
            {
            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 3: // '\003'
                nodeSets[i++] = new FuncSet(findex, 2, Parameters.doubleClass, Parameters.doubleClass, fname);
                Parameters.CountNodeType[0]++;
                break;

            case 4: // '\004'
            case 5: // '\005'
            case 6: // '\006'
            case 7: // '\007'
            case 8: // '\b'
            case 9: // '\t'
                nodeSets[i++] = new FuncSet(findex, 1, Parameters.doubleClass, Parameters.doubleClass, fname);
                Parameters.CountNodeType[1]++;
                break;

            case 11: // '\013'
            case 12: // '\f'
            case 13: // '\r'
            case 14: // '\016'
            case 15: // '\017'
            case 16: // '\020'
                nodeSets[i++] = new FuncSet(findex, 2, Parameters.booleanClass, Parameters.doubleClass, fname);
                Parameters.CountNodeType[2]++;
                break;

            case 17: // '\021'
            case 18: // '\022'
                nodeSets[i++] = new FuncSet(findex, 2, Parameters.booleanClass, Parameters.booleanClass, fname);
                Parameters.CountNodeType[3]++;
                break;

            case 19: // '\023'
                nodeSets[i++] = new FuncSet(findex, 1, Parameters.booleanClass, Parameters.booleanClass, fname);
                Parameters.CountNodeType[4]++;
                break;
            }
        }

        return NumOfFunctions;
    }

    OperatorTree GenerateOpTree(int opDepth)
    {
        OperatorTree opTree = new OperatorTree(opDepth);
        int opIndex;
        int loopIndex =0;
        int arity;
        while(true) {
           opIndex = random.nextInt(opSize) ;
           if(opSet[opIndex].getArity() <= (opDepth-loopIndex)) {
               opTree.opSet[loopIndex] = opSet[opIndex];
               loopIndex++;
           }
           
           if(loopIndex == opDepth)
            break;
        }
       
        return opTree;
    }

    int  GenerateOpSet(String operatorSet)
    {
        StringTokenizer op = new StringTokenizer(operatorSet, ":");
        int numOfOperator = op.countTokens();
        opSet = new OpSet[numOfOperator];
        int opIndex;
        int arity;
        String opName;
       
        for(int i = 0; i < numOfOperator; i++)        
        {
            opName = op.nextToken();
            opIndex = Parameters.GetOperatorIndex(opName);
            arity = Parameters.GetOperatorType(opIndex);
            opSet[i] = new OpSet(opIndex,arity,opName); 
        }
        return numOfOperator;
    }


    public void CreateGPNodes(String attrinfo, String funcset)
    {
        RuleReturnType = ReturnType(funcset);
        int i;
        for(i = 0; i < Parameters.CountNodeType.length; i++)
            Parameters.CountNodeType[i] = 0;

        int nconst = AddConstTerms(attrinfo, RuleReturnType);
        int nterms = ATTRIBUTE + AddBoolTerm(attrinfo, RuleReturnType) + nconst;
        StringTokenizer functions = new StringTokenizer(funcset, ":");
        int nfuncs = functions.countTokens();
        terminals = new Variable[nterms];
        nodeSets = new Function[nterms + nfuncs];
        CreateTerminals(0, attrinfo);
        i = ATTRIBUTE;
        if(AddBoolTerm(attrinfo, RuleReturnType) > 0)
        {
            terminals[i] = Variable.create(22, "T", Parameters.booleanClass);
            terminals[i].set(new Boolean(true));
            nodeSets[i] = terminals[i];
            i++;
            Parameters.CountNodeType[6]++;
        }
        for(int j = 0; j < nconst; j++)
        {
            terminals[i] = Variable.create(21, "C" + j, Parameters.doubleClass);
            terminals[i].set(new Double(j));
            nodeSets[i] = terminals[i];
            i++;
            Parameters.CountNodeType[5]++;
        }

        CreateFunctions(i, funcset);
    }

    void CreateTerminals(int s, String AttrsInfo)
    {
        StringTokenizer attrinfo = new StringTokenizer(AttrsInfo, ":");
        int nattrtypes = attrinfo.countTokens();
        int startindex = 1;
        int endindex = ATTRIBUTE;
        int maxval = 0;
        for(int i = 0; i < nattrtypes;)
        {
            String atype = attrinfo.nextToken();
            int index;
            Class AttributeType;
            switch(atype.charAt(0))
            {
            case 78: // 'N'
                AttributeType = Parameters.doubleClass;
                startindex = Integer.parseInt(atype.substring(1));
                endindex = Integer.parseInt(attrinfo.nextToken());
                index = 20;
                Parameters.CountNodeType[5] += (endindex - startindex) + 1;
                i += 2;
                break;

            case 76: // 'L'
                AttributeType = Parameters.doubleClass;
                startindex = Integer.parseInt(atype.substring(1));
                endindex = Integer.parseInt(attrinfo.nextToken());
                index = 20;
                Parameters.CountNodeType[5] += (endindex - startindex) + 1;
                maxval = Math.max(maxval, Integer.parseInt(attrinfo.nextToken()));
                i += 3;
                break;

            case 66: // 'B'
                AttributeType = Parameters.booleanClass;
                startindex = Integer.parseInt(atype.substring(1));
                endindex = Integer.parseInt(attrinfo.nextToken());
                index = 22;
                Parameters.CountNodeType[6] += (endindex - startindex) + 1;
                i += 2;
                break;

            default:
                AttributeType = Parameters.doubleClass;
                index = index = 20;
                i++;
                break;
            }
            for(int j = startindex; j <= endindex; j++)
            {
                terminals[(s + j) - 1] = Variable.create(index, "X" + j, AttributeType);
                nodeSets[(s + j) - 1] = terminals[(s + j) - 1];
            }

        }

    }

    public Individual FitnessProportionateSelect()
    {
        float tf = getTotalFitness();
        float chosen = random.nextFloat() * tf;
        int num = 0;
        Population pop = getPopulation();
        num = Arrays.binarySearch(pop.fitnessRank, chosen);
        if(num >= 0)
            return pop.getIndividual(num);
        else
            return pop.getIndividual(-num - 2);
    }

    public void GUIOutput(JTextArea JTABRule, JLabel ruleinfo, JLabel geninfo, JTable predictions, JProgressBar pbar, int maxpbarval)
    {
        GUIDisplay = true;
        JTextAreaBRule = JTABRule;
        JLabelGenInfo = geninfo;
        JLabelRuleInfo = ruleinfo;
        JTablePredictions = predictions;
        CurRowPTable = 0;
        decimalformat.setMaximumFractionDigits(2);
        progressbar = pbar;
        MaxPBarValue = maxpbarval;
    }

    public Individual GreedyoverSelect()
/*      */   {
/*  338 */     float sum = 0.0F,origChosen,chosen;
               int num;
                       
/*  339 */     int popSize = getPopulation().getSize();
/*  340 */     float cutoff = popSize < 8000 ? 0.08F : popSize < 4000 ? 0.16F : popSize < 2000 ? 0.32F : 0.04F;
/*      */ 
/*  347 */     cutoff *= getTotalFitness();
/*      */ 
/*  362 */     int cutoffIndex = popSize - 1;
/*  363 */     while (sum <= cutoff)
/*  364 */       sum += getPopulation().getIndividual(cutoffIndex--).getFitness();
/*  365 */     cutoffIndex++;
/*  366 */     sum -= getPopulation().getIndividual(cutoffIndex - 1).getFitness();
/*      */ 
/*  369 */     cutoff = sum;
/*      */ 
/*  372 */     if (this.random.nextFloat() < 0.2D) {
/*  373 */       chosen = this.random.nextFloat() * (getTotalFitness() - cutoff);
/*  374 */       origChosen = chosen;
/*  375 */       num = 0;
/*  376 */       for (num = 0; (chosen >= 0.0F) && (num < cutoffIndex - 1); num++)
/*      */         try {
/*  378 */           chosen -= getPopulation().getIndividual(num).getFitness();
/*      */         }
/*      */         catch (ArrayIndexOutOfBoundsException ex) {
/*  381 */           System.out.println("Group II:");
/*  382 */           System.out.println("pop size = " + getPopulation().getSize());
/*  383 */           System.out.println("num = " + num);
/*  384 */           System.out.println("chosen = " + chosen);
/*  385 */           System.out.println("origChosen = " + origChosen);
/*  386 */           System.out.println("cutoff = " + cutoff);
/*  387 */           System.out.println("cutoffIndex = " + cutoffIndex);
/*  388 */           System.out.println("World fitness = " + getTotalFitness());
/*  389 */           throw ex;
/*      */         }
/*  391 */       if (num >= cutoffIndex - 1)
/*  392 */         num = cutoffIndex - 1;
/*  393 */       return getPopulation().getIndividual(num);
/*      */     }
/*      */ 
/*  396 */     chosen = this.random.nextFloat() * cutoff;
/*  397 */     origChosen = chosen;
/*      */ 
/*  399 */     for (num = cutoffIndex; (chosen >= 0.0F) && (num < popSize); num++)
/*      */       try {
/*  401 */         chosen -= getPopulation().getIndividual(num).getFitness();
/*      */       }
/*      */       catch (ArrayIndexOutOfBoundsException ex) {
/*  404 */         System.out.println("Group I:");
/*  405 */         System.out.println("pop size = " + getPopulation().getSize());
/*  406 */         System.out.println("num = " + num);
/*  407 */         System.out.println("chosen = " + chosen);
/*  408 */         System.out.println("origChosen = " + origChosen);
/*  409 */         System.out.println("cutoff = " + cutoff);
/*  410 */         System.out.println("cutoffIndex = " + cutoffIndex);
/*  411 */         System.out.println("World fitness = " + getTotalFitness());
/*  412 */         throw ex;
/*      */       }
/*  414 */     if (num >= popSize - 1)
/*  415 */       num = popSize - 1;
/*  416 */     return getPopulation().getIndividual(num);
/*      */   }

    public void InitPop(int targetClass)
    {
        TargetClass = targetClass;
        population = new Population(PopSize);
        population.create(RuleReturnType, nodeSets);
        AssignFitness();
        population.sort(new FitnessComparator());
    }

    public void MVGPC(int run, int member, PrintWriter resultfile, PrintWriter statfile)
        throws IOException
    {
        int nRULE;
        int pVote[][];
        int Errors[];
        if(GUIDisplay)
        {
            String rowvalue = "*******:*******:Run=" + run + ":*******:*******";
            CurRowPTable = WriteTableRow(JTablePredictions, CurRowPTable, rowvalue);
        }
        nRULE = nCLASS;
        if(nCLASS == 2)
            nRULE = 1;
        if(member < 3)
            member = 3;
        pVote = new int[SAMPLE][nCLASS];
        Errors = new int[SAMPLE];
        for(int i = 0; i < SAMPLE; i++)
        {
            for(int k = 0; k < nCLASS; k++)
                pVote[i][k] = 0;

        }
        try
        {
        for(int i = 0; i < member && Parameters.RunningMode; i++)
        {
            for(int k = 0; k < SAMPLE; k++)
                Errors[k] = 0;

            for(int k = 0; k < nRULE && Parameters.RunningMode; k++)
            {
                if(GUIDisplay)
                {
                    if(nRULE == 1)
                        JLabelRuleInfo.setText("Evolving rule:" + Integer.toString(i + 1));
                    else
                        JLabelRuleInfo.setText("Evolving rule for set:" + Integer.toString(i + 1) + " class:" + k);
                }
                if(GUIDisplay)
                    JTextAreaBRule.setText("Generating initial population!");
                    if(opDepth >0 )
                        opTree = GenerateOpTree(opDepth);
                InitPop(k);
                Individual bestRule = OneRun();
                UpdateVotes(bestRule, pVote, Errors);
                PrintRule(bestRule, i + 1, k, resultfile);
                GeneStat.countGeneFreq(bestRule.chromosome);
                if(GUIDisplay && Parameters.RunningMode)
                {
                    int taskprogress = (run - 1) * nRULE * member + i * nRULE + k + 1;
                    progressbar.setValue((taskprogress * 100) / MaxPBarValue);
                }
            }

            WriteRuleStat(i + 1, Errors, statfile);
        }

        WriteMVGPCPredictions(pVote, nCLASS, resultfile, statfile);
        }
        catch(Exception ex)
        {
        System.out.println(ex);
        ex.printStackTrace();
        resultfile.close();
        }
    }

    public Individual OneRun()
        throws CloneNotSupportedException
    {
        Individual best = getBestIndividual();
        if(!GUIDisplay)
            System.out.println("0\t" + RuleFitness(best) + "\t" + getTotalFitness() / (float)population.getSize());
        if(best.getFitness() >= 1.0F)
            return best;
        for(int i = 1; i <= MaxGen && Parameters.RunningMode; i++)
        {
            if(opSize >0)
                nextMetaGeneration();
            else
                nextGeneration();
            best = getBestIndividual();
            if(GUIDisplay)
            {
                JLabelGenInfo.setText("Generation:" + i + "    Best Fitness: " + best.getFitness());
                JTextAreaBRule.setText(best.toString());
            } else
            {
                System.out.println(i + "\t" + best.getFitness() + "\t" + getTotalFitness() / (float)population.getSize());
            }
            if(best.getFitness() >= 1.0F)
                return best;
        }

        return best;
    }

    public void PrintRule(Individual ind, int setindex, int ruleindex, PrintWriter resultfile)
    {
        if(nCLASS == 2)
            resultfile.print("Rule " + setindex + ":");
        else
            resultfile.print("Set " + setindex + "/Rule for class " + ruleindex + ":");
        TestStatistics(ind);
        resultfile.print("(" + ind.getFitness() + "," + getTotalFitness() / (float)population.getSize() + ")");
        int TP = ind.chromosome.TP;
        int TN = ind.chromosome.TN;
        int FP = ind.chromosome.FP;
        int FN = ind.chromosome.FN;
        int tTP = ind.chromosome.testTP;
        int tTN = ind.chromosome.testTN;
        int tFP = ind.chromosome.testFP;
        int tFN = ind.chromosome.testFN;
        resultfile.print("(" + TP + "," + TN + "," + FP + "," + FN + ":" + ind.trainingaccuracy + ")");
        resultfile.println("(" + tTP + "," + tTN + "," + tFP + "," + tFN + ":" + ind.testaccuracy + ")");
        resultfile.println(ind.chromosome);
        resultfile.flush();
    }

    public void ReadData(String datafile, String validfile, boolean uciml, int nclass)
        throws IOException
    {
        nCLASS = nclass;
        data = new double[SAMPLE][];
        label = new int[SAMPLE];
        int ncols = 0;
        for(int i = 0; i < data.length; i++)
            data[i] = new double[ATTRIBUTE];

        BufferedReader fp = new BufferedReader(new FileReader(datafile));
        int j;
        String s;
        for(j = 0; (s = fp.readLine()) != null; j++)
        {
            StringTokenizer st = new StringTokenizer(s, " \t\n\r\f:,;");
            int k = st.countTokens();
            if(uciml)
            {
                label[j] = Integer.parseInt(st.nextToken());
                for(int i = 1; i < k; i++)
                    data[j][i - 1] = ConvertToNumeric(st.nextToken());

            } else
            {
                if(j == 0)
                {
                    for(int i = 0; i < k; i++)
                        label[i] = Integer.parseInt(st.nextToken());

                    ncols = k;
                } else
                {
                    for(int i = 0; i < k; i++)
                        data[i][j - 1] = ConvertToNumeric(st.nextToken());

                }
            }
        }

        fp.close();
        if(validfile != null)
        {
            if(uciml)
                TRAINSIZE = j;
            else
                TRAINSIZE = ncols;
            trainset = new int[TRAINSIZE];
            for(int i = 0; i < TRAINSIZE; i++)
                trainset[i] = i;

            fp = new BufferedReader(new FileReader(validfile));
            String line;
            for(j = 0; (line = fp.readLine()) != null; j++)
            {
                StringTokenizer st = new StringTokenizer(line, " \t\n\r\f:,;");
                int k = st.countTokens();
                if(uciml)
                {
                    label[j + TRAINSIZE] = Integer.parseInt(st.nextToken());
                    for(int i = 1; i < k; i++)
                        data[j + TRAINSIZE][i - 1] = ConvertToNumeric(st.nextToken());

                } else
                {
                    if(j == 0)
                    {
                        for(int i = 0; i < k; i++)
                            label[i + TRAINSIZE] = Integer.parseInt(st.nextToken());

                        ncols = k;
                    } else
                    {
                        for(int i = 0; i < k; i++)
                            data[i + TRAINSIZE][j - 1] = ConvertToNumeric(st.nextToken());

                    }
                }
            }

            fp.close();
            if(uciml)
                TESTSIZE = j;
            else
                TESTSIZE = ncols;
            testset = new int[TESTSIZE];
            for(int i = 0; i < TESTSIZE; i++)
                testset[i] = i + TRAINSIZE;

        }
    }

    public void ReadMicroarrayData(String GeneExpressionFile, int nclass)
        throws IOException
    {
        nCLASS = nclass;
        data = new double[SAMPLE][];
        label = new int[SAMPLE];
        for(int i = 0; i < data.length; i++)
            data[i] = new double[ATTRIBUTE];

        BufferedReader fp = new BufferedReader(new FileReader(GeneExpressionFile));
        int j = 0;
        String s;
        while((s = fp.readLine()) != null) 
        {
            StringTokenizer st = new StringTokenizer(s, " \t\n\r\f:,;");
            int k = st.countTokens();
            for(int i = 0; i < k; i++)
                if(j == 0)
                    label[i] = Integer.parseInt(st.nextToken());
                else
                    data[i][j - 1] = ConvertToNumeric(st.nextToken());

            if(++j == ATTRIBUTE)
                break;
        }
        fp.close();
    }

    public void ReadUCIMLData(String GeneExpressionFile, int nclass)
        throws IOException
    {
        nCLASS = nclass;
        data = new double[SAMPLE][];
        label = new int[SAMPLE];
        for(int i = 0; i < data.length; i++)
            data[i] = new double[ATTRIBUTE];

        BufferedReader fp = new BufferedReader(new FileReader(GeneExpressionFile));
        int j = 0;
        String s;
        while((s = fp.readLine()) != null) 
        {
            StringTokenizer st = new StringTokenizer(s, " \t\n\r\f:,;");
            int k = st.countTokens();
            label[j] = Integer.parseInt(st.nextToken());
            for(int i = 1; i < k; i++)
                data[j][i - 1] = ConvertToNumeric(st.nextToken());

            if(++j == SAMPLE)
                break;
        }
        fp.close();
    }

    double RealizeOutput(Individual ind)
/*      */   {
/*      */     try
/*      */     {
/*  693 */       if (this.RuleReturnType == Parameters.booleanClass) {
/*  694 */         boolean ovalues = ind.execute_boolean();
/*  695 */         if (ovalues) {
/*  696 */           return 100.0D;
/*      */         }
/*  698 */         return -100.0D;
/*      */       }
/*  700 */       return ind.execute_double();
/*      */     }
/*      */     catch (ArithmeticException ex) {
/*  703 */       System.out.println("Error!");
/*  704 */       System.out.println(ind);
                throw ex;
/*  705 */     }
/*      */   }

    Class ReturnType(String funcset)
    {
        String loperators[] = {
            "AND", "OR", "NOT", "<=", ">=", "=", "<>", ">", "<"
        };
        for(int i = 0; i < loperators.length; i++)
            if(funcset.indexOf(loperators[i]) > -1)
                return Parameters.booleanClass;

        return Parameters.doubleClass;
    }

    public float RuleFitness(Individual ind)
    {
        float fitness = 0.0F;
        float correct = 0.0F;
        int TP = 0;
        int TN = 0;
        int FP = 0;
        int FN = 0;
        for(int i = 0; i < TRAINSIZE; i++)
        {
            for(int j = 0; j < ATTRIBUTE; j++)
                SetTerminalValue(j, data[trainset[i]][j]);

            try
            {
                float error = 0.0F;
                double ovalues = RealizeOutput(ind);
                if(ovalues >= 0.0D && label[trainset[i]] == TargetClass)
                    TP++;
                else
                if(ovalues < 0.0D && label[trainset[i]] != TargetClass)
                    TN++;
                else
                if(ovalues >= 0.0D && label[trainset[i]] != TargetClass)
                {
                    FP++;
                    error++;
                } else
                if(ovalues < 0.0D && label[trainset[i]] == TargetClass)
                {
                    FN++;
                    error++;
                }
                if(error == 0.0F)
                    correct++;
            }
            catch(ArithmeticException ex)
            {
                System.out.println("Error!");
                System.out.println(ind);
                throw ex;
            }
        }

        float C = Correlation(TP, TN, FP, FN);
        ind.chromosome.SetFitness(TP, TN, FP, FN);
        fitness = (1.0F + C) / 2.0F;
        ind.trainingaccuracy = correct;
        return fitness;
    }

    void RuleInterpret(PrintWriter resultfile, int c)
    {
        resultfile.println("\nMeaning of different values in a rule output: (a,b)(c,d,e,f:g)(h,i,j,k:l)");
        resultfile.print("a=fitness of the best rule; b=average fitness of the population; c, d, e, f, g are the number of true positives, ");
        resultfile.print("true negatives, false positives, false negatives, and correct predictions respectively in the training data ");
        resultfile.println("while h, i, j, k, and l are those values in the test data.\n");
        String ruleinfo = "Class Prediction: IF (";
        if(RuleReturnType == Parameters.booleanClass)
            ruleinfo = ruleinfo + "S-expression) THEN ";
        else
            ruleinfo = ruleinfo + "S-expression>=0) THEN ";
        if(c == 2)
            ruleinfo = ruleinfo + "Class0 ELSE Class1.";
        else
            ruleinfo = ruleinfo + "TargetClass ELSE Other.";
        resultfile.println(ruleinfo);
    }

    void SetTerminalValue(int index, double value)
    {
        if(terminals[index].returnType == Parameters.booleanClass)
        {
            if(value == 1.0D)
                terminals[index].set(new Boolean(true));
            else
                terminals[index].set(new Boolean(false));
        } else
        {
            terminals[index].set(new Double(value));
        }
    }

    static void ShowSyntax()
    {
        String syntax = "Usage: java [-Xmx<heapsize>] -jar EGPCcom.jar [arguments...]\nCommand line arguments and formats:\n\t-Xmx<HeapSize>: maximum heap size; some datasets may require higher heap size. Example: -Xmx512m  (m or M for mega byte).\n\t-u: UCIML format; default (if it is omitted) is Microarray format.\n\t-d <datafile>: data file name (with path if not on the current working directory); <datafile> must be provided.\n\t-v <validationfile>: validation file name (with path if not on the current working directory); if it is not provided, the training information must be provide under the argument \u2013t below.\n\t-s <sample size>: number of samples; must be provided.\n\t-a <attribute size>: number of attributes; must be provided.\n\t-A <attribute info>: attribute information; default is that all attributes are numeric. See Readme.txt or Readme.pdf for details about attribute information.\n\t-t <training info>: training subset information; the training information can be either the file name (with path if not on the current working directory) containing the indexes of training samples or training sizes of each type of sample delimited by colon as 179:106.\n\t-c <classes>: number of classes; default is 2.\n\t-m <ensemble size>: ensemble size; default is 3.\n\t-F <functions>: functions to be used; functions are delimited by colon (:) and the default functions are \"+:-:/:*:sqr:sqrt\". Note here that the functions' string must be within double quotations (\" \").\n\t-p <population size>: population size; default is 1000.\n\t-g <max gen>: maximum number of generations; default is 50.\n\t-r <max run>: number of trials or repetitions; default is 20.\n";
        System.out.println(syntax);
    }

    int SplitDataFixed(String trainfile)
        throws IOException
    {
        boolean selected[] = new boolean[SAMPLE];
        BufferedReader fp = new BufferedReader(new FileReader(trainfile));
        int i = 0;
        TRAINSIZE = 0;
        for(i = 0; i < selected.length; i++)
            selected[i] = false;

        String s;
        while((s = fp.readLine()) != null) 
        {
            i = Integer.parseInt(s) - 1;
            selected[i] = true;
            TRAINSIZE++;
        }
        fp.close();
        trainset = new int[TRAINSIZE];
        TESTSIZE = SAMPLE - TRAINSIZE;
        testset = new int[TESTSIZE];
        i = 0;
        int j = 0;
        int k = 0;
        for(; i < SAMPLE; i++)
            if(selected[i])
            {
                trainset[j] = i;
                j++;
            } else
            {
                testset[k] = i;
                k++;
            }

        return TRAINSIZE;
    }

    void SplitDataRandom(int trainsamples[])
    {
        int c = trainsamples.length;
        int sampledist[] = new int[c];
        int counter[] = new int[c];
        boolean selected[] = new boolean[SAMPLE];
        TRAINSIZE = 0;
        int i;
        for(i = 0; i < c; i++)
            TRAINSIZE += trainsamples[i];

        TESTSIZE = SAMPLE - TRAINSIZE;
        for(i = 0; i < c; i++)
            sampledist[i] = 0;

        for(i = 0; i < SAMPLE; i++)
        {
            sampledist[label[i]]++;
            selected[i] = false;
        }

        for(i = 0; i < c; i++)
            counter[i] = 0;

        trainset = new int[TRAINSIZE];
        testset = new int[TESTSIZE];
        for(i = 0; i < TRAINSIZE;)
        {
            int r = random.nextInt(SAMPLE);
            if(!selected[r] && counter[label[r]] < trainsamples[label[r]])
            {
                selected[r] = true;
                counter[label[r]]++;
                i++;
            }
        }

        i = 0;
        int j = 0;
        int k = 0;
        for(; i < SAMPLE; i++)
            if(selected[i])
            {
                trainset[j] = i;
                j++;
            } else
            {
                testset[k] = i;
                k++;
            }

    }

    public void TestStatistics(Individual ind)
    {
        float correct = 0.0F;
        int TP = 0;
        int TN = 0;
        int FP = 0;
        int FN = 0;
        for(int i = 0; i < TESTSIZE; i++)
        {
            for(int j = 0; j < ATTRIBUTE; j++)
                SetTerminalValue(j, data[testset[i]][j]);

            try
            {
                float error = 0.0F;
                double ovalues = RealizeOutput(ind);
                if(ovalues >= 0.0D && label[testset[i]] == TargetClass)
                    TP++;
                else
                if(ovalues < 0.0D && label[testset[i]] != TargetClass)
                    TN++;
                else
                if(ovalues >= 0.0D && label[testset[i]] != TargetClass)
                {
                    FP++;
                    error++;
                } else
                if(ovalues < 0.0D && label[testset[i]] == TargetClass)
                {
                    FN++;
                    error++;
                }
                if(error == 0.0F)
                    correct++;
            }
            catch(ArithmeticException ex)
            {
                System.out.println("Error!");
                System.out.println(ind);
                throw ex;
            }
        }

        ind.testaccuracy = correct;
        ind.chromosome.SetTestResults(TP, TN, FP, FN);
    }

    public void UpdateVotes(Individual ind, int pvote[][], int errors[])
    {
        for(int i = 0; i < SAMPLE; i++)
        {
            for(int j = 0; j < ATTRIBUTE; j++)
                SetTerminalValue(j, data[i][j]);

            try
            {
                double ovalues = RealizeOutput(ind);
                if(ovalues >= 0.0D)
                    pvote[i][TargetClass]++;
                else
                if(nCLASS == 2)
                    pvote[i][1 - TargetClass]++;
                if(ovalues >= 0.0D && label[i] != TargetClass || ovalues < 0.0D && label[i] == TargetClass)
                    errors[i]++;
            }
            catch(ArithmeticException ex)
            {
                System.out.println("Error!");
                System.out.println(ind);
                throw ex;
            }
        }

    }

    public void WriteGeneFreq(PrintWriter genefreq)
    {
        GeneStat.sortGeneFreq();
        int nrow = ATTRIBUTE;
        for(int i = 0; i < nrow; i++)
            genefreq.println(Integer.toString(i + 1) + "\t" + "X" + GeneStat.getId(i) + "\t" + GeneStat.getFreq(i));

    }

    public void WriteMVGPCPredictions(int pvote[][], int c, PrintWriter resultfile, PrintWriter statfile)
    {
        int traincorrect = 0;
        int testcorrect = 0;
        for(int i = 0; i < TRAINSIZE; i++)
        {
            int predicted = -1;
            int maxvote = 0;
            for(int j = 0; j < c; j++)
            {
                if(pvote[trainset[i]][j] > maxvote)
                {
                    maxvote = pvote[trainset[i]][j];
                    predicted = j;
                } else
                if(pvote[trainset[i]][j] == maxvote)
                {
                    boolean takenextone = random.nextBoolean();
                    if(takenextone)
                    {
                        maxvote = pvote[trainset[i]][j];
                        predicted = j;
                    }
                }
            }

            if(label[trainset[i]] == predicted)
                traincorrect++;
        }

        for(int i = 0; i < TESTSIZE; i++)
        {
            int predicted = -1;
            int maxvote = 0;
            for(int j = 0; j < c; j++)
            {
                if(pvote[testset[i]][j] > maxvote)
                {
                    maxvote = pvote[testset[i]][j];
                    predicted = j;
                } else
                if(pvote[testset[i]][j] == maxvote)
                {
                    boolean takenextone = random.nextBoolean();
                    if(takenextone)
                    {
                        maxvote = pvote[testset[i]][j];
                        predicted = j;
                    }
                }
            }

            if(label[testset[i]] == predicted)
                testcorrect++;
        }

        double tracc = ((double)traincorrect * 100D) / (double)TRAINSIZE;
        double tsacc = ((double)testcorrect * 100D) / (double)TESTSIZE;
        statfile.println("MVGPC\t" + traincorrect + "\t" + testcorrect + "\t" + decimalformat.format(tracc) + "\t" + decimalformat.format(tsacc));
        resultfile.println("MVGPC correct::training=" + traincorrect + "\ttest=" + testcorrect);
        Statistics.setMVGPCAccuracy(tracc, tsacc);
        statfile.flush();
        resultfile.flush();
        if(GUIDisplay)
        {
            String rowvalue = "MVGPC:" + traincorrect + ":" + testcorrect + ":" + decimalformat.format(tracc) + ":" + decimalformat.format(tsacc);
            CurRowPTable = WriteTableRow(JTablePredictions, CurRowPTable, rowvalue);
        }
    }

    public void WriteRuleStat(int n, int errors[], PrintWriter resultfile)
    {
        int CorrectTraining = 0;
        int CorrectTest = 0;
        for(int i = 0; i < TRAINSIZE; i++)
            if(errors[trainset[i]] <= 0)
                CorrectTraining++;

        for(int i = 0; i < TESTSIZE; i++)
            if(errors[testset[i]] <= 0)
                CorrectTest++;

        double tracc = ((double)CorrectTraining * 100D) / (double)TRAINSIZE;
        double tsacc = ((double)CorrectTest * 100D) / (double)TESTSIZE;
        resultfile.println(n + "\t" + CorrectTraining + "\t" + CorrectTest + "\t" + decimalformat.format(tracc) + "\t" + decimalformat.format(tsacc));
        Statistics.setPTRAccuracy(tracc, tsacc);
        if(GUIDisplay)
        {
            String rowvalue = n + ":" + CorrectTraining + ":" + CorrectTest + ":" + decimalformat.format(tracc) + ":" + decimalformat.format(tsacc);
            CurRowPTable = WriteTableRow(JTablePredictions, CurRowPTable, rowvalue);
        }
        resultfile.flush();
    }

    int WriteTableRow(JTable jtable, int nrow, String rowstring)
    {
        StringTokenizer columnvalues = new StringTokenizer(rowstring, ":");
        int ncols = columnvalues.countTokens();
        for(int i = 0; i < ncols; i++)
            jtable.getModel().setValueAt(columnvalues.nextToken(), nrow, i);

        return ++nrow;
    }

    public void WriteVotes(int pvote[][], int c, int accurate[], PrintWriter resultfile)
    {
        resultfile.print("Serial:");
        for(int i = 0; i < TESTSIZE; i++)
        {
            int s = testset[i] + 1;
            resultfile.print("\t" + s);
        }

        resultfile.print("\nOriginal:");
        for(int i = 0; i < TESTSIZE; i++)
            resultfile.print("\t" + label[testset[i]]);

        resultfile.print("\nPredicted:");
        for(int i = 0; i < TESTSIZE; i++)
        {
            int predicted = -1;
            int maxvote = 0;
            for(int j = 0; j < c; j++)
            {
                if(pvote[testset[i]][j] > maxvote)
                {
                    maxvote = pvote[testset[i]][j];
                    predicted = j;
                } else
                if(pvote[testset[i]][j] == maxvote)
                {
                    boolean takenextone = random.nextBoolean();
                    if(takenextone)
                    {
                        maxvote = pvote[testset[i]][j];
                        predicted = j;
                    }
                }
            }

            resultfile.print("\t" + predicted);
        }

        for(int j = 0; j < c; j++)
        {
            resultfile.print("\nClass" + j);
            for(int i = 0; i < TESTSIZE; i++)
                resultfile.print("\t" + pvote[testset[i]][j]);

        }

        resultfile.println();
        resultfile.flush();
    }

    public void create(int popSize, Class rtype, Function nodeSets[])
    {
        population = new Population(popSize);
        population.create(rtype, nodeSets);
        AssignFitness();
        population.sort(new FitnessComparator());
    }

    public Individual getBestIndividual()
    {
        return population.getIndividual(population.getSize() - 1);
    }

    public static int getMaxChromosomes()
    {
        return Parameters.maxChromosomes;
    }

    public Population getPopulation()
    {
        return population;
    }

    public float getTotalFitness()
    {
        return totalFitness;
    }

    public Individual getWorstIndividual()
    {
        return population.getIndividual(0);
    }

    public static void main(String args[])
        throws IOException
    {
        String DataFile;
        String ValidationFile;
        int POPSIZE;
        int ATTRIBUTE;
        int SAMPLE;
        int TRAINSIZE;
        int NCLASS;
        int MAXGEN;
        int MAXRUN;
        int MEMBERS;
        boolean UCIML;
        int trainsamples[];
        String funcset;
        String OperatorSet;
        int OperatorDepth;
        String attrTypes;
        String trainsetinfo;
        Parameters.RunningMode = true;
        DataFile = null;
        ValidationFile = null;
        POPSIZE = 1000;
        ATTRIBUTE = 0;
        SAMPLE = 0;
        TRAINSIZE = 0;
        int TESTSIZE = 0;
        NCLASS = 2;
        MAXGEN = 50;
        MAXRUN = 20;
        MEMBERS = 3;
        boolean FIXEDSPLIT = false;
        UCIML = false;
        trainsamples = new int[NCLASS];
        funcset = "+:-:/:*:sqr:sqrt";
        OperatorSet="";
        OperatorDepth=0;
        attrTypes = "N";
        trainsetinfo = " ";
        if(args.length == 0)
        {
            ShowSyntax();
            System.exit(0);
        }
        for(int i = 0; i < args.length; i++)
        {
            if(args[i].charAt(0) != '-')
                break;
            i++;
            switch(args[i - 1].charAt(1))
            {
            case 117: // 'u'
                UCIML = true;
                i--;
                break;

            case 100: // 'd'
                DataFile = args[i];
                break;

            case 118: // 'v'
                ValidationFile = args[i];
                break;

            case 112: // 'p'
                POPSIZE = Integer.parseInt(args[i]);
                break;

            case 115: // 's'
                SAMPLE = Integer.parseInt(args[i]);
                break;

            case 97: // 'a'
                ATTRIBUTE = Integer.parseInt(args[i]);
                break;

            case 65: // 'A'
                attrTypes = args[i].toUpperCase();
                break;

            case 114: // 'r'
                MAXRUN = Integer.parseInt(args[i]);
                break;

            case 103: // 'g'
                MAXGEN = Integer.parseInt(args[i]);
                break;

            case 116: // 't'
                trainsetinfo = args[i];
                break;

            case 99: // 'c'
                NCLASS = Integer.parseInt(args[i]);
                break;

            case 109: // 'm'
                MEMBERS = Integer.parseInt(args[i]);
                break;

            case 70: // 'F'
                funcset = args[i].toUpperCase();
                break;
            case 111: // 'o'
                    OperatorSet = args[i].toUpperCase();
                    break;
            case 79: // O'
                    OperatorDepth = Integer.parseInt(args[i].toUpperCase());
                    break;
            }
        }

        if(attrTypes.compareTo("N") == 0)
            attrTypes = "N1:" + ATTRIBUTE;
        try
        {
        MainClass mvgpcmain = new MainClass(POPSIZE, MAXGEN, ATTRIBUTE, attrTypes, SAMPLE, funcset,OperatorDepth,OperatorSet);
        
        if(ValidationFile != null && ValidationFile.length() > 0 && ValidationFile.trim().length() > 0)
        {
            mvgpcmain.ReadData(DataFile, ValidationFile, UCIML, NCLASS);
            FIXEDSPLIT = true;
            TRAINSIZE = mvgpcmain.TRAINSIZE;
            TESTSIZE = mvgpcmain.TESTSIZE;
        } else
        {
            if(UCIML)
                mvgpcmain.ReadUCIMLData(DataFile, NCLASS);
            else
                mvgpcmain.ReadMicroarrayData(DataFile, NCLASS);
            if(trainsetinfo.indexOf(":") > -1)
            {
                StringTokenizer traindist = new StringTokenizer(trainsetinfo, ":");
                int c = traindist.countTokens();
                for(int k = 0; k < c; k++)
                {
                    trainsamples[k] = Integer.parseInt(traindist.nextToken());
                    TRAINSIZE += trainsamples[k];
                }

                FIXEDSPLIT = false;
            } else
            {
                FIXEDSPLIT = true;
                TRAINSIZE = mvgpcmain.SplitDataFixed(trainsetinfo);
            }
            TESTSIZE = SAMPLE - TRAINSIZE;
        }
        String DataFileName = Parameters.getFileName(DataFile);
        String OutputFile = "Rules" + DataFileName;
        PrintWriter ResultFileRule = new PrintWriter(new FileWriter(OutputFile));
        OutputFile = "Accuracy" + DataFileName;
        PrintWriter ResultFileStat = new PrintWriter(new FileWriter(OutputFile));
        OutputFile = "GeneFreq" + DataFileName;
        PrintWriter ResultFileGene = new PrintWriter(new FileWriter(OutputFile));
        ResultFileRule.println("Cancer File=" + DataFile);
        ResultFileRule.println("Population=" + POPSIZE + "\tIndividual Length(Max)=" + Parameters.maxSize);
        ResultFileRule.println("Genes=" + ATTRIBUTE + "\tSamples=" + SAMPLE + "\tTraining Size=" + TRAINSIZE + "\tTest Size=" + TESTSIZE);
        mvgpcmain.RuleInterpret(ResultFileRule, NCLASS);
        ResultFileRule.flush();
        ResultFileStat.println("Cancer File=" + DataFile);
        ResultFileStat.println("Number of rules per class=" + MEMBERS);
        ResultFileStat.println("Set/Rule#\t#Training(" + TRAINSIZE + ")\t#Test(" + TESTSIZE + ")\t%Training\t%Test");
        ResultFileStat.flush();
        ResultFileGene.println("Rank\tGeneId\tFrequency");
        ResultFileGene.flush();
        Statistics.initStat(MEMBERS * MAXRUN, MAXRUN);
        int run;
        for(run = 1; run <= MAXRUN; run++)
        {
            long beforerun = System.currentTimeMillis();
            if(!FIXEDSPLIT)
                mvgpcmain.SplitDataRandom(trainsamples);
            ResultFileRule.println("*******************Run:" + run + "************************");
            ResultFileStat.println("*******************Run:" + run + "************************");
            System.out.println("*******************Run:" + run + "************************");
            mvgpcmain.MVGPC(run, MEMBERS, ResultFileRule, ResultFileStat);
            long afterrun = System.currentTimeMillis();
            double etime = (double)(afterrun - beforerun) / 1000D;
            ResultFileRule.println("Total Execution Time=" + etime + " sec");
            System.out.println("Total Execution Time=" + etime + " sec");
        }

        if(run > 1 && Parameters.RunningMode)
        {
            DecimalFormat dformat = new DecimalFormat("0.00");
            String minfo = "MVGPC: Training accuracy=";
            double trainstat[] = Statistics.getMVGPCTrainStat();
            double teststat[] = Statistics.getMVGPCTestStat();
            minfo = minfo + dformat.format(trainstat[0]) + "\261" + dformat.format(trainstat[1]) + "; Test accuracy=" + dformat.format(teststat[0]) + "\261" + dformat.format(teststat[1]);
            ResultFileStat.println(minfo);
            minfo = "SR/SSR: Training accuracy=";
            trainstat = Statistics.getPTRTrainStat();
            teststat = Statistics.getPTRTestStat();
            minfo = minfo + dformat.format(trainstat[0]) + "\261" + dformat.format(trainstat[1]) + "; Test accuracy=" + dformat.format(teststat[0]) + "\261" + dformat.format(teststat[1]);
            ResultFileStat.println(minfo);
        }
        ResultFileRule.close();
        ResultFileStat.close();
        mvgpcmain.WriteGeneFreq(ResultFileGene);
        ResultFileGene.close();
        System.out.println("finish");
        }
        catch(Exception ex)
        {
        
        System.out.println(ex);
        ex.printStackTrace();
        }
    }

    public void nextMetaGeneration()
        {
            Population newPopulation = new Population(population.getSize());
            newPopulation.setIndividual(0, getBestIndividual());
            for(int i = 1; i < population.getSize(); i++)
            {
                    
                    Individual i1 = GreedyoverSelect();
                    Individual i2 = GreedyoverSelect();
                    Individual Offspring[] = new Individual[2];
                    
                    if(i1.fitness >= i2.fitness)
                        Offspring = Parameters.geneticoperation.applyGenOperator(i1, i2,opTree);
                    else
                        Offspring = Parameters.geneticoperation.applyGenOperator(i2, i1,opTree);

                    Offspring[0].setFitness(RuleFitness(Offspring[0]));

                    Offspring[0].setFitness(RuleFitness(Offspring[0]));
                    Offspring[1].setFitness(RuleFitness(Offspring[1]));
                if(i < population.getSize() - 1)
                    newPopulation.setIndividual(i++, Offspring[0]);
                if (i<population.getSize())
                    newPopulation.setIndividual(i, Offspring[1]);
            }

            population = newPopulation;
            AssignFitness();
            population.sort(new FitnessComparator());
    }
    
    public void nextGeneration()
        throws CloneNotSupportedException
    {
    
        Population newPopulation = new Population(population.getSize());
        newPopulation.setIndividual(0, getBestIndividual());
        for(int i = 1; i < population.getSize(); i++)
        {
            float val = random.nextFloat();
            if(i < population.getSize() - 1 && val < Parameters.crossoverProb)
            {
                Individual i1 = GreedyoverSelect();
                Individual i2 = GreedyoverSelect();
                Individual Offspring[] = new Individual[2];
                if(i1.fitness >= i2.fitness)
                    Offspring = Parameters.geneticoperation.cross(i1, i2);
                else
                    Offspring = Parameters.geneticoperation.cross(i2, i1);
                Offspring[0].setFitness(RuleFitness(Offspring[0]));
                float mp = random.nextFloat();
                if(mp < Parameters.mutationProb && Offspring[0].getFitness() < 1.0F)
                {
                    Individual temp = Parameters.geneticoperation.mutate(Offspring[0]);
                    Offspring[0] = temp;
                    Offspring[0].setFitness(RuleFitness(Offspring[0]));
                }
                Offspring[1].setFitness(RuleFitness(Offspring[1]));
                mp = random.nextFloat();
                if(mp < Parameters.mutationProb && Offspring[1].getFitness() < 1.0F)
                {
                    Individual temp = Parameters.geneticoperation.mutate(Offspring[1]);
                    Offspring[1] = temp;
                    Offspring[1].setFitness(RuleFitness(Offspring[1]));
                }
                newPopulation.setIndividual(i++, Offspring[0]);
                newPopulation.setIndividual(i, Offspring[1]);
            } else
            if(val < Parameters.crossoverProb + Parameters.reproductionProb)
                newPopulation.setIndividual(i, GreedyoverSelect());
        }

        population = newPopulation;
        AssignFitness();
        population.sort(new FitnessComparator());
    }

    int ATTRIBUTE;
    int CurRowPTable;
    boolean GUIDisplay;
    JLabel JLabelGenInfo;
    JLabel JLabelRuleInfo;
    JTable JTablePredictions;
    JTextArea JTextAreaBRule;
    final int MaxGen;
    int MaxPBarValue;
    final float OptimumFitness = 1.0F;
    final int PopSize;
    Class RuleReturnType;
    int SAMPLE;
    int TESTSIZE;
    int TRAINSIZE;
    int TargetClass;
    double data[][];
    DecimalFormat decimalformat;
    int label[];
    int nCLASS;
    Function nodeSets[];
    int opSize;
    int opDepth;
    OpSet opSet[];
    OperatorTree opTree;
    int nsamples[];
    Population population;
    JProgressBar progressbar;
    Random random;
    Variable terminals[];
    int testset[];
    float totalFitness;
    int trainset[];
}
