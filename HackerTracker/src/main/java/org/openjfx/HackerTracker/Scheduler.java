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
 * Stores the schedule of questions, maintains one static final instance shared amongst
 * the main view, settings and progress view controllers
 * 
 * @author Kavin Jha, Dany Sigha
 * @version 1.0
 */
public class Scheduler {
    private static final Scheduler INSTANCE = new Scheduler();
    
    private HashMap<String, List<Integer>> questionsPerDay = new HashMap<>(); // schedule of questions per day
    private HashMap<Integer, Problem> problemMapping = new HashMap<Integer, Problem>(); // mapping of questions to their identifiers
    private Map<String, List<Problem>> completedByTopic = new HashMap<>(); // mapping of completed questions to their topic
	private Map<String, List<Problem>> notCompletedByTopic = new HashMap<>(); // mapping of non completed questions to their topic
	private List<Problem> completed = new ArrayList<>(); // list of completed questions
	private List<Problem> notCompleted = new ArrayList<>(); // list of non completed questions
	private int scheduleWeekNumber; // schedule week number (a value between 1 and 52)
    
    
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
     * Getter method to provide access to the mapping of problems to their identifiers
     */
    public HashMap<Integer, Problem> getProblemMapping() {
        return this.problemMapping;
    }
    
    /**
     * Setter method to define the mapping of week days to their questions
     */
    public void setQuestionsPerDay( HashMap<String, List<Integer>> newSetOfQuestionsPerDay) {
    	this.questionsPerDay = newSetOfQuestionsPerDay;
    }
    
    /**
     * Setter method to define the mapping of problems to their identifiers
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
     * Schedules the next unscheduled and incomplete problem for the specified day.
     * 
     * This method iterates through the existing problem mappings to find the next problem 
     * that has not been scheduled and is incomplete. Once such a problem is found, 
     * it is added to the schedule for the given day, marking it as scheduled.
     * 
     * - Updates the problem's scheduled status to true.
     * - Adds the problem ID to the schedule for the specified day.
     * - If no suitable problem is found, the method returns -1.
     * 
     * @param day A three-character string representing a day of the week (e.g., "Mon").
     *            It must match the keys used in the daily schedule (`questionsPerDay`).
     * @return `0` if a problem was successfully scheduled; `-1` if no problem could be scheduled.
     * 
     * @see Problem#getIsScheduled Retrieves the problem's scheduling status.
     * @see Problem#setIsScheduled Updates the problem's scheduling status.
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
     * Unschedules the most recently scheduled problem for the specified day.
     * 
     * This method removes the last problem ID from the schedule for the given day 
     * (if any problems are scheduled) and updates the corresponding problem's 
     * scheduled status to `false`. This makes the problem inaccessible to all views.
     * 
     * - Checks if there are any problems scheduled for the specified day.
     * - If there is at least one problem, it removes the most recently scheduled one.
     * - Updates the problem's scheduled status to reflect its unscheduled state.
     * - If no problems are scheduled for the day, the method does nothing.
     * 
     * @param day A three-character string representing a day of the week (e.g., "Mon").
     *            It must match the keys used in the daily schedule (`questionsPerDay`).
     * 
     * @see Problem#setIsScheduled Updates the problem's scheduling status.
     */
    public void unScheduledProblem(String day) {
    	int numberOfQuestions = this.questionsPerDay.get(day).size();
    	if ( numberOfQuestions > 0 ) {
    		Integer lastPorblemId = this.questionsPerDay.get(day).remove(numberOfQuestions - 1);
        	Problem lastProblem = this.problemMapping.get(lastPorblemId);
        	lastProblem.setIsScheduled(false);
    	}
    }
    
