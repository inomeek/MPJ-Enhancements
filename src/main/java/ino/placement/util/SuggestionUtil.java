package ino.placement.util;

import ino.placement.entity.AssessmentResult;

import java.util.*;

public class SuggestionUtil {

    public static List<String> getSuggestions(List<AssessmentResult> list) {

        Map<String, Double> avgMap = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        for (AssessmentResult a : list) {

            String type = a.getAssessmentType();

            double percent = (a.getScoreObtained() * 100.0) / a.getMaxScore();

            avgMap.put(type, avgMap.getOrDefault(type, 0.0) + percent);
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        List<String> suggestions = new ArrayList<>();

        for (String type : avgMap.keySet()) {

            double avg = avgMap.get(type) / countMap.get(type);

            if (avg < 40) {
                suggestions.add(type + ": Weak → Practice basics, daily problems");
            } else if (avg < 70) {
                suggestions.add(type + ": Average → Improve consistency");
            }
        }

        return suggestions;
    }
}