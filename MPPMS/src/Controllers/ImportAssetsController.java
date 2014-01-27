package Controllers;

import Models.Asset;
import Models.SetOfAssets;
import Models.Task;
import Models.Task.TaskType;
import Views.ImportAssetsView;
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

public class ImportAssetsController {
    private final ImportAssetsView view;
    
    public ImportAssetsController(ImportAssetsView view) {
        this.view = view;
    }
    
    public void initialise() {
        view.addFileChooserActionListener(new AssetsChooserActionListener());
    }
    
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

                        Asset asset = new Asset(0, length, assetType, description);
                        asset.save();
                        
                        // Create Inbound QA Task for Asset
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
    
    public void createTasks(Asset asset) {
        SetOfAssets assets = new SetOfAssets();
        assets.add(asset);
        Task newTask = new Task();
        newTask.setTitle("Inbound QA Task for Asset " + asset.getId());
        newTask.setTaskType(TaskType.QA);
        newTask.setAssets(assets);
        newTask.save();
    }
    
    class AssetsChooserActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            parseAssetFiles();
        } 
    }  
}
