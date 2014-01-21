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
        this.view.setProject(project);
        
        if (project.getId() != null) {
            // Populate the ui controls
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
            
            // Add event listeners
            this.view.addTeamChoiceActionListener(new TeamChoiceActionListener());
            this.view.addTasksChoiceActionListener(new TasksChoiceActionListener());
            this.view.addComponentsChoiceActionListener(new ComponentsChoiceActionListener());
        } else {
            // New Project
            this.view.setSaveButtonVisibility(true);
            this.view.setEditButtonVisibility(false);
        }
        
        this.view.addSaveButtonActionListener(new SaveButtonActionListener());
    }
    
    class SaveButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {   
            view.getProject().save();
            view.displayInfoMessage("Project Saved");
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
                    view.setTeam(new DefaultComboBoxModel<>(users.toArray()));
                    break;
                    
                case Task:
                    SetOfTasks tasks = new SetOfTasks();
                    tasks.addAll((Collection)modelChoiceController.getChosenModels());
                    modelChoiceController.closeView();                    
                    project.setTasks(tasks);
                    view.setTeam(new DefaultComboBoxModel<>(tasks.toArray()));
                    break;
                    
                case Component:
                    SetOfComponents components = new SetOfComponents();
                    components.addAll((Collection)modelChoiceController.getChosenModels());
                    modelChoiceController.closeView();                    
                    project.setComponents(components);
                    view.setTeam(new DefaultComboBoxModel<>(components.toArray()));
                    break;
            }
            view.setProject(project);
        }        
    }
}
