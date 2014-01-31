package Controllers;

import Application.AppObservable;
import Application.AppTracker;
import Models.Asset;
import Models.Component;
import Models.Project;
import Models.Task;
import Models.User;
import Models.User.Role;
import Views.AssetDetailView;
import Views.ComponentDetailView;
import Views.ImportAssetsView;
import Views.ProjectDetailView;
import Views.IndexView;
import Views.ProjectOverviewView;
import Views.ProjectsHierarchyView;
import Views.TaskDetailView;
import java.awt.Cursor;
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

/**
 * Controller for IndexView. Observer to AppObservable.
 * 
 * @see IndexView
 * @see AppObservable
 */
public class IndexController implements Observer {
    private User currentUser;
    
    private final IndexView view = new IndexView();
    private ProjectDetailController projectDetailController;
    private TaskDetailController taskDetailController;
    private ComponentDetailController componentDetailController;
    private AssetDetailController assetDetailController;
    private ProjectOverviewController projectOverviewController;
    private ImportAssetsController importAssetsController;
    private ProjectsHierarchyController contentHierarchyController;
    
    /**
     * IndexController constructor
     * 
     * @param currentUser The logged in User
     */
    public IndexController(User currentUser) {
        this.currentUser = currentUser;
    }
    
    /**
     * Initialises the view, adds event listeners and makes the view visible.
     */
    public void launch() {
        populateTables();
        
        this.view.setWelcomeMessage("Welcome, " + currentUser.getName() + "!");
        
        // Controls are enabled if the current logged in user is a Project Manager
        this.view.setCreateProjectButtonEnabled(this.currentUser.getRole() == Role.ProjectManager);
        this.view.setCreateTaskButtonEnabled(this.currentUser.getRole() == Role.ProjectManager ||
                                                this.currentUser.getRole() == Role.ProjectCoordinator ||
                                                this.currentUser.getRole() == Role.QCTeamLeader);
        this.view.setContentHierarchyButtonEnabled(this.currentUser.getRole() == Role.ProjectManager);
        
        // IndexView controls events
        this.view.addNewProjectButtonActionListener(new NewProjectButtonActionListener());
        this.view.addNewTaskButtonActionListener(new NewTaskButtonActionListener());
        this.view.addNewComponentButtonActionListener(new NewComponentButtonActionListener());
        this.view.addNewAssetButtonActionListener(new NewAssetButtonActionListener());   
        this.view.addImportAssetsButtonActionListener(new ImportAssetsButtonActionListener());
        this.view.addUserMenuLogOutActionListener(new UserMenuLogOutActionListener());
        this.view.addApplicationMenuExitActionListener(new ApplicationMenuExitActionListener());        
        this.view.addProjectsTableListSelectionListener(new ProjectsTableListSelectionListener());
        this.view.addTasksTableListSelectionListener(new TasksTableListSelectionListener());
        this.view.addComponentsTableListSelectionListener(new ComponentsTableListSelectionListener());
        this.view.addAssetsTableListSelectionListener(new AssetsTableListSelectionListener());        
        this.view.addTabChangeListener(new TabChangeListener());
        this.view.addContentHierarchyButtonActionListener(new ContentHierarchyButtonActionListener());
        
        this.view.setVisible(true);
        
        /**
         * Craig - TC B2c: Real time updates
         * Register this controller as an observer
         */
        AppObservable.getInstance().addObserver(this);
    }
    
    /**
     * Retrieves all relevant data for the logged in User and populates the 4
     * tables (1 per tab).
     */
    private void populateTables() {
        this.view.setProjectsTableData(Project.getProjectsForUser(currentUser));
        this.view.setTasksTableData(Task.getTasksForUser(currentUser));
        this.view.setComponentsTableData(Component.getAllComponents());
        this.view.setAssetsTableData(Asset.getAllAssets());
    }
    
    /**
     * Called when the selected value of the Projects table changes. Creates a new
     * ProjectDetailView and ProjectDetailController - the view is shown within 
     * this controller's view.
     */
    private void projectValueChanged() {
        if (view.getSelectedProject() == null) {
            view.setDetailViewPanel(null);
        }
        else {
            ProjectDetailView projectDetailView = new ProjectDetailView();
            view.setDetailViewPanel(projectDetailView);
            projectDetailView.addTaskViewButtonActionListener(new ProjectDetailTaskViewButtonActionListener());
            projectDetailView.addComponentViewButtonActionListener(new ProjectDetailComponentViewButtonActionListener());
            projectDetailView.addOverviewButtonActionListener(new ProjectDetailOverviewButtonActionListener());
            
            boolean canEdit = currentUser.getRole() == Role.ProjectManager;
            boolean canEditTasks = canEdit || currentUser.getRole() == Role.ProjectCoordinator || currentUser.getRole() == Role.QCTeamLeader;
            projectDetailController = new ProjectDetailController(projectDetailView, view.getSelectedProject(), canEdit, canEditTasks);
            projectDetailController.initialise();
        }
    }
    
