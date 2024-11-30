package org.openjfx.HackerTracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores the schedule of questions, maintaining one static final instance shared amongst
 * the main view, settings and progress view controllers
 * 
 * @author Kavin Jha, Dany Sigha
 * @version 1.0
 */
public class Scheduler {
    private static final Scheduler INSTANCE = new Scheduler();

    private HashMap<String, List<Integer>> questionsPerDay = new HashMap<>();
    private HashMap<Integer, Problem> problemMapping = new HashMap<Integer, Problem>();
    
    /**
     * Empty constructor of the Scheduler class
     */
    private Scheduler() { }
    
    /**
     * Getter method to provide access to the one Scheduler instance used by all view to 
     * preserve data and the state of the views
     */
    public static Scheduler getInstance() {
        return INSTANCE;
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
}

