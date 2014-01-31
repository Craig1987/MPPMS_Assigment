package Controllers;

import Application.AppObservable;
import Exceptions.NoModelSelectedException;
import Models.Asset;
import Models.Component;
import Models.Model;
import Models.Task;
import Models.User;
import Views.ModelChoiceView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Controller for ModelChoiceView. Any models (User, Asset etc.) passed to this 
 * controller must inherit from Model.
 * 
 * @see Model
 */
public class ModelChoiceController implements Observer {
    private final ModelChoiceView view = new ModelChoiceView();
    private final ModelType modelType;
    
    private ArrayList<Model> allModels = new ArrayList<>();

    public enum ModelType {
        User,
        Task,
        Component,
        Asset
    }
    
    /**
     * ModelChoiceController constructor. This constructor is used when passing in
     * available models but no chosen ones.
     * 
     * @param allTheModels All available models
     * @param locationParent Parent panel for positioning purposes
     */
    public ModelChoiceController(Collection allTheModels, JPanel locationParent) {
        allModels = (ArrayList<Model>) allTheModels;
        
        view.setLocationRelativeTo(locationParent);
        
        view.setAvailableModels(allModels.toArray());
        view.setChosenModels(new Object[0]);
        
        this.modelType = determineModelType();
        
        setLabels();
    }
    
    /**
     * ModelChoiceController constructor. This constructor is used when passing in
     * both available models and pre-chosen models. If available models contain
     * any of the same models as in chosen, they are parsed so that there are no duplicates.
     * 
     * @param allTheModels All available models
     * @param preChosenModels Pre-selected models
     * @param locationParent Parent panel for positioning purposes
     */
    public ModelChoiceController(Collection allTheModels, Collection preChosenModels, JPanel locationParent) {
        allModels = (ArrayList<Model>) allTheModels;
        
        ArrayList<Model> remainingModels = new ArrayList<>();
        ArrayList<Model> preChosen = (ArrayList<Model>) preChosenModels;
        
        remainingModels.addAll(allTheModels);
        remainingModels.removeAll(preChosenModels);
        
        view.setLocationRelativeTo(locationParent);
        
        view.setAvailableModels(remainingModels.toArray());
        view.setChosenModels(preChosen.toArray());
        
        view.setAddModelButtonEnabled(false);
        view.setRemoveModelButtonEnabled(false);
        view.setClearButtonEnabled(preChosenModels.size() > 0);
        
        this.modelType = determineModelType();
        
        setLabels();
    }
    
    /**
     * Adds event listeners to the view and makes the view visible.
     */
    public void launch() {
        view.addAddModelButtonActionListener(new AddModelButtonActionListener());
        view.addRemoveModelButtonActionListener(new RemoveModelButtonActionListener());
        view.addClearSelectionButtonActionListener(new ClearSelectionButtonActionListener());
        view.addChosenModelsListSelectionListener(new ChosenModelsListSelectionListener());
        view.addAvailableModelsListSelectionListener(new AvailableModelsListSelectionListener());
        
        view.setVisible(true);
        
        /**
         * Craig - TC B2c: Real time updates
         * Register this controller as an observer
         */
        AppObservable.getInstance().addObserver(this);
    }
    
    /**
     * Disposes of the view.
     */
    public void closeView() {
        view.dispose();
    }
    
    /**
     * Adds an action listener to the 'Save' button.
     * 
     * @param listener The Action listener to be added
     */
    public void addSaveButtonActionListener(ActionListener listener) {
        view.addSaveChosenModelsButtonActionListener(listener);
    }
    
    /**
     * Gets all models in the view's 'Chosen' list.
     * 
     * @return ArrayList of chosen Models
     */
    public ArrayList<Model> getChosenModels() {
        return view.getChosenModels();
    }
    
    /**
     * Gets all models in the view's 'Available' list.
     * 
     * @return ArrayList of available Models
     */
    public ArrayList<Model> getAvailableModels() {
        return view.getAvailableModels();
    }
    
    /**
     * Gets the type of the model being dealt with.
     * @return The model type (User, Asset etc.)
     */
    public ModelType getModelType() {
        return this.modelType;
    }
    
    /**
     * Figures out the type of Model being dealth with.
     * 
     * @return The model type (User, Asset etc.)
     */
    private ModelType determineModelType() {
        String classDescription = allModels.get(0).getClass().toString();
        String className = classDescription.substring(classDescription.lastIndexOf(".") + 1);
        return ModelType.valueOf(className);
    }
    
    /**
     * Sets the view's data labels based on the determined model type.
     */
    private void setLabels() {
        view.setTitle("MPPMS - Select " + this.modelType + "s");
        view.setTitleLabel("Select " + this.modelType + "s");
        view.setAvailableModelsLabel("Available " + this.modelType + "s");
        view.setChosenModelsLabel("Selected " + this.modelType + "s");
    }

