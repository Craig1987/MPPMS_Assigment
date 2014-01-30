package Controllers;

import Application.AppObservable;
import Models.Asset;
import Models.Component;
import Models.Project;
import Models.SetOfAssets;
import Models.SetOfComponents;
import Models.SetOfProjects;
import Models.SetOfTasks;
import Models.Task;
import Models.User;
import Views.ProjectsHierarchyView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * 
 * @author Kirsty
 */

public class ProjectsHierarchyController implements Observer {
    private final ProjectsHierarchyView view;
    private final User currentUser;
    private Asset asset;
    private Project selectedProject;
    private DefaultMutableTreeNode selectedNode;
    
    public ProjectsHierarchyController(ProjectsHierarchyView view, User currentUser) {
        this.view = view;
        this.currentUser = currentUser;
        
        this.view.addProjectsTreeSelectionListener(new ProjectsTreeSelectionListener());
        this.view.addAddToTaskButtonActionListener(new AddToTaskActionListener());
        this.view.addAddToComponentButtonActionListener(new AddToComponentActionListener());
        this.view.addRemoveFromTaskButtonActionListener(new RemoveFromTaskActionListener());
        this.view.addRemoveFromComponentButtonActionListener(new RemoveFromComponentActionListener());
    }
    
    public void initialise() {
        view.setControlsEnabled(false);
        view.setControlsVisible(false);
        view.setVisible(true);
        refreshView();
        
        AppObservable.getInstance().addObserver(this);
    }
    
    private void refreshView() {
       // All the projects that the current logged in user has access to
       SetOfProjects projects = Project.getProjectsForUser(currentUser);
       
       DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Projects");
       
       if (projects != null) {
            for (Project project : projects) {
                // Create Project node
                DefaultMutableTreeNode projectNode = new DefaultMutableTreeNode(project);
                
                // Get all tasks for project node
                SetOfTasks tasks = project.getTasks();
                DefaultMutableTreeNode taskRootNode = new DefaultMutableTreeNode("Tasks");
                
                if (tasks != null) {
                    for (Task task : tasks) {
                        DefaultMutableTreeNode taskNode = new DefaultMutableTreeNode(task);

                        // Get all assets for task node
                        SetOfAssets assets = task.getAssets();
                        if (assets != null) {
                            for (Asset taskAsset : assets) {
                                taskNode.add(new DefaultMutableTreeNode(taskAsset));
                            }
                        }
                        
                        taskRootNode.add(taskNode);
                    }
                    projectNode.add(taskRootNode);
                }
                
                DefaultMutableTreeNode comRootNode = new DefaultMutableTreeNode("Components");
                
                //Get all components for project node
                SetOfComponents coms = project.getComponents();
                if (coms != null) {
                    for (Component com : coms) {
                        DefaultMutableTreeNode comNode = new DefaultMutableTreeNode(com);
                        
                        //Get all assets for component node
                        SetOfAssets assets = com.getAssets();
                        if (assets != null) {
                            for (Asset compAsset : assets) {
                                comNode.add(new DefaultMutableTreeNode(compAsset));
                            }
                        }
                        comRootNode.add(comNode);
                    }
                    projectNode.add(comRootNode);
                }
                rootNode.add(projectNode);
            }

            DefaultTreeModel newModel = new DefaultTreeModel(rootNode);
            newModel.nodeStructureChanged(rootNode);
            view.setTreeModel(newModel);
       }
    }
    
    @Override
    public void update(Observable o, Object o1) {       
        refreshView();
        this.asset = Asset.getAssetByID(this.asset.getId());
        updateAssetDetail();
    }
    
    public void updateAssetDetail() {
        view.setProjectDetails("Project: " + selectedProject.toString());
        view.setAssetDetails("Asset: " + asset.toString());
        view.setControlsEnabled(true);

        SetOfTasks tasks = selectedProject.getTasks();
        SetOfComponents comps = selectedProject.getComponents();

        SetOfTasks removeTasks = new SetOfTasks();
        SetOfTasks addTasks = new SetOfTasks();
        SetOfComponents removeComps = new SetOfComponents();
        SetOfComponents addComps = new SetOfComponents();


        // Populate Task ComboBoxes
        for (Task task : tasks) {
           SetOfAssets taskAssets = task.getAssets();
           if (taskAssets.contains(asset))
               removeTasks.add(task);
           else
               addTasks.add(task);
        }

        // Populate Component ComboBoxes
        for (Component comp : comps) {
           SetOfAssets compAssets = comp.getAssets();
           if (compAssets.contains(asset))
               removeComps.add(comp);
           else
               addComps.add(comp);
        }

        view.removeTasksEnabled(removeTasks.size() > 0);
        view.setRemoveTasksComboBox(removeTasks.toArray());

        view.addTasksEnabled(addTasks.size() > 0);
        view.setAddtoTasksComboBox(addTasks.toArray());

        view.removeComponentsEnabled(removeComps.size() > 0);
        view.setRemoveComponentsComboBox(removeComps.toArray());

        view.addComponentsEnabled(addComps.size() > 0);
        view.setAddtoComponentsComboBox(addComps.toArray());

        view.setControlsVisible(true);
    }
    
    class ProjectsTreeSelectionListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) { 
            // Get selected node object
            selectedNode = view.getSelectedTreeNode();
            if (selectedNode != null) {
                Object assetObj = selectedNode.getUserObject();
            
                if (assetObj instanceof Asset) {
                    asset = (Asset) assetObj; 

                    // If asset is selected then 3 levels up should be a project
                    DefaultMutableTreeNode projectNode = (DefaultMutableTreeNode) selectedNode.getParent().getParent().getParent();
                    Object projectObj = projectNode.getUserObject();

                    if (projectObj instanceof Project)
                    {
                        selectedProject = (Project) projectObj;
                        updateAssetDetail();
                    }
                } 
                else {
                    view.setControlsEnabled(false);
                    view.setControlsVisible(false);
                }     
            }
            
        }
    }
    
    class AddToTaskActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Task selectedTask = view.getSelectedAddTask();
            
            if (asset != null) {
                SetOfAssets tasksAssets = selectedTask.getAssets();
                tasksAssets.add(asset);
                selectedTask.setAssets(tasksAssets);
                selectedTask.save();
            }
            
        }
    }
    
    class RemoveFromTaskActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Task selectedTask = view.getSelectedRemoveTask();
            
            if (asset != null) {
                SetOfAssets tasksAssets = selectedTask.getAssets();
                tasksAssets.remove(asset);
                selectedTask.setAssets(tasksAssets);
                selectedTask.save();
            }
        }
    }
    
    class AddToComponentActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Component selectedComponent = view.getSelectedAddComponent();
            
            if (asset != null) {
                SetOfAssets componentAssets = selectedComponent.getAssets();
                componentAssets.add(asset);
                selectedComponent.setAssets(componentAssets);
                selectedComponent.save();
            }
        }
    }
    
    class RemoveFromComponentActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Component selectedComponent = view.getSelectedRemoveComponent();
            
            if (asset != null) {
                SetOfAssets componentAssets = selectedComponent.getAssets();
                componentAssets.remove(asset);
                selectedComponent.setAssets(componentAssets);
                selectedComponent.save();
            }
        }
    }
}
