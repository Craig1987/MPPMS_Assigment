package Controllers;

import Models.Report;
import Models.SetOfAssets;
import Models.SetOfUsers;
import Models.Task;
import Models.User;
import Models.User;
import Views.ModerationTaskGeneratorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Craig
 */
public class ModerationTaskGeneratorController {
    private final int qcTaskId;
    private final SetOfAssets assets;
    private final ModerationTaskGeneratorView view;
    
    public ModerationTaskGeneratorController(JFrame parentFrame, int qcTaskId, SetOfAssets assets) {
        this.qcTaskId = qcTaskId;
        this.assets = assets;
        this.view = new ModerationTaskGeneratorView(parentFrame, true);
    }
    
    public void launch() {
        this.view.setTeamLeaders(User.getUsersByRole(User.Role.QCTeamLeader).toArray());
        this.view.addGenerateButtonActionListener(new GenerateButtonActionListener());        
        this.view.setVisible(true);
    }
    
    class GenerateButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {            
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
