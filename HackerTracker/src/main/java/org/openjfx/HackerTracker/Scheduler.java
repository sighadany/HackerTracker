package org.openjfx.HackerTracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores the schedule of questions
 * 
 * @author Dany Sigha, Kavin Jha
 * @version 1.0
 */
public class Scheduler {
    private static final Scheduler instance = new Scheduler();

    private HashMap<String, List<Integer>> questionsPerDay = new HashMap<>();
    private HashMap<Integer, Problem> problemMapping = new HashMap<Integer, Problem>();

    private Scheduler() { }

    public static Scheduler getInstance() {
        return instance;
    }

    public HashMap<String, List<Integer>> getQuestionsPerDay() {
        return this.questionsPerDay;
    }
    
    public HashMap<Integer, Problem> getProblemMapping() {
        return this.problemMapping;
    }
    
    public void setQuestionsPerDay( HashMap<String, List<Integer>> newSetOfQuestionsPerDay) {
    	this.questionsPerDay = newSetOfQuestionsPerDay;
    }
    
    public void setProblemMapping( HashMap<Integer, Problem> newSetOfProblemMappings) {
    	this.problemMapping = newSetOfProblemMappings;
    }
    
    public int scheduleNextProblem(String day) {
    	for (Map.Entry<Integer, Problem> entry : this.problemMapping.entrySet()) {
    		Integer key = entry.getKey();
            Problem problem = entry.getValue();
            if(problem.getIsScheduled() == false) {
            	this.questionsPerDay.get(day).add(key);
            	problem.setIsScheduled(true);
            	return 0;
            }
        }
    	return -1;
    }
    
    public void unScheduleProblem(String day) {
    	
    	int numberOfQuestions = this.questionsPerDay.get(day).size();
    	
    	if ( numberOfQuestions > 0 ) {
    		Integer lastPorblemId = this.questionsPerDay.get(day).remove(numberOfQuestions - 1);
        	
        	Problem lastProblem = this.problemMapping.get(lastPorblemId);
        	
        	lastProblem.setIsScheduled(false);
    	}
    }
}

