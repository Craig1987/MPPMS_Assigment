package Application;

import Controllers.LoginController;

/**
 * Controls the initial action which is performed when the application is loaded.
 */
public class AppController {
    
    /**
     * Launches the login screen.
     */
    public void showLogin() {
        LoginController controller = new LoginController();
        controller.launch();
    }
}
