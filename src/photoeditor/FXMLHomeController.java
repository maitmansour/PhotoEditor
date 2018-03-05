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
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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
    
    
    @FXML
    private ListView<String> picturesList;

    
    
    @FXML
    private void langFrChoosed(ActionEvent event) {
        loadLang("fr");
    }
    
    @FXML
    private void langEnChoosed(ActionEvent event) {
        loadLang("en");
    }
    @FXML
    private void langArChoosed(ActionEvent event) {
        loadLang("ar");
    }

    @FXML
    private void updateHandler(ActionEvent event)  throws IOException  {
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
        // TODO
        Image IMAGE_RUBY  = new Image("https://upload.wikimedia.org/wikipedia/commons/f/f1/Ruby_logo_64x64.png");
     Image IMAGE_APPLE  = new Image("http://findicons.com/files/icons/832/social_and_web/64/apple.png");
     Image IMAGE_VISTA  = new Image("http://antaki.ca/bloom/img/windows_64x64.png");
     Image IMAGE_TWITTER = new Image("http://files.softicons.com/download/social-media-icons/fresh-social-media-icons-by-creative-nerds/png/64x64/twitter-bird.png");

     Image[] listOfImages = {IMAGE_RUBY, IMAGE_APPLE, IMAGE_VISTA, IMAGE_TWITTER};

        ObservableList<String> items =FXCollections.observableArrayList (
                "RUBY", "APPLE", "VISTA", "TWITTER");
        picturesList.setItems(items);

        picturesList.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(name.equals("RUBY"))
                        imageView.setImage(listOfImages[0]);
                    else if(name.equals("APPLE"))
                        imageView.setImage(listOfImages[1]);
                    else if(name.equals("VISTA"))
                        imageView.setImage(listOfImages[2]);
                    else if(name.equals("TWITTER"))
                        imageView.setImage(listOfImages[3]);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
    
    }    
    
    
    private void loadLang(String lang){
        // TODO : Complete internationnalization
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("bundles.lang", locale);
        titleLabel.setText(bundle.getString("titleLabel"));
        
    }
    
}
