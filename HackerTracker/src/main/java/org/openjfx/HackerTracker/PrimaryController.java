package org.openjfx.HackerTracker;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

public class PrimaryController {
    public HashMap<Integer, Problem> problemMap = new HashMap<Integer, Problem>();
    public HashMap<String, List<Integer>> problemSchedule;
    private final SharedData sharedData = SharedData.getInstance();
    private Problem selectedProblem;

    @FXML
    private void switchToSettings() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    private void switchToProgress() throws IOException {
        App.setRoot("third");
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
    
    @FXML
    public void setDate() {
    	LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy");
        String formattedDate = currentDate.format(formatter);
    	fullDate.setText(formattedDate);
    }

    @FXML
    public void initialize() {
    	setDate();
    	loadQuestionsFromSharedData();
    	
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
     
        loadJsonProblems();
        problemSchedule = Scheduler.getSchedule();
        
        addListenersToFields();
    }
    
    private void loadQuestionsFromSharedData() {
        Map<String, Integer> questionsPerDay = sharedData.getQuestionsPerDay();

        updateDayUI(Monday, questionsPerDay.getOrDefault("Monday", 0), "Mon:");
        updateDayUI(Tuesday, questionsPerDay.getOrDefault("Tuesday", 0), "Tue:");
        updateDayUI(Wednesday, questionsPerDay.getOrDefault("Wednesday", 0), "Wed:");
        updateDayUI(Thursday, questionsPerDay.getOrDefault("Thursday", 0), "Thu:");
        updateDayUI(Friday, questionsPerDay.getOrDefault("Friday", 0), "Fri:");
        updateDayUI(Saturday, questionsPerDay.getOrDefault("Saturday", 0), "Sat:");
        updateDayUI(Sunday, questionsPerDay.getOrDefault("Sunday", 0), "Sun:");
    }
    
    private void updateDayUI(VBox dayBox, int questionCount, String day) {
        dayBox.getChildren().clear(); // Clear existing buttons
        for (int i = 1; i <= questionCount; i++) {
            Button questionButton = new Button("Q. " + i);
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
	    
  
    private void loadJsonProblems() {
        try {
    		byte[] json = Files.readAllBytes(Paths.get("../leetcode_problems.json"));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(json);
                        
            for (JsonNode p : jsonNode.get("problems")) {
            	int problemId = p.get("Problem_id").asInt();
            	Problem problem = new Problem(problemId, 0 , p.get("topic_question_questionname").asText(), p.get("topic_name").asText(), 
            			p.get("topic_question_page").asText(), p.get("topic_question_difficulty").asText(),
            			p.get("subtopic").asText(), 0, 0, "", false, null );
            	problemMap.put(problemId, problem);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
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
    	    
    		// Set the details as per the corresponding object in map
    	    if (selectedProblem != null) {
                // Populate fields with problem data
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
    
    static class Scheduler {
    	static HashMap<String, List<Integer>> getSchedule() {
    		//Hardcoded for now
    		HashMap<String, List<Integer>> sch = new HashMap<>();
    		sch.put("Mon", List.of(41,42,43,44,45));
    		sch.put("Tue", List.of());
    		sch.put("Wed", List.of(15,16));
    		sch.put("Thu", List.of(27));
    		sch.put("Fri", List.of(38,39));
    		sch.put("Sat", List.of());
    		sch.put("Sun", List.of(50,51,52,53,54));
    		return sch;
    	}
    }
}