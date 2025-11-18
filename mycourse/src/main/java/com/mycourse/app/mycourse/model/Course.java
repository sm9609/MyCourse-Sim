package com.mycourse.app.mycourse.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Course {
    private Logger LOG = Logger.getLogger(Course.class.getName());
    

    // fields
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("instructor") private String instructor;
    @JsonProperty("credit") private int credit;
    
    public Course(
        @JsonProperty("id") int id,
        @JsonProperty("name") String name,
        @JsonProperty("instructor") String instructor,
        @JsonProperty("credit") int credit
    ){
        this.id = id;
        this.name = name;
        this.instructor = instructor;
        this.credit = credit;
    }

    //Getters
    public int getId(){
        return id;
    }

    public String getName(){
        return this.name;
    }

    public String getInstructor(){
        return this.instructor;
    }

    public int getCredit(){
        return this.credit;
    }

    //Setters
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setInstructor(String instructor){
        this.instructor = instructor;
    }

    public void setCredit(int credit){
        this.credit = credit;
    }
}
