package com.example.patientproject;


import com.example.patientproject.models.Doctor;

public class Response {
    private int code;
    private String message;
    private String description;
    private String name;
    public Doctor doctor;
    public Response(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public Response(int code, String message, String description, String name) {
        this.code = code;
        this.message = message;
        this.description = description;
        this.name = name;
    }

public Response(int code, String message, Doctor doctor) {
        this.code = code;
        this.message = message;
       this.doctor = doctor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public static Response ok(Response response) {
        // Assuming this method sets the HTTP status to 200 and returns the Response object
        response.setCode(200);
        return response;
    }

    public static Response success(int code,String message, String description) {
        return new Response(code, message, description);
    }
    public static Response successLogin(int code,String message, String description ,String name) {
        return new Response(code, message, description ,name);
    }
    public static Response successAdd(int code,String message,Doctor doctor) {
        return new Response(code, message,doctor);
    }
    public static Response failure(int code, String message, String description) {
        return new Response(code, message, description);
    }
}
