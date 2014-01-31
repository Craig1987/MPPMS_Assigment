/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Views;

import Models.Asset;
import Models.Annotation;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.media.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.Manager;
import javax.media.Player;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.imgscalr.Scalr;

/**
 *
 * @author ryantk
 */
public class MultimediaDisplayView extends javax.swing.JFrame {

    /**
     *  Component sizes
     */
    Dimension mediaDisplaySize    = new Dimension(600, 300);
    Dimension mediaControlsSize   = new Dimension(600, 20);
    Dimension mediaPanelSize      = new Dimension(600, mediaDisplaySize.height + mediaControlsSize.height);
    
    File file;
    MediaPlayer player;
    Asset asset;
    
    JButton start, stop;
    JPanel mediaPanel, annotationsPanel;


    public MultimediaDisplayView(Asset a) {
        initComponents();
        
        asset = a;
        
        // setup media file and player
        file   = asset.getFile();
        player = new JMFMediaPlayer();
        
        //if (! isMovieFile(file))
         //   mediaPanelSize = mediaControlsSize;
        
        // setup media panel
        mediaPanel = new JPanel();
        mediaPanel.setLayout(new BoxLayout(mediaPanel, BoxLayout.PAGE_AXIS));
        mediaPanel.setMaximumSize(mediaPanelSize);
        mediaPanel.setBorder(BorderFactory.createTitledBorder("Asset: " + file.getName()));
        mediaPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // set frame layout
        getContentPane().setLayout(
            new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS)
        );
        
        launchMedia();

        // add media and annotations panel to main window
        add(mediaPanel);
        
        // adjust size of window
        getContentPane().setSize(mediaPanelSize);
        
        // dont kill everything on close
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        // make window visible
        setVisible(true);
    }
    
    private void launchMedia() {
        // display media differently based on file type
        String extension = getFileExtension(file);
        
        switch (extension) {
            case "jpeg":
            case "jpg":
            case "png":
                displayImageAsset();
                break;

            case "mov":
            case "avi":
            case "aif":
            case "wav":
                player.readyMovieAsset();
                break;

            default:
                // Type unsupported
                displayMessage("File type: " + extension + " not supported");
        }
    }
    
    private String getFileExtension(File f){
        String filename = f.getName();
        return filename.substring(filename.indexOf(".") + 1);
    }
    
    private String annotationLabelText(Annotation an) {
        String text = isMovieFile(file) ? "[" + formatTimeToMinutes(an.getTime()) + "] " : "";
        return text + an.getText();
    }
    
    private String formatTimeToMinutes(Time t) {
        int seconds = (int) t.getSeconds();
        
        if (seconds > 3600) {
            int hours = seconds / 3600;
            int mins  = seconds - (hours * 3600);
            int secs  = seconds - (mins * 60);
            
            return String.format("%d:%02d:%02d", hours, mins, secs);
        } else if (seconds >= 60) {
            int mins = seconds / 60;
            int secs = seconds - (mins * 60);
            
            return String.format("%02d:%02d", mins, secs);
        } else {
            return String.format("00:%02d", seconds);
        }
    }

    private void displayMessage(String msg) {
        mediaPanel.removeAll();
        mediaPanel.add(formatDisplayLabel(new JLabel(msg)));
        mediaPanel.repaint();
    }

    private void displayImageAsset() {
        JLabel imageDisplay = formatDisplayLabel(new JLabel());

        try {
            BufferedImage thumbnail = Scalr.resize(
                ImageIO.read(file), 
                Scalr.Method.SPEED,
                Scalr.Mode.FIT_TO_HEIGHT,
                300, 
                280, 
                Scalr.OP_ANTIALIAS
            );            
            imageDisplay.setIcon(new ImageIcon(thumbnail));
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
            imageDisplay.setText("Unable to load file: " + file.getAbsolutePath());
        }

        mediaPanel.add(imageDisplay);
    }

    private JLabel formatDisplayLabel(JLabel label) {
        label.setSize(mediaDisplaySize);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        return label;
    }
    
    private List movieFileExtensions() {
        return Arrays.asList("mpeg", "mpg", "mov", "avi");
    }
        
    private Boolean isMovieFile(File f) {
        return movieFileExtensions().contains(getFileExtension(f));
    }
    
    class TestAsset extends Asset {
        List<Annotation> annotations = new ArrayList();
        File f;
        
        public TestAsset(File theFile){
            f = theFile;
            annotations.add(new Annotation("Great moment", new Time(6.0)));
            annotations.add(new Annotation("Rubbish moment", new Time(8.0)));
            annotations.add(new Annotation("Crap Quality", new Time(9.0)));
        }
        
        public File getFile() {
            return f;
        }
        
        public List<Annotation> getAnnotations() {
            return annotations;
        }
    }
    
    class AnnotationListener implements MouseListener {

        JLabel label;
        Annotation annotation;
        
        public AnnotationListener(JLabel lbl, Annotation an) {
            label = lbl;
            annotation = an;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            if (isMovieFile(file))
                player.setTime(annotation.getTime());
        }

        @Override
        public void mouseReleased(MouseEvent e) {}
        
        @Override
        public void mouseEntered(MouseEvent e) {
            label.setForeground(Color.red);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            label.setForeground(Color.black);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }        
    }
    
    interface MediaPlayer {
        public void readyMovieAsset();
        public void start();
        public void stop();
        public void setTime(Time t);
    }
    
    class JMFMediaPlayer implements MediaPlayer {
        Player jmfPlayer;
        Component mediaControls, visualComponent;
        
        @Override
        public void readyMovieAsset() {
            try {
                jmfPlayer = Manager.createRealizedPlayer(file.toURI().toURL());                
            } catch (Exception ex) {
                Logger.getLogger(MultimediaDisplayView.class.getName()).log(Level.SEVERE, null, ex);
                displayMessage("Unable to start media player");
                return;
            }
            
            // Only display the visual element for video files
            if (isMovieFile(file)){
                visualComponent = jmfPlayer.getVisualComponent();
                visualComponent.setSize(mediaDisplaySize);
                
                mediaPanel.add(visualComponent);
            }
            
            mediaControls = jmfPlayer.getControlPanelComponent();
            mediaControls.setSize(mediaControlsSize);
            mediaControls.setMaximumSize(mediaControlsSize);
            
            mediaPanel.add(mediaControls);
        }

        @Override
        public void start() {}

        @Override
        public void stop() {}
        
        @Override
        public void setTime(Time t) {
            jmfPlayer.setMediaTime(t);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>
    // Variables declaration - do not modify
    // End of variables declaration
}