package Views;

import Models.Asset;
import Models.Component;
import Models.Project;
import Models.SetOfAssets;
import Models.SetOfComponents;
import Models.SetOfProjects;
import Models.Task;
import Models.SetOfTasks;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.DefaultEventTableModel;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class IndexView extends javax.swing.JFrame {

    public IndexView() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/resources/icon.png")).getImage());
        this.setLocationRelativeTo(null);
    }
    
    public void setWelcomeMessage(String message) {
        this.welcomeLabel.setText(message);
    }
    
    public void setProjectsTableData(SetOfProjects projects) {
        String[] headers = new String[]{ "Title", "Creation date", "Manager", "Coordinator" };
        String[] properties = new String[]{ "Title", "FormattedCreationDate", "Manager", "Coordinator" };
        
        EventList projectsEventList = GlazedLists.eventList(projects);
        TableFormat projectsTableFormat = GlazedLists.tableFormat(Project.class, properties, headers);
        DefaultEventTableModel projectsTableModel = new DefaultEventTableModel(projectsEventList, projectsTableFormat);
        
        this.projectsTable.setModel(projectsTableModel);
        
        alignTableContent(projectsTable, JLabel.LEFT);
    }
    
    public void setTasksTableData(SetOfTasks tasks) {
        String[] properties = new String[]{ "Title", "Status", "Priority" };
        
        EventList tasksEventList = GlazedLists.eventList(tasks);
        TableFormat tasksTableFormat = GlazedLists.tableFormat(Task.class, properties, properties);
        DefaultEventTableModel tasksTableModel = new DefaultEventTableModel(tasksEventList, tasksTableFormat);
        
        this.tasksTable.setModel(tasksTableModel);
        
        alignTableContent(tasksTable, JLabel.LEFT);
    }
    
    public void setComponentsTableData(SetOfComponents components) {
        String[] headers = new String[]{ "ID", "Description", "Asset count" };
        String[] properties = new String[]{ "Id", "Description", "NumberOfAssets" };
        
        EventList eventList = GlazedLists.eventList(components);
        TableFormat tableFormat = GlazedLists.tableFormat(Component.class, properties, headers);
        DefaultEventTableModel tableModel = new DefaultEventTableModel(eventList, tableFormat);
        
        this.componentsTable.setModel(tableModel);
        
        alignTableContent(componentsTable, JLabel.LEFT);
    }
    
    public void setAssetsTableData(SetOfAssets assets) {
        String[] headers = new String[]{ "ID", "Length", "Asset type" };
        String[] properties = new String[]{ "Id", "Length", "AssetType" };
        
        EventList eventList = GlazedLists.eventList(assets);
        TableFormat tableFormat = GlazedLists.tableFormat(Asset.class, properties, headers);
        DefaultEventTableModel tableModel = new DefaultEventTableModel(eventList, tableFormat);
        
        this.assetsTable.setModel(tableModel);
        
        alignTableContent(assetsTable, JLabel.LEFT);
    }
    
    private void alignTableContent(JTable table, int alignment) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(alignment);
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }
    
    public Project getSelectedProject() {
        if (this.projectsTable.getSelectedRow() < 0) {
            return null;
        }
        return (Project)((DefaultEventTableModel)this.projectsTable.getModel()).getElementAt(this.projectsTable.getSelectedRow());
    }
    
    public void clearProjectSelection() {
        this.projectsTable.clearSelection();
    }
    
    public Task getSelectedTask() {
        if (this.tasksTable.getSelectedRow() < 0) {
            return null;
        }
        return (Task)((DefaultEventTableModel)this.tasksTable.getModel()).getElementAt(this.tasksTable.getSelectedRow());
    }
    
    public void clearTaskSelection() {
        this.tasksTable.clearSelection();
    }
    
    public Component getSelectedComponent() {
        if (this.componentsTable.getSelectedRow() < 0) {
            return null;
        }
        return (Component)((DefaultEventTableModel)this.componentsTable.getModel()).getElementAt(this.componentsTable.getSelectedRow());
    }
    
    public void clearComponentSelection() {
        this.componentsTable.clearSelection();
    }
    
    public Asset getSelectedAsset() {
        if (this.assetsTable.getSelectedRow() < 0) {
            return null;
        }
        return (Asset)((DefaultEventTableModel)this.assetsTable.getModel()).getElementAt(this.assetsTable.getSelectedRow());
    }
    
    public void clearAssetSelection() {
        this.assetsTable.clearSelection();
    }
    
    public String getSelectedTabName() {
        int index = this.tabbedPaneView.getSelectedIndex();
        String name = this.tabbedPaneView.getTitleAt(index);
        return name;
    }
    
    public void setDetailViewPanel(JPanel panel) {
        if (panel == null) {
            detailScrollPane.getViewport().add(new JPanel());
        }
        else {
            detailScrollPane.getViewport().add(panel);
        }
    }
    
    public void addNewProjectButtonActionListener(ActionListener listener) {
        newProjectButton.addActionListener(listener);
    }
    
    public void addNewTaskButtonActionListener(ActionListener listener) {
        newTaskButton.addActionListener(listener);
    }
    
    public void addNewComponentButtonActionListener(ActionListener listener) {
        newComponentButton.addActionListener(listener);
    }
    
    public void addNewAssetButtonActionListener(ActionListener listener) {
        newAssetButton.addActionListener(listener);
    }
    
    public void addUserMenuLogOutActionListener(ActionListener listener) {
        userMenuLogOut.addActionListener(listener);
    }
    
    public void addApplicationMenuExitActionListener(ActionListener listener) {
        applicationMenuExit.addActionListener(listener);
    }
    
    public void addProjectsTableListSelectionListener(ListSelectionListener listener) {
        projectsTable.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void addTasksTableListSelectionListener(ListSelectionListener listener) {
        tasksTable.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void addComponentsTableListSelectionListener(ListSelectionListener listener) {
        componentsTable.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void addAssetsTableListSelectionListener(ListSelectionListener listener) {
        assetsTable.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void addTabChangeListener(ChangeListener listener) {
        tabbedPaneView.addChangeListener(listener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        projectSplitPane = new javax.swing.JSplitPane();
        detailPanel = new javax.swing.JPanel();
        detailScrollPane = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        tabbedPaneView = new javax.swing.JTabbedPane();
        projectsTablePanel = new javax.swing.JPanel();
        projectsScrollPane = new javax.swing.JScrollPane();
        projectsTable = new javax.swing.JTable();
        newProjectButton = new javax.swing.JButton();
        tasksTablePanel = new javax.swing.JPanel();
        tasksScrollPane = new javax.swing.JScrollPane();
        tasksTable = new javax.swing.JTable();
        newTaskButton = new javax.swing.JButton();
        componentsTablePanel = new javax.swing.JPanel();
        componentsScrollPane = new javax.swing.JScrollPane();
        componentsTable = new javax.swing.JTable();
        newComponentButton = new javax.swing.JButton();
        assetsTablePanel = new javax.swing.JPanel();
        assetsScrollPane = new javax.swing.JScrollPane();
        assetsTable = new javax.swing.JTable();
        newAssetButton = new javax.swing.JButton();
        welcomeLabel = new javax.swing.JLabel();
        projectMenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        applicationMenuExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        userMenuLogOut = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MPPMS - Home");
        setPreferredSize(new java.awt.Dimension(900, 555));

        projectSplitPane.setBorder(null);
        projectSplitPane.setDividerLocation(450);

        detailScrollPane.setBorder(null);

        javax.swing.GroupLayout detailPanelLayout = new javax.swing.GroupLayout(detailPanel);
        detailPanel.setLayout(detailPanelLayout);
        detailPanelLayout.setHorizontalGroup(
            detailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(detailScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
        );
        detailPanelLayout.setVerticalGroup(
            detailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(detailScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
        );

        projectSplitPane.setRightComponent(detailPanel);

        tabbedPaneView.setMinimumSize(new java.awt.Dimension(100, 55));
        tabbedPaneView.setPreferredSize(new java.awt.Dimension(400, 430));

        projectsScrollPane.setBorder(null);

        projectsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        projectsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        projectsScrollPane.setViewportView(projectsTable);

        newProjectButton.setText("Create New Project");

        javax.swing.GroupLayout projectsTablePanelLayout = new javax.swing.GroupLayout(projectsTablePanel);
        projectsTablePanel.setLayout(projectsTablePanelLayout);
        projectsTablePanelLayout.setHorizontalGroup(
            projectsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(projectsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(projectsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(projectsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                    .addComponent(newProjectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        projectsTablePanelLayout.setVerticalGroup(
            projectsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(projectsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newProjectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(projectsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneView.addTab("Projects", projectsTablePanel);

        tasksScrollPane.setBorder(null);

        tasksTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tasksTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tasksScrollPane.setViewportView(tasksTable);

        newTaskButton.setText("Create New Task");

        javax.swing.GroupLayout tasksTablePanelLayout = new javax.swing.GroupLayout(tasksTablePanel);
        tasksTablePanel.setLayout(tasksTablePanelLayout);
        tasksTablePanelLayout.setHorizontalGroup(
            tasksTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tasksTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tasksTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tasksScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                    .addComponent(newTaskButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tasksTablePanelLayout.setVerticalGroup(
            tasksTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tasksTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newTaskButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tasksScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabbedPaneView.addTab("Tasks", tasksTablePanel);

        componentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        componentsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        componentsScrollPane.setViewportView(componentsTable);

        newComponentButton.setText("Create New Component");

        javax.swing.GroupLayout componentsTablePanelLayout = new javax.swing.GroupLayout(componentsTablePanel);
        componentsTablePanel.setLayout(componentsTablePanelLayout);
        componentsTablePanelLayout.setHorizontalGroup(
            componentsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(componentsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(componentsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(componentsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                    .addComponent(newComponentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        componentsTablePanelLayout.setVerticalGroup(
            componentsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, componentsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newComponentButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(componentsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneView.addTab("Components", componentsTablePanel);

        assetsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        assetsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        assetsScrollPane.setViewportView(assetsTable);

        newAssetButton.setText("Create New Asset");

        javax.swing.GroupLayout assetsTablePanelLayout = new javax.swing.GroupLayout(assetsTablePanel);
        assetsTablePanel.setLayout(assetsTablePanelLayout);
        assetsTablePanelLayout.setHorizontalGroup(
            assetsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assetsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(assetsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(assetsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                    .addComponent(newAssetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        assetsTablePanelLayout.setVerticalGroup(
            assetsTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, assetsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newAssetButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(assetsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneView.addTab("Assets", assetsTablePanel);

        welcomeLabel.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(welcomeLabel)
                .addContainerGap(406, Short.MAX_VALUE))
            .addComponent(tabbedPaneView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(welcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabbedPaneView, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))
        );

        tabbedPaneView.getAccessibleContext().setAccessibleName("");

        projectSplitPane.setLeftComponent(jPanel1);

        projectMenuBar.setBackground(new java.awt.Color(204, 204, 255));
        projectMenuBar.setBorder(null);

        jMenu1.setText("Application");

        applicationMenuExit.setText("Exit");
        jMenu1.add(applicationMenuExit);

        projectMenuBar.add(jMenu1);

        jMenu2.setText("User");

        userMenuLogOut.setText("Log Out");
        jMenu2.add(userMenuLogOut);

        projectMenuBar.add(jMenu2);

        setJMenuBar(projectMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(projectSplitPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(projectSplitPane)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem applicationMenuExit;
    private javax.swing.JScrollPane assetsScrollPane;
    private javax.swing.JTable assetsTable;
    private javax.swing.JPanel assetsTablePanel;
    private javax.swing.JScrollPane componentsScrollPane;
    private javax.swing.JTable componentsTable;
    private javax.swing.JPanel componentsTablePanel;
    private javax.swing.JPanel detailPanel;
    private javax.swing.JScrollPane detailScrollPane;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton newAssetButton;
    private javax.swing.JButton newComponentButton;
    private javax.swing.JButton newProjectButton;
    private javax.swing.JButton newTaskButton;
    private javax.swing.JMenuBar projectMenuBar;
    private javax.swing.JSplitPane projectSplitPane;
    private javax.swing.JScrollPane projectsScrollPane;
    private javax.swing.JTable projectsTable;
    private javax.swing.JPanel projectsTablePanel;
    private javax.swing.JTabbedPane tabbedPaneView;
    private javax.swing.JScrollPane tasksScrollPane;
    private javax.swing.JTable tasksTable;
    private javax.swing.JPanel tasksTablePanel;
    private javax.swing.JMenuItem userMenuLogOut;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
