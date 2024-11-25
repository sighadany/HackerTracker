package org.openjfx.HackerTracker.problems;
import java.util.List;
import java.util.Objects;

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

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "AbstractProblem{" +
                "problemId=" + problemId +
                ", priority=" + priority +
                ", questionTitle='" + questionTitle + '\'' +
                ", topicName='" + topicName + '\'' +
                ", link='" + link + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                ", tag=" + tag +
                '}';
    }

    public abstract AttemptedProblem markCompleted(int problemId);
}
