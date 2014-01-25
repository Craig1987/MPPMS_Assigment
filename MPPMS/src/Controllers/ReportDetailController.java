package Controllers;

import Models.Comment;
import Models.Report;
import Views.ReportDetailView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ReportDetailController { 
    private final ReportDetailView view = new ReportDetailView();
    private final Report report;
    
    public ReportDetailController(Report report) {
        this.report = report;
        
        view.addCommentsListSelectionListener(new CommentsListSelectionListener());
        view.addEditButtonActionListener(new EditButtonActionListener());
        view.addDiscardButtonActionListener(new DiscardChangesActionListener());
        view.addSaveButtonActionListener(new SaveCommentChangesActionListener());
    }
    
    public void launch() {
        view.setListOfComments(report.getAllComments().toArray());
        view.setReportIdLabelText("Report ID: " + report.getId());
        view.setControlsEnabled(false);
        view.setPanelVisibility(false);
        view.setVisible(true);
    }
    
    public void displaySelectedComment() {
        // Populate UI controls       
        if (view.getSelectedComment() == null) {
            view.setPanelVisibility(false);
        }
        else {
            Comment selectedComment = view.getSelectedComment();
            view.setCommentLabelID("Comment ID: " + selectedComment.getId());
            view.setCommentDate(selectedComment.getDate());
            view.setUser(selectedComment.getUser().getName());
            view.setContent(selectedComment.getContent());
            view.setControlsEnabled(false);
            view.setPanelVisibility(true);
        }
    }
    
    class CommentsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            displaySelectedComment();
        }
    }
    
    class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            view.setControlsEnabled(true);
        }
    }
    
    class DiscardChangesActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            
        }
    }
    
    class SaveCommentChangesActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            
        }
    }
}
