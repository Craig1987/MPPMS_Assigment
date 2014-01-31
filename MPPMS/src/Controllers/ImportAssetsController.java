package Controllers;

import Models.Asset;
import Models.SetOfAssets;
import Models.Task;
import Models.Task.TaskType;
import Views.ImportAssetsView;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Controller for ImportAssetsView.
 *
 * @see ImportAssetsView
 */

public class ImportAssetsController {
    private final ImportAssetsView view;
    
    /**
     * ImportAssetsController constructor
     * 
     * @param view The Controller's view
     */
    public ImportAssetsController(ImportAssetsView view) {
        this.view = view;
    }
    
    /**
     * Adds ActionListener for the file chooser.
     */
    public void initialise() {
        view.addFileChooserActionListener(new AssetsChooserActionListener());
    }
    
    /**
     * Called from the file chooser ActionListener.
     * Parses the XML content of the chosen .asset files and creates Asset objects for each one.
     * Calls createTasks for each .asset file if the check box to create 'Inbound QA Tasks' is ticked.
     */
    
    public void parseAssetFiles() {
        File [] assetFiles = view.getFiles();
        
        for (File file : assetFiles) {
            try 
            {
                String pathToFile = file.getPath();
                pathToFile = pathToFile.replaceAll("%20", " ");
                File fXmlFile = new File(pathToFile);
                DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = docBuilder.parse(fXmlFile);

                // Prevent unwanted behaviour resulting from poorly formatted XML
                doc.getDocumentElement().normalize();

                // Get the assets
                NodeList nodes = doc.getElementsByTagName("Asset");
                
                for (int i = 0; i < nodes.getLength(); i++)
                {
                    Node node = nodes.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element element = (Element)node;
                        int length = Integer.parseInt(element.getElementsByTagName("Length").item(0).getTextContent());
                        Asset.AssetType assetType = Asset.AssetType.valueOf(element.getElementsByTagName("Type").item(0).getTextContent());
                        String description = element.getElementsByTagName("Description").item(0).getTextContent();
                        String filePath = element.getElementsByTagName("FilePath").item(0).getTextContent();

                        Asset asset = new Asset(0, length, assetType, description);
                        
                        File f = new File(filePath);
                        
                        if (f.exists()) {
                            asset.setFile(f);
                        } else {
                            
                        }
                        
                        asset.save();
                        
                        // Create Inbound QA_Moderation Task for Asset
                        if (view.getTasksCheckbox()) {
                            createTasks(asset);
                        }
                    }
                }
            }
            catch (ParserConfigurationException | SAXException | IOException | DOMException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    /**
     * Creates a new task of type Inbound QA for the selected asset
     * @param asset The asset object created from the selected .asset file.
     */
    public void createTasks(Asset asset) {
        SetOfAssets assets = new SetOfAssets();
        assets.add(asset);
        Task newTask = new Task();
        newTask.setTitle("Inbound QA Task for Asset " + asset.getId());
        newTask.setTaskType(TaskType.Inbound_QA);
        newTask.setAssets(assets);
        newTask.save();
    }
    
    /**
     * Called when an action event is triggered by the file chooser
     * Calls parseAssetFiles();
     */
    class AssetsChooserActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            parseAssetFiles();
            view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } 
    }  
}
