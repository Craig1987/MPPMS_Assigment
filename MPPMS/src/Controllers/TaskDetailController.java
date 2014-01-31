package Controllers;

import Application.AppObservable;
import Models.Asset;
import Models.SetOfAssets;
import Models.SetOfUsers;
import Models.Task;
import Models.User;
import Models.User.Role;
import Views.TaskDetailView;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Controller for TaskDetailView. Observes AppObservable.
 * 
 * @see AppObservable
 * @see TaskDetailView
 */
public class TaskDetailController implements Observer {
    private final TaskDetailView view;
    private final boolean canEdit;
    private final User currentUser;
    private final JFrame parentFrame;
    
    private Task task;
    private boolean isNew;
    
    private ModelChoiceController modelChoiceController;
    
    /**
     * TaskDetailController constructor.
     * 
     * @param view This controller's view
     * @param task The Task to display data about
     * @param currentUser The logged in user
     * @param parentFrame The parent JFrame for modal ModerationTaskGeneratorView.
     * @see ModerationTaskGeneratorView
     */
    public TaskDetailController(TaskDetailView view, Task task, User currentUser, JFrame parentFrame) {
        this.view = view;
        this.task = task;
        this.isNew = task.getId() == 0;
        this.canEdit = (currentUser.getRole() == Role.ProjectManager || 
                        currentUser.getRole() == Role.ProjectCoordinator || 
                        currentUser.getRole() == Role.QCTeamLeader);
        this.currentUser = currentUser;
        this.parentFrame = parentFrame;
    }
    
    /**
     * Initialises the view and adds event listeners.
     */
    public void initialise() {
        refreshView();
        
        this.view.setEditMode(this.isNew, this.canEdit);
        this.view.setCanViewAsset(false);
        
        this.view.addAssignedToChoiceActionListener(new AssignedToChoiceActionListener());
        this.view.addAssetChoiceActionListener(new AssetChoiceActionListener());
        this.view.addSaveButtonActionListener(new SaveButtonActionListener());
        this.view.addEditButtonActionListener(new EditButtonActionListener());
        this.view.addAssetsListSelectionListener(new AssetsListSelectionListener());
        if (!this.isNew) {
            this.view.addDiscardButtonActionListener(new DiscardButtonActionListener());
            this.view.addEditReportActionListener(new EditReportActionListener());
        }
        
        /**
         * Craig - TC B2c: Real time updates
         * Register this controller as an observer
         */
        AppObservable.getInstance().addObserver(this);
    }
    
    /**
     * Refreshes the view to show the Tasks's data.
     */
    private void refreshView() {
        view.setIdLabelText("ID: " + task.getId());
        view.setTitleText(task.getTitle());
        view.setTaskType(Task.TaskType.values(), task.getTaskType());
        view.setStatus(Task.Status.values(), task.getStatus());
        view.setPriority(Task.Priority.values(), task.getPriority());
        view.setReportText(task.getReport().toString());
        view.setAssignedTo(task.getAssignedTo().toArray());
        view.setAssets(task.getAssets().toArray());
    }
    
