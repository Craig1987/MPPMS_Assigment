package Controllers;

import Models.Project;
import Models.User;
import Views.ProjectIndexView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectIndexController {
    ProjectIndexView view = new ProjectIndexView();
    
    private final User currentUser;
    
    public ProjectIndexController(User currentUser) {
        this.currentUser = currentUser;
        
        this.view.setWelcomeMessage("Welcome, " + currentUser.getName() + "!");
        this.view.setProjectsTableData(Project.getProjectsForUser(currentUser));
    }
    
    public void launch() {
        this.view.setVisible(true);
        
        this.view.addNewProjectButtonActionListener(new NewProjectButtonActionListener());
        this.view.addNewTaskButtonActionListener(new NewTaskButtonActionListener());
        this.view.addLogOutMenuActionListener(new LogOutMenuActionListener());
        this.view.addExitMenuActionListener(new ExitMenuActionListener());
    }
    
    class NewProjectButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // New Project Happens
        }
    }
    
    class NewTaskButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // New Task Happens
        }
    }
    
    class LogOutMenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Log out happens
        }
    }
    
    class ExitMenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
