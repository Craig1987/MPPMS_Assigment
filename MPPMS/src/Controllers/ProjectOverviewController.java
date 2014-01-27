package Controllers;

import Application.AppObservable;
import Models.Project;
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

    public ProjectOverviewController(Project project, ProjectOverviewView view) {
        this.project = project;
        this.view = view;
    }
    
    public void launch() {
        refreshView();
        
        this.view.addOptionComboActionListener(new OptionComboActionListener());
        this.view.addTasksListSelectionListener(new TasksListSelectionListener());
        
        this.view.setVisible(true);
        
        AppObservable.getInstance().addObserver(this);
    }
    
    private void refreshView() {
        this.view.setTasks(project.getTasks().toArray());
        this.view.setInfoLabel(this.project.toString());
    }
    
    @Override
    public void update(Observable o, Object o1) {
        this.project = Project.getProjectById(this.project.getId());
        refreshView();
    }
    
    class OptionComboActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            view.setOptionLabel(((JComboBox)ae.getSource()).getSelectedItem().toString() + " for this Project");
        }
    }
    
    class TasksListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setViewButtonEnabled(!((DefaultListSelectionModel)lse.getSource()).isSelectionEmpty());
        }
    }
}
