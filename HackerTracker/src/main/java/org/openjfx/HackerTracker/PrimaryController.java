package org.openjfx.HackerTracker;

import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

public class PrimaryController {
	
	private VBox selectedDay;
	private int questionIndex;

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
    private VBox Monday;

    @FXML
    private VBox Tuesday;
    
    @FXML
    private VBox Wednesday;
    
    @FXML
    private VBox Thursday;
    
    @FXML
    private VBox Friday;
    
    @FXML
    private VBox Saturday;
    
    @FXML
    private VBox Sunday;
    
    @FXML 
    private Text fullDate;
    
    @FXML
    public void addQuestion() {
        if (selectedDay != null) {
            Button newButton = new Button("Q. " + questionIndex);
            newButton.setWrapText(true);
            selectedDay.getChildren().add(newButton);
        }
    }
    
    @FXML
    public void setDate() {
    	LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy");
        String formattedDate = currentDate.format(formatter);
    	fullDate.setText(formattedDate);
    }
    
    
    public void setDayAndIndex(VBox day, int i) {
        this.selectedDay = day;
        this.questionIndex = i;
    }
    

    @FXML
    public void initialize() {
    	setDate();
    	
        ObservableList<String> options = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        difficultyChoiceBox.setItems(options);
        
     // Define the range and step size
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 180, 30, 1);
        timeSpinner.setValueFactory(valueFactory);
     
        
     // for now we manually enter the number of questions
        
        for(int i = 1; i <= 3; i++) {
        	setDayAndIndex(Monday, i);
        	addQuestion();
        }
        
        for(int i = 1; i <= 6; i++) {
        	setDayAndIndex(Wednesday, i);
        	addQuestion();
        }
        
        for(int i = 1; i <= 2; i++) {
        	setDayAndIndex(Sunday, i);
        	addQuestion();
        }
        
    }
    
    
    
    
    
    
}
