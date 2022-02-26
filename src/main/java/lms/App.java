package lms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


/**
 * JavaFX App
 */
public class App extends Application {

    // private static Scene scene = new Scene(null);

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        Parent root = FXMLLoader.load(App.class.getResource("fxml/login.fxml"));
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Loan Management System");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    // static void setRoot(String fxml) throws IOException {
    //     scene.setRoot(loadFXML(fxml));
    //     scene.getStylesheets().add(App.class.getResource("css/main.css").toExternalForm());
    // }

    // private static Parent loadFXML(String fxml) throws IOException {
    //     FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/"+ fxml + ".fxml"));
    //     return fxmlLoader.load();
    // }

    public static URL loadFxml(String fxmlName)
    {
        URL fileUrl = App.class.getResource("fxml/" + fxmlName + ".fxml");
        return fileUrl;
        
    }

    public static URL loadDb()
    {
        URL dbUrl = App.class.getResource("db/dblms.db");
        return dbUrl;
    }


    public static void main(String[] args) {
        launch();
    }

}