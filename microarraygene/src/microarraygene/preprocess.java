/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package microarraygene;

/**
 *
 * @author kusriram
 */
import java.io.*;
import java.util.*;

public class preprocess 
{
    //global variables
    String cancer_type;
    String filepath;
    static int attributes=0;
    static int instances=0;
    static float data[][]=new float[instances][attributes];
    //Theshold values tend to change depending on the type of cancer.
    static float low_threshold=10F;
    static float high_threshold=600F;
    static float diff=6000F;
    static float fold=5F;
    static float max_value,min_value;
    static int instance_list[]=new int[instances];  
    
    //Constructor to initialize the user-choosen parameters
    preprocess(String t,String f) throws FileNotFoundException, IOException
    {
        cancer_type=t;
        filepath=f;
        //Calling the function to count the number of instances and attributes
        countparameters(filepath);
    }
     
    //Function to count the number of instances and attributes from the Data Set
    public static void countparameters(String filename) throws FileNotFoundException, IOException
    {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String content;
            int count[]=new int[500];
            int count_index=0;
            while((content=br.readLine())!=null)
            {
                StringTokenizer str=new StringTokenizer(content,",");
                //To count the number of attributes each instance has.
                while(str.hasMoreElements())
                {
                    count[count_index]++;
                    System.out.println(str.nextElement());
                    
                }
                //To count the instances , the data set has
                count_index++;
            }
            
            //Initializing the number of instances and attributes
            instances=count_index;
            attributes=count[0];
            
            //Initializing the Data set array Size.
            data=new float[instances][attributes];
            
            //Initializing the Instance List Size
            instance_list=new int[instances];
            
            br.close();
            readData(filename);
                  
    }
    
    //Function to read data from the dataset into a data Array
    public static void readData(String filename) throws FileNotFoundException, IOException
    {
        
        BufferedReader br1 = new BufferedReader(new FileReader(filename));
        String content;
        int instance_index=0,attribute_index=0;
        while((content=br1.readLine())!=null)
            {
                StringTokenizer str=new StringTokenizer(content,",");
                while(str.hasMoreTokens())
                {
                    //Parsing the data and storing it in the data ARRAY
                    data[instance_index][attribute_index]=Float.parseFloat( str.nextToken());
                    attribute_index++;
                    
                }
                //Incrementing the instance index position
                instance_index++;
                
                //Re-Initializing the attribute index as 0 for the next instance.
                attribute_index=0;
            }
        
        //To display the data from the data Array
        for(instance_index=0;instance_index<instances;instance_index++)
        {
            int c=0;
            System.out.println("Instance index : " + instance_index);
            for(attribute_index=0;attribute_index<attributes;attribute_index++)
            {
                System.out.println(data[instance_index][attribute_index]);
                c++;
            }
            System.out.println("Instance Count : " + c );
        }
        
        br1.close();
        preprocessData(filename,low_threshold,high_threshold);
        
    }
    public static void preprocessData(String filename,float low_threshold,float high_threshold) throws IOException
    {
        max_value = low_threshold;
        max_value = high_threshold;
        
        for(int i = 0; i < instances; i++)
        {
            float maxg = low_threshold;
            float ming = high_threshold;
            
            for(int j = 0; j < attributes; j++)
            {
                maxg = (float)Math.max(data[i][j], maxg);
                ming = (float)Math.min(data[i][j], ming);
            }
            /*
             Variation Filter Conditions
            (Maximum value - Minimum Value) must be greater than 50
            (Maximum value / Minimum value) must be greater than 5
             */
            if((maxg - ming > diff) && (maxg / ming > fold))
            {
                
                instance_list[i] = 1;
                max_value = Math.max(max_value, maxg);
                min_value = Math.min(min_value, ming);
            } 
            else
            {
                instance_list[i] = 0;
            }
            System.out.println(instance_list[i]);
        }
        int flag=0,flag1=0;
        for(int i=0;i<instances;i++)
        {
            if(instance_list[i]==1)
                flag++;
            else
                flag1++;
        }
        System.out.println("Parameter Analysis : ");
        System.out.println("instances : "+instances+" attributes : "+attributes+"\n");
        System.out.println("Preprocessing Details ");
        System.out.println("Total Number of Test instances Taken :" + instances);
        System.out.println("instances retained after preprocessing : " + flag);
        System.out.println("instances rejected after preprocessing : " + flag1);
        
        normalizeData();
    }
    public static void normalizeData() throws IOException 
    {
        double lnbase =2.0D;
        //System.out.println("Minimum value : " + min_value);
        
            for(int i = 0; i < instances; i++)
            {
                if(instance_list[i]==1)
                {
                for(int j = 0; j < attributes; j++)
                {
                    data[i][j] = (float)(Math.log(data[i][j]) / lnbase);
                    //System.out.print("\n"+data[i][j]);
                }
                }

            }

         writeData();
    }
    public static void writeData() throws IOException
    {
        String filepath="F:/7th sem/project/Datasets/op.data";
        PrintWriter outfile = new PrintWriter(new FileWriter(filepath));
        for(int i = 0; i < instances; i++)
            if(instance_list[i]==1)
            {
                outfile.print(data[i][0]);
                for(int j = 1; j < attributes; j++)
                    outfile.print("\t" + data[i][j]);

                outfile.println();
            }
        System.out.println("Preprocessing Data Written in the output file ! ");
        outfile.flush();
        outfile.close();
        
        
    }
    
    
}
