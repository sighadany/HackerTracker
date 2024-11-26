package org.openjfx.HackerTracker;

import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSettings() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    private void printHello(MouseEvent event) throws IOException {
    	System.out.println("Hello!");
    }
    
    @FXML
    private ChoiceBox<String> difficultyChoiceBox;
    
    @FXML
    private Spinner<Integer> timeSpinner;
    
    @FXML
    private VBox mondayButtons;

    public void test(){
        System.out.println("Testing");
    }

    @FXML
    public void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        difficultyChoiceBox.setItems(options);
        
     // Define the range and step size
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 180, 30, 1);
        timeSpinner.setValueFactory(valueFactory);
        
        Button Q1 = new Button("Q1");
        mondayButtons.getChildren().add(Q1);
        
        Button Q2 = new Button("Q1");
        mondayButtons.getChildren().add(Q2);
    }
    
}
