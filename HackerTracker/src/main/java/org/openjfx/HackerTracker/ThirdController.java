package org.openjfx.HackerTracker;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ThirdController {
	
	private final Scheduler SHARED_DATA = Scheduler.getInstance();
	
	@FXML
	private Label easyCount;
	
	@FXML
	private Label mediumCount;
	
	@FXML
	private Label hardCount;
	
	@FXML
	private Text questionCategoryLabel;
	
	@FXML
	private MenuButton topicList;
	
	@FXML
	private StackPane chartSpace;
	
	@FXML
	private VBox generalProgress;
	
	@FXML
    private Label generalProgressLabel;
	
	@FXML
    private Canvas generalProgressCanvas;
	

    private final double RADIUS = 200;
    private final double CENTER_X = 225;
    private final double CENTER_Y = 250;
    private final double START_ANGLE = 180;
    
    //this is dummy data that must be replaced by number of completed questions across all topics 
    private double numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblems().size();
    
    //intially we consider all 150 questions
    private double numberOfQuestions = numberOfQuestionsCompleted +  SHARED_DATA.getNotCompletedProblems().size();
    
    //number of easy questions completed
    private int easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(SHARED_DATA.getCompletedProblems(), "Easy").size();
    
  //number of easy questions completed
    private int mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(SHARED_DATA.getCompletedProblems(), "Medium").size();
    
  //number of easy questions completed
    private int hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(SHARED_DATA.getCompletedProblems(), "Hard").size();


    // Method to draw the progress on the canvas
    public void drawProgress(double percentage) {
    
        GraphicsContext gc = generalProgressCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, generalProgressCanvas.getWidth(), generalProgressCanvas.getHeight()); // Clear the canvas

        // Calculate the angle based on the percentage (0 to 100%)
        double angle = -180 * (percentage / 100); // 180 degrees for half-circle

        // Draw the border of the semi-circle
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(15);

        
        gc.strokeArc(CENTER_X - RADIUS, CENTER_Y - RADIUS, 2 * RADIUS, 2 * RADIUS, 
                     START_ANGLE, -180, javafx.scene.shape.ArcType.OPEN);

        
        // Set the stroke color for the progress
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(10);
     // Draw the arc based on the calculated angle
        gc.strokeArc(CENTER_X - RADIUS, CENTER_Y - RADIUS, 2 * RADIUS, 2 * RADIUS, 
                     START_ANGLE, angle, javafx.scene.shape.ArcType.OPEN);
    }
    
    
    public void updateProgressView(String topic) {
    	List<Problem> problems = SHARED_DATA.getCompletedProblemsByTopic(topic);
		this.numberOfQuestionsCompleted = problems.size();
		this.numberOfQuestions = this.numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic(topic).size();
		this.easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
		this.mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
		this.hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    }

    // Example: Initialize the progress to 50% when the application starts
    public void initialize() {
    	
    	// dummy data to get started on milestone view
    	// must be replaced by user progress data
    	// we must initialize numberOfQuestionsCompleted, numberOfQuestions, and  topicList text by reading from file
    	
    	easyCount.setText("Easy: " + easyQuestions);
    	
    	mediumCount.setText("Medium: " + mediumQuestions);
    	
    	hardCount.setText("Hard: " + hardQuestions);
    	
    	// Update the label with the progress 
    	generalProgressLabel.setText(String.format("Progress: %.0f / %.0f", numberOfQuestionsCompleted, numberOfQuestions));
        
        drawProgress( (numberOfQuestionsCompleted / numberOfQuestions) * 100);  // draw the progress bar
    }
    
    @FXML
    private void switchToSettings() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    private void switchCategory(ActionEvent event) throws IOException {
    	
    	MenuItem source = (MenuItem) event.getSource();
    	
    	String elementText = source.getText();
    	
//    	System.out.println(elementText);
    	
    	String newTitle = "";
    	
    	switch (elementText) {
    		case "1D DP":
    			newTitle = "1D Dynamic Programming";
    			// dummy data, must be replaced with actual user data
    			updateProgressView("1D DP");
    			break;
    		case "Array / String":
    			newTitle = "Array / String";
    			updateProgressView("Array / String");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Array / String").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Array / String").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Backtracking":
    			newTitle = "Backtracking";
    			updateProgressView("Backtracking");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Backtracking").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Backtracking").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Binary Search":
    			newTitle = "Binary Search";
    			updateProgressView("Binary Search");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Binary Search").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Binary Search").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Binary Search Tree":
    			newTitle = "Binary Search Tree";
    			updateProgressView("Binary Search Tree");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Binary Search Tree").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Binary Search Tree").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Binary Tree BFS":
    			newTitle = "Binary Search BFS";
    			updateProgressView("Binary Tree BFS");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Binary Tree BFS").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Binary Tree BFS").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Binary Tree General":
    			newTitle = "Binary Tree General";
    			updateProgressView("Binary Tree General");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Binary Tree General").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Binary Tree General").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Bit Manipulation":
    			newTitle = "Bit Manipulation";
    			updateProgressView("Bit Manipulation");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Bit Manipulation").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Bit Manipulation").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Divide and Conquer":
    			newTitle = "Divide and Conquer";
    			updateProgressView("Divide & Conquer");
