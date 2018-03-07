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
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
    private static Map < String, ArrayList > MapOfKeywords;

    @Override
    public void start(Stage stage) throws Exception {
        setSelectedPath("");
        //unFreezeMapOfKeywords();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResource("bundles/lang_en.properties").openStream();
        ResourceBundle bundle = new PropertyResourceBundle(inputStream);
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"), bundle);

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
     * @param selectedPath 
     */
    public static String prepareImagePath(String imageName) {
        return "file:///" + PhotoEditor.getSelectedPath() + "\\" + imageName;
    }


    /**
     * ExtentionAndFileFounder Getter / Singleton
     * @return FindCertainExtension Instance
     */
    public static Map < String, ArrayList > getMapOfKeywords() throws Exception {
        if (PhotoEditor.MapOfKeywords == null) {
           // if (!unFreezeMapOfKeywords()) {
                PhotoEditor.MapOfKeywords = new HashMap < > ();
            //}
        }
        return PhotoEditor.MapOfKeywords;
    }


    public static void freezeMapOfKeywords() throws Exception {
        FileOutputStream keywordsFile = new FileOutputStream("src/data/keywords.dat");
        ObjectOutputStream writer = new ObjectOutputStream(keywordsFile);
        writer.writeObject(getMapOfKeywords());
        writer.close();
    }

    public static boolean unFreezeMapOfKeywords() throws Exception {
        File f = new File("src/data/keywords.dat");
        if (f.exists() && !f.isDirectory()) {
            FileInputStream keywordsFile = new FileInputStream("src/data/keywords.dat");
            ObjectInputStream reader = new ObjectInputStream(keywordsFile);
            MapOfKeywords = ( Map < String, ArrayList >) reader.readObject();
            reader.close();
            return true;
        }
        f.createNewFile();
        return false;
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Stage is closing");
       // freezeMapOfKeywords();
    }

}