package Controllers;

import Models.Asset;
import Models.Component;
import Models.SetOfAssets;
import Views.ComponentDetailView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class ComponentDetailController {
    private final ComponentDetailView view;
    
    private Component component;
    private boolean isNew;
    
    private ModelChoiceController modelChoiceController;
    
    public ComponentDetailController(ComponentDetailView view, Component component) {
        this.view = view;
        this.component = component;
        this.isNew = this.component.getId() < 1;
    }
    
    public void initialise() {
        refreshView();
        
        this.view.setEditMode(isNew);
        
        this.view.addAssetChoiceActionListener(new AssetChoiceActionListener());
        this.view.addSaveButtonActionListener(new SaveButtonActionListener());
        this.view.addEditButtonActionListener(new EditButtonActionListener());
        if (!this.isNew) {
            this.view.addDiscardButtonActionListener(new DiscardButtonActionListener());
        }
    }
    
    private void refreshView() {
        this.view.setIdLabelText("ID: " + this.component.getId());
        this.view.setDescriptionText(this.component.getDescription());
        this.view.setAssets(this.component.getAssets().toArray());
    }
    
    class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setEditMode(false);
            isNew = false;
        }        
    }
    
    class DiscardButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setEditMode(false);
            refreshView();
        }        
    }
    
    class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {   
            view.setEditMode(true);
        }        
    }

    class AssetChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController(Asset.getAllAssets(), component.getAssets());
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    class ModelChoiceSaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SetOfAssets assets = new SetOfAssets();
            assets.addAll((Collection)modelChoiceController.getChosenModels());                    
            modelChoiceController.closeView();                    
            component.setAssets(assets);
            view.setAssets(assets.toArray());
        }        
    }
}