    /**
     * Called when the selected value of the Tasks table changes. Creates a new
     * TaskDetailView and TaskDetailController - the view is shown within 
     * this controller's view.
     */
    private void taskValueChanged() {
        if (view.getSelectedTask() == null) {
            view.setDetailViewPanel(null);
        }
        else {
            TaskDetailView taskDetailView = new TaskDetailView();
            view.setDetailViewPanel(taskDetailView);
            taskDetailView.addAssetViewButtonActionListener(new TaskDetailAssetViewButtonActionListener());
            
            taskDetailController = new TaskDetailController(taskDetailView, view.getSelectedTask(), currentUser, view);
            taskDetailController.initialise();
        }
    }
    
    /**
     * Called when the selected value of the Components table changes. Creates a new
     * ComponentDetailView and ComponentDetailController - the view is shown within 
     * this controller's view.
     */
    private void componentValueChanged() {
        if (view.getSelectedComponent() == null) {
            view.setDetailViewPanel(null);
        }
        else {
            ComponentDetailView componentDetailView = new ComponentDetailView();
            view.setDetailViewPanel(componentDetailView);
            componentDetailView.addAssetViewButtonActionListener(new ComponentDetailAssetViewButtonActionListener());
            
            componentDetailController = new ComponentDetailController(componentDetailView, view.getSelectedComponent());
            componentDetailController.initialise();
        }
    }
    
    /**
     * Called when the selected value of the Assets table changes. Creates a new
     * AssetDetailView and AssetDetailController - the view is shown within 
     * this controller's view.
     */
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
    
    /**
     * Called whenever a selected value changes in any of the 4 tables.
     */
    private void valueChanged() {
        view.setCursor(new Cursor(Cursor.WAIT_CURSOR));
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
        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void update(Observable o, Object o1) {
        this.currentUser = User.getUserByUsername(this.currentUser.getUsername());
        
        // Store any currently selected Models in the tables
        int selectedProjectId = (view.getSelectedProject() == null ? -1 : view.getSelectedProject().getId());
        int selectedTaskId = (view.getSelectedTask() == null ? -1 : view.getSelectedTask().getId());
        int selectedComponentId = (view.getSelectedComponent() == null ? -1 : view.getSelectedComponent().getId());
        int selectedAssetId = (view.getSelectedAsset() == null ? -1 : view.getSelectedAsset().getId());
        
        // Repopulate the tables
        populateTables();
        
        // Select any models which were previously selected
        view.setSelectedProject(Project.getProjectById(selectedProjectId));
        view.setSelectedTask(Task.getTaskByID(selectedTaskId));
        view.setSelectedComponent(Component.getComponentByID(selectedComponentId));
        view.setSelectedAsset(Asset.getAssetByID(selectedAssetId));
        
        // Ensure that we remain looking at the same tab / detail view
        valueChanged();
    }
    
    /**
     * Event listener for the 'Create New Project' button. Clears the selected
     * Project and shows a new ProjectDetailView with blank / default values.
     */
    class NewProjectButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {        
            ProjectDetailView detailView = new ProjectDetailView();
            detailView.addDiscardButtonActionListener(new DiscardNewActionListener());
            
            projectDetailController = new ProjectDetailController(detailView, new Project());
            projectDetailController.initialise();
            
            view.clearProjectSelection();
            view.setDetailViewPanel(detailView);
        }
    }
    
    /**
     * Event listener for the 'Create New Task' button. Clears the selected
     * Task and shows a new TaskDetailView with blank / default values.
     */
    class NewTaskButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TaskDetailView detailView = new TaskDetailView();
            detailView.addDiscardButtonActionListener(new DiscardNewActionListener());
            
            taskDetailController = new TaskDetailController(detailView, new Task(), currentUser, view);
            taskDetailController.initialise();
            
