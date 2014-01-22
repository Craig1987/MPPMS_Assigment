package Controllers;

import Models.Component;
import Models.Project;
import Models.SetOfComponents;
import Models.SetOfTasks;
import Models.SetOfUsers;
import Models.Task;
import Models.User;
import Views.ProjectDetailView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Collection;
import javax.swing.DefaultComboBoxModel;

public class ProjectDetailController {
    private final ProjectDetailView view;
    private final Project project;
    
    private ModelChoiceController modelChoiceController;
    
    public ProjectDetailController(ProjectDetailView view, Project project) {
        this.view = view;        
        this.project = project;
    }
    
    public void initialise() {
        if (project != null) {
            // Populate the ui controls
            this.view.setIdLabelText("ID: " + project.getId());
            this.view.setProjectTitleText(project.getTitle());
            this.view.setManagerText(project.getManager().getName());
            this.view.setCoordinatorText(project.getCoordinator().getName());
            this.view.setCreationDateText(new SimpleDateFormat("dd MMM yyyy").format(project.getCreationDate()));
            this.view.setDeadlineText(new SimpleDateFormat("dd MMM yyyy").format(project.getDeadline()));
            this.view.setPriority(Project.Priority.values(), project.getPriority().ordinal());
            this.view.setTeam(project.getTeam().toArray());
            this.view.setTasks(project.getTasks().toArray());
            this.view.setComponents(project.getComponents().toArray());
            
            // Add event listeners
            this.view.addTeamChoiceActionListener(new TeamChoiceActionListener());
            this.view.addTasksChoiceActionListener(new TasksChoiceActionListener());
            this.view.addComponentsChoiceActionListener(new ComponentsChoiceActionListener());
        }
    }
    
    class TeamChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController(User.getAllUsers(), project.getTeam());
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    class TasksChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController(Task.getAllTasks(), project.getTasks());
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    class ComponentsChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController(Component.getAllComponents(), project.getComponents());
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    class ModelChoiceSaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            switch (modelChoiceController.getModelType()) {
                
                case User:
                    SetOfUsers users = new SetOfUsers();
                    users.addAll((Collection)modelChoiceController.getChosenModels());                    
                    modelChoiceController.closeView();                    
                    project.setTeam(users);
                    view.setTeam(users.toArray());
                    break;
                    
                case Task:
                    SetOfTasks tasks = new SetOfTasks();
                    tasks.addAll((Collection)modelChoiceController.getChosenModels());
                    modelChoiceController.closeView();                    
                    project.setTasks(tasks);
                    view.setTeam(tasks.toArray());
                    break;
                    
                case Component:
                    SetOfComponents components = new SetOfComponents();
                    components.addAll((Collection)modelChoiceController.getChosenModels());
                    modelChoiceController.closeView();                    
                    project.setComponents(components);
                    view.setTeam(components.toArray());
                    break;
            }
        }        
    }
}
