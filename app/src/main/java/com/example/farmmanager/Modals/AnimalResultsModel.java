package com.example.farmmanager.Modals;

public class AnimalResultsModel {

    String tagnumber, gender, id,date,weight,checker,parent_tagnumber,type,breed;

    public AnimalResultsModel(String tagnumber, String gender, String id, String date, String weight,
                              String checker,String parent_tagnumber,String type, String breed) {
        this.tagnumber = tagnumber;
        this.gender = gender;
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.checker = checker;
        this.parent_tagnumber = parent_tagnumber;
        this.type = type;
        this.breed = breed;
    }

    public String getTagnumber() {
        return tagnumber;
    }

    public void setTagnumber(String tagnumber) {
        this.tagnumber = tagnumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getParent_tagnumber() {
        return parent_tagnumber;
    }

    public void setParent_tagnumber(String parent_tagnumber) {
        this.parent_tagnumber = parent_tagnumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
