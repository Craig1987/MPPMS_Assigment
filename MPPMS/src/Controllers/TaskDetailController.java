package Controllers;

import Application.AppObservable;
import Models.Asset;
import Models.Report;
import Models.SetOfAssets;
import Models.SetOfUsers;
import Models.Task;
import Models.User;
import Views.TaskDetailView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

public class TaskDetailController implements Observer {
    private final TaskDetailView view;
    
    private Task task;
    private boolean isNew;
    
    private ModelChoiceController modelChoiceController;
    
    public TaskDetailController(TaskDetailView view, Task task) {
        this.view = view;
        this.task = task;
        this.isNew = this.task.getId() < 1;
    }
    
    public void initialise() {
        refreshView();
        
        this.view.setEditMode(this.isNew);
        
        this.view.addAssignedToChoiceActionListener(new AssignedToChoiceActionListener());
        this.view.addAssetChoiceActionListener(new AssetChoiceActionListener());
        this.view.addAssetEditActionListener(new AssetEditActionListener());
        this.view.addSaveButtonActionListener(new SaveButtonActionListener());
        this.view.addEditButtonActionListener(new EditButtonActionListener());
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
        view.setReportText((task.getReport().getId() < 1 ? "Blank report" : task.getReport().toString()));
        view.setAssignedTo(task.getAssignedTo().toArray());
        view.setAssets(task.getAssets().toArray());
    }

    @Override
    public void update(Observable o, Object o1) {
        if (!this.isNew) {
            this.task = Task.getTaskByID(this.task.getId());
            refreshView();
        }
    }
    
    class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setEditMode(false);
            isNew = false;
            
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
            
            Task newTask = new Task(task.getId(), Task.TaskType.valueOf(view.getTaskType().toString()));
            newTask.setTitle(view.getTaskTitle());            
            newTask.setPriority(Task.Priority.valueOf(view.getPriority().toString()));
            newTask.setStatus(Task.Status.valueOf(view.getStatus().toString()));
            newTask.setReport(Report.getReportByID(task.getReport().getId()));
            newTask.setAssignedTo(assignedTo);
            newTask.setAssets(assets);
            newTask.save();
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
    
    class AssignedToChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController(User.getAllUsers(), task.getAssignedTo());
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceAssignedToSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    class AssetChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController(Asset.getAllAssets(), task.getAssets());
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
            ReportDetailController reportDetailController = new ReportDetailController(task.getReport());
            reportDetailController.launch();
        }
    }
}
