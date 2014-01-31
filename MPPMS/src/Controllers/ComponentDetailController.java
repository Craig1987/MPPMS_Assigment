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

/**
 * Controller for ComponentDetailView. Observes AppObservable
 * 
 * @see ComponentDetailView
 * @see AppObservable
 */
public class ComponentDetailController implements Observer {
    private final ComponentDetailView view;
    
    private Component component;
    private boolean isNew;
    
    private ModelChoiceController modelChoiceController;
    
    /**
     * ComponentDetailController constructor
     * 
     * @param view This controller's view
     * @param component The Component to display / edit
     */
    public ComponentDetailController(ComponentDetailView view, Component component) {
        this.view = view;
        this.component = component;
        this.isNew = this.component.getId() < 1;
    }
    
    public void initialise() {
        refreshView();
        
        this.view.setEditMode(isNew);
        this.view.setCanViewAsset(false);
        
        this.view.addAssetChoiceActionListener(new AssetChoiceActionListener());
        this.view.addSaveButtonActionListener(new SaveButtonActionListener());
        this.view.addEditButtonActionListener(new EditButtonActionListener());
        this.view.addAssetsListSelectionListener(new AssetsListSelectionListener());
        if (!this.isNew) {
            this.view.addDiscardButtonActionListener(new DiscardButtonActionListener());
        }
        
        /**
         * Craig - TC B2c: Real time updates
         * Register this controller as an observer
         */
        AppObservable.getInstance().addObserver(this);
    }
    
    /**
     * Refreshes the data displayed on the view
     */
    private void refreshView() {
        this.view.setIdLabelText("ID: " + this.component.getId());
        this.view.setDescriptionText(this.component.getDescription());
        this.view.setAssets(this.component.getAssets().toArray());
    }
    
    /**
     * Gets the selected Asset
     * 
     * @return The Asset selected in the JList in the view
     */
    public Asset getSelectedAsset() {
        return (Asset)this.view.getSelectedAsset();
    }
    
    /**
     * Validates the user inputs prior to saving a new / edited Component
     * 
     * @return true if validation passes, false if it fails
     */
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
    
    /**
     * Event listener for the 'Save' button. Disables editing of UI controls if
     * the save is successful.
     */
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
                    // Success
                    if (modelChoiceController != null) {
                        modelChoiceController.closeView();
                    }
                    view.setEditMode(false);
                    isNew = false;
                }
                else {
                    // Failure
                    component = temp;
                    JOptionPane.showMessageDialog(view, "Error saving Component", "'Component' Error", JOptionPane.ERROR_MESSAGE);
                }
                
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }        
    }
    
    /**
     * Event listener for the 'Discard' button. Disables editing of UI controls.
     */
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
    
    /**
     * Event listener for the 'Edit' button. Enabled editing of the UI controls.
     */
    class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {   
            view.setEditMode(true);
        }        
    }

    /**
     * Event listener for the 'Add / Remove' button next to the Assets list. Launches #
     * the ModelChoice view to allow selection of which Assets are included as part
     * of this Component.
     */
    class AssetChoiceActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            modelChoiceController = new ModelChoiceController((Collection)Asset.getAllAssets().clone(), component.getAssets(), view);
            modelChoiceController.addSaveButtonActionListener(new ModelChoiceSaveActionListener());
            modelChoiceController.launch();
        }        
    }
    
    /**
     * Event listener for the 'Save' button on the ModelChoice view. Updates the
     * view with the chosen Assets.
     */
    class ModelChoiceSaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            SetOfAssets assets = new SetOfAssets();
            assets.addAll((Collection)modelChoiceController.getChosenModels());                    
            modelChoiceController.closeView();
            view.setAssets(assets.toArray());
        }        
    }
    
    /**
     * Event listener for the list of Assets. Enables or disables the 'View' button 
     * alongside the list of Assets.
     */
    class AssetsListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            view.setCanViewAsset(!((DefaultListSelectionModel)lse.getSource()).isSelectionEmpty());
        }
    }
}
