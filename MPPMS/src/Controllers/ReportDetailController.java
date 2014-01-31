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

/**
 * Controller for ReportDetailView. Observes AppObservable.
 * 
 * @see AppObservable
 * @see ReportDetailView
 */
public class ReportDetailController implements Observer { 
    private final ReportDetailView view = new ReportDetailView();
    
    private User currentUser;    
    private boolean isNewComment;    
    private Report report;
    private Comment selectedComment;
    
    /**
     * ReportDetailView constructor.
     * 
     * @param report The Report to display details of in the view.
     * @param locationParent The parent JFrame used for positioning purposes
     * @param currentUser 
     */
    public ReportDetailController(Report report, JPanel locationParent, User currentUser) {
        this.report = report;
        this.view.setLocationRelativeTo(locationParent);
        this.currentUser = currentUser;
        this.isNewComment = false;
        this.selectedComment = new Comment();
    }
    
    /**
     * Initialises the view's data, adds event listeners and makes the view visible.
     */
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
        
        /**
         * Craig - TC B2c: Real time updates
         * Register this controller as an observer
         */
        AppObservable.getInstance().addObserver(this);
    }
    
    /**
     * Refreshes the data displayed in the view.
     */
    public void refreshView() {
        Comment comment;
        
        /**
         * Displayed data is based on either a new, default comment or one that is 
         * selected in the list of comments.
         */
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
    /**
     * Validates the user inputs when saving a new or edited Report.
     * 
     * @return true of the validation passes, false if it fails.
     */
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
        this.currentUser = User.getUserByUsername(this.currentUser.getUsername());
        int index = this.view.getSelectedIndex();
        this.report = Report.getReportByID(this.report.getId());
        this.view.setComments(this.report.getComments().toArray());
        this.view.setSelectedIndex(index);
    }
    
    /**
     * Event listener for the list of Comments. Sets the selected comment to that
     * which is selected in the view and refreshes the UI with its data.
     */
    class CommentsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            selectedComment = view.getSelectedComment();
            refreshView();
        }
    }
    
    /**
     * Event listener for the 'Edit' button. Enables editing of the UI controls.
     */
    class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            view.setEditMode(true);
            refreshView();
        }
    }
    
    /**
     * Event listener for the 'Discard changes' button. Disables editing of the UI
     * controls and reverts the user inputs.
     */
    class DiscardChangesActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            view.setEditMode(false);
            isNewComment = false;
            refreshView();
        }
    }
    
    /**
     * Event listener for the 'Save' button. 
     */
    class SaveCommentChangesActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (validateUserInputs()) {
                view.setCursor(new Cursor(Cursor.WAIT_CURSOR));

                if (isNewComment)
                {
                    /**
                     * Create a new comment and add it to the report, then save the
                     * report which will also save the comments is contains.
                     */                    
                    Report tempReport = report;
                    Comment tempComment = selectedComment;

                    selectedComment.setContent(view.getContent());
                    selectedComment.setDate(new Date());
                    selectedComment.setUser(currentUser);

                    report.addComment(selectedComment);
                    isNewComment = false;

                    if (report.save()) {
                        // Success
                        view.setEditMode(false);
                    }
                    else {
                        // Failure
                        isNewComment = true;
                        report = tempReport;
                        selectedComment = tempComment;
                        JOptionPane.showMessageDialog(view, "Error saving Report", "'Report' Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    /**
                     * Find and edit the existing comment (which is already part of
                     * this report), update it then save the report which will also
                     * save the comments it contains.
                     */
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
                        // Success
                        view.setEditMode(false);
                    }
                    else {
                        // Failure
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
    
    /**
     * Event listener for the 'New Comment' button. Enables editing of the UI controls
     * and refreshes the view's data to show default / blank values.
     */
    class NewCommentActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            view.setEditMode(true);
            isNewComment = true;
            refreshView();
        }
    }
}