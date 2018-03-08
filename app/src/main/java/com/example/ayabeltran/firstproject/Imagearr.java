package com.example.ayabeltran.firstproject;

/**
 * Created by ayabeltran on 08/03/2018.
 */

public class Imagearr {

    private String message;
    private String path;

    public Imagearr(String message, String path) {
        this.message = message;
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
