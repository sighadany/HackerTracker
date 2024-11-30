package org.openjfx.HackerTracker;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;

/**
 * A class that stores information about the leetcode problems 
 * and the user specific info related to the problem
 * 
 * @author Daniel Klevak, Sarthak Mallick
 * @version 1.0
 */
public class Problem extends AbstractProblem {
    private SimpleIntegerProperty difficultyRating;
    private SimpleIntegerProperty timeSpentOnQuestion;
    private SimpleStringProperty notes;
    private boolean isCompleted;
    private LocalDateTime timeOfLastAttempt;
    private boolean isScheduled;
    
    /**
     * Constructor of the Problem class
     * 
     * @param problemId the unique id of the leetcode problem
     * @param priority the priority of the leetcode problem based on past attempts
     * @param questionTitle the name of the leetcode question
     * @param topicName the name of the topic the leetcode question belongs to
     * @param link the URL to the leetcode problem
     * @param difficultyLevel the difficulty of the problem on the leetcode platform
     * @param tag the subtopics of the leetcode question 
     * @param difficultyRating the user assigned difficulty for the leetcode problem
     * @param timeSpentOnQuestion the time in minutes the user reported spending on the question
     * @param notes the notes entered by the user to remember how to solve similar questions
     * @param isCompleted a flag to indicate if the problem is completed
     * @param timeOfLastAttempt a string of the time the question was last attempted
     * @param isScheduled a flag that indicates if the question is currently in the schedule
     */
    public Problem(int problemId, int priority, String questionTitle, String topicName, String link, String difficultyLevel,
                   String tag, int difficultyRating, int timeSpentOnQuestion, String notes,
                   boolean isCompleted, LocalDateTime timeOfLastAttempt, boolean isScheduled) {
        super(problemId, priority, questionTitle, topicName, link, difficultyLevel, tag);
        this.difficultyRating = new SimpleIntegerProperty(difficultyRating);
        this.timeSpentOnQuestion = new SimpleIntegerProperty(timeSpentOnQuestion);
        this.notes = new SimpleStringProperty(notes);
        this.isCompleted = isCompleted;
        this.timeOfLastAttempt = timeOfLastAttempt;
        this.isScheduled = isScheduled;
    }
    
    /**
     * A getter method that returns the user defined difficulty of the question
     */
    public int getDifficultyRating() {
        return difficultyRating.get();
    }
    
    /**
     * A setter method that let's the user assign a difficulty to a question
     */
    public void setDifficultyRating(int difficultyRating) {
        this.difficultyRating.set(difficultyRating);
    }
    
    /**
     * A getter method that returns the time the user spent on a question
     */
    public int getTimeSpentOnQuestion() {
        return timeSpentOnQuestion.get();
    }
    
    /**
     * A setter method that let's the user specify how much time they spent on a question
     */
    public void setTimeSpentOnQuestion(Integer timeSpentOnQuestion) {
        this.timeSpentOnQuestion.set(timeSpentOnQuestion);
    }
    
    /**
     * A getter method that returns the notes entered by the user for a specific question
     */
    public String getNotes() {
        return notes.get();
    }
    
    /**
     * A setter method that let's the user add notes for a specific question
     */
    public void setNotes(String notes) {
        this.notes.set(notes);
    }
    
    /**
     * A getter method that returns a flag that indicates if the question was completed or not
     */
    public boolean getIsCompleted() {
        return isCompleted;
    }
    
    /**
     * A setter method that let's the user mark a question as completed
     */
    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    /**
     * A getter method that returns a flag that indicates if the question is scheduled
     */
    public boolean getIsScheduled() {
        return isScheduled;
    }

    /**
     * A setter method that marks a question as scheduled
     */
    public void setIsScheduled(boolean isScheduled) {
        this.isScheduled = isScheduled;
    }
    
    /**
     * A getter method that returns the time when the question was last attempted
     */
    public LocalDateTime getTimeOfLastAttempt() {
        return timeOfLastAttempt;
    }
    
    /**
     * A setter method that specifies the time a question was last completed
     */
    public void setTimeOfLastAttempt(LocalDateTime timeOfLastAttempt) {
        this.timeOfLastAttempt = timeOfLastAttempt;
    }
    
    /**
     * Returns a String version of the class with it properties
     * @see AbstractProblem
     */
    @Override
    public String toString() {
        return "Problem{" +
                "difficultyRating=" + difficultyRating.get() +
                ", timeSpentOnQuestion=" + timeSpentOnQuestion +
                ", notes='" + notes.get() + '\'' +
                ", isCompleted=" + isCompleted +
                ", timeOfLastAttempt=" + timeOfLastAttempt +
                ", isScheduled=" + isScheduled +
                "} " + super.toString();
    }
}
