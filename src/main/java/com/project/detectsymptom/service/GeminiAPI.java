package com.project.detectsymptom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GeminiAPI {
    public List<String> gemini(List<String> userSymptoms){

        String prompt = "Suggest one most probable disease due to the symptoms "+userSymptoms+" , just return one disease along with its probability percentage of happening in the form of array like [disease , probability percentage without percentage symbol] ";
        //String prompt = "Suggest all the probable one/two words symptoms for migraine in an array form like [symptoms] , don't write any unnecessary text , just return the array of symptoms";

        GeminiService geminiService = new GeminiService();
        CompletableFuture<String> responseFuture = geminiService.generateContent(prompt);

        // Wait for the response
        String response = responseFuture.join();

        // Result
        List<String> result = new ArrayList<>();
        // Proceed with further processing using the response
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            // Assuming there's only one candidate in the response
            JsonNode contentNode = jsonNode.at("/candidates/0/content/parts/0/text");

            String input = contentNode.asText();

            // Remove square brackets and split by comma
            String[] elements = input.substring(1, input.length() - 1).split(", ");

            // Add elements to the result list
            for (String element : elements) {
                result.add(element);
            }




        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
