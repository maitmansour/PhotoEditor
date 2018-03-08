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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    private TextField  SearchTagValue;
    
    @FXML
    private Label  pictureTagsValue;

    @FXML
    private AnchorPane firstPane;

    @FXML
    private ImageView bigPicture;

    

    @FXML
    private void findByTagHandler(ActionEvent event) throws Exception {
        if (SearchTagValue.getText().equals("")) {
            PhotoEditor.alertBuilder(3,Alert.AlertType.WARNING);
        initListView(null);
        }else{
                    initListView(SearchTagValue.getText());
        }
    }
    /**
     * Fill the Big Picture Frame with clicked picture
     * @param event 
     */
    @FXML
    private void clickOnItemHandler(MouseEvent event) throws Exception {
        String currentPicture=picturesList.getSelectionModel().getSelectedItem();
        FXMLUpdatePictureController.tmpPicture=currentPicture;
        bigPicture.setImage(MapOfImages.get(currentPicture).getImage());
        bigPicture.setFitHeight(300);
        bigPicture.setFitWidth(300);
        bigPicture.setPreserveRatio(false);
        ArrayList<String> currentPictureKeywords = PhotoEditor.getMapOfKeywords().get(currentPicture);
        pictureTitleName.setText(currentPicture);
        StringBuilder sb = new StringBuilder();
        currentPictureKeywords.stream().map((s) -> {
            sb.append(s);
            return s;
        }).forEach((_item) -> {
            sb.append(",");
        });
        FXMLUpdatePictureController.tmpTitle=currentPicture;
        FXMLUpdatePictureController.tmpTags=sb.toString();
        pictureTagsValue.setText(sb.toString());
        
    }

    @FXML
    private void langFrChoosed(ActionEvent event) {
      firstPane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        PhotoEditor.alertBuilder(4,Alert.AlertType.INFORMATION);
        loadLang("fr");
    }

    @FXML
    private void langEnChoosed(ActionEvent event) {
      firstPane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        PhotoEditor.alertBuilder(4,Alert.AlertType.INFORMATION);
        loadLang("en");
    }
    
    @FXML
    private void langArChoosed(ActionEvent event) {
                PhotoEditor.alertBuilder(4,Alert.AlertType.INFORMATION);

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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            MapOfImages = new HashMap <  > ();
            initListView(null);
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
    private void initListView(String Keyword) throws Exception {
        picturesList.getItems().removeAll();
        String[] listOfImagesPaths = PhotoEditor.getExtentionAndFileFounder().getFilesList(PhotoEditor.getSelectedPath(), ".jpg");

        if (Keyword!=null) {    
           Set<String> foundedList = new HashSet<>();
            for (Map.Entry<String, ArrayList> onPicture : PhotoEditor.getMapOfKeywords().entrySet()) {
                            for(int i=0;i<onPicture.getValue().size();i++){
                    if(onPicture.getValue().get(i).toString().contains(Keyword.toUpperCase()))
                    {
                        foundedList.add(onPicture.getKey());
                    }
                }   
            }
            listOfImagesPaths=foundedList.toArray(new String[0]);
        }
        
        for (String listOfImagesPath : listOfImagesPaths) {
            ImageView tmpImageView = new ImageView(PhotoEditor.prepareImagePath(listOfImagesPath));
            tmpImageView.setFitHeight(150);
            tmpImageView.setFitWidth(150);
            PhotoEditor.getMapOfKeywords().putIfAbsent(listOfImagesPath, new ArrayList());
            MapOfImages.put(listOfImagesPath, tmpImageView);
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
