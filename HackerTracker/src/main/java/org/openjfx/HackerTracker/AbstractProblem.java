package org.openjfx.HackerTracker;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class AbstractProblem {
    private SimpleIntegerProperty problemId;
    private SimpleIntegerProperty priority;
    private SimpleStringProperty questionTitle;
    private SimpleStringProperty topicName;
    private SimpleStringProperty link;
    private SimpleStringProperty difficultyLevel;
    private SimpleStringProperty tag;

    public AbstractProblem(int problemId, int priority, String questionTitle, String topicName, String link, String difficultyLevel, String tag) {
        this.problemId = new SimpleIntegerProperty(problemId);
        this.priority = new SimpleIntegerProperty(priority);
        this.questionTitle = new SimpleStringProperty(questionTitle);
        this.topicName = new SimpleStringProperty(topicName);
        this.link = new SimpleStringProperty(link);
        this.difficultyLevel = new SimpleStringProperty(difficultyLevel);
        this.tag = new SimpleStringProperty(tag);
    }


    public int getProblemId() {
        return problemId.get();
    }

    public void setProblemId(int problemId) {
        this.problemId.set(problemId);
    }

    public int getPriority() {
        return priority.get();
    }

    public void setPriority(int priority) {
        this.priority.set(priority);
    }

    public String getQuestionTitle() {
        return questionTitle.get();
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle.set(questionTitle);
    }

    public String getTopicName() {
        return topicName.get();
    }

    public void setTopicName(String topicName) {
        this.topicName.set(topicName);
    }

    public String getLink() {
        return link.get();
    }

    public void setLink(String link) {
        this.link.set(link);
    }

    public String getDifficultyLevel() {
        return difficultyLevel.get();
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel.set(difficultyLevel);
    }

    public String getTag() {
        return tag.get();
    }

    public void setTag(String tag) {
        this.tag.set(tag);;
    }

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
