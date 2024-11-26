package org.openjfx.HackerTracker;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;
import java.util.List;

public class Problem extends AbstractProblem {
    private SimpleIntegerProperty difficultyRating;
    private List<Integer> timeSpentOnQuestion;
    private SimpleStringProperty notes;
    private SimpleIntegerProperty numberOfCompletedAttempts;
    private LocalDateTime timeOfLastAttempt;

    public Problem(int problemId, int priority, String questionTitle, String topicName, String link, String difficultyLevel,
                   List<String> tag, int difficultyRating, List<Integer> timeSpentOnQuestion, String notes,
                   int numberOfCompletedAttempts, LocalDateTime timeOfLastAttempt) {
        super(problemId, priority, questionTitle, topicName, link, difficultyLevel, tag);
        this.difficultyRating = new SimpleIntegerProperty(difficultyRating);
        this.timeSpentOnQuestion = timeSpentOnQuestion;
        this.notes = new SimpleStringProperty(notes);
        this.numberOfCompletedAttempts = new SimpleIntegerProperty(numberOfCompletedAttempts);
        this.timeOfLastAttempt = timeOfLastAttempt;
    }
    
    public int getDifficultyRating() {
        return difficultyRating.get();
    }

    public void setDifficultyRating(int difficultyRating) {
        this.difficultyRating.set(difficultyRating);
    }

    public List<Integer> getTimeSpentOnQuestion() {
        return timeSpentOnQuestion;
    }

    public void setTimeSpentOnQuestion(List<Integer> timeSpentOnQuestion) {
        this.timeSpentOnQuestion = timeSpentOnQuestion;
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public int getNumberOfCompletedAttempts() {
        return numberOfCompletedAttempts.get();
    }

    public void setNumberOfCompletedAttempts(int numberOfCompletedAttempts) {
        this.numberOfCompletedAttempts.set(numberOfCompletedAttempts);
    }

    public LocalDateTime getTimeOfLastAttempt() {
        return timeOfLastAttempt;
    }

    public void setTimeOfLastAttempt(LocalDateTime timeOfLastAttempt) {
        this.timeOfLastAttempt = timeOfLastAttempt;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "difficultyRating=" + difficultyRating.get() +
                ", timeSpentOnQuestion=" + timeSpentOnQuestion +
                ", notes='" + notes.get() + '\'' +
                ", numberOfCompletedAttempts=" + numberOfCompletedAttempts.get() +
                ", timeOfLastAttempt=" + timeOfLastAttempt +
                "} " + super.toString();
    }

    @Override
    public AttemptedProblem markCompleted(int problemId) {
    	numberOfCompletedAttempts.set(numberOfCompletedAttempts.get()+1);
        this.timeOfLastAttempt = LocalDateTime.now();
        return new AttemptedProblem(problemId, timeOfLastAttempt);
    }
}
