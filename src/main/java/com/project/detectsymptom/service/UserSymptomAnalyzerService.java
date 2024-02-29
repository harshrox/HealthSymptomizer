package com.project.detectsymptom.service;


import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserSymptomAnalyzerService {
    @Autowired
    private ApplicationContext context;

    public Map<String, Double> analyzeUserSymptoms(String[] userSymptoms) throws Exception {

        // 1. Load data from data.json
        Resource resource = context.getResource("classpath:data.json");
        String jsonData;
        try {
            jsonData = new String(resource.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new Exception("Error reading data.json: " + e.getMessage());
        }

        // 2. Parse JSON
        JSONParser parser = new JSONParser();
        JSONArray diseases = (JSONArray) parser.parse(jsonData);

        // 3. Analyze user symptoms
        Map<String, Double> diseaseScores = new HashMap<>();
        for (Object diseaseObj : diseases) {
            JSONObject disease = (JSONObject) diseaseObj;
            String diseaseName = (String) disease.get("Disease");

            JSONArray diseaseSymptoms = (JSONArray) disease.get("Symptoms");
            @SuppressWarnings("unchecked")
            List<String> diseaseSymptomsList = (List<String>) diseaseSymptoms.stream().map(Object::toString).collect(Collectors.toList());

            // Calculate matching percentage
            int matchedCount = 0;
            for (String userSymptom : userSymptoms) {
                if (diseaseSymptomsList.contains(userSymptom.toLowerCase())) {
                    matchedCount++;
                }
            }
            double percentage = (double) matchedCount / diseaseSymptomsList.size() * 100;

            // Store the percentage only if it's not 10%
            if (percentage > 10) {
                Double currentMaxPercentage = diseaseScores.getOrDefault(diseaseName, 0.0);
                diseaseScores.put(diseaseName, Math.max(percentage, currentMaxPercentage));
            }
        }


        // 4. Sort the map by percentage (descending order)
        List<Map.Entry<String, Double>> sortedDiseases = new ArrayList<>(diseaseScores.entrySet());
        sortedDiseases.sort(new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2) {
                return Double.compare(entry2.getValue(), entry1.getValue());
            }
        });

        // 5. Return the sorted map
        Map<String, Double> resultMap = new HashMap<>();
        for (Map.Entry<String, Double> entry : sortedDiseases) {
            resultMap.put(entry.getKey(), entry.getValue());
        }
        return resultMap;
    }
}
