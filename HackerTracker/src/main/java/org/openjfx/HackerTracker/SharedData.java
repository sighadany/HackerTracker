package org.openjfx.HackerTracker;

import java.util.HashMap;
import java.util.Map;

public class SharedData {
    private static final SharedData instance = new SharedData();

    private final Map<String, Integer> questionsPerDay = new HashMap<>();

    private SharedData() { }

    public static SharedData getInstance() {
        return instance;
    }

    public Map<String, Integer> getQuestionsPerDay() {
        return questionsPerDay;
    }
}

