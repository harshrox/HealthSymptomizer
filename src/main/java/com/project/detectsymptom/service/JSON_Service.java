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
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class JSON_Service {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private DataUpdaterService dataUpdaterService;
    @Autowired
    private OptionsUpdaterService optionsUpdaterService;

    public Map<String, List<String>> analyzeUserSymptoms(List<String> userSymptoms , int age) throws Exception {
        String filePath;
        if(age==0){
            filePath = "data/upto2months.json";
        }
        else if(age==1){
            filePath = "data/2monthsTo5Years.json";
        }
        else{
            filePath = "data/data.json";
        }

        JSONParser parser = new JSONParser();
        JSONArray diseases = null;
        String jsonData;
        try (FileReader reader = new FileReader(filePath)) {
            Object obj = parser.parse(reader);
            diseases = (JSONArray) obj;
        } catch (IOException e) {
            throw new Exception("Error reading data.json: " + e.getMessage());
        }


        Map<String, List<String>> diseaseScores = new HashMap<>();
        SymptomMatching check = new SymptomMatching();

        for (Object diseaseObj : diseases) {
            JSONObject disease = (JSONObject) diseaseObj;
            String diseaseName = (String) disease.get("Disease");
            String precaution = (String) disease.get("Precaution");

            JSONArray diseaseSymptoms = (JSONArray) disease.get("Symptoms");
            @SuppressWarnings("unchecked")
            List<String> diseaseSymptomsList = (List<String>) diseaseSymptoms.stream().map(Object::toString).collect(Collectors.toList());

            List<String> output = check.match(diseaseName, diseaseSymptomsList, userSymptoms);
            if (Double.parseDouble(output.get(1)) > 15) {
                diseaseScores.put(output.get(0), Arrays.asList(output.get(1) , precaution , String.valueOf(diseaseSymptoms)));
            }
        }

        if (diseaseScores.size() == 0 && age>5) {
            String prompt = "Suggest all the probable diseases based on the symptoms provided. Return the results in json form having 3 fixed keys , Disease : string , Symptoms : array of strings and Precaution(one single string). Make sure NOT to return in markdown format but as raw string of array of jsons." +
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
                    JsonNode disease = diseaseArray.get(i);
                    JsonNode symptomsNode = disease.get("Symptoms");
                    if (symptomsNode.isArray()) {
                        for (JsonNode symptom : symptomsNode) {
                            optionsUpdaterService.updateOptions(symptom.asText());
                        }
                    }
                }

                return analyzeUserSymptoms(userSymptoms , age);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Sorting the hashmap on the basis of percentage probability

        // Convert the entries of the HashMap to a List
        List<Map.Entry<String, List<String>>> entryList = new ArrayList<>(diseaseScores.entrySet());

        // Sort the List based on the first value of the List<String> in each entry
        Collections.sort(entryList, (entry1, entry2) -> Double.compare(
                Double.parseDouble(entry2.getValue().get(0)),
                Double.parseDouble(entry1.getValue().get(0))));

        // Create a LinkedHashMap to store the sorted entries
        int count = 0;    // To show top 7 most probable diseases
        LinkedHashMap<String, List<String>> sortedScores = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : entryList) {
            sortedScores.put(entry.getKey(), entry.getValue());
            count++;
            if(count == 7){
                break;
            }
        }


        return sortedScores;
    }
}

