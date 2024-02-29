package com.project.detectsymptom.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.detectsymptom.model.UserModel;
import com.project.detectsymptom.service.GeminiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserContoller {
    @PostMapping("/user")
    public ResponseEntity<?> User(@RequestBody UserModel userModel){

        if(userModel.getUsername() == null || userModel.getAge() == null || userModel.getGender() == null || userModel.getSymptoms()== null){
            return ResponseEntity.badRequest().body("All fields are required.");
        }

        String username = userModel.getUsername();
        String gender = userModel.getGender();
        String age = userModel.getAge();
        List<String> symptoms = userModel.getSymptoms();
        System.out.println(userModel.toString());
        System.out.println(symptoms);




        UserModel user = new UserModel(username,gender,age,symptoms);



        return new ResponseEntity<>(user, HttpStatus.OK);


    }
    /*
  GEMINI_API_KEY = AIzaSyDx2Vq_FGhvCjpVfa9WTVQJSq62YTMs3-k
  PROJECT_ID = intense-climber-415609
**/
    @GetMapping("/gemini")
    public Object Gemini(){
        String prompt = "suggest the diseases name for symptoms list as follows: ['muscle weakness','stiff neck','swelling joints','movement stiffness','painfull walking']";

        GeminiService geminiService = new GeminiService();
        CompletableFuture<String> responseFuture = geminiService.generateContent(prompt);

        // Wait for the response
        String response = responseFuture.join();

        // Proceed with further processing using the response
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            // Assuming there's only one candidate in the response
            JsonNode contentNode = jsonNode.at("/candidates/0/content/parts/0/text");
            System.out.println(contentNode);

            return contentNode.asText();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }


    }

}
