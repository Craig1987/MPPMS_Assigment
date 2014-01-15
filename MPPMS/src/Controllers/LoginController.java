package Controllers;

import Models.User;
import Views.LoginView;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private final LoginView view = new LoginView();
    
    public LoginController() {
        this.view.setVisible(true);
        this.view.addActionListener(new LoginButtonActionListener());
    }
    
    class LoginButtonActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (User.authenticate(view.getUsername(), view.getPassword())) {
                ProjectIndexController controller = new ProjectIndexController(User.getUserByUsername(view.getUsername()));
                view.setState(Frame.ICONIFIED);
            }
            else {
                view.showErrorMessage("Incorrect username or password.");
            }
        }
        
    }
}
