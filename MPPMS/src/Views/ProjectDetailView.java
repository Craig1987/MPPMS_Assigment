package Views;

import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionListener;

public class ProjectDetailView extends javax.swing.JPanel {
    
    public ProjectDetailView() {
        initComponents();
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 1, 1);
        this.pickCreationDate.setMinSelectableDate(calendar.getTime());
        this.pickDeadlineDate.setMinSelectableDate(calendar.getTime());
    }
    
    public void setEditMode(boolean editMode, boolean canEditProject) {
        saveButton.setVisible(editMode);
        discardButton.setVisible(editMode);
        editButton.setVisible(!editMode && canEditProject);
        textProjectTitle.setEnabled(editMode);
        managerCombo.setEnabled(editMode);
        coordinatorCombo.setEnabled(editMode);
        pickCreationDate.setEnabled(editMode);
        pickDeadlineDate.setEnabled(editMode);
        cmboPriority.setEnabled(editMode);
        teamChoiceButton.setEnabled(editMode);
        tasksChoiceButton.setEnabled(editMode);
        componentsChoiceButton.setEnabled(editMode);
    }
    
    public void setCanViewOverview(boolean b) {
        this.overviewButton.setVisible(b);
    }
    
    public void setCanEditTask(boolean canEdit) {
        this.tasksEditButton.setEnabled(canEdit);
    }
    
    public void setCanEditComponent(boolean canEdit) {
        this.componentsEditButton.setEnabled(canEdit);
    }
    
    public void setIdLabelText(String text) {
        this.lblID.setText(text);
    }
    
    public void setProjectTitleText(String text) {
        this.textProjectTitle.setText(text);
    }
    
    public String getProjectTitle() {
        return this.textProjectTitle.getText();
    }
    
    public void setManager(Object[] items, Object selectedItem) {
        this.managerCombo.setModel(new DefaultComboBoxModel<>(items));
        this.managerCombo.setSelectedItem(selectedItem);
    }
    
    public Object getManager() {
        return this.managerCombo.getSelectedItem();
    }
    
    public void setCoordinator(Object[] items, Object selectedItem) {
        this.coordinatorCombo.setModel(new DefaultComboBoxModel<>(items));
        this.coordinatorCombo.setSelectedItem(selectedItem);
    }
    
    public Object getCoordinator() {
        return this.coordinatorCombo.getSelectedItem();
    }
    
    public void setCreationDateText(Date creationDate) {
        this.pickCreationDate.setDate(creationDate);
    }
    
    public Date getCreationDate() {
        return this.pickCreationDate.getDate();
    }
    
    public void setDeadlineText(Date deadlineDate) {
        this.pickDeadlineDate.setDate(deadlineDate);
    }
    
    public Date getDeadlineDate() {
        return this.pickDeadlineDate.getDate();
    }
    
    public void setPriority(Object[] values, int index) {
        this.cmboPriority.setModel(new DefaultComboBoxModel<>(values));
        this.cmboPriority.setSelectedIndex(index);
    }
    
    public Object getPriority() {
        return this.cmboPriority.getSelectedItem();
    }
    
    public void setTeam(Object[] values) {
        this.listTeam.setModel(new DefaultComboBoxModel<>(values));
    }
    
    public Object[] getTeam() {
        return getItemsFromList(listTeam);
    }
    
    public void setTasks(Object[] values) {
        this.listTasks.setModel(new DefaultComboBoxModel<>(values));
    }
    
    public Object[] getTasks() {
        return getItemsFromList(listTasks);
    }
    
    public Object getSelectedTask() {
        return this.listTasks.getSelectedValue();
    }
    
    public void setProjectComponents(Object[] values) {
        this.listComponents.setModel(new DefaultComboBoxModel<>(values));
    }
    
    public Object[] getProjectComponents() {
        return getItemsFromList(listComponents);
    }
    
    public Object getSelectedComponent() {
        return this.listComponents.getSelectedValue();
    }
    
    public void addEditButtonActionListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }
    
    public void addSaveButtonActionListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    
    public void addDiscardButtonActionListener(ActionListener listener) {
        discardButton.addActionListener(listener);
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
    
    public void addTaskEditButtonActionListener(ActionListener listener) {
        this.tasksEditButton.addActionListener(listener);
    }
    
    public void addComponentEditButtonActionListener(ActionListener listener) {
        this.componentsEditButton.addActionListener(listener);
    }
    
    public void addTasksListSelectionListener(ListSelectionListener listener) {
        this.listTasks.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void addComponentsListSelectionListener(ListSelectionListener listener) {
        this.listComponents.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void addOverviewButtonActionListener(ActionListener listener) {
        this.overviewButton.addActionListener(listener);
    }
    
    private Object[] getItemsFromList(JList list) {
        ListModel model = list.getModel();
        Object[] items = new Object[model.getSize()];

        for(int i = 0; i < model.getSize(); i++) {
             items[i] =  model.getElementAt(i);
        }
        
        return items;
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
        tasksEditButton = new javax.swing.JButton();
        componentsChoiceButton = new javax.swing.JButton();
        managerCombo = new javax.swing.JComboBox();
        coordinatorCombo = new javax.swing.JComboBox();
        componentsEditButton = new javax.swing.JButton();
        pickCreationDate = new com.toedter.calendar.JDateChooser();
        pickDeadlineDate = new com.toedter.calendar.JDateChooser();
        overviewButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        discardButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();

        lblProjectDetails.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblProjectDetails.setText("Project Details");

        jLabel1.setText("Manager:");

        lblCreationDate.setText("Creation Date:");

        lblDeadlineDate.setText("Deadline Date:");

        jLabel4.setText("Coordinator:");

        lblPriority.setText("Priority:");

        lblProjectTitle.setText("Title:");

        lblTeam.setText("Team:");

        listTeam.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(listTeam);

        lblTasks.setText("Tasks:");

        listTasks.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(listTasks);

        lblID.setText("ID:");

        lblComponents.setText("Components:");

        listComponents.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listComponents.setAutoscrolls(false);
        jScrollPane3.setViewportView(listComponents);

        tasksChoiceButton.setText("Add / Remove");

        teamChoiceButton.setText("Add / Remove");

        tasksEditButton.setText("Edit");

        componentsChoiceButton.setText("Add / Remove");

        componentsEditButton.setText("Edit");

        pickCreationDate.setDateFormatString("dd MMM yyyy");

        pickDeadlineDate.setDateFormatString("dd MMM yyyy");

        overviewButton.setText("Project overview");

        discardButton.setText("Discard changes");

        saveButton.setText("Save");

        editButton.setText("Edit");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(discardButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editButton))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(discardButton)
                .addComponent(saveButton)
                .addComponent(editButton))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(teamChoiceButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tasksEditButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tasksChoiceButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblID))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblCreationDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblProjectTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(lblPriority, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTeam)
                            .addComponent(lblTasks)
                            .addComponent(lblDeadlineDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(coordinatorCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmboPriority, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textProjectTitle)
                            .addComponent(managerCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pickCreationDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pickDeadlineDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(componentsChoiceButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(componentsEditButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblComponents)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProjectDetails)
                        .addGap(18, 18, 18)
                        .addComponent(overviewButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblProjectDetails)
                        .addComponent(overviewButton))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textProjectTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblProjectTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(managerCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(coordinatorCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCreationDate)
                    .addComponent(pickCreationDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDeadlineDate)
                        .addGap(16, 16, 16)
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
                                .addComponent(tasksEditButton))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblComponents)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(componentsChoiceButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(componentsEditButton))))
                    .addComponent(pickDeadlineDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmboPriority;
    private javax.swing.JButton componentsChoiceButton;
    private javax.swing.JButton componentsEditButton;
    private javax.swing.JComboBox coordinatorCombo;
    private javax.swing.JButton discardButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JComboBox managerCombo;
    private javax.swing.JButton overviewButton;
    private com.toedter.calendar.JDateChooser pickCreationDate;
    private com.toedter.calendar.JDateChooser pickDeadlineDate;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton tasksChoiceButton;
    private javax.swing.JButton tasksEditButton;
    private javax.swing.JButton teamChoiceButton;
    private javax.swing.JTextField textProjectTitle;
    // End of variables declaration//GEN-END:variables
}
