package org.openjfx.HackerTracker;

import java.io.IOException;

//import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;


import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private int numberOfMondayButtons;
    @FXML
    private VBox mondayButtons;
    
    @FXML 
    private int numberOfTuesdayButtons;
    @FXML 
    private VBox tuesdayButtons;
    
    @FXML
    private Label questionTitle;

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
        
        numberOfMondayButtons = 5;
        for(int i=1; i<=numberOfMondayButtons; i++) {
        	Button problemTile = new Button("Q"+i);
        	problemTile.setOnAction(new ShowProblemDetails());
            mondayButtons.getChildren().add(problemTile);
        }
        
        numberOfTuesdayButtons = 3;
        for(int i=1; i<=numberOfTuesdayButtons; i++) {
        	tuesdayButtons.getChildren().add(new Button("Q"+i));
        }
        
//        MongoClient client = new MongoClient("mongodb+srv://admin:admin@hackertracker.1ovmp.mongodb.net/?retryWrites=true&w=majority&appName=HackerTracker");
//        MongoDatabase db = client.getDatabase("HackerTracker");
//        MongoCollection<Document> problems = db.getCollection("problems");
        MongoClient client = MongoClients.create("mongodb+srv://admin:admin@hackertracker.1ovmp.mongodb.net/?retryWrites=true&w=majority&appName=HackerTracker");
        MongoDatabase db = client.getDatabase("HackerTracker");
        MongoCollection<Document> problems = db.getCollection("problems");
        Document doc = problems.find().first();
        System.out.println(doc.toJson());
    }
    
    class ShowProblemDetails implements EventHandler<ActionEvent>{
    	@Override
    	public void handle(ActionEvent event) {
    		questionTitle.setText("Problem title 1");
    	}
    }
    
}
