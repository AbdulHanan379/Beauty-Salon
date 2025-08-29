package com.hanan.parlourapp;


public class LoginResponse {
    private String message;
    private String error;
    private String token;  // if your server sends a token

    public String getMessage() { return message; }
    public String getError() { return error; }
    public String getToken() { return token; }
}
