package Controllers;

import Models.Report;
import Views.ReportDetailView;

public class ReportDetailController { 
    private final ReportDetailView view = new ReportDetailView();
    private final Report report;
    
    public ReportDetailController(Report report) {
        this.report = report;
    }
    
    public void launch() {
        this.view.setListOfComments(report.getAllComments().toArray());
        this.view.setVisible(true);
    }
    
    public void initialise() {
        if (report != null) {
            // Populate ui controls
            view.setIdLabelText("ID: " + report.getId());
        }
    }
}
