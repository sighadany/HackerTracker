package org.openjfx.HackerTracker;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.awt.Desktop;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextArea;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.geometry.Pos;

/**
 * Dynamically updates the appearance of the main view. Updates the calendar
 * with leetcode questions based on a schedule defined by the user.
 * 
 * Provides a view for detailed information about scheduled leetcode questions. 
 * This information appears dynamically as questions are selected on the calendar.
 * 
 * @author Dany Sigha, Sarthak Mallick, Kavin Jha
 * @version 1.0
 */
public class PrimaryController {
	
    public HashMap<Integer, Problem> problemMap = new HashMap<Integer, Problem>();
    public HashMap<String, List<Integer>> problemSchedule;
    private final Scheduler SHARED_DATA = Scheduler.getInstance();
    private Problem selectedProblem;
    private Button selectedProblemButton;
    
    /**
     * Switches from the main view to the settings view
     *
     * @throws IOException if the FXML file cannot be read
     */
    @FXML
    private void switchToSettings() throws IOException {
        App.setRoot("secondary");
    }
    
    /**
     * Switches from the main view to the progress view
     *
     * @throws IOException if the FXML file cannot be read
     */
    @FXML
    private void switchToProgress() throws IOException {
        App.setRoot("third");
    }
    
    
    @FXML
    private ChoiceBox<String> difficultyChoiceBox; // User defined difficulty out of 10
    
    @FXML
    private Spinner<Integer> timeSpinner; // Time spent on question defined by user
    
    @FXML
    private VBox detailsVBox; // Right hand side container of the main view
    
    @FXML 
    private Button toggleSubtopics; // Show / Hide subtopics
    
    @FXML
    private VBox Monday; // Week day component in the calendar

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
    private Text fullDate; // Current date
    
    @FXML
    private Label questionTitle; // Question title
    
    @FXML
    private CheckBox questionCompleted; // Question completion status
    
    @FXML
    private Label questionTopic; // Topic of the question
    
    @FXML
    private Label questionDifficulty; // Leetcode provided difficulty tag
    
    @FXML
    private Hyperlink hyperLink; // Link to leetcode question
    
    @FXML
    private TextArea userNotes; // Notes field
    
