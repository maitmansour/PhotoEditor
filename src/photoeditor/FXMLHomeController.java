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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Mohamed AIT MANSOUR <contact@numidea.com>
 */
public class FXMLHomeController implements Initializable {

    /**
     * FXML Declarations
     */
    @FXML
    private ListView < String > picturesList;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Label pictureTitleName;
    @FXML
    private TextField SearchTagValue;
    @FXML
    private Label pictureTagsValue;
    @FXML
    private AnchorPane firstPane;
    @FXML
    private ImageView bigPicture;
    @FXML
    private ImageView peLogo;
    @FXML
    private Button homeUpdateBtn;

    /**
     * Ordinary declarations
     */
    private Map < String, ImageView > MapOfImages;


    @FXML
    private void chooseExit(ActionEvent event) {
        Optional < ButtonType > result = PhotoEditor.alertBuilder(7, Alert.AlertType.CONFIRMATION);
        if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
            Platform.exit();
        }
    }

    @FXML
    private void aboutUs(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle("PhotoEditor");
        alert.setGraphic(peLogo);
        alert.setHeaderText(null);
        alert.setContentText("PhotoEditor V 1.0.0 \nMIT Licence ");
        alert.showAndWait();
    }
    @FXML
    private void chooseAnotherDirectory(ActionEvent event) throws Exception {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory == null) {
            PhotoEditor.alertBuilder(1, Alert.AlertType.WARNING);
        } else {
            String path = selectedDirectory.getAbsolutePath();
            PhotoEditor.setSelectedPath(path);
            if (PhotoEditor.getExtentionAndFileFounder().checkFileExistence(path, PhotoEditor.FILE_TEXT_EXT)) {
                fillListViewWorker(null);

            } else {
                PhotoEditor.alertBuilder(2, Alert.AlertType.WARNING);
            }
        }
    }
    @FXML
    private void findByTagHandler(ActionEvent event) throws Exception {
        if (SearchTagValue.getText().equals("")) {
            fillListViewWorker(null);
        } else {
            fillListViewWorker(SearchTagValue.getText());
        }



    }

    @FXML
    public void onEnter(ActionEvent ae) throws Exception {
        findByTagHandler(ae);
    }

    private void selectItem() throws Exception {
        String currentPicture = picturesList.getSelectionModel().getSelectedItem();
        FXMLUpdatePictureController.tmpPicture = currentPicture;
        bigPicture.setImage(MapOfImages.get(currentPicture).getImage());
        bigPicture.setFitHeight(300);
        bigPicture.setFitWidth(300);
        bigPicture.setPreserveRatio(false);
        ArrayList < String > currentPictureKeywords = PhotoEditor.getMapOfKeywords().get(currentPicture);
        pictureTitleName.setText(currentPicture);
        StringBuilder sb = new StringBuilder();
        currentPictureKeywords.stream().map((s) -> {
            sb.append(s);
            return s;
        }).forEach((_item) -> {
            sb.append(",");
        });
        FXMLUpdatePictureController.tmpTitle = currentPicture;
        FXMLUpdatePictureController.tmpTags = sb.toString();
        pictureTagsValue.setText(sb.toString());
    }

    /**
     * Fill the Big Picture Frame with clicked picture
     * @param event 
     */
    @FXML
    private void clickOnItemHandler(MouseEvent event) throws Exception {
        selectItem();
    }

    /**
     * Fill the Big Picture Frame with selected picture
     * @param event 
     */
    @FXML
    private void arrowsOnItemHandler(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
            selectItem();

        }
    }

    @FXML
    private void onlineHelp(ActionEvent event) throws IOException, URISyntaxException {
        if (Desktop.isDesktopSupported()) {
            PhotoEditor.alertBuilder(6, Alert.AlertType.INFORMATION);
            Desktop.getDesktop().browse(new URI("https://github.com/maitmansour95/PhotoEditor"));
        }else{
            PhotoEditor.alertBuilder(10, Alert.AlertType.WARNING);
        }
    }
    /**
     * Alert if functionnality is under dev.
     * @param event 
     */
    @FXML
    private void undefinedFunctionnality(MouseEvent event) throws Exception {
        PhotoEditor.alertBuilder(5, Alert.AlertType.ERROR);
    }


    /**
     * Update View Language
     * @param event 
     */
    @FXML
    private void langChoosed(ActionEvent event) throws IOException {

        String lang = event.getSource().toString().subSequence(12, 14).toString();
        if (lang.equals(PhotoEditor.getLocal())) {
            PhotoEditor.alertBuilder(9, Alert.AlertType.WARNING);
        } else {
            PhotoEditor.setLocal(lang);
            Stage app_stage = (Stage) firstPane.getScene().getWindow();
            app_stage.close();
            PhotoEditor reload = new PhotoEditor();
            if ("ar".equals(lang)) {
                reload.reload(true);
            } else {
                reload.reload(false);

            }
            PhotoEditor.alertBuilder(4, Alert.AlertType.INFORMATION);

        }
    }

    /**
     * When Update Button clicked
     * @param event
     * @throws IOException 
     */
    @FXML
    private void updateHandler(ActionEvent event) throws IOException {
        homeUpdateBtn.getParent().getParent().setDisable(true);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLUpdatePicture.fxml"), PhotoEditor.getBundleByLocal());
        Parent rootWindow = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(rootWindow);
        scene.setNodeOrientation(PhotoEditor.nodeOrientation);
        stage.setScene(scene);
        stage.showAndWait();
        pictureTagsValue.setText(FXMLUpdatePictureController.tmpTags);
        homeUpdateBtn.getParent().getParent().setDisable(false);
    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            homeUpdateBtn.setDisable(true);
            MapOfImages = new HashMap < > ();
            fillListViewWorker(null);
            

        } catch (Exception ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public Task createWorker(String Keyword) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                /**
                 * fill Listview
                 */
                picturesList.getItems().removeAll();
                String[] listOfImagesPaths = PhotoEditor.getExtentionAndFileFounder().getFilesList(PhotoEditor.getSelectedPath(), ".jpg");


                if (Keyword != null) {
                    Set < String > foundedList = new HashSet < > ();
                    for (Map.Entry < String, ArrayList > onPicture: PhotoEditor.getMapOfKeywords().entrySet()) {
                        for (int i = 0; i < onPicture.getValue().size(); i++) {
                            if (onPicture.getValue().get(i).toString().contains(Keyword.toUpperCase()) && Arrays.asList(listOfImagesPaths).contains(onPicture.getKey())) {
                                foundedList.add(onPicture.getKey());
                            }
                        }
                    }
                    listOfImagesPaths = foundedList.toArray(new String[0]);
                }
                
                if (listOfImagesPaths.length>0) {
                double step = 1.000 / listOfImagesPaths.length;
                double progress = step;
                for (String listOfImagesPath: listOfImagesPaths) {
                    updateProgress(progress, 0.999);
                    progress += step;
                    ImageView tmpImageView = new ImageView(PhotoEditor.prepareImagePath(listOfImagesPath));
                    tmpImageView.setFitHeight(150);
                    tmpImageView.setFitWidth(150);
                    PhotoEditor.getMapOfKeywords().putIfAbsent(listOfImagesPath, new ArrayList());
                    MapOfImages.put(listOfImagesPath, tmpImageView);
                }
                ObservableList < String > items = FXCollections.observableArrayList(
                    listOfImagesPaths);
                                return items;

                }
                      updateProgress(1, 1);
  
                this.cancel();
                return null;

            }
        };
    }

    private void fillListViewWorker(String Keyword) {
        Task fillListView = createWorker(Keyword);
                       

        fillListView.setOnCancelled(new EventHandler < WorkerStateEvent > () {
            @Override
            public void handle(WorkerStateEvent event) {
                PhotoEditor.alertBuilder(3, Alert.AlertType.WARNING);
                SearchTagValue.setText("");
                        }
        });
        fillListView.setOnSucceeded(new EventHandler < WorkerStateEvent > () {
            @Override
            public void handle(WorkerStateEvent event) {
                picturesList.setItems((ObservableList < String > ) fillListView.getValue());
                picturesList.setCellFactory(new Callback < ListView < String > , ListCell < String >> () {

                    @Override
                    public ListCell < String > call(ListView < String > param) {
                        return new ListCell < String > () {
                            @Override
                            public void updateItem(String name, boolean empty) {
                                super.updateItem(name, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(MapOfImages.get(name));
                                }
                            }
                        };
                    }
                });

                                   picturesList.getSelectionModel().selectFirst();
        try {
            selectItem();
            if (homeUpdateBtn.isDisabled()) {
            homeUpdateBtn.setDisable(false);
            }
        } catch (Exception ex) {
            Logger.getLogger(FXMLHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        });

            progressIndicator.progressProperty().unbind();
            progressIndicator.progressProperty().bind(fillListView.progressProperty());
            progressIndicator.progressProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue.doubleValue() >= 1.0) {
                    Text doneText = (Text) progressIndicator.lookup(".percentage");
                    doneText.setText(PhotoEditor.getBundleByLocal().getString("done"));
                }
            });


          
        new Thread(fillListView).start();
    }

}