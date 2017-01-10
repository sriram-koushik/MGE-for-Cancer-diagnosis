
package MVGPC;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;


// Referenced classes of package MVGPC:
//            MainFrame

public class GUIVersion
{

    public GUIVersion()
    {
        packFrame = false;
        MainFrame frame = new MainFrame();
        if(packFrame)
            frame.pack();
        else
            frame.validate();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        frame.setSize(623, 600);
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }

    public static void main(String args[])
    {
        try
        {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(Exception e)
        {
        e.printStackTrace();}
        new GUIVersion();
    }

    boolean packFrame;
}
