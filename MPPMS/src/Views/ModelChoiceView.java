package Views;

import Exceptions.NoModelSelectedException;
import Models.Model;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionListener;

public class ModelChoiceView extends javax.swing.JFrame {
    private ArrayList<Model> availableModels = new ArrayList<>();
    private ArrayList<Model> chosenModels = new ArrayList<>();
    
    public ModelChoiceView() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/resources/icon.png")).getImage());
    }
    
    public Model getSelectedAvailableModel() throws NoModelSelectedException {
        int selectedIndex = listbox_availableModels.getSelectedIndex();        
        if (selectedIndex < 0) {
            throw new NoModelSelectedException();
        }        
        return availableModels.get(selectedIndex);
    }
    
    public void setSelectedAvailableModel(Model model) {
        listbox_availableModels.setSelectedValue(model, true);
    }
    
    public Model getSelectedChosenModel() throws NoModelSelectedException {
        int selectedIndex = listbox_chosenModels.getSelectedIndex();        
        if (selectedIndex < 0) {
            throw new NoModelSelectedException();
        }        
        return chosenModels.get(selectedIndex);
    }
    
    public void setSelectedChosenModel(Model model) {
        listbox_chosenModels.setSelectedValue(model, true);
    }
    
    public ArrayList<Model> getChosenModels(){
        return (ArrayList<Model>) chosenModels.clone();
    }
    
    public ArrayList<Model> getAvailableModels() {
        return (ArrayList<Model>) availableModels.clone();
    }
    
    public int getChosenModelsSelectedIndex() {
        return this.listbox_chosenModels.getSelectedIndex();
    }
    
    public int getAvailableModelsSelectedIndex() {
        return this.listbox_availableModels.getSelectedIndex();
    }
    
    public void setAddModelButtonEnabled(boolean b) {
        this.btn_addSelectedModel.setEnabled(b);
    }
    
    public void setRemoveModelButtonEnabled(boolean b) {
        this.btn_removeSelectedModel.setEnabled(b);
    }
    
    public void setClearButtonEnabled(boolean b) {
        this.btn_clearChosenModels.setEnabled(b);
    }
    
    public void setTitleLabel(String title) {
        lbl_title.setText(title);
    }
    
    public void setAvailableModelsLabel(String text) {
        lbl_availableModels.setText(text);
    }
    
    public void setChosenModelsLabel(String text) {
        lbl_chosenModels.setText(text);
    }
    
    public void setAvailableModels(ArrayList<Model> models){
        availableModels = models;
        listbox_availableModels.setListData(models.toArray());
    }
    
    public void setChosenModels(ArrayList<Model> models){
        chosenModels = models;
        listbox_chosenModels.setListData(models.toArray());
    }
    
    public void addAddModelButtonActionListener(ActionListener listener) {
        btn_addSelectedModel.addActionListener(listener);
    }
    
    public void addRemoveModelButtonActionListener(ActionListener listener) {
        btn_removeSelectedModel.addActionListener(listener);
    }
    
    public void addClearSelectionButtonActionListener(ActionListener listener) {
        btn_clearChosenModels.addActionListener(listener);
    }
    
    public void addSaveChosenModelsButtonActionListener(ActionListener listener) {
        btn_saveChosenModels.addActionListener(listener);
    }
    
    public void addChosenModelsListSelectionListener(ListSelectionListener listener) {
        listbox_chosenModels.getSelectionModel().addListSelectionListener(listener);
    }
    
    public void addAvailableModelsListSelectionListener(ListSelectionListener listener) {
        listbox_availableModels.getSelectionModel().addListSelectionListener(listener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listbox_chosenModels = new javax.swing.JList();
        lbl_availableModels = new javax.swing.JLabel();
        lbl_title = new javax.swing.JLabel();
        btn_saveChosenModels = new javax.swing.JButton();
        btn_addSelectedModel = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listbox_availableModels = new javax.swing.JList();
        btn_clearChosenModels = new javax.swing.JButton();
        lbl_chosenModels = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        btn_removeSelectedModel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        listbox_chosenModels.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(listbox_chosenModels);

        lbl_availableModels.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_availableModels.setText("Available [model]s");

        lbl_title.setText("Select [model]s");

        btn_saveChosenModels.setText("Save");

        btn_addSelectedModel.setText("<-- Add");

        listbox_availableModels.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(listbox_availableModels);

        btn_clearChosenModels.setText("Clear selected");

        lbl_chosenModels.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_chosenModels.setText("Selected [model]s");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        btn_removeSelectedModel.setText("Remove -->");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(lbl_chosenModels, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_addSelectedModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_removeSelectedModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_clearChosenModels, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_saveChosenModels, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_availableModels, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_title, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 273, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_chosenModels)
                    .addComponent(lbl_availableModels))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_addSelectedModel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_removeSelectedModel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_clearChosenModels)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_saveChosenModels)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        // Dispose of this JFrame if it's focus is lost to another JFrame belonging to this application
        if(evt.getOppositeWindow() != null){
            this.dispose();
        }
    }//GEN-LAST:event_formWindowDeactivated

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_addSelectedModel;
    private javax.swing.JButton btn_clearChosenModels;
    private javax.swing.JButton btn_removeSelectedModel;
    private javax.swing.JButton btn_saveChosenModels;
    private javax.swing.JButton cancelButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_availableModels;
    private javax.swing.JLabel lbl_chosenModels;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JList listbox_availableModels;
    private javax.swing.JList listbox_chosenModels;
    // End of variables declaration//GEN-END:variables
}
