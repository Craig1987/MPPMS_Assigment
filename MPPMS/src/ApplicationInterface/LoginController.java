/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ApplicationInterface;

import Models.LoginModel;
import Views.LoginUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author ryantk
 */
public class LoginController {

    LoginUI loginView;
    LoginModel loginModel;   
    
    public LoginController(LoginUI lv, LoginModel lm) {
        loginView = lv;
        loginModel = lm;
        
        loginView.addActionListener(new loginButtonActionListener());
        
        loginModel.registerObserver(loginView);
    }
    
    public void login() {
        loginModel.loginUser(loginView.getUsername(), loginView.getPassword());
    }
    
    class loginButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            login();
        } 
    } 
}
