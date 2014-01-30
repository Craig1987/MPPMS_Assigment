package Controllers;

import Application.AppObservable;
import Models.Comment;
import Models.Report;
import Models.User;
import Views.ReportDetailView;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ReportDetailController implements Observer { 
    private final ReportDetailView view = new ReportDetailView();
    private final User currentUser;
    
    private boolean isNewComment;    
    private Report report;
    private Comment selectedComment;
    
    public ReportDetailController(Report report, JPanel locationParent, User currentUser) {
        this.report = report;
        this.view.setLocationRelativeTo(locationParent);
        this.currentUser = currentUser;
        this.isNewComment = false;
        this.selectedComment = new Comment();
    }
    
    public void launch() {
        view.setEditMode(false);        
        view.setComments(report.getComments().toArray());
        
        view.addCommentsListSelectionListener(new CommentsListSelectionListener());
        view.addEditButtonActionListener(new EditButtonActionListener());
        view.addDiscardButtonActionListener(new DiscardChangesActionListener());
        view.addSaveButtonActionListener(new SaveCommentChangesActionListener());
        view.addNewCommentActionListener(new NewCommentActionListener());
        
        view.setVisible(true);
        
        refreshView();
        
        AppObservable.getInstance().addObserver(this);
    }
    
    public void refreshView() {
        Comment comment;
        
        if (isNewComment) {
            comment = new Comment(0, new Date(), currentUser, "");
            view.clearCommentSelection();
        }
        else {
            comment = this.selectedComment;
        }
        
        view.setReportIdLabelText("(Report ID: " + report.getId() + ") " + report.getTitle());
        view.setCommentIdLabel("Comment ID: " + comment.getId());
        view.setCommentDate(new SimpleDateFormat("dd MMM YYYY").format(comment.getDate()));
        view.setUsername(comment.getUser().getName());
        view.setContent(comment.getContent());
        view.setPanelVisibility(isNewComment || view.getSelectedComment().getId() > 0);
    }
    
    private boolean validateUserInputs() {
        ArrayList<String> errors = new ArrayList();
        
        if (this.view.getContent().equals("")) {
            errors.add("\t - Enter a comment");
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
        int index = this.view.getSelectedIndex();
        this.report = Report.getReportByID(this.report.getId());
        this.view.setComments(this.report.getComments().toArray());
        this.view.setSelectedIndex(index);
    }
    
    class CommentsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            selectedComment = view.getSelectedComment();
            refreshView();
        }
    }
    
    class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            view.setEditMode(true);
            refreshView();
        }
    }
    
    class DiscardChangesActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            view.setEditMode(false);
            isNewComment = false;
            refreshView();
        }
    }
    
    class SaveCommentChangesActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (validateUserInputs()) {
                view.setCursor(new Cursor(Cursor.WAIT_CURSOR));

                if (isNewComment)
                {
                    Report tempReport = report;
                    Comment tempComment = selectedComment;

                    selectedComment.setContent(view.getContent());
                    selectedComment.setDate(new Date());
                    selectedComment.setUser(currentUser);

                    report.addComment(selectedComment);
                    isNewComment = false;

                    if (report.save()) {
                        view.setEditMode(false);
                    }
                    else {
                        isNewComment = true;
                        report = tempReport;
                        selectedComment = tempComment;
                        JOptionPane.showMessageDialog(view, "Error saving Report", "'Report' Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    Comment temp = null;

                    for (Comment comment : report.getComments()) {
                        if (comment.getId() == view.getSelectedComment().getId()) {
                            temp = comment;
                            selectedComment = comment;
                            comment.setUser(currentUser);
                            comment.setDate(new Date());
                            comment.setContent(view.getContent());
                        }
                    }

                    if (report.save()) {
                        view.setEditMode(false);
                    }
                    else {
                        for (Comment comment : report.getComments()) {
                            if (comment.getId() == view.getSelectedComment().getId()) {
                                comment = temp;
                            }
                        }
                        selectedComment = temp;
                        JOptionPane.showMessageDialog(view, "Error saving Report", "'Report' Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
    
    class NewCommentActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            view.setEditMode(true);
            isNewComment = true;
            refreshView();
        }
    }
}