/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoeditor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import java.io.File;
/**
 *
 * @author Mohamed AIT MANSOUR <contact@numidea.com>
 */
public class FXMLMainController implements Initializable {
    
    @FXML
    private Label selectedDirectoryText;
    
    @FXML
    private void selectDirectoryHandler(MouseEvent event) {
        // TODO : Path not showing correctely
         DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory =  directoryChooser.showDialog(null);
                
                if(selectedDirectory == null){
                   // selectedDirectoryText.setText("No Directory selected");
                    System.out.println("No Directory selected");
                }else{
                    String path=selectedDirectory.getAbsolutePath();
                   // selectedDirectoryText.setText(path);
                    System.out.println(path);
                }
    }
    
    @FXML
    private void closeHandler(MouseEvent event) {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
