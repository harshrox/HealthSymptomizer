package com.project.detectsymptom.service;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SymptomMatching {

    /*
    This service class has a method named match which takes three parameters , one disease name , second list of
    symptoms , and the last userArray containing user symptoms, it finds how many symptoms of userArray are present
    in symptoms list.

    It returns a list containing the disease and its percentage probability.
     */
    public List<String> match(String disease , List<String> symptoms , List<String> userArray){

        // Convert all symptoms to uppercase
        for (int i = 0; i < symptoms.size(); i++) {
            String element = symptoms.get(i);
            symptoms.set(i, element.toUpperCase());
        }

        int countSymptoms = 0;
        for(String symptom : userArray){
            if(symptoms.contains(symptom.toUpperCase())){
                countSymptoms++;
            }
        }

        List<String> result = new ArrayList<>();    // Output list containing disease and its percentage of happening

        // If no user symptoms match
        if(countSymptoms==0){
            result.add(0,disease);
            result.add(1,"0");
            return result;
        }

        // If user symptoms match
        double percentage = ((double) countSymptoms/symptoms.size())*100;
        DecimalFormat df = new DecimalFormat("#.##");
        result.add(0 , disease);
        result.add(1 , df.format(percentage));

        return result;
    }
}
