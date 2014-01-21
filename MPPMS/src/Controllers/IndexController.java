package Controllers;

import Models.Project;
import Models.Task;
import Models.User;
import Views.ProjectDetailView;
import Views.IndexView;
import Views.TaskDetailView;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class IndexController {
    IndexView view = new IndexView();
    private final User currentUser;
    
    public IndexController(User currentUser) {
        this.currentUser = currentUser;
    }
    
    public void launch() {
        this.view.setWelcomeMessage("Welcome, " + currentUser.getName() + "!");
        this.view.setProjectsTableData(Project.getProjectsForUser(currentUser));
        this.view.setTasksTableData(Task.getTasksForUser(currentUser));
        
        this.view.addNewProjectButtonActionListener(new NewProjectButtonActionListener());
        this.view.addNewTaskButtonActionListener(new NewTaskButtonActionListener());
        this.view.addUserMenuLogOutActionListener(new UserMenuLogOutActionListener());
        this.view.addApplicationMenuExitActionListener(new ApplicationMenuExitActionListener());
        this.view.addProjectsTableListSelectionListener(new ProjectsTableListSelectionListener());
        this.view.addTasksTableListSelectionListener(new TasksTableListSelectionListener());
        this.view.addTabChangeListener(new TabChangeListener());
        
        this.view.setVisible(true);
    }
    
    private void projectValueChanged() {
        if (view.getSelectedProject() == null) {
            view.setDetailViewPanel(null);
        }
        else {
            ProjectDetailView projectDetailView = new ProjectDetailView();
            view.setDetailViewPanel(projectDetailView);
            ProjectDetailController controller = new ProjectDetailController(projectDetailView, view.getSelectedProject());
            controller.initialise();
        }
    }
    
    private void taskValueChanged() {
        if (view.getSelectedTask() == null) {
            view.setDetailViewPanel(null);
        }
        else {
            TaskDetailView taskDetailView = new TaskDetailView();
            view.setDetailViewPanel(taskDetailView);
            TaskDetailController controller = new TaskDetailController(taskDetailView, view.getSelectedTask());
            controller.initialise();
        }
    }
    
    class NewProjectButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        
            ProjectDetailView detailView = new ProjectDetailView();
            
            ProjectDetailController controller = new ProjectDetailController(detailView, new Project());
            controller.initialise();
            
            view.setDetailViewPanel(detailView);
            
        }
    }
    
    class NewTaskButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // New Task Happens
        }
    }
    
    class ApplicationMenuExitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    class UserMenuLogOutActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getSource());
            view.dispose();
            for (Frame frame : Frame.getFrames()) {
                if (frame.getTitle().equals("MPPMS - Login")) {
                    frame.setState(Frame.NORMAL);
                }
            }
        }
    }
    
    class ProjectsTableListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            projectValueChanged();
        }
    }
    
    class TasksTableListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            taskValueChanged();
        }
    }
    
    class TabChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent ce) {
            switch (view.getSelectedTabIndex()) {
                case 0:
                    projectValueChanged();
                    break;
                case 1:
                    taskValueChanged();
                    break;
            }
        }        
    }
}
