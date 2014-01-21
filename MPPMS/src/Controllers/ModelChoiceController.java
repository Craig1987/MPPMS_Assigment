package Controllers;

import Exceptions.NoModelSelectedException;
import Models.Model;
import Views.ModelChoiceView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * IMPORTANT: To use a model with this controller/view it must extend Model
 * 
 */
public class ModelChoiceController {
    private final ModelChoiceView view = new ModelChoiceView();
    private final ModelType modelType;
    
    private ArrayList<Model> allModels = new ArrayList<>();

    public enum ModelType {
        User,
        Task,
        Component
    }
    
    public ModelChoiceController(Collection allTheModels) {
        allModels = (ArrayList<Model>) allTheModels;
        
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
    public ModelChoiceController(Collection allTheModels, Collection preChosenModels) {
        allModels = (ArrayList<Model>) allTheModels;
        
        ArrayList<Model> remainingModels = new ArrayList<>();
        ArrayList<Model> preChosen = (ArrayList<Model>) preChosenModels;
        
        remainingModels.addAll(allTheModels);
        remainingModels.removeAll(preChosenModels);
        
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
