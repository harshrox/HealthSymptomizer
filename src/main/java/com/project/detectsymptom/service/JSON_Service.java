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
public class JSON_Service {
    @Autowired
    private ApplicationContext context;

    public Map<Double , String> analyzeUserSymptoms(List<String> userSymptoms) throws Exception {

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
        Map<Double ,  String> diseaseScores = new TreeMap<>(Collections.reverseOrder());
        SymptomMatching check = new SymptomMatching();
        GeminiAPI geminiAPI = new GeminiAPI();
        for (Object diseaseObj : diseases) {
            JSONObject disease = (JSONObject) diseaseObj;
            String diseaseName = (String) disease.get("Disease");

            JSONArray diseaseSymptoms = (JSONArray) disease.get("Symptoms");
            @SuppressWarnings("unchecked")
            List<String> diseaseSymptomsList = (List<String>) diseaseSymptoms.stream().map(Object::toString).collect(Collectors.toList());



            List<String> output = check.match(diseaseName , diseaseSymptomsList , userSymptoms);
            if(Double.parseDouble(output.get(1)) > 30){
                diseaseScores.put(Double.parseDouble(output.get(1)) , output.get(0));
            }


        }

        if(diseaseScores.size()==0){
            List<String> output = geminiAPI.gemini(userSymptoms);
            diseaseScores.put(Double.parseDouble(output.get(1)) , output.get(0));
        }


        return diseaseScores;
    }
}
