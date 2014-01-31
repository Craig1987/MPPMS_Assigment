package Controllers;

import Application.AppObservable;
import Models.Project;
import Models.SetOfTasks;
import Models.Task;
import Views.ProjectOverviewView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Controller for the ProjectOverviewView. Observes AppObservable.
 * 
 * @see ProjectOverviewView
 * @see AppObservable
 */
public class ProjectOverviewController implements Observer {
    private final ProjectOverviewView view;
    
    private Project project;
    private Task task;

    /**
     * ProjectOverviewController constructor
     * 
     * @param project The Project to display an overview of.
     * @param view This controller's view.
     */
    public ProjectOverviewController(Project project, ProjectOverviewView view) {
        this.project = project;
        this.view = view;
    }
    
    /**
     * Initialises the view's data, adds event listener and makes the view visible.
     */
    public void launch() {        
        this.view.setOptions(Task.Status.values(), Task.Status.Created);
        this.view.setViewButtonEnabled(false);
        
        this.view.addProjectComboActionListener(new ProjectComboActionListener());
        this.view.addOptionComboActionListener(new OptionComboActionListener());
        this.view.addTasksListSelectionListener(new TasksListSelectionListener());
        
        this.view.setVisible(true);
        
        refreshView();
        
        /**
         * Craig - TC B2c: Real time updates
         * Register this controller as an observer
         */
        AppObservable.getInstance().addObserver(this);
    }
    
    /**
     * Gets the selected Task in the view.
     * 
     * @return The Project's selected Task
     */
    public Task getSelectedTask() {
        return (Task)this.view.getSelectedTask();
    }
    
    /**
     * Refreshes data displayed in the view about this Project and its Tasks.
     */
    private void refreshView() {
        this.view.setTasks(getTasks().toArray(), this.task);
        this.view.setProjects(Project.getAllProjects().toArray(), this.project);
    }
    
    /**
     * Gets all Tasks for this Project.
     * 
     * @return Set of all Tasks which are part of the Project.
     */
    private SetOfTasks getTasks() {
        SetOfTasks tasks = new SetOfTasks();
        for (Task t : this.project.getTasks()) {
            if (t.getStatus() == (Task.Status)this.view.getOption()) {
                tasks.add(t);
            }
        }
        return tasks;
    }
    
    @Override
    public void update(Observable o, Object o1) {
        this.project = Project.getProjectById(this.project.getId());
        refreshView();
    }
    
    /**
     * Event listener for the Projects ComboBox. Updates the selected Project and
     * shows the new data in the view.
     */
    class ProjectComboActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            project = (Project)view.getSelectedProject();
            refreshView();
        }
    }
    
    /**
     * Event listener for the Options ComboBox. Updates the list of Tasks based on 
     * the criteria selected (the option).
     */
    class OptionComboActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            view.setOptionLabel("This Project's " + ((JComboBox)ae.getSource()).getSelectedItem() + " Tasks:");
            refreshView();
        }
    }
    
    /**
     * Event listener for the list of Tasks. Either enabled or disables the 'View' button
     * in the view.
     */
    class TasksListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setViewButtonEnabled(!((DefaultListSelectionModel)lse.getSource()).isSelectionEmpty());
            task = (Task)view.getSelectedTask();
        }
    }
}
