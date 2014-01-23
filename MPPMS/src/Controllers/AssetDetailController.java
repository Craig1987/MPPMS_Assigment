package Controllers;

import Application.AppObservable;
import Models.Asset;
import Views.AssetDetailView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

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
    
    @Override
    public void update(Observable o, Object o1) {
        this.asset = Asset.getAssetByID(this.asset.getId());        
        refreshView();
    }
    
    class SaveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setEditMode(false);
            isNew = false;
            
            Asset newAsset = new Asset(0, view.getLength(), view.getAssetType(), view.getDescription());
            newAsset.save();
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
