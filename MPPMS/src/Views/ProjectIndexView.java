package Views;

import Models.Project;
import Models.SetOfProjects;
import Models.Task;
import Models.SetOfTasks;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.DefaultEventTableModel;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;

public class ProjectIndexView extends javax.swing.JFrame {

    public ProjectIndexView() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public void setWelcomeMessage(String message) {
        this.welcomeLabel.setText(message);
    }
    
    public void setProjectsTableData(SetOfProjects projects) {
        String[] headers = new String[]{ "Title", "Creation date", "Manager", "Coordinator" };
        String[] properties = new String[]{ "Title", "CreationDate", "Manager", "Coordinator" };
        
        EventList projectsEventList = GlazedLists.eventList(projects);
        TableFormat projectsTableFormat = GlazedLists.tableFormat(Project.class, properties, headers);
        DefaultEventTableModel projectsTableModel = new DefaultEventTableModel(projectsEventList, projectsTableFormat);
        
        this.projectsTable.setModel(projectsTableModel);
    }
    
    public void setTasksTableData(SetOfTasks tasks) {
        String[] headers = new String[]{ "Title", "Status", "Priority" };
        String[] properties = new String[]{ "Title", "Status", "Priority" };
        
        EventList tasksEventList = GlazedLists.eventList(tasks);
        TableFormat tasksTableFormat = GlazedLists.tableFormat(Task.class, properties, headers);
        DefaultEventTableModel tasksTableModel = new DefaultEventTableModel(tasksEventList, tasksTableFormat);
        
        this.tasksTable.setModel(tasksTableModel);
    }
    
    public Project getSelectedProject() {
        if (this.projectsTable.getSelectedRow() < 0) {
            return null;
        }
        return (Project)((DefaultEventTableModel)this.projectsTable.getModel()).getElementAt(this.projectsTable.getSelectedRow());
    }
    
    public Task getSelectedTask() {
        if (this.tasksTable.getSelectedRow() < 0) {
            return null;
        }
        return (Task)((DefaultEventTableModel)this.tasksTable.getModel()).getElementAt(this.tasksTable.getSelectedRow());
    }
    
    public int getSelectedTabIndex() {
        return this.tabbedPaneView.getSelectedIndex();
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
        projectTablePanel = new javax.swing.JPanel();
        projectsScrollPane = new javax.swing.JScrollPane();
        projectsTable = new javax.swing.JTable();
        newProjectButton = new javax.swing.JButton();
        taskTablePanel = new javax.swing.JPanel();
        tasksScrollPane = new javax.swing.JScrollPane();
        tasksTable = new javax.swing.JTable();
        newTaskButton = new javax.swing.JButton();
        welcomeLabel = new javax.swing.JLabel();
        projectMenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        applicationMenuExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        userMenuLogOut = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MPPMS - Projects");
        setPreferredSize(new java.awt.Dimension(900, 450));

        projectSplitPane.setBorder(null);
        projectSplitPane.setDividerLocation(300);

        detailScrollPane.setBorder(null);

        javax.swing.GroupLayout detailPanelLayout = new javax.swing.GroupLayout(detailPanel);
        detailPanel.setLayout(detailPanelLayout);
        detailPanelLayout.setHorizontalGroup(
            detailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(detailScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
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

        newProjectButton.setText("New Project");

        javax.swing.GroupLayout projectTablePanelLayout = new javax.swing.GroupLayout(projectTablePanel);
        projectTablePanel.setLayout(projectTablePanelLayout);
        projectTablePanelLayout.setHorizontalGroup(
            projectTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(projectTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(projectTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(projectsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(newProjectButton, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
                .addContainerGap())
        );
        projectTablePanelLayout.setVerticalGroup(
            projectTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(projectTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newProjectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(projectsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneView.addTab("Projects", projectTablePanel);

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

        newTaskButton.setText("New Task");

        javax.swing.GroupLayout taskTablePanelLayout = new javax.swing.GroupLayout(taskTablePanel);
        taskTablePanel.setLayout(taskTablePanelLayout);
        taskTablePanelLayout.setHorizontalGroup(
            taskTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(taskTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(taskTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tasksScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(newTaskButton, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
                .addContainerGap())
        );
        taskTablePanelLayout.setVerticalGroup(
            taskTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(taskTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newTaskButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tasksScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneView.addTab("Tasks", taskTablePanel);

        welcomeLabel.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(welcomeLabel)
                .addContainerGap(256, Short.MAX_VALUE))
            .addComponent(tabbedPaneView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(welcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabbedPaneView, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))
        );

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
    private javax.swing.JPanel detailPanel;
    private javax.swing.JScrollPane detailScrollPane;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton newProjectButton;
    private javax.swing.JButton newTaskButton;
    private javax.swing.JMenuBar projectMenuBar;
    private javax.swing.JSplitPane projectSplitPane;
    private javax.swing.JPanel projectTablePanel;
    private javax.swing.JScrollPane projectsScrollPane;
    private javax.swing.JTable projectsTable;
    private javax.swing.JTabbedPane tabbedPaneView;
    private javax.swing.JPanel taskTablePanel;
    private javax.swing.JScrollPane tasksScrollPane;
    private javax.swing.JTable tasksTable;
    private javax.swing.JMenuItem userMenuLogOut;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
