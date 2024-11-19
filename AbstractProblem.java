import java.util.List;

public abstract class AbstractProblem {
    private int problemId;
    private int priority;
    private String questionTitle;
    private String topicName;
    private String link;
    private String difficultyLevel;
    private List<String> tag;

    public AbstractProblem(int problemId, int priority, String questionTitle, String topicName, String link, String difficultyLevel, List<String> tag) {
        this.problemId = problemId;
        this.priority = priority;
        this.questionTitle = questionTitle;
        this.topicName = topicName;
        this.link = link;
        this.difficultyLevel = difficultyLevel;
        this.tag = tag;
    }

    public abstract AttemptedProblem markCompleted(int problemId);

    // Getters and Setters
    // Override toString() if needed
}
