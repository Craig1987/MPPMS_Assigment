package Controllers;

import Application.AppObservable;
import Models.Asset;
import Models.Component;
import Models.SetOfAssets;
import Views.ComponentDetailView;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
        this.view.setCanEditAsset(false);
        
        this.view.addAssetChoiceActionListener(new AssetChoiceActionListener());
        this.view.addSaveButtonActionListener(new SaveButtonActionListener());
        this.view.addEditButtonActionListener(new EditButtonActionListener());
        this.view.addAssetsListSelectionListener(new AssetsListSelectionListener());
        if (!this.isNew) {
            this.view.addDiscardButtonActionListener(new DiscardButtonActionListener());
        }
        
        AppObservable.getInstance().addObserver(this);
    }
    
    private void refreshView() {
        this.view.setIdLabelText("ID: " + this.component.getId());
        this.view.setDescriptionText(this.component.getDescription());
        this.view.setAssets(this.component.getAssets().toArray());
    }
    
    public Asset getSelectedAsset() {
        return (Asset)this.view.getSelectedAsset();
    }
    
    private boolean validateUserInputs() {
        ArrayList<String> errors = new ArrayList();
        
        if (this.view.getDescription().equals("")) {
            errors.add("\t - Enter a description");
        }
        if (this.view.getAssets().length == 0) {
            errors.add("\t - Add at least one Asset");
        }
        
        if (errors.size() > 0) {
            String errorMsg = "Unable to save new Asset.\nDetails:";
            for (String error : errors) {
                errorMsg += "\n" + error;
            }
            JOptionPane.showMessageDialog(this.view, errorMsg, "Unable to Save", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
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
            if (validateUserInputs()) {
                view.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                
                Component temp = component;
                
                Object[] objects = view.getAssets();
                SetOfAssets assets = new SetOfAssets();
                for (Object object : objects) {
                    assets.add((Asset)object);
                }

                component.setDescription(view.getDescription());
                component.setAssets(assets); 
                
                if (component.save()) {
                    if (modelChoiceController != null) {
                        modelChoiceController.closeView();
                    }
                    view.setEditMode(false);
                    isNew = false;
                }
                else {
                    component = temp;
                    JOptionPane.showMessageDialog(view, "Error saving Component", "'Component' Error", JOptionPane.ERROR_MESSAGE);
                }
                
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }        
    }
    
    class DiscardButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (modelChoiceController != null) {
                modelChoiceController.closeView();
            }
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
            modelChoiceController = new ModelChoiceController((Collection)Asset.getAllAssets().clone(), component.getAssets(), view);
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
    
    class AssetsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setCanEditAsset(!((DefaultListSelectionModel)lse.getSource()).isSelectionEmpty());
        }
    }
}
