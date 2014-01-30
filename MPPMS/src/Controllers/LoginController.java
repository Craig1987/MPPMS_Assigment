package Controllers;

import Application.AppTracker;
import Models.User;
import Views.LoginView;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginController {
    private final LoginView view = new LoginView();
    
    public LoginController() {
        this.view.addLoginButtonActionListener(new LoginButtonActionListener());
        this.view.addUsernameFieldKeyListener(new TextFieldKeyListener());
        this.view.addPasswordFieldKeyListener(new TextFieldKeyListener());
    }
    
    public void launch() {
        this.view.setVisible(true);
    }
    
    private void login() {
        if (User.authenticate(view.getUsername(), view.getPassword())) {
            if (AppTracker.getInstance().isUserLoggedIn(view.getUsername())) {
                view.showErrorMessage("User '" + view.getUsername() + "' is already logged in to the MPPMS system.");
            }
            else {
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
            view.showErrorMessage("Incorrect username or password.");
        }
    }
    
    class LoginButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            login();
        }        
    }
    
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
