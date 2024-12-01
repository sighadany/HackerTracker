package org.openjfx.HackerTracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * Stores the schedule of questions, maintaining one static final instance shared amongst
 * the main view, settings and progress view controllers
 * 
 * @author Kavin Jha, Dany Sigha
 * @version 1.0
 */
public class Scheduler {
    private static final Scheduler INSTANCE = new Scheduler();
//    private static final ProblemTracker TRACKER_INSTANCE = ProblemTracker.getInstance();

    private HashMap<String, List<Integer>> questionsPerDay = new HashMap<>();
    private HashMap<Integer, Problem> problemMapping = new HashMap<Integer, Problem>();
    
    private Map<String, List<Problem>> completedByTopic = new HashMap<>();
	private Map<String, List<Problem>> notCompletedByTopic = new HashMap<>();
	private List<Problem> completed = new ArrayList<>();
	private List<Problem> notCompleted = new ArrayList<>();
	private int scheduleWeekNumber;
    
    
    /**
     * Empty constructor of the Scheduler class
     */
    private Scheduler() { }
    
    /**
     * Getter method to provide access to the one Scheduler instance used by all views to 
     * preserve data and the state of the views
     */
    public static Scheduler getInstance() {
        return INSTANCE;
    }
    
    /**
     * Getter method to provide access to the week number of the schedule
     */
    public int getScheduleWeekNumber() {
        return this.scheduleWeekNumber;
    }
    
    /**
     * Setter method to define the week number of the schedule
     */
    public void setScheduleWeekNumber(int newScheduleWeekNumber) {
    	this.scheduleWeekNumber = newScheduleWeekNumber;
    }
    
    /**
     * Getter method to provide access to the mapping of weekdays to their questions
     */
    public HashMap<String, List<Integer>> getQuestionsPerDay() {
        return this.questionsPerDay;
    }
    
    /**
     * Getter method to provide access to the mapping of problems to their ids
     */
    public HashMap<Integer, Problem> getProblemMapping() {
        return this.problemMapping;
    }
    
    /**
     * Setter method to define the mapping of weekdays to their questions
     */
    public void setQuestionsPerDay( HashMap<String, List<Integer>> newSetOfQuestionsPerDay) {
    	this.questionsPerDay = newSetOfQuestionsPerDay;
    }
    
    /**
     * Setter method to define the mapping of problems to their ids
     */
    public void setProblemMapping( HashMap<Integer, Problem> newSetOfProblemMappings) {
    	this.problemMapping = newSetOfProblemMappings;
    	
    	for (Map.Entry<Integer, Problem> entry : this.problemMapping.entrySet()) { 
    		Problem problem = entry.getValue();
    		categorizeProblem(problem);
    	}
    }
    
    /**
     * Returns all the completed problems
     */
    public List<Problem> getCompletedProblems() {
        return this.completed;
    }
    
    /**
     * Returns all the non completed problems
     */
    public List<Problem> getNotCompletedProblems() {
        return this.notCompleted;
    }
    
    /**
     * Returns all the completed problems of a specified topic
     * 
     * @param topic the String of the topic name
     */
    public List<Problem> getCompletedProblemsByTopic(String topic) {
        return completedByTopic.getOrDefault(topic, Collections.emptyList());
    }
    
    /**
     * Returns all the non completed problems of a specified topic
     * 
     * @param topic the String of the topic name
     */
    public List<Problem> getNotCompletedProblemsByTopic(String topic) {
        return notCompletedByTopic.getOrDefault(topic, Collections.emptyList());
    }
    
    /**
     * Upon adding questions in the settings view, this method is invoked
     * to schedule the next question, making it accessible to all the views
     * 
     * @param day the three character String representing a weekday 
     * @see Problem#getIsScheduled
     * @see Problem#setIsScheduled
     */
    public int scheduleNextProblem(String day) {
    	for (Map.Entry<Integer, Problem> entry : this.problemMapping.entrySet()) {
    		Integer key = entry.getKey();
            Problem problem = entry.getValue();
            if(problem.getIsScheduled() == false && problem.getIsCompleted() == false) {
            	this.questionsPerDay.get(day).add(key);
            	problem.setIsScheduled(true);
            	return 0;
            }
        }
    	return -1;
    }
    
    /**
     * Upon removing questions in the settings view, this method is invoked
     * to mark a question as unscheduled on the specified day, making it inaccessible to all the views
     * 
     * @param day the three character String representing a weekday 
     * @see Problem#setIsScheduled
     */
    public void unScheduledProblem(String day) {
    	int numberOfQuestions = this.questionsPerDay.get(day).size();
    	if ( numberOfQuestions > 0 ) {
    		Integer lastPorblemId = this.questionsPerDay.get(day).remove(numberOfQuestions - 1);
        	Problem lastProblem = this.problemMapping.get(lastPorblemId);
        	lastProblem.setIsScheduled(false);
    	}
    }
    
    
    public void updateSchedule() {
    	// Get the current date
        LocalDate currentDate = LocalDate.now();
        
        // Get the current week number
        int currentWeekNumber = currentDate.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        
        if (currentWeekNumber > this.scheduleWeekNumber) {
        	for (Map.Entry<String, List<Integer>> entry : this.questionsPerDay.entrySet()) {
        		String dayKey = entry.getKey();
                List<Integer> problemIds = entry.getValue();
        		List<Integer> problemIdsCopy = new ArrayList<>(entry.getValue());

                
                for(Integer id : problemIdsCopy) {
                	Problem problem = this.problemMapping.get(id);
                	if(problem.getIsCompleted()) {
                		problemIds.remove(id);
                		problem.setIsScheduled(false);
                		scheduleNextProblem(dayKey);
                	}
                }
                
            }
        	
        	this.scheduleWeekNumber = currentWeekNumber;
        }
    }
    
 // Add a problem to the tracker
    public void categorizeProblem(Problem problem) {
        String topic = problem.getTopicName();
        if (problem.getIsCompleted()) {
            completedByTopic.computeIfAbsent(topic, k -> new ArrayList<>()).add(problem);
            completed.add(problem);
        } else {
            notCompletedByTopic.computeIfAbsent(topic, k -> new ArrayList<>()).add(problem);
            notCompleted.add(problem);
        }
    }
    
  
    
 // Update the completion status of a problem
    public void updateProblemCompletionStatus(Problem problem) {
        String topic = problem.getTopicName();

        // Remove from current list
        if (problem.getIsCompleted()) {
        	problem.setIsCompleted(false);
            completedByTopic.getOrDefault(topic, new ArrayList<>()).remove(problem);
            completed.remove(problem);
        } else {
        	problem.setIsCompleted(true);
            notCompletedByTopic.getOrDefault(topic, new ArrayList<>()).remove(problem);
            notCompleted.remove(problem);
        }

        categorizeProblem(problem);
    }
    
    // filter by leetcode difficulty
    public List<Problem> filterByLeetcodeDifficulty(List<Problem> problems, String difficulty) {
    	List<Problem> filteredProblems = new ArrayList<>();
    	for (Problem p : problems) {
    		if (p.getDifficultyLevel().equals(difficulty)) {
    			filteredProblems.add(p);
    		}
    	}
//    	System.out.print(problems);
//    	System.out.print(problems);
    	return filteredProblems;
    }
    
    @Override
    public String toString() {
        return "ProblemTracker{" +
                "completedByTopic=" + completedByTopic +
                ", notCompletedByTopic=" + notCompletedByTopic +
                '}';
    }
    
}

