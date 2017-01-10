/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package microarraygene;

import java.io.File;
import java.io.FileInputStream;

/**
 *
 * @author kusriram
 */
public class Microarraygene {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        File f=new File("F:/7th sem/project/Datasets/lungCancer_train.txt");
        FileInputStream fin=null;
        try
        {
            fin=new FileInputStream(f);
            
			int content;
			while ((content = fin.read()) != -1) {
				// convert to char and display it
				System.out.print((char) content);}
        }
        catch(Exception e)
        {
            
        }
            
        
    }
}
