package com.jabirdeveloper.ircofhurapollz.model.authentication;

public class ValideteCookie {
    private String status;
    private boolean valid;

    public ValideteCookie(String status, boolean valid) {
        this.status = status;
        this.valid = valid;
    }

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return valid;
    }
}
