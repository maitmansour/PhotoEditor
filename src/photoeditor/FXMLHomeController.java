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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mohamed AIT MANSOUR <contact@numidea.com>
 */
public class FXMLHomeController implements Initializable {

    @FXML
    private Label titleLabel;
    private Locale locale;
    private ResourceBundle bundle;
    private Map < String, ImageView > MapOfImages;


     
    
    
    @FXML
    private ListView < String > picturesList;
    
    @FXML
    private Label  pictureTitleName;
    
    @FXML
    private Label  pictureTagsValue;

    @FXML
    private AnchorPane firstPane;

    @FXML
    private ImageView bigPicture;

    /**
     * Fill the Big Picture Frame with clicked picture
     * @param event 
     */
    @FXML
    private void clickOnItemHandler(MouseEvent event) throws Exception {
        String currentPicture=picturesList.getSelectionModel().getSelectedItem();
        bigPicture.setImage(MapOfImages.get(currentPicture).getImage());
        bigPicture.setFitHeight(300);
        bigPicture.setFitWidth(300);
        bigPicture.setPreserveRatio(false);
        ArrayList<String> currentPictureKeywords = PhotoEditor.getMapOfKeywords().get(currentPicture);
        pictureTitleName.setText(currentPicture);
        StringBuilder sb = new StringBuilder();
        for (String s : currentPictureKeywords)
        {
            sb.append(s);
            sb.append(",\t");
        }
        FXMLUpdatePictureController.tmpTitle=currentPicture;
        FXMLUpdatePictureController.tmpTags=sb.toString();
        //To add new Keyword : 
        //PhotoEditor.getMapOfKeywords().get(currentPicture).add("ok");
       // pictureTagsValue.setText(sb.toString());
        
    }

    @FXML
    private void langFrChoosed(ActionEvent event) {
      firstPane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        loadLang("fr");
    }

    @FXML
    private void langEnChoosed(ActionEvent event) {
      firstPane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        loadLang("en");
    }
    
    @FXML
    private void langArChoosed(ActionEvent event) {
      firstPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        loadLang("ar");

    }

    /**
     * When Update Button clicked
     * @param event
     * @throws IOException 
     */
    @FXML
    private void updateHandler(ActionEvent event) throws IOException {
        //System.out.println(PhotoEditor.getSelectedPath());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLUpdatePicture.fxml"));
        Parent rootWindow = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(rootWindow));
        stage.show();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MapOfImages = new HashMap <  > ();
            initListView();
        } catch (Exception ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Load Lang and changes values of current textes to other language
     * @param lang 
     */
    private void loadLang(String lang) {
        // TODO : Complete internationnalization
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("bundles.lang", locale);
        titleLabel.setText(bundle.getString("titleLabel"));

    }

    /**
     * fill Listview
     */
    private void initListView() throws Exception {
        String[] listOfImagesPaths = PhotoEditor.getExtentionAndFileFounder().getFilesList(PhotoEditor.getSelectedPath(), ".jpg");

        ImageView[] listOfImages = new ImageView[listOfImagesPaths.length];

        for (int i = 0; i < listOfImagesPaths.length; i++) {
            ImageView tmpImageView = new ImageView(PhotoEditor.prepareImagePath(listOfImagesPaths[i]));
            tmpImageView.setFitHeight(150);
            tmpImageView.setFitWidth(150);
            PhotoEditor.getMapOfKeywords().putIfAbsent(listOfImagesPaths[i], new ArrayList());
            MapOfImages.put(listOfImagesPaths[i], tmpImageView);
        }


        ObservableList < String > items = FXCollections.observableArrayList(
            listOfImagesPaths);
        picturesList.setItems(items);
        picturesList.setCellFactory(param -> new ListCell < String > () {
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    //setText(name);
                    setGraphic(MapOfImages.get(name));
                }
            }
        });


    }

}