    /**
     * Validates user inputs before saving a new or edited Task.
     * @return 
     */
    private boolean validateUserInputs() {
        ArrayList<String> errors = new ArrayList();
        
        if (this.view.getTaskTitle().equals("")) {
            errors.add("\t - Enter a title");
        }
        
        if ((Task.Status)this.view.getStatus() != Task.Status.Created && this.view.getAssignedTo().length == 0) {
            errors.add("\t - Assign at least 1 User or set Status to '" + Task.Status.Created.toString() + "'");
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
        if (!this.isNew) {
            this.task = Task.getTaskByID(this.task.getId());
            refreshView();
        }
    }
    
    /**
     * Gets the selected Asset in the view's list of Assets
     * 
     * @return The selected Asset
     */
    public Asset getSelectedAsset() {
        return (Asset)this.view.getSelectedAsset();
    }
    
    /**
     * Event listener for the 'Save' button. Validates user input before saving the
     * updated Task.
     */
    class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (validateUserInputs()) {
                view.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                
                /*
                Craig - TC B3b: Auto task creation
                Determine if the requirements are met to be able to automatically
                generate a QA_Moderation task for this task (status changed to Completed
                and this is a QC Task)
                */
                boolean canOfferAutoTaskGeneration = (task.getTaskType() == Task.TaskType.QC &&
                                                    task.getStatus() != (Task.Status)view.getStatus() && 
                                                    ((Task.Status)view.getStatus()) == Task.Status.Completed); 
                
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

                Task temp = task;

                task.setTaskType(Task.TaskType.valueOf(view.getTaskType().toString()));
                task.setTitle(view.getTaskTitle());            
                task.setPriority(Task.Priority.valueOf(view.getPriority().toString()));
                task.setStatus(Task.Status.valueOf(view.getStatus().toString()));
                task.setAssignedTo(assignedTo);
                task.setAssets(assets);

                if (task.save()) {
                    // Success
                    /*
                    Craig - TC B3b: Auto task creation
                    Ask the user if they want the auto generation to take place
                    and if so launch a Frame to prompt for which QC Team Leader
                    should be assigned this task.
                    */
                    if (canOfferAutoTaskGeneration) {
                        int result = JOptionPane.showConfirmDialog(view, 
                                                "Generate a QA_Moderation Task now that this QC Task is completed?", 
                                                "Automatic QA_Moderation Task Generation", 
                                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (result == JOptionPane.YES_OPTION) {
                            ModerationTaskGeneratorController controller = new ModerationTaskGeneratorController(parentFrame, task.getId(), task.getAssets());
                            controller.launch();
                        }
                    }
                    
                    if (modelChoiceController != null) {
                        modelChoiceController.closeView();
                    }
                    view.setEditMode(false, canEdit);
                    isNew = false;
                }
                else {
                    // Failure
                    task = temp;
                    JOptionPane.showMessageDialog(view, "Error saving Task", "'Task' Error", JOptionPane.ERROR_MESSAGE);
                }
                
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }        
    }
    
    /**
     * Event listener for the 'Discard changes' button. Disables editing of UI controls 
     * and reverts user inputs.
     */
    class DiscardButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (modelChoiceController != null) {
                modelChoiceController.closeView();
            }
            view.setEditMode(false, canEdit);
            refreshView();
        }        
    }
    
    /**
     * Event listener for the 'Edit' button. Enables editing of the view's controls.
     */
    class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {   
            view.setEditMode(true, canEdit);
        }        
    }
    
    /**
     * Event listener for the 'Add / Remove' button alongside the AssignedTo list.
     * Launches a ModelChoice view allowing the user to add or remove users who are
     * assigned this Task.
     */
    class AssignedToChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController((Collection)User.getAllUsers().clone(), task.getAssignedTo(), view);
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceAssignedToSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    /**
     * Event listener for the 'Add / Remove' button alongside the Assets list.
     * Launches a ModelChoice view allowing the user to add or remove the Task's Assets.
     */
    class AssetChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController((Collection)Asset.getAllAssets().clone(), task.getAssets(), view);
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceAssetsSaveActionListener());
            modelChoiceController.launch();
        }
    }
    
    /**
     * Event listener for the 'Save' button in the ModelChoiceView. Updates the view
     * with the chosen Users that were selected.
     * 
     * @see ModelChoiceView
     */
    class ModelChoiceAssignedToSaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SetOfUsers users = new SetOfUsers();
            users.addAll((Collection)modelChoiceController.getChosenModels());                    
            modelChoiceController.closeView();
            view.setAssignedTo(users.toArray());
        }        
    }
    
    /**
     * Event listener for the 'Save' button in the ModelChoiceView. Updates the view
     * with the chosen Assets that were selected.
     * 
     * @see ModelChoiceView
     */
    class ModelChoiceAssetsSaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SetOfAssets assets = new SetOfAssets();
            assets.addAll((Collection)modelChoiceController.getChosenModels());                    
            modelChoiceController.closeView();
            view.setAssets(assets.toArray());
        }        
    }
    
    /**
     * Event listener for the 'Edit report' button. Launches a ReportDetailView to 
     * display this Tasks's Report's data.
     */
    class EditReportActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            ReportDetailController reportDetailController = new ReportDetailController(task.getReport(), view, currentUser);
            reportDetailController.launch();
        }
    }
    
    /**
     * Event listener for the list of Assets. Enables or disables the 'View' button 
     * alongside the list of Assets.
     */
    class AssetsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setCanViewAsset(!((DefaultListSelectionModel)lse.getSource()).isSelectionEmpty());
        }
    }
}
