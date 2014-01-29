package Controllers;

import Application.AppObservable;
import Models.Comment;
import Models.Report;
import Models.User;
import Views.ReportDetailView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ReportDetailController implements Observer { 
    private final ReportDetailView view = new ReportDetailView();
    private final Report report;
    private final User currentUser;
    
    public ReportDetailController(Report report, JPanel locationParent, User currentUser) {
        this.report = report;
        this.view.setLocationRelativeTo(locationParent);
        this.currentUser = currentUser;
    }
    
    public void launch() {
        view.setListOfComments(Comment.getAllComments().toArray());
        view.setReportIdLabelText("Report ID: " + report.getId());
        view.setControlsEnabled(false);
        view.setPanelVisibility(false);
        
        view.addCommentsListSelectionListener(new CommentsListSelectionListener());
        view.addEditButtonActionListener(new EditButtonActionListener());
        view.addDiscardButtonActionListener(new DiscardChangesActionListener());
        view.addSaveButtonActionListener(new SaveCommentChangesActionListener());
        view.addNewCommentActionListener(new NewCommentActionListener());
        
        view.setVisible(true);
        AppObservable.getInstance().addObserver(this);
    }
    
    public void refreshListOfComments() {
        view.setListOfComments(Comment.getAllComments().toArray());
    }
    
    public void refreshView(Comment comment) {
        view.setCommentLabelID("Comment ID: " + comment.getId());
        view.setCommentDate(new SimpleDateFormat("dd MMM YYYY").format(comment.getDate()));
        view.setUser(comment.getUser().getName());
        view.setContent(comment.getContent());
        view.setCommentID(comment.getId());
        view.setPanelVisibility(true);
    }
    
    public void displaySelectedComment() {
        // Populate UI controls       
        if (view.getSelectedComment() == null) {
            view.setPanelVisibility(false);
        }
        else {
            Comment selectedComment = view.getSelectedComment();
            refreshView(selectedComment);
        }
    }
    
    @Override
    public void update(Observable o, Object o1) {
        Comment selectedComment = view.getSelectedComment();
        refreshListOfComments();
        
        // Edited comment changes should be displayed - will be null if a new comment has been added
        if (selectedComment != null) 
            view.setSelectedComment(selectedComment.getId() - 1);
        displaySelectedComment();
    }
    
    class CommentsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            displaySelectedComment();
            view.setControlsEnabled(false);
            view.setEditMode(false); 
        }
    }
    
    class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            view.setControlsEnabled(true);
            view.setEditMode(true);
            
            // Labels updated to show that user will be updated to the current user and date to the current date
            view.setUsername(currentUser.getName());
            view.setCommentDate(new SimpleDateFormat("dd MMM YYYY").format(new Date()));
        }
    }
    
    class DiscardChangesActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            displaySelectedComment(); 
            view.setControlsEnabled(false);
            view.setEditMode(false);
        }
    }
    
    class SaveCommentChangesActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (view.getCommentID() < 1)
            {
                Comment newComment = new Comment((Comment.getAllComments().size() + 1), new Date(), currentUser, view.getContent());
                report.addComment(newComment);
            }
            else
            {
                Comment updatedComment = Comment.getCommentByID(view.getCommentID());
                updatedComment.setUser(currentUser);
                updatedComment.setDate(new Date());
                updatedComment.setContent(view.getContent());
            }
            report.save();
        }
    }
    
    class NewCommentActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            Comment newComment = new Comment(0, new Date(), currentUser, "");
            refreshView(newComment);
            view.clearCommentSelection();
            view.setEditMode(true);
            view.setControlsEnabled(true);
        }
    }
}