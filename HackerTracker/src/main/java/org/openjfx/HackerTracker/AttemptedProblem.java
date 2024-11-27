package org.openjfx.HackerTracker;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleIntegerProperty;
public class AttemptedProblem {
    private SimpleIntegerProperty problemId;
    private LocalDateTime timeCompleted;

    public AttemptedProblem(int problemId, LocalDateTime timeCompleted) {
        this.problemId = new SimpleIntegerProperty(problemId);
        this.timeCompleted = timeCompleted;
    }

    public int getProblemId() {
        return problemId.get();
    }

    public void setProblemId(int problemId) {
        this.problemId.set(problemId);
    }

    public LocalDateTime getTimeCompleted() {
        return timeCompleted;
    }

    public void setTimeCompleted(LocalDateTime timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    @Override
    public String toString() {
        return "AttemptedProblem{" +
                "problemId=" + problemId.get() +
                ", timeCompleted=" + timeCompleted +
                '}';
    }
}