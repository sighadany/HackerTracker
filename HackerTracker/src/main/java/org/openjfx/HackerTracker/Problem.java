package org.openjfx.HackerTracker;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;

public class Problem extends AbstractProblem {
    private SimpleIntegerProperty difficultyRating;
    private SimpleIntegerProperty timeSpentOnQuestion;
    private SimpleStringProperty notes;
    private SimpleIntegerProperty numberOfCompletedAttempts;
    private LocalDateTime timeOfLastAttempt;

    public Problem(int problemId, int priority, String questionTitle, String topicName, String link, String difficultyLevel,
                   String tag, int difficultyRating, int timeSpentOnQuestion, String notes,
                   int numberOfCompletedAttempts, LocalDateTime timeOfLastAttempt) {
        super(problemId, priority, questionTitle, topicName, link, difficultyLevel, tag);
        this.difficultyRating = new SimpleIntegerProperty(difficultyRating);
        this.timeSpentOnQuestion = new SimpleIntegerProperty(timeSpentOnQuestion);
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

    public int getTimeSpentOnQuestion() {
        return timeSpentOnQuestion.get();
    }

    public void setTimeSpentOnQuestion(Integer timeSpentOnQuestion) {
        this.timeSpentOnQuestion.set(timeSpentOnQuestion);
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
