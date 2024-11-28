package org.openjfx.HackerTracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javafx.scene.Node;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private int numberOfWednesdayButtons;
    @FXML 
    private VBox wednesdayButtons;
    @FXML
    private int numberOfThursdayButtons;
    @FXML 
    private VBox thursdayButtons;
    @FXML
    private int numberOfFridayButtons;
    @FXML 
    private VBox fridayButtons;
    @FXML
    private int numberOfSaturdayButtons;
    @FXML 
    private VBox saturdayButtons;
    @FXML
    private int numberOfSundayButtons;
    @FXML 
    private VBox sundayButtons;
    

    
    @FXML
    private Label questionTitle;
    
    @FXML
    private CheckBox questionCompleted;
    
    @FXML
    private Label questionTopic;
    
    @FXML
    private Label questionDifficulty;

    public void test(){
        System.out.println("Testing");
    };
    
    public HashMap<Integer, Problem> problemMap = new HashMap<Integer, Problem>();
    public HashMap<String, List<Integer>> problemSchedule;
    public HashMap<Integer, AttemptedProblem> attemptedProblem = new HashMap<Integer, AttemptedProblem>();

    @FXML
    public void initialize() {

        ObservableList<String> options = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        difficultyChoiceBox.setItems(options);
        
     // Define the range and step size
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 180, 30, 1);
        timeSpinner.setValueFactory(valueFactory);
        
        addProblemButtons();
        loadJsonProblems();
        problemSchedule = Scheduler.getSchedule();
    }
    
    private void addProblemButtons() {
    	numberOfMondayButtons = 5;
        numberOfTuesdayButtons = 0;
        numberOfWednesdayButtons = 2;
        numberOfThursdayButtons = 1;
        numberOfFridayButtons = 2;
        numberOfSaturdayButtons = 0;
        numberOfSundayButtons = 5;
        
        for(int i=1; i<=numberOfMondayButtons; i++) {
        	Button problemTile = new Button("Q"+i);
        	problemTile.setUserData("Mon:"+i);
        	problemTile.setOnAction(new ShowProblemDetails());
            mondayButtons.getChildren().add(problemTile);
        }
        for(int i=1; i<=numberOfTuesdayButtons; i++) {
        	Button problemTile = new Button("Q"+i);
        	problemTile.setUserData("Tue:"+i);
        	problemTile.setOnAction(new ShowProblemDetails());
            tuesdayButtons.getChildren().add(problemTile);
        }
        for(int i=1; i<=numberOfWednesdayButtons; i++) {
        	Button problemTile = new Button("Q"+i);
        	problemTile.setUserData("Wed:"+i);
        	problemTile.setOnAction(new ShowProblemDetails());
            wednesdayButtons.getChildren().add(problemTile);
        }
        for(int i=1; i<=numberOfThursdayButtons; i++) {
        	Button problemTile = new Button("Q"+i);
        	problemTile.setUserData("Thu:"+i);
        	problemTile.setOnAction(new ShowProblemDetails());
            thursdayButtons.getChildren().add(problemTile);
        }
        for(int i=1; i<=numberOfFridayButtons; i++) {
        	Button problemTile = new Button("Q"+i);
        	problemTile.setUserData("Fri:"+i);
        	problemTile.setOnAction(new ShowProblemDetails());
            fridayButtons.getChildren().add(problemTile);
        }
        for(int i=1; i<=numberOfSaturdayButtons; i++) {
        	Button problemTile = new Button("Q"+i);
        	problemTile.setUserData("Sat"+i);
        	problemTile.setOnAction(new ShowProblemDetails());
            saturdayButtons.getChildren().add(problemTile);
        }
        
        for(int i=1; i<=numberOfSundayButtons; i++) {
        	Button problemTile = new Button("Q"+i);
        	problemTile.setUserData("Sun:"+i);
        	problemTile.setOnAction(new ShowProblemDetails());
            sundayButtons.getChildren().add(problemTile);
        }
    }
    
    private void loadJsonProblems() {
        try {
    		byte[] json = Files.readAllBytes(Paths.get("../cleaned_leetcode_questions.json"));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode problems = mapper.readTree(json);
            
            int problemId = 1;
            for (JsonNode p : problems) {
            	Problem problem = new Problem(problemId, 0 , p.get("topic_question_questionname").asText(), p.get("topic_name").asText(), 
            			p.get("topic_question_page").asText(), p.get("topic_question_difficulty").asText(),
            			p.get("topic_question_selection1_subtopic").asText(), 0, 0, "", 0, null );
            	problemMap.put(problemId, problem);
            	problemId++;
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
    	    Problem problem = problemMap.get(problemSchedule.get(day).get(button-1));
    	        	    
    		// Set the details as per the corresponding object in map
    	    questionTitle.setText(problem.getQuestionTitle());
    	    if (attemptedProblem.containsKey(problem.getProblemId())) questionCompleted.setSelected(true);
    		questionTopic.setText(problem.getTopicName());
    		questionDifficulty.setText(problem.getDifficultyLevel());
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
