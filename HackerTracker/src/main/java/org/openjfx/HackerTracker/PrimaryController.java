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
import javafx.collections.FXCollections;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

/**
 * Dynamically updates the appearance of the main view. Updates the calendar
 * with leetcode questions based on a schedule defined by the user.
 * 
 * Provides a view for detailed information about leetcode questions. 
 * This information appears dynamically as questions are selected.
 * 
 * @author Dany Sigha, Sarthak Mallick, Kavin Jha
 * @version 1.0
 */
public class PrimaryController {
	
    public HashMap<Integer, Problem> problemMap = new HashMap<Integer, Problem>();
    public HashMap<String, List<Integer>> problemSchedule;
    private final Scheduler SHARED_DATA = Scheduler.getInstance();
    private Problem selectedProblem;
    
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
    private Label questionTitle;
    
    @FXML
    private CheckBox questionCompleted;
    
    @FXML
    private Label questionTopic;
    
    @FXML
    private Label questionDifficulty;
    
    @FXML
    private Hyperlink hyperLink;
    
    @FXML
    private TextArea userNotes; // Notes field
    
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
     * Upon loading the main view, set the date, load the leetcode questions,
     * and initialize the values of the difficulty and time taken inputs.
     * 
     * @see Scheduler
     * @see PrimaryController#loadJsonProblems()
     * @see PrimaryController#addQuestionsToCalendar()
     */
    @FXML
    public void initialize() {
    	setDate();
    	
    	if (SHARED_DATA.getProblemMapping().size() == 0) {
    		loadJsonProblems(); // get the problems and the schedule from the json file
    	}else {
    		problemSchedule = SHARED_DATA.getQuestionsPerDay(); 
    		// for now this is an error, but the assigning problems logic should be in the settings page
    		problemMap = SHARED_DATA.getProblemMapping();
    	}
    	
    	addQuestionsToCalendar(); // get the schedule from the settings page
    	
        ObservableList<String> options = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        difficultyChoiceBox.setItems(options);
        
        
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
        
     // Define the range and step size
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 180, 30, 1);
        timeSpinner.setValueFactory(valueFactory);
        
        addListenersToFields();
    }
    
    /**
     * Read from the problemSchedule object to retrieve the schedule set by the user and populate the calendar
     * 
     * @see Scheduler
     */
    private void addQuestionsToCalendar() {

        updateDayUI(Monday, "Mon", problemSchedule.get("Mon").size());
        updateDayUI(Tuesday, "Tue", problemSchedule.get("Tue").size());
        updateDayUI(Wednesday, "Wed", problemSchedule.get("Wed").size());
        updateDayUI(Thursday, "Thu", problemSchedule.get("Thu").size());
        updateDayUI(Friday, "Fri", problemSchedule.get("Fri").size());
        updateDayUI(Saturday, "Sat", problemSchedule.get("Sat").size());
        updateDayUI(Sunday, "Sun", problemSchedule.get("Sun").size());
    }
    
    /**
     * Adds question buttons to a day of the week.
     *
     * @param dayBox a Vertical Box that represents a day of the week in the UI
     * @param day a String that contains a day of the week
     * @param questionCount the number of questions to add on a specified day
     * @see ShowProblemDetails
     */
    private void updateDayUI(VBox dayBox, String day, int questionCount) {
        dayBox.getChildren().clear(); // Clear existing buttons
        for (int i = 1; i <= questionCount; i++) {
            Button questionButton = new Button("Q. " + i);
            questionButton.setUserData( day + ":" + i);
            questionButton.setOnAction(new ShowProblemDetails());
            questionButton.setWrapText(true);
            questionButton.setUserData(day+i);
            questionButton.setOnAction(new ShowProblemDetails());
            dayBox.getChildren().add(questionButton);
        }
    }
    
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
        questionCompleted.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedProblem != null) {
                selectedProblem.setIsCompleted(newValue);
            }
        });
        // Update notes field
        userNotes.textProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedProblem != null) {
                selectedProblem.setNotes(newValue);
            }
        });
    }
    
    
    /**
     * Reads and stores leetcode problems and schedule from the json file
     *
     * @throws RuntimeException if the json file cannot be read
     * @see Problem
     * @see Scheduler
     * @see Scheduler
     */
    private void loadJsonProblems() {
        try {
    		byte[] json = Files.readAllBytes(Paths.get("../leetcode_problems.json"));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(json);
            
            
            problemSchedule = SHARED_DATA.getQuestionsPerDay();
            
            JsonNode storedSchedule = jsonNode.get("schedule");
            
         // get the schedule from the json file
            
            for(String day : new String[] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"}) {
            	JsonNode dayNode = storedSchedule.get(day);
            	
            	String[] questionIds = (dayNode != null && dayNode.isArray() && dayNode.size() > 0) 
                        ? dayNode.toString().replace("[", "").replace("]", "").trim().split(",")
                        : new String[0]; // Return an empty array if it's an empty list or non-existent
            	
            	List<Integer> integerList = Arrays.stream(questionIds).map(s -> Integer.parseInt(s) ).collect(Collectors.toList());
            	
//            	System.out.println(integerList);
            	
            	problemSchedule.put(day, integerList);
            		            	
            }
            
         // get the leetcode questions from the json file
            
            for (JsonNode p : jsonNode.get("problems")) {
            	int problemId = p.get("Problem_id").asInt();
            	Problem problem = new Problem(problemId, 0 , p.get("topic_question_questionname").asText(), p.get("topic_name").asText(), 
            			p.get("topic_question_page").asText(), p.get("topic_question_difficulty").asText(),
            			p.get("subtopic").asText(), p.get("difficultyRating").asInt(), p.get("timeSpentOnQuestion").asInt(), p.get("notes").asText(), p.get("isCompleted").asBoolean(), null, p.get("isScheduled").asBoolean() );
            	problemMap.put(problemId, problem);
            }
            
            // store the schedule and the questions in an object
            
            SHARED_DATA.setQuestionsPerDay(problemSchedule);
            
            SHARED_DATA.setProblemMapping(problemMap); // set the dynamic problem map data
            
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
//    	    Problem problem = problemMap.get(problemSchedule.get(day).get(button-1));
    	        	    
    		// Set the details as per the corresponding object in map
    	    if(selectedProblem != null) {
    	    	questionTitle.setText(selectedProblem.getQuestionTitle());
        	    if (selectedProblem.getIsCompleted()) questionCompleted.setSelected(true);
        		questionTopic.setText(selectedProblem.getTopicName());
        		questionDifficulty.setText(selectedProblem.getDifficultyLevel());
        		hyperLink.setText(selectedProblem.getLink());
        		questionTitle.setText(selectedProblem.getQuestionTitle());
                questionTopic.setText(selectedProblem.getTopicName());
                questionDifficulty.setText(selectedProblem.getDifficultyLevel());
                hyperLink.setText(selectedProblem.getLink());
                questionCompleted.setSelected(selectedProblem.getIsCompleted());
                difficultyChoiceBox.getSelectionModel().select(String.valueOf(selectedProblem.getDifficultyRating()));
                timeSpinner.getValueFactory().setValue(selectedProblem.getTimeSpentOnQuestion());
                userNotes.setText(selectedProblem.getNotes());
        		
    	    }
    	    
    		
    		
    	}
    }

}
