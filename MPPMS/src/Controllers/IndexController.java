package Controllers;

import Application.AppObservable;
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
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class IndexController implements Observer {
    private final User currentUser;
    
    private IndexView view = new IndexView();
    private ProjectDetailController projectDetailController;
    private TaskDetailController taskDetailController;
    private ComponentDetailController componentDetailController;
    private AssetDetailController assetDetailController;
    
    public IndexController(User currentUser) {
        this.currentUser = currentUser;
    }
    
    public void launch() {
        populateTables();
        
        this.view.setWelcomeMessage("Welcome, " + currentUser.getName() + "!");        
        
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
        
        AppObservable.getInstance().addObserver(this);
    }
    
    private void populateTables() {
        this.view.setProjectsTableData(Project.getProjectsForUser(currentUser));
        this.view.setTasksTableData(Task.getTasksForUser(currentUser));
        this.view.setComponentsTableData(Component.getAllComponents());
        this.view.setAssetsTableData(Asset.getAllAssets());
    }
    
    private void projectValueChanged() {
        if (view.getSelectedProject() == null) {
            view.setDetailViewPanel(null);
        }
        else {
            ProjectDetailView projectDetailView = new ProjectDetailView();
            view.setDetailViewPanel(projectDetailView);
            projectDetailView.addTaskEditButtonActionListener(new ProjectDetailTaskEditButtonActionListener());
            projectDetailView.addComponentEditButtonActionListener(new ProjectDetailComponentEditButtonActionListener());
            
            projectDetailController = new ProjectDetailController(projectDetailView, view.getSelectedProject());
            projectDetailController.initialise();
        }
    }
    
    private void taskValueChanged() {
        if (view.getSelectedTask() == null) {
            view.setDetailViewPanel(null);
        }
        else {
            TaskDetailView taskDetailView = new TaskDetailView();
            view.setDetailViewPanel(taskDetailView);
            taskDetailView.addAssetEditButtonActionListener(new TaskDetailAssetEditButtonActionListener());
            
            taskDetailController = new TaskDetailController(taskDetailView, view.getSelectedTask());
            taskDetailController.initialise();
        }
    }
    
    private void componentValueChanged() {
        if (view.getSelectedComponent() == null) {
            view.setDetailViewPanel(null);
        }
        else {
            ComponentDetailView componentDetailView = new ComponentDetailView();
            view.setDetailViewPanel(componentDetailView);
            componentDetailView.addAssetEditButtonActionListener(new ComponentDetailAssetEditButtonActionListener());
            
            componentDetailController = new ComponentDetailController(componentDetailView, view.getSelectedComponent());
            componentDetailController.initialise();
        }
    }
    
    private void assetValueChanged() {
        if (view.getSelectedAsset() == null) {
            view.setDetailViewPanel(null);
        }
        else {
            AssetDetailView assetDetailView = new AssetDetailView();
            view.setDetailViewPanel(assetDetailView);
            assetDetailController = new AssetDetailController(assetDetailView, view.getSelectedAsset());
            assetDetailController.initialise();
        }
    }
    
    private void valueChanged() {
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

    @Override
    public void update(Observable o, Object o1) {
        // Store any currently selected Models in the tables
        Project selectedProject = view.getSelectedProject();
        Task selectedTask = view.getSelectedTask();
        Component selectedComponent = view.getSelectedComponent();
        Asset selectedAsset = view.getSelectedAsset();
        
        // Repopulate the tables
        populateTables();
        
        // Select any models which were previously selected
        view.setSelectedProject(selectedProject);
        view.setSelectedTask(selectedTask);
        view.setSelectedComponent(selectedComponent);
        view.setSelectedAsset(selectedAsset);
        
        // Ensure that we remain looking at the same tab / detail view
        valueChanged();
    }
    
    class NewProjectButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {        
            ProjectDetailView detailView = new ProjectDetailView();
            detailView.addDiscardButtonActionListener(new DiscardNewProjectActionListener());
            
            projectDetailController = new ProjectDetailController(detailView, new Project());
            projectDetailController.initialise();
            
            view.clearProjectSelection();
            view.setDetailViewPanel(detailView);
        }
    }
    
    class NewTaskButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TaskDetailView detailView = new TaskDetailView();
            detailView.addDiscardButtonActionListener(new DiscardNewProjectActionListener());
            
            taskDetailController = new TaskDetailController(detailView, new Task());
            taskDetailController.initialise();
            
            view.clearTaskSelection();
            view.setDetailViewPanel(detailView);
        }
    }
    
    class NewComponentButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ComponentDetailView detailView = new ComponentDetailView();
            detailView.addDiscardButtonActionListener(new DiscardNewProjectActionListener());
            
            componentDetailController = new ComponentDetailController(detailView, new Component());
            componentDetailController.initialise();
            
            view.clearComponentSelection();
            view.setDetailViewPanel(detailView);            
        }
    }
    
    class NewAssetButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AssetDetailView detailView = new AssetDetailView();
            detailView.addDiscardButtonActionListener(new DiscardNewProjectActionListener());
            
            assetDetailController = new AssetDetailController(detailView, new Asset());
            assetDetailController.initialise();
            
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
            valueChanged();
        }        
    }
    
    class ProjectDetailTaskEditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setSelectedTask(projectDetailController.getSelectedTask());
            view.setSelectedTab("Tasks");
        }
    }
    
    class ProjectDetailComponentEditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setSelectedComponent(projectDetailController.getSelectedComponent());
            view.setSelectedTab("Components");
        }
    }
    
    class TaskDetailAssetEditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setSelectedAsset(taskDetailController.getSelectedAsset());
            view.setSelectedTab("Assets");
        }
    }
    
    class ComponentDetailAssetEditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setSelectedAsset(componentDetailController.getSelectedAsset());
            view.setSelectedTab("Assets");
        }
    }
}