            view.clearTaskSelection();
            view.setDetailViewPanel(detailView);
        }
    }
    
    /**
     * Event listener for the 'Create New Component' button. Clears the selected
     * Component and shows a new ComponentDetailView with blank / default values.
     */
    class NewComponentButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ComponentDetailView detailView = new ComponentDetailView();
            detailView.addDiscardButtonActionListener(new DiscardNewActionListener());
            
            componentDetailController = new ComponentDetailController(detailView, new Component());
            componentDetailController.initialise();
            
            view.clearComponentSelection();
            view.setDetailViewPanel(detailView);            
        }
    }
    
    /**
     * Event listener for the 'Create New Asset' button. Clears the selected
     * Asset and shows a new AssetDetailView with blank / default values.
     */
    class NewAssetButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AssetDetailView detailView = new AssetDetailView();
            detailView.addDiscardButtonActionListener(new DiscardNewActionListener());
            
            assetDetailController = new AssetDetailController(detailView, new Asset());
            assetDetailController.initialise();
            
            view.clearAssetSelection();
            view.setDetailViewPanel(detailView);  
        }
    }
    
    /**
     * Event listener for the 'Import Assets' button. Shows a new ImportAssetsView.
     */
    class ImportAssetsButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ImportAssetsView detailView = new ImportAssetsView();
            
            importAssetsController = new ImportAssetsController(detailView);
            importAssetsController.initialise();
            
            view.clearAssetSelection();
            view.setDetailViewPanel(detailView);
        }
    }
    
    /**
     * Event listener for the 'Discard changes' button in all DetailViews. If a 
     * new model is discarded, the detail area in this controller's view is emptied.
     */
    class DiscardNewActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setDetailViewPanel(new JPanel());
        }
    }
    
    /**
     * Event listener for the Application->Exit menu option. Exits the entire application.
     */
    class ApplicationMenuExitActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    /**
     * Event listener for the User->Log Out menu option. Closes the view and returns 
     * to the login screen.
     */
    class UserMenuLogOutActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            for (Frame frame : Frame.getFrames()) {
                if (frame.getTitle().equals("MPPMS - Login")) {
                    frame.setState(Frame.NORMAL);
                }
            }
            AppTracker.getInstance().userLoggedOut(currentUser.getUsername());
        }
    }
    
    /**
     * Event listener for the Projects table. Forces the detail area to update
     * thus displaying the newly selected Project (or nothing if deselecting).
     */
    class ProjectsTableListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            projectValueChanged();
        }
    }
    
    /**
     * Event listener for the Tasks table. Forces the detail area to update
     * thus displaying the newly selected Task (or nothing if deselecting).
     */
    class TasksTableListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            taskValueChanged();
        }
    }
    
    /**
     * Event listener for the Components table. Forces the detail area to update
     * thus displaying the newly selected Component (or nothing if deselecting).
     */
    class ComponentsTableListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            componentValueChanged();
        }
    }
    
    /**
     * Event listener for the Assets table. Forces the detail area to update
     * thus displaying the newly selected Asset (or nothing if deselecting).
     */
    class AssetsTableListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            assetValueChanged();
        }
    }
    
    /**
     * Event listener for the view's tab pane. Forces the detail area to update
     * thus displaying whatever is selected in the newly chosen tab.
     */
    class TabChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent ce) {
            valueChanged();
        }        
    }
    
    /**
     * Event listener for the 'View' button alongside the list of Tasks in the
     * ProjectDetailView. Switches to the Tasks tab and displays the Task which
     * was selected in the ProjectDetailView.
     */
    class ProjectDetailTaskViewButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setSelectedTask(projectDetailController.getSelectedTask());
            view.setSelectedTab("Tasks");
        }
    }
    
    /**
     * Event listener for the 'View' button alongside the list of Components in the
     * ProjectDetailView. Switches to the Components tab and displays the Component which
     * was selected in the ProjectDetailView.
     */
    class ProjectDetailComponentViewButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setSelectedComponent(projectDetailController.getSelectedComponent());
            view.setSelectedTab("Components");
        }
    }
    
    /**
     * Event listener for the 'View' button alongside the list of Assets in the
     * TaskDetailView. Switches to the Assets tab and displays the Asset which
     * was selected in the TaskDetailView.
     */
    class TaskDetailAssetViewButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setSelectedAsset(taskDetailController.getSelectedAsset());
            view.setSelectedTab("Assets");
        }
    }
    
    /**
     * Event listener for the 'View' button alongside the list of Assets in the
     * ComponentDetailView. Switches to the Assets tab and displays the Asset which
     * was selected in the ComponentDetailView.
     */
    class ComponentDetailAssetViewButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setSelectedAsset(componentDetailController.getSelectedAsset());
            view.setSelectedTab("Assets");
        }
    }
    
    /**
     * Event listener for the 'Project Overview' button inside ProjectDetailView.
     * Opens the ProjectOverviewView for the selected project.
     */
    class ProjectDetailOverviewButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProjectOverviewView projectOverviewView = new ProjectOverviewView();
            projectOverviewView.addViewButtonActionListener(new ProjectOverviewViewButtonActionListener());
            
            projectOverviewController = new ProjectOverviewController(view.getSelectedProject(), projectOverviewView);
            projectOverviewController.launch();
        }
    }

    /**
     * Event listener for the 'Content Hierarchy' button. Opens the ContentHierarchyView.
     */
    class ContentHierarchyButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProjectsHierarchyView detailView = new ProjectsHierarchyView();
            
            contentHierarchyController = new ProjectsHierarchyController(detailView, currentUser);
            contentHierarchyController.initialise();  
        }
    }
    
    /**
     * Event listener for the 'View' button in the ProjectOverviewView. Switches to the 
     * Projects tab and selects the Project which was selected in ProjectOverview.
     */
    class ProjectOverviewViewButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setSelectedTask(projectOverviewController.getSelectedTask());
            view.setSelectedTab("Tasks");
            view.toFront();
        }
    }
}
