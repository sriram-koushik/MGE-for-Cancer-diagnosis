

package MVGPC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Referenced classes of package MVGPC:
//            MainFrame

class AListener
    implements ActionListener
{

    AListener(MainFrame adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.PerformAction(e);
    }

    MainFrame adaptee;
}
