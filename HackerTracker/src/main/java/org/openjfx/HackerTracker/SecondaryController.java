package org.openjfx.HackerTracker;

import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

public class SecondaryController {
	
	private VBox selectedDay;
	private int questionIndex;
	private final Map<String, Integer> questionsPerDay = new HashMap<>();

	
    @FXML
    private void printHello(MouseEvent event) throws IOException {
    	System.out.println("Hello!");
    }
    
    @FXML
    private ChoiceBox<String> difficultyChoiceBox;
    
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
    private void goToHomePage() throws IOException {
        App.setRoot("primary");
    }

    
    @FXML
    public void setDate() {
    	LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy");
        String formattedDate = currentDate.format(formatter);
    	fullDate.setText(formattedDate);
    }
    
    @FXML
    private void handleDayClick(MouseEvent event) {
        VBox clickedDay = (VBox) event.getSource(); // Identify the clicked day
        selectedDay = clickedDay; // Set as the active day
        questionIndex = selectedDay.getChildren().size() + 1; // Update question index
        loadQuestionsForSelectedDay();

        // Reset all days to default background color and borders
        resetDayStyles();

        // Highlight the selected day while retaining the border
        clickedDay.setStyle("-fx-background-color: lightgray; -fx-border-color: black; -fx-border-width: 1;");

    }
    
    private void resetDayStyles() {
        String defaultStyle = "-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1;";
        Monday.setStyle(defaultStyle);
        Tuesday.setStyle(defaultStyle);
        Wednesday.setStyle(defaultStyle);
        Thursday.setStyle(defaultStyle);
        Friday.setStyle(defaultStyle);
        Saturday.setStyle(defaultStyle);
        Sunday.setStyle(defaultStyle);
    }

    private void updateQuestionsForSelectedDay(int newCount) {
        if (selectedDay == null) return;

        int currentCount = selectedDay.getChildren().size();
        questionsPerDay.put(selectedDay.getId(), newCount);

        // Add or remove buttons dynamically
        if (newCount > currentCount) {
            for (int i = currentCount + 1; i <= newCount; i++) {
                Button newButton = new Button("Q. " + i);
                newButton.setWrapText(true);
                selectedDay.getChildren().add(newButton);
            }
        } else if (newCount < currentCount) {
            selectedDay.getChildren().remove(newCount, currentCount);
        }
    }
    
    private void loadQuestionsForSelectedDay() {
        int count = questionsPerDay.getOrDefault(selectedDay.getId(), 0);
        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, count);
        numberOfQuestions.setValueFactory(factory);
    }
    
    public void setDayAndIndex(VBox day, int i) {
        this.selectedDay = day;
        this.questionIndex = i;
    }
    
    @FXML
    private Spinner<Integer> numberOfQuestions;
    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
    
    @FXML
    public void initialize() {
    	setDate();
    	numberOfQuestions.setValueFactory(svf);
        ObservableList<String> options = FXCollections.observableArrayList("Arrays", "Two Pointers", "Sliding Window", "Matrix", "Hashmap", "Intervals", "Stack", "Linked List", "Binary Tree General", "Binary Tree BFS","Binary Search Tree", "Graph General", "Backtracking", "Divide & Conquer", "Kadane's Algorithm", "Binary Search", "Heap", "Bit Manipulation", "Math", "1D DP", "Multidimensional DP");
        difficultyChoiceBox.setItems(options);
        
     // Define the range and step size
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 180, 30, 1);
     
        numberOfQuestions.valueProperty().addListener((obs, oldValue, newValue) -> {
            updateQuestionsForSelectedDay(newValue);
        });
        
    }
}