//    			newTitle = "Divide and Conquer";
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Divide & Conquer").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Divide & Conquer").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Graph BFS":
    			newTitle = "Graph BFS";
    			updateProgressView("Graph BFS");
//    			newTitle = "Graph Breadth First Search";
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Graph BFS").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Graph BFS").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Graph General":
    			newTitle = "Graph General";
    			updateProgressView("Graph General");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Graph General").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Graph General").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Hashmap":
    			newTitle = "Hashmap";
    			updateProgressView("Hashmap");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Hashmap").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Hashmap").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Heap":
    			newTitle = "Heap";
    			updateProgressView("Heap");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Heap").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Heap").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Intervals":
    			newTitle = "Intervals";
    			updateProgressView("Intervals");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Intervals").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Intervals").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Kadane's Algorithm":
    			newTitle = "Kadane's algorithm";
    			updateProgressView("Kadane's Algorithm");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Kadane\\u0027s Algorithm").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Kadane's Algorithm").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Linked List":
    			newTitle = "Linked List";
    			updateProgressView("Linked List");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Linked List").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Linked List").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Math":
    			newTitle = "Math";
    			updateProgressView("Math");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Math").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Math").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Matrix":
    			newTitle = "Matrix";
    			updateProgressView("Matrix");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Matrix").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Matrix").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Multidimensional DP":
    			newTitle = "Multidimensional Dynamic Programming";
    			updateProgressView("Multidimensional DP");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Multidimensional DP").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Multidimensional DP").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Sliding Window":
    			newTitle = "Sliding Window";
    			updateProgressView("Sliding Window");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Sliding Window").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Sliding Window").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Stack":
    			newTitle = "Stack";
    			updateProgressView("Stack");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Stack").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Stack").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Trie":
    			newTitle = "Trie";
    			updateProgressView("Trie");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Trie").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Trie").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		case "Two Pointers":
    			newTitle = "Two Pointers";
    			updateProgressView("Two Pointers");
//    			numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblemsByTopic("Two Pointers").size();
//    			numberOfQuestions = numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic("Two Pointers").size();
//    			easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
//    			mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
//    			hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    		default:
    			newTitle = "All 150 questions";
    			List<Problem> problems = SHARED_DATA.getCompletedProblems();
    			this.numberOfQuestionsCompleted = problems.size();
    			this.numberOfQuestions = this.numberOfQuestionsCompleted +  SHARED_DATA.getNotCompletedProblems().size();
    			this.easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
    			this.mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
    			this.hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    	}    	
    	
    	questionCategoryLabel.setText(newTitle);
    	topicList.setText(newTitle);
    	
    	easyCount.setText("Easy: " + this.easyQuestions);
    	
    	mediumCount.setText("Medium: " + this.mediumQuestions);
    	
    	hardCount.setText("Hard: " + this.hardQuestions);
    	
    	// Update the label with the progress 
    	generalProgressLabel.setText(String.format("Progress: %.0f / %.0f", this.numberOfQuestionsCompleted, this.numberOfQuestions));
        
        drawProgress( (this.numberOfQuestionsCompleted / this.numberOfQuestions) * 100);  // draw the progress bar
    	
    }
    
//    private void 
}