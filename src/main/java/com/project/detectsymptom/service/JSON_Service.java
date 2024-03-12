package com.project.detectsymptom.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class JSON_Service {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private DataUpdaterService dataUpdaterService;

    public Map<Double, String> analyzeUserSymptoms(List<String> userSymptoms) throws Exception {
        String filePath = "data/data.json";
        JSONParser parser = new JSONParser();
        JSONArray diseases = null;
        String jsonData;
        try (FileReader reader = new FileReader(filePath)) {
            Object obj = parser.parse(reader);
            diseases = (JSONArray) obj;
        } catch (IOException e) {
            throw new Exception("Error reading data.json: " + e.getMessage());
        }


        Map<Double, String> diseaseScores = new TreeMap<>(Collections.reverseOrder());
        SymptomMatching check = new SymptomMatching();
        GeminiAPI geminiAPI = new GeminiAPI();
        for (Object diseaseObj : diseases) {
            JSONObject disease = (JSONObject) diseaseObj;
            String diseaseName = (String) disease.get("Disease");

            JSONArray diseaseSymptoms = (JSONArray) disease.get("Symptoms");
            @SuppressWarnings("unchecked")
            List<String> diseaseSymptomsList = (List<String>) diseaseSymptoms.stream().map(Object::toString).collect(Collectors.toList());

            List<String> output = check.match(diseaseName, diseaseSymptomsList, userSymptoms);
            if (Double.parseDouble(output.get(1)) > 30) {
                diseaseScores.put(Double.parseDouble(output.get(1)), output.get(0));
            }
        }

        if (diseaseScores.size() == 0) {
            String prompt = "Suggest all the probable diseases based on the symptoms provided. Return the results in json form having 3 fixed keys , Disease : string , Symptoms : array of strings and Precautions(one single string). Make sure NOT to return in markdown format but as raw string of array of jsons." +
                    "Also, write the other related symptoms(one/two words only) too since provided symptoms may be very few. The result should be of type json array and must follow the given format. It should not deviate from the instructions as the returned response is assumed to be json array and will be further used in the project, wrong format can crash the project. Do not include any other words or phrases just the json array." +
                    "The symptoms for the disease to be predicted are " + userSymptoms + ", make sure to include these symptoms too in the ouput";

            GeminiService geminiService = new GeminiService();
            CompletableFuture<String> responseFuture = geminiService.generateContent(prompt);

            String response = responseFuture.join();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response);
                JsonNode contentNode = jsonNode.at("/candidates/0/content/parts/0/text");
                System.out.println(contentNode);
                String jsonArrayString = contentNode.asText();
                JsonNode diseaseArray = objectMapper.readValue(jsonArrayString, JsonNode.class);
                for (int i = 0; i < diseaseArray.size(); i++) {
                    dataUpdaterService.updateData(diseaseArray.get(i));
                }

                return analyzeUserSymptoms(userSymptoms);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return diseaseScores;
    }
}

