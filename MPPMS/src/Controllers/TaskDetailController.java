package Controllers;

import Models.Task;
import Views.TaskDetailView;
import javax.swing.DefaultComboBoxModel;

public class TaskDetailController {
    private final TaskDetailView view;
    private final Task task;
    
    public TaskDetailController(TaskDetailView view, Task task) {
        this.view = view;
        this.task = task;
    }
    
    public void initialise() {
        if (task != null) {
            view.setIdLabelText("ID: " + task.getId());
            view.setTitleText(task.getTitle());
            view.setStatus(new DefaultComboBoxModel<>(Task.Status.values()), task.getStatus().ordinal());
            view.setPriority(new DefaultComboBoxModel<>(Task.Priority.values()), task.getPriority().ordinal());
            view.setReport(task.getReport());
            view.setAssignedTo(task.getAssignedTo());
        }
    }
}
