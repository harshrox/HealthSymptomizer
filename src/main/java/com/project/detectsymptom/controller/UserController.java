package com.project.detectsymptom.controller;


import com.project.detectsymptom.model.UserModel;
import com.project.detectsymptom.service.UserSymptomAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserSymptomAnalyzerService userSymptomAnalyzerService; // Inject the service

//    @PostMapping("/user")
//    public ResponseEntity<?> User(@RequestBody UserModel userModel) {
//
//        if (userModel.getUsername() == null || userModel.getAge() == null || userModel.getGender() == null || userModel.getSymptoms() == null) {
//            return ResponseEntity.badRequest().body("All fields are required.");
//        }
//
//        String username = userModel.getUsername();
//        String gender = userModel.getGender();
//        String age = userModel.getAge();
//        List<String> symptoms = userModel.getSymptoms();
//
//        Map<String, Double> analyzer;
//        try {
//            analyzer = userSymptomAnalyzerService.analyzeUserSymptoms(symptoms.toArray(new String[0])); // Use the injected service
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        return new ResponseEntity<>(analyzer, HttpStatus.OK);
//    }

    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody List<String> symptoms) {

        Map<String, Double> analyzer;
        try {
            analyzer = userSymptomAnalyzerService.analyzeUserSymptoms(symptoms); // Use the injected service
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(analyzer, HttpStatus.OK);
    }
}


