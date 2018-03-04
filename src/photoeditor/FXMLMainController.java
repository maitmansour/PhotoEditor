/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoeditor;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Mohamed AIT MANSOUR <contact@numidea.com>
 */
public class FXMLMainController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void selectDirectoryHandler(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
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
