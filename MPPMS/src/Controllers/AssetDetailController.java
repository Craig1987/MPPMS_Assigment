package Controllers;

import Application.AppObservable;
import Models.Asset;
import Views.AssetDetailView;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

/**
 * Controller class for AssetDetailView. Observer of AppObservable.
 * 
 * @see AssetDetailView
 * @see AppObservable
 */
public class AssetDetailController implements Observer {
    private final AssetDetailView view;
    
    private Asset asset;
    private boolean isNew;
    
    /**
     * AssetDetailController constructor
     *
     * @param view This controller's view
     * @param asset The Asset to be displayed / edited
     */
    public AssetDetailController(AssetDetailView view, Asset asset) {
        this.view = view;
        this.asset = asset;
        // Indicate whether or not we are creating a new Asset which doesn't yet exist in the database.
        this.isNew = this.asset.getId() < 1;
    }
    
    /**
     * Initialise the view and add event listeners to its UI controls.
     */
    public void initialise() {
        refreshView();
        
        // Enable / disable specific UI controls
        this.view.setEditMode(this.isNew);
        
        // Event listeners
        this.view.addSaveButtonActionListener(new SaveButtonActionListener());
        this.view.addEditButtonActionListener(new EditButtonActionListener());
        this.view.addDiscardButtonActionListener(new DiscardButtonActionListener());
        
        /**
         * Craig - TC B2c: Real time updates
         * Register this controller as an observer
         */
        AppObservable.getInstance().addObserver(this);
    }
    
    /**
     * Refresh all of the data displayed in the view.
     */
    private void refreshView() {
        this.view.setIdLabelText("ID: " + this.asset.getId());
        this.view.setDescriptionText(this.asset.getDescription());
        this.view.setType(Asset.AssetType.values(), this.asset.getAssetType());
        this.view.setLengthText(this.asset.getLengthAsString());
    }
    
    /**
     * Validation of the user inputs when editing or creating an Asset.
     * 
     * @return true if the validation passes, false if it fails.
     */
    private boolean validateUserInputs() {
        ArrayList<String> errors = new ArrayList();
        
        if (this.view.getDescription().equals("")) {
            errors.add("\t - Enter a description");
        }
        if (this.view.getLength() < 0) {
            errors.add("\t - Enter a valid asset length (0 if N/A)");
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
            this.asset = Asset.getAssetByID(this.asset.getId());        
            refreshView();
        }
    }
    
    /**
     * Event listener for the 'Save' button. Only attempts to save the Asset if
     * validation passes.
     * 
     * @see #validateUserInputs()
     * @see ActionListener
     */
    class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (validateUserInputs()) {
                view.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                
                Asset temp = asset;
                
                asset.setAssetType(view.getAssetType());
                asset.setDescription(view.getDescription());
                asset.setLength(view.getLength());
                
                if (asset.save()) {
                    // Success
                    view.setEditMode(false);
                    isNew = false;
                }
                else {
                    // Failure
                    asset = temp;
                    JOptionPane.showMessageDialog(view, "Error saving Asset", "'Asset' Error", JOptionPane.ERROR_MESSAGE);
                }
                
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }        
    }
    
    /**
     * Event listener for the 'Discard changes' button. Disables UI control editing.
     */
    class DiscardButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setEditMode(false);
            refreshView();
        }        
    }
    
    /**
     * Event listener for the 'Edit' button. Enables UI control editing.
     */
    class EditButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {   
            view.setEditMode(true);
        }        
    }
}
