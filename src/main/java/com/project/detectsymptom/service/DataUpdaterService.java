package com.project.detectsymptom.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class DataUpdaterService {

    @Autowired
    private ApplicationContext context;

    public void updateData(Object newDisease) throws Exception {
        String filePath = "data/data.json";
        File file = new File(filePath);;
        JsonNode diseases;
        try {
            ObjectMapper mapper = new ObjectMapper();
            diseases = mapper.readTree(file);
        } catch (IOException e) {
            throw new Exception("Error reading data.json: " + e.getMessage());
        }

        // Convert new data to JSON node (assuming newDisease is a POJO)
        ObjectMapper mapper = new ObjectMapper();
        JsonNode newJsonNode;
        newJsonNode = mapper.valueToTree(newDisease);

        // Add new disease
        ((ArrayNode) diseases).add(newJsonNode);

        // Write updated data to a file in a writable location
        try (FileWriter writer = new FileWriter("data/data.json")) {
            mapper.writeValue(writer, diseases);
        } catch (IOException e) {
            throw new Exception("Error writing data.json: " + e.getMessage());
        }

        String diseaseName;
        try {
            diseaseName = newJsonNode.get("Disease").asText();
        } catch (NullPointerException e) {
            // Handle potential missing field in the new disease data
            diseaseName = "Unknown";
        }

        // Append to log.txt in a writable location
        try (FileWriter logWriter = new FileWriter("logs/log.txt", true)) { // Append mode
            logWriter.write(String.format("Disease added: %s\n", newJsonNode.get("Disease").asText()));
        } catch (IOException e) {
            throw new Exception("Error writing log.txt: " + e.getMessage());
        }
    }
}

