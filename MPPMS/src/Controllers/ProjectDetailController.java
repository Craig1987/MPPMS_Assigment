package Controllers;

import Models.Project;
import Views.ProjectDetailView;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;

public class ProjectDetailController {
    private final ProjectDetailView view;
    private final Project project;
    
    public ProjectDetailController(ProjectDetailView view, Project project) {
        this.view = view;        
        this.project = project;
        
        // Populate the ui controls
        this.view.setIdLabelText("ID: " + project.getId());
        this.view.setProjectTitleText(project.getTitle());
        this.view.setManagerText(project.getManager().getName());
        this.view.setCoordinatorText(project.getCoordinator().getName());
        this.view.setCreationDateText(new SimpleDateFormat("dd MMM yyyy").format(project.getCreationDate()));
        this.view.setDeadlineText(new SimpleDateFormat("dd MMM yyyy").format(project.getDeadline()));
        this.view.setPriority(new DefaultComboBoxModel<>(Project.Priority.values()), project.getPriority().ordinal());
        this.view.setTeam(new DefaultComboBoxModel<>(project.getTeam()));
        this.view.setTasks(new DefaultComboBoxModel<>(project.getTasks()));
        this.view.setComponents(new DefaultComboBoxModel<>(project.getComponents()));
    }
}
