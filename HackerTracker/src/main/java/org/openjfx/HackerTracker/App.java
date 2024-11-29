package org.openjfx.HackerTracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.openjfx.HackerTracker.App;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("third"));
//    	scene = new Scene(loadFXML("resizing"));
        stage.setScene(scene);
        stage.setTitle("HackerTracker");
        stage.setMinWidth(900);
        stage.setMinHeight(570);
//        stage.setWidth(scene.getWidth());
//        stage.setHeight(scene.getHeight());
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}