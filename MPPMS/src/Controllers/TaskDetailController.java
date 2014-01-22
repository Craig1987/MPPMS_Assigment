package Controllers;

import Models.SetOfComponents;
import Models.SetOfTasks;
import Models.SetOfUsers;
import Models.Task;
import Models.User;
import Views.TaskDetailView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.DefaultComboBoxModel;

public class TaskDetailController {
    private final TaskDetailView view;
    private final Task task;
    
    private ModelChoiceController modelChoiceController;
    
    public TaskDetailController(TaskDetailView view, Task task) {
        this.view = view;
        this.task = task;
    }
    
    public void initialise() {
        if (task != null) {
            // Populate ui controls
            view.setIdLabelText("ID: " + task.getId());
            view.setTitleText(task.getTitle());
            view.setStatus(new DefaultComboBoxModel<>(Task.Status.values()), task.getStatus().ordinal());
            view.setPriority(new DefaultComboBoxModel<>(Task.Priority.values()), task.getPriority().ordinal());
            view.setReportText(task.getReport().toString());
            view.setAssignedTo(task.getAssignedTo().toArray());
            
            // Add event listeners
            view.addAssignedToChoiceActionListener(new AssignedToChoiceActionListener());
        }
    }
    
    class AssignedToChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController(User.getAllUsers(), task.getAssignedTo());
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    class ModelChoiceSaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SetOfUsers users = new SetOfUsers();
            users.addAll((Collection)modelChoiceController.getChosenModels());                    
            modelChoiceController.closeView();                    
            task.setAssignedTo(users);
            view.setAssignedTo(users.toArray());
        }        
    }
}
