package Controllers;

import Application.AppTracker;
import Models.User;
import Views.LoginView;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controller for LoginView
 * 
 * @see LoginView
 */
public class LoginController {
    private final LoginView view = new LoginView();
    
    /**
     * LoginView constructor
     */
    public LoginController() {
        this.view.addLoginButtonActionListener(new LoginButtonActionListener());
        this.view.addUsernameFieldKeyListener(new TextFieldKeyListener());
        this.view.addPasswordFieldKeyListener(new TextFieldKeyListener());
    }
    
    /**
     * Initialises the view, adds event listeners and makes the view visible.
     */
    public void launch() {
        this.view.setVisible(true);
    }
    
    /**
     * Validates the username and password entered by the user.
     */
    private void login() {
        if (User.authenticate(view.getUsername(), view.getPassword())) {
            if (AppTracker.getInstance().isUserLoggedIn(view.getUsername())) {
                /**
                 * Correct credentials but this user is already logged in and can't 
                 * login again yet.
                 */
                view.showErrorMessage("User '" + view.getUsername() + "' is already logged in to the MPPMS system.");
            }
            else {
                // Correct credentials and not already logged in, so log in and launch the main screen (Index)
                AppTracker.getInstance().userLoggedIn(view.getUsername());
                
                IndexController controller = new IndexController(User.getUserByUsername(view.getUsername()));
                controller.launch();

                view.setUsername("");
                view.setPassword("");
                view.setState(Frame.ICONIFIED);
                view.focusUsername();
            }
        }
        else {
            // Invalid credentials
            view.showErrorMessage("Incorrect username or password.");
        }
    }
    
    /**
     * Event listener for the 'Login' button. Calls the login method and attempts to
     * log the user into the system.
     * 
     * @see #login() 
     */
    class LoginButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            login();
        }        
    }
    
    /**
     * Event listener for the username and password fields. Calls the login method
     * when the enter key is pressed.
     * 
     * @see #login()
     */
    class TextFieldKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent ke) { }

        @Override
        public void keyPressed(KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                login();
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) { }        
    }
}
