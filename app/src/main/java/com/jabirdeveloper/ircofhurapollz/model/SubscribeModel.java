package com.jabirdeveloper.ircofhurapollz.model;

public class SubscribeModel {
    private String status, message;

    public SubscribeModel(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
