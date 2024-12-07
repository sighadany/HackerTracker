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

/**
 * Dynamically updates the appearance of the progress view. Updates the progress
 * bar to show how many questions were completed per the topic selected. This
 * includes a break down for Easy, Medium, and Hard questions.
 * 
 * Provides a drop down to select the topic for which to display progress.
 * 
 * @author Dany Sigha
 * @version 1.0
 */
public class ThirdController {
	
	private final Scheduler SHARED_DATA = Scheduler.getInstance();
	
	@FXML
	private Label timeToComplete; // The estimated weeks needed to complete
	
	@FXML
	private Label easyCount; // The count of the easy questions completed
	
	@FXML
	private Label mediumCount; // The count of the medium questions completed
	
	@FXML
	private Label hardCount; // The count of the hard questions completed
	
	@FXML
	private Text questionCategoryLabel; // The topic of the questions for which we show progress
	
	@FXML
	private MenuButton topicList; // The drop down menu for the topics
	
	@FXML
	private StackPane chartSpace; // The contained of the progress bar
	
	@FXML
	private VBox generalProgress;
	
	@FXML
    private Label generalProgressLabel; // Label of progress completion
	
	@FXML
    private Canvas generalProgressCanvas; // The canvas of the progress bar
	

    private final double RADIUS = 200;
    private final double CENTER_X = 225;
    private final double CENTER_Y = 250;
    private final double START_ANGLE = 180;
    
    
    private double numberOfQuestionsCompleted = SHARED_DATA.getCompletedProblems().size();
    
    private double numberOfQuestions = numberOfQuestionsCompleted +  SHARED_DATA.getNotCompletedProblems().size();
    
    private double weeksPassed = SHARED_DATA.getScheduleWeekNumber() - SHARED_DATA.getFirstWeekNumber();
    
    //number of easy questions completed
    private int easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(SHARED_DATA.getCompletedProblems(), "Easy").size();
    
  //number of medium questions completed
    private int mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(SHARED_DATA.getCompletedProblems(), "Medium").size();
    
  //number of hard questions completed
    private int hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(SHARED_DATA.getCompletedProblems(), "Hard").size();


    /**
     * Draws a progress indicator as a semi-circular arc on a canvas.
     * 
     * This method clears the existing canvas and renders a progress visualization as a semi-circle. 
     * The visualization consists of:
     * 1. A black border representing the full range (0% to 100%) as a half-circle.
     * 2. A green arc indicating the current progress based on the specified percentage.
     * 
     * **Key Steps:**
     * - Clears the canvas to remove any previous drawings.
     * - Calculates the arc's angle based on the given percentage (0-100%), 
     *   where 0% corresponds to 0 degrees and 100% to -180 degrees (half-circle).
     * - Draws a black border as the base semi-circle using the `strokeArc` method.
     * - Draws a green progress arc over the black border based on the calculated angle.
     * 
     * **Parameters:**
     * @param percentage The current progress as a percentage (0-100). 
     *                   - 0% indicates no progress, and 100% indicates full progress.
     * 
     * **Canvas Details:**
     * - The canvas (`generalProgressCanvas`) is cleared before drawing to avoid overlapping progress arcs.
     * - The center and radius of the arcs are pre-determined constants (`CENTER_X`, `CENTER_Y`, and `RADIUS`).
     * 
     * **Graphics Context Details:**
     * - The `GraphicsContext` is used to perform the drawing operations.
     * - `strokeArc` is used to draw both the border and progress arcs. 
     *   - The `ArcType.OPEN` ensures the arc is drawn as an open curve.
     * 
     * **Notes:**
     * - The `START_ANGLE` defines where the semi-circle begins (typically at 0 degrees).
     * - Negative angles in `strokeArc` indicate clockwise drawing.
     */
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
    
    /**
     * Updates the progress view for a specific topic by recalculating 
     * the total number of problems, completed problems, and the count 
     * of problems based on their difficulty levels.
     *
     * @param topic the name of the topic whose progress is being updated
     * @see Schedule#getCompletedProblemsByTopic(String) Returns the list of completed problems
     * @see Schedule#getNotCompletedProblemsByTopic(String) Returns the list of problems not completed
     * @see Schedule#filterByLeetcodeDifficulty(List, String) Returns the list of completed problems for the specified topic
     */
    public void updateProgressView(String topic) {
    	List<Problem> problems = SHARED_DATA.getCompletedProblemsByTopic(topic);
		this.numberOfQuestionsCompleted = problems.size();
		this.numberOfQuestions = this.numberOfQuestionsCompleted + SHARED_DATA.getNotCompletedProblemsByTopic(topic).size();
		this.easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
		this.mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
		this.hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    }

    /**
     * Initializes the progress view by updating the difficulty counts, 
     * displaying the total progress, and drawing the progress bar.
     * 
     * This method assumes that the necessary data (easyQuestions, mediumQuestions, 
     * hardQuestions, numberOfQuestionsCompleted, and numberOfQuestions) 
     * has already been computed and is available for use.
     */
    public void initialize() {
    	
    	easyCount.setText("Easy: " + easyQuestions);
    	
    	mediumCount.setText("Medium: " + mediumQuestions);
    	
    	hardCount.setText("Hard: " + hardQuestions);
    	
    	int weeksToComplete = 20;
    	if (numberOfQuestionsCompleted > 0) {
    		weeksToComplete = (int)((150-numberOfQuestionsCompleted)*(weeksPassed+1)/numberOfQuestionsCompleted);
    	}
		  timeToComplete.setText(weeksToComplete+" Weeks Required");

    	// Update the progress label with the progress 
    	generalProgressLabel.setText(String.format("Progress: %.0f / %.0f", numberOfQuestionsCompleted, numberOfQuestions));
        
        drawProgress( (numberOfQuestionsCompleted / numberOfQuestions) * 100);  // draw the progress bar
    }
    
