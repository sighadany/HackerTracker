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

    @Override
    public AttemptedProblem markCompleted(int problemId) {
        this.numberOfCompletedAttempts++;
        this.timeOfLastAttempt = LocalDateTime.now();
        return new AttemptedProblem(problemId, timeOfLastAttempt);
    }

    // Getters and Setters
    // Override toString() if needed
}
