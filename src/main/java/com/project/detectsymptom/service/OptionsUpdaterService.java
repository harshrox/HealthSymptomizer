package com.project.detectsymptom.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class OptionsUpdaterService {

    @Autowired
    private ApplicationContext context;

    public void updateOptions(String newOption) throws Exception {
        String filePath = "src/main/resources/static/dataOptions.json";
        File file = new File(filePath);
        JsonNode rootNode;
        try {
            ObjectMapper mapper = new ObjectMapper();
            rootNode = mapper.readTree(file);
        } catch (IOException e) {
            throw new Exception("Error reading options.json: " + e.getMessage());
        }

        // Ensure the root node is an object and contains the "options" array
        if (!rootNode.isObject() || !rootNode.has("options")) {
            throw new Exception("options.json does not have the expected structure.");
        }
        // Get the "options" array node
        ArrayNode optionsArray = (ArrayNode) rootNode.get("options");

        // Add new option to the "options" array
        optionsArray.add(newOption);

        // Write updated data to a file in a writable location
        try (FileWriter writer = new FileWriter(filePath)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, rootNode);
        } catch (IOException e) {
            throw new Exception("Error writing options.json: " + e.getMessage());
        }
    }
}
