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

public class naivebayes 
{
    String filename;
    static int instances=0;
    static int attributes=0;
    static float data[][]=new float[instances][attributes];
    static String classvalue[]=new String[instances];
    static float attribute_list[]=new float[attributes];
    static double class_positive;
    static double class_negative;
    static double prob_pos,prob_neg;
    
    naivebayes(String f) throws FileNotFoundException, IOException
    {
        filename=f;
        countparameters(f);
    }
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
            attribute_list=new float[attributes];
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
                if(data[instance_index][attribute_index]<2&&data[instance_index][attribute_index]>1)
                {
                    data[instance_index][attribute_index]=1;
                }
                if(data[instance_index][attribute_index]<1)
                {
                    data[instance_index][attribute_index]=0;
                }
                if(data[instance_index][attribute_index]<3&&data[instance_index][attribute_index]>2)
                {
                    data[instance_index][attribute_index]=2;
                }
                if(data[instance_index][attribute_index]<4&&data[instance_index][attribute_index]>3)
                {
                    data[instance_index][attribute_index]=3;
                }
                if(data[instance_index][attribute_index]<5&&data[instance_index][attribute_index]>4)
                {
                    data[instance_index][attribute_index]=4;
                }
                c++;
            }
            System.out.println("Instance Count : " + c );
        }
        
        br1.close();
        //Function call to preprocess the data
        System.out.println("Number of Attributes : " + attributes);
         System.out.println("Number of Instances : " + instances);
        
        classify();
    }
    public static void classify()
    {
        int i,j;
        class_positive=class_negative=0;
        for(i=0;i<instances;i++)
        {
            if(classvalue[i].equalsIgnoreCase("positive"))
            {
                class_positive++;
            }
            if(classvalue[i].equalsIgnoreCase("negative"))
            {
                class_negative++;
            }
        }
        System.out.println("Positive : " + class_positive);
        System.out.println("Negative : " + class_negative);
        
        prob_pos=class_positive/instances;
        prob_neg=class_negative/instances;
        
        System.out.println(prob_pos);
        System.out.println(prob_neg);
        
        double prob_zero_yes=0;
        double prob_zero_no=0;
        double prob_one_yes=0;
        double prob_one_no=0;
        double prob_two_yes=0;
        double prob_two_no=0;
        double prob_three_yes=0;
        double prob_three_no=0;
        double prob_four_yes=0;
        double prob_four_no=0;
        double prob_attr_yes[][]=new double[attributes][5];
        double prob_attr_no[][]=new double[attributes][5];
        for(i=0;i<instances;i++)
            for(j=0;j<attributes;j++)
                System.out.println(data[i][j]);
        for(i=0;i<attributes;i++)
        {
            prob_zero_no=prob_zero_yes=0;
                prob_one_no=prob_one_yes=0;
                prob_two_no=prob_two_yes=0;
                prob_three_no=prob_three_yes=0;
                prob_four_no=prob_four_yes=0;
            for(j=0;j<instances;j++)
            {
                System.out.println("dgg");
                
                if(data[j][i]==0.0&&classvalue[j].equalsIgnoreCase("negative"))
                {
                    prob_zero_no++;
                }
                if(data[j][i]==0.0&&classvalue[j].equalsIgnoreCase("positive"))
                {
                    prob_zero_yes++;
                }
                if(data[j][i]==1.0&&classvalue[j].equalsIgnoreCase("negative"))
                {
                    prob_one_no++;
                }
                if(data[j][i]==1.0&&classvalue[j].equalsIgnoreCase("positive"))
                {
                    prob_one_yes++;
                }
                if(data[j][i]==2.0&&classvalue[j].equalsIgnoreCase("negative"))
                {
                    prob_two_no++;
                    System.out.println("Abi");
                }
                if(data[j][i]==2.0&&classvalue[j].equalsIgnoreCase("positive"))
                {
                    prob_two_yes++;
                    System.out.println("Abi");
                }
                if(data[j][i]==3.0&&classvalue[j].equalsIgnoreCase("negative"))
                {
                    prob_three_no++;
                }
                if(data[j][i]==3.0&&classvalue[j].equalsIgnoreCase("positive"))
                {
                    prob_three_yes++;
                }
                if(data[j][i]==4.0&&classvalue[j].equalsIgnoreCase("negative"))
                {
                    prob_four_no++;
                    System.out.println("Abi");
                }
                if(data[j][i]==4.0&&classvalue[j].equalsIgnoreCase("positive"))
                {
                    prob_four_yes++;
                    System.out.println("Abi");
                    
                }
            }
            prob_attr_yes[i][0]=prob_zero_yes/class_positive;
            
            prob_attr_no[i][0]=prob_zero_no/class_negative;
             prob_attr_yes[i][1]=prob_one_yes/class_positive;
            prob_attr_no[i][1]=prob_one_no/class_negative;
             prob_attr_yes[i][2]=prob_two_yes/class_positive;
            prob_attr_no[i][2]=prob_two_no/class_negative;
             prob_attr_yes[i][3]=prob_three_yes/class_positive;
            prob_attr_no[i][3]=prob_three_no/class_negative;
             prob_attr_yes[i][4]=prob_four_yes/class_positive;
            prob_attr_no[i][4]=prob_four_no/class_negative;
            System.out.println(prob_zero_no);
            System.out.println(prob_zero_yes);
            System.out.println(prob_one_no);
            System.out.println(prob_one_yes);
            System.out.println(prob_two_no);
            System.out.println(prob_two_yes);
            System.out.println(prob_three_no);
            System.out.println(prob_three_yes);
            System.out.println(prob_four_no);
            
            System.out.println(prob_four_yes);
            System.out.println(prob_attr_yes[i][0]);
            System.out.println(prob_attr_yes[i][1]);
            System.out.println(prob_attr_yes[i][2]);
            System.out.println(prob_attr_yes[i][3]);
            System.out.println(prob_attr_yes[i][4]);
            System.out.println(prob_attr_no[i][0]);
            System.out.println(prob_attr_no[i][1]);
            System.out.println(prob_attr_no[i][2]);
            System.out.println(prob_attr_no[i][3]);
            System.out.println(prob_attr_no[i][4]);
        }
        double final_pos=1,final_neg=1;
        int flag=0,flag1=0;
        for(i=0;i<instances;i++)
        {
            final_pos=final_neg=1;
            for(j=0;j<attributes;j++)
            {
                if(data[i][j]==0.0)
                {
                    if(prob_attr_yes[j][0]!=0)
                        final_pos=final_pos*prob_attr_yes[j][0];
                    if(prob_attr_no[j][0]!=0)
                        final_neg=final_neg*prob_attr_no[j][0];
                }
                if(data[i][j]==1.0)
                {
                    if(prob_attr_yes[j][1]!=0.0)
                    final_pos=final_pos*prob_attr_yes[j][1];
                    if(prob_attr_no[j][1]!=0.0)
                    final_neg=final_neg*prob_attr_no[j][1];
                }
                if(data[i][j]==2.0)
                {
                    if(prob_attr_yes[j][2]!=0.0)
                    final_pos=final_pos*prob_attr_yes[j][2];
                    if(prob_attr_no[j][2]!=0.0)
                    final_neg=final_neg*prob_attr_no[j][2];
                }
                if(data[i][j]==3.0)
                {
                    if(prob_attr_yes[j][3]!=0.0)
                    final_pos=final_pos*prob_attr_yes[j][3];
                    if(prob_attr_no[j][3]!=0.0)
                    final_neg=final_neg*prob_attr_no[j][3];
                }
                if(data[i][j]==4.0)
                {
                    if(prob_attr_yes[j][4]!=0.0)
                    final_pos=final_pos*prob_attr_yes[j][4];
                    if(prob_attr_no[j][4]!=0.0)
                    final_neg=final_neg*prob_attr_no[j][4];
                }
            }
            
            System.out.println("Final Results");
            System.out.println(final_pos*prob_pos);
            System.out.println(final_neg*prob_neg);
            if((final_pos*prob_pos)>(final_neg*prob_neg))
            {
                        System.out.println("Instance number " + i + " positive");
                        flag++;
            }
            
            if((final_pos*prob_pos)<(final_neg*prob_neg))
            {
                     System.out.println("Instance number " + i + " negative");
                     flag1++;
            }
       
        }
        System.out.println("Total Postive : " + flag);
        System.out.println("Total Negative : " + flag1);
    }
}
