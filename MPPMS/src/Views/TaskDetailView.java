package Views;

import Models.Report;
import Models.SetOfUsers;
import javax.swing.DefaultComboBoxModel;

public class TaskDetailView extends javax.swing.JPanel {

    public TaskDetailView() {
        initComponents();
        
        this.saveButton.setVisible(false);
        this.discardButton.setVisible(false);
    }
    
    public void setIdLabelText(String text) {
        this.lblTaskID.setText(text);
    }
    
    public void setTitleText(String text) {
        this.textTaskTitle.setText(text);
    }
    
    public void setStatus(DefaultComboBoxModel model, int index) {
        this.cmboStatus.setModel(model);
        this.cmboStatus.setSelectedIndex(index);
    }
    
    public void setPriority(DefaultComboBoxModel model, int index) {
        this.cmboPriority.setModel(model);
        this.cmboPriority.setSelectedIndex(index);
    }
    
    public void setReport(Report report) {
        this.textReport.setText(report.toString());
    }
    
    public void setAssignedTo(SetOfUsers users) {
        this.listAssignedTo.setListData(users);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editReportButton = new javax.swing.JButton();
        lblReport = new javax.swing.JLabel();
        cmboStatus = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        listAssignedTo = new javax.swing.JList();
        lblTaskID = new javax.swing.JLabel();
        lblTaskDetails = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        cmboPriority = new javax.swing.JComboBox();
        lblPriority = new javax.swing.JLabel();
        textTaskTitle = new javax.swing.JTextField();
        lblTaskTitle = new javax.swing.JLabel();
        btnEditAssignedTo = new javax.swing.JButton();
        lblAssignedTo = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        discardButton = new javax.swing.JButton();
        textReport = new javax.swing.JTextField();

        editReportButton.setText("Edit report");

        lblReport.setText("Report:");

        cmboStatus.setFocusable(false);

        listAssignedTo.setFocusable(false);
        jScrollPane1.setViewportView(listAssignedTo);

        lblTaskID.setText("ID:");

        lblTaskDetails.setText("Task Details");

        lblStatus.setText("Status:");

        cmboPriority.setFocusable(false);

        lblPriority.setText("Priority:");

        textTaskTitle.setFocusable(false);

        lblTaskTitle.setText("Title:");

        btnEditAssignedTo.setText("Add / Remove");

        lblAssignedTo.setText("Assigned to:");

        editButton.setText("Edit");

        saveButton.setText("Save");

        discardButton.setText("Discard changes");

        textReport.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTaskDetails)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTaskID, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(discardButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editButton))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAssignedTo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(lblTaskTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblStatus)
                                    .addGap(26, 26, 26)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPriority)
                                    .addComponent(lblReport))
                                .addGap(23, 23, 23)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textReport)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editReportButton))
                            .addComponent(cmboPriority, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmboStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textTaskTitle, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditAssignedTo)
                        .addGap(1, 1, 1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTaskDetails)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editButton)
                        .addComponent(saveButton)
                        .addComponent(discardButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTaskID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTaskTitle)
                    .addComponent(textTaskTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPriority)
                    .addComponent(cmboPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReport)
                    .addComponent(editReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAssignedTo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEditAssignedTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditAssignedTo;
    private javax.swing.JComboBox cmboPriority;
    private javax.swing.JComboBox cmboStatus;
    private javax.swing.JButton discardButton;
    private javax.swing.JButton editButton;
    private javax.swing.JButton editReportButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAssignedTo;
    private javax.swing.JLabel lblPriority;
    private javax.swing.JLabel lblReport;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTaskDetails;
    private javax.swing.JLabel lblTaskID;
    private javax.swing.JLabel lblTaskTitle;
    private javax.swing.JList listAssignedTo;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField textReport;
    private javax.swing.JTextField textTaskTitle;
    // End of variables declaration//GEN-END:variables
}
