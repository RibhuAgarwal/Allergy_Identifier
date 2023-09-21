package com.example.allergyidentifier;


public class UserModel {
    private String name;
    private String email;
    private String gender;
    private String allergen;

    UserModel(){}

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getAllergen() {
        return allergen;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAllergen(String allergen) {
        this.allergen = allergen;
    }
}
