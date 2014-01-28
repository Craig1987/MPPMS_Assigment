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

public class ProjectDetailController implements Observer {
    private final ProjectDetailView view;
    private final boolean canEdit;
    private final boolean canEditTasks;
    
    private Project project;    
    private boolean isNew;
    
    private ModelChoiceController modelChoiceController;
    
    public ProjectDetailController(ProjectDetailView view, Project project) {
        this.view = view;        
        this.project = project;
        this.isNew = true;
        this.canEdit = true;
        this.canEditTasks = true;
    }
    
    public ProjectDetailController(ProjectDetailView view, Project project, boolean canEdit, boolean canEditTasks) {
        this.view = view;        
        this.project = project;
        this.isNew = false;
        this.canEdit = canEdit;
        this.canEditTasks = canEditTasks;
    }
    
    public void initialise() {
        refreshView();
            
        this.view.setEditMode(this.isNew, this.canEdit);
        this.view.setCanEditTask(false);
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
        
        AppObservable.getInstance().addObserver(this);
    }
    
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
    
    public Task getSelectedTask() {
        return (Task)this.view.getSelectedTask();
    }
    
    public Component getSelectedComponent() {
        return (Component)this.view.getSelectedComponent();
    }
    
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
    
    class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (validateUserInputs()) {
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
                
                Project newProject = new Project(project.getId(), view.getCreationDate());
                newProject.setDeadline(view.getDeadlineDate());
                newProject.setTitle(view.getProjectTitle());
                newProject.setPriority(Project.Priority.valueOf(view.getPriority().toString()));
                newProject.setClient(view.getClient());
                newProject.setManager((User)view.getManager());
                newProject.setCoordinator((User)view.getCoordinator());
                newProject.setTeam(team);
                newProject.setTasks(tasks);
                newProject.setComponents(components);
                if (newProject.save()) {
                    if (modelChoiceController != null) {
                        modelChoiceController.closeView();
                    }
                    view.setEditMode(false, canEdit);
                    isNew = false;
                }
            }
        }        
    }
    
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
    
    class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {   
            view.setEditMode(true, canEdit);
        }        
    }
    
    class TeamChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController((Collection)User.getAllUsers().clone(), project.getTeam(), view);
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    class TasksChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController((Collection)Task.getAllTasks().clone(), project.getTasks(), view);
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    class ComponentsChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController((Collection)Component.getAllComponents().clone(), project.getComponents(), view);
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
    
    class TasksListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setCanEditTask(!((DefaultListSelectionModel)lse.getSource()).isSelectionEmpty() && canEditTasks);
        }
    }
    
    class ComponentsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setCanEditComponent(!((DefaultListSelectionModel)lse.getSource()).isSelectionEmpty());
        }
    }
}
