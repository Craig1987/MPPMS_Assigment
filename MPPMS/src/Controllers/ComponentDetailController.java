package Controllers;

import Models.Asset;
import Models.Component;
import Models.SetOfAssets;
import Views.ComponentDetailView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

public class ComponentDetailController implements Observer {
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
    
    @Override
    public void update(Observable o, Object o1) {
        if (!this.isNew) {
            this.component = Component.getComponentByID(this.component.getId());        
            refreshView();
        }
    }
    
    class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setEditMode(false);
            isNew = false;
            
            Object[] objects = view.getAssets();
            SetOfAssets assets = new SetOfAssets();
            for (Object object : objects) {
                assets.add((Asset)object);
            }
            
            Component newComponent = new Component(component.getId(), view.getDescription());
            newComponent.setAssets(assets);            
            newComponent.save();
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
            view.setAssets(assets.toArray());
        }        
    }
}
