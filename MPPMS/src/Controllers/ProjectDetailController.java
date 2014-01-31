package Controllers;

import Application.AppObservable;
import Models.Component;
import Models.Project;
import Models.SetOfComponents;
import Models.SetOfTasks;
import Models.SetOfUsers;
import Models.Task;
import Models.User;
import Views.ProjectDetailView;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Controller for ProjectDetailView.
 * 
 * @see ProjectDetailView
 */
public class ProjectDetailController implements Observer {
    private final ProjectDetailView view;
    private final boolean canEdit;
    
    private Project project;    
    private boolean isNew;
    
    private ModelChoiceController modelChoiceController;
    
    /**
     * ProjectDetailController constructor. This constructor is used when the 
     * 'Create New Project' button is clicked in IndexView.
     * @param view This controller's view
     * @param project The project to use for displaying data in the view (will be 
     * default Project in this case with an ID of 0).
     * @see IndexView
     * @see ProjectDetailView
     */
    public ProjectDetailController(ProjectDetailView view, Project project) {
        this.view = view;        
        this.project = project;
        this.isNew = true;
        this.canEdit = true;
    }
    
    /**
     * ProjectDetailController constructor. This constructor is used the user chooses 
     * to view and existing Project.
     * @param view This controller's view
     * @param project The selected Project
     * @param canEdit Whether or not editing is allowed for the logged in User
     */
    public ProjectDetailController(ProjectDetailView view, Project project, boolean canEdit, boolean canEditTasks) {
        this.view = view;        
        this.project = project;
        this.isNew = false;
        this.canEdit = canEdit;
    }
    
    /**
     * Initialises the view, adds event listeners and makes the view visible.
     */
    public void initialise() {
        refreshView();
            
        this.view.setEditMode(this.isNew, this.canEdit);
        this.view.setCanViewTask(false);
        this.view.setCanEditComponent(false);
        this.view.setCanViewOverview(this.canEdit && !this.isNew);

        // Add event listeners
        this.view.addTeamChoiceActionListener(new TeamChoiceActionListener());
        this.view.addTasksChoiceActionListener(new TasksChoiceActionListener());
        this.view.addComponentsChoiceActionListener(new ComponentsChoiceActionListener());        
        this.view.addSaveButtonActionListener(new SaveButtonActionListener());
        this.view.addEditButtonActionListener(new EditButtonActionListener());
        this.view.addTasksListSelectionListener(new TasksListSelectionListener());
        this.view.addComponentsListSelectionListener(new ComponentsListSelectionListener());
        if (!this.isNew) {
            this.view.addDiscardButtonActionListener(new DiscardButtonActionListener());
        }
        
        /**
         * Craig - TC B2c: Real time updates
         * Register this controller as an observer
         */
        AppObservable.getInstance().addObserver(this);
    }
    
    /**
     * Refreshes all of the selected Project's data which is shown in the view.
     */
    private void refreshView() {
        this.view.setIdLabelText("ID: " + project.getId());
        this.view.setProjectTitleText(project.getTitle());
        this.view.setClient(User.getUsersByRole(User.Role.Client), project.getClient());
        this.view.setManager(User.getUsersByRole(User.Role.ProjectManager).toArray(), project.getManager());
        this.view.setCoordinator(User.getUsersByRole(User.Role.ProjectCoordinator).toArray(), project.getCoordinator());
        this.view.setCreationDateText(project.getCreationDate());
        this.view.setDeadlineText(project.getDeadline());
        this.view.setPriority(Project.Priority.values(), project.getPriority().ordinal());
        this.view.setTeam(project.getTeam().toArray());
        this.view.setTasks(project.getTasks().toArray());
        this.view.setProjectComponents(project.getComponents().toArray());
    }
    
    /**
     * Gets whichever of this Projects Tasks is selected in the list of Tasks.
     * 
     * @return Selected Task
     */
    public Task getSelectedTask() {
        return (Task)this.view.getSelectedTask();
    }
    
    /**
     * Gets whichever of this Projects Components is selected in the list of Components.
     * 
     * @return Selected Component
     */
    public Component getSelectedComponent() {
        return (Component)this.view.getSelectedComponent();
    }
    
