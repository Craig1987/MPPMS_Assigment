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
import java.util.Vector;

/**
 *
 * IMPORTANT: To use a model with this controller/view it must extend Model
 * 
 * @author ryantk
 */
public class ModelChoiceController {
    
    ModelChoiceView view = new ModelChoiceView();
    Vector<Model> allModels = new Vector<>();
    
    public ModelChoiceController(Vector<Model> allTheModels) {
        allModels = (Vector<Model>) allTheModels.clone();
        
        view.setAvailableModels(allModels);
        view.setChosenModels(new Vector<Model>());
        
        view.setTitle("Chose Model");
    }
    
    /*
        preChosenModels MUST be a Vector of the EXACT objects as in allTheModels
        due to the way Vector.remove removes based on object ID rather than
        looking at it's fields
    */
    public ModelChoiceController(Vector<Model> allTheModels, Vector<Model> preChosenModels) {
        allModels = (Vector<Model>) allTheModels.clone();
        
        Vector<Model> remainingModels = new Vector<>();
        
        remainingModels.addAll(allTheModels);
        remainingModels.removeAll(preChosenModels);
        
        view.setAvailableModels(remainingModels);
        view.setChosenModels(preChosenModels);
        
        view.setTitle("Chose Model");
    }
    
    public void setModelTypeName(String name) {
        view.setTitle("Chose a " + name);
        view.setAvailableModelsLabel("Available " + name + "s");
        view.setChosenModelsLabel("Selected " + name + "s");
    }
    
    public void setTitle(String title) {
        view.setTitle(title);
    }
    
    public void launch() {
        view.addAddModelButtonActionListener(new AddModelButtonActionListener());
        view.addRemoveModelButtonActionListener(new RemoveModelButtonActionListener());
        view.addClearSelectionButtonActionListener(new ClearSelectionButtonActionListener());
        view.addSaveChosenModelsButtonActionListener(new SaveChosenModelsButtonActionListener());
        
        view.setVisible(true);
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
            //
        }
    }
    
}
