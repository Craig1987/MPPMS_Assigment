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
    private final Component component;
    
    private ModelChoiceController modelChoiceController;
    
    public ComponentDetailController(ComponentDetailView view, Component component) {
        this.view = view;
        this.component = component;
    }
    
    public void initialise() {
        this.view.setIdLabelText("ID: " + this.component.getId());
        this.view.setDescriptionText(this.component.getDescription());
        this.view.setAssets(this.component.getAssets().toArray());
        
        if (component.getId() == null) {
            this.view.setIdLabelText("ID: New Component");
            this.view.setEditButtonVisible(false);
            this.view.setSaveButtonVisible(true);
        }
        
        this.view.addAssetChoiceActionListener(new AssetChoiceActionListener());
        this.view.addSaveButtonActionListener(new SaveButtonActionListener());
    }
    
    class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            view.getComponent().save();
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
