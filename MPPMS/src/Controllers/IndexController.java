package Controllers;

import Models.Asset;
import Models.Component;
import Models.Project;
import Models.Task;
import Models.User;
import Views.AssetDetailView;
import Views.ComponentDetailView;
import Views.ProjectDetailView;
import Views.IndexView;
import Views.TaskDetailView;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
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
        this.view.setComponentsTableData(Component.getAllComponents());
        this.view.setAssetsTableData(Asset.getAllAssets());
        
        this.view.addNewProjectButtonActionListener(new NewProjectButtonActionListener());
        this.view.addNewTaskButtonActionListener(new NewTaskButtonActionListener());
        this.view.addNewComponentButtonActionListener(new NewComponentButtonActionListener());
        this.view.addNewAssetButtonActionListener(new NewAssetButtonActionListener());
        
        this.view.addUserMenuLogOutActionListener(new UserMenuLogOutActionListener());
        this.view.addApplicationMenuExitActionListener(new ApplicationMenuExitActionListener());
        
        this.view.addProjectsTableListSelectionListener(new ProjectsTableListSelectionListener());
        this.view.addTasksTableListSelectionListener(new TasksTableListSelectionListener());
        this.view.addComponentsTableListSelectionListener(new ComponentsTableListSelectionListener());
        this.view.addAssetsTableListSelectionListener(new AssetsTableListSelectionListener());
        
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
    
    private void componentValueChanged() {
        if (view.getSelectedComponent() == null) {
            view.setDetailViewPanel(null);
        }
        else {
            ComponentDetailView componentDetailView = new ComponentDetailView();
            view.setDetailViewPanel(componentDetailView);
            ComponentDetailController controller = new ComponentDetailController(componentDetailView, view.getSelectedComponent());
            controller.initialise();
        }
    }
    
    private void assetValueChanged() {
        if (view.getSelectedAsset() == null) {
            view.setDetailViewPanel(null);
        }
        else {
            AssetDetailView assetDetailView = new AssetDetailView();
            view.setDetailViewPanel(assetDetailView);
            AssetDetailController controller = new AssetDetailController(assetDetailView, view.getSelectedAsset());
            controller.initialise();
        }
    }
    
    class NewProjectButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {        
            ProjectDetailView detailView = new ProjectDetailView();
            detailView.addDiscardButtonActionListener(new DiscardNewProjectActionListener());
            
            ProjectDetailController controller = new ProjectDetailController(detailView, new Project());
            controller.initialise();
            
            view.clearProjectSelection();
            view.setDetailViewPanel(detailView);
        }
    }
    
    class NewTaskButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TaskDetailView detailView = new TaskDetailView();
            detailView.addDiscardButtonActionListener(new DiscardNewProjectActionListener());
            
            TaskDetailController controller = new TaskDetailController(detailView, new Task());
            controller.initialise();
            
            view.clearTaskSelection();
            view.setDetailViewPanel(detailView);
        }
    }
    
    class NewComponentButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ComponentDetailView detailView = new ComponentDetailView();
            detailView.addDiscardButtonActionListener(new DiscardNewProjectActionListener());
            
            ComponentDetailController controller = new ComponentDetailController(detailView, new Component());
            controller.initialise();
            
            view.clearComponentSelection();
            view.setDetailViewPanel(detailView);            
        }
    }
    
    class NewAssetButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AssetDetailView detailView = new AssetDetailView();
            detailView.addDiscardButtonActionListener(new DiscardNewProjectActionListener());
            
            AssetDetailController controller = new AssetDetailController(detailView, new Asset());
            controller.initialise();
            
            view.clearAssetSelection();
            view.setDetailViewPanel(detailView);  
        }
    }
    
    class DiscardNewProjectActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setDetailViewPanel(new JPanel());
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
    
    class ComponentsTableListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            componentValueChanged();
        }
    }
    
    class AssetsTableListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            assetValueChanged();
        }
    }
    
    class TabChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent ce) {
            switch (view.getSelectedTabName()) {
                case "Projects":
                    projectValueChanged();
                    break;
                case "Tasks":
                    taskValueChanged();
                    break;
                case "Components":
                    componentValueChanged();
                    break;
                case "Assets":
                    assetValueChanged();
                    break;
            }
        }        
    }
}
