package Views;

import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;

public class ProjectDetailView extends javax.swing.JPanel {
    public ProjectDetailView() {
        initComponents();
        
        this.saveButton.setVisible(false);
        this.discardButton.setVisible(false);
    }
    
    public void setIdLabelText(String text) {
        this.lblID.setText(text);
    }
    
    public void setProjectTitleText(String text) {
        this.textProjectTitle.setText(text);
    }
    
    public void setManagerText(String text) {
        this.textManager.setText(text);
    }
    
    public void setCoordinatorText(String text) {
        this.textCoordinator.setText(text);
    }
    
    public void setCreationDateText(String text) {
        this.textCreationDate.setText(text);
    }
    
    public void setDeadlineText(String text) {
        this.textDeadlineDate.setText(text);
    }
    
    public void setPriority(DefaultComboBoxModel model, int index) {
        this.cmboPriority.setModel(model);
        this.cmboPriority.setSelectedIndex(index);
    }
    
    public void setTeam(DefaultComboBoxModel model) {
        this.listTeam.setModel(model);
    }
    
    public void setTasks(DefaultComboBoxModel model) {
        this.listTasks.setModel(model);
    }
    
    public void setComponents(DefaultComboBoxModel model) {
        this.listComponents.setModel(model);
    }
    
    public void addTeamChoiceActionListener(ActionListener listener) {
        this.teamChoiceButton.addActionListener(listener);
    }
    
    public void addTasksChoiceActionListener(ActionListener listener) {
        this.tasksChoiceButton.addActionListener(listener);
    }
    
    public void addComponentsChoiceActionListener(ActionListener listener) {
        this.componentsChoiceButton.addActionListener(listener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblProjectDetails = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblCreationDate = new javax.swing.JLabel();
        lblDeadlineDate = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblPriority = new javax.swing.JLabel();
        textManager = new javax.swing.JTextField();
        textCoordinator = new javax.swing.JTextField();
        textCreationDate = new javax.swing.JTextField();
        textDeadlineDate = new javax.swing.JTextField();
        cmboPriority = new javax.swing.JComboBox();
        lblProjectTitle = new javax.swing.JLabel();
        textProjectTitle = new javax.swing.JTextField();
        lblTeam = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listTeam = new javax.swing.JList();
        lblTasks = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listTasks = new javax.swing.JList();
        lblID = new javax.swing.JLabel();
        lblComponents = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listComponents = new javax.swing.JList();
        tasksChoiceButton = new javax.swing.JButton();
        teamChoiceButton = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        componentsChoiceButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        discardButton = new javax.swing.JButton();

        lblProjectDetails.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblProjectDetails.setText("Project Details");

        jLabel1.setText("Manager:");

        lblCreationDate.setText("Creation Date:");

        lblDeadlineDate.setText("Deadline Date:");

        jLabel4.setText("Coordinator:");

        lblPriority.setText("Priority:");

        lblProjectTitle.setText("Title:");

        lblTeam.setText("Team:");

        jScrollPane1.setViewportView(listTeam);

        lblTasks.setText("Tasks:");

        jScrollPane2.setViewportView(listTasks);

        lblID.setText("ID:");

        lblComponents.setText("Components:");

        jScrollPane3.setViewportView(listComponents);

        tasksChoiceButton.setText("Add / Remove");

        teamChoiceButton.setText("Add / Remove");

        jButton3.setText("Edit");

        componentsChoiceButton.setText("Add / Remove");

        editButton.setText("Edit");

        saveButton.setText("Save");

        discardButton.setText("Discard changes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblProjectTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textCoordinator)
                            .addComponent(textProjectTitle)
                            .addComponent(textManager, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPriority)
                            .addComponent(lblTeam)
                            .addComponent(lblCreationDate, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(lblDeadlineDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textDeadlineDate)
                            .addComponent(textCreationDate, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmboPriority, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProjectDetails)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(discardButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblID))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTasks)
                            .addComponent(lblComponents))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(componentsChoiceButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(teamChoiceButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tasksChoiceButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProjectDetails)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editButton)
                        .addComponent(saveButton)
                        .addComponent(discardButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textProjectTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProjectTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textManager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(textCoordinator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textCreationDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCreationDate))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDeadlineDate)
                    .addComponent(textDeadlineDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPriority)
                    .addComponent(cmboPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTeam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(teamChoiceButton, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTasks)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tasksChoiceButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblComponents)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(componentsChoiceButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmboPriority;
    private javax.swing.JButton componentsChoiceButton;
    private javax.swing.JButton discardButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblComponents;
    private javax.swing.JLabel lblCreationDate;
    private javax.swing.JLabel lblDeadlineDate;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblPriority;
    private javax.swing.JLabel lblProjectDetails;
    private javax.swing.JLabel lblProjectTitle;
    private javax.swing.JLabel lblTasks;
    private javax.swing.JLabel lblTeam;
    private javax.swing.JList listComponents;
    private javax.swing.JList listTasks;
    private javax.swing.JList listTeam;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton tasksChoiceButton;
    private javax.swing.JButton teamChoiceButton;
    private javax.swing.JTextField textCoordinator;
    private javax.swing.JTextField textCreationDate;
    private javax.swing.JTextField textDeadlineDate;
    private javax.swing.JTextField textManager;
    private javax.swing.JTextField textProjectTitle;
    // End of variables declaration//GEN-END:variables
}
