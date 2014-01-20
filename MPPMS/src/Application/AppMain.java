package Application;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AppMain
{
    public static void main(String[] args) {
        try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) 
        {
            //Default look and feel is 'Metal'
        }
        
        AppController controller = new AppController();
        controller.showLogin();
    }
}