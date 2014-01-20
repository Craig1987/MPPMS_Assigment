/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Exceptions.NoModelSelectedException;
import Models.Model;
import Views.ModelChoiceView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

/**
 *
 * IMPORTANT: To use a model with this controller/view it must extend Model
 * 
 * @author ryantk
 */
public class ModelChoiceController {    
    private ModelChoiceView view = new ModelChoiceView();
    private Vector<Model> allModels = new Vector<>();
    
    public ModelChoiceController(Collection allTheModels) {
        allModels = (Vector<Model>) allTheModels;
        
        view.setAvailableModels(allModels);
        view.setChosenModels(new Vector<Model>());
        
        view.setTitle("Chose Model");
    }
    
    /*
        preChosenModels MUST be a Vector of the EXACT objects as in allTheModels
        due to the way Vector.remove removes based on object ID rather than
        looking at it's fields
    */
    public ModelChoiceController(Collection allTheModels, Collection preChosenModels) {
        allModels = (Vector<Model>) allTheModels;
        
        Vector<Model> remainingModels = new Vector<>();
        Vector<Model> preChosen = (Vector<Model>) preChosenModels;
        
        remainingModels.addAll(allTheModels);
        remainingModels.removeAll(preChosenModels);
        
        view.setAvailableModels(remainingModels);
        view.setChosenModels(preChosen);
        
        String classDescription = allModels.get(0).getClass().toString();
        String className = classDescription.substring(classDescription.lastIndexOf(".") + 1);
        view.setTitle("MPPMS - Select " + className + "s");
        view.setTitleLabel("Select " + className + "s");
        view.setAvailableModelsLabel("Available " + className + "s");
        view.setChosenModelsLabel("Selected " + className + "s");
    }
    
    public void launch() {
        view.addAddModelButtonActionListener(new AddModelButtonActionListener());
        view.addRemoveModelButtonActionListener(new RemoveModelButtonActionListener());
        view.addClearSelectionButtonActionListener(new ClearSelectionButtonActionListener());
        view.addSaveChosenModelsButtonActionListener(new SaveChosenModelsButtonActionListener());
        
        view.setVisible(true);
    }
    
    public void setTitle(String title) {
        view.setTitle(title);
    }  
    
    class AddModelButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Vector<Model> chosenModels = view.getChosenModels();
            Vector<Model> availableModels = view.getAvailableModels();
            
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
        }
    }
    
    class RemoveModelButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Vector<Model> chosenModels = view.getChosenModels();
            Vector<Model> availableModels = view.getAvailableModels();
            
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
        }
    }
    
    class ClearSelectionButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {            
            view.setChosenModels(new Vector<Model>());
            view.setAvailableModels(allModels);
        }
    }
    
    class SaveChosenModelsButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {            
            // TODO
        }
    }
    
}
