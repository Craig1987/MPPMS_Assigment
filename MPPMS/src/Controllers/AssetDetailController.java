package Controllers;

import Models.Asset;
import Views.AssetDetailView;

public class AssetDetailController {
    private final Asset asset;
    private final AssetDetailView view;
    
    public AssetDetailController(AssetDetailView view, Asset asset) {
        this.view = view;
        this.asset = asset;
    }
    
    public void initialise() {
        this.view.setIdLabelText("ID: " + this.asset.getId());
        this.view.setDescriptionText(this.asset.getDescription());
        this.view.setTypeText(this.asset.getAssetType().toString());
        this.view.setLengthText(this.asset.getLengthAsString());
    }
}
