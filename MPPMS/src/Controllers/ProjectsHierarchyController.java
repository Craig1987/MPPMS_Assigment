package Controllers;

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

/**
 * 
 * @author Kirsty
 */

public class ProjectsHierarchyController implements Observer {
    private final ProjectsHierarchyView view;
    private final User currentUser;
    private Asset asset;
    
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
    }
    
    private void refreshView() {
       // All the projects that the current logged in user has access to
       SetOfProjects projects = Project.getProjectsForUser(currentUser);
       
       // Gets existing TreeModel allowing child nodes to be added to the root node "Projects"
       DefaultTreeModel model = view.getTreeModel();
       DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();
       
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
                            for (Asset asset : assets) {
                                taskNode.add(new DefaultMutableTreeNode(asset));
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
                            for (Asset asset : assets) {
                                comNode.add(new DefaultMutableTreeNode(asset));
                            }
                        }
                        comRootNode.add(comNode);
                    }
                    projectNode.add(comRootNode);
                }
                rootNode.add(projectNode);
            }

            DefaultTreeModel newModel = new DefaultTreeModel(rootNode);
            view.setTreeModel(newModel);
       }
    }
    
    @Override
    public void update(Observable o, Object o1) {       
        refreshView();
    }
    
    class ProjectsTreeSelectionListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = view.getSelectedTreeNode();
            Object assetObj = node.getUserObject();
            
            if (assetObj instanceof Asset) {
                asset = (Asset) assetObj; 
                view.setAssetDetails(asset.toString());
                
                SetOfTasks tasks = Task.getAllTasks();
                SetOfComponents comps = Component.getAllComponents();
                
                SetOfTasks removeTasks = new SetOfTasks();
                SetOfTasks addTasks = new SetOfTasks();
                SetOfComponents removeComps = new SetOfComponents();
                SetOfComponents addComps = new SetOfComponents();
                
                
                // Populate Task ComboBoxes
                for (Task task : tasks) {
                   SetOfAssets assets = task.getAssets();
                   for (Asset taskAsset : assets) {
                       // Asset can be removed from this task
                       if (taskAsset.getId() == asset.getId())
                           removeTasks.add(task);
                       // Asset can be added to this task
                       else
                           addTasks.add(task);
                   }
                }
                
                for (Component comp : comps) {
                   SetOfAssets assets = comp.getAssets();
                   for (Asset compAsset : assets) {
                       // Asset can be removed from this component
                       if (compAsset.getId() == asset.getId())
                           removeComps.add(comp);
                       // Asset can added to this component
                       else
                           addComps.add(comp);
                   }
                }
                
                view.setRemoveTasksComboBox(removeTasks.toArray());
                view.setAddtoTasksComboBox(addTasks.toArray());
                view.setRemoveComponentsComboBox(removeComps.toArray());
                view.setAddtoComponentsComboBox(addComps.toArray());
                view.setControlsEnabled(true);
                view.setControlsVisible(true);
            } 
            else {
                view.setControlsEnabled(false);
                view.setControlsVisible(false);
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
