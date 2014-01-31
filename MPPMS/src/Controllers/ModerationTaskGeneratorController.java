package Controllers;

import Models.Report;
import Models.SetOfAssets;
import Models.SetOfUsers;
import Models.Task;
import Models.User;
import Views.ModerationTaskGeneratorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Controller for the view which is used when the user is required to select
 * a QC Team Leader whom will be assigned the auto-generated QA_Moderation task.
 *
 * @author Craig - TC B3b: Auto task creation
 * @see ModerationTaskGeneratorView
 */
public class ModerationTaskGeneratorController {
    private final int qcTaskId;
    private final SetOfAssets assets;
    private final ModerationTaskGeneratorView view;
    
    /**
     * ModerationTaskGeneratorController constructor
     *
     * @author Craig - TC B3b: Auto task creation
     * @param parentFrame The parent JFrame to which this controller's view will
     * be modal to.
     * @param qcTaskId The ID of the QC Task which prompted the generation of a 
     * QA_Moderation Task.
     * @param assets The Assets from the QC Task which will be set as the Assets
     * of the new QA_Moderation task.
     * @see ModerationTaskGeneratorView
     */
    public ModerationTaskGeneratorController(JFrame parentFrame, int qcTaskId, SetOfAssets assets) {
        this.qcTaskId = qcTaskId;
        this.assets = assets;
        this.view = new ModerationTaskGeneratorView(parentFrame, true);
    }
    
    /**
     * Initialises the view, adds relevant ActionListeners to its UI controls and
     * makes the view visible.
     *
     * @author Craig - TC B3b: Auto task creation
     * @see ModerationTaskGeneratorView
     */
    public void launch() {
        this.view.setTeamLeaders(User.getUsersByRole(User.Role.QCTeamLeader).toArray());
        this.view.addGenerateButtonActionListener(new GenerateButtonActionListener());        
        this.view.setVisible(true);
    }
    
    /**
     * ActionListener for the 'Generate' button. The actionPerformed method is 
     * called if this button is clicked.
     * 
     * @author Craig - TC B3b: Auto task creation
     */
    class GenerateButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            /**
             * Create a new QA_Moderation Task based on the completed QC Task
             * which prompted the auto generation.
             */
            SetOfUsers assignedTo = new SetOfUsers();
            assignedTo.add((User)view.getSelectedTeamleader());
            
            Task task = new Task(0, Task.TaskType.QA_Moderation);
            task.setAssets(assets);
            task.setAssignedTo(assignedTo);
            task.setPriority(Task.Priority.Normal);
            task.setReport(new Report());
            task.setStatus(Task.Status.Created);
            task.setTitle("Moderation Task following QC Task ID " + qcTaskId);
            if (!task.save()) {
                JOptionPane.showMessageDialog(view, "Error saving Moderation Task", "'Moderation Task' Error", JOptionPane.ERROR_MESSAGE);
            }
            view.dispose();
        }
    }
}
