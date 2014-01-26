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
 *
 * IMPORTANT: To use a model with this controller/view it must extend Model
 * 
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
    
    public ModelChoiceController(Collection allTheModels, JPanel locationParent) {
        allModels = (ArrayList<Model>) allTheModels;
        
        view.setLocationRelativeTo(locationParent);
        
        view.setAvailableModels(allModels);
        view.setChosenModels(new ArrayList<Model>());
        
        this.modelType = determineModelType();
        
        setLabels();
    }
    
    /*
        preChosenModels MUST be a ArrayList of the EXACT objects as in allTheModels
        due to the way ArrayList.remove removes based on object ID rather than
        looking at it's fields
    */
    public ModelChoiceController(Collection allTheModels, Collection preChosenModels, JPanel locationParent) {
        allModels = (ArrayList<Model>) allTheModels;
        
        ArrayList<Model> remainingModels = new ArrayList<>();
        ArrayList<Model> preChosen = (ArrayList<Model>) preChosenModels;
        
        remainingModels.addAll(allTheModels);
        remainingModels.removeAll(preChosenModels);
        
        view.setLocationRelativeTo(locationParent);
        
        view.setAvailableModels(remainingModels);
        view.setChosenModels(preChosen);
        
        view.setAddModelButtonEnabled(false);
        view.setRemoveModelButtonEnabled(false);
        view.setClearButtonEnabled(preChosenModels.size() > 0);
        
        this.modelType = determineModelType();
        
        setLabels();
    }
    
    public void launch() {
        view.addAddModelButtonActionListener(new AddModelButtonActionListener());
        view.addRemoveModelButtonActionListener(new RemoveModelButtonActionListener());
        view.addClearSelectionButtonActionListener(new ClearSelectionButtonActionListener());
        view.addChosenModelsListSelectionListener(new ChosenModelsListSelectionListener());
        view.addAvailableModelsListSelectionListener(new AvailableModelsListSelectionListener());
        
        view.setVisible(true);
        
        AppObservable.getInstance().addObserver(this);
    }
    
    public void closeView() {
        view.dispose();
    }
    
    public void addSaveButtonActionListener(ActionListener listener) {
        view.addSaveChosenModelsButtonActionListener(listener);
    }
    
    public ArrayList<Model> getChosenModels() {
        return view.getChosenModels();
    }
    
    public ArrayList<Model> getAvailableModels() {
        return view.getAvailableModels();
    }
    
    public ModelType getModelType() {
        return this.modelType;
    }
    
    private ModelType determineModelType() {
        String classDescription = allModels.get(0).getClass().toString();
        String className = classDescription.substring(classDescription.lastIndexOf(".") + 1);
        return ModelType.valueOf(className);
    }
    
    private void setLabels() {
        view.setTitle("MPPMS - Select " + this.modelType + "s");
        view.setTitleLabel("Select " + this.modelType + "s");
        view.setAvailableModelsLabel("Available " + this.modelType + "s");
        view.setChosenModelsLabel("Selected " + this.modelType + "s");
    }

    @Override
    public void update(Observable o, Object o1) {
        Model selectedChosen = null;
        Model selectedAvailable = null;
        
        this.allModels.clear();
        
        // Update selected models
        ArrayList<Model> updatedSelected = new ArrayList();
        for (Model model : getChosenModels()) {
            switch (this.getModelType()) {
                case User:
                    User user = User.getUserByUsername(((User)model).getUsername());
                    updatedSelected.add(user);
                    this.allModels.add(user);
                    // Remember the user's JList selection
                    try {
                        selectedChosen = User.getUserByUsername(((User)view.getSelectedChosenModel()).getUsername());
                    }
                    catch (NoModelSelectedException ex) { }
                    break;
                case Task:
                    Task task = Task.getTaskByID(((Task)model).getId());
                    updatedSelected.add(task);
                    this.allModels.add(task);
                    // Remember the user's JList selection
                    try {
                        selectedChosen = Task.getTaskByID(((Task)view.getSelectedChosenModel()).getId());
                    }
                    catch (NoModelSelectedException ex) { }
                    break;
                case Component:
                    Component component = Component.getComponentByID(((Component)model).getId());
                    updatedSelected.add(component);
                    this.allModels.add(component);
                    // Remember the user's JList selection
                    try {
                        selectedChosen = Component.getComponentByID(((Component)view.getSelectedChosenModel()).getId());
                    }
                    catch (NoModelSelectedException ex) { }
                    break;
                case Asset:
                    Asset asset = Asset.getAssetByID(((Asset)model).getId());
                    updatedSelected.add(asset);
                    this.allModels.add(asset);
                    // Remember the user's JList selection
                    try {
                        selectedChosen = Asset.getAssetByID(((Asset)view.getSelectedChosenModel()).getId());
                    }
                    catch (NoModelSelectedException ex) { }
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
                    // Remember the user's JList selection
                    try {
                        selectedAvailable = User.getUserByUsername(((User)view.getSelectedChosenModel()).getUsername());
                    }
                    catch (NoModelSelectedException ex) { }
                    break;
                case Task:
                    Task task = Task.getTaskByID(((Task)model).getId());
                    updatedAvailable.add(task);
                    this.allModels.add(task);
                    // Remember the user's JList selection
                    try {
                        selectedAvailable = Task.getTaskByID(((Task)view.getSelectedChosenModel()).getId());
                    }
                    catch (NoModelSelectedException ex) { }
                    break;
                case Component:
                    Component component = Component.getComponentByID(((Component)model).getId());
                    updatedAvailable.add(component);
                    this.allModels.add(component);
                    // Remember the user's JList selection
                    try {
                        selectedAvailable = Component.getComponentByID(((Component)view.getSelectedChosenModel()).getId());
                    }
                    catch (NoModelSelectedException ex) { }
                    break;
                case Asset:
                    Asset asset = Asset.getAssetByID(((Asset)model).getId());
                    updatedAvailable.add(asset);
                    this.allModels.add(asset);
                    // Remember the user's JList selection
                    try {
                        selectedAvailable = Asset.getAssetByID(((Asset)view.getSelectedChosenModel()).getId());
                    }
                    catch (NoModelSelectedException ex) { }
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
        this.view.setChosenModels(updatedSelected);
        this.view.setAvailableModels(updatedAvailable);
        
        // Persist the user's previous selections
        this.view.setSelectedChosenModel(selectedChosen);
        this.view.setSelectedAvailableModel(selectedAvailable);
    }
    
    class AddModelButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Model> chosenModels = view.getChosenModels();
            ArrayList<Model> availableModels = view.getAvailableModels();
            
            Model selectedModel;
            
            try {
                selectedModel = view.getSelectedAvailableModel();
            } catch (NoModelSelectedException ex) {
                return;
            }
            
            chosenModels.add(selectedModel);
            availableModels.remove(selectedModel);
            
            view.setChosenModels(chosenModels);
            view.setAvailableModels(availableModels);
            
            view.setClearButtonEnabled(chosenModels.size() > 0);
        }
    }
    
    class RemoveModelButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Model> chosenModels = view.getChosenModels();
            ArrayList<Model> availableModels = view.getAvailableModels();
            
            Model selectedModel;
            
            try {
                selectedModel = view.getSelectedChosenModel();
            } catch (NoModelSelectedException ex) {
                return;
            }
            
            availableModels.add(selectedModel);
            chosenModels.remove(selectedModel);
            
            view.setChosenModels(chosenModels);
            view.setAvailableModels(availableModels);
            
            view.setClearButtonEnabled(chosenModels.size() > 0);
        }
    }
    
    class ClearSelectionButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {            
            view.setChosenModels(new ArrayList<Model>());
            view.setAvailableModels(allModels);
        }
    }
    
    class ChosenModelsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setRemoveModelButtonEnabled(view.getChosenModelsSelectedIndex() >= 0);
        }
    }
    
    class AvailableModelsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setAddModelButtonEnabled(view.getAvailableModelsSelectedIndex() >= 0);
        }
    }
}
