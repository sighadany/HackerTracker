import java.time.LocalDateTime;

public class AttemptedProblem {
    private int problemId;
    private LocalDateTime timeCompleted;

    public AttemptedProblem(int problemId, LocalDateTime timeCompleted) {
        this.problemId = problemId;
        this.timeCompleted = timeCompleted;
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
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
                "problemId=" + problemId +
                ", timeCompleted=" + timeCompleted +
                '}';
    }
}
