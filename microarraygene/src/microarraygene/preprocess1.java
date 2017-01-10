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

public class preprocess1 
{
    //global variables
    String cancer_type;
    String filepath;
    static int attributes=0;
    static int instances=0;
    static float data[][]=new float[instances][attributes];
    static String classvalue[]=new String[instances];
    //Theshold values tend to change depending on the type of cancer.
    static float low_threshold=10F;
    static float high_threshold=16000F;
    static float diff=500F;
    static float fold=5F;
    static float max_value,min_value;
    static int attribute_list[]=new int[instances];  
    
    //Constructor to initialize the user-choosen parameters
    preprocess1(String t,String f) throws FileNotFoundException, IOException
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
                       // System.out.println(count_index+" : ");
                    System.out.println( str.nextElement());
                    
                }
                
                //To count the instances , the data set has
                count_index++;
            }
            
            //Initializing the number of instances and attributes
            instances=count_index;
            attributes=count[0]-1;
            
            //Initializing the Data set array Size.
            data=new float[instances][attributes];
            
            //Initializing the Attribute List Size
            attribute_list=new int[attributes];
            classvalue=new String[instances];
            
            br.close();
            //Function call to read the data
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
                System.out.println("Attributes " + attributes);
                StringTokenizer str=new StringTokenizer(content,",");
                while(str.hasMoreTokens())
                {
                    System.out.println(attribute_index);
                    
                    //Parsing the data and storing it in the data ARRAY
                    if(attribute_index==(attributes))
                    {
                        
                        classvalue[instance_index]=(str.nextToken());
                        break;
                        
                    }
                   
                    data[instance_index][attribute_index]=Float.parseFloat( str.nextToken());
                    System.out.println(data[instance_index][attribute_index]);
                    attribute_index++;
                    
                    
                 }
                 //System.out.println("Value : " +data[instance_index][2]);
                   
                //Incrementing the instance index position
                instance_index++;
                
                //Re-Initializing the attribute index as 0 for the next instance.
                attribute_index=0;
            }
        
        //Loop To display the data from the data Array
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
        //Function call to preprocess the data
        preprocessData(filename,low_threshold,high_threshold);
        
    }
    
    //Function to preprocess the data
    public static void preprocessData(String filename,float low_threshold,float high_threshold) throws IOException
    {
        //Fixing the threshold values
        min_value = low_threshold;
        max_value = high_threshold;
        
        //Finding the maximum and minimum value for each attribute
        for(int i = 0; i < attributes; i++)
        {
            float maxg = low_threshold;
            float ming = high_threshold;
            
            for(int j = 0; j < instances; j++)
            {
                //Computing the values and Parsing it
                maxg = (float)Math.max(data[j][i], maxg);
                ming = (float)Math.min(data[j][i], ming);
            }
            /*
             Variation Filter Conditions
            (Maximum value - Minimum Value) must be greater than 50
            (Maximum value / Minimum value) must be greater than 5
             */
            if((maxg - ming > diff) && (maxg / ming > fold))
            {
                //Updating the attribute list
                attribute_list[i] = 1;
                //Re-assigning the maximum and the mininum value with the current attributes'
                max_value = Math.max(max_value, maxg);
                min_value = Math.min(min_value, ming);
            } 
            else
            {
                attribute_list[i] = 0;
            }
            System.out.println(attribute_list[i]);
        }
        //Flags to count the Number of attributes rejected and eliminated
        int flag=0,flag1=0;
        for(int i=0;i<attributes;i++)
        {
            if(attribute_list[i]==1)
                flag++;
            else
                flag1++;
        }
        
        //Displaying the Results
        System.out.println("Parameter Analysis : ");
        System.out.println("instances : "+instances+" attributes : "+(attributes)+"\n");
        System.out.println("Preprocessing Details ");
        System.out.println("Total Number of Test Attributes Taken :" + (attributes));
        System.out.println("Attributes retained after preprocessing : " + flag);
        System.out.println("Attributes rejected after preprocessing : " + flag1);
        
        //Function call to normalize data
        normalizeData();
    }
    
    //Function to normalize data
    public static void normalizeData() throws IOException 
    {
        //Assigning the logbase as 2
        double lnbase =2.0D;
        
        for(int i = 0; i < attributes; i++)
            {
                if(attribute_list[i]==1)
                {
                    for(int j = 0; j < instances; j++)
                    {
                        //Normalizing the data to log base 2
                        data[j][i] = (float)(Math.log(data[j][i]) / lnbase);
                    }
                }

            }
          //Function call to write data into file
         writeData();
    }
    
    //Function to write the preprocessed data into file
    public static void writeData() throws IOException
    {
        //Output file name
        String filepath="F:/7th sem/project/Datasets/op.data";
        PrintWriter outfile = new PrintWriter(new FileWriter(filepath));
        for(int i = 0; i < instances; i++)
        {
                for(int j = 0; j < attributes; j++)
                {
                    //Includes only if the attribute is retained by the preprocessing steps
                    if(attribute_list[j]==1)
                    {
                    outfile.print( data[i][j] + ",");
                    
                    
                    }
                }
                outfile.print(classvalue[i]);
                outfile.println();
                
        }
        
        System.out.println("Preprocessing Data Written in the output file ! ");
        outfile.flush();
        outfile.close();
           naivebayes n=new naivebayes("F:/7th sem/project/Datasets/op.data");
    }
 
    
}
