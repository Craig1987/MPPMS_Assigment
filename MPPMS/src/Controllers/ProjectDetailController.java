package Controllers;

import Models.Project;
import Views.ProjectDetailView;
import java.text.SimpleDateFormat;

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
        
        //this.cmboPriority.removeAllItems();
        //this.cmboPriority.addItem("1 - Highest");
        //this.cmboPriority.addItem("2");
        //this.cmboPriority.addItem("3");        
        //this.cmboPriority.addItem("4");
        //this.cmboPriority.addItem("5 - Lowest");
    }
}
