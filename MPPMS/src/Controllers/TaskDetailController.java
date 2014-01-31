package Controllers;

import Application.AppObservable;
import Models.Asset;
import Models.SetOfAssets;
import Models.SetOfUsers;
import Models.Task;
import Models.User;
import Models.User.Role;
import Views.TaskDetailView;
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

public class TaskDetailController implements Observer {
    private final TaskDetailView view;
    private final boolean canEdit;
    private final User currentUser;
    
    private Task task;
    private boolean isNew;
    
    private ModelChoiceController modelChoiceController;
    
    public TaskDetailController(TaskDetailView view, Task task, User currentUser) {
        this.view = view;
        this.task = task;
        this.isNew = task.getId() == 0;
        this.canEdit = (currentUser.getRole() == Role.ProjectManager || 
                        currentUser.getRole() == Role.ProjectCoordinator || 
                        currentUser.getRole() == Role.QCTeamLeader);
        this.currentUser = currentUser;
    }
    
    public void initialise() {
        refreshView();
        
        this.view.setEditMode(this.isNew, this.canEdit);
        this.view.setCanEditAsset(false);
        
        this.view.addAssignedToChoiceActionListener(new AssignedToChoiceActionListener());
        this.view.addAssetChoiceActionListener(new AssetChoiceActionListener());
        this.view.addAssetEditActionListener(new AssetEditActionListener());
        this.view.addSaveButtonActionListener(new SaveButtonActionListener());
        this.view.addEditButtonActionListener(new EditButtonActionListener());
        this.view.addAssetsListSelectionListener(new AssetsListSelectionListener());
        if (!this.isNew) {
            this.view.addDiscardButtonActionListener(new DiscardButtonActionListener());
            this.view.addEditReportActionListener(new EditReportActionListener());
        }
        
        AppObservable.getInstance().addObserver(this);
    }
    
    private void refreshView() {
        view.setIdLabelText("ID: " + task.getId());
        view.setTitleText(task.getTitle());
        view.setTaskType(Task.TaskType.values(), task.getTaskType());
        view.setStatus(Task.Status.values(), task.getStatus());
        view.setPriority(Task.Priority.values(), task.getPriority());
        view.setReportText(task.getReport().toString());
        view.setAssignedTo(task.getAssignedTo().toArray());
        view.setAssets(task.getAssets().toArray());
    }
    
    private boolean validateUserInputs() {
        ArrayList<String> errors = new ArrayList();
        
        if (this.view.getTaskTitle().equals("")) {
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
            this.task = Task.getTaskByID(this.task.getId());
            refreshView();
        }
    }
    
    public Asset getSelectedAsset() {
        return (Asset)this.view.getSelectedAsset();
    }
    
    class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (validateUserInputs()) {
                view.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                
                Object[] objects = view.getAssignedTo();
                SetOfUsers assignedTo = new SetOfUsers();
                for (Object object : objects) {
                    assignedTo.add((User)object);
                }

                objects = view.getAssets();
                SetOfAssets assets = new SetOfAssets();
                for (Object object : objects) {
                    assets.add((Asset)object);
                }

                Task temp = task;
                
                task.setTaskType(Task.TaskType.valueOf(view.getTaskType().toString()));
                task.setTitle(view.getTaskTitle());            
                task.setPriority(Task.Priority.valueOf(view.getPriority().toString()));
                task.setStatus(Task.Status.valueOf(view.getStatus().toString()));
                task.setAssignedTo(assignedTo);
                task.setAssets(assets);
                
                if (task.save()) {
                    if (modelChoiceController != null) {
                        modelChoiceController.closeView();
                    }
                    view.setEditMode(false, canEdit);
                    isNew = false;
                }
                else {
                    task = temp;
                    JOptionPane.showMessageDialog(view, "Error saving Task", "'Task' Error", JOptionPane.ERROR_MESSAGE);
                }
                
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
    
    class AssignedToChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController((Collection)User.getAllUsers().clone(), task.getAssignedTo(), view);
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceAssignedToSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    class AssetChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController((Collection)Asset.getAllAssets().clone(), task.getAssets(), view);
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceAssetsSaveActionListener());
            modelChoiceController.launch();
        }
    }
    
    class AssetEditActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            // TODO
        }
    }
    
    class ModelChoiceAssignedToSaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SetOfUsers users = new SetOfUsers();
            users.addAll((Collection)modelChoiceController.getChosenModels());                    
            modelChoiceController.closeView();
            view.setAssignedTo(users.toArray());
        }        
    }
    
    class ModelChoiceAssetsSaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SetOfAssets assets = new SetOfAssets();
            assets.addAll((Collection)modelChoiceController.getChosenModels());                    
            modelChoiceController.closeView();
            view.setAssets(assets.toArray());
        }        
    }
    
    class EditReportActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            ReportDetailController reportDetailController = new ReportDetailController(task.getReport(), view, currentUser);
            reportDetailController.launch();
        }
    }
    
    class AssetsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setCanEditAsset(!((DefaultListSelectionModel)lse.getSource()).isSelectionEmpty());
        }
    }
}
