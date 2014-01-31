package Application;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Defines the entry point of the application.
 */
public class AppMain
{
    /**
     * Application entry point.
     *
     * @param args Command line parameters.
     */
    public static void main(String[] args) {
        try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            //Default look and feel is 'Metal'
        }
        
        // Perform the initial action of the application (begin the flow)
        AppController controller = new AppController();
        controller.showLogin();
    }
}