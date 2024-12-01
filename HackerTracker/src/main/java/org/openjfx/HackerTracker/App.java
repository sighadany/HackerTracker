package org.openjfx.HackerTracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.openjfx.HackerTracker.App;
//import org.openjfx.HackerTracker.PrimaryController.ShowProblemDetails;

/**
 * JavaFX App with a calendar based view that shows details about 
 * leetcode questions on the right hand side. The view is interactive, 
 * allowing users to select questions and update information about them.
 * 
 * @author Dany Sigha
 * @version 1.0
 */
public class App extends Application {

    private static Scene scene;
    
    /**
     * Initializes the JavaFX application with a window and its dimensions, some content, and a title
     *
     * @throws IOException if the FXML file cannot be read
     * @param stage the top level container or the "window" in which the application runs
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.setTitle("HackerTracker");
        stage.setMinWidth(900);
        stage.setMinHeight(570);
        stage.show();
    }

    /**
     * Sets the content that is held within the stage
     *
     * @throws IOException if the FXML file cannot be read
     * @param fxml the name of the fxml file that contains content to show in the app window
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    /**
     * load and initialize an FXML file into the JavaFX application
     *
     * @throws IOException if the FXML file cannot be read
     * @param fxml the name of the fxml file that contains content to show in the app window
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    /**
     * Starts the JavaFX application by creating a JavaFX Application Thread and a JavaFX Event Loop
     */
    public static void main(String[] args) {
        launch();
    }

}