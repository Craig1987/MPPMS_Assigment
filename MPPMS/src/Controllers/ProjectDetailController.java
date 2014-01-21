package Controllers;

import Models.Component;
import Models.Project;
import Models.Task;
import Models.User;
import Views.ProjectDetailView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;

public class ProjectDetailController {
    private final ProjectDetailView view;
    private final Project project;
    
    public ProjectDetailController(ProjectDetailView view, Project project) {
        this.view = view;        
        this.project = project;
    }
    
    public void initialise() {
        // Populate the ui controls
        if (project != null) {
            this.view.setIdLabelText("ID: " + project.getId());
            this.view.setProjectTitleText(project.getTitle());
            this.view.setManagerText(project.getManager().getName());
            this.view.setCoordinatorText(project.getCoordinator().getName());
            this.view.setCreationDateText(new SimpleDateFormat("dd MMM yyyy").format(project.getCreationDate()));
            this.view.setDeadlineText(new SimpleDateFormat("dd MMM yyyy").format(project.getDeadline()));
            this.view.setPriority(new DefaultComboBoxModel<>(Project.Priority.values()), project.getPriority().ordinal());
            this.view.setTeam(new DefaultComboBoxModel<>(project.getTeam().toArray()));
            this.view.setTasks(new DefaultComboBoxModel<>(project.getTasks().toArray()));
            this.view.setComponents(new DefaultComboBoxModel<>(project.getComponents().toArray()));
            this.view.addTeamChoiceActionListener(new TeamChoiceActionListener());
            this.view.addTasksChoiceActionListener(new TasksChoiceActionListener());
            this.view.addComponentsChoiceActionListener(new ComponentsChoiceActionListener());
        }
    }
    
    class TeamChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            ModelChoiceController controller = new ModelChoiceController(User.getAllUsers(), project.getTeam());
            controller.launch();
        }        
    }
    
    class TasksChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            ModelChoiceController controller = new ModelChoiceController(Task.getAllTasks(), project.getTasks());
            controller.launch();
        }        
    }
    
    class ComponentsChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            ModelChoiceController controller = new ModelChoiceController(Component.getAllComponents(), project.getComponents());
            controller.launch();
        }        
    }
}
