package com.project.detectsymptom.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class Json {
    List<String> userArray;
    public Json(List<String> arr){
        this.userArray = arr;
    }
    public List<String> test(){
        SymptomMatching check = new SymptomMatching();
        String disease = "Influenza";
        List<String> symptoms = Arrays.asList("Fever" , "Cough" , "Sore throat" , "Runny nose" , "Headache");
        List<String> result = check.match(disease, symptoms , this.userArray);
        return result;
    }
}
