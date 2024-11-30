package org.openjfx.HackerTracker;

import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
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

/**
 * Dynamically updates the appearance of the settings view. Updates the calendar
 * with leetcode questions based on a schedule defined by the user.
 * 
 * Provides a spinner to let the user define how many questions to schedule on one
 * day
 * 
 * @author Kavin Jha, Dany Sigha
 * @version 1.0
 */
public class SecondaryController {
	
	private VBox selectedDay;
	private int questionIndex;
//	private Map<String, Integer> questionsPerDay = new HashMap<>();
	private final Scheduler SHARED_DATA = Scheduler.getInstance();
    
    @FXML
    private Spinner<Integer> numberOfQuestions;
    
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
    
    /**
     * Add question button to the selected day
     */
    @FXML
    public void addQuestion() {
        if (selectedDay != null) {
            Button newButton = new Button("Q. " + questionIndex);
            newButton.setWrapText(true);
            selectedDay.getChildren().add(newButton);
        }
    }
    
    /**
     * Switches from the settings view to the main view
     *
     * @throws IOException if the FXML file cannot be read
     */
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("primary");
    }
    
    /**
     * Switches from the settings view to the progress view
     *
     * @throws IOException if the FXML file cannot be read
     */
    @FXML
    private void switchToProgress() throws IOException {
        App.setRoot("third");
    }

    // Call this when switching *back* to the settings view
//    public void refreshSettingsView() {
//        refreshDayViews(); // Refresh the UI with the shared state
//    }


    /**
     * Sets the current date on the right side of the view
     */
    @FXML
    public void setDate() {
    	LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy");
        String formattedDate = currentDate.format(formatter);
    	fullDate.setText(formattedDate);
    }
    
    /**
     * Upon clicking on a day, update the spinner on the right with the correct number of questions
     * 
     * @param event the event triggered by clicking the calendar
     * @see SecondaryController#loadQuestionsForSelectedDay()
     * @see SecondaryController#resetDayStyles()
     */
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
    
    /**
     * Set the default style on all days of the calendar
     */
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
    
    /**
     * Upon updating the spinner value (the number of questions) on the right 
     * the function adds / removes and schedules / marks unscheduled questions
     * 
     * @param newCount the new number of question for the selected day
     * @see Scheduler#scheduleNextProblem
     * @see Scheduler#unScheduleProblem
     */
    @FXML
    private void updateQuestionsForSelectedDay(int newCount) {
        if (selectedDay == null) return;
        
        if (newCount > 10) return;

        String dayId = selectedDay.getId().substring(0, 3);
        int currentCount = selectedDay.getChildren().size();
        
        // add a question id
        
        
        if (newCount > currentCount) {
        	
            for (int i = currentCount + 1; i <= newCount; i++) {
            	int status = SHARED_DATA.scheduleNextProblem(dayId);
            	
            	if (status == 0) {
            		Button newButton = new Button("Q. " + i);
                    selectedDay.getChildren().add(newButton);
            	}
                
            }
        } else if (newCount < currentCount) {
        	
        	for (int i = newCount; i < currentCount; i++) {
        		SHARED_DATA.unScheduledProblem(dayId);
        	}
        	
            selectedDay.getChildren().remove(newCount, currentCount);
        }
    }
    
    /**
     * Upon clicking on a specific day on the calendar update the value
     * shown in the spinner on the right hand side of the view
     * 
     * @see Scheduler
     */
    private void loadQuestionsForSelectedDay() {
        if (selectedDay == null) return;

        String dayId = selectedDay.getId().substring(0, 3);
        int count = SHARED_DATA.getQuestionsPerDay().get(dayId).size();

        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, count);
        numberOfQuestions.setValueFactory(factory);
    }
    
    /**
     * Upon loading the settings view, set the date, and load the schedule, and track user input for number of questions per day.
     * 
     * @see Scheduler
     * @see SecondaryController#setDate()
     * @see SecondaryController#refreshDayViews()
     */
    @FXML
    public void initialize() {
        setDate();
        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        numberOfQuestions.setValueFactory(svf);

        // Populate all days' UI based on the shared state
        refreshDayViews();

        // Add listener to the Spinner for real-time updates
        numberOfQuestions.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (selectedDay != null) {
                updateQuestionsForSelectedDay(newValue); // Update UI dynamically
            }
        });
    }
    
    /**
     * Based on the Scheduler, set the calendar view of the settings page
     * 
     * @see Scheduler
     */
    private void refreshDayViews() {
        for (VBox day : List.of(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday)) {
            String dayId = day.getId().substring(0, 3);
            
            int questionCount = SHARED_DATA.getQuestionsPerDay().get(dayId).size();

            day.getChildren().clear(); // Clear current buttons

            for (int i = 1; i <= questionCount; i++) {
                Button newButton = new Button("Q. " + i);
                newButton.setWrapText(true);
                day.getChildren().add(newButton);
            }
        }
    }


}