    /**
     * Updates the weekly schedule to reflect the current week's status.
     * 
     * This method performs the following tasks:
     * - Determines the current week number using the system's date.
     * - Checks if the current week number is greater than the stored `scheduleWeekNumber`.
     * - If the week has advanced, iterates through the scheduled questions for each day and:
     *   - Removes completed problems from the daily schedule.
     *   - Marks these problems as unscheduled in the problem mapping.
     *   - Schedules the next available problem for the day to replace the completed one, if applicable.
     * - Updates the stored `scheduleWeekNumber` to the current week number.
     * 
     * **Key operations:**
     * - Uses `LocalDate.now()` to retrieve the current date.
     * - Handles scheduling adjustments using a copy of the problem IDs to avoid 
     *   `ConcurrentModificationException` during iteration.
     * - Invokes `scheduleNextProblem` to maintain the daily schedule.
     * 
     * **Notes:**
     * - Ensures that completed problems do not persist in the schedule across weeks.
     * - Maintains synchronization between the schedule and the problem mapping.
     * - Updates the internal state to match the progression of time.
     * 
     * @see Problem#setIsScheduled Updates the scheduling status of a problem.
     * @see #scheduleNextProblem Schedules a new problem to fill any gaps in the daily schedule.
     */
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

        }
        
        this.scheduleWeekNumber = currentWeekNumber;
    }
    
    /**
     * Categorizes a problem based on its completion status and topic.
     * 
     * This method assigns a `Problem` to one of two categories:
     * - **Completed Problems:** Added to the `completedByTopic` map and the `completed` list.
     * - **Not Completed Problems:** Added to the `notCompletedByTopic` map and the `notCompleted` list.
     * 
     * **Steps:**
     * - Retrieves the topic name from the provided `Problem` object.
     * - Checks the completion status of the problem using `Problem#getIsCompleted`.
     * - If the problem is completed:
     *   - Adds it to the `completedByTopic` map under its topic. If the topic does not exist in the map, 
     *     a new list is created and the problem is added to this list.
     *   - Adds the problem to the global `completed` list.
     * - If the problem is not completed:
     *   - Adds it to the `notCompletedByTopic` map under its topic. If the topic does not exist in the map, 
     *     a new list is created and the problem is added to this list.
     *   - Adds the problem to the global `notCompleted` list.
     * 
     * **Key Notes:**
     * - Uses `computeIfAbsent` to handle the initialization of topic-based lists within maps.
     * - Ensures that the problem is correctly categorized based on both completion status and topic.
     * - Updates global lists (`completed` and `notCompleted`) for quick access to all problems in their respective categories.
     * 
     * @param problem The `Problem` object to be categorized.
     * @see Problem#getIsCompleted Retrieves the completion status of the problem.
     * @see Problem#getTopicName Retrieves the topic associated with the problem.
     */
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
    
    /**
     * Updates the completion status of a given problem and re-categorizes it based on the new status.
     * 
     * This method toggles the completion status of a problem:
     * - If the problem is currently marked as completed, it is moved to the "not completed" category.
     * - If the problem is currently marked as not completed, it is moved to the "completed" category.
     * 
     * **Steps:**
     * - Retrieves the topic of the provided `Problem` using `Problem#getTopicName`.
     * - If the problem is completed:
     *   - Sets the problem’s completion status to `false` (marking it as not completed).
     *   - Removes the problem from the `completedByTopic` map and the `completed` list.
     * - If the problem is not completed:
     *   - Sets the problem’s completion status to `true` (marking it as completed).
     *   - Removes the problem from the `notCompletedByTopic` map and the `notCompleted` list.
     * - After updating the completion status, the problem is re-categorized using the `categorizeProblem` method.
     * 
     * **Key Notes:**
     * - Uses `getOrDefault` to handle cases where a topic may not exist in the corresponding map, ensuring no `NullPointerException` occurs.
     * - The `categorizeProblem` method is invoked to update the categorization in both the topic-based maps and the global lists based on the new status.
     * 
     * @param problem The `Problem` object whose completion status is to be updated.
     * @see Problem#getIsCompleted Returns the current completion status of the problem.
     * @see Problem#setIsCompleted Updates the completion status of the problem.
     * @see Problem#getTopicName Retrieves the topic name associated with the problem.
     * @see #categorizeProblem Re-categorizes the problem after its completion status has changed.
     */
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
    
    /**
     * Filters a list of problems based on the specified Leetcode difficulty level.
     * 
     * This method iterates over the provided list of problems and checks if each problem's difficulty level
     * matches the specified difficulty. If a match is found, the problem is added to a new list of filtered problems.
     * 
     * **Steps:**
     * - Iterates over each `Problem` in the provided list.
     * - Compares the problem's difficulty level (retrieved via `Problem#getDifficultyLevel`) with the provided difficulty string.
     * - If the problem's difficulty matches, it is added to the list `filteredProblems`.
     * - Once all problems have been checked, the filtered list is returned.
     * 
     * **Key Notes:**
     * - The method assumes that `Problem#getDifficultyLevel` returns a string representing the difficulty level, such as "Easy", "Medium", or "Hard".
     * - The comparison is case-sensitive; ensure the provided difficulty matches the format of the difficulty level exactly.
     * 
     * @param problems The list of `Problem` objects to be filtered.
     * @param difficulty The difficulty level to filter problems by (e.g., "Easy", "Medium", "Hard").
     * @return A list of `Problem` objects that match the specified difficulty level.
     * @see Problem#getDifficultyLevel Retrieves the difficulty level of the problem.
     */
    public List<Problem> filterByLeetcodeDifficulty(List<Problem> problems, String difficulty) {
    	List<Problem> filteredProblems = new ArrayList<>();
    	for (Problem p : problems) {
    		if (p.getDifficultyLevel().equals(difficulty)) {
    			filteredProblems.add(p);
    		}
    	}

    	return filteredProblems;
    }
    
    /**
     * Returns a String version of the class with its properties
     */
    @Override
    public String toString() {
    	return "Schedule{ " +
    			"scheduleWeekNumber=" + scheduleWeekNumber +
    			", questionsPerDay=" + questionsPerDay +
    			", problemMapping=" + problemMapping +
                ", completedByTopic=" + completedByTopic +
                ", notCompletedByTopic=" + notCompletedByTopic +
                ", completed=" + completed +
                ", notCompleted=" + notCompleted +
                '}';
    }
    
   
    
}

