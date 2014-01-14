package ApplicationInterface;

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
        loginView.setVisible(true);
    }
    
    public void showMainView(User user)
    {
        MainUI mainView = new MainUI(user);
        mainView.setVisible(true);
    }
}
