package Views;

import Models.Task;
import Models.Component;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Kirsty
 */
public class ProjectsHierarchyView extends javax.swing.JFrame {

    public ProjectsHierarchyView() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/resources/icon.png")).getImage());
        this.setLocationRelativeTo(null);
        
        projectsTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }
    
    public Task getSelectedAddTask() {
        return (Task)comboAddToTasks.getSelectedItem();
    }

    public Task getSelectedRemoveTask() {
        return (Task)comboRemoveFromTasks.getSelectedItem();
    }

    public Component getSelectedRemoveComponent() {
        return (Component)comboRemoveFromComponents.getSelectedItem();
    }
   
    public Component getSelectedAddComponent() {
        return (Component)comboAddToComponents.getSelectedItem();
    }
    
    public void setTreeModel(DefaultTreeModel model) {
       projectsTree.setModel(model);
        for (int i = 0; i < projectsTree.getRowCount(); i++) {
            projectsTree.expandRow(i);
        }
    }
    
    public DefaultTreeModel getTreeModel() {
        return (DefaultTreeModel) projectsTree.getModel();
    }
    
    public void addProjectsTreeSelectionListener(TreeSelectionListener listener){
        projectsTree.addTreeSelectionListener(listener);
    }
    
    public DefaultMutableTreeNode  getSelectedTreeNode() {
        return (DefaultMutableTreeNode) projectsTree.getLastSelectedPathComponent();
    }
    
    public void setControlsEnabled(boolean enabled) {
        java.awt.Component[] coms = assetPanel.getComponents();
        for (java.awt.Component com : coms) {
            com.setEnabled(enabled);
        }
    }
    
    public void setControlsVisible (boolean visible) {
        java.awt.Component[] coms = assetPanel.getComponents();
        for (java.awt.Component com : coms) {
            com.setVisible(visible);
        }
    }
    
    public void setAssetDetails(String detail) {
        lblAssetDetails.setText(detail);
    }
    
    public void setProjectDetails(String detail) {
        lblProject.setText(detail);
    }
    
    public void setRemoveTasksComboBox(Object[] items) {
        comboRemoveFromTasks.setModel(new DefaultComboBoxModel<>(items));
    }
    
    public void setAddtoTasksComboBox(Object[] items) {
        comboAddToTasks.setModel(new DefaultComboBoxModel<>(items));
    }
    
    public void setRemoveComponentsComboBox(Object[] items) {
        comboRemoveFromComponents.setModel(new DefaultComboBoxModel<>(items));
    }
    
    public void setAddtoComponentsComboBox(Object[] items) {
        comboAddToComponents.setModel(new DefaultComboBoxModel<>(items));
    }
    
    public void addRemoveFromTaskButtonActionListener(ActionListener listener) {
        btnRemoveFromTask.addActionListener(listener);
    }
    
    public void addAddToTaskButtonActionListener(ActionListener listener) {
        btnAddToTask.addActionListener(listener);
    }
    
    public void addRemoveFromComponentButtonActionListener(ActionListener listener) {
        btnRemoveFromComponent.addActionListener(listener);
    }
    
    public void addAddToComponentButtonActionListener(ActionListener listener) {
        btnAddToComponent.addActionListener(listener);
    }
    
    public void removeTasksEnabled(boolean enabled) {
        comboRemoveFromTasks.setEnabled(enabled);
        btnRemoveFromTask.setEnabled(enabled);
    }
    
    public void addTasksEnabled(boolean enabled) {
        comboAddToTasks.setEnabled(enabled);
        btnAddToTask.setEnabled(enabled);
    }
    
    public void removeComponentsEnabled(boolean enabled) {
        comboRemoveFromComponents.setEnabled(enabled);
        btnRemoveFromComponent.setEnabled(enabled);
    }
    
    public void addComponentsEnabled(boolean enabled) {
        comboAddToComponents.setEnabled(enabled);
        btnAddToComponent.setEnabled(enabled);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblContent = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        projectsTree = new javax.swing.JTree();
        assetPanel = new javax.swing.JPanel();
        btnAddToTask = new javax.swing.JButton();
        comboAddToComponents = new javax.swing.JComboBox();
        lblAssetDetails = new javax.swing.JLabel();
        lblComponents = new javax.swing.JLabel();
        btnRemoveFromTask = new javax.swing.JButton();
        lblTasks = new javax.swing.JLabel();
        comboAddToTasks = new javax.swing.JComboBox();
        comboRemoveFromComponents = new javax.swing.JComboBox();
        btnAddToComponent = new javax.swing.JButton();
        comboRemoveFromTasks = new javax.swing.JComboBox();
        btnRemoveFromComponent = new javax.swing.JButton();
        lblProject = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Content Hierarchy");

        lblContent.setText("Content Hierarchy");

        jSplitPane1.setDividerLocation(350);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Projects");
        projectsTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        projectsTree.setDragEnabled(true);
        projectsTree.setEditable(true);
        jScrollPane1.setViewportView(projectsTree);

        jSplitPane1.setLeftComponent(jScrollPane1);

        btnAddToTask.setText("Add to Chosen Task");

        comboAddToComponents.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblAssetDetails.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAssetDetails.setText("Asset Details");

        lblComponents.setText("Components:");

        btnRemoveFromTask.setText("Remove from Chosen Task");

        lblTasks.setText("Tasks:");

        comboAddToTasks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        comboRemoveFromComponents.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAddToComponent.setText("Add to Chosen Component");

        comboRemoveFromTasks.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnRemoveFromComponent.setText("Remove from Chosen Component");

        lblProject.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblProject.setText("Project Details");

        javax.swing.GroupLayout assetPanelLayout = new javax.swing.GroupLayout(assetPanel);
        assetPanel.setLayout(assetPanelLayout);
        assetPanelLayout.setHorizontalGroup(
            assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(assetPanelLayout.createSequentialGroup()
                        .addGroup(assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(assetPanelLayout.createSequentialGroup()
                                .addGroup(assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(comboRemoveFromTasks, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(comboAddToTasks, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(20, 20, 20)
                                .addGroup(assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnRemoveFromTask, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                                    .addComponent(btnAddToTask, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(assetPanelLayout.createSequentialGroup()
                                .addGroup(assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(assetPanelLayout.createSequentialGroup()
                                        .addComponent(lblComponents)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(assetPanelLayout.createSequentialGroup()
                                        .addGroup(assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(comboAddToComponents, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(comboRemoveFromComponents, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(20, 20, 20)))
                                .addGroup(assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnRemoveFromComponent, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                                    .addComponent(btnAddToComponent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(assetPanelLayout.createSequentialGroup()
                                .addComponent(lblAssetDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 185, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(assetPanelLayout.createSequentialGroup()
                        .addGroup(assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTasks)
                            .addComponent(lblProject, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        assetPanelLayout.setVerticalGroup(
            assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(assetPanelLayout.createSequentialGroup()
                .addComponent(lblProject)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAssetDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTasks)
                .addGap(11, 11, 11)
                .addGroup(assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRemoveFromTask, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboRemoveFromTasks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddToTask)
                    .addComponent(comboAddToTasks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(assetPanelLayout.createSequentialGroup()
                        .addComponent(lblComponents)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboRemoveFromComponents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(assetPanelLayout.createSequentialGroup()
                        .addComponent(btnRemoveFromComponent)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(assetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddToComponent)
                            .addComponent(comboAddToComponents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(185, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(assetPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblContent)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblContent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel assetPanel;
    private javax.swing.JButton btnAddToComponent;
    private javax.swing.JButton btnAddToTask;
    private javax.swing.JButton btnRemoveFromComponent;
    private javax.swing.JButton btnRemoveFromTask;
    private javax.swing.JComboBox comboAddToComponents;
    private javax.swing.JComboBox comboAddToTasks;
    private javax.swing.JComboBox comboRemoveFromComponents;
    private javax.swing.JComboBox comboRemoveFromTasks;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblAssetDetails;
    private javax.swing.JLabel lblComponents;
    private javax.swing.JLabel lblContent;
    private javax.swing.JLabel lblProject;
    private javax.swing.JLabel lblTasks;
    private javax.swing.JTree projectsTree;
    // End of variables declaration//GEN-END:variables

}
