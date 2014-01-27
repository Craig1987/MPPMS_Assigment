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

public class ProjectOverviewController implements Observer {
    private final ProjectOverviewView view;
    
    private Project project;
    private Task task;

    public ProjectOverviewController(Project project, ProjectOverviewView view) {
        this.project = project;
        this.view = view;
    }
    
    public void launch() {
        refreshView();
        
        this.view.setOptions(Task.Status.values(), Task.Status.New);
        this.view.setViewButtonEnabled(false);
        
        this.view.addProjectComboActionListener(new ProjectComboActionListener());
        this.view.addOptionComboActionListener(new OptionComboActionListener());
        this.view.addTasksListSelectionListener(new TasksListSelectionListener());
        
        this.view.setVisible(true);
        
        AppObservable.getInstance().addObserver(this);
    }
    
    private void refreshView() {
        this.view.setTasks(getTasks().toArray(), this.task);
        this.view.setProjects(Project.getAllProjects().toArray(), this.project);
    }
    
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
    
    class ProjectComboActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            project = (Project)view.getSelectedProject();
            refreshView();
        }
    }
    
    class OptionComboActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            view.setOptionLabel("This Project's " + ((JComboBox)ae.getSource()).getSelectedItem() + " Tasks:");
            refreshView();
        }
    }
    
    class TasksListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setViewButtonEnabled(!((DefaultListSelectionModel)lse.getSource()).isSelectionEmpty());
            task = (Task)view.getSelectedTask();
        }
    }
}
