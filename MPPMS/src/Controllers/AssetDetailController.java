package Controllers;

import Models.Asset;
import Views.AssetDetailView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssetDetailController {
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
    }
    
    private void refreshView() {
        this.view.setIdLabelText("ID: " + this.asset.getId());
        this.view.setDescriptionText(this.asset.getDescription());
        this.view.setType(Asset.AssetType.values(), this.asset.getAssetType());
        this.view.setLengthText(this.asset.getLengthAsString());
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
}
