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

public class AssetDetailController implements Observer {
    private final AssetDetailView view;
    
    private Asset asset;
    private boolean isNew;
    
    public AssetDetailController(AssetDetailView view, Asset asset) {
        this.view = view;
        this.asset = asset;
        this.isNew = this.asset.getId() < 1;
    }
    
    public void initialise() {
        refreshView();
        
        this.view.setEditMode(this.isNew);
        
        this.view.addSaveButtonActionListener(new SaveButtonActionListener());
        this.view.addEditButtonActionListener(new EditButtonActionListener());
        this.view.addDiscardButtonActionListener(new DiscardButtonActionListener());
        
        AppObservable.getInstance().addObserver(this);
    }
    
    private void refreshView() {
        this.view.setIdLabelText("ID: " + this.asset.getId());
        this.view.setDescriptionText(this.asset.getDescription());
        this.view.setType(Asset.AssetType.values(), this.asset.getAssetType());
        this.view.setLengthText(this.asset.getLengthAsString());
    }
    
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
    
    class PreviewButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new MultimediaDisplayController(asset);
        }
    }
    
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
                    view.setEditMode(false);
                    isNew = false;
                }
                else {
                    asset = temp;
                    JOptionPane.showMessageDialog(view, "Error saving Asset", "'Asset' Error", JOptionPane.ERROR_MESSAGE);
                }
                
                view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
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
}
