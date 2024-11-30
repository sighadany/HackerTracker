package org.openjfx.HackerTracker;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * A class that stores information about the leetcode problems 
 * and the user specific info related to the problem
 * 
 * @author Daniel Klevak
 * @version 1.0
 */
public abstract class AbstractProblem {
    private SimpleIntegerProperty problemId;
    private SimpleIntegerProperty priority;
    private SimpleStringProperty questionTitle;
    private SimpleStringProperty topicName;
    private SimpleStringProperty link;
    private SimpleStringProperty difficultyLevel;
    private SimpleStringProperty tag;
    
    /**
     * Constructor of the AbstractProblem class
     * 
     * @param problemId the unique id of the leetcode problem
     * @param priority the priority of the leetcode problem based on past attempts
     * @param questionTitle the name of the leetcode question
     * @param topicName the name of the topic the leetcode question belongs to
     * @param link the URL to the leetcode problem
     * @param difficultyLevel the difficulty of the problem on the leetcode platform
     * @param tag the subtopics of the leetcode question 
     */
    public AbstractProblem(int problemId, int priority, String questionTitle, String topicName, String link, String difficultyLevel, String tag) {
        this.problemId = new SimpleIntegerProperty(problemId);
        this.priority = new SimpleIntegerProperty(priority);
        this.questionTitle = new SimpleStringProperty(questionTitle);
        this.topicName = new SimpleStringProperty(topicName);
        this.link = new SimpleStringProperty(link);
        this.difficultyLevel = new SimpleStringProperty(difficultyLevel);
        this.tag = new SimpleStringProperty(tag);
    }

    /**
     * A getter method that returns the problem id
     */
    public int getProblemId() {
        return problemId.get();
    }
    
    /**
     * A setter method that defined the problem id
     */
    public void setProblemId(int problemId) {
        this.problemId.set(problemId);
    }
    
    /**
     * A getter method that returns the question priority
     */
    public int getPriority() {
        return priority.get();
    }
    
    /**
     * A setter method that defines the question priority
     */
    public void setPriority(int priority) {
        this.priority.set(priority);
    }
    
    /**
     * A getter method that returns the question title
     */
    public String getQuestionTitle() {
        return questionTitle.get();
    }
    
    /**
     * A setter method that defines the question title
     */
    public void setQuestionTitle(String questionTitle) {
        this.questionTitle.set(questionTitle);
    }
    
    /**
     * A getter method that returns the question topic
     */
    public String getTopicName() {
        return topicName.get();
    }
    
    /**
     * A setter method that defines the question topic
     */
    public void setTopicName(String topicName) {
        this.topicName.set(topicName);
    }
    
    /**
     * A getter method returns the URL to the question
     */
    public String getLink() {
        return link.get();
    }
    
    /**
     * A setter method that defines the URL of the question
     */
    public void setLink(String link) {
        this.link.set(link);
    }

    /**
     * A getter method returns the leetcode difficulty of the question
     */
    public String getDifficultyLevel() {
        return difficultyLevel.get();
    }
    
    /**
     * A setter method that sets the difficulty of a question
     */
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel.set(difficultyLevel);
    }
    
    /**
     * A getter method that returns the subtopics of a question
     */
    public String getTag() {
        return tag.get();
    }
    
    /**
     * A setter method that sets the subtopics of a question
     */
    public void setTag(String tag) {
        this.tag.set(tag);;
    }
    
    /**
     * Returns a String version of the class with it properties
     */
    @Override
    public String toString() {
        return "AbstractProblem{" +
                "problemId=" + problemId.get() +
                ", priority=" + priority.get() +
                ", questionTitle='" + questionTitle.get() + '\'' +
                ", topicName='" + topicName.get() + '\'' +
                ", link='" + link.get() + '\'' +
                ", difficultyLevel='" + difficultyLevel.get() + '\'' +
                ", tag=" + tag +
                '}';
    }
}