    /**
     * Validates the user inputs when saving a new / edited Project.
     * 
     * @return true if validation passes, false if it fails.
     */
    private boolean validateUserInputs() {
        ArrayList<String> errors = new ArrayList();
        
        if (this.view.getProjectTitle().equals("")) {
            errors.add("\t - Enter a title");
        }
        
        if (errors.size() > 0) {
            String errorMsg = "Unable to save new Asset.\nDetails:";
            for (String error : errors) {
                errorMsg += "\n" + error;
            }
            JOptionPane.showMessageDialog(this.view, errorMsg, "Unable to Save", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void update(Observable o, Object o1) {
        if (!this.isNew) {
            this.project = Project.getProjectById(this.project.getId());
            refreshView();
        }
    }
    
    /**
     * Event listener for the 'Save' button. Validates user input and saves the Project
     * if it passes.
     */
    class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (validateUserInputs()) {
                view.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                
                // Setup the project with user inputted data from the view
                Object[] objects = view.getTeam();
                SetOfUsers team = new SetOfUsers();
                for (Object object : objects) {
                    team.add((User)object);
                }

                objects = view.getTasks();
                SetOfTasks tasks = new SetOfTasks();
                for (Object object : objects) {
                    tasks.add((Task)object);
                }

                objects = view.getProjectComponents();
                SetOfComponents components = new SetOfComponents();
                for (Object object : objects) {
                    components.add((Component)object);
                }
                
                Project temp = project;
                
                project.setDeadline(view.getDeadlineDate());
                project.setTitle(view.getProjectTitle());
                project.setPriority(Project.Priority.valueOf(view.getPriority().toString()));
                project.setClient(view.getClient());
                project.setManager((User)view.getManager());
                project.setCoordinator((User)view.getCoordinator());
                project.setTeam(team);
                project.setTasks(tasks);
                project.setComponents(components);
                if (project.save()) {
                    // Success
                    if (modelChoiceController != null) {
                        modelChoiceController.closeView();
                    }
                    view.setEditMode(false, canEdit);
                    isNew = false;
                }
                else {
                    // Failure
                    project = temp;
                    JOptionPane.showMessageDialog(view, "Error saving Project", "'Project' Error", JOptionPane.ERROR_MESSAGE);
                }
                
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }        
    }
    
    /**
     * Event listener for the 'Discard changes' button. Disables editing of the 
     * view's UI controls and reverts and user inputs.
     */
    class DiscardButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (modelChoiceController != null) {
                modelChoiceController.closeView();
            }
            view.setEditMode(false, canEdit);
            refreshView();
        }        
    }
    
    /**
     * Event listener for the 'Edit' button. Enables editing of the view's UI controls.
     */
    class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {   
            view.setEditMode(true, canEdit);
        }        
    }
    
    /**
     * Event listener for the 'Add / Remove' button alongside the Team list. Launches
     * a ModelChoice view to allow the user to add or remove Users.
     */
    class TeamChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController((Collection)User.getAllUsers().clone(), project.getTeam(), view);
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    /**
     * Event listener for the 'Add / Remove' button alongside the Tasks list. Launches
     * a ModelChoice view to allow the user to add or remove Tasks.
     */
    class TasksChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController((Collection)Task.getAllTasks().clone(), project.getTasks(), view);
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    /**
     * Event listener for the 'Add / Remove' button alongside the Components list. Launches
     * a ModelChoice view to allow the user to add or remove Components.
     */
    class ComponentsChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController((Collection)Component.getAllComponents().clone(), project.getComponents(), view);
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    /**
     * Event listener for the 'Save' button in the ModelChoiceView. Updates the
     * view with the new values (Team, Tasks or Components).
     */
    class ModelChoiceSaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            switch (modelChoiceController.getModelType()) {
                
                case User:
                    SetOfUsers users = new SetOfUsers();
                    users.addAll((Collection)modelChoiceController.getChosenModels());                    
                    modelChoiceController.closeView();
                    view.setTeam(users.toArray());
                    break;
                    
                case Task:
                    SetOfTasks tasks = new SetOfTasks();
                    tasks.addAll((Collection)modelChoiceController.getChosenModels());
                    modelChoiceController.closeView();
                    view.setTasks(tasks.toArray());
                    break;
                    
                case Component:
                    SetOfComponents components = new SetOfComponents();
                    components.addAll((Collection)modelChoiceController.getChosenModels());
                    modelChoiceController.closeView();
                    view.setProjectComponents(components.toArray());
                    break;
            }
        }        
    }
    
    /**
     * Event listener for the list of Tasks. If the selected value changes, the view's
     * 'View' button alongside the list of Tasks is either enabled or disabled.
     */
    class TasksListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setCanViewTask(!((DefaultListSelectionModel)lse.getSource()).isSelectionEmpty());
        }
    }
    
    /**
     * Event listener for the list of Components. If the selected value changes, the view's
     * 'View' button alongside the list of Components is either enabled or disabled.
     */
    class ComponentsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setCanEditComponent(!((DefaultListSelectionModel)lse.getSource()).isSelectionEmpty());
        }
    }
}
