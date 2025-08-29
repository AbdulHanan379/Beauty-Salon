package com.hanan.parlourapp;



public class ApiResponse {
    private String status;
    private String message;
    private User user; // only for login response

    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public User getUser() { return user; }
}