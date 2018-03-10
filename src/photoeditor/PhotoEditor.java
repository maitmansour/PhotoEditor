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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Mohamed AIT MANSOUR <contact@numidea.com>
 */
public class PhotoEditor extends Application {

    private static String selectedPath;
    private static FindCertainExtension extentionAndFileFounder = new FindCertainExtension();
    private static Map < String, ArrayList > MapOfKeywords;
    private static final Alert alert = new Alert(Alert.AlertType.NONE);
    public static final String FILE_TEXT_EXT = ".jpg";
    public static Locale locale;
    public static NodeOrientation nodeOrientation;


    @Override
    public void start(Stage stage) throws Exception {
        setSelectedPath("");
        unFreezeKeywordsMap();
        unFreezeConfiguration();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"), getBundleByLocal());

        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        scene.setNodeOrientation(nodeOrientation);
        stage.setScene(scene);
        stage.show();
    }

    public void reload(Boolean orientation) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLHome.fxml"), getBundleByLocal());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        if (orientation) {
            nodeOrientation = NodeOrientation.RIGHT_TO_LEFT;
        } else {
            nodeOrientation = NodeOrientation.LEFT_TO_RIGHT;
        }
        scene.setNodeOrientation(nodeOrientation);
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
        if (PhotoEditor.extentionAndFileFounder == null) {
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
     * @param imageName 
     * @return  
     */
    public static String prepareImagePath(String imageName) {
        return "file:///" + PhotoEditor.getSelectedPath() + "\\" + imageName;
    }


    /**
     * ExtentionAndFileFounder Getter / Singleton
     * @return FindCertainExtension Instance
     * @throws java.lang.Exception
     */
    public static Map < String, ArrayList > getMapOfKeywords() throws Exception {
        if (PhotoEditor.MapOfKeywords == null) {
            PhotoEditor.MapOfKeywords = new HashMap < > ();
        }
        return PhotoEditor.MapOfKeywords;
    }


    /**
     * freeze keywords Map
     * @throws Exception 
     */
    public static void freezeKeywordsMap() throws Exception {
        FileOutputStream keywordsFile = new FileOutputStream("src/data/keywords.dat");
        try (ObjectOutputStream writer = new ObjectOutputStream(keywordsFile)) {
            writer.writeObject(getMapOfKeywords());
        }
    }


    /**
     * freeze keywords Map
     * @throws Exception 
     */
    public static void freezeConfiguration() throws Exception {
        try (PrintWriter writer = new PrintWriter("src/data/configuration.dat", "UTF-8")) {
            writer.print(locale.toString());
        }
    }

    /**
     * unfreeze keywords and fill MapOfKeywords
     * @return
     * @throws Exception 
     */
    public static boolean unFreezeKeywordsMap() throws Exception {
        File f = new File("src/data/keywords.dat");
        if (f.exists() && !f.isDirectory()) {
            FileInputStream keywordsFile = new FileInputStream("src/data/keywords.dat");
            try (ObjectInputStream reader = new ObjectInputStream(keywordsFile)) {
                MapOfKeywords = (Map < String, ArrayList > ) reader.readObject();
            }
            return true;
        }
        f.createNewFile();
        return false;
    }



    /**
     * unfreeze keywords and fill MapOfKeywords
     * @return
     * @throws Exception 
     */
    public static boolean unFreezeConfiguration() throws Exception {
        File f = new File("src/data/configuration.dat");

        if (f.exists() && !f.isDirectory()) {
            String savedLocal;
            try (Scanner in = new Scanner(new FileReader("src/data/configuration.dat"))) {
                savedLocal = in .next();
            }
            setLocal(savedLocal);
            System.out.println(savedLocal);
            if (savedLocal.contains("ar")) {
                nodeOrientation = NodeOrientation.RIGHT_TO_LEFT;
            } else {
                nodeOrientation = NodeOrientation.LEFT_TO_RIGHT;
            }
            return true;
        } else {
            locale = Locale.getDefault();
            nodeOrientation = NodeOrientation.LEFT_TO_RIGHT;
            freezeConfiguration();
        }

        return false;
    }

    public static Optional < ButtonType > alertBuilder(int message, AlertType type) {
        alert.setAlertType(type);
        alert.setTitle("PhotoEditor");
        alert.setHeaderText(null);

        if (message == 1) {
            alert.setContentText("Please Select a directory !");
        } else if (message == 2) {
            alert.setContentText("Please Select a directory that contains images !");
        } else if (message == 3) {
            alert.setContentText("Please a valid tag ! ");
        } else if (message == 4) {
            alert.setContentText("Language changed successfully ");
        } else if (message == 5) {
            alert.setContentText("Sorry, this Functionnality is under developpement !");
        } else if (message == 7) {
            alert.setContentText("Are you sure that you want to exit ?");
        } else if (message == 8) {
            alert.setContentText("Tags Updated successfully !");
        } else if (message == 9) {
            alert.setContentText("You are trying to switch to a language that is already applied !");
        }
        return alert.showAndWait();
    }

    /**
     * Stop stage and Freeze keywords
     * @throws Exception 
     */
    @Override
    public void stop() throws Exception {
        freezeKeywordsMap();
        freezeConfiguration();

    }

    static void setLocal(String loc) {
        if (loc == null) {
            locale = Locale.getDefault();
        } else {
            locale = new Locale(loc);
        }
    }
    static String getLocal() {

        return locale.toString();
    }
    static ResourceBundle getBundleByLocal() {
        return ResourceBundle.getBundle("bundles.lang", locale);
    }



}