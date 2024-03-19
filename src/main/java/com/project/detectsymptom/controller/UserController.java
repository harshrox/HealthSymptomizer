package com.project.detectsymptom.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.detectsymptom.model.UserModel;
import com.project.detectsymptom.service.DataUpdaterService;
import com.project.detectsymptom.service.GeminiService;
import com.project.detectsymptom.service.JSON_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {


    /*
  GEMINI_API_KEY = AIzaSyDx2Vq_FGhvCjpVfa9WTVQJSq62YTMs3-k
  PROJECT_ID = intense-climber-415609
**/


    @Autowired
    private JSON_Service jsonService; // Inject the service


    @PostMapping("/submit")
    public ResponseEntity<?> submit(@RequestBody UserModel user ){

        String username = user.getUsername();
        String gender = user.getGender();
        int age = Integer.parseInt(user.getAge());

        System.out.println(username+" "+gender+" "+age);

        List<String> symptoms = user.getSymptoms();
        // Extracting unique symptoms
        HashSet<String> uniqueSet = new HashSet<>();
        uniqueSet.addAll(symptoms);
        symptoms = new ArrayList<>(uniqueSet);
        System.out.println(symptoms);

        Map<String , List<String>> analyzer;
        try {
            analyzer = jsonService.analyzeUserSymptoms(symptoms , age); // Use the injected service
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(analyzer, HttpStatus.OK);





    }

    @PostMapping("/generate-report")
    public byte[] generateReport(@RequestBody String html) throws IOException {
        // Create a PDF renderer
        System.out.println(html);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, null);
        builder.toStream(outputStream);
        builder.run();

        // Return the PDF as a byte array
        return outputStream.toByteArray();
    }




}


