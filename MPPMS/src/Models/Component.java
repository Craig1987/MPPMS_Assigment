package Models;

import java.util.Vector;

public class Component {
    private Vector<Asset> assets = new Vector<>();
    
    public Vector<Asset> getAssets() {
        return assets;
    }
    
    public void addAsset(Asset a) {
        assets.add(a);
    }
    
    public void removeAsset(Asset a) {
        assets.remove(a);
    }
}
