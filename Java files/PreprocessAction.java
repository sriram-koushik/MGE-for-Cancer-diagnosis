
package MVGPC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Referenced classes of package MVGPC:
//            MainFrame

class PreprocessAction
    implements ActionListener
{

    PreprocessAction(MainFrame adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.doPreprocessAction(e);
    }

    MainFrame adaptee;
}
