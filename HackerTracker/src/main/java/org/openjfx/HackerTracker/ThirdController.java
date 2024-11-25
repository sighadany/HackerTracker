package org.openjfx.HackerTracker;

import java.io.IOException;

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
	
	@FXML
	private Label easyTag;
	
	@FXML
	private Label mediumTag;
	
	@FXML
	private Label hardTag;
	
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
    private double numberOfQuestionsCompleted = 27;
    
    //intially we consider all 150 questions
    private double numberOfQuestions = 150;

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

    // Example: Initialize the progress to 50% when the application starts
    public void initialize() {
    	
    	// dummy data to get started on milestone view
    	// must be replaced by user progress data
    	// we must initialize numberOfQuestionsCompleted, numberOfQuestions, and  topicList text by reading from file
    	
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
    			numberOfQuestionsCompleted = 6;
    			numberOfQuestions = 7;
    			break;
    		case "Array String":
    			newTitle = "Array String";
    			numberOfQuestionsCompleted = 1;
    			numberOfQuestions = 6;
    			break;
    		case "Backtracking":
    			newTitle = "Backtracking";
    			numberOfQuestionsCompleted = 9;
    			numberOfQuestions = 14;
    			break;
    		case "Binary Search":
    			newTitle = "Binary Search";
    			numberOfQuestionsCompleted = 10;
    			numberOfQuestions = 10;
    			break;
    		case "Binary Search Tree":
    			newTitle = "Binary Search Tree";
    			numberOfQuestionsCompleted = 3;
    			numberOfQuestions = 4;
    			break;
    		case "Binary Tree BFS":
    			newTitle = "Binary Search BFS";
    			numberOfQuestionsCompleted = 7;
    			numberOfQuestions = 11;
    			break;
    		case "Binary Tree General":
    			newTitle = "Binary Tree General";
    			numberOfQuestionsCompleted = 3;
    			numberOfQuestions = 8;
    			break;
    		case "Bit Manipulation":
    			newTitle = "Bit Manipulation";
    			numberOfQuestionsCompleted = 8;
    			numberOfQuestions = 13;
    			break;
    		case "Divide and Conquer":
    			newTitle = "Divide and Conquer";
    			numberOfQuestionsCompleted = 10;
    			numberOfQuestions = 12;
    			break;
    		case "Graph BFS":
    			newTitle = "Graph Breadth First Search";
    			numberOfQuestionsCompleted = 1;
    			numberOfQuestions = 3;
    			break;
    		case "Graph General":
    			newTitle = "Graph General";
    			numberOfQuestionsCompleted = 0;
    			numberOfQuestions = 5;
    			break;
    		case "Hashmap":
    			newTitle = "Hashmap";
    			numberOfQuestionsCompleted = 1;
    			numberOfQuestions = 3;
    			break;
    		case "Heap":
    			newTitle = "Heap";
    			numberOfQuestionsCompleted = 1;
    			numberOfQuestions = 4;
    			break;
    		case "Intervals":
    			newTitle = "Intervals";
    			numberOfQuestionsCompleted = 1;
    			numberOfQuestions = 1;
    			break;
    		case "Kadane's Algorithm":
    			newTitle = "Kadane's algorithm";
    			numberOfQuestionsCompleted = 4;
    			numberOfQuestions = 5;
    			break;
    		case "Linked List":
    			newTitle = "Linked List";
    			numberOfQuestionsCompleted = 3;
    			numberOfQuestions = 5;
    			break;
    		case "Math":
    			newTitle = "Math";
    			numberOfQuestionsCompleted = 5;
    			numberOfQuestions = 6;
    			break;
    		case "Matrix":
    			newTitle = "Matrix";
    			numberOfQuestionsCompleted = 2;
    			numberOfQuestions = 4;
    			break;
    		case "Multidimensional DP":
    			newTitle = "Multidimensional Dynamic Programming";
    			numberOfQuestionsCompleted = 2;
    			numberOfQuestions = 5;
    			break;
    		case "Sliding Window":
    			newTitle = "Sliding Window";
    			numberOfQuestionsCompleted = 1;
    			numberOfQuestions = 7;
    			break;
    		case "Stack":
    			newTitle = "Stack";
    			numberOfQuestionsCompleted = 3;
    			numberOfQuestions = 8;
    			break;
    		case "Trie":
    			newTitle = "Trie";
    			numberOfQuestionsCompleted = 3;
    			numberOfQuestions = 6;
    			break;
    		case "Two Pointers":
    			newTitle = "Two Pointers";
    			numberOfQuestionsCompleted = 0;
    			numberOfQuestions = 3;
    			break;
    		default:
    			newTitle = "All 150 questions";
    			numberOfQuestionsCompleted = 84;
    			numberOfQuestions = 150;
    			break;
    	}
    	
    	
    	questionCategoryLabel.setText(newTitle);
    	topicList.setText(newTitle);
    	
    	// Update the label with the progress 
    	generalProgressLabel.setText(String.format("Progress: %.0f / %.0f", numberOfQuestionsCompleted, numberOfQuestions));
        
        drawProgress( (numberOfQuestionsCompleted / numberOfQuestions) * 100);  // draw the progress bar
    	
    }
}