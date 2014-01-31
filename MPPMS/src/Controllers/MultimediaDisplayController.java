/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Models.Asset;
import Views.MultimediaDisplayView;

/**
 *
 * @author ryantk
 */
public class MultimediaDisplayController {
    
    Asset asset;
    MultimediaDisplayView view;
    
    public MultimediaDisplayController(Asset a) {
        asset = a;
        view = new MultimediaDisplayView(asset);
    }
    
}
