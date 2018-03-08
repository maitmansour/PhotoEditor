/*
 * The MIT License
 *
 * Copyright 2018 Mohamed AIT MANSOUR <contact@numidea.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package photoeditor;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Mohamed AIT MANSOUR <contact@numidea.com>
 */
public class FXMLUpdatePictureController implements Initializable {

        public static String tmpTitle;
        public static String tmpTags;
        public static String tmpPicture;

 @FXML 
 private javafx.scene.control.Button saveButton;
 
 @FXML 
 private TextArea tagsTextArea;
 
 @FXML 
 private TextField titleTextField;
       
        
    @FXML
    private void saveHandler(ActionEvent event) throws IOException, Exception {
    Stage stage = (Stage) saveButton.getScene().getWindow();
    String currentPictureTags = tagsTextArea.getText().toUpperCase();
    String[] TagsParts = currentPictureTags.split(",");
    Set<String> HashTagsParts = new HashSet<String>(Arrays.asList(TagsParts));
    PhotoEditor.getMapOfKeywords().get(tmpPicture).clear();
            for (String TagsPart : HashTagsParts) {
                PhotoEditor.getMapOfKeywords().get(tmpPicture).add(TagsPart);
            }

    stage.close();
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleTextField.setText(tmpTitle);
        tagsTextArea.setText(tmpTags);
        // TODO
    }    
    
}