    /**
     * Switches from the progress view to the settings view
     *
     * @throws IOException if the FXML file cannot be read
     */
    @FXML
    private void switchToSettings() throws IOException {
        App.setRoot("secondary");
    }
    
    /**
     * Switches from the progress view to the main view
     *
     * @throws IOException if the FXML file cannot be read
     */
    @FXML
    private void switchToHome() throws IOException {
        App.setRoot("primary");
    }
    
    /**
     * Handles the action of switching between question categories in the UI.
     * Based on the selected menu item, this method updates the category title, 
     * recalculates progress data (questions completed, difficulty counts, etc.), 
     * and updates the visual representation in the progress view.
     * 
     * @param event the action event triggered by the user selecting a category
     * @throws IOException if there is an error during data processing
     */
    @FXML
    private void switchCategory(ActionEvent event) throws IOException {
    	
    	MenuItem source = (MenuItem) event.getSource();
    	
    	String elementText = source.getText();
    	    	
    	String newTitle = "";
    	
    	// update the list of questions considered based on topic selected
    	switch (elementText) {
    		case "1D DP":
    			newTitle = "1D Dynamic Programming"; // updates the label of the progress bar
    			updateProgressView("1D DP"); // only considers 1D DP questions
    			break;
    		case "Array / String":
    			newTitle = "Array / String";
    			updateProgressView("Array / String");
    			break;
    		case "Backtracking":
    			newTitle = "Backtracking";
    			updateProgressView("Backtracking");
    			break;
    		case "Binary Search":
    			newTitle = "Binary Search";
    			updateProgressView("Binary Search");
    			break;
    		case "Binary Search Tree":
    			newTitle = "Binary Search Tree";
    			updateProgressView("Binary Search Tree");
    			break;
    		case "Binary Tree BFS":
    			newTitle = "Binary Search BFS";
    			updateProgressView("Binary Tree BFS");
    			break;
    		case "Binary Tree General":
    			newTitle = "Binary Tree General";
    			updateProgressView("Binary Tree General");
    			break;
    		case "Bit Manipulation":
    			newTitle = "Bit Manipulation";
    			updateProgressView("Bit Manipulation");
    			break;
    		case "Divide and Conquer":
    			newTitle = "Divide and Conquer";
    			updateProgressView("Divide & Conquer");
    			break;
    		case "Graph BFS":
    			newTitle = "Graph BFS";
    			updateProgressView("Graph BFS");
    			break;
    		case "Graph General":
    			newTitle = "Graph General";
    			updateProgressView("Graph General");
    			break;
    		case "Hashmap":
    			newTitle = "Hashmap";
    			updateProgressView("Hashmap");
    			break;
    		case "Heap":
    			newTitle = "Heap";
    			updateProgressView("Heap");
    			break;
    		case "Intervals":
    			newTitle = "Intervals";
    			updateProgressView("Intervals");
    			break;
    		case "Kadane's Algorithm":
    			newTitle = "Kadane's algorithm";
    			updateProgressView("Kadane's Algorithm");
    			break;
    		case "Linked List":
    			newTitle = "Linked List";
    			updateProgressView("Linked List");
    			break;
    		case "Math":
    			newTitle = "Math";
    			updateProgressView("Math");
    			break;
    		case "Matrix":
    			newTitle = "Matrix";
    			updateProgressView("Matrix");
    			break;
    		case "Multidimensional DP":
    			newTitle = "Multidimensional Dynamic Programming";
    			updateProgressView("Multidimensional DP");
    			break;
    		case "Sliding Window":
    			newTitle = "Sliding Window";
    			updateProgressView("Sliding Window");
    			break;
    		case "Stack":
    			newTitle = "Stack";
    			updateProgressView("Stack");
    			break;
    		case "Trie":
    			newTitle = "Trie";
    			updateProgressView("Trie");
    			break;
    		case "Two Pointers":
    			newTitle = "Two Pointers";
    			updateProgressView("Two Pointers");
    			break;
    		default: // we consider all the questions for the progress bar
    			newTitle = "All 150 questions";
    			List<Problem> problems = SHARED_DATA.getCompletedProblems();
    			this.numberOfQuestionsCompleted = problems.size();
    			this.numberOfQuestions = this.numberOfQuestionsCompleted +  SHARED_DATA.getNotCompletedProblems().size();
    			this.easyQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Easy").size();
    			this.mediumQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Medium").size();
    			this.hardQuestions = SHARED_DATA.filterByLeetcodeDifficulty(problems, "Hard").size();
    			break;
    	}    	
    	
    	questionCategoryLabel.setText(newTitle); // update the progress bar label
    	topicList.setText(newTitle); // indicate selected topic on the drop down menu
    	
    	easyCount.setText("Easy: " + this.easyQuestions); // update the displayed count of easy questions
    	
    	mediumCount.setText("Medium: " + this.mediumQuestions); // update the displayed count of medium questions
    	
    	hardCount.setText("Hard: " + this.hardQuestions); // update the displayed count of hard questions
    	
    	// Update the label with the progress 
    	generalProgressLabel.setText(String.format("Progress: %.0f / %.0f", this.numberOfQuestionsCompleted, this.numberOfQuestions));
        
        drawProgress( (this.numberOfQuestionsCompleted / this.numberOfQuestions) * 100);  // draw the progress bar
    	
    }
    
}