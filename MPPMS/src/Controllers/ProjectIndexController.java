package Controllers;

import Models.Project;
import Models.User;
import Views.ProjectIndexView;

public class ProjectIndexController {
    ProjectIndexView view = new ProjectIndexView();
    
    private final User currentUser;
    
    public ProjectIndexController(User currentUser) {
        this.currentUser = currentUser;
        
        this.view.setWelcomeMessage("Welcome, " + currentUser.getName() + "!");
        this.view.setProjectsTableData(Project.getProjectsForUser(currentUser));
        this.view.setVisible(true);
    }
}
