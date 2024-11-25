package org.openjfx.HackerTracker.problems;
import java.time.LocalDateTime;
import java.util.List;

public class Problem extends AbstractProblem {
    private int difficultyRating;
    private List<Integer> timeSpentOnQuestion;
    private String notes;
    private int numberOfCompletedAttempts;
    private LocalDateTime timeOfLastAttempt;

    public Problem(int problemId, int priority, String questionTitle, String topicName, String link, String difficultyLevel,
                   List<String> tag, int difficultyRating, List<Integer> timeSpentOnQuestion, String notes,
                   int numberOfCompletedAttempts, LocalDateTime timeOfLastAttempt) {
        super(problemId, priority, questionTitle, topicName, link, difficultyLevel, tag);
        this.difficultyRating = difficultyRating;
        this.timeSpentOnQuestion = timeSpentOnQuestion;
        this.notes = notes;
        this.numberOfCompletedAttempts = numberOfCompletedAttempts;
        this.timeOfLastAttempt = timeOfLastAttempt;
    }

    public int getDifficultyRating() {
        return difficultyRating;
    }

    public void setDifficultyRating(int difficultyRating) {
        this.difficultyRating = difficultyRating;
    }

    public List<Integer> getTimeSpentOnQuestion() {
        return timeSpentOnQuestion;
    }

    public void setTimeSpentOnQuestion(List<Integer> timeSpentOnQuestion) {
        this.timeSpentOnQuestion = timeSpentOnQuestion;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getNumberOfCompletedAttempts() {
        return numberOfCompletedAttempts;
    }

    public void setNumberOfCompletedAttempts(int numberOfCompletedAttempts) {
        this.numberOfCompletedAttempts = numberOfCompletedAttempts;
    }

    public LocalDateTime getTimeOfLastAttempt() {
        return timeOfLastAttempt;
    }

    public void setTimeOfLastAttempt(LocalDateTime timeOfLastAttempt) {
        this.timeOfLastAttempt = timeOfLastAttempt;
    }

    @Override
    public AttemptedProblem markCompleted(int problemId) {
        this.numberOfCompletedAttempts++;
        this.timeOfLastAttempt = LocalDateTime.now();
        return new AttemptedProblem(problemId, timeOfLastAttempt);
    }

    @Override
    public String toString() {
        return "Problem{" +
                "difficultyRating=" + difficultyRating +
                ", timeSpentOnQuestion=" + timeSpentOnQuestion +
                ", notes='" + notes + '\'' +
                ", numberOfCompletedAttempts=" + numberOfCompletedAttempts +
                ", timeOfLastAttempt=" + timeOfLastAttempt +
                "} " + super.toString();
    }
}
