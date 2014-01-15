package ApplicationInterface;

import Models.LoginModel;
import Views.LoginUI;
import Views.MainUI;
import Users.User;

public class ApplicationController
{
    private static ApplicationController instance = null;    
    public static ApplicationController getInstance()
    {
        if (instance == null)
        {
            instance = new ApplicationController();
        }
        return instance;
    }
    
    public void showLoginView()
    {  
        LoginUI loginView = new LoginUI();
        LoginModel loginModel = new LoginModel();
        loginView.setVisible(true);
        LoginController loginController = new LoginController(loginView, loginModel);    
    }
    
    public void showMainView(User user)
    {
        MainUI mainView = new MainUI(user);
        mainView.setVisible(true);
    }
}
