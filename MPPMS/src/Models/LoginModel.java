/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import Observers.Observer;
import Observers.Subject;
import Registry.UserRegistry;
import Users.User;
import java.util.ArrayList;

/**
 *
 * @author ryantk
 */
public class LoginModel implements Subject {

    private Boolean result;
    private ArrayList<Observer> observers = new ArrayList<Observer>();
    
    
    public void loginUser(String username, String password){
        User user = UserRegistry.getInstance().authenticate(username, password);
        if (user != null)
            result = true;
        else 
            result = false;
        notifyObservers();
    }
    
    public Boolean getResult(){
        return result;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void unregisterObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers){
            System.out.println("notifying observers");
            observer.update(result);
        }
    }
    
}
