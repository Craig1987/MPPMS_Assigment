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

public class ProjectDetailController {
    private final ProjectDetailView view;
    
    private Project project;    
    private boolean isNew;
    
    private ModelChoiceController modelChoiceController;
    
    public ProjectDetailController(ProjectDetailView view, Project project) {
        this.view = view;        
        this.project = project;
        this.isNew = project.getId() < 1;
    }
    
    public void initialise() {
        refreshView();
            
        this.view.setEditMode(this.isNew);

        // Add event listeners
        this.view.addTeamChoiceActionListener(new TeamChoiceActionListener());
        this.view.addTasksChoiceActionListener(new TasksChoiceActionListener());
        this.view.addComponentsChoiceActionListener(new ComponentsChoiceActionListener());        
        this.view.addSaveButtonActionListener(new SaveButtonActionListener());
        this.view.addEditButtonActionListener(new EditButtonActionListener());
        if (!this.isNew) {
            this.view.addDiscardButtonActionListener(new DiscardButtonActionListener());
        }
    }
    
    private void refreshView() {
        this.view.setIdLabelText("ID: " + project.getId());
        this.view.setProjectTitleText(project.getTitle());
        this.view.setManager(User.getUsersByRole(User.Role.ProjectManager).toArray(), project.getManager());
        this.view.setCoordinatorText(User.getUsersByRole(User.Role.ProjectCoordinator).toArray(), project.getCoordinator());
        this.view.setCreationDateText(new SimpleDateFormat("dd MMM yyyy").format(project.getCreationDate()));
        this.view.setDeadlineText(new SimpleDateFormat("dd MMM yyyy").format(project.getDeadline()));
        this.view.setPriority(Project.Priority.values(), project.getPriority().ordinal());
        this.view.setTeam(project.getTeam().toArray());
        this.view.setTasks(project.getTasks().toArray());
        this.view.setComponents(project.getComponents().toArray());
    }
    
    class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setEditMode(false);
            isNew = false;
        }        
    }
    
    class DiscardButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setEditMode(false);
            refreshView();
        }        
    }
    
    class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {   
            view.setEditMode(true);
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
                    view.setTeam(users.toArray());
                    break;
                    
                case Task:
                    SetOfTasks tasks = new SetOfTasks();
                    tasks.addAll((Collection)modelChoiceController.getChosenModels());
                    modelChoiceController.closeView();
                    view.setTeam(tasks.toArray());
                    break;
                    
                case Component:
                    SetOfComponents components = new SetOfComponents();
                    components.addAll((Collection)modelChoiceController.getChosenModels());
                    modelChoiceController.closeView();
                    view.setTeam(components.toArray());
                    break;
            }
        }        
    }
}
