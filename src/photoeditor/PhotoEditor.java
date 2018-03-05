/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoeditor;

import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Mohamed AIT MANSOUR <contact@numidea.com>
 */
public class PhotoEditor extends Application {
    
    private static String selectedPath;
    private static FindCertainExtension extentionAndFileFounder = new FindCertainExtension();
    
    @Override
    public void start(Stage stage) throws Exception {
        setSelectedPath("");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResource("bundles/lang_en.properties").openStream();
        ResourceBundle bundle = new PropertyResourceBundle(inputStream);
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"),bundle);

        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * SelectedPath Getter
     * @return String Selected Path
     */
    public static String getSelectedPath() {
        return PhotoEditor.selectedPath;
    }
    
    /**
     * ExtentionAndFileFounder Getter / Singleton
     * @return FindCertainExtension Instance
     */
    public static FindCertainExtension getExtentionAndFileFounder() {
        if (PhotoEditor.extentionAndFileFounder==null) {
         PhotoEditor.extentionAndFileFounder = new FindCertainExtension();
        }
        return PhotoEditor.extentionAndFileFounder;
    }
   

    /**
     * SelectedPath Setter
     * @param selectedPath 
     */
    public static void setSelectedPath(String selectedPath) {
        PhotoEditor.selectedPath = selectedPath;
    }
    
    /**
     * Prepare image Path 
     * @param selectedPath 
     */
    public static String prepareImagePath(String imageName) {
        return "file:///"+PhotoEditor.getSelectedPath()+"\\"+imageName;
    }
    
}