    @Override
    public void update(Observable o, Object o1) {
        /**
         * This method is called after something in the database has changed.
         * It retrieves all of it's data again (which will now be updated) and sets
         * it in the view.
         */
        
        this.allModels.clear();
        
        // Update selected models
        ArrayList<Model> updatedSelected = new ArrayList();
        for (Model model : getChosenModels()) {
            switch (this.getModelType()) {
                case User:
                    User user = User.getUserByUsername(((User)model).getUsername());
                    updatedSelected.add(user);
                    this.allModels.add(user);
                    break;
                case Task:
                    Task task = Task.getTaskByID(((Task)model).getId());
                    updatedSelected.add(task);
                    this.allModels.add(task);
                    break;
                case Component:
                    Component component = Component.getComponentByID(((Component)model).getId());
                    updatedSelected.add(component);
                    this.allModels.add(component);
                    break;
                case Asset:
                    Asset asset = Asset.getAssetByID(((Asset)model).getId());
                    updatedSelected.add(asset);
                    this.allModels.add(asset);
                    break;
            }
        }
        
        // Update available models
        ArrayList<Model> updatedAvailable = new ArrayList();
        for (Model model : getAvailableModels()) {
            switch (this.getModelType()) {
                case User:
                    User user = User.getUserByUsername(((User)model).getUsername());
                    updatedAvailable.add(user);
                    this.allModels.add(user);
                    break;
                case Task:
                    Task task = Task.getTaskByID(((Task)model).getId());
                    updatedAvailable.add(task);
                    this.allModels.add(task);
                    break;
                case Component:
                    Component component = Component.getComponentByID(((Component)model).getId());
                    updatedAvailable.add(component);
                    this.allModels.add(component);
                    break;
                case Asset:
                    Asset asset = Asset.getAssetByID(((Asset)model).getId());
                    updatedAvailable.add(asset);
                    this.allModels.add(asset);
                    break;
            }
        }
        
        // Check for any newly added models
        // Add them to allModels and available models if any
        switch (this.getModelType()) {
            case User:
                for (User user : User.getAllUsers()) {
                    if (!this.allModels.contains(user)) {
                        updatedAvailable.add(user);
                        this.allModels.add(user);
                    }
                }
                break;
            case Task:
                for (Task task : Task.getAllTasks()) {
                    if (!this.allModels.contains(task)) {
                        updatedAvailable.add(task);
                        this.allModels.add(task);
                    }
                }
                break;
            case Component:
                for (Component component : Component.getAllComponents()) {
                    if (!this.allModels.contains(component)) {
                        updatedAvailable.add(component);
                        this.allModels.add(component);
                    }
                }
                break;
            case Asset:
                for (Asset asset : Asset.getAllAssets()) {
                    if (!this.allModels.contains(asset)) {
                        updatedAvailable.add(asset);
                        this.allModels.add(asset);
                    }
                }
                break;
        }
        
        // Set the freshly updated models in the UI
        this.view.setChosenModels(updatedSelected.toArray());
        this.view.setAvailableModels(updatedAvailable.toArray());
    }
    
    /**
     * Event listener for the '<-- Add' button. Moves any selected models in the
     * view's available models list to the view's chosen models list.
     */
    class AddModelButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Model> chosenModels = view.getChosenModels();
            ArrayList<Model> availableModels = view.getAvailableModels();
            
            try {
                for (Model model : view.getSelectedAvailableModels()) {
                    chosenModels.add(model);
                    availableModels.remove(model);
                }
            } catch (NoModelSelectedException ex) {
                return;
            }
            
            view.setChosenModels(chosenModels.toArray());
            view.setAvailableModels(availableModels.toArray());            
            view.setClearButtonEnabled(chosenModels.size() > 0);
        }
    }
    
    /**
     * Event listener for the 'Remove -->' button. Moves any selected models in the
     * view's chosen models list to the view's available models list.
     */
    class RemoveModelButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Model> chosenModels = view.getChosenModels();
            ArrayList<Model> availableModels = view.getAvailableModels();
            
            try {
                for (Model model : view.getSelectedChosenModels()) {
                    chosenModels.remove(model);
                    availableModels.add(model);
                }
            } catch (NoModelSelectedException ex) {
                return;
            }
            
            view.setChosenModels(chosenModels.toArray());
            view.setAvailableModels(availableModels.toArray());            
            view.setClearButtonEnabled(chosenModels.size() > 0);
        }
    }
    
    /**
     * Event listener for the 'Clear selected' button. Moves all models in the view's
     * chosen models list to the view's available models list.
     */
    class ClearSelectionButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {            
            view.setChosenModels(new Object[0]);
            view.setAvailableModels(allModels.toArray());
        }
    }
    
    /**
     * Event listener for the view's chosen models list. Enables and disables the
     * 'Remove -->' button.
     */
    class ChosenModelsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setRemoveModelButtonEnabled(view.chosenModelSelected());
        }
    }
    
    /**
     * Event listener for the view's available models list. Enables and disables the
     * '<-- Add' button.
     */
    class AvailableModelsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setAddModelButtonEnabled(view.availableModelSelected());
        }
    }
}
