package com.project.detectsymptom.model;

import java.util.List;

public class UserModel {
    private String username;
    private String gender;
    private String age;
    private List<String> symptoms;

    public UserModel(String username, String gender, String age, List<String> symptoms) {
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.symptoms = symptoms;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", symptoms=" + symptoms +
                '}';
    }
}
