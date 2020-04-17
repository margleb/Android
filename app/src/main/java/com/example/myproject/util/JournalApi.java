package com.example.myproject.util;

import android.app.Application;

public class JournalApi extends Application {
    private String username;
    private String userId;
    private JournalApi instance;

    public JournalApi getInstance() {
        if(instance == null)
            instance = new JournalApi();
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