    /**
     * Displays subtopics as hints for the patterns to adopt to solve the question
     *
     * @param event an event object that contains information about the object that fired the event
     * @see ShowProblemDetails Displays question details on the right side of the main view
     */
    @FXML
    private void toggleSubtopics(ActionEvent event) {
        
        if (selectedProblem != null) {
        	
        	// Get the button that was clicked
            Button sourceButton = (Button) event.getSource();

            // Get the HBox containing the button
            HBox currentHBox = (HBox) sourceButton.getParent();

            // Find the index of the current HBox in the VBox
            int currentIndex = detailsVBox.getChildren().indexOf(currentHBox);
            
        	
        	if (
        			currentIndex + 1 < detailsVBox.getChildren().size() &&
        			detailsVBox.getChildren().get(currentIndex + 1) instanceof HBox &&
        			detailsVBox.getChildren().get(currentIndex + 1).getId() != null &&
        			detailsVBox.getChildren().get(currentIndex + 1).getId().equals("toggleTags")) {

                    // Remove the HBox with subtopics if it's already present
        		    detailsVBox.getChildren().remove(currentIndex + 1);
        		    toggleSubtopics.setText("Show subtopics");
            } else {
            	toggleSubtopics.setText("Hide subtopics");
            	// Create a new HBox with subtopics if it is not already there
                HBox newHBox = new HBox(10); // 10 is spacing
                newHBox.setAlignment(Pos.TOP_CENTER);
                newHBox.setPrefHeight(40);
                newHBox.setId("toggleTags");
                
            	newHBox.getChildren().add(new Label(selectedProblem.getTag()));
                
                // Insert the new HBox below the current HBox
            	detailsVBox.getChildren().add(currentIndex + 1, newHBox);
            }
        	
        }
        
    }
    
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
     * Upon loading the main view, set the date, load the leetcode questions, display the schedule,
     * monitor user input, and initialize the values of the difficulty and time taken inputs fields.
     * 
     * @see Scheduler
     * @see PrimaryController#loadJsonProblems() Reads and processes LeetCode problems and their schedule from a JSON file
     * @see PrimaryController#addQuestionsToCalendar() Reads from the problemSchedule object to retrieve the schedule set by the user
     * @see PrimaryController#addListenersToFields() Adds listeners to various UI fields to handle user interactions
     */
    @FXML
    public void initialize() {
    	setDate();
    	
    	if (SHARED_DATA.getProblemMapping().size() == 0) {
    		loadJsonProblemsAndSchedule(); // get the problems and the schedule from the json file on program startup
    	}else {
    		// get the problems and the schedule from shared object when switching between tabs
    		problemSchedule = SHARED_DATA.getQuestionsPerDay(); 
    		problemMap = SHARED_DATA.getProblemMapping();
    	}
    	
    	addQuestionsToCalendar(); // add elements to the calendar based on schedule
    	
    	// initialize the options of the difficultyChoiceBox
        ObservableList<String> options = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        difficultyChoiceBox.setItems(options);
        
        // monitor user click of the link to open it default browser
        hyperLink.setOnAction(event -> {
            try {
                // Get the URL from the Hyperlink text
                String url = hyperLink.getText();
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url; // Add protocol if missing
                }
                // Open the URL in the default web browser
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        // Define the range and step size of the spinner for the time taken on a question
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 180, 30, 1);
        timeSpinner.setValueFactory(valueFactory);
        
        // monitor user input and store new information
        addListenersToFields();
    }
    
    /**
     * Reads from the problemSchedule object to retrieve the schedule set by the user and populate the calendar
     * 
     * @see Scheduler A class that stores the questions and the question schedule
     */
    private void addQuestionsToCalendar() {
        updateDayUI(Monday, "Mon", problemSchedule.get("Mon").size(), problemSchedule.get("Mon"));
        updateDayUI(Tuesday, "Tue", problemSchedule.get("Tue").size(), problemSchedule.get("Tue"));
        updateDayUI(Wednesday, "Wed", problemSchedule.get("Wed").size(), problemSchedule.get("Wed"));
        updateDayUI(Thursday, "Thu", problemSchedule.get("Thu").size(), problemSchedule.get("Thu"));
        updateDayUI(Friday, "Fri", problemSchedule.get("Fri").size(), problemSchedule.get("Fri"));
        updateDayUI(Saturday, "Sat", problemSchedule.get("Sat").size(), problemSchedule.get("Sat"));
        updateDayUI(Sunday, "Sun", problemSchedule.get("Sun").size(), problemSchedule.get("Sun"));
    }
    
    /**
     * Applies a completed style to the provided question button.
     * Updates the button's appearance to visually indicate the question is completed.
     *
     * @param questionButton the button to which the completed style will be applied
     *                       (e.g., changes opacity, background color, text color, and other styles)
     */
    private void setQuestionCompletedStyle(Button questionButton) {
    	questionButton.setStyle(
        	    "-fx-background-color: #3498db; " +  // Background color
        	    "-fx-text-fill: white; " +           // Text color
        	    "-fx-background-radius: 15; " +      // Rounded corners
        	    "-fx-padding: 10; " +                // Padding
        	    "-fx-font-family: Arial; " +         // Font family
        	    "-fx-font-weight: bold; " +          // Bold text
        	    "-fx-opacity: 0.5;"                  // Less opaque to indicate completion
        	);
    }
    
    /**
     * Resets the provided question button to its default style.
     * Ensures a consistent appearance for buttons that are not marked as completed.
     *
     * @param questionButton the button to which the default style will be applied
     *                       (e.g., restores full opacity and predefined visual properties)
     */
    private void setQuestionDefaultStyle(Button questionButton) {
    	// Apply custom styling
        questionButton.setStyle(
        	    "-fx-background-color: #3498db; " +  // Background color
        	    "-fx-text-fill: white; " +           // Text color
        	    "-fx-background-radius: 15; " +      // Rounded corners
        	    "-fx-padding: 10; " +                // Padding
        	    "-fx-font-family: Arial; " +         // Font family
        	    "-fx-font-weight: bold;"             // Bold text
        	);
    }
    
    /**
     * Adds question buttons to a day of the week in the calendar.
     *
     * @param dayBox a Vertical Box that represents a day of the week in the UI
     * @param day a String that contains a day of the week
     * @param questionCount the number of questions to add on a specified day
     * @see ShowProblemDetails Displays question details on the right side of the main view
     */
    private void updateDayUI(VBox dayBox, String day, int questionCount, List<Integer> problems) {
    	
        dayBox.getChildren().clear(); // Clear all questions on the calendar
        
        for (int i = 1; i <= questionCount; i++) { 
            Button questionButton = new Button("Q. " + i); // create question button
            
            // questionButton.setWrapText(true);
            
            setQuestionDefaultStyle(questionButton); // default styling of a question button
            
            questionButton.setUserData(day+":"+i); // set question button identifier
            Problem problem = problemMap.get(problems.get(i-1));
            
            if(problem.getIsCompleted()) { // change question style to indicate it is completed
            	setQuestionCompletedStyle(questionButton);
            }
            
            questionButton.setOnAction(new ShowProblemDetails()); // show question details when the question is selected
            dayBox.getChildren().add(questionButton); // add question to the calendar
        }
    }
    
    /**
     * Listener that updates the calendar view to reflect the completion status of a question.
     * Reduces the opacity of the associated button when the question is marked as completed.
     *
     * @param observable the value being monitored for changes (e.g., button click state)
     * @param oldValue the button's completion status before the change
     * @param newValue the button's completion status after the change
     * @see Scheduler#updateProblemCompletionStatus Updates tally of completed and non completed problems
     */
    private final ChangeListener<Boolean> questionCompletedListener = (observable, oldValue, newValue) -> {
        if (selectedProblem != null) {
            SHARED_DATA.updateProblemCompletionStatus(selectedProblem);
            
            if (selectedProblemButton != null) {
            	if(selectedProblem.getIsCompleted()) {
            		setQuestionCompletedStyle(selectedProblemButton);
                } else {
                	setQuestionDefaultStyle(selectedProblemButton);
                }
            }
        }
    };

    /**
     * Adds listeners to various UI fields to handle user interactions and update
     * the corresponding properties of the selected problem.
     * 
     * Ensures that changes made through the UI are reflected in the underlying data model.
     */
    private void addListenersToFields() {
        // Update difficulty rating when changed
        difficultyChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedProblem != null && newValue != null) {
                selectedProblem.setDifficultyRating(Integer.parseInt(newValue));
            }
        });
        // Update time spent when the spinner value changes
        timeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedProblem != null) {
                selectedProblem.setTimeSpentOnQuestion(newValue);
            }
        });
        // Update completion status
        questionCompleted.selectedProperty().addListener(questionCompletedListener);

        // Update notes field
        userNotes.textProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedProblem != null) {
                selectedProblem.setNotes(newValue);
            }
        });
    }
    
    
    /**
     * Reads and processes LeetCode problems and their schedule from a JSON file.
     * 
     * This method parses a JSON file containing a list of LeetCode problems and a weekly schedule,
     * then updates the shared data model with the parsed information. 
     * 
     * - Extracts the current week number and updates the schedule week.
     * - Parses and maps daily question IDs to a schedule.
     * - Converts problem details into `Problem` objects and populates a problem mapping.
     * 
     * Updates the shared data store to reflect the loaded problems and schedule.
     *
     * @throws RuntimeException if the JSON file cannot be read or parsed
     * @see Problem Represents individual LeetCode problems.
     * @see Scheduler#getQuestionsPerDay Retrieves the current schedule
     * @see Scheduler#setQuestionsPerDay(HashMap<String, List<Integer>>) Updates the daily schedule.
     * @see Scheduler#setProblemMapping(HashMap<Integer, Problem>) Maps problem IDs to `Problem` objects.
     * @see Scheduler#setScheduleWeekNumber(int) Sets the current week number for the schedule.
     * @see Schedule#updateSchedule() Synchronizes the schedule with the current week.
     */
    private void loadJsonProblemsAndSchedule() {
        try {
    		byte[] json = Files.readAllBytes(Paths.get("../leetcode_problems.json"));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(json);
                        
            int weekNumber = jsonNode.get("week_number").asInt(); // retrieve the week number of the schedule
            
            problemSchedule = SHARED_DATA.getQuestionsPerDay();
            
            JsonNode storedSchedule = jsonNode.get("schedule");
            
         // get the schedule from the json file
            for(String day : new String[] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"}) {
            	JsonNode dayNode = storedSchedule.get(day);
            	String[] questionIds = (dayNode != null && dayNode.isArray() && dayNode.size() > 0) 
                        ? dayNode.toString().replace("[", "").replace("]", "").trim().split(",")
                        : new String[0]; // Return an empty array if it's an empty list or non-existent
            	List<Integer> integerList = Arrays.stream(questionIds).map(s -> Integer.parseInt(s) ).collect(Collectors.toList());            	
            	problemSchedule.put(day, integerList); // store the schedule in the json
            		            	
            }
            
         // get the leetcode questions from the json file
            for (JsonNode p : jsonNode.get("problems")) {
            	int problemId = p.get("Problem_id").asInt();
            	Problem problem = new Problem(problemId, 0 , p.get("topic_question_questionname").asText(), p.get("topic_name").asText(), 
            			p.get("topic_question_page").asText(), p.get("topic_question_difficulty").asText(),
            			p.get("subtopic").asText(), p.get("difficultyRating").asInt(), p.get("timeSpentOnQuestion").asInt(), p.get("notes").asText(), p.get("isCompleted").asBoolean(), null, p.get("isScheduled").asBoolean() );
            	problemMap.put(problemId, problem); // store the questions in the json
            }
            
            // store the week number, the schedule and the questions in a the shared data store
            SHARED_DATA.setQuestionsPerDay(problemSchedule);
            SHARED_DATA.setProblemMapping(problemMap);
            SHARED_DATA.setScheduleWeekNumber(weekNumber);
            SHARED_DATA.updateSchedule();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /**
     * Displays question details on the right side of the main view upon selecting a question
     * 
     * @see Problem
     * @author Sarthak Mallick
     * @version 1.0
     */
    class ShowProblemDetails implements EventHandler<ActionEvent>{
    	@Override
    	public void handle(ActionEvent event) {
    	    Node node = (Node) event.getSource();
    	    String btnId = (String) node.getUserData();
    	    
    	    // Determine the button pressed and find the corresponding day and problem
    	    String[] dayAndButton = btnId.split(":");
    	    String day = dayAndButton[0];
    	    int button = Integer.parseInt(dayAndButton[1]);
    	    selectedProblem = problemMap.get(problemSchedule.get(day).get(button-1));
    	    selectedProblemButton = (Button) event.getSource();
    	        	    
    		// Set the details as per the corresponding object in map
    	    if(selectedProblem != null) {
                questionTitle.setText(selectedProblem.getQuestionTitle());
                questionTopic.setText(selectedProblem.getTopicName());
                questionDifficulty.setText(selectedProblem.getDifficultyLevel());
                hyperLink.setText(selectedProblem.getLink());
                
             // Temporarily remove listener to avoid triggering the method
                questionCompleted.selectedProperty().removeListener(questionCompletedListener);
                questionCompleted.setSelected(selectedProblem.getIsCompleted());
                questionCompleted.selectedProperty().addListener(questionCompletedListener);
                
                
                difficultyChoiceBox.getSelectionModel().select(String.valueOf(selectedProblem.getDifficultyRating()));
                timeSpinner.getValueFactory().setValue(selectedProblem.getTimeSpentOnQuestion());
                userNotes.setText(selectedProblem.getNotes());
    	    }
    	}
    }

}
