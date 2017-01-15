package com.example.kamel.learnprogrammingapp;

/**
 * Created by kamel on 15/01/2017.
 */

public class Result {


    public Result(String result, String course, String userna) {
        this.result = result;
        this.course = course;
        this.username = userna;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;
    private String course;
    private String username;

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
